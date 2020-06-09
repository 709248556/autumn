package com.autumn.mybatis.wrapper.commands;

import com.autumn.exception.ExceptionUtils;
import com.autumn.mybatis.metadata.EntityTable;
import com.autumn.mybatis.wrapper.clauses.UpdateClause;
import com.autumn.mybatis.wrapper.commands.impl.UpdateSectionImpl;
import com.autumn.mybatis.wrapper.expressions.ColumnExpression;

import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

/**
 * 更新包装器
 *
 * @Description TODO
 * @Author 老码农
 * @Date 2019-06-08 22:33
 */
public class UpdateWrapperCommand extends WrapperCommand {

    /**
     *
     */
    private static final long serialVersionUID = -725834527078989591L;

    /**
     * 创建包查询装器
     *
     * @param table
     * @return
     */
    public static UpdateWrapperCommand createUpdateWrapper(EntityTable table) {
        return new UpdateWrapperCommand(table);
    }

    /**
     * 获取排序集合
     *
     * @return
     */
    private final Map<String, UpdateClause> updateClauses = new LinkedHashMap<>();

    private final EntityTable table;

    /**
     *
     */
    UpdateWrapperCommand(EntityTable table) {
        this.table = table;
    }

    /**
     * 移除列
     *
     * @param columnName 列名称
     */
    public void removeSet(String columnName) {
        updateClauses.remove(columnName.toUpperCase(Locale.ENGLISH));
    }

    /**
     * 设置更新
     *
     * @param columnExpression
     * @param value
     */
    public void set(ColumnExpression columnExpression, Object value) {
        this.removeSet(columnExpression.getColumnName());
        UpdateClause updateClause = new UpdateClause(columnExpression.getColumnName(), value);
        this.updateClauses.put(updateClause.getColumnName().toUpperCase(Locale.ENGLISH), updateClause);
    }

    /**
     * 重置
     */
    @Override
    public void reset() {
        super.reset();
        this.updateClauses.clear();
    }

    /**
     * 创建段
     *
     * @return
     */
    @Override
    public UpdateSection createSection() {
        UpdateSectionImpl qs = new UpdateSectionImpl(this.table != null ? this.table.getName() : "", this.getLockMode());
        if (updateClauses.size() == 0) {
            ExceptionUtils.throwApplicationException("至少需要更新一列以上。");
        }
        // 条件表达式
        qs.getCriterias().addAll(this.createCriteriaSections());
        qs.setAlias(this.getAlias());
        qs.getUpdateClauses().addAll(this.updateClauses.values());
        return qs;
    }

}
