package com.autumn.evaluator;


import com.autumn.evaluator.nodes.*;
import com.autumn.exception.ArgumentNullException;
import com.autumn.exception.FormatException;
import com.autumn.util.DoubleLinkedList;
import com.autumn.util.StringUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

//
// * 用于常用的表达式,数组等解析相关的帮助
//

/**
 * 解析帮助
 */
public final class EvaluatorUtils {

    /**
     * 解析并返回结果
     *
     * @param expression 表达式
     * @param context    上下文接口,可传入 null
     * @return 返回 Variant 对象
     */
    public static Variant valueParse(String expression, Context context) {
        return nodeCompiled(expression, new ValueLexerParse(expression)).parse(new DefaultParseContext(context));
    }

    /**
     * 解析并返回结果
     *
     * @param expression 表达式
     * @param lexerParse 解析器
     * @param context    上下文接口,可传入 null
     * @return 返回 Variant 对象
     */
    public static Variant valueParse(String expression, LexerParse lexerParse, Context context) {
        return nodeCompiled(expression, lexerParse).parse(new DefaultParseContext(context));
    }

    /**
     * 解析并返回结果
     *
     * @param node    节点
     * @param context 上下文接口,可传入 null
     * @return 返回 Variant 对象
     */
    public static Variant valueParse(AbstractTreeNode node, Context context) {
        if (node == null) {
            throw new ArgumentNullException("node");
        }
        return node.parse(new DefaultParseContext(context));
    }

    /**
     * 解析并返回结果
     *
     * @param tokens  标记集合
     * @param context 上下文接口,可传入 null
     * @return 返回 Variant 对象
     */
    public static Variant valueParse(Collection<Token> tokens, Context context) {
        AbstractTreeNode node = nodeCompiled(tokens);
        return node.parse(new DefaultParseContext(context));
    }

    /**
     * 编译并返回节点
     *
     * @param expression 表达式
     * @param lexerParse
     * @return
     */
    public static AbstractTreeNode nodeCompiled(String expression, LexerParse lexerParse) {
        if (StringUtils.isNullOrBlank(expression)) {
            return AbstractTreeNode.createNullNode();
        }
        if (lexerParse == null) {
            lexerParse = new ValueLexerParse(expression);
        }
        return lexerParse.compiled();
    }

    /**
     * 编译并返回节点
     *
     * @param expression 表达式
     * @return
     */
    public static AbstractTreeNode nodeCompiled(String expression) {
        return nodeCompiled(expression, null);
    }

    /**
     * 数组是否存在变量
     *
     * @param arrayName    数组名称
     * @param variableName 变量名称
     * @return
     */
    public static boolean arrayExistVariable(String arrayName, String variableName) {
        if (StringUtils.isNullOrBlank(arrayName)) {
            throw new ArgumentNullException("arrayName");
        }
        if (StringUtils.isNullOrBlank(variableName)) {
            throw new ArgumentNullException("variableName");
        }
        HashSet<String> keys = new HashSet<>();
        keys.add(variableName);
        return arrayExistVariable(arrayName, keys);
    }

    /**
     * 数组是否存在变量
     *
     * @param arrayName 数组名称
     * @param variables 变量集合
     * @return
     */
    public static boolean arrayExistVariable(String arrayName, Collection<String> variables) {
        if (StringUtils.isNullOrBlank(arrayName)) {
            throw new ArgumentNullException("arrayName");
        }
        if (variables == null) {
            throw new ArgumentNullException("Variables");
        }
        if (variables.size() == 0) {
            return false;
        }
        Collection<String> keys;
        if (!(variables instanceof HashSet)) {
            keys = new HashSet<>(variables);
        } else {
            keys = variables;
        }
        String[] arrays = StringUtils.split("\\:", arrayName);
        if (arrays.length != 2) {
            throw new FormatException(arrayName + "数组格式不正确。");
        }
        ArrayPosition start = ArrayPosition.getArrayPosition(arrays[0]);
        ArrayPosition end = ArrayPosition.getArrayPosition(arrays[1]);
        for (int c = start.getColNumber(); c <= end.getColNumber(); c++) {
            String colName = ArrayPosition.getArrayColName(c);
            for (int r = start.getRowNumber(); r <= end.getRowNumber(); r++) {
                String name = colName + (new Integer(r)).toString();
                if (keys.contains(name)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 表达式中是否包含特定的变量名称
     *
     * @param expression   表达式
     * @param variableName 变更名称
     * @return
     */
    public static boolean expressionExistVariable(String expression, String variableName) {
        if (StringUtils.isNullOrBlank(expression) || StringUtils.isNullOrBlank(variableName)) {
            return false;
        }
        HashSet<String> keys = new HashSet<>();
        keys.add(variableName);
        return expressionExistVariable(expression, keys);
    }

    /**
     * 表达式中是否包含特定的变量名称
     *
     * @param evaluator    解析器
     * @param expression   表达式
     * @param variableName 变更名称
     * @return
     */
    public static boolean expressionExistVariable(DefaultEvaluatorSession evaluator, String expression, String variableName) {
        if (StringUtils.isNullOrBlank(expression) || StringUtils.isNullOrBlank(variableName)) {
            return false;
        }
        HashSet<String> keys = new HashSet<>();
        keys.add(variableName);
        return expressionExistVariable(evaluator, expression, keys);
    }

    /**
     * 表达式中是否包含特定的变量名称
     *
     * @param expression 表达式
     * @param variables  变量集合
     * @return
     */
    public static boolean expressionExistVariable(String expression, Collection<String> variables) {
        return expressionExistVariable(nodeCompiled(expression, null), variables);
    }

    /**
     * 表达式中是否包含特定的变量名称
     *
     * @param evaluator  解析器
     * @param expression 表达式
     * @param variables  变量集合
     * @return
     */
    public static boolean expressionExistVariable(DefaultEvaluatorSession evaluator, String expression, Collection<String> variables) {
        if (evaluator == null) {
            throw new ArgumentNullException("evaluator");
        }
        return expressionExistVariable(evaluator.compiled(expression), variables);
    }



    /**
     * 表达式中是否包含特定的变量名称
     *
     * @param node
     * @param variables
     * @return
     */
    private static boolean expressionExistVariable(AbstractTreeNode node, Collection<String> variables) {
        if (variables.size() == 0) {
            return false;
        }
        Collection<String> keys;
        if (!(variables instanceof HashSet)) {
            keys = new HashSet<>(variables);
        } else {
            keys = variables;
        }
        return existVariable(node, keys);
    }

    private static boolean existVariable(AbstractTreeNode node, Collection<String> items) {
        switch (node.getType()) {
            case VARIABLE:
                if (items.contains(((VariableNode) node).getName())) {
                    return true;
                }
                break;
            case ARRAY:
                ArrayNode array = (ArrayNode) node;
                if (existVariables(array.getStartPosition(), array.getEndPosition(), items)) {
                    return true;
                }
                break;
            case CONDITION_GROUP:
                if (existVariable(((ConditionGroupNode) node).getContent(), items)) {
                    return true;
                }
                break;
            case CONDITIONAL:
                if (existVariable(((ConditionalNode) node).getTest(), items)) {
                    return true;
                }
                if (existVariable(((ConditionalNode) node).getIfTrue(), items)) {
                    return true;
                }
                if (existVariable(((ConditionalNode) node).getIfFalse(), items)) {
                    return true;
                }
                break;
            case CALL:
                FunctionNode fun = (FunctionNode) node;
                if (fun.getArguments().size() > 0) {
                    for (AbstractTreeNode tree : fun.getArguments()) {
                        if (existVariable(tree, items)) {
                            return true;
                        }
                    }
                }
                break;
            default:
                if (node instanceof BinaryNode) {
                    if (existVariable(((BinaryNode) node).getLeft(), items)) {
                        return true;
                    }
                    if (existVariable(((BinaryNode) node).getRight(), items)) {
                        return true;
                    }
                } else if (node instanceof UnaryNode) {
                    if (existVariable(((UnaryNode) node).getOperand(), items)) {
                        return true;
                    }
                }
                break;
        }
        return false;
    }

    /**
     * @param start
     * @param end
     * @param items
     * @return
     */
    private static boolean existVariables(ArrayPosition start, ArrayPosition end, Collection<String> items) {
        for (int c = start.getColNumber(); c <= end.getColNumber(); c++) {
            String colName = ArrayPosition.getArrayColName(c);
            for (int r = start.getRowNumber(); r <= end.getRowNumber(); r++) {
                if (items.contains(colName + (new Integer(r)).toString())) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 输入一个数组名称,返回所有变更集合
     *
     * @param arrayName 数组名称
     * @return 返回变量集合
     */
    public static ArrayList<String> arrayVariables(String arrayName) {
        if (StringUtils.isNullOrBlank(arrayName)) {
            throw new ArgumentNullException("ArrayName");
        }
        String[] arrays = StringUtils.split("\\:", arrayName);

        if (arrays.length != 2) {
            throw new FormatException(arrayName + "数组格式不正确。");
        }
        return arrayVariables(ArrayPosition.getArrayPosition(arrays[0]), ArrayPosition.getArrayPosition(arrays[1]));
    }

    /**
     * 输入一个数组名称,返回所有变更集合
     *
     * @param start
     * @param end
     * @return
     */
    private static ArrayList<String> arrayVariables(ArrayPosition start, ArrayPosition end) {
        ArrayList<String> vs = new ArrayList<>();
        for (int c = start.getColNumber(); c <= end.getColNumber(); c++) {
            String colName = ArrayPosition.getArrayColName(c);
            for (int r = start.getRowNumber(); r <= end.getRowNumber(); r++) {
                vs.add(colName + (new Integer(r)).toString());
            }
        }
        return vs;
    }

    /**
     * 输入一个表达式,返回所有变量集合
     *
     * @param expression 表达式
     * @return
     */
    public static ArrayList<String> expressionVariables(String expression) {
        return nodeVariables(nodeCompiled(expression, null));
    }

    /**
     * 编译并返回节点
     *
     * @param tokens 标记集合
     * @return
     */
    public static AbstractTreeNode nodeCompiled(Collection<Token> tokens) {
        Utils.checkNotNull("tokens", tokens);
        if (tokens.size() == 0) {
            return AbstractTreeNode.createNullNode();
        }
        for (Token token : tokens) {
            if (token == null) {
                throw new ArgumentNullException("tokens", "tokens 存在 null 的元素。");
            }
        }
        DoubleLinkedList<Token> link;
        if (tokens instanceof DoubleLinkedList) {
            link = (DoubleLinkedList<Token>) tokens;
        } else {
            link = new DoubleLinkedList<>(tokens);
        }
        NodeParse parse = new NodeParse(link, null);
        return parse.compiled();
    }

    /**
     * 输入一个表达式,返回所有变量集合
     *
     * @param evaluator  解析器
     * @param expression 表达式
     * @return
     */
    public static ArrayList<String> expressionVariables(DefaultEvaluatorSession evaluator, String expression) {
        if (evaluator == null) {
            throw new ArgumentNullException("evaluator");
        }
        return nodeVariables(evaluator.compiled(expression));
    }

    /**
     * 输入一个节点,返回所有变更集合
     *
     * @param node 节点
     * @return
     */
    public static ArrayList<String> nodeVariables(AbstractTreeNode node) {
        if (node == null) {
            throw new ArgumentNullException("node");
        }
        ArrayList<String> vs = new ArrayList<>();
        loadVariables(node, vs);
        return vs;
    }

    private static void loadVariables(AbstractTreeNode node, ArrayList<String> items) {
        switch (node.getType()) {
            case VARIABLE:
                items.add(((VariableNode) node).getName());
                break;
            case ARRAY:
                ArrayNode array = (ArrayNode) node;
                items.addAll(arrayVariables(array.getStartPosition(), array.getEndPosition()));
                break;
            case CONDITION_GROUP:
                loadVariables(((ConditionGroupNode) node).getContent(), items);
                break;
            case CONDITIONAL:
                loadVariables(((ConditionalNode) node).getTest(), items);
                loadVariables(((ConditionalNode) node).getIfTrue(), items);
                loadVariables(((ConditionalNode) node).getIfFalse(), items);
                break;
            case CALL:
                FunctionNode fun = (FunctionNode) node;
                if (fun.getArguments().size() > 0) {
                    for (AbstractTreeNode tree : fun.getArguments()) {
                        loadVariables(tree, items);
                    }
                }
                break;
            default:
                if (node instanceof BinaryNode) {
                    loadVariables(((BinaryNode) node).getLeft(), items);
                    loadVariables(((BinaryNode) node).getRight(), items);
                } else if (node instanceof UnaryNode) {
                    loadVariables(((UnaryNode) node).getOperand(), items);
                }
                break;
        }
    }
}