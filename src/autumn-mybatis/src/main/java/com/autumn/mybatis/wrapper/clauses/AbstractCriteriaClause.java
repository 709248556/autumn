package com.autumn.mybatis.wrapper.clauses;

import com.autumn.mybatis.wrapper.CriteriaOperatorConstant;
import com.autumn.mybatis.wrapper.CriteriaOperatorEnum;
import com.autumn.mybatis.wrapper.LogicalOperatorEnum;
import com.autumn.util.DateUtils;
import com.autumn.util.StringUtils;

import java.util.*;

/**
 * 条件表达式
 *
 * @author 老码农
 * <p>
 * 2017-10-26 10:48:21
 */
public abstract class AbstractCriteriaClause extends AbstractClause {

    /**
     *
     */
    private static final long serialVersionUID = -5282864561043386524L;

    private LogicalOperatorEnum logical;
    private final CriteriaOperatorEnum operator;
    private String funName;
    private String logic;
    private final String expression;
    private final String op;
    private Object value;
    private Object secondValue;
    private String leftBrackets;
    private String rigthBrackets;

    /**
     * @param logical
     * @param expression
     * @param operator
     */
    public AbstractCriteriaClause(LogicalOperatorEnum logical, String expression, CriteriaOperatorEnum operator) {
        this.logical = logical;
        this.expression = expression;
        this.operator = operator;
        this.leftBrackets = "";
        this.rigthBrackets = "";
        this.logic = logical.getOperator();
        this.op = operator.getOperator();
    }

    /**
     * 获取逻辑
     *
     * @return
     */
    public String getLogic() {
        return logic;
    }

    /**
     * 获取操作符
     *
     * @return
     */
    public String getOp() {
        return op;
    }

    /**
     * 获取函数名称
     *
     * @return
     */
    public String getFunName() {
        return funName;
    }

    /**
     * 设置函数名称
     *
     * @param funName 函数名称
     */
    public void setFunName(String funName) {
        this.funName = funName;
    }

    /**
     * 获取表达式(如列名称)
     *
     * @return
     */
    public String getExpression() {
        return this.expression;
    }

    /**
     * 获取逻辑
     *
     * @return
     */
    public final LogicalOperatorEnum getLogical() {
        return logical;
    }

    /**
     * 设置逻辑
     *
     * @return
     */
    public final void setLogical(LogicalOperatorEnum logical) {
        this.logical = logical;
        this.logic = logical.getOperator();
    }

    /**
     * 获取运算符
     *
     * @return
     */
    public final CriteriaOperatorEnum getOperator() {
        return this.operator;
    }

    /**
     * 生成表达式
     *
     * @param paramName 参数名称
     * @return
     */
    public abstract String builderExpression(String paramName);

    /**
     * @return
     */
    public final Object getValue() {
        return value;
    }

    public final void setValue(Object value) {
        this.value = value;
    }

    /**
     * 获取 to Value
     *
     * @return
     */
    public final Object getSecondValue() {
        return secondValue;
    }

    /**
     * 设置 to Value
     *
     * @param secondValue
     */
    public final void setSecondValue(Object secondValue) {
        this.secondValue = secondValue;
    }

    /**
     * 获取值集合
     *
     * @return
     */
    public final List<Object> getValues() {
        if (this.getValue() != null) {
            if (this.getValue() instanceof List) {
                List source = (List) this.getValue();
                List<Object> items = new ArrayList<>(source.size());
                items.addAll(source);
                return items;
            }
            if (this.getValue().getClass().isArray()) {
                Object[] source = (Object[]) this.getValue();
                List<Object> items = new ArrayList<>(source.length);
                items.addAll(Arrays.asList(source));
                return items;
            }
        }
        return new ArrayList<>();
    }

    /**
     * 添加左边括号
     */
    public void addLeftBrackets() {
        if (StringUtils.isNullOrBlank(this.leftBrackets)) {
            this.leftBrackets = "(";
        } else {
            this.leftBrackets = this.leftBrackets + "(";
        }
    }

    /**
     * 添加右边括号
     */
    public void addRigthBrackets() {
        if (StringUtils.isNullOrBlank(this.rigthBrackets)) {
            this.rigthBrackets = ")";
        } else {
            this.rigthBrackets = this.rigthBrackets + ")";
        }
    }

    /**
     * 获取左括号
     *
     * @return
     */
    public final String getLeftBrackets() {
        return leftBrackets;
    }

    /**
     * 获取右括号
     *
     * @return
     */
    public final String getRigthBrackets() {
        return rigthBrackets;
    }

    @Override
    public String toString() {
        return this.toString(true);
    }

    /**
     * 转换为Sql
     *
     * @param isAddLogical
     * @return
     */
    public String toString(boolean isAddLogical) {
        StringBuilder builder = new StringBuilder();
        if (isAddLogical) {
            builder.append(this.getLogic()).append(" ");
        }
        if (!StringUtils.isNullOrBlank(this.getLeftBrackets())) {
            builder.append(this.getLeftBrackets());
        }
        if (!(this.getOp().equalsIgnoreCase(CriteriaOperatorConstant.EXISTS)
                || this.getOp().equalsIgnoreCase(CriteriaOperatorConstant.NOT_EXISTS))) {
            if (!StringUtils.isNullOrBlank(this.getFunName())) {
                builder.append(this.getFunName());
                if ("COUNT".equalsIgnoreCase(this.getFunName())) {
                    builder.append("(*) ");
                } else {
                    builder.append("(").append(this.getExpression()).append(") ");
                }
            } else {
                builder.append(this.getExpression()).append(" ");
            }
        }
        addContent(builder);
        if (!StringUtils.isNullOrBlank(this.getRigthBrackets())) {
            builder.append(this.getRigthBrackets());
        }
        return builder.toString();
    }

    private void addContent(StringBuilder builder) {
        switch (this.getOp().toUpperCase()) {
            case CriteriaOperatorConstant.LIKE:
                builder.append("LIKE CONCAT('%',").append(toSqlValue(this.getValue())).append(",'%')");
                break;
            case CriteriaOperatorConstant.LEFT_LIKE:
                builder.append("LIKE CONCAT(").append(toSqlValue(this.getValue())).append(",'%')");
                break;
            case CriteriaOperatorConstant.RIGHT_LIKE:
                builder.append("LIKE CONCAT('%',").append(toSqlValue(this.getValue())).append(")");
                break;
            case CriteriaOperatorConstant.IS_NULL:
                builder.append("IS NULL");
                break;
            case CriteriaOperatorConstant.IS_NOT_NULL:
                builder.append("IS NOT NULL");
                break;
            case CriteriaOperatorConstant.IN:
            case CriteriaOperatorConstant.NOT_IN:
                if (this.getOp().equalsIgnoreCase(CriteriaOperatorConstant.IN)) {
                    builder.append("IN(");
                } else {
                    builder.append("NOT IN(");
                }
                List<Object> values = this.getValues();
                for (int i = 0; i < values.size(); i++) {
                    Object v = values.get(i);
                    if (i > 0) {
                        builder.append(",");
                    }
                    builder.append(toSqlValue(v));
                }
                builder.append(")");
                break;
            case CriteriaOperatorConstant.IN_SQL:
                builder.append("IN(").append(this.getValue()).append(")");
                break;
            case CriteriaOperatorConstant.NOT_IN_SQL:
                builder.append("NOT IN(").append(this.getValue()).append(")");
                break;
            case CriteriaOperatorConstant.EXISTS:
                builder.append("EXISTS(").append(this.getExpression()).append(")");
                break;
            case CriteriaOperatorConstant.NOT_EXISTS:
                builder.append("NOT EXISTS(").append(this.getExpression()).append(")");
                break;
            case CriteriaOperatorConstant.BETWEEN:
                builder.append("BETWEEN ").append(toSqlValue(this.getValue())).append(" AND ").append(toSqlValue(this.getSecondValue()));
                break;
            default:
                builder.append(this.getOp()).append(" ").append(toSqlValue(this.getValue()));
                break;
        }
    }

    /**
     * @param value
     * @return
     */
    public static String toSqlValue(Object value) {
        if (value instanceof String) {
            return "'" + ((String) value).replace("'", "") + "'";
        } else if (value instanceof Date) {
            return "'" + DateUtils.dateFormat((Date) value) + "'";
        }
        if (value == null) {
            return "";
        }
        return value.toString();
    }


}
