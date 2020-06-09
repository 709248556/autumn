package com.autumn.evaluator.functions;

import com.autumn.evaluator.Enums;
import com.autumn.evaluator.Token;
import com.autumn.evaluator.ValueLexerParse;
import com.autumn.evaluator.Variant;
import com.autumn.evaluator.exception.FunctionParamException;
import com.autumn.util.DoubleLinkedList;

import java.util.HashMap;
import java.util.Iterator;
import java.util.function.Function;

/**
 * 函数基础
 * @author ycg
 */
public abstract class AbstractFunctionBase {

    private static HashMap<String, Enums.NodeType> criterias = new HashMap<>();

    static {
        criterias.put("=", Enums.NodeType.EQUAL);
        criterias.put("==", Enums.NodeType.EQUAL);
        criterias.put("!=", Enums.NodeType.NOT_EQUAL);
        criterias.put("<>", Enums.NodeType.NOT_EQUAL);
        criterias.put(">", Enums.NodeType.GREATER_THAN);
        criterias.put(">=", Enums.NodeType.GREATER_THAN_OR_EQUAL);
        criterias.put("!<", Enums.NodeType.GREATER_THAN_OR_EQUAL);
        criterias.put("<", Enums.NodeType.LESS_THAN);
        criterias.put("<=", Enums.NodeType.LESS_THAN_OR_EQUAL);
        criterias.put("!>", Enums.NodeType.LESS_THAN_OR_EQUAL);
    }

    /**
     * 创建条件
     *
     * @param funName    函数名称
     * @param paramName  参数名称
     * @param ignoreCase 忽略大小写
     * @param paramValue 参数值
     * @return
     */
    public static Function<Variant, Boolean> createCriteria(String funName, String paramName, boolean ignoreCase,
                                                            Variant paramValue) {
        if (paramValue.isNumber() || paramValue.isDateTime() || paramValue.isBoolean() || paramValue.isUUID()) {
            return (v) -> (v.getVariantType() == paramValue.getVariantType() && v.equals(paramValue));
        }
        if (!paramValue.isString()) {
            throw new FunctionParamException(funName, paramName, "定义的条件不正确或不支持。");
        }
        String str = paramValue.toString();
        if ("".equals(str)) {
            return (v) -> (v.isString() && "".equals((v.getValue())));
        }
        if (str.length() <= 1) {
            if (criterias.containsKey(str)) {
                throw new FunctionParamException(funName, paramName, "定义的条件不正确或不支持。");
            }
            if (ignoreCase) {
                return (v) -> (v.isString() && ((String) v.getValue()).compareToIgnoreCase(str) == 0);
            } else {
                return (v) -> (v.isString() && ((String) v.getValue()).compareTo(str) == 0);
            }
        }
        boolean flag;
        String start;
        if (str.length() > 2) {
            start = str.substring(0, 2);
            if (criterias.containsKey(start)) {
                flag = true;
            } else {
                start = str.substring(0, 1);
                flag = criterias.containsKey(start);
            }
        } else {
            start = str.substring(0, 1);
            flag = criterias.containsKey(start);
        }
        if (!flag) {
            if (ignoreCase) {
                return (v) -> (v.isString() && ((String) v.getValue()).compareToIgnoreCase(str) == 0);
            } else {
                return (v) -> (v.isString() && ((String) v.getValue()).compareTo(str) == 0);
            }
        }
        ValueLexerParse lexer = new ValueLexerParse(paramValue.toString());
        DoubleLinkedList<Token> tokens = lexer.readTokens();
        Variant equalValue;
        Enums.NodeType type = Enums.NodeType.EQUAL;
        Token node = tokens.getFirst();
        Token token = node;
        if (token.getType() == Enums.TokenType.DELIMITER && (type = criterias.get(token.getText())) == null) {
            throw new FunctionParamException(funName, paramName, "定义的条件不正确或不支持。");
        }
        if (tokens.size() == 2) {
            node = tokens.getLast();
            token = node;
            switch (token.getType()) {
                case DATETIME:
                case DECIMAL:
                case DOUBLE:
                case UUID:
                case INTEGER:
                case STRING:
                case VARIABLE:
                    equalValue = new Variant(token.getValue());
                    break;
                default:
                    throw new FunctionParamException(funName, paramName, "定义的条件不正确或不支持。");
            }
        } else {
            StringBuilder builder = new StringBuilder();
            for (Iterator<Token> iter = tokens.listIterator(1); iter.hasNext(); ) {
                builder.append(((Token) iter.next().getValue()).getText());
            }
            equalValue = new Variant(builder.toString());
            type = Enums.NodeType.EQUAL;
        }
        if (equalValue.isString()) {
            switch (type) {
                case EQUAL:
                    return (v) -> {
                        if (ignoreCase) {
                            return (v.isString()
                                    && ((String) v.getValue()).compareToIgnoreCase((String) equalValue.getValue()) == 0);
                        } else {
                            return (v.isString() && ((String) v.getValue()).compareTo((String) equalValue.getValue()) == 0);
                        }
                    };
                case NOT_EQUAL:
                    return (v) -> {
                        if (ignoreCase) {
                            return (v.isString()
                                    && ((String) v.getValue()).compareToIgnoreCase((String) equalValue.getValue()) != 0);
                        } else {
                            return (v.isString() && ((String) v.getValue()).compareTo((String) equalValue.getValue()) != 0);
                        }
                    };
                case GREATER_THAN:
                    return (v) -> {
                        if (ignoreCase) {
                            return (v.isString()
                                    && ((String) v.getValue()).compareToIgnoreCase((String) equalValue.getValue()) > 0);
                        } else {
                            return (v.isString() && ((String) v.getValue()).compareTo((String) equalValue.getValue()) > 0);
                        }
                    };
                case GREATER_THAN_OR_EQUAL:
                    return (v) -> {
                        if (ignoreCase) {
                            return (v.isString()
                                    && ((String) v.getValue()).compareToIgnoreCase((String) equalValue.getValue()) >= 0);
                        } else {
                            return (v.isString() && ((String) v.getValue()).compareTo((String) equalValue.getValue()) >= 0);
                        }
                    };
                case LESS_THAN:
                    return (v) -> {
                        if (ignoreCase) {
                            return (v.isString()
                                    && ((String) v.getValue()).compareToIgnoreCase((String) equalValue.getValue()) < 0);
                        } else {
                            return (v.isString() && ((String) v.getValue()).compareTo((String) equalValue.getValue()) < 0);
                        }
                    };
                case LESS_THAN_OR_EQUAL:
                    return (v) -> {
                        if (ignoreCase) {
                            return (v.isString()
                                    && ((String) v.getValue()).compareToIgnoreCase((String) equalValue.getValue()) <= 0);
                        } else {
                            return (v.isString() && ((String) v.getValue()).compareTo((String) equalValue.getValue()) <= 0);
                        }
                    };
                default:
                    return (v) -> false;
            }
        } else {
            switch (type) {
                case EQUAL:
                    return (v) -> v.compareTo(equalValue) == 0;
                case NOT_EQUAL:
                    return (v) -> v.compareTo(equalValue) != 0;
                case GREATER_THAN:
                    return (v) -> v.compareTo(equalValue) > 0;
                case GREATER_THAN_OR_EQUAL:
                    return (v) -> v.compareTo(equalValue) >= 0;
                case LESS_THAN:
                    return (v) -> v.compareTo(equalValue) < 0;
                case LESS_THAN_OR_EQUAL:
                    return (v) -> v.compareTo(equalValue) <= 0;
                default:
                    return (v) -> false;
            }
        }
    }
}