package com.autumn.domain.values;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 字符常最项目值
 *
 * @author 老码农 2018-11-24 22:37:39
 */
public class StringConstantItemValue extends ConstantItemValue<String> {

    /**
     *
     */
    private static final long serialVersionUID = -192659313514490416L;

    /**
     * 实例化 StringConstantItemValue
     *
     * @param value   值
     * @param name    名称
     * @param explain 说明
     */
    public StringConstantItemValue(String value, String name, String explain) {
        super(value, name, explain);
    }

    /**
     * 实例化
     *
     * @param value   值
     * @param name    名称
     * @param explain 说明
     * @param order   顺序
     */
    public StringConstantItemValue(String value, String name, String explain, int order) {
        super(value, name, explain, order);
    }

    @Override
    public int compareTo(ConstantItemValue<String> o) {
        if (this.getOrder() == o.getOrder()) {
            return this.getValue().compareTo(o.getValue());
        }
        return Integer.compare(this.getOrder(), o.getOrder());
    }

    /**
     * 生成常量
     *
     * @return
     */
    public static Map<String, StringConstantItemValue> generateMap(Class<?> constantClass) {
        List<Field> fields = findStaticFields(constantClass, String.class);
        Map<String, StringConstantItemValue> map = new HashMap<>(fields.size());
        int sort = 1;
        for (Field field : fields) {
            ConstantField constantField = field.getAnnotation(ConstantField.class);
            if (constantField != null) {
                try {
                    String value = (String) field.get(null);
                    int order = constantField.order();
                    if (order == 0) {
                        order = sort;
                    }
                    StringConstantItemValue itemValue = new StringConstantItemValue(value, constantField.name(), constantField.explain(), order);
                    map.put(itemValue.getValue(), itemValue);
                } catch (IllegalAccessException ignored) {

                }
                sort++;
            }
        }
        return map;
    }

}
