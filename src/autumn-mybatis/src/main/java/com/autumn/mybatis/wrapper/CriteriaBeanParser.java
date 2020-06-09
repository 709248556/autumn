package com.autumn.mybatis.wrapper;

import java.util.List;
import java.util.Map;

import com.autumn.mybatis.metadata.EntityTable;
import com.autumn.mybatis.wrapper.commands.CriteriaBean;
import com.autumn.mybatis.wrapper.expressions.AbstractCriteriaExpression;
import com.autumn.mybatis.wrapper.expressions.AbstractExpression;
import com.autumn.mybatis.wrapper.expressions.ColumnExpression;
import com.autumn.exception.ExceptionUtils;
import com.autumn.util.StringUtils;
import com.autumn.util.tuple.TupleTwo;

/**
 * 条件 Bean 解析
 *
 * @author 老码农
 * <p>
 * 2017-10-29 16:01:22
 */
public class CriteriaBeanParser<E extends CriteriaBean> extends AbstractParser {

    private final List<E> criteriaBeans;
    public final static Map<String, CriteriaOperatorEnum> CRITERIA_OPERATOR_MAP;
    public final static Map<String, LogicalOperatorEnum> LOGICAL_OPERATOR_MAP;

    static {
        CRITERIA_OPERATOR_MAP = CriteriaOperatorEnum.getMapByOperator();
        LOGICAL_OPERATOR_MAP = LogicalOperatorEnum.getMapByOperator();
    }

    /**
     * 实例化 CriteriaBeanParser
     *
     * @param entityTable   表
     * @param criteriaBeans 条件集合
     */
    public CriteriaBeanParser(EntityTable entityTable, List<E> criteriaBeans) {
        super(entityTable);
        this.criteriaBeans = criteriaBeans;
    }

    /**
     * 解析
     *
     * @return 返回 条件表达式
     */
    public AbstractCriteriaExpression parser() {
        if (this.criteriaBeans == null || this.criteriaBeans.size() == 0) {
            return null;
        }
        AbstractCriteriaExpression expression = null;
        for (E item : criteriaBeans) {
            if (item == null) {
                ExceptionUtils.throwArgumentException("item", "条件项目存在为 null 的 Bean。");
            }
            TupleTwo<LogicalOperatorEnum, AbstractCriteriaExpression> logicExp = createCriteriaExpression(item);
            if (expression == null) {
                expression = logicExp.getItem2();
            } else {
                expression = AbstractExpression.logical(logicExp.getItem1(), expression, logicExp.getItem2());
            }
        }
        return expression;
    }

    /**
     * @param item
     * @return
     */
    private TupleTwo<LogicalOperatorEnum, AbstractCriteriaExpression> createCriteriaExpression(E item) {
        TupleTwo<LogicalOperatorEnum, CriteriaOperatorEnum> tuple = checkItem(item);
        ColumnExpression col = column(item.getExpression());
        AbstractCriteriaExpression criteriaExp;
        if (tuple.getItem2().equals(CriteriaOperatorEnum.BETWEEN)) {
            criteriaExp = AbstractExpression.binary(CriteriaOperatorEnum.BETWEEN, col, AbstractExpression
                    .between(constantValue(col, item.getValue()), constantValue(col, item.getSecondValue())));
        } else {
            criteriaExp = AbstractExpression.binary(tuple.getItem2(), col, constant(col, item.getValue()));
        }
        return new TupleTwo<>(tuple.getItem1(), criteriaExp);
    }

    /**
     * @param item
     * @return
     */
    private TupleTwo<LogicalOperatorEnum, CriteriaOperatorEnum> checkItem(E item) {
        if (StringUtils.isNullOrBlank(item.getLogic())) {
            item.setLogic("AND");
        }
        if (StringUtils.isNullOrBlank(item.getExpression())) {
            ExceptionUtils.throwArgumentException("item", "条件项目的表达式 expression 为 null 或空白值。");
        }
        if (StringUtils.isNullOrBlank(item.getOp())) {
            ExceptionUtils.throwArgumentException("item", "条件项目的运算符 op 为 null 或空白值。");
        }
        CriteriaOperatorEnum op = CRITERIA_OPERATOR_MAP.get(item.getOp().toUpperCase());
        if (op == null) {
            ExceptionUtils.throwArgumentException("item", "条件项目的运算符 op 为[" + item.getOp() + "]无效或不支持。");
        }
        if (!op.isSupportClient()) {
            ExceptionUtils.throwArgumentException("item", "条件项目的运算符 op 为[" + item.getOp() + "]不支持前端输入。");
        }
        boolean isCheckValue = !(op.equals(CriteriaOperatorEnum.IS_NULL) || op.equals(CriteriaOperatorEnum.IS_NOT_NULL));
        if (isCheckValue) {
            if (item.getValue() == null || StringUtils.isNullOrBlank(item.getValue().toString())) {
                ExceptionUtils.throwArgumentException("item", "条件项目的值 value 为 null。");
            }
        }
        boolean isNotSecondValue = item.getSecondValue() == null
                || StringUtils.isNullOrBlank(item.getSecondValue().toString());
        if (op.equals(CriteriaOperatorEnum.BETWEEN) && isNotSecondValue) {
            ExceptionUtils.throwArgumentException("item", "条件项目的值 secondValue 为 null。");
        }
        LogicalOperatorEnum logic = LOGICAL_OPERATOR_MAP.get(item.getLogic().toUpperCase());
        if (logic == null) {
            ExceptionUtils.throwArgumentException("item", "条件项目的逻辑表达式 logic 为[" + item.getLogic() + "]无效或不支持。");
        }
        return new TupleTwo<>(logic, op);
    }

}
