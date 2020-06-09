package com.autumn.util.convert;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import com.autumn.util.EqualsUtils;
import com.autumn.exception.InvalidCastException;
import com.autumn.util.PackageUtils;
import com.autumn.util.TypeUtils;

/**
 * 数据转换抽象
 *
 * @param <T> 类型
 * @author 老码农
 * <p>
 * 2017-09-29 15:03:20
 */
public abstract class AbstractDataConvert<T> implements DataConvert {

    private final static Map<Class<?>, DataConvert> DATA_CONVERT_MAP;

    static {
        DATA_CONVERT_MAP = registerDataConvertMap();
    }

    private final Class<T> convertClass;

    public AbstractDataConvert(Class<T> convertClass) {
        this.convertClass = convertClass;
    }

    @Override
    public Class<?> getType() {
        return this.convertClass;
    }

    /**
     * 获取默认值
     *
     * @return
     * @author 老码农 2017-09-29 15:00:18
     */
    @Override
    public abstract T getDefaultValue();

    /**
     * 注册
     *
     * @return
     * @author 老码农 2017-09-30 17:02:58
     */
    private static Map<Class<?>, DataConvert> registerDataConvertMap() {
        Map<Class<?>, DataConvert> map = new ConcurrentHashMap<>(32);
        try {
            Set<Class<?>> typeSet = PackageUtils.getPackageTargetClass(DataConvert.class.getPackage().getName(),
                    DataConvert.class, false, false, true);
            for (Class<?> type : typeSet) {
                DataConvert dataConvert = (DataConvert) type.newInstance();
                map.put(dataConvert.getType(), dataConvert);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    /**
     * 获取数据转换器
     *
     * @param type
     * @return
     * @author 老码农 2017-09-30 17:01:08
     */
    public static DataConvert getDataConvert(Class<?> type) {
        DataConvert convert = DATA_CONVERT_MAP.get(type);
        if (convert == null && Enum.class.isAssignableFrom(type)) {
            convert = DATA_CONVERT_MAP.get(Enum.class);
        }
        return convert;
    }

    /**
     * 是否为基本类型
     *
     * @return
     * @author 老码农 2017-09-29 15:01:05
     */
    @Override
    public boolean isBaseType() {
        return TypeUtils.isBaseType(this.getType());
    }

    /**
     * 是否为数字类型
     *
     * @return
     * @author 老码农 2017-09-29 15:01:40
     */
    @Override
    public boolean isNumberType() {
        return TypeUtils.isNumberType(this.getType());
    }

    /**
     * 转换
     *
     * @param source 源
     * @return
     * @author 老码农 2017-09-29 15:02:43
     */
    @Override
    public final Object convert(Object source) {
        return this.convert(this.getType(), source);
    }

    /**
     * 转换
     */
    @Override
    public final Object convert(Class<?> targetClass, Object source) {
        if (source == null) {
            return this.getDefaultValue();
        }
        Class<?> sourceClass = source.getClass();
        if (sourceClass.equals(targetClass) || EqualsUtils.equalsPrimitiveClass(targetClass, sourceClass)) {
            return source;
        }
        try {
            return convert(targetClass, sourceClass, source);
        } catch (InvalidCastException e) {
            throw e;
        } catch (Throwable e) {
            throw this.throwConvertException(targetClass, sourceClass, source, e);
        }
    }

    /**
     * 转换
     *
     * @param targetClass 目标类型
     * @param sourceClass 源类型
     * @param source      源
     * @return
     */
    protected abstract T convert(Class<?> targetClass, Class<?> sourceClass, Object source);

    /**
     * 抛出转换异常
     *
     * @param sourceClass 源类型
     * @param source      源值
     * @return
     * @author 老码农 2017-09-29 16:19:50
     */
    protected InvalidCastException throwConvertException(Class<?> sourceClass, Object source) {
        return throwConvertException(this.getType(), sourceClass, source, null);
    }

    /**
     * 抛出转换异常
     *
     * @param targetClass 目标类型
     * @param sourceClass 源类型
     * @param source      源值
     * @param e           错误源
     * @return
     * @author 老码农 2017-09-29 16:19:50
     */
    protected InvalidCastException throwConvertException(Class<?> targetClass, Class<?> sourceClass, Object source,
                                                         Throwable e) {
        if (e != null) {
            throw new InvalidCastException(String.format("类型[%s]的值为[%s]，无法转换为[%s]类型。错误信息：%s", sourceClass.getName(),
                    source.toString(), targetClass.getName(), e.getMessage()), e);
        }
        throw new InvalidCastException(String.format("类型[%s]的值为[%s]，无法转换为[%s]类型。", sourceClass.getName(),
                source.toString(), targetClass.getName()));
    }

    /**
     * 获取枚举Ordinal
     *
     * @param source
     * @return
     */
    @SuppressWarnings("rawtypes")
    protected int getEnumOrdinal(Object source) {
        return ((Enum) source).ordinal();
    }

    /**
     * 获取枚举名称
     *
     * @param source
     * @return
     */
    @SuppressWarnings("rawtypes")
    protected String getEnumName(Object source) {
        return ((Enum) source).name();
    }
}
