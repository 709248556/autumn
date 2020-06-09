package com.autumn.util.reflect;

import com.autumn.exception.ExceptionUtils;
import com.autumn.exception.SystemException;
import com.autumn.util.StringUtils;
import com.autumn.util.TypeUtils;
import com.esotericsoftware.reflectasm.MethodAccess;

import java.beans.PropertyDescriptor;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Bean属性
 *
 * @author 老码农
 * <p>
 * 2017-09-27 17:03:06
 */
public class BeanProperty {

    private final String name;
    private final Method readMethod;
    private final Method writeMethod;
    private final MethodAccess methodAccess;
    private final Class<?> type;
    private final Class<?> beanClass;
    private final int readIndex;
    private final int writeIndex;
    private final boolean baseType;
    private final Field field;
    private final PropertyDescriptor propertyDescriptor;

    /**
     * 实例化
     *
     * @param beanClass
     * @param field
     * @param propertyDescriptor
     */
    public BeanProperty(Class<?> beanClass, Field field, PropertyDescriptor propertyDescriptor) {
        this.beanClass = ExceptionUtils.checkNotNull(beanClass, "beanClass");
        ExceptionUtils.checkNotNull(propertyDescriptor, "propertyDescriptor");
        this.propertyDescriptor = propertyDescriptor;
        this.field = field;
        this.name = propertyDescriptor.getName();
        this.type = propertyDescriptor.getPropertyType();
        this.methodAccess = ReflectUtils.getMethodAccess(this.beanClass);
        this.readMethod = propertyDescriptor.getReadMethod();
        if (this.readMethod != null) {
            this.readIndex = methodAccess.getIndex(this.readMethod.getName(), 0);
        } else {
            this.readIndex = -1;
        }
        this.writeMethod = propertyDescriptor.getWriteMethod();
        if (this.writeMethod != null) {
            int index;
            if (this.readMethod != null) {
                index = methodAccess.getIndex(this.writeMethod.getName(), 1);
            } else {
                try {
                    index = methodAccess.getIndex(this.writeMethod.getName(), this.readMethod.getReturnType());
                } catch (Exception e) {
                    index = methodAccess.getIndex(this.writeMethod.getName(), this.type);
                }
            }
            this.writeIndex = index;
        } else {
            this.writeIndex = -1;
        }
        this.baseType = TypeUtils.isBaseType(this.type);
    }

    /**
     * 获取Bean类型
     *
     * @return
     */
    public Class<?> getBeanClass() {
        return this.beanClass;
    }

    /**
     * 是否是基本类型
     *
     * @return
     */
    public boolean isBaseType() {
        return this.baseType;
    }

    /**
     * 允许读写
     *
     * @return
     */
    public boolean canReadWrite() {
        return this.canRead() && this.canWrite();
    }

    /**
     * 允许读
     *
     * @return
     */
    public boolean canRead() {
        return this.getReadMethod() != null;
    }

    /**
     * 允许写
     *
     * @return
     */
    public boolean canWrite() {
        return this.getWriteMethod() != null;
    }

    /**
     * 获取值
     *
     * @param instance 实例
     * @return
     * @author 老码农 2017-09-29 10:00:35
     */
    public Object getValue(Object instance) {
        if (this.canRead()) {
            return methodAccess.invoke(instance, this.readIndex);
        }
        String readName = StringUtils.upperCaseCapitalize(this.name);
        throw new SystemException("类型[" + this.beanClass + "]不存在方法["
                + ReflectUtils.PROPERTY_GET_NAME + readName + "或"
                + ReflectUtils.PROPERTY_IS_NAME + readName + "]");
    }

    /**
     * 设置值
     *
     * @param instance 实例
     * @param value    值
     * @author 老码农 2017-09-29 10:01:46
     */
    public void setValue(Object instance, Object value) {
        if (this.canWrite()) {
            methodAccess.invoke(instance, this.writeIndex, value);
        } else {
            throw new SystemException("类型[" + this.beanClass + "]不存在方法["
                    + ReflectUtils.PROPERTY_SET_NAME + StringUtils.upperCaseCapitalize(this.name) + "]");
        }
    }

    /**
     * 是否有注解
     *
     * @param annotationClass
     * @return
     * @author 老码农 2017-09-27 17:14:29
     */
    public boolean isAnnotation(Class<? extends Annotation> annotationClass) {
        return getAnnotation(annotationClass) != null;
    }

    /**
     * 获取指定的注解
     *
     * @param annotationClass
     * @param <T>
     * @return
     */
    public <T extends Annotation> T getAnnotation(Class<T> annotationClass) {
        T result = null;
        if (this.canRead()) {
            result = this.readMethod.getAnnotation(annotationClass);
        }
        if (result == null && this.canWrite()) {
            result = this.writeMethod.getAnnotation(annotationClass);
        }
        if (result == null && this.field != null) {
            result = field.getAnnotation(annotationClass);
        }
        return result;
    }

    /**
     * 获取名称
     *
     * @return
     * @author 老码农 2017-09-27 17:12:21
     */
    public String getName() {
        return name;
    }

    /**
     * 获取 set Method
     *
     * @return
     * @author 老码农 2017-09-27 17:12:36
     */
    public Method getReadMethod() {
        return this.readMethod;
    }

    /**
     * 获取 get Method
     *
     * @return
     * @author 老码农 2017-09-27 17:12:53
     */
    public Method getWriteMethod() {
        return this.writeMethod;
    }

    /**
     * 获取类型
     *
     * @return
     * @author 老码农 2017-10-10 09:57:56
     */
    public Class<?> getType() {
        return this.type;
    }

    /**
     * 是否是数组
     *
     * @return
     */
    public boolean isArray() {
        return this.getType().isArray();
    }

    /**
     * 是否是接口
     *
     * @return
     */
    public boolean isInterface() {
        return this.getType().isInterface();
    }

    /**
     * 是否是抽象类型
     *
     * @return
     */
    public boolean isAbstract() {
        return Modifier.isAbstract(this.getType().getModifiers());
    }

    /**
     * 是否是 Iterable 类型
     *
     * @return
     */
    public boolean isIterableType() {
        return Iterable.class.isAssignableFrom(this.getType());
    }

    /**
     * 是否是 List 类型
     *
     * @return
     */
    public boolean isListType() {
        return List.class.isAssignableFrom(this.getType());
    }

    /**
     * 是否是 Collection 类型
     *
     * @return
     */
    public boolean isCollectionType() {
        return Collection.class.isAssignableFrom(this.getType());
    }

    /**
     * 是否是 Map 类型
     *
     * @return
     */
    public boolean isMapType() {
        return Map.class.isAssignableFrom(this.getType());
    }

    @Override
    public int hashCode() {
        return this.propertyDescriptor.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof BeanProperty) {
            BeanProperty beanProperty = (BeanProperty) obj;
            return this.getBeanClass().equals(beanProperty.getBeanClass())
                    && this.getType().equals(beanProperty.getType())
                    && this.getName().equals(beanProperty.getName());
        }
        return false;
    }

    @Override
    public String toString() {
        return this.getName() + " (" + this.getType() + ")";
    }

}
