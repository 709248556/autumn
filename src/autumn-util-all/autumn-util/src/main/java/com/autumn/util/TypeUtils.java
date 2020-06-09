package com.autumn.util;

import com.alibaba.fastjson.util.GenericArrayTypeImpl;
import com.alibaba.fastjson.util.ParameterizedTypeImpl;
import com.autumn.annotation.FriendlyProperty;
import com.autumn.exception.ExceptionUtils;
import com.autumn.exception.SystemException;
import com.autumn.util.convert.AbstractDataConvert;
import com.autumn.util.convert.DataConvert;
import com.autumn.util.reflect.BeanProperty;
import com.autumn.util.reflect.ReflectUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 类型帮助
 *
 * @author 老码农
 * <p>
 * 2017-09-29 14:28:59
 */
public class TypeUtils {

    private static final Map<Class<?>, Integer> BASE_TYPE_MAP = new HashMap<>(50);
    private static final Map<Class<?>, Map<BeanProperty, String>> PROPERTY_FRIENDLY_MAP = new ConcurrentHashMap<>(300);

    static {
        /*
         * 0表示通用，数字>=1,1=表示整数,2=浮点数，11=长整数，12=长实数
         */
        BASE_TYPE_MAP.put(String.class, 0);
        BASE_TYPE_MAP.put(Time.class, 0);
        BASE_TYPE_MAP.put(TimeSpan.class, 0);
        BASE_TYPE_MAP.put(Date.class, 0);
        BASE_TYPE_MAP.put(LocalDate.class, 0);
        BASE_TYPE_MAP.put(LocalDateTime.class, 0);
        BASE_TYPE_MAP.put(LocalTime.class, 0);
        BASE_TYPE_MAP.put(OffsetDateTime.class, 0);
        BASE_TYPE_MAP.put(Byte.TYPE, 1);
        BASE_TYPE_MAP.put(Byte.class, 1);
        BASE_TYPE_MAP.put(Short.TYPE, 1);
        BASE_TYPE_MAP.put(Short.class, 1);
        BASE_TYPE_MAP.put(Character.TYPE, 0);
        BASE_TYPE_MAP.put(Character.class, 0);
        BASE_TYPE_MAP.put(Integer.TYPE, 1);
        BASE_TYPE_MAP.put(Integer.class, 1);
        BASE_TYPE_MAP.put(Long.TYPE, 1);
        BASE_TYPE_MAP.put(Long.class, 1);
        BASE_TYPE_MAP.put(Float.TYPE, 2);
        BASE_TYPE_MAP.put(Float.class, 2);
        BASE_TYPE_MAP.put(Double.TYPE, 2);
        BASE_TYPE_MAP.put(Double.class, 2);
        BASE_TYPE_MAP.put(Boolean.TYPE, 0);
        BASE_TYPE_MAP.put(Boolean.class, 0);
        BASE_TYPE_MAP.put(java.sql.Time.class, 0);
        BASE_TYPE_MAP.put(java.sql.Date.class, 0);
        BASE_TYPE_MAP.put(java.sql.Timestamp.class, 0);
        BASE_TYPE_MAP.put(BigInteger.class, 11);
        BASE_TYPE_MAP.put(BigDecimal.class, 12);
        BASE_TYPE_MAP.put(Enum.class, 0);
    }

    /**
     * 是否是基础类型
     *
     * @param type 类型
     * @return
     * @author 老码农 2017-09-29 14:36:38
     */
    public static boolean isBaseType(Class<?> type) {
        return BASE_TYPE_MAP.containsKey(type) || Enum.class.isAssignableFrom(type);
    }

    /**
     * 是否为数字类型
     *
     * @param type 类型
     * @return byte、Byte、short、Short、int、Integer、long、Long、float、Float、double、Double、BigInteger、BigDecimal
     * 则为 true, 否则为false
     * @author 老码农 2017-09-29 15:22:00
     */
    public static boolean isNumberType(Class<?> type) {
        return Number.class.isAssignableFrom(type);
    }

    /**
     * 是否为整数类型
     *
     * @param type 类型
     * @return byte、Byte、short、Short、int、Integer、long、Long 则为 true,否则为 false
     * @author 老码农 2017-09-29 15:22:00
     */
    public static boolean isIntegerType(Class<?> type) {
        Integer value = BASE_TYPE_MAP.get(type);
        if (value == null) {
            return false;
        }
        return value == 1;
    }

    /**
     * 是否为浮点数类型
     *
     * @param type 类型
     * @return float、Float、double、Double 为 true，否则为 false
     * @author 老码农 2017-09-29 15:22:00
     */
    public static boolean isFloatType(Class<?> type) {
        Integer value = BASE_TYPE_MAP.get(type);
        if (value == null) {
            return false;
        }
        return value == 2;
    }

    /**
     * 是否是二进制类型
     *
     * @param type 类型
     * @return
     * @author 老码农 2017-09-29 14:39:27
     */
    public static boolean isBinaryType(Class<?> type) {
        if (type == null || !type.isArray()) {
            return false;
        }
        return type.equals(byte[].class) || type.equals(Byte[].class);
    }

    /**
     * 是否是基础或二进制类型
     *
     * @param type 类型
     * @return
     * @author 老码农 2017-09-29 14:36:38
     */
    public static boolean isBaseOrBinaryType(Class<?> type) {
        return isBaseType(type) || isBinaryType(type);
    }

    /**
     * 判断类是否包含特定的注解
     *
     * @param fullClassName
     * @param annotationClass
     * @return
     * @throws ClassNotFoundException
     */
    public static boolean isAnnotationClass(String fullClassName, Class<? extends Annotation> annotationClass)
            throws ClassNotFoundException {
        if (fullClassName == null || fullClassName.trim().length() == 0) {
            throw new NullPointerException("fullClassName 为 null 或空字符串");
        }
        return isAnnotationClass(Class.forName(fullClassName), annotationClass);
    }

    /**
     * 判断类是否包含特定的注解
     *
     * @param classType
     * @param annotationClass
     * @return
     */
    public static boolean isAnnotationClass(Class<?> classType, Class<? extends Annotation> annotationClass) {
        if (classType == null) {
            throw new NullPointerException("classType 为 null ");
        }
        if (annotationClass == null) {
            throw new NullPointerException("annotationClass 为 null");
        }
        return classType.getAnnotation(annotationClass) != null;
    }

    /**
     * 对象转换
     *
     * @param targetClass 目标类型
     * @param source      源
     * @return
     * @author 老码农 2017-09-30 17:05:13
     */
    public static Object toObjectConvert(Class<?> targetClass, Object source) {
        if (targetClass == null) {
            return source;
        }
        if (source != null) {
            if (targetClass.isInstance(source)) {
                return source;
            }
            Class<?> sourceClass = source.getClass();
            if (EqualsUtils.equalsPrimitiveClass(targetClass, sourceClass)) {
                return source;
            }
        }
        DataConvert dataConvert = AbstractDataConvert.getDataConvert(targetClass);
        if (dataConvert == null) {
            return source;
        }
        return dataConvert.convert(targetClass, source);
    }

    /**
     * 对象转换
     *
     * @param targetClass 目标类型
     * @param source      源
     * @return
     * @author 老码农 2017-09-30 17:05:13
     */
    @SuppressWarnings("unchecked")
    public static <T> T toConvert(Class<T> targetClass, Object source) {
        return (T) toObjectConvert(targetClass, source);
    }

    /**
     * 是否支持转换
     *
     * @param targetClass 目标类型
     * @return
     * @author 老码农 2017-09-30 17:11:22
     */
    public static Object isSupportConvert(Class<?> targetClass) {
        return AbstractDataConvert.getDataConvert(targetClass) != null || Enum.class.isAssignableFrom(targetClass);
    }

    /**
     * 是否是接口类型
     *
     * @param type 类型
     * @return
     */
    public static boolean isInterface(Class<?> type) {
        int mod = type.getModifiers();
        return Modifier.isInterface(mod);
    }

    /**
     * 是否是抽象类型
     *
     * @param type 类型
     * @return
     */
    public static boolean isAbstract(Class<?> type) {
        int mod = type.getModifiers();
        return Modifier.isAbstract(mod);
    }

    /**
     * 是否是公用类型
     *
     * @param type 类型
     * @return
     */
    public static boolean isPublic(Class<?> type) {
        int mod = type.getModifiers();
        return Modifier.isPublic(mod);
    }

    /**
     * 是否拥有公用的默认构造
     *
     * @param type 类型
     * @return
     */
    public static boolean isDefaultPublicConstructor(Class<?> type) {
        Constructor<?>[] conArray;
        try {
            conArray = type.getConstructors();
            for (Constructor<?> constructor : conArray) {
                if (constructor.getParameterTypes().length == 0) {
                    return true;
                }
            }
            return false;
        } catch (SecurityException e) {
            return false;
        }
    }

    /**
     * 是否是私有类型
     *
     * @param type 类型
     * @return
     */
    public static boolean isPrivate(Class<?> type) {
        int mod = type.getModifiers();
        return Modifier.isPrivate(mod);
    }

    /**
     * 是否是 protected 类型
     *
     * @param type 类型
     * @return
     */
    public static boolean isProtected(Class<?> type) {
        int mod = type.getModifiers();
        return Modifier.isProtected(mod);
    }

    /**
     * 是否是 final 类型(不可继承)
     *
     * @param type 类型
     * @return
     */
    public static boolean isFinal(Class<?> type) {
        int mod = type.getModifiers();
        return Modifier.isFinal(mod);
    }

    /**
     * 是否是静态类型
     *
     * @param type 类型
     * @return
     */
    public static boolean isStatic(Class<?> type) {
        int mod = type.getModifiers();
        return Modifier.isStatic(mod);
    }

    /**
     * 加载类名称
     *
     * @param className 类名称
     * @return 不存在则返回 null
     */
    public static Class<?> forName(String className) {
        ExceptionUtils.checkNotNull(className, "className");
        try {
            return Class.forName(className);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 是否存在类名称
     *
     * @param className 类名称类名称
     * @return
     */
    public static boolean isExistClassName(String className) {
        return forName(className) != null;
    }

    /**
     * 获取所有接口
     *
     * @param type
     * @return
     */
    public static Set<Class<?>> getInterfaces(Class<?> type) {
        ExceptionUtils.checkNotNull(type, "type");
        Set<Class<?>> types = new HashSet<>();
        Class<?>[] interfaces = type.getInterfaces();
        for (Class<?> interfaceType : interfaces) {
            types.add(interfaceType);
            types.addAll(getInterfaces(interfaceType));
        }
        return types;
    }

    /**
     * 比较两个类型是否完成相同
     *
     * @param leftTypes  右边类型集合
     * @param rigthTypes 右边类型集合
     * @return
     */
    public static boolean equalsTypes(Class<?>[] leftTypes, Class<?>[] rigthTypes) {
        if (leftTypes == null) {
            leftTypes = new Class<?>[0];
        }
        if (rigthTypes == null) {
            rigthTypes = new Class<?>[0];
        }
        if (leftTypes.length != rigthTypes.length) {
            return false;
        }
        for (int i = 0; i < leftTypes.length; i++) {
            if (!leftTypes[i].equals(rigthTypes[i])) {
                return false;
            }
        }
        return true;
    }

    /**
     * 获取属性友好Map
     * <p>
     * value 友好名称
     * </p>
     *
     * @param beanClass bean类型
     * @return
     */
    public static Map<BeanProperty, String> getPropertyFriendlyMap(Class<?> beanClass) {
        return PROPERTY_FRIENDLY_MAP.computeIfAbsent(beanClass, type -> {
            Collection<BeanProperty> properties = ReflectUtils.getBeanPropertyMap(type).values();
            Map<BeanProperty, String> friendlyMap = new HashMap<>(properties.size());
            for (BeanProperty property : properties) {
                FriendlyProperty friendlyProperty = property.getAnnotation(FriendlyProperty.class);
                if (friendlyProperty != null) {
                    friendlyMap.put(property, friendlyProperty.value());
                }
            }
            return friendlyMap;
        });
    }

    /**
     * 获取
     *
     * @param type
     * @return
     */
    public static Class<?> getRawClass(Type type) {
        if (type instanceof Class<?>) {
            return (Class<?>) type;
        } else if (type instanceof ParameterizedType) {
            return getRawClass(((ParameterizedType) type).getRawType());
        } else {
            throw new SystemException("TODO");
        }
    }

    /**
     * 获取集合项目类型
     *
     * @param fieldType
     * @return
     */
    public static Type getCollectionItemType(Type fieldType) {
        if (fieldType instanceof ParameterizedType) {
            return getCollectionItemType((ParameterizedType) fieldType);
        }
        if (fieldType instanceof Class<?>) {
            return getCollectionItemType((Class<?>) fieldType);
        }
        return Object.class;
    }

    private static Type getCollectionItemType(Class<?> clazz) {
        return clazz.getName().startsWith("java.")
                ? Object.class
                : getCollectionItemType(getCollectionSuperType(clazz));
    }

    private static Type getCollectionItemType(ParameterizedType parameterizedType) {
        Type rawType = parameterizedType.getRawType();
        Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
        if (rawType == Collection.class) {
            return getWildcardTypeUpperBounds(actualTypeArguments[0]);
        }
        Class<?> rawClass = (Class<?>) rawType;
        Map<TypeVariable, Type> actualTypeMap = createActualTypeMap(rawClass.getTypeParameters(), actualTypeArguments);
        Type superType = getCollectionSuperType(rawClass);
        if (superType instanceof ParameterizedType) {
            Class<?> superClass = getRawClass(superType);
            Type[] superClassTypeParameters = ((ParameterizedType) superType).getActualTypeArguments();
            return superClassTypeParameters.length > 0
                    ? getCollectionItemType(makeParameterizedType(superClass, superClassTypeParameters, actualTypeMap))
                    : getCollectionItemType(superClass);
        }
        return getCollectionItemType((Class<?>) superType);
    }

    private static Type getCollectionSuperType(Class<?> clazz) {
        Type assignable = null;
        for (Type type : clazz.getGenericInterfaces()) {
            Class<?> rawClass = getRawClass(type);
            if (rawClass == Collection.class) {
                return type;
            }
            if (Collection.class.isAssignableFrom(rawClass)) {
                assignable = type;
            }
        }
        return assignable == null ? clazz.getGenericSuperclass() : assignable;
    }

    /**
     * 创建实现类型 Map
     *
     * @param typeParameters
     * @param actualTypeArguments
     * @return
     */
    private static Map<TypeVariable, Type> createActualTypeMap(TypeVariable[] typeParameters, Type[] actualTypeArguments) {
        int length = typeParameters.length;
        Map<TypeVariable, Type> actualTypeMap = new HashMap<TypeVariable, Type>(length);
        for (int i = 0; i < length; i++) {
            actualTypeMap.put(typeParameters[i], actualTypeArguments[i]);
        }
        return actualTypeMap;
    }

    /**
     * 定义的参数类型
     *
     * @param rawClass
     * @param typeParameters
     * @param actualTypeMap
     * @return
     */
    private static ParameterizedType makeParameterizedType(Class<?> rawClass, Type[] typeParameters, Map<TypeVariable, Type> actualTypeMap) {
        int length = typeParameters.length;
        Type[] actualTypeArguments = new Type[length];
        for (int i = 0; i < length; i++) {
            actualTypeArguments[i] = getActualType(typeParameters[i], actualTypeMap);
        }
        return new ParameterizedTypeImpl(actualTypeArguments, null, rawClass);
    }

    /**
     * 获取实际类型
     *
     * @param typeParameter
     * @param actualTypeMap
     * @return
     */
    private static Type getActualType(Type typeParameter, Map<TypeVariable, Type> actualTypeMap) {
        if (typeParameter instanceof TypeVariable) {
            return actualTypeMap.get(typeParameter);
        } else if (typeParameter instanceof ParameterizedType) {
            return makeParameterizedType(getRawClass(typeParameter), ((ParameterizedType) typeParameter).getActualTypeArguments(), actualTypeMap);
        } else if (typeParameter instanceof GenericArrayType) {
            return new GenericArrayTypeImpl(getActualType(((GenericArrayType) typeParameter).getGenericComponentType(), actualTypeMap));
        }
        return typeParameter;
    }

    private static Type getWildcardTypeUpperBounds(Type type) {
        if (type instanceof WildcardType) {
            WildcardType wildcardType = (WildcardType) type;
            Type[] upperBounds = wildcardType.getUpperBounds();
            return upperBounds.length > 0 ? upperBounds[0] : Object.class;
        }
        return type;
    }

    /**
     * 获取集合项目类型
     *
     * @param fieldType
     * @return
     */
    public static Class<?> getCollectionItemClass(Type fieldType) {
        if (fieldType instanceof ParameterizedType) {
            Class<?> itemClass;
            Type actualTypeArgument = ((ParameterizedType) fieldType).getActualTypeArguments()[0];
            if (actualTypeArgument instanceof WildcardType) {
                WildcardType wildcardType = (WildcardType) actualTypeArgument;
                Type[] upperBounds = wildcardType.getUpperBounds();
                if (upperBounds.length == 1) {
                    actualTypeArgument = upperBounds[0];
                }
            }
            if (actualTypeArgument instanceof Class) {
                itemClass = (Class<?>) actualTypeArgument;
                if (!Modifier.isPublic(itemClass.getModifiers())) {
                    throw new SystemException(fieldType.toString() + " 的实际类型为非 public 类型。");
                }
            } else {
                throw new SystemException(fieldType.toString() + " 无法取得实际类型。");
            }
            return itemClass;
        }
        return Object.class;
    }

    /**
     * 创建集合类型
     *
     * @param type
     * @return
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public static Collection createCollection(Type type) {
        Class<?> rawClass = getRawClass(type);
        Collection list;
        if (rawClass == AbstractCollection.class //
                || rawClass == Collection.class
                || rawClass == List.class) {
            list = new ArrayList();
        } else if (rawClass.isAssignableFrom(HashSet.class)) {
            list = new HashSet();
        } else if (rawClass.isAssignableFrom(LinkedHashSet.class) || rawClass == Set.class) {
            list = new LinkedHashSet();
        } else if (rawClass.isAssignableFrom(TreeSet.class)) {
            list = new TreeSet();
        } else if (rawClass.isAssignableFrom(ArrayList.class)) {
            list = new ArrayList();
        } else if (rawClass.isAssignableFrom(EnumSet.class)) {
            Type itemType;
            if (type instanceof ParameterizedType) {
                itemType = ((ParameterizedType) type).getActualTypeArguments()[0];
            } else {
                itemType = Object.class;
            }
            list = EnumSet.noneOf((Class<Enum>) itemType);
        } else if (rawClass.isAssignableFrom(Queue.class)) {
            list = new LinkedList();
        } else {
            try {
                list = (Collection) rawClass.newInstance();
            } catch (Exception e) {
                throw new SystemException("create instance error, class " + rawClass.getName());
            }
        }
        return list;
    }
}
