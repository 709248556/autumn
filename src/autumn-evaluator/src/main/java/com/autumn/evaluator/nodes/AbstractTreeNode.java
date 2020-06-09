package com.autumn.evaluator.nodes;

import com.autumn.evaluator.*;
import com.autumn.evaluator.exception.ParserDelimiterException;
import com.autumn.evaluator.exception.ParserException;
import com.autumn.evaluator.visit.VisitExpression;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * 树节点
 */
public abstract class AbstractTreeNode implements Cloneable, Serializable {

    /**
     * 序列号
     */
    private static final long serialVersionUID = -628389192437391435L;

    /**
     * 实例化 TreeNode 类新实例
     */
    protected AbstractTreeNode() {
        this(null);
    }

    /**
     * 实例化 TreeNode 类新实例
     *
     * @param token 标记
     */
    protected AbstractTreeNode(Token token) {
        this.setToken(token);
    }

    /**
     * 获取标记
     */
    private Token token;

    public final Token getToken() {
        return token;
    }

    private void setToken(Token value) {
        token = value;
    }

    /**
     * 获取首个标记
     *
     * @return
     */
    public abstract Token getFirstToken();

    /**
     * 获取最后标记
     *
     * @return
     */
    public abstract Token getLastToken();

    /**
     * 获取节点类型
     *
     * @return
     */
    public abstract Enums.NodeType getType();

    /**
     * 克隆节点
     *
     * @return
     */
    public abstract AbstractTreeNode dupNode();

    /**
     * 获取是否这空标记
     * @return
     */
    public final boolean getIsNil() {
        return this.getToken() == null;
    }

    public AbstractTreeNode parent;

    /**
     * 获取父级
     * @return
     */
    public final AbstractTreeNode getParent() {
        return this.parent;
    }

    /**
     * 克隆
     *
     * @return
     */
    @Override
    protected Object clone() throws CloneNotSupportedException {
        return this.dupNode();
    }

    /**
     * 获取级别,0表示无父级
     *
     * @return
     */
    public final int level() {
        return this.level(this);
    }

    private int level(AbstractTreeNode node) {
        int level = 0;
        if (node != null) {
            if (node.getParent() != null) {
                level++;
                level += this.level(node.getParent());
            }
        }
        return level;
    }

    /**
     * 解析
     *
     * @param parseContext 上下文
     * @return
     */
    public abstract Variant parse(ParseContext parseContext);

    /**
     * 生成
     *
     * @param visit
     * @param context
     */
    public final void build(VisitExpression visit, ParseContext context) {
        visit.visit(this, context);
    }

    /**
     * 输出节点
     *
     * @return
     */
    @Override
    public abstract String toString();

    /**
     * @param delimiter
     * @param desc
     * @return
     */
    protected final ParserDelimiterException parserDelimiterException(String delimiter, String desc) {
        if (this.getIsNil()) {
            return new ParserDelimiterException(-1, delimiter.length(), delimiter, desc);
        } else {
            return new ParserDelimiterException(this.getToken().getStartIndex(), this.getToken().getLength(), delimiter,
                    desc);
        }
    }

    /**
     * @return
     */
    protected final ParserException parserException() {
        if (this.getIsNil()) {
            return new ParserException(-1, 0, "解析发生了异常，可能过多、缺少运算符或不正确的表达式。");
        } else {
            return new ParserException(this.getToken().getStartIndex(), this.getToken().getLength(), "解析发生了异常，可能过多、缺少运算符或不正确的表达式。");
        }
    }

    /**
     * 检查父级
     *
     * @param node 节点
     */
    public static void checkParent(AbstractTreeNode node) {
        if (node != null && node.getParent() != null) {
            throw new RuntimeException(String.format("%s 已属于其他节点的子节点。", node.toString()));
        }
    }

    /**
     * 创建数组
     *
     * @param token 标记
     * @return
     */
    public static ArrayNode array(Token token) {
        ArrayPosition[] arrays = (ArrayPosition[]) token.getValue();
        return new ArrayNode(token, arrays[0], arrays[1]);
    }

    /**
     * 创建数组
     *
     * @param start 开始
     * @param end   结束
     * @return
     */
    public static ArrayNode array(String start, String end) {
        ArrayPosition startPosition = ArrayPosition.getArrayPosition(Utils.checkNotNullOrEmpty("start", start));
        ArrayPosition endPosition = ArrayPosition.getArrayPosition(Utils.checkNotNullOrEmpty("end", end));
        if (startPosition.getColNumber() > endPosition.getColNumber()) {
            throw new ParserDelimiterException(-1, start.length(), ":", String.format("组数 %s 的下标标识不能小于上标标识。", start));
        }
        if (startPosition.getRowNumber() > endPosition.getRowNumber()) {
            throw new ParserDelimiterException(-1, start.length(), ":", String.format("组数 %s 的下标位置不能小于上标位置。", start));
        }
        return new ArrayNode(startPosition, endPosition);
    }

    /**
     * 创建数组
     *
     * @param start 开始
     * @param end   结束
     * @return
     */
    public static ArrayNode array(ArrayPosition start, ArrayPosition end) {
        start = Utils.checkNotNull("start", start);
        end = Utils.checkNotNull("end", end);
        if (start.getColNumber() > end.getColNumber()) {
            throw new ParserDelimiterException(-1, 0, ":", String.format("组数 %s 的下标标识不能小于上标标识。", start));
        }
        if (start.getRowNumber() > end.getRowNumber()) {
            throw new ParserDelimiterException(-1, 0, ":", String.format("组数 %s 的下标位置不能小于上标位置。", start));
        }
        return new ArrayNode(start, end);
    }

    /**
     * 加法运算，如 a + b，针对数值操作数
     *
     * @param left  左
     * @param right 右
     * @return
     */
    public static BinaryNode add(AbstractTreeNode left, AbstractTreeNode right) {
        Utils.checkNotNull("left", left);
        Utils.checkNotNull("right", right);
        checkParent(left);
        checkParent(right);
        return new BinaryNode(Enums.NodeType.ADD, left, right);
    }

    /**
     * 逻辑 AND 运算，如 a > 0 and b > 0
     *
     * @param left  左
     * @param right 右
     * @return
     */
    public static BinaryNode and(AbstractTreeNode left, AbstractTreeNode right) {
        Utils.checkNotNull("left", left);
        Utils.checkNotNull("right", right);
        checkParent(left);
        checkParent(right);
        return new BinaryNode(Enums.NodeType.AND, left, right);
    }

    /**
     * 字符连接符，如 a &amp; b
     *
     * @param left  左
     * @param right 右
     * @return
     */
    public static BinaryNode concat(AbstractTreeNode left, AbstractTreeNode right) {
        Utils.checkNotNull("left", left);
        Utils.checkNotNull("right", right);
        checkParent(left);
        checkParent(right);
        return new BinaryNode(Enums.NodeType.CONCAT, left, right);
    }

    /**
     * 除法运算，如 (a / b)，针对数值操作数。
     *
     * @param left  左
     * @param right 右
     * @return
     */
    public static BinaryNode divide(AbstractTreeNode left, AbstractTreeNode right) {
        Utils.checkNotNull("left", left);
        Utils.checkNotNull("right", right);
        checkParent(left);
        checkParent(right);
        return new BinaryNode(Enums.NodeType.DIVIDE, left, right);
    }

    /**
     * 表示相等比较的节点，如 a = b 。
     *
     * @param left  左
     * @param right 右
     * @return
     */
    public static BinaryNode equal(AbstractTreeNode left, AbstractTreeNode right) {
        Utils.checkNotNull("left", left);
        Utils.checkNotNull("right", right);
        checkParent(left);
        checkParent(right);
        return new BinaryNode(Enums.NodeType.EQUAL, left, right);
    }

    /**
     * "大于"比较，如 (a &gt; b)。
     *
     * @param left  左
     * @param right 右
     * @return
     */
    public static BinaryNode greaterThan(AbstractTreeNode left, AbstractTreeNode right) {
        Utils.checkNotNull("left", left);
        Utils.checkNotNull("right", right);
        checkParent(left);
        checkParent(right);
        return new BinaryNode(Enums.NodeType.GREATER_THAN, left, right);
    }

    /**
     * "大于或等于"比较，如 (a &gt;= b)。
     *
     * @param left  左
     * @param right 右
     * @return
     */
    public static BinaryNode greaterThanOrEqual(AbstractTreeNode left, AbstractTreeNode right) {
        Utils.checkNotNull("left", left);
        Utils.checkNotNull("right", right);
        checkParent(left);
        checkParent(right);
        return new BinaryNode(Enums.NodeType.GREATER_THAN_OR_EQUAL, left, right);
    }

    /**
     * "小于"比较，如 (a &lt; b)。
     *
     * @param left  左
     * @param right 右
     * @return
     */
    public static BinaryNode lessThan(AbstractTreeNode left, AbstractTreeNode right) {
        Utils.checkNotNull("left", left);
        Utils.checkNotNull("right", right);
        checkParent(left);
        checkParent(right);
        return new BinaryNode(Enums.NodeType.LESS_THAN, left, right);
    }

    /**
     * "小于或等于"比较，如 (a &lt;= b)。
     *
     * @param left  左
     * @param right 右
     * @return
     */
    public static BinaryNode lessThanOrEqual(AbstractTreeNode left, AbstractTreeNode right) {
        Utils.checkNotNull("left", left);
        Utils.checkNotNull("right", right);
        checkParent(left);
        checkParent(right);
        return new BinaryNode(Enums.NodeType.LESS_THAN_OR_EQUAL, left, right);
    }

    /**
     * 算术余数运算，如 a % b。
     *
     * @param left  左
     * @param right 右
     * @return
     */
    public static BinaryNode modulo(AbstractTreeNode left, AbstractTreeNode right) {
        Utils.checkNotNull("left", left);
        Utils.checkNotNull("right", right);
        checkParent(left);
        checkParent(right);
        return new BinaryNode(Enums.NodeType.MODULO, left, right);
    }

    /**
     * 乘法运算，如 (a * b) 。
     *
     * @param left  左
     * @param right 右
     * @return
     */
    public static BinaryNode multiply(AbstractTreeNode left, AbstractTreeNode right) {
        Utils.checkNotNull("left", left);
        Utils.checkNotNull("right", right);
        checkParent(left);
        checkParent(right);
        return new BinaryNode(Enums.NodeType.MULTIPLY, left, right);
    }

    /**
     * 不相等比较，如 a != b 或 a &lt;&gt; 6 。
     *
     * @param left  左
     * @param right 右
     * @return
     */
    public static BinaryNode notEqual(AbstractTreeNode left, AbstractTreeNode right) {
        Utils.checkNotNull("left", left);
        Utils.checkNotNull("right", right);
        checkParent(left);
        checkParent(right);
        return new BinaryNode(Enums.NodeType.NOT_EQUAL, left, right);
    }

    /**
     * 逻辑 OR 运算，如 a &gt; 0 or b &gt; 0 。
     *
     * @param left  左
     * @param right 右
     * @return
     */
    public static BinaryNode or(AbstractTreeNode left, AbstractTreeNode right) {
        Utils.checkNotNull("left", left);
        Utils.checkNotNull("right", right);
        checkParent(left);
        checkParent(right);
        return new BinaryNode(Enums.NodeType.OR, left, right);
    }

    /**
     * 数字进行幂运算的数学运算，如 a ^ b 。
     *
     * @param left  左
     * @param right 右
     * @return
     */
    public static BinaryNode power(AbstractTreeNode left, AbstractTreeNode right) {
        Utils.checkNotNull("left", left);
        Utils.checkNotNull("right", right);
        checkParent(left);
        checkParent(right);
        return new BinaryNode(Enums.NodeType.POWER, left, right);
    }

    /**
     * 减法运算，如 (a - b) 。
     *
     * @param left  左
     * @param right 右
     * @return
     */
    public static BinaryNode subtract(AbstractTreeNode left, AbstractTreeNode right) {
        Utils.checkNotNull("left", left);
        Utils.checkNotNull("right", right);
        checkParent(left);
        checkParent(right);
        return new BinaryNode(Enums.NodeType.SUBTRACT, left, right);
    }

    /**
     * 创建二元运算
     *
     * @param token
     * @param nodeType 节点类型
     * @param left
     * @param right
     * @return
     */
    public static BinaryNode binary(Token token, Enums.NodeType nodeType, AbstractTreeNode left,
                                    AbstractTreeNode right) {
        return new BinaryNode(token, nodeType, Utils.checkNotNull("left", left), Utils.checkNotNull("right", right));
    }

    /**
     * 创建常量
     *
     * @param token
     * @return
     */
    public static ConstantNode constant(Token token) {
        return new ConstantNode(token);
    }

    /**
     * 创建常量
     *
     * @param value
     * @return
     */
    public static ConstantNode constant(Variant value) {
        return new ConstantNode(value);
    }

    /**
     * 调用函数
     *
     * @param token     标记
     * @param arguments 参数集合
     * @return
     */
    public static FunctionNode call(Token token, AbstractTreeNode[] arguments) {
        return new FunctionNode(token, arguments);
    }

    /**
     * 调用函数
     *
     * @param name      函数名称
     * @param arguments 参数集合
     * @return
     */
    public static FunctionNode call(String name, AbstractTreeNode... arguments) {
        Utils.checkNotNull("arguments", arguments);
        Set<AbstractTreeNode> set = new HashSet<>(Arrays.asList(arguments));
        if (set.size() != arguments.length) {
            throw new RuntimeException("同一子级节点不能重复添加。");
        }
        for (AbstractTreeNode item : arguments) {
            Utils.checkNotNull("item", item);
            checkParent(item);
        }
        return new FunctionNode(Utils.checkNotNullOrEmpty("name", name).toUpperCase(), arguments);
    }

    /**
     * 创建空值节点
     *
     * @param token
     * @return
     */
    public static NullNode createNullNode(Token token) {
        return new NullNode(token);
    }

    /**
     * 创建空值节点
     *
     * @return
     */
    public static NullNode createNullNode() {
        return new NullNode();
    }

    /**
     * 创建括号
     *
     * @param token
     * @param content
     * @return
     */
    public static ConditionGroupNode conditionGroup(Token token, AbstractTreeNode content) {
        return new ConditionGroupNode(token, content);
    }

    /**
     * 条件组
     *
     * @param content
     * @return
     */
    public static ConditionGroupNode conditionGroup(AbstractTreeNode content) {
        checkParent(content);
        return new ConditionGroupNode(Utils.checkNotNull("content", content));
    }

    /**
     * 创建条件运算符
     *
     * @param token
     * @param test
     * @param ifTrue
     * @param ifFalse
     * @return
     */
    public static ConditionalNode conditional(Token token, AbstractTreeNode test, AbstractTreeNode ifTrue,
                                              AbstractTreeNode ifFalse) {
        return new ConditionalNode(token, test, ifTrue, ifFalse);
    }

    /**
     * 创建条件运算符
     *
     * @param test
     * @param ifTrue
     * @param ifFalse
     * @return
     */
    public static ConditionalNode conditional(AbstractTreeNode test, AbstractTreeNode ifTrue,
                                              AbstractTreeNode ifFalse) {
        Utils.checkNotNull("test", test);
        Utils.checkNotNull("ifTrue", ifTrue);
        Utils.checkNotNull("ifFalse", ifFalse);
        checkParent(test);
        checkParent(ifTrue);
        checkParent(ifFalse);
        return new ConditionalNode(test, ifTrue, ifFalse);
    }

    /**
     * 创建一元运算符
     *
     * @param token
     * @param nodeType 节点类型
     * @param operand
     * @return
     */
    public static UnaryNode unary(Token token, Enums.NodeType nodeType, AbstractTreeNode operand) {
        return new UnaryNode(token, nodeType, operand);
    }

    /**
     * 一元算术求反运算，如 (-a) 。
     *
     * @param operand
     * @return
     */
    public static UnaryNode negate(AbstractTreeNode operand) {
        Utils.checkNotNull("operand", operand);
        checkParent(operand);
        return new UnaryNode(Enums.NodeType.NEGATE, operand);
    }

    /**
     * 一元加法运算，如 (+a) 。
     *
     * @param operand
     * @return
     */
    public static UnaryNode unaryPlus(AbstractTreeNode operand) {
        Utils.checkNotNull("operand", operand);
        checkParent(operand);
        return new UnaryNode(Enums.NodeType.UNARY_PLUS, operand);
    }

    /**
     * 一元逻辑求反运算，如 !a 或 not a 。
     *
     * @param operand
     * @return
     */
    public static UnaryNode not(AbstractTreeNode operand) {
        Utils.checkNotNull("operand", operand);
        checkParent(operand);
        return new UnaryNode(Enums.NodeType.NOT, operand);
    }

    /**
     * 一元百分数运算，如 10%。
     *
     * @param operand
     * @return
     */
    public static UnaryNode percent(AbstractTreeNode operand) {
        Utils.checkNotNull("operand", operand);
        checkParent(operand);
        return new UnaryNode(Enums.NodeType.PERCENT, operand);
    }

    /**
     * 创建变量
     *
     * @param token 标记
     * @return
     */
    public static VariableNode variable(Token token) {
        return new VariableNode(token);
    }

    /**
     * 创建变量
     *
     * @param name 名称
     * @return
     */
    public static VariableNode variable(String name) {
        return new VariableNode(Utils.checkNotNullOrEmpty("name", name));
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}