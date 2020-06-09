package com.autumn.audited;

import com.autumn.annotation.FriendlyProperty;
import com.autumn.audited.annotation.LogMessage;
import com.autumn.util.DateUtils;
import com.autumn.util.StringUtils;
import com.autumn.util.Time;
import com.autumn.util.TypeUtils;
import com.autumn.util.reflect.BeanProperty;
import com.autumn.util.reflect.ReflectUtils;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 审计日志帮助
 *
 * @Description TODO
 * @Author 老码农
 * @Date 2019-07-29 23:27
 */
public class AuditedUtils {

    private static final Map<Class<?>, Map<BeanProperty, LogMessagePropertyInfo>> OBJ_LOG_MAP = new ConcurrentHashMap<>();

    /**
     * 获取操作日志详情
     *
     * @param obj 对象
     * @return
     */
    public static String getOperationLogDetails(Object obj) {
        if (obj != null) {
            if (obj instanceof OperationLog) {
                OperationLog auditedLog = (OperationLog) obj;
                String msg = auditedLog.logMessage();
                if (StringUtils.isNullOrBlank(msg)) {
                    msg = getObjLogMessage(obj);
                }
                return msg;
            } else {
                Class<?> objClass = obj.getClass();
                if (!TypeUtils.isBaseType(objClass)) {
                    String msg = getObjLogMessage(obj);
                    return msg;
                }
                return toLogString(obj);
            }
        } else {
            return "";
        }
    }

    private static String getObjLogMessage(Object obj) {
        Map<BeanProperty, LogMessagePropertyInfo> map = getLogMessagePropertyMap(obj.getClass());
        if (map.size() > 0) {
            StringBuilder builder = new StringBuilder();
            int i = 0;
            for (Map.Entry<BeanProperty, LogMessagePropertyInfo> entry : map.entrySet()) {
                if (i > 0) {
                    builder.append(",");
                }
                Object value = entry.getKey().getValue(obj);
                builder.append(entry.getValue().getName());
                builder.append("=");
                if (value != null) {
                    builder.append(toLogString(value));
                }
                i++;
            }
            return builder.toString();
        }
        return "";
    }

    private static String toLogString(Object value) {
        if (value instanceof Date) {
            Date d = (Date) value;
            Time t = DateUtils.getTime(d);
            if (t.getHour() == 0 && t.getMinute() == 0 && t.getSecond() == 0) {
                return DateUtils.dateFormat(d, "yyyy-MM-dd");
            } else {
                return DateUtils.dateFormat(d, "yyyy-MM-dd HH:mm:ss");
            }
        } else if (value instanceof Boolean) {
            Boolean ok = (Boolean) value;
            return ok ? "是" : "否";
        }
        if (TypeUtils.isBaseType(value.getClass())) {
            return value.toString();
        }
        return getOperationLogDetails(value);
    }

    private static Map<BeanProperty, LogMessagePropertyInfo> getLogMessagePropertyMap(Class<?> objClass) {
        return OBJ_LOG_MAP.computeIfAbsent(objClass, beanClass -> {
            Collection<BeanProperty> propertys = ReflectUtils.getBeanPropertyMap(beanClass).values();
            List<LogMessagePropertyInfo> lst = new ArrayList<>(propertys.size());
            Map<LogMessagePropertyInfo, BeanProperty> hashMap = new HashMap<>(propertys.size());
            for (BeanProperty property : propertys) {
                LogMessage logMessage = property.getAnnotation(LogMessage.class);
                if (logMessage != null) {
                    String name = logMessage.name();
                    if (StringUtils.isNullOrBlank(name)) {
                        FriendlyProperty friendlyProperty = property.getAnnotation(FriendlyProperty.class);
                        if (friendlyProperty != null) {
                            name = friendlyProperty.value();
                        }
                    }
                    if (StringUtils.isNullOrBlank(name)) {
                        name = property.getName();
                    }
                    LogMessagePropertyInfo info = new LogMessagePropertyInfo(name, logMessage.order());
                    lst.add(info);
                    hashMap.put(info, property);
                }
            }
            Map<BeanProperty, LogMessagePropertyInfo> propertyMap = new LinkedHashMap<>(hashMap.size());
            if (lst.size() > 0) {
                lst.sort(Comparator.comparingInt(LogMessagePropertyInfo::getOrder));
                for (LogMessagePropertyInfo info : lst) {
                    propertyMap.put(hashMap.get(info), info);
                }
            }
            lst.clear();
            hashMap.clear();
            return propertyMap;
        });
    }

}
