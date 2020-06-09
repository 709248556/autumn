package com.autumn.evaluator;

//
// * 解析表达式，生成表达式树节点
//

import com.autumn.evaluator.exception.ParserDelimiterException;
import com.autumn.util.DateUtils;
import com.autumn.util.StringUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.UUID;

/**
 * 值文法解析
 */
public class ValueLexerParse extends AbstractLexerParse {

    /**
     * 实例化 TokenParse 类新实例
     *
     * @param expression 表达式
     */
    public ValueLexerParse(String expression) {
        this(expression, OperatorConstant.FULL_DELIMITER);
    }

    /**
     * 实例化 TokenParse 类新实例
     *
     * @param expression
     * @param delimiter
     */
    private ValueLexerParse(String expression, String delimiter) {
        super(expression, delimiter);
    }

    /**
     * 读取标记
     *
     * @return
     */
    @Override
    protected Token read() {
        Token result = null;
        try {
            this.scanWhite();
            if (this.index >= this.expression.length()) {
                return null;
            }
            // 符合运算符
            if (isDelimiter(this.delimiter, this.expression.charAt(this.index))) {
                result = this.readDelimiterToken(OperatorConstant.CHAR_LT, OperatorConstant.CHAR_GT, OperatorConstant.CHAR_EQ);
                if (result != null) {
                    return result;
                }
                result = this.readDelimiterToken(OperatorConstant.CHAR_GT, OperatorConstant.CHAR_EQ);
                if (result != null) {
                    return result;
                }
                result = this.readDelimiterToken(OperatorConstant.CHAR_CONNECT, OperatorConstant.CHAR_CONNECT);
                if (result != null) {
                    return result;
                }
                result = this.readDelimiterToken(OperatorConstant.CHAR_SEPARATE, OperatorConstant.CHAR_SEPARATE);
                if (result != null) {
                    if ("|".equals(result.getText())) {
                        throw new ParserDelimiterException(result.getStartIndex(), result.getLength(), OperatorConstant.SYMBOL_SEPARATE,
                                String.format("无法解析的表达式，不支持的运算符 \"%s\" 。", "|"));
                    }
                    return result;
                }
                result = this.readDelimiterToken(OperatorConstant.CHAR_EXCLAMATORY_MARK,
                        OperatorConstant.CHAR_EQ, OperatorConstant.CHAR_GT, OperatorConstant.CHAR_LT);
                if (result != null) {
                    return result;
                }
                result = this.readDelimiterToken(OperatorConstant.CHAR_EQ, OperatorConstant.CHAR_EQ);
                if (result != null) {
                    return result;
                }
                // 其他无组合的运算符
                result = new Token(this.expression.charAt(this.index) + "", null, Enums.TokenType.DELIMITER, this.index,
                        1);
                this.index++;
                return result;
            }

            // 获取函数\数组1\变量\系统运算符
            result = this.readFunctionOrVariableToken();
            if (result != null) {
                return result;
            }

            // 数字
            result = this.readNumberToken();
            if (result != null) {
                return result;
            }

            // 特殊变量
            result = this.readSplitToken(OperatorConstant.CHAR_LEFT_BRACKETS,
                    OperatorConstant.CHAR_RIGHT_BRACKETS, Enums.TokenType.VARIABLE, false);
            if (result != null) {
                return result;
            }

            // 日期时间型
            result = this.readDateTimeToken('#', '#');
            if (result != null) {
                return result;
            }
            // 字符窜和UUID
            result = this.readSplitToken('"', '"', Enums.TokenType.STRING, true);
            if (result != null) {
                return result;
            }
            // 字符窜和UUID
            result = this.readSplitToken('\'', '\'', Enums.TokenType.STRING, true);
            if (result != null) {
                return result;
            }
            // 组数2(静态组数)
            result = this.readSplitToken('[', ']', Enums.TokenType.STATIC_ARRAY, false);
            if (result != null) {
                return result;
            }
            // 不支持的运算符
            if (this.index < this.expression.length()) {
                int start = this.index;
                do {
                    this.index++;
                    if (this.index >= this.expression.length()) {
                        break;
                    }
                } while (!StringUtils.isWhiteChar(this.expression.charAt(this.index)));
                String unknownName = this.expression.substring(start, this.index);
                this.scanWhite();
                // 多余的运算符(未实现或不支持)
                throw new ParserDelimiterException(start, this.index - start, unknownName,
                        String.format("无法解析的表达式，不支持的运算符 \"%s\" 。", unknownName));
            } else {
                return null;
            }
        } finally {
            if (result != null) {
                if (result.getType() == Enums.TokenType.DELIMITER) {
                    this.previousDelimiter = result.getText();
                }
            }
        }
    }

    /**
     * 读取数组
     *
     * @param startIndex
     * @param name
     * @return
     */
    @Override
    protected Token readArrayToken(int startIndex, String name) {
        String[] split = name.split(":");
        if (split.length > 2) {
            throw new ParserDelimiterException(startIndex, this.index - startIndex, ":",
                    String.format("组数 %s 无效,组数中只能包含一个 \":\" 。", name));
        }
        if ("".equals(split[1])) // 需要取得下标
        {
            this.scanWhite();
            if (this.index < this.expression.length()) {
                int start1 = this.index;
                do {
                    this.index++;
                    if (this.index >= this.expression.length()) {
                        break;
                    }
                } while (!isDelimiter(this.delimiter, this.expression.charAt(this.index)));
                split[1] = this.expression.substring(start1, this.index).trim();
            }
        }
        split[0] = split[0].trim();
        split[1] = split[1].trim();
        name = split[0] + ":" + split[1];
        ArrayPosition start = ArrayPosition.getArrayPosition(split[0]);
        ArrayPosition end = ArrayPosition.getArrayPosition(split[1]);
        if (start.getColNumber() > end.getColNumber()) {
            throw new ParserDelimiterException(startIndex, this.index - startIndex, ":",
                    String.format("组数 %s 的下标标识不能小于上标标识。", name));
        }
        if (start.getRowNumber() > end.getRowNumber()) {
            throw new ParserDelimiterException(startIndex, this.index - startIndex, ":",
                    String.format("组数 %s 的下标位置不能小于上标位置。", name));
        }
        ArrayPosition[] value = new ArrayPosition[]{start, end};
        return new Token(name, value, Enums.TokenType.ARRAY, startIndex, this.index - startIndex);
    }

    /**
     * 创建静态数组
     *
     * @param text
     * @param delimiter
     * @param startIndex
     * @param length
     * @return
     */
    @Override
    protected Variant createStaticArray(String text, String delimiter, int startIndex, int length) {
        if (StringUtils.isNullOrBlank(text)) {
            throw new ParserDelimiterException(startIndex, length, delimiter, "静态数组格式不正确。");
        }
        ArrayList<ArrayList<Token>> vTokens = new ArrayList<>();
        ValueLexerParse scanner = new ValueLexerParse(text, ",;");
        Token token;
        ArrayList<Token> tokens = null;
        while ((token = scanner.read()) != null) {
            if (token.getType() != Enums.TokenType.DELIMITER) {
                if (!(token.getType() == Enums.TokenType.DATETIME || token.getType() == Enums.TokenType.DECIMAL
                        || token.getType() == Enums.TokenType.DOUBLE || token.getType() == Enums.TokenType.UUID
                        || token.getType() == Enums.TokenType.INTEGER || token.getType() == Enums.TokenType.STRING)) {
                    throw new ParserDelimiterException(startIndex, length, delimiter, "静态数组只支持常量(数值|日期|字符窜|UUID)。");
                }
                if (tokens == null) {
                    tokens = new ArrayList<>();
                    vTokens.add(tokens);
                }
                tokens.add(token);
            } else {
                if (";".equals(token.getText())) {
                    tokens = null;
                }
            }
        }
        if (vTokens.isEmpty()) {
            throw new ParserDelimiterException(startIndex, length, delimiter, "静态数组格式不正确。");
        }
        int size = vTokens.get(0).size();
        int count = vTokens.size();
        for (ArrayList<Token> vToken : vTokens) {
            tokens = vToken;
            if (tokens.size() != size) {
                throw new ParserDelimiterException(startIndex, length, delimiter, "静态数组维数不相同。");
            }
        }
        ArrayList<Variant> vCols = new ArrayList<>();
        for (int c = 0; c < size; c++) {
            ArrayList<Variant> vRows = new ArrayList<>();
            for (ArrayList<Token> rows : vTokens) {
                Token ken = rows.get(c);
                switch (ken.getType()) {
                    case DATETIME:
                        vRows.add(new Variant(DateUtils.parseDate(ken.getText())));
                        break;
                    case DECIMAL:
                        vRows.add(new Variant(new BigDecimal(ken.getText())));
                        break;
                    case DOUBLE:
                        vRows.add(new Variant(Double.parseDouble(ken.getText())));
                        break;
                    case INTEGER:
                        vRows.add(new Variant(Long.parseLong(ken.getText())));
                        break;
                    case UUID:
                        vRows.add(new Variant(UUID.fromString(ken.getText())));
                        break;
                    case STRING:
                        vRows.add(new Variant(ken.getText()));
                        break;
                    default:
                        break;
                }
            }
            vCols.add(new Variant(vRows.toArray(new Variant[]{})));
        }
        return new Variant(vCols.toArray(new Variant[]{}));
    }

}