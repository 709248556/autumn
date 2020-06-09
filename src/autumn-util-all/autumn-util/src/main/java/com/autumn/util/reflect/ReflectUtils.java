package com.autumn.util.reflect;

import com.autumn.exception.ExceptionUtils;
import com.autumn.exception.SystemException;
import com.autumn.util.StringUtils;
import com.autumn.util.TypeUtils;
import com.esotericsoftware.reflectasm.ConstructorAccess;
import com.esotericsoftware.reflectasm.FieldAccess;
import com.esotericsoftware.reflectasm.MethodAccess;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Predicate;

/**
 * 反射实用工具
 *
 * @author 老码农
 * <p>
 * 2017-09-28 14:41:03
 */
public class ReflectUtils {

    /**
     * 默认对象属性数量
     */
    public final static int DEFAULT_OBJECT_PROPERTY_INITIAL_CAPACITY = 50;

    /**
     * 属性 get 开头
     */
    public final static String PROPERTY_GET_NAME = "get";

    /**
     * 属性 is 开头
     */
    public final static String PROPERTY_IS_NAME = "is";

    /**
     * 属性 set 开头
     */
    public final static String PROPERTY_SET_NAME = "set";

    private final static Map<Class<?>, MethodAccess> METHOD_ACCESS_MAP = new ConcurrentHashMap<>();
    private final static Map<Class<?>, FieldAccess> FIELD_ACCESS_MAP = new ConcurrentHashMap<>();
    private final static Map<Class<?>, ConstructorAccess<?>> CONSTRUCTOR_ACCESS_MAP = new ConcurrentHashMap<>();
    private final static Map<Class<?>, Object> ENUM_TYPE_ARRAY_MAP = new ConcurrentHashMap<>();
    private final static Map<Class<?>, Map<String, BeanProperty>> BEAN_PROPERTY_MAP = new ConcurrentHashMap<>();

    /**
     * 获取bean属性集合（先从缓存读取，若为Null,则再执行查找，并做缓存处理）
     *
     * @param beanClass
     * @return
     */
    public static Map<String, BeanProperty> getBeanPropertyMap(Class<?> beanClass) {
        return BEAN_PROPERTY_MAP.computeIfAbsent(beanClass, t -> Collections.unmodifiableMap(findBeanPropertyMap(t)));
    }

    /**
     * 获取枚举类型数组
     *
     * @param enumType 枚举类型
     * @return
     */
    public static Object getEnumTypeArray(Class<?> enumType) {
        ExceptionUtils.checkNotNull(enumType, "enumType");
        Object values = ENUM_TYPE_ARRAY_MAP.get(enumType);
        if (values == null) {
            if (!Enum.class.isAssignableFrom(enumType)) {
                ExceptionUtils.throwSystemException("类型[" + enumType.getName() + "]不是 Enum 类型。");
            }
            try {
                Method method = enumType.getMethod("values");
                values = method.invoke(null);
            } catch (Exception e) {
                throw ExceptionUtils.throwSystemException(e.getMessage(), e);
            }
            ENUM_TYPE_ARRAY_MAP.put(enumType, values);
        }
        return values;
    }

    /**
     * 查找 Bean 的属性集合
     *
     * @param beanClass
     * @return
     * @author 老码农 2017-10-10 09:53:09
     */
    public static List<BeanProperty> findBeanPropertys(Class<?> beanClass) {
        ExceptionUtils.checkNotNull(beanClass, "type");
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(beanClass);
            List<Field> fields = findFields(beanClass);
            List<BeanProperty> propetys = new ArrayList<>(DEFAULT_OBJECT_PROPERTY_INITIAL_CAPACITY);
            if (beanInfo.getPropertyDescriptors() != null) {
                for (PropertyDescriptor propertyDescriptor : beanInfo.getPropertyDescriptors()) {
                    if (!"class".equals(propertyDescriptor.getName())) {
                        Field fieldProperty = null;
                        for (Field field : fields) {
                            if (field.getName().equals(propertyDescriptor.getName())) {
                                fieldProperty = field;
                                break;
                            }
                        }
                        propetys.add(new BeanProperty(beanClass, fieldProperty, propertyDescriptor));
                    }
                }
            }
            return propetys;
        } catch (IntrospectionException e) {
            throw new SystemException(e.getMessage(), e);
        }
    }

    /**
     * 查找 Bean 的属性 Map
     *
     * @param beanClass Bean 类型
     * @return
     */
    public static Map<String, BeanProperty> findBeanPropertyMap(Class<?> beanClass) {
        List<BeanProperty> propertys = findBeanPropertys(beanClass);
        Map<String, BeanProperty> map = new HashMap<>(propertys.size());
        for (BeanProperty beanProperty : propertys) {
            map.put(beanProperty.getName(), beanProperty);
        }
        propertys.clear();
        return map;
    }

    /**
     * 查找所有非静态的字段(包括基类)
     *
     * @param beanClass 类型
     * @param predicate 条件
     * @return 返回本类以及继承链上的字段集合
     */
    public static List<Field> findFields(Class<?> beanClass, Predicate<Field> predicate) {
        List<Field> fieldList = new ArrayList<>(DEFAULT_OBJECT_PROPERTY_INITIAL_CAPACITY);
        Field[] fields = beanClass.getDeclaredFields();
        for (Field field : fields) {
            int modifiers = field.getModifiers();
            if (!Modifier.isStatic(modifiers)) {
                if (predicate != null) {
                    if (predicate.test(field)) {
                        fieldList.add(field);
                    }
                } else {
                    fieldList.add(field);
                }
            }
        }
        Class<?> superclass = beanClass.getSuperclass();
        if (superclass != null && !superclass.equals(Object.class)) {
            fieldList.addAll(findFields(superclass, predicate));
        }
        return fieldList;
    }

    /**
     * 查找所有非静态的字段(包括基类)
     *
     * @param beanClass 类型
     * @return 返回本类以及继承链上的方法集合，并按照父级向下顺序返回
     */
    public static List<Field> findFields(Class<?> beanClass) {
        return findFields(beanClass, null);
    }

    /**
     * 查找属性的 Set 函数
     *
     * @param beanClass
     * @return 属性名为 key
     */
    public static Map<String, Method> findPropertiesSetMethodMap(Class<?> beanClass) {
        List<Method> methods = findMethods(beanClass);
        Map<String, Method> methodMap = new HashMap<>(DEFAULT_OBJECT_PROPERTY_INITIAL_CAPACITY);
        for (Method method : methods) {
            if (method.getName().length() > 3 && method.getReturnType().equals(void.class)
                    && method.getParameterCount() == 1 && method.getName().startsWith(PROPERTY_SET_NAME)) {
                String name = StringUtils.lowerCaseCapitalize(method.getName().substring(3));
                methodMap.put(name, method);
            }
        }
        methods.clear();
        return methodMap;
    }

    /**
     * 查找所有非静态的公有方法(包括基类)
     *
     * @param beanClass 类型
     * @return 返回本类以及继承链上的方法集合，并按照父级向下顺序返回
     */
    public static List<Method> findMethods(Class<?> beanClass) {
        List<Method> methodList = new ArrayList<>();
        Class<?> superclass = beanClass.getSuperclass();
        if (superclass != null && !superclass.equals(Object.class)) {
            List<Method> superMethodList = findMethods(superclass);
            methodList.addAll(superMethodList);
        }
        Method[] methods = beanClass.getDeclaredMethods();
        for (Method method : methods) {
            int modifiers = method.getModifiers();
            if (Modifier.isPublic(modifiers) && !Modifier.isStatic(modifiers)) {
                Iterator<Method> iter = methodList.iterator();
                boolean isAdd = true;
                int index = 0;
                while (iter.hasNext()) {
                    Method cruuentMethod = iter.next();
                    if (cruuentMethod.getName().equals(method.getName())
                            && TypeUtils.equalsTypes(cruuentMethod.getParameterTypes(), method.getParameterTypes())) {
                        isAdd = false;
                        methodList.set(index, method);
                        break;
                    }
                    index++;
                }
                if (isAdd) {
                    methodList.add(method);
                }
            }
        }
        return methodList;
    }

    /**
     * 查找非静态的公有方法
     *
     * @param beanClass      Bean 类型
     * @param name           名称
     * @param returnType     返回类型
     * @param parameterTypes 参数类型
     * @return
     */
    public static Method findMethod(Class<?> beanClass, String name, Class<?> returnType, Class<?>... parameterTypes) {
        Method[] methods = beanClass.getDeclaredMethods();
        for (Method method : methods) {
            int modifiers = method.getModifiers();
            boolean status = Modifier.isPublic(modifiers) && !Modifier.isStatic(modifiers);
            boolean exist = method.getName().equals(name) && method.getReturnType().equals(returnType)
                    && TypeUtils.equalsTypes(parameterTypes, method.getParameterTypes());
            if (status && exist) {
                return method;
            }
        }
        Class<?> superclass = beanClass.getSuperclass();
        if (superclass != null && !superclass.equals(Object.class)) {
            return findMethod(superclass, name, returnType, parameterTypes);
        }
        return null;
    }

    /**
     * 获取方法访问
     *
     * @param type
     * @return
     * @author 老码农 2017-09-28 14:43:38
     */
    public static MethodAccess getMethodAccess(Class<?> type) {
        ExceptionUtils.checkNotNull(type, "type");
        return METHOD_ACCESS_MAP.computeIfAbsent(type, MethodAccess::get);
    }

    /**
     * 获取字段访问
     *
     * @param type
     * @return
     * @author 老码农 2017-09-28 14:43:38
     */
    public static FieldAccess getFieldAccess(Class<?> type) {
        ExceptionUtils.checkNotNull(type, "type");
        return FIELD_ACCESS_MAP.computeIfAbsent(type, FieldAccess::get);
    }

    /**
     * 获取构造访问
     *
     * @param type
     * @return
     * @author 老码农 2017-09-28 14:43:38
     */
    @SuppressWarnings("unchecked")
    public static <T> ConstructorAccess<T> getConstructorAccess(Class<T> type) {
        ExceptionUtils.checkNotNull(type, "type");
        return (ConstructorAccess<T>) CONSTRUCTOR_ACCESS_MAP.computeIfAbsent(type, ConstructorAccess::get);
    }

    /**
     * 获取泛型实际参数类型,若多继承的情况下，必须按顺序继承，增加时必须在最后一个才准确
     *
     * @param currentClass 当前类
     * @param rootClass    根类
     * @return
     */
    public static List<Class<?>> getGenericActualArgumentsType(Class<?> currentClass, Class<?> rootClass) {
        List<List<Class<?>>> classItems = new ArrayList<>();
        Class<?> nextClass = currentClass;
        do {
            Type type = nextClass.getGenericSuperclass();
            if (type instanceof ParameterizedType) {
                ParameterizedType parType = (ParameterizedType) type;
                Type[] types = parType.getActualTypeArguments();
                List<Class<?>> classList = new ArrayList<>();
                for (Type t : types) {
                    if (t instanceof Class<?>) {
                        classList.add((Class<?>) t);
                    }
                }
                if (classList.size() > 0) {
                    classItems.add(classList);
                }
            }
            nextClass = nextClass.getSuperclass();
        } while (!nextClass.equals(Object.class) && !nextClass.equals(rootClass));
        List<Class<?>> items = new ArrayList<>();
        for (int i = classItems.size() - 1; i >= 0; i--) {
            items.addAll(classItems.get(i));
        }
        classItems.clear();
        return items;
    }

    /**
     * 获取泛型参实际参数类型Map(注意，若整个链条若出现参数名重复，则取直接上级)
     *
     * @param beanClass
     * @return Key = 泛型参数名称,Value 实际类型
     */
    public static Map<String, Class<?>> getGenericActualArgumentsTypeMap(Class<?> beanClass) {
        Map<String, Class<?>> typeMap = new HashMap<>(DEFAULT_OBJECT_PROPERTY_INITIAL_CAPACITY);
        Class<?> nextClass = beanClass;
        do {
            Type type = nextClass.getGenericSuperclass();
            setGenericActualArgumentsType(type, typeMap);
            nextClass = nextClass.getSuperclass();
        } while (!nextClass.equals(Object.class));
        Type[] genTypes = beanClass.getGenericInterfaces();
        for (Type genType : genTypes) {
            setGenericActualArgumentsType(genType, typeMap);
        }
        return typeMap;
    }

    /**
     * @param type
     * @param typeMap
     */
    private static void setGenericActualArgumentsType(Type type, Map<String, Class<?>> typeMap) {
        if (type instanceof ParameterizedType) {
            ParameterizedType parType = (ParameterizedType) type;
            if (parType.getRawType() instanceof Class<?>) {
                Class<?> rawClass = (Class<?>) parType.getRawType();
                TypeVariable<?>[] typeVars = rawClass.getTypeParameters();
                Type[] types = parType.getActualTypeArguments();
                for (int i = 0; i < types.length; i++) {
                    Type t = types[i];
                    if (t instanceof Class<?>) {
                        TypeVariable<?> typeVar = typeVars[i];
                        if (!typeMap.containsKey(typeVar.getName())) {
                            typeMap.put(typeVar.getName(), (Class<?>) t);
                        }
                    } else {
                        if (t instanceof ParameterizedType) {
                            setGenericActualArgumentsType(t, typeMap);
                        }
                    }
                }
            }
        }
    }

}
