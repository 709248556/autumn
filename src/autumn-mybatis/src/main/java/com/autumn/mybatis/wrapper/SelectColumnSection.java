package com.autumn.mybatis.wrapper;

import com.autumn.mybatis.wrapper.commands.QueryWrapperCommand;
import com.autumn.mybatis.wrapper.expressions.ColumnExpression;
import com.autumn.mybatis.wrapper.expressions.PropertyExpression;
import com.autumn.exception.ExceptionUtils;
import com.autumn.util.StringUtils;

import java.util.function.Function;

/**
 * 查询列段
 *
 * @param <TSourceWrapper> 来源
 * @param <TNameFunc>      名称或函数
 */
public class SelectColumnSection<TSourceWrapper, TNameFunc> implements OfWrapper<TSourceWrapper> {

    private final TSourceWrapper sourceWrapper;
    private final QueryWrapperCommand queryWrapperCommand;
    private final Function<TNameFunc, ColumnExpression> columnExpressionFunction;

    /**
     * 实例化 公共条件
     *
     * @param sourceWrapper            源Wrapper
     * @param queryWrapperCommand      命令
     * @param columnExpressionFunction 名称或列表达式
     */
    SelectColumnSection(TSourceWrapper sourceWrapper, QueryWrapperCommand queryWrapperCommand,
                        Function<TNameFunc, ColumnExpression> columnExpressionFunction) {
        this.columnExpressionFunction = columnExpressionFunction;
        this.queryWrapperCommand = queryWrapperCommand;
        this.sourceWrapper = sourceWrapper;
    }

    /**
     * 添加查询列
     *
     * @param nameFunc 名称或函数
     * @param fun      函数
     * @param alias    别名
     * @return
     */
    private SelectColumnSection<TSourceWrapper, TNameFunc> addSelectColumn(TNameFunc nameFunc, PolymerizationEnum fun, String alias) {
        ExceptionUtils.checkNotNull(nameFunc, "nameFunc");
        ColumnExpression col = this.columnExpressionFunction.apply(nameFunc);
        if (StringUtils.isNullOrBlank(alias) && col instanceof PropertyExpression) {
            PropertyExpression property = (PropertyExpression) col;
            this.queryWrapperCommand.addSelectColumn(col.getColumnName(), fun, property.getColumn().getPropertyName());
        } else {
            this.queryWrapperCommand.addSelectColumn(col.getColumnName(), fun, alias);
        }
        return this;
    }

    /**
     * 查询列
     *
     * @param nameFunc 名称或函数
     * @param alias    别名
     * @return
     */
    public SelectColumnSection<TSourceWrapper, TNameFunc> column(TNameFunc nameFunc, String alias) {
        return this.addSelectColumn(nameFunc, null, alias);
    }


    /**
     * 查询列
     *
     * @param nameFunc 名称或函数
     * @return
     */
    public SelectColumnSection<TSourceWrapper, TNameFunc> column(TNameFunc nameFunc) {
        return this.column(nameFunc, null);
    }

    /**
     * 统计 SELECT COUNT(*)
     *
     * @param nameFunc 名称或函数
     * @param alias
     * @return
     */
    public SelectColumnSection<TSourceWrapper, TNameFunc> count(TNameFunc nameFunc, String alias) {
        return this.addSelectColumn(nameFunc, PolymerizationEnum.COUNT, alias);
    }

    /**
     * 统计 SELECT COUNT(*)
     *
     * @param nameFunc 名称或函数
     * @return
     */
    public SelectColumnSection<TSourceWrapper, TNameFunc> count(TNameFunc nameFunc) {
        return this.count(nameFunc, null);
    }

    /**
     * 平均值 SELECT AVG(field)
     *
     * @param nameFunc 名称或函数
     * @param alias    别名
     * @return
     */
    public SelectColumnSection<TSourceWrapper, TNameFunc> avg(TNameFunc nameFunc, String alias) {
        return this.addSelectColumn(nameFunc, PolymerizationEnum.AVG, alias);
    }

    /**
     * 平均值 SELECT AVG(field)
     *
     * @param nameFunc 名称或函数
     * @return
     */
    public SelectColumnSection<TSourceWrapper, TNameFunc> avg(TNameFunc nameFunc) {
        return this.avg(nameFunc, null);
    }

    /**
     * 最小值 SELECT MIN(field)
     *
     * @param nameFunc 名称或函数
     * @param alias    别名
     * @return
     */
    public SelectColumnSection<TSourceWrapper, TNameFunc> min(TNameFunc nameFunc, String alias) {
        return this.addSelectColumn(nameFunc, PolymerizationEnum.MIN, alias);
    }

    /**
     * 最小值 SELECT MIN(field)
     *
     * @param nameFunc 名称或函数
     * @return
     */
    public SelectColumnSection<TSourceWrapper, TNameFunc> min(TNameFunc nameFunc) {
        return this.min(nameFunc, null);
    }

    /**
     * 最大值 SELECT MAX(field)
     *
     * @param nameFunc 名称或函数
     * @param alias    别名
     * @return
     */
    public SelectColumnSection<TSourceWrapper, TNameFunc> max(TNameFunc nameFunc, String alias) {
        return this.addSelectColumn(nameFunc, PolymerizationEnum.MAX, alias);
    }

    /**
     * 最大值 SELECT MAX(field)
     *
     * @param nameFunc 名称或函数
     * @return
     */
    public SelectColumnSection<TSourceWrapper, TNameFunc> max(TNameFunc nameFunc) {
        return this.max(nameFunc, null);
    }

    /**
     * 合计 SELECT SUM(field)
     *
     * @param nameFunc 名称或函数
     * @param alias    别名
     * @return
     */
    public SelectColumnSection<TSourceWrapper, TNameFunc> sum(TNameFunc nameFunc, String alias) {
        return this.addSelectColumn(nameFunc, PolymerizationEnum.SUM, alias);
    }

    /**
     * 合计 SELECT SUM(field)
     *
     * @param nameFunc 名称或函数
     * @return
     */
    public SelectColumnSection<TSourceWrapper, TNameFunc> sum(TNameFunc nameFunc) {
        return this.sum(nameFunc, null);
    }

    @Override
    public TSourceWrapper of() {
        return this.sourceWrapper;
    }
}
