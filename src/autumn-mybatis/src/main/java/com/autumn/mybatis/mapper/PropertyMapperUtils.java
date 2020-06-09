package com.autumn.mybatis.mapper;

import com.autumn.mybatis.mapper.annotation.ColumnType;
import com.autumn.mybatis.metadata.EntityTable;
import com.autumn.mybatis.type.TimeSpanTypeHandler;
import com.autumn.mybatis.type.TimeTypeHandler;
import com.autumn.util.StringUtils;
import com.autumn.util.Time;
import com.autumn.util.TimeSpan;
import com.autumn.util.TypeUtils;
import com.autumn.util.reflect.BeanProperty;
import com.autumn.util.tuple.TupleTwo;
import org.apache.ibatis.type.*;

import javax.persistence.*;
import java.lang.annotation.Annotation;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 属性映射帮助
 *
 * @author 老码农
 * <p>
 * 2017-10-11 12:18:51
 */
public class PropertyMapperUtils {
    private static final Map<Class<?>, TupleTwo<JdbcType, Class<? extends TypeHandler<?>>>> JDBC_TYPE_MAP = new HashMap<>(50);

    /**
     * 实体主键生成方式
     */
    private static final Map<Class<?>, GenerationType> ENTITY_KEY_GENERATION_TYPE_MAP = new ConcurrentHashMap<>(EntityTable.DEFAULT_ENTITY_NUMBER);

    static {

        TupleTwo<JdbcType, Class<? extends TypeHandler<?>>> tuple;

        tuple = new TupleTwo<>(JdbcType.VARCHAR, StringTypeHandler.class);
        JDBC_TYPE_MAP.put(String.class, tuple);

        tuple = new TupleTwo<>(JdbcType.TIME, TimeTypeHandler.class);
        JDBC_TYPE_MAP.put(Time.class, tuple);

        tuple = new TupleTwo<>(JdbcType.TIME, SqlTimeTypeHandler.class);
        JDBC_TYPE_MAP.put(java.sql.Time.class, tuple);

        tuple = new TupleTwo<>(JdbcType.BIGINT, TimeSpanTypeHandler.class);
        JDBC_TYPE_MAP.put(TimeSpan.class, tuple);

        tuple = new TupleTwo<>(JdbcType.TINYINT, ByteTypeHandler.class);
        JDBC_TYPE_MAP.put(Byte.class, tuple);
        JDBC_TYPE_MAP.put(Byte.TYPE, tuple);

        tuple = new TupleTwo<>(JdbcType.SMALLINT, ShortTypeHandler.class);
        JDBC_TYPE_MAP.put(Short.class, tuple);
        JDBC_TYPE_MAP.put(Short.TYPE, tuple);

        tuple = new TupleTwo<>(JdbcType.NCHAR, CharacterTypeHandler.class);
        JDBC_TYPE_MAP.put(Character.class, tuple);
        JDBC_TYPE_MAP.put(Character.TYPE, tuple);

        tuple = new TupleTwo<>(JdbcType.INTEGER, IntegerTypeHandler.class);
        JDBC_TYPE_MAP.put(Integer.class, tuple);
        JDBC_TYPE_MAP.put(Integer.TYPE, tuple);

        tuple = new TupleTwo<>(JdbcType.BIGINT, LongTypeHandler.class);
        JDBC_TYPE_MAP.put(Long.class, tuple);
        JDBC_TYPE_MAP.put(Long.TYPE, tuple);

        tuple = new TupleTwo<>(JdbcType.FLOAT, FloatTypeHandler.class);
        JDBC_TYPE_MAP.put(Float.class, tuple);
        JDBC_TYPE_MAP.put(Float.TYPE, tuple);

        tuple = new TupleTwo<>(JdbcType.DOUBLE, DoubleTypeHandler.class);
        JDBC_TYPE_MAP.put(Double.class, tuple);
        JDBC_TYPE_MAP.put(Double.TYPE, tuple);

        tuple = new TupleTwo<>(JdbcType.BIT, BooleanTypeHandler.class);
        JDBC_TYPE_MAP.put(Boolean.class, tuple);
        JDBC_TYPE_MAP.put(Boolean.TYPE, tuple);

        tuple = new TupleTwo<>(JdbcType.TIMESTAMP, DateTypeHandler.class);
        JDBC_TYPE_MAP.put(Date.class, tuple);

        tuple = new TupleTwo<>(JdbcType.TIMESTAMP, SqlDateTypeHandler.class);
        JDBC_TYPE_MAP.put(java.sql.Date.class, tuple);

        tuple = new TupleTwo<>(JdbcType.TIMESTAMP, SqlTimestampTypeHandler.class);
        JDBC_TYPE_MAP.put(java.sql.Timestamp.class, tuple);

        tuple = new TupleTwo<>(JdbcType.DATE, LocalDateTypeHandler.class);
        JDBC_TYPE_MAP.put(LocalDate.class, tuple);

        tuple = new TupleTwo<>(JdbcType.TIMESTAMP, LocalDateTimeTypeHandler.class);
        JDBC_TYPE_MAP.put(LocalDateTime.class, tuple);

        tuple = new TupleTwo<>(JdbcType.TIME, LocalTimeTypeHandler.class);
        JDBC_TYPE_MAP.put(LocalTime.class, tuple);

        tuple = new TupleTwo<>(JdbcType.VARCHAR, BigIntegerTypeHandler.class);
        JDBC_TYPE_MAP.put(BigInteger.class, tuple);

        tuple = new TupleTwo<>(JdbcType.DECIMAL, BigDecimalTypeHandler.class);
        JDBC_TYPE_MAP.put(BigDecimal.class, tuple);

        tuple = new TupleTwo<>(JdbcType.INTEGER, IntegerTypeHandler.class);
        JDBC_TYPE_MAP.put(Enum.class, tuple);

        tuple = new TupleTwo<>(JdbcType.TINYINT, ByteArrayTypeHandler.class);
        JDBC_TYPE_MAP.put(byte[].class, tuple);
        JDBC_TYPE_MAP.put(Byte[].class, tuple);
    }

    /**
     * 获取属性类型映射
     *
     * @param property 属性
     * @return
     */
    public static TupleTwo<JdbcType, Class<? extends TypeHandler<?>>> getPropertyTypeMap(BeanProperty property) {
        JdbcType jdbcType = null;
        Class<? extends TypeHandler<?>> typeHandlerClass = null;
        TupleTwo<JdbcType, Class<? extends TypeHandler<?>>> columntypeMap = null;
        ColumnType columnType = property.getAnnotation(ColumnType.class);
        if (columnType != null) {
            columntypeMap = new TupleTwo<>(columnType.jdbcType(), columnType.typeHandlerClass());
        }
        if (columntypeMap != null && columntypeMap.getItem1() != null
                && !columntypeMap.getItem1().equals(JdbcType.UNDEFINED)) {
            jdbcType = columntypeMap.getItem1();
        }
        if (columntypeMap != null && columntypeMap.getItem2() != null
                && !columntypeMap.getItem2().equals(UnknownTypeHandler.class)) {
            typeHandlerClass = columntypeMap.getItem2();
        }
        if (jdbcType == null || typeHandlerClass == null) {
            TupleTwo<JdbcType, Class<? extends TypeHandler<?>>> typeMap = getDefaultTypeMap(property.getType());
            if (jdbcType == null) {
                jdbcType = typeMap.getItem1();
            }
            if (typeHandlerClass == null) {
                typeHandlerClass = typeMap.getItem2();
            }
        }
        return new TupleTwo<>(jdbcType, typeHandlerClass);
    }

    /**
     * 获取 类型 映射
     *
     * @param type java类型
     * @return
     */
    public static TupleTwo<JdbcType, Class<? extends TypeHandler<?>>> getDefaultTypeMap(Class<?> type) {
        TupleTwo<JdbcType, Class<? extends TypeHandler<?>>> map = JDBC_TYPE_MAP.get(type);
        if (map == null) {
            if (Enum.class.isAssignableFrom(type)) {
                map = JDBC_TYPE_MAP.get(Enum.class);
            }
            if (map == null) {
                map = new TupleTwo<>(JdbcType.UNDEFINED, UnknownTypeHandler.class);
            }
        }
        return map;
    }

    /**
     * 获取或默认列名称
     *
     * @param property                属性
     * @param specificationDefinition 规范
     * @return
     */
    public static String getOrDefaultColumnName(BeanProperty property, SpecificationDefinition specificationDefinition) {
        String name;
        if (property.getType().equals(boolean.class) || property.getType().equals(Boolean.class)) {
            if (!property.getName().toLowerCase(Locale.ENGLISH).startsWith("is")) {
                name = "is" + StringUtils.upperCaseCapitalize(property.getName());
            } else {
                name = property.getName();
            }
        } else {
            name = property.getName();
        }
        if (specificationDefinition != null) {
            return specificationDefinition.defaultColumnName(name);
        }
        return name;
    }

    /**
     * 获取或默认列
     *
     * @param property                属性
     * @param specificationDefinition 规范
     * @return
     */
    public static Column getOrDefaultColumn(BeanProperty property, SpecificationDefinition specificationDefinition) {
        Column column = property.getAnnotation(Column.class);
        if (column == null) {
            column = new Column() {
                @Override
                public Class<? extends Annotation> annotationType() {
                    return Column.class;
                }

                @Override
                public String name() {
                    return getOrDefaultColumnName(property, specificationDefinition);
                }

                @Override
                public boolean unique() {
                    return false;
                }

                @Override
                public boolean nullable() {
                    return true;
                }

                @Override
                public boolean insertable() {
                    return true;
                }

                @Override
                public boolean updatable() {
                    return true;
                }

                @Override
                public String columnDefinition() {
                    return null;
                }

                @Override
                public String table() {
                    return null;
                }

                @Override
                public int length() {
                    return 0;
                }

                @Override
                public int precision() {
                    return 0;
                }

                @Override
                public int scale() {
                    return 0;
                }
            };
        }
        return column;
    }

    /**
     * 获取列排序方式
     *
     * @param property 属性
     * @return
     */
    public static String getColumnOrderBy(BeanProperty property) {
        OrderBy orderBy = property.getAnnotation(OrderBy.class);
        if (orderBy != null) {
            return orderBy.value();
        }
        return "";
    }

    /**
     * 获取列的序列名称
     *
     * @param property 属性
     * @return
     */
    public static String getColumnSequenceName(BeanProperty property) {
        SequenceGenerator generator = property.getAnnotation(SequenceGenerator.class);
        if (generator != null) {
            return generator.sequenceName();
        }
        return "";
    }

    /**
     * 注册实体主键生成类型
     *
     * @param generationTypeMap 生成类型Map
     */
    public static void registerEntityKeyGenerationType(Map<Class<?>, GenerationType> generationTypeMap) {
        if (generationTypeMap != null) {
            ENTITY_KEY_GENERATION_TYPE_MAP.putAll(generationTypeMap);
        }
    }

    /**
     * 获取主键列生成方式
     *
     * @param property 属性
     * @return
     */
    public static GenerationType getKeyColumnGenerationType(BeanProperty property) {
        if (TypeUtils.isIntegerType(property.getType())) {
            GeneratedValue generatedValue = property.getAnnotation(GeneratedValue.class);
            if (generatedValue != null && generatedValue.strategy() != null) {
                return generatedValue.strategy();
            }
        }
        return GenerationType.AUTO;
    }

    /**
     * 获取主键列生成方式
     *
     * @param entityClass 实体类型Key
     * @param property    属性
     * @return
     */
    public static GenerationType getKeyColumnGenerationType(Class<?> entityClass, BeanProperty property) {
        GenerationType type = ENTITY_KEY_GENERATION_TYPE_MAP.get(entityClass);
        if (type != null) {
            return type;
        }
        return getKeyColumnGenerationType(property);
    }
}
