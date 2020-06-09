package com.autumn.util.convert;

import com.autumn.util.DateUtils;
import com.autumn.util.StringUtils;

import java.time.*;
import java.util.Date;

/**
 * 本地日期时间转换
 *
 * @Description TODO
 * @Author 老码农
 * @Date 2019-10-07 2:27
 */
public class LocalDateTimeConvert extends AbstractDataConvert<LocalDateTime> {

    public LocalDateTimeConvert() {
        super(LocalDateTime.class);
    }

    @Override
    public LocalDateTime getDefaultValue() {
        return null;
    }

    @Override
    public final boolean isBaseType() {
        return true;
    }

    @Override
    public final boolean isNumberType() {
        return false;
    }

    @Override
    protected LocalDateTime convert(Class<?> targetClass, Class<?> sourceClass, Object source) {
        if (sourceClass.equals(long.class)) {
            long value = (long) source;
            return LocalDateTime.ofInstant(Instant.ofEpochMilli(value), ZoneId.systemDefault());
        }
        if (sourceClass.equals(Long.class)) {
            Long value = (Long) source;
            return LocalDateTime.ofInstant(Instant.ofEpochMilli(value), ZoneId.systemDefault());
        }
        if (sourceClass.equals(Date.class)) {
            Date value = (Date) source;
            return DateUtils.toLocalDateTime(value);
        }
        if (sourceClass.equals(LocalDate.class)) {
            LocalDate value = (LocalDate) source;
            return LocalDateTime.of(value.getYear(), value.getMonth(), value.getDayOfMonth(), 0, 0, 0);
        }
        if (sourceClass.equals(LocalTime.class)) {
            LocalTime value = (LocalTime) source;
            return LocalDateTime.of(LocalDate.now(), value);
        }
        if (Date.class.isAssignableFrom(sourceClass)) {
            Date value = (Date) source;
            return DateUtils.toLocalDateTime(value);
        }
        if (sourceClass.equals(String.class)) {
            String value = source.toString().trim();
            if (StringUtils.isNullOrBlank(value)) {
                return this.getDefaultValue();
            }
            LocalDateTime localDateTime = DateUtils.parseLocalDateTime(value);
            if (localDateTime == null) {
                throw this.throwConvertException(sourceClass, source);
            }
            return localDateTime;
        }
        throw this.throwConvertException(sourceClass, source);
    }
}
