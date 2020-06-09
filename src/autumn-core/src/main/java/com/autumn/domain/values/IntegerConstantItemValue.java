package com.autumn.domain.values;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 整数常量项目
 *
 * @author 老码农 2018-11-24 22:36:07
 */
public class IntegerConstantItemValue extends ConstantItemValue<Integer> {

    /**
     *
     */
    private static final long serialVersionUID = 4818020628166463329L;

    /**
     * 实例化
     *
     * @param value   值
     * @param name    名称
     * @param explain 说明
     */
    public IntegerConstantItemValue(Integer value, String name, String explain) {
        super(value, name, explain, value);
    }

    /**
     * 实例化
     *
     * @param value   值
     * @param name    名称
     * @param explain 说明
     * @param order   顺序
     */
    public IntegerConstantItemValue(Integer value, String name, String explain, int order) {
        super(value, name, explain, order);
    }

    @Override
    public int compareTo(ConstantItemValue<Integer> o) {
        if (this.getOrder() == o.getOrder()) {
            return Integer.compare(this.getValue(), o.getValue());
        }
        return Integer.compare(this.getOrder(), o.getOrder());
    }

    /**
     * 生成常量
     *
     * @return
     */
    public static Map<Integer, IntegerConstantItemValue> generateMap(Class<?> constantClass) {
        List<Field> fields = findStaticFields(constantClass, int.class);
        Map<Integer, IntegerConstantItemValue> map = new HashMap<>(fields.size());
        for (Field field : fields) {
            ConstantField constantField = field.getAnnotation(ConstantField.class);
            if (constantField != null) {
                try {
                    Integer value = (Integer) field.get(null);
                    IntegerConstantItemValue itemValue = new IntegerConstantItemValue(value,
                            constantField.name(),
                            constantField.explain(),
                            constantField.order() == 0 ? value : constantField.order());
                    map.put(itemValue.getValue(), itemValue);
                } catch (IllegalAccessException ignored) {

                }
            }
        }
        return map;
    }
}
