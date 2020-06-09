package com.autumn.util;

import com.autumn.exception.ExceptionUtils;
import org.apache.commons.collections.map.CaseInsensitiveMap;

import java.lang.reflect.Array;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * 集合帮助
 *
 * @author 老码农
 * <p>
 * 2017-10-27 15:40:42
 */
public class CollectionUtils {

    /**
     * 集合 initialCapacity 默认大小
     */
    public static final int COLLECTION_DEFAULT_INITIAL_CAPACITY = 16;

    /**
     * 实例化 默认 initialCapacity 为 16 的 List 新实例
     *
     * @param <E>
     * @return
     */
    public static <E> List<E> newArrayList() {
        return new ArrayList<>(COLLECTION_DEFAULT_INITIAL_CAPACITY);
    }

    /**
     * 实例化 默认 initialCapacity 为 16 的 Set 新实例
     *
     * @param <E>
     * @return
     */
    public static <E> Set<E> newHashSet() {
        return new HashSet<>(COLLECTION_DEFAULT_INITIAL_CAPACITY);
    }

    /**
     * 实例化 默认 initialCapacity 为 16 的 Map 新实例
     *
     * @param <K> 键
     * @param <V> 值
     * @return
     */
    public static <K, V> Map<K, V> newHashMap() {
        return new HashMap<>(COLLECTION_DEFAULT_INITIAL_CAPACITY);
    }

    /**
     * 实例化 默认 initialCapacity 为 16 的 Map 安全线程新实例
     *
     * @param <K> 键
     * @param <V> 值
     * @return
     */
    public static <K, V> Map<K, V> newConcurrentHashMap() {
        return new ConcurrentHashMap<>(COLLECTION_DEFAULT_INITIAL_CAPACITY);
    }

    /**
     * 实例化 默认 initialCapacity 为 16 不区分大小写的 Linked Map 新实例
     *
     * @param <V> 值
     * @return
     */
    public static <K, V> Map<K, V> newLinkedHashMap() {
        return new LinkedHashMap<>(COLLECTION_DEFAULT_INITIAL_CAPACITY);
    }

    /**
     * 实例化 默认 initialCapacity 为 16 不区分大小写的 Map 新实例
     *
     * @param <V> 值
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <V> Map<String, V> newCaseInsensitiveMap() {
        return new CaseInsensitiveMap(COLLECTION_DEFAULT_INITIAL_CAPACITY);
    }

    /**
     * 转为数组
     *
     * @param collection
     * @param componentType
     * @param <E>
     * @return
     */
    public static <E> E[] toArray(Collection<E> collection, Class<E> componentType) {
        return toArray(collection, componentType, null);
    }

    /**
     * 转为数组
     *
     * @param collection    集合
     * @param componentType 类型
     * @param predicate     条件
     * @return 返回新的数组
     */
    @SuppressWarnings("unchecked")
    public static <E> E[] toArray(Collection<E> collection, Class<E> componentType, Predicate<E> predicate) {
        ExceptionUtils.checkNotNull(componentType, "componentType");
        if (collection == null) {
            return null;
        }
        Collection<E> items = collection;
        if (predicate != null) {
            List<E> newItems = new ArrayList<>(items.size());
            for (E item : collection) {
                if (predicate.test(item)) {
                    newItems.add(item);
                }
            }
            items = newItems;
        }
        E[] array = (E[]) Array.newInstance(componentType, items.size());
        int i = 0;
        for (E item : items) {
            array[i] = item;
            i++;
        }
        return array;
    }

    /**
     * 计算集合大小
     *
     * @param collection 集合
     * @param predicate  条件
     * @return
     */
    public static <E> int count(Collection<E> collection, Predicate<E> predicate) {
        ExceptionUtils.checkNotNull(collection, "collection");
        ExceptionUtils.checkNotNull(predicate, "predicate");
        int count = 0;
        for (E item : collection) {
            if (predicate.test(item)) {
                count++;
            }
        }
        return count;
    }


    /**
     * 查找符合条件并返回新的集合
     *
     * @param collection 集合
     * @param predicate  条件
     * @return
     */
    public static <E> List<E> findCollection(Collection<E> collection, Predicate<E> predicate) {
        ExceptionUtils.checkNotNull(collection, "collection");
        ExceptionUtils.checkNotNull(predicate, "predicate");
        List<E> items = new ArrayList<>(collection.size());
        for (E e : collection) {
            if (predicate.test(e)) {
                items.add(e);
            }
        }
        return items;
    }

    /**
     * 所有元素是否都满足条件,若全部满足则
     *
     * @param collection 集合
     * @param predicate  条件
     * @return
     */
    public static <E> boolean all(Collection<E> collection, Predicate<E> predicate) {
        return count(collection, predicate) == collection.size();
    }

    /**
     * 是否有一个以上元素满足条件
     *
     * @param collection 集合
     * @param predicate  条件
     * @return
     */
    public static <E> boolean any(Collection<E> collection, Predicate<E> predicate) {
        ExceptionUtils.checkNotNull(collection, "collection");
        ExceptionUtils.checkNotNull(predicate, "predicate");
        for (E item : collection) {
            if (predicate.test(item)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 集合循环处理
     *
     * @param collection 集合
     * @param action     动作
     */
    public static <E> void forHandle(Collection<E> collection, Consumer<E> action) {
        if (collection == null) {
            return;
        }
        if (action == null) {
            return;
        }
        for (E item : collection) {
            action.accept(item);
        }
    }

    /**
     * 集合循环处理
     *
     * @param collection 集合
     * @param action     动作
     * @param predicate  条件
     */
    public static <E> void forHandle(Collection<E> collection, Consumer<E> action, Predicate<E> predicate) {
        if (collection == null) {
            return;
        }
        if (action == null) {
            return;
        }
        if (predicate != null) {
            for (E item : collection) {
                if (predicate.test(item)) {
                    action.accept(item);
                }
            }
        } else {
            for (E item : collection) {
                action.accept(item);
            }
        }
    }
}
