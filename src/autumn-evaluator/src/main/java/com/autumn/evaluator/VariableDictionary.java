package com.autumn.evaluator;

//
// * 主要应用于变量上下文接口的提供应用，在本引擎中没有被使用，仅供外部参考使用
// * 不区分键的大小写(线程是安全)
//

import com.autumn.exception.ArgumentNullException;
import com.autumn.exception.ExceptionUtils;
import com.autumn.util.CollectionUtils;
import com.autumn.util.StringUtils;
import com.autumn.util.TypeUtils;
import com.autumn.util.reflect.BeanProperty;
import com.autumn.util.reflect.ReflectUtils;
import org.apache.commons.collections.map.CaseInsensitiveMap;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 变量字典(供IContext接口使用)
 *
 * @author ycg
 */
public class VariableDictionary implements Serializable {

    /**
     * 序列号
     */
    private static final long serialVersionUID = 2278019085871050588L;

    private Map<String, Variant> dictionary;
    private ReentrantLock lock = new ReentrantLock();

    /**
     * 实例化 VariableDictionary 类新实例
     */
    public VariableDictionary() {
        this.dictionary = CollectionUtils.newCaseInsensitiveMap();
    }

    /**
     * 实例化 VariableDictionary 类新实例
     *
     * @param capacity 元素数量
     */
    @SuppressWarnings("unchecked")
    public VariableDictionary(int capacity) {
        this.dictionary = new CaseInsensitiveMap(capacity);
    }

    /**
     * 将指定的键和值添加到字典中。
     *
     * @param name  名称
     * @param value 值
     */
    public final void add(String name, Variant value) {
        if (StringUtils.isNullOrBlank(name)) {
            throw new ArgumentNullException("name");
        }
        synchronized (this.getSyncRoot()) {
            this.dictionary.put(name.trim(), value);
        }
    }

    /**
     * 确定 VariableDictionary 是否包含指定的键。
     *
     * @param name 名称
     * @return
     */
    public final boolean containsKey(String name) {
        if (StringUtils.isNullOrBlank(name)) {
            throw new ArgumentNullException("name");
        }
        synchronized (this.getSyncRoot()) {
            return this.dictionary.containsKey(name.trim());
        }
    }

    /**
     * 获取包含 VariableDictionary 中的键的集合。
     */
    public final Collection<String> getKeys() {
        synchronized (this.getSyncRoot()) {
            return this.dictionary.keySet();
        }
    }

    /**
     * 从 VariableDictionary 中移除所指定的键的值。
     *
     * @param name 名称
     * @return
     */
    public final boolean remove(String name) {
        if (name == null || "".equals(name.trim())) {
            throw new ArgumentNullException("key");
        }
        synchronized (this.getSyncRoot()) {
            try {
                Object res = this.dictionary.remove(name.trim());
                if (res == null) {
                    return false;
                }
            } catch (Exception e) {
                return false;
            }

            return true;
        }
    }

    /**
     * 获取包含 VariableDictionary 中的值的集合。
     */
    public final Collection<Variant> getValues() {
        synchronized (this.getSyncRoot()) {
            return this.dictionary.values();
        }
    }

    /**
     * 获取或设置相关键
     *
     * @param name 名称
     * @return
     */
    public final Variant getItem(String name) {
        if (StringUtils.isNullOrBlank(name)) {
            throw new ArgumentNullException("name");
        }
        synchronized (this.getSyncRoot()) {
            return this.dictionary.get(name.trim());
        }
    }

    public final void setItem(String name, Variant value) {
        if (name == null || "".equals(name.trim())) {
            throw new ArgumentNullException("key");
        }
        synchronized (this.getSyncRoot()) {
            this.dictionary.put(name.trim(), value);
        }
    }

    /**
     * 清除集合
     */
    public final void clear() {
        synchronized (this.getSyncRoot()) {
            this.dictionary.clear();
        }
    }

    /**
     * 获取包含在 VariableDictionary 中的键/值对的数目。
     */
    public final int getCount() {
        synchronized (this.getSyncRoot()) {
            return this.dictionary.size();
        }
    }

    /**
     * 同步对象
     */
    public final Object getSyncRoot() {
        return this;
    }

    /**
     * 加锁
     */
    public final void lock() {
        lock.lock();
    }

    /**
     * 解锁
     */
    public final void unLock() {
        lock.unlock();
    }

    /**
     * 将对象转换为字典
     *
     * @param obj 类对象
     * @return
     */
    public static VariableDictionary toDictionary(Object obj) {
        VariableDictionary dictionary = new VariableDictionary();
        if (obj != null) {
            dictionary.load(obj);
        }
        return dictionary;
    }

    /**
     * 将类对象加载到字典中
     *
     * @param obj 对象
     */
    @SuppressWarnings("rawtypes")
    public final void load(Object obj) {
        if (obj != null) {
            Class<?> type = obj.getClass();
            if (TypeUtils.isBaseType(type)) {
                ExceptionUtils.throwValidationException("obj 必须是对象类型，不支持基本类型。");
            }
            if (obj instanceof Collection) {
                ExceptionUtils.throwValidationException("obj 必须是对象类型，不支持集合类型。");
            }
            if (type.isArray()) {
                ExceptionUtils.throwValidationException("obj 必须是对象类型，不支持数组类型。");
            }
            if (obj instanceof Map) {
                Map itemDic = (Map) obj;
                boolean isKey = false;
                Class<?> valueType = null;
                boolean isBaseType = false;
                for (Object item : itemDic.entrySet()) {
                    if (!isKey) {
                        if (!((Map.Entry) item).getKey().getClass().equals(String.class)) {
                            throw new IllegalArgumentException("指定  Map 类型的键类型必须是 String 类型。");
                        }
                        isKey = true;
                    }
                    String key = ((Map.Entry) item).getKey().toString();
                    if (((Map.Entry) item).getValue() != null) {
                        if (valueType == null) {
                            valueType = ((Map.Entry) item).getValue().getClass();
                            isBaseType = Variant.isBasicType(valueType);
                        }
                        if (isBaseType) {
                            this.setItem(key, new Variant(((Map.Entry) item).getValue()));
                        } else {
                            if (((Map.Entry) item).getValue() instanceof Iterable) {
                                // 用迭代对象的第一项来获取泛型参数类型
                                Iterable mapValue = (Iterable) ((Map.Entry) item).getValue();
                                Type iterableItemType = null;
                                for (Object o : mapValue) {
                                    if (iterableItemType == null) {
                                        iterableItemType = o.getClass();
                                    } else {
                                        break;
                                    }
                                }
                                this.setItem(key,
                                        Variant.toVariant(iterableItemType, null, valueType,
                                                ((((Map.Entry) item).getValue() instanceof Iterable)
                                                        ? (Iterable) ((Map.Entry) item).getValue()
                                                        : null),
                                                false));
                            } else {
                                Collection<BeanProperty> fields = ReflectUtils.getBeanPropertyMap(valueType).values();
                                Map<String, Variant> subItems = toVariants(fields, ((Map.Entry) item).getValue());
                                for (Map.Entry<String, Variant> subItem : subItems.entrySet()) {
                                    this.setItem(key + "." + subItem.getKey(), subItem.getValue());
                                }
                            }
                        }
                    } else {
                        this.setItem(key, new Variant());
                    }
                }
            } else {
                Collection<BeanProperty> fields = ReflectUtils.getBeanPropertyMap(type).values();
                Map<String, Variant> subItems = toVariants(fields, obj);
                for (Map.Entry<String, Variant> subItem : subItems.entrySet()) {
                    this.setItem(subItem.getKey(), subItem.getValue());
                }
            }
        }
    }

    /**
     * 将 object 对象集合 转换为 Variant 数组
     *
     * @param properties
     * @param obj
     * @return
     */
    @SuppressWarnings("rawtypes")
    public static Map<String, Variant> toVariants(Collection<BeanProperty> properties, Object obj) {
        Map<String, Variant> items = CollectionUtils.newHashMap();
        if (properties == null && obj != null) {
            properties = ReflectUtils.getBeanPropertyMap(obj.getClass()).values();
        }
        if (properties != null) {
            for (BeanProperty p : properties) {
                if (p.canRead()) {
                    if (obj != null) {
                        Object value = p.getValue(obj);
                        if (Variant.isBasicType(p.getType())) {
                            if (value == null && p.getType() == String.class) {
                                items.put(p.getName(), new Variant(""));
                            } else {
                                items.put(p.getName(), new Variant(value));
                            }
                        } else {
                            if (value instanceof Iterable) {
                                Variant v = Variant.toVariant(null, p.getReadMethod().getGenericReturnType(), p.getType(),
                                        (Iterable) value, false);
                                items.put(p.getName(), v);
                            } else {
                                Collection<BeanProperty> fields = ReflectUtils.getBeanPropertyMap(p.getType()).values();
                                Map<String, Variant> subItems = toVariants(fields, value);
                                for (Map.Entry<String, Variant> item : subItems.entrySet()) {
                                    items.put(p.getName() + "." + item.getKey(), item.getValue());
                                }
                            }
                        }
                    } else {
                        items.put(p.getName(), new Variant());
                    }
                }
            }
        }
        return items;
    }
}