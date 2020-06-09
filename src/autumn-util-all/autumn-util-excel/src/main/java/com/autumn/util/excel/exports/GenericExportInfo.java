package com.autumn.util.excel.exports;

import com.autumn.exception.ExceptionUtils;
import com.autumn.util.TypeUtils;
import com.autumn.util.excel.utils.YesOrNoConst;
import com.autumn.util.reflect.BeanProperty;
import com.autumn.util.reflect.ReflectUtils;

import java.util.Map;

/**
 * 泛型导出信息
 *
 * @param <T>
 */
public class GenericExportInfo<T> extends AbstractExportInfo<T> {

    private Map<String, BeanProperty> propertyMap = null;

    private Class<T> beanClass;

    /**
     * 获取泛型的具体类型
     *
     * @return
     */
    public Class<T> getBeanClass() {
        return beanClass;
    }

    public GenericExportInfo() {
    }

    /**
     * 初始化导出信息，并传入泛型类型
     *
     * @param beanClass
     */
    public GenericExportInfo(Class<T> beanClass) {
        this.beanClass = ExceptionUtils.checkNotNull(beanClass, "beanClass");
    }


    /**
     * 读取
     *
     * @param item 项目
     * @param name 名稱
     */
    @Override
    public final Object read(T item, String name) {
        if (item == null || name == null) {
            return null;
        }
        if (propertyMap == null) {
            this.propertyMap = ReflectUtils.getBeanPropertyMap(item.getClass());
        }
        BeanProperty property = this.propertyMap.get(name);
        if (property != null && property.canRead()) {
            return property.getValue(item);
        }
        return null;
    }

    /**
     * 写入
     */
    @Override
    public final void write(T item, String name, Object value) {
        if (item == null || name == null) {
            return;
        }
        if (propertyMap == null) {
            propertyMap = ReflectUtils.getBeanPropertyMap((item.getClass()));
        }
        BeanProperty property = this.propertyMap.get(name);
        if (property != null && property.canRead()) {
            if (value != null && property.getType().equals(boolean.class)) {
                String strValue = value.toString().trim().toLowerCase();
                if (YesOrNoConst.TRUE_SET.contains(strValue)) {
                    property.setValue(item, true);
                } else {
                    property.setValue(item, false);
                }
            } else {
                property.setValue(item, TypeUtils.toConvert(property.getType(), value));
            }
        }
    }

}
