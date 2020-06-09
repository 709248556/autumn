package com.autumn.evaluator;

//

// * 提供编译的解析，并支持对已编译的表达式缓存，即运算解析
//

import com.autumn.evaluator.nodes.AbstractTreeNode;
import com.autumn.exception.ExceptionUtils;
import com.autumn.util.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 默认解析器
 */
public class DefaultEvaluatorSession implements EvaluatorSession {

    /**
     * 缓存集合
     */
    private final Map<String, AbstractTreeNode> caches;

    /**
     * 实例化 Evaluator 类新实例
     */
    public DefaultEvaluatorSession() {
        this.caches = new ConcurrentHashMap<>(16);
    }

    /**
     * 创建文法解析器
     *
     * @param expression 表达式
     * @return
     */
    protected LexerParse createLexerParse(String expression) {
        return new ValueLexerParse(expression);
    }

    /**
     * 解析并返回结果
     *
     * @param expression 表达式
     * @param context    上下文接口,可传入 null
     * @return 返回 Variant 对象
     */
    @Override
    public Variant parse(String expression, Context context) {
        DefaultParseContext parseContext = new DefaultParseContext(context);
        return this.compiled(expression).parse(parseContext);
    }

    /**
     * 编译并返回节点
     *
     * @param expression 表达式
     * @return
     */
    @Override
    public AbstractTreeNode compiled(String expression) {
        if (StringUtils.isNullOrBlank(expression)) {
            return AbstractTreeNode.createNullNode();
        }
        final String key = expression.trim();
        return this.caches.computeIfAbsent(key, k -> {
            LexerParse parse = this.createLexerParse(key);
            return parse.compiled();
        });
    }

    /**
     * 读取标记集合
     *
     * @param expression 表达式
     * @return
     */
    @Override
    public List<Token> readTokens(String expression) {
        Utils.checkNotNullOrEmpty("expression", expression);
        LexerParse lexer = this.createLexerParse(expression);
        return lexer.readTokens();
    }

    /**
     * 清除缓存
     */
    public void clearCache() {
        this.caches.clear();
    }

    /**
     * 移除缓存
     *
     * @param expression 表达式
     * @return
     */
    public void removeCache(String expression) {
        ExceptionUtils.checkNotNullOrBlank(expression, "expression");
        this.caches.remove(expression.trim());
    }
}