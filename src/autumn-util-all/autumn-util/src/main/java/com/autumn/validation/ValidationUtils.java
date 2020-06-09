package com.autumn.validation;

import com.autumn.exception.ExceptionUtils;
import com.autumn.util.TypeUtils;
import com.autumn.util.reflect.BeanProperty;
import com.autumn.util.reflect.ReflectUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.validation.*;
import java.lang.reflect.Array;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 验证帮助
 *
 * @author 老码农
 * <p>
 * 2018-01-11 16:54:23
 */
public class ValidationUtils {

    /**
     * 日志
     */
    private static final Log logger = LogFactory.getLog(ValidationUtils.class);

    private static final Validator VALIDATOR;

    static {
        Validator validator;
        try {
            Configuration<?> config = Validation.byDefaultProvider().configure();
            ValidatorFactory factory = config.buildValidatorFactory();
            validator = factory.getValidator();
        } catch (Exception err) {
            validator = null;
            logger.error(err.getMessage(), err);
        }
        VALIDATOR = validator;
    }

    /**
     * 验证对象
     *
     * @param obj
     * @return
     */
    public static void validation(Object obj) {
        ExceptionUtils.checkNotNull(obj, "obj");
        if (VALIDATOR == null) {
            logger.error("初始化 ValidatorFactory 失败，无法调用验证。");
            return;
        }
        Set<Object> objSet = new HashSet<>();
        try {
            validation(obj, objSet);
        } finally {
            objSet.clear();
        }
    }

    /**
     * 对象验证
     *
     * @param obj    对象
     * @param objSet 对象集
     * @param <T>    类型
     */
    private static <T> void validation(T obj, Set<Object> objSet) {
        Class<?> type = obj.getClass();
        if (TypeUtils.isBaseType(type)) {
            return;
        }
        if (obj instanceof Collection) {
            collectionValidation((Collection) obj, objSet);
        }
        if (obj instanceof Map) {
            mapValidation((Map) obj, objSet);
        }
        if (obj.getClass().isArray()) {
            arrayValidation(obj, objSet);
        }
        //防止数组或集合内含重复的对象造成无限递归
        if (objSet.add(obj)) {
            Set<ConstraintViolation<T>> violations = VALIDATOR.validate(obj);
            if (violations.size() > 0) {
                violations.forEach(s -> ExceptionUtils.throwValidationException(s.getMessage()));
            }
            Collection<BeanProperty> fields = ReflectUtils.getBeanPropertyMap(type).values();
            for (BeanProperty field : fields) {
                if (!field.isBaseType()) {
                    Object value = field.getValue(obj);
                    if (value != null) {
                        validation(value, objSet);
                    }
                }
            }
        }
    }

    /**
     * 对象验证
     *
     * @param array 数组
     */
    private static void arrayValidation(Object array, Set<Object> objSet) {
        int size = Array.getLength(array);
        for (int i = 0; i < size; i++) {
            Object obj = Array.get(array, i);
            if (obj == null) {
                ExceptionUtils.throwValidationException("数组中不能包含null对象。");
            }
            validation(obj, objSet);
        }
    }

    /**
     * 对象验证
     *
     * @param items 对象集合
     */
    private static void collectionValidation(Collection items, Set<Object> objSet) {
        boolean valid = false;
        for (Object obj : items) {
            if (obj == null) {
                ExceptionUtils.throwValidationException("集合中不能包含null对象。");
            }
            validation(obj, objSet);
        }
    }

    /**
     * Map验证
     *
     * @param map map集合
     */
    private static void mapValidation(Map map, Set<Object> objSet) {
        for (Object key : map.keySet()) {
            if (key == null) {
                ExceptionUtils.throwValidationException("Map 的 key 包含null对象。");
            }
            Object value = map.get(key);
            if (value == null) {
                ExceptionUtils.throwValidationException("Map 的 value 包含null对象。");
            }
            validation(key, objSet);
            validation(value, objSet);
        }
    }


}
