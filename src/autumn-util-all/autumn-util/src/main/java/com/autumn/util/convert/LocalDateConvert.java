package com.autumn.util.convert;

import com.autumn.util.DateUtils;
import com.autumn.util.StringUtils;

import java.time.*;
import java.util.Date;

/**
 * 本地日期转换
 *
 * @Description TODO
 * @Author 老码农
 * @Date 2019-10-06 23:19
 */
public class LocalDateConvert extends AbstractDataConvert<LocalDate> {

    public LocalDateConvert() {
        super(LocalDate.class);
    }

    @Override
    public LocalDate getDefaultValue() {
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
    protected LocalDate convert(Class<?> targetClass, Class<?> sourceClass, Object source) {
        if (sourceClass.equals(long.class)) {
            long value = (long) source;
            return LocalDateTime.ofInstant(Instant.ofEpochMilli(value), ZoneId.systemDefault()).toLocalDate();
        }
        if (sourceClass.equals(Long.class)) {
            Long value = (Long) source;
            return LocalDateTime.ofInstant(Instant.ofEpochMilli(value), ZoneId.systemDefault()).toLocalDate();
        }
        if (sourceClass.equals(Date.class)) {
            Date value = (Date) source;
            return DateUtils.toLocalDate(value);
        }
        if (sourceClass.equals(LocalDateTime.class)) {
            LocalDateTime value = (LocalDateTime) source;
            return value.toLocalDate();
        }
        if (sourceClass.equals(LocalTime.class)) {
            LocalTime value = (LocalTime) source;
            LocalDateTime localDateTime = LocalDateTime.of(LocalDate.now(), value);
            return localDateTime.toLocalDate();
        }
        if (Date.class.isAssignableFrom(sourceClass)) {
            Date value = (Date) source;
            return DateUtils.toLocalDate(value);
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
            return localDateTime.toLocalDate();
        }
        throw this.throwConvertException(sourceClass, source);
    }
}
