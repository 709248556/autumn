package com.autumn.evaluator;

import com.autumn.evaluator.exception.FunctionNotExistException;
import com.autumn.evaluator.exception.ParserException;
import com.autumn.evaluator.nodes.AbstractTreeNode;
import com.autumn.util.DateUtils;
import com.autumn.util.DoubleLinkedList;
import com.autumn.util.StringUtils;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

/**
 * 文法解析
 */
public abstract class AbstractLexerParse extends AbstractParse implements LexerParse {

    /**
     * 二元运算符集合
     */
    public final static HashMap<String, Enums.NodeType> BINARY_OPERATORS = new HashMap<String, Enums.NodeType>() {

        /**
         * 序列号
         */
        private static final long serialVersionUID = -5670922709722204901L;

        {
            put(OperatorConstant.SYMBOL_EQ, Enums.NodeType.EQUAL);
            put(OperatorConstant.SYMBOL_EQ2, Enums.NodeType.EQUAL);
            put(OperatorConstant.SYMBOL_NOT_EQ, Enums.NodeType.NOT_EQUAL);
            put(OperatorConstant.SYMBOL_NOT_EQ2, Enums.NodeType.NOT_EQUAL);
            put(OperatorConstant.SYMBOL_LT, Enums.NodeType.LESS_THAN);
            put(OperatorConstant.SYMBOL_LE, Enums.NodeType.LESS_THAN_OR_EQUAL);
            put(OperatorConstant.SYMBOL_NOT_GT, Enums.NodeType.LESS_THAN_OR_EQUAL);
            put(OperatorConstant.SYMBOL_GT, Enums.NodeType.GREATER_THAN);
            put(OperatorConstant.SYMBOL_GE, Enums.NodeType.GREATER_THAN_OR_EQUAL);
            put(OperatorConstant.SYMBOL_NOT_LT, Enums.NodeType.GREATER_THAN_OR_EQUAL);
            put(OperatorConstant.SYMBOL_PLUS, Enums.NodeType.ADD);
            put(OperatorConstant.SYMBOL_REDUCE, Enums.NodeType.SUBTRACT);
            put(OperatorConstant.SYMBOL_MULTIPLY, Enums.NodeType.MULTIPLY);
            put(OperatorConstant.SYMBOL_DIVIDE, Enums.NodeType.DIVIDE);
            put(OperatorConstant.SYMBOL_PERCENTAGE, Enums.NodeType.MODULO);
            put(OperatorConstant.SYMBOL_CONNECT, Enums.NodeType.CONCAT);
            put(OperatorConstant.SYMBOL_XOR, Enums.NodeType.POWER);
            put(OperatorConstant.SYMBOL_AND2, Enums.NodeType.AND);
            put(OperatorConstant.SYMBOL_AND, Enums.NodeType.AND);
            put(OperatorConstant.SYMBOL_OR, Enums.NodeType.OR);
            put(OperatorConstant.SYMBOL_OR2, Enums.NodeType.OR);
        }
    };

    /**
     * 一元运算符集合
     */
    public final static HashMap<String, Enums.NodeType> UNARY_OPERATORS = new HashMap<String, Enums.NodeType>() {

        /**
         * 序列号
         */
        private static final long serialVersionUID = 98462043192116351L;

        {
            put(OperatorConstant.SYMBOL_REDUCE, Enums.NodeType.NEGATE);
            put(OperatorConstant.SYMBOL_PLUS, Enums.NodeType.UNARY_PLUS);
            put(OperatorConstant.SYMBOL_EXCLAMATORY_MARK, Enums.NodeType.NOT);
            put(OperatorConstant.SYMBOL_NOT, Enums.NodeType.NOT);
            put(OperatorConstant.SYMBOL_PERCENTAGE, Enums.NodeType.PERCENT);
        }
    };

    /**
     * 运算符级别集合
     */
    public final static HashMap<String, Integer> OPERATOR_LEVELS = new HashMap<String, Integer>() {

        /**
         * 序列号
         */
        private static final long serialVersionUID = -8827676047759375808L;

        {
            put(OperatorConstant.SYMBOL_EXCLAMATORY_MARK, 1);
            put(OperatorConstant.SYMBOL_NOT, 1);
            put(OperatorConstant.SYMBOL_XOR, 2);
            put(OperatorConstant.SYMBOL_MULTIPLY, 3);
            put(OperatorConstant.SYMBOL_DIVIDE, 3);
            put(OperatorConstant.SYMBOL_PERCENTAGE, 3);
            put(OperatorConstant.SYMBOL_PLUS, 4);
            put(OperatorConstant.SYMBOL_REDUCE, 4);
            put(OperatorConstant.SYMBOL_CONNECT, 4);
            put(OperatorConstant.SYMBOL_GT, 5);
            put(OperatorConstant.SYMBOL_LT, 5);
            put(OperatorConstant.SYMBOL_GE, 5);
            put(OperatorConstant.SYMBOL_NOT_LT, 5);
            put(OperatorConstant.SYMBOL_LE, 5);
            put(OperatorConstant.SYMBOL_NOT_GT, 5);
            put(OperatorConstant.SYMBOL_EQ, 6);
            put(OperatorConstant.SYMBOL_EQ2, 6);
            put(OperatorConstant.SYMBOL_NOT_EQ2, 6);
            put(OperatorConstant.SYMBOL_NOT_EQ, 6);
            put(OperatorConstant.SYMBOL_AND, 7);
            put(OperatorConstant.SYMBOL_AND2, 7);
            put(OperatorConstant.SYMBOL_OR, 8);
            put(OperatorConstant.SYMBOL_OR2, 8);
        }
    };

    public static HashMap<String, String> systemDelimiter;


    protected String delimiter; // 当前运算符

    protected String expression;

    protected int index;

    protected String previousDelimiter; // 上一个运算符

    static {
        systemDelimiter = new HashMap<>();
        systemDelimiter.put(OperatorConstant.SYMBOL_AND, "And");
        systemDelimiter.put(OperatorConstant.SYMBOL_OR, "Or");
        systemDelimiter.put(OperatorConstant.SYMBOL_NOT, "Not");
    }

    /**
     * 实例化 TokenParse 类新实例
     *
     * @param expression 表达式
     */
    protected AbstractLexerParse(String expression) {
        this(expression, OperatorConstant.FULL_DELIMITER);
    }

    /**
     * 实例化 TokenParse 类新实例
     *
     * @param expression
     * @param delimiter
     */
    protected AbstractLexerParse(String expression, String delimiter) {
        super();
        this.expression = expression.trim();
        this.previousDelimiter = null;
        this.index = 0;
        this.delimiter = delimiter;
    }

    /**
     * 编译
     *
     * @return
     */
    @Override
    public final AbstractTreeNode compiled() {
        DoubleLinkedList<Token> link = new DoubleLinkedList<>();
        Token token;
        while ((token = this.read()) != null) {
            link.addLast(token);
        }
        NodeParse parse = new NodeParse(link, null);
        return parse.compiled();
    }

    /**
     * 读取标记集合
     *
     * @return
     */
    @Override
    public final DoubleLinkedList<Token> readTokens() {
        DoubleLinkedList<Token> link = new DoubleLinkedList<>();
        Token token;
        this.previousDelimiter = null;
        this.index = 0;
        while ((token = this.read()) != null) {
            link.addLast(token);
        }
        return link;
    }

    /**
     * 读取标记
     *
     * @return
     */
    protected abstract Token read();

    /**
     * 过滤空字符
     */
    protected final void scanWhite() {
        while (this.index < this.expression.length() && StringUtils.isWhiteChar(this.expression.charAt(this.index))) {
            this.index++;
        }
    }

    /**
     * 读取运算符
     *
     * @param delimiter    当前运算符
     * @param combinations 可组合的运算符
     * @return
     */
    protected final Token readDelimiterToken(char delimiter, char... combinations) {
        Token result = null;
        if (this.expression.charAt(this.index) == delimiter) {
            this.index++;
            if (this.index < this.expression.length()) {
                if (combinations != null) {
                    StringBuilder sb = new StringBuilder();
                    for (char c : combinations) {
                        if (this.expression.charAt(index) == c) {
                            sb.append(delimiter);
                            sb.append(c);
                            result = new Token(sb.toString(), null, Enums.TokenType.DELIMITER, this.index, 1);
                            this.index++;
                            return result;
                        }
                    }
                }
            }
            result = new Token(String.valueOf(delimiter), null, Enums.TokenType.DELIMITER, this.index - 1, 1);
            return result;
        }
        return result;
    }

    /**
     * 读取函数和变量和系统运算符 必须符合 Unicode 标准 即必须是以字母为开头
     *
     * @return
     */
    protected final Token readFunctionOrVariableToken() {
        Token result = null;
        if (isLetter(this.expression.charAt(this.index))) {
            int startIndex = this.index;
            do {
                this.index++;
                if (this.index >= this.expression.length()) {
                    break;
                }
            } while (!isDelimiter(this.delimiter, this.expression.charAt(this.index))
                    && !StringUtils.isWhiteChar(this.expression.charAt(this.index)));
            String name = this.expression.substring(startIndex, this.index).trim(); // 不区分大小写
            this.scanWhite();
            if (!OperatorConstant.SYMBOL_QUESTION_MARK.equals(this.previousDelimiter)) {
                if (this.index < this.expression.length()) {
                    if (this.expression.charAt(this.index) == OperatorConstant.CHAR_COLON) // 遇到数组或三元运算符，但有空格时
                    {
                        name = name + OperatorConstant.SYMBOL_COLON;
                        this.index++;
                        int start1 = this.index;
                        do {
                            this.index++;
                            if (this.index >= this.expression.length()) {
                                break;
                            }
                        } while (!isDelimiter(this.delimiter, this.expression.charAt(this.index)));
                        name = name + this.expression.substring(start1, this.index).trim();
                        this.scanWhite();
                    }
                }
            }
            if ((!OperatorConstant.SYMBOL_QUESTION_MARK.equals(this.previousDelimiter)) && name.contains(OperatorConstant.SYMBOL_COLON)) // 数组
            {
                result = readArrayToken(startIndex, name);
            } else {
                result = readObjectToken(startIndex, name);
            }
        }
        return result;
    }

    /**
     * 读取数组
     *
     * @param startIndex
     * @param name
     * @return
     */
    protected abstract Token readArrayToken(int startIndex, String name);

    /**
     * 读取函数或系统变量或变量
     *
     * @param startIndex
     * @param name
     * @return
     */
    protected final Token readObjectToken(int startIndex, String name) {
        Enums.TokenType type;
        String sysDelimiter;
        if (this.index < this.expression.length() && this.expression.charAt(this.index) == OperatorConstant.CHAR_LEFT_BRACKETS) {
            if (StringUtils.isWhiteChar(this.expression.charAt(this.index - 1))) {
                // 若如出现上一空格的现象，则转为运算符，如：9>8 and (9>8)
                if ((sysDelimiter = systemDelimiter.get(name)) != null) {
                    name = sysDelimiter;
                    type = Enums.TokenType.DELIMITER;
                } else {
                    // 函数
                    if (FunctionManager.existFunction(name)) {
                        type = Enums.TokenType.FUNCTION;
                    } else {
                        throw new FunctionNotExistException(startIndex, this.index - startIndex, name,
                                String.format("函数 %s 未注册。", name));
                    }
                }
            } else {
                // 函数
                if (FunctionManager.existFunction(name)) {
                    type = Enums.TokenType.FUNCTION;
                } else {
                    if ((sysDelimiter = systemDelimiter.get(name)) != null) {
                        name = sysDelimiter;
                        type = Enums.TokenType.DELIMITER;
                    } else {
                        throw new FunctionNotExistException(startIndex, this.index - startIndex, name,
                                String.format("函数 %s 未注册。", name));
                    }
                }
            }
        } else {
            if ((sysDelimiter = systemDelimiter.get(name)) != null) {
                name = sysDelimiter;
                type = Enums.TokenType.DELIMITER;
            } else {
                Variable variable = Variable.getVariable(name);
                if (variable != null) {
                    name = variable.getName();
                }
                type = Enums.TokenType.VARIABLE;
            }
        }
        return new Token(name, name, type, startIndex, this.index - startIndex);
    }

    /**
     * 读取数字
     *
     * @return
     */
    protected final Token readNumberToken() {
        Token result = null;
        Object value;
        if (isDigit(this.expression.charAt(this.index))) {
            int start = this.index;
            boolean isScience = false; // 是否是科学格式
            do {
                this.index++;
                if (this.index >= this.expression.length()) {
                    break;
                }
            } while (isDigit(this.expression.charAt(this.index)) || this.expression.charAt(this.index) == OperatorConstant.CHAR_DOT);
            StringBuilder sb = new StringBuilder(this.expression.substring(start, this.index));
            if (this.index + 1 < this.expression.length()) // 验证科学记数
            {
                String science = this.expression.substring(this.index, this.index + 2);
                if (OperatorConstant.SCIENCE_E_PLUS.equalsIgnoreCase(science)
                        || OperatorConstant.SCIENCE_E_REDUCE.equalsIgnoreCase(science)) {
                    this.index += 2;
                    if (this.index >= this.expression.length()) {
                        throw new ParserException(start, this.index - start, "科学记数法格式不正确。");
                    }
                    if (isDigit(this.expression.charAt(this.index))) {
                        int s = this.index;
                        sb.append(science);
                        do {
                            this.index++;
                            if (this.index >= this.expression.length()) {
                                break;
                            }
                        } while (isDigit(this.expression.charAt(this.index)));
                        sb.append(this.expression, s, this.index);
                    } else {
                        throw new ParserException(start, this.index - start, "科学记数法格式不正确。");
                    }
                    isScience = true;
                }
            }
            Enums.TokenType type;
            String strResult = sb.toString();
            if (isScience) // 科学记数
            {
                type = Enums.TokenType.DOUBLE;
                value = Double.parseDouble(strResult);
            } else {
                boolean isPoint = strResult.contains(OperatorConstant.SYMBOL_DOT);
                if (isPoint) // 有小数点
                {
                    BigDecimal dv = new BigDecimal(0);
                    boolean tempVar = true;
                    try {
                        dv = new BigDecimal(strResult);
                    } catch (Exception e) {
                        tempVar = false;
                    }
                    if (tempVar) {
                        type = Enums.TokenType.DECIMAL;
                        value = dv;
                    } else {
                        type = Enums.TokenType.DOUBLE;
                        value = Double.parseDouble(strResult);
                    }
                } else {
                    if (strResult.length() < 19) // 小于long.MaxValue 长度
                    {
                        type = Enums.TokenType.INTEGER;
                        value = Long.parseLong(strResult);
                    } else {
                        long iv = 0;
                        boolean tempVar2 = true;
                        try {
                            iv = Long.parseLong(strResult);
                        } catch (Exception e) {
                            tempVar2 = false;
                        }
                        if (tempVar2) {
                            type = Enums.TokenType.INTEGER;
                            value = iv;
                        } else {
                            BigDecimal dv = new BigDecimal(0);
                            boolean tempVar3 = true;
                            try {
                                dv = new BigDecimal(strResult);
                            } catch (Exception e) {
                                tempVar3 = false;
                            }
                            if (tempVar3) {
                                type = Enums.TokenType.DECIMAL;
                                value = dv;
                            } else {
                                type = Enums.TokenType.DOUBLE;
                                value = Double.parseDouble(strResult);
                            }
                        }
                    }
                }
            }
            result = new Token(strResult, new Variant(value), type, start, this.index - start);
            return result;
        }
        return result;
    }

    /**
     * 读取日期时间值
     *
     * @param beginChar
     * @param endChar
     * @return
     */
    protected final Token readDateTimeToken(char beginChar, char endChar) {
        Token result = null;
        if (this.expression.charAt(this.index) == beginChar) {
            int start = this.index + 1;
            do {
                this.index++;
            } while (this.index < this.expression.length() && this.expression.charAt(index) != endChar);
            if (this.index >= this.expression.length()) {
                throw new ParserException(start - 1, this.index - Math.max(0, (start - 2)), "日期格式无效。");
            }
            String name = this.expression.substring(start, this.index);
            Date d = new Date(0);
            Enums.TokenType type;
            boolean tempVar = name.length() >= 8;
            try {
                d = DateUtils.parseDate(name);
            } catch (Exception e) {
                tempVar = false;
            }
            if (tempVar) {
                type = Enums.TokenType.DATETIME;
            } else {
                throw new ParserException(start - 1, this.index - Math.max(0, (start - 2)), "日期格式无效。");
            }
            result = new Token(name, new Variant(d), type, start - 1, this.index - Math.max(0, (start - 2)));
            this.index++;
            return result;
        }
        return result;
    }

    /**
     * 读取分割值
     *
     * @param startDelimiter           开始分割符
     * @param endDelimiter             结束分割符
     * @param type                     类型
     * @param isSupportEscapeCharacter 是否支持转义符
     * @return
     */
    protected final Token readSplitToken(char startDelimiter, char endDelimiter, Enums.TokenType type,
                                         boolean isSupportEscapeCharacter) {
        Token result = null;
        if (this.expression.charAt(this.index) == startDelimiter) {
            boolean isEnd;
            int start = this.index + 1;
            StringBuilder builder = new StringBuilder();
            do {
                this.index++;
                char indexValue = this.expression.charAt(this.index);
                isEnd = indexValue == endDelimiter;
                if (isSupportEscapeCharacter && isEnd && this.index > 0) {
                    if (this.expression.charAt(this.index - 1) == '\\') {
                        if (builder.length() > 0) {
                            builder.deleteCharAt(builder.length() - 1);
                        }
                        isEnd = false;
                    }
                }
                if (!isEnd) {
                    builder.append(indexValue);
                }
            } while (this.index < this.expression.length() && !isEnd);
            if (this.index >= this.expression.length()) {
                throw new ParserException(start - 1, this.index - Math.max(0, (start - 2)),
                        String.format("无法解析的表达式,缺少运算符 %s 。", String.valueOf(endDelimiter)));
            }
            String text = builder.toString();
            Object value = text;
            if (type == Enums.TokenType.STRING || type == Enums.TokenType.UUID) {
                if (text.length() == 36) {
                    try {
                        UUID id = UUID.fromString(text);
                        text = id.toString();
                        type = Enums.TokenType.UUID;
                        value = id;
                    } catch (java.lang.Exception e) {
                        if (type == Enums.TokenType.UUID) {
                            type = Enums.TokenType.STRING;
                        }
                    }
                }
            }
            int startIndex = start - 1;
            int length = this.index - Math.max(0, (start - 2));
            if (type == Enums.TokenType.STATIC_ARRAY) {
                result = new Token(text, createStaticArray(text, "#", startIndex, length), type, startIndex, length);
            } else {
                result = new Token(text, new Variant(value), type, startIndex, length);
            }
            this.index++;
            return result;
        }
        return result;
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
    protected abstract Variant createStaticArray(String text, String delimiter, int startIndex, int length);

    /**
     * 是否是运算符
     *
     * @param value
     * @param c
     * @return
     */
    public static boolean isDelimiter(String value, char c) {
        return value.indexOf(c) != -1;
    }

    /**
     * 是否属于 Unicode 字符字母类别(包含中文)
     *
     * @param c
     * @return
     */
    public static boolean isLetter(char c) {
        return Character.isLetter(c);
    }

    /**
     * 是否属于十进制
     *
     * @param c
     * @return
     */
    public static boolean isDigit(char c) {
        return Character.isDigit(c);
    }
}