package com.autumn.domain.values;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 整数树常量项目值
 * <p>
 * </p>
 *
 * @description: TODO
 * @author: 老码农
 * @create: 2019-11-20 16:34
 **/
public class IntegerTreeConstantItemValue extends IntegerConstantItemValue {

    private static final long serialVersionUID = -7408141700063028014L;
    private final Map<Integer, IntegerTreeConstantItemValue> children;

    /**
     * 实例化
     *
     * @param value   值
     * @param name    名称
     * @param explain 说明
     */
    public IntegerTreeConstantItemValue(Integer value, String name, String explain) {
        super(value, name, explain);
        this.children = new LinkedHashMap<>(16);
    }

    /**
     * 实例化
     *
     * @param value   值
     * @param name    名称
     * @param explain 说明
     * @param order   顺序
     */
    public IntegerTreeConstantItemValue(Integer value, String name, String explain, int order) {
        super(value, name, explain, order);
        this.children = new LinkedHashMap<>(16);
    }

    /**
     * 获取子级
     *
     * @return
     */
    public final Map<Integer, IntegerTreeConstantItemValue> getChildren() {
        return this.children;
    }

    /**
     * 生成常量
     *
     * @return
     */
    public static Map<Integer, IntegerTreeConstantItemValue> generateTreeMap(Class<?> constantClass) {
        List<Field> fields = findStaticFields(constantClass, int.class);
        Map<Integer, IntegerTreeConstantItemValue> map = new HashMap<>(fields.size());
        for (Field field : fields) {
            ConstantField constantField = field.getAnnotation(ConstantField.class);
            if (constantField != null) {
                try {
                    Integer value = (Integer) field.get(null);
                    IntegerTreeConstantItemValue itemValue = new IntegerTreeConstantItemValue(value,
                            constantField.name(),
                            constantField.explain(),
                            constantField.order() == 0 ? value : constantField.order());
                    if (constantField.children() != null) {
                        for (ConstantChildrenField child : constantField.children()) {
                            IntegerTreeConstantItemValue childrenValue = new IntegerTreeConstantItemValue(child.value(),
                                    child.name(),
                                    child.explain(),
                                    child.order() == 0 ? child.value() : child.order());
                            itemValue.getChildren().put(childrenValue.getValue(), childrenValue);
                        }
                    }
                    map.put(itemValue.getValue(), itemValue);
                } catch (IllegalAccessException ignored) {

                }
            }
        }
        return map;
    }

}
