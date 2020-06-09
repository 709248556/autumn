package com.autumn.evaluator;

//
// * 根据表达树生成的双向链表，并对双向链表进行解析返回解析结果
// 

import com.autumn.evaluator.Enums.TokenType;
import com.autumn.evaluator.exception.ParserDelimiterException;
import com.autumn.evaluator.exception.ParserException;
import com.autumn.evaluator.nodes.*;
import com.autumn.util.DoubleLinkedList;

import java.util.ArrayList;
import java.util.Stack;

import static com.autumn.evaluator.Enums.NodeType.PERCENT;

/**
 * 节点解析
 */
public class NodeParse extends AbstractParse implements ParseContext {

    private Context context;

    /**
     * 获取上下文
     */
    @Override
    public final Context getContext() {
        return this.context;
    }

    private DoubleLinkedList<Token> link;
    private int startIndex;
    private int length;

    /**
     * 实例化 Evaluator 类
     *
     * @param tokens  标记集合
     * @param context 上下文
     */
    public NodeParse(DoubleLinkedList<Token> tokens, Context context) {
        super();
        this.startIndex = 0;
        this.length = 1;
        this.link = tokens;
        this.context = context;
    }

    /**
     * 当前节点
     */
    private DoubleLinkedList.Node<Token> currentNode;

    /**
     * 移动下一个节点
     *
     * @return
     */
    private Token moveNext() {
        if (this.currentNode != null) {
            this.currentNode = this.currentNode.getNext();
            if (this.currentNode != null) {
                this.startIndex = this.currentNode.getItem().getStartIndex();
                this.length = this.currentNode.getItem().getLength();
            }
            return this.currentNode.getItem();
        } else {
            return null;
        }
    }

    /**
     * 是否逗号
     *
     * @param token 标记
     * @return
     */
    private boolean isComma(Token token) {
        return token.getType() == TokenType.DELIMITER && ",".equals(token.getText());
    }

    /**
     * 是否是左括号
     *
     * @param node 节点
     * @return
     */
    @SuppressWarnings("unused")
    private boolean isLeftBrackets(DoubleLinkedList.Node<Token> node) {
        return node != null && node.getItem().getType() == TokenType.DELIMITER && "(".equals(node.getItem().getText());
    }

    /**
     * 是否是右括号
     *
     * @param node
     * @return
     */
    private boolean isRightBrackets(DoubleLinkedList.Node<Token> node) {
        return node != null && node.getItem().getType() == TokenType.DELIMITER && ")".equals(node.getItem().getText());
    }

    /**
     * 是否是二元表达式
     *
     * @param node 节点
     * @return
     */
    private boolean isBinary(DoubleLinkedList.Node<Token> node) {
        return node != null && (node.getItem().getType() == TokenType.DELIMITER)
                && ValueLexerParse.BINARY_OPERATORS.containsKey(node.getItem().getText());
    }

    /**
     * 是否是三元表达式
     *
     * @param node 节点
     * @return
     */
    private boolean isThree(DoubleLinkedList.Node<Token> node) {
        return node != null && (node.getItem().getType() == TokenType.DELIMITER)
                && "?".equals(node.getItem().getText());
    }

    /**
     * 是否存在下一个节点
     *
     * @return
     */
    protected final boolean isExistNext() {
        return this.currentNode != null && this.currentNode.getNext() != null;
    }

    /**
     * 初始化
     */
    private void initialize() {
        this.currentNode = this.link.getFirstNode();
        this.startIndex = 0;
        this.length = 1;
    }

    /**
     * 编译
     *
     * @return
     */
    public final AbstractTreeNode compiled() {
        this.initialize();
        AbstractTreeNode node = this.createNode(true);
        if (this.isExistNext()) {
            throw this.parserException();
        }
        return node;
    }

    /**
     * 运算
     *
     * @return
     */
    public final Variant computing() {
        return this.compiled().parse(this);
    }

    /**
     * 创建节点
     *
     * @param createBinary 创建二元
     * @return
     */
    private AbstractTreeNode createNode(boolean createBinary) {
        AbstractTreeNode node = null;
        Token token = this.currentNode.getItem();
        this.startIndex = token.getStartIndex();
        this.length = token.getLength();
        switch (token.getType()) {
            case INTEGER:
            case DECIMAL:
            case DOUBLE:
            case STRING:
            case DATETIME:
            case UUID:
            case STATIC_ARRAY:
                node = AbstractTreeNode.constant(token);
                break;
            case VARIABLE:
                node = AbstractTreeNode.variable(token);
                break;
            case ARRAY:
                node = AbstractTreeNode.array(token);
                break;
            case FUNCTION:
                node = this.createFunction();
                break;
            case DELIMITER:
                if ("(".equals(token.getText())) {
                    node = this.createParenthesis();
                } else {
                    Enums.NodeType nodeType;
                    nodeType = ValueLexerParse.UNARY_OPERATORS.get(token.getText());
                    if (nodeType != null) {
                        switch (nodeType) {
                            case NEGATE:
                            case UNARY_PLUS:
                            case NOT:
                                Token unaryToken = token;
                                this.moveNext();
                                if (this.currentNode == null) {
                                    throw this.parserException();
                                }
                                token = this.currentNode.getItem();
                                this.startIndex = token.getStartIndex();
                                this.length = token.getLength();
                                node = AbstractTreeNode.unary(unaryToken, nodeType, this.createNode(false));
                                break;
                            default:
                                break;
                        }
                    }
                }
                break;
            default:
                break;
        }
        if (node == null) {
            throw this.parserException();
        }
        if (this.isExistNext()) {
            if (this.currentNode.getNext().getItem().getType() == TokenType.DELIMITER
                    && "%".equals(this.currentNode.getNext().getItem().getText())) {
                if (this.currentNode.getNext().getNext() == null
                        || this.currentNode.getNext().getNext().getItem().getType() == TokenType.DELIMITER) {
                    this.moveNext();
                    token = this.currentNode.getItem();
                    this.startIndex = token.getStartIndex();
                    this.length = token.getLength();
                    node = AbstractTreeNode.unary(token, PERCENT, node);
                }
            }
        }
        if (createBinary && this.isExistNext() && this.isBinary(this.currentNode.getNext())) {
            if (this.isBinary(this.currentNode.getNext())) {
                this.moveNext();
                node = this.createBinary(node);
                if (this.isExistNext() && this.isThree(this.currentNode.getNext())) {
                    this.moveNext();
                    node = this.createThreeUnary(node);
                }
            } else {
                if (!(this.currentNode.getNext().getItem().getType() == TokenType.DELIMITER
                        && ":".equals(this.currentNode.getNext().getItem().getText()))) // 三元分隔符
                {
                    throw this.parserException();
                }
            }
        }
        return node;
    }

    /**
     * 创建函数
     *
     * @return
     */
    private FunctionNode createFunction() {
        Token funToken = this.currentNode.getItem();
        ArrayList<AbstractTreeNode> nodes = new ArrayList<>();
        // skip (
        Token temp = this.moveNext();
        if (temp == null) {
            throw new ParserDelimiterException(this.startIndex, this.length, ")",
                    String.format("无法解析的表达式,缺少运算符 %s 。", ")"));
        }
        boolean comma = false;
        while (this.moveNext() != null) {
            Token token = this.currentNode.getItem();
            this.startIndex = token.getStartIndex();
            this.length = token.getLength();
            if (this.isRightBrackets(this.currentNode)) {
                break;
            } else {
                if (!this.isComma(token)) {
                    nodes.add(this.createNode(true));
                    comma = true;
                } else {
                    if (!comma) // 连续出现逗号
                    {
                        nodes.add(AbstractTreeNode.createNullNode(token));
                    }
                    comma = false;
                }
            }
        }
        if (!this.isRightBrackets(this.currentNode)) {
            throw new ParserDelimiterException(this.startIndex, this.length, ")",
                    String.format("无法解析的表达式,缺少运算符 %s 。", ")"));
        }
        return AbstractTreeNode.call(funToken, nodes.toArray(new AbstractTreeNode[0]));
    }

    /**
     * 创建括号
     *
     * @return
     */
    private ConditionGroupNode createParenthesis() {
        Token token = this.currentNode.getItem();
        this.moveNext();
        if (this.currentNode == null) {
            throw new ParserDelimiterException(this.startIndex, this.length, ")",
                    String.format("无法解析的表达式,缺少运算符 %s 。", ")"));
        }
        ConditionGroupNode parenthesis = AbstractTreeNode.conditionGroup(token, this.createNode(true));
        this.moveNext();
        if (!this.isRightBrackets(this.currentNode)) {
            throw new ParserDelimiterException(this.startIndex, this.length, ")",
                    String.format("无法解析的表达式,缺少运算符 %s 。", ")"));
        }
        return parenthesis;
    }

    /**
     * 创建二元
     *
     * @param leftNode 左节点
     * @return
     */
    private BinaryNode createBinary(AbstractTreeNode leftNode) {
        if (this.isExistNext()) {
            // 中缀转后缀
            // 级别从大到小的优生顺序,越小越优先
            // 详查阅逆波兰表达式
            DoubleLinkedList<Object> nodes = new DoubleLinkedList<>();
            Stack<Token> tokens = new Stack<>();
            nodes.offer(leftNode);
            Token token;
            while (this.isBinary(this.currentNode)) {
                token = this.currentNode.getItem();
                this.startIndex = token.getStartIndex();
                this.length = token.getLength();
                this.moveNext();
                if (this.currentNode == null) {
                    throw this.parserException();
                }
                AbstractTreeNode nextNode = this.createNode(false);
                if (nextNode == null) {
                    throw this.parserException();
                }
                int nextLevel = ValueLexerParse.OPERATOR_LEVELS.get(token.getText());
                if (tokens.size() > 0) {
                    // 若当前运算符级别小于最后一个运算符级别，则优先，压入运算符栈
                    if (nextLevel < ValueLexerParse.OPERATOR_LEVELS.get(tokens.peek().getText())) {
                        tokens.push(token);
                    } else {
                        while (tokens.size() > 0
                                && nextLevel >= ValueLexerParse.OPERATOR_LEVELS.get(tokens.peek().getText())) {
                            // 若当前运算符级别大于最后一个运算符级别，则取出压入操作符栈，直遇到同级或到栈顶
                            nodes.offer(tokens.pop());
                        }
                        tokens.push(token);
                    }
                } else {
                    tokens.push(token);
                }
                nodes.offer(nextNode);
                if (this.isExistNext() && this.isBinary(this.currentNode.getNext())) {
                    this.moveNext(); // 下一个仍然是运算符则移动
                }
            }
            while (tokens.size() > 0) {
                nodes.offer(tokens.pop());
            }
            if (nodes.size() < 3) {
                throw this.parserException();
            }
            BinaryNode node;
            if (nodes.size() == 3) // 只有一个二元运算符
            {
                Object left = nodes.poll();
                Object right = nodes.poll();
                token = (Token) nodes.poll();
                node = AbstractTreeNode.binary(token, ValueLexerParse.BINARY_OPERATORS.get(token.getText()),
                        (AbstractTreeNode) left, (AbstractTreeNode) right);
                return node;
            }
            Stack<AbstractTreeNode> nodeStack = new Stack<>();
            while (nodes.size() > 0) {
                Object item = nodes.poll();
                if (item instanceof AbstractTreeNode) {
                    nodeStack.push((AbstractTreeNode) item);
                } else {
                    AbstractTreeNode right = nodeStack.pop();
                    AbstractTreeNode left = nodeStack.pop();
                    token = (Token) item;
                    node = AbstractTreeNode.binary(token, ValueLexerParse.BINARY_OPERATORS.get(token.getText()), left,
                            right);
                    nodeStack.push(node);
                }
            }
            node = (BinaryNode) nodeStack.pop();
            return node;
        } else {
            throw this.parserException();
        }
    }

    /**
     * 创建三元
     *
     * @param logical 逻辑节点
     * @return
     */
    private ConditionalNode createThreeUnary(AbstractTreeNode logical) {
        if (this.isExistNext()) {
            Token token = this.currentNode.getItem();
            this.moveNext();
            if (this.currentNode == null) {
                throw this.parserException();
            }
            AbstractTreeNode trueNode = this.createNode(true);
            this.moveNext();
            if (this.currentNode == null) {
                throw this.parserException();
            }
            if (!(this.currentNode.getItem().getType() == TokenType.DELIMITER
                    && ":".equals(this.currentNode.getItem().getText()))) {
                throw this.parserException();
            }
            this.moveNext();
            AbstractTreeNode falseNode = this.createNode(true);
            return AbstractTreeNode.conditional(token, logical, trueNode, falseNode);
        } else {
            throw this.parserException();
        }
    }

    private ParserException parserException() {
        return new ParserException(this.startIndex, this.length, "解析发生了异常，可能过多、缺少运算符或不正确的表达式。");
    }

}