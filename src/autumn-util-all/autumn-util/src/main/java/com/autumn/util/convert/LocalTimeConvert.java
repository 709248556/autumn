package com.autumn.util.convert;

import com.autumn.util.DateUtils;
import com.autumn.util.StringUtils;

import java.time.*;
import java.util.Date;

/**
 * 本地时间
 *
 * @Description TODO
 * @Author 老码农
 * @Date 2019-10-07 12:33
 */
public class LocalTimeConvert extends AbstractDataConvert<LocalTime> {

    public LocalTimeConvert() {
        super(LocalTime.class);
    }

    @Override
    public LocalTime getDefaultValue() {
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
    protected LocalTime convert(Class<?> targetClass, Class<?> sourceClass, Object source) {
        if (sourceClass.equals(long.class)) {
            long value = (long) source;
            return LocalTime.ofNanoOfDay(value);
        }
        if (sourceClass.equals(Long.class)) {
            Long value = (Long) source;
            return LocalTime.ofNanoOfDay(value);
        }
        if (sourceClass.equals(Date.class)) {
            Date value = (Date) source;
            return DateUtils.toLocalDateTime(value).toLocalTime();
        }
        if (sourceClass.equals(LocalDate.class)) {
            LocalDate value = (LocalDate) source;
            return LocalDateTime.of(value.getYear(), value.getMonth(), value.getDayOfMonth(), 0, 0, 0)
                    .toLocalTime();
        }
        if (Date.class.isAssignableFrom(sourceClass)) {
            Date value = (Date) source;
            return DateUtils.toLocalDateTime(value).toLocalTime();
        }
        if (sourceClass.equals(String.class)) {
            String value = source.toString().trim();
            if (StringUtils.isNullOrBlank(value)) {
                return this.getDefaultValue();
            }
            return LocalTime.parse(value);
        }
        throw this.throwConvertException(sourceClass, source);
    }

}
