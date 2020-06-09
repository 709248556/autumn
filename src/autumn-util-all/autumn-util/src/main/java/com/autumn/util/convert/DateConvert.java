package com.autumn.util.convert;

import com.autumn.util.DateUtils;
import com.autumn.util.StringUtils;

import java.time.*;
import java.util.Date;


/**
 * 日期类型转换
 *
 * @author 老码农
 * <p>
 * 2017-10-10 11:32:43
 */
public class DateConvert extends AbstractDataConvert<Date> {

    public DateConvert() {
        super(Date.class);
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
    public Date getDefaultValue() {
        return null;
    }


    @Override
    protected Date convert(Class<?> targetClass, Class<?> sourceClass, Object source) {
        if (sourceClass.equals(long.class)) {
            long value = (long) source;
            return new Date(value);
        }
        if (sourceClass.equals(Long.class)) {
            Long value = (Long) source;
            return new Date(value);
        }
        if (sourceClass.equals(LocalDate.class)) {
            LocalDate value = (LocalDate) source;
            return DateUtils.from(value);
        }
        if (sourceClass.equals(LocalDateTime.class)) {
            LocalDateTime value = (LocalDateTime) source;
            return DateUtils.from(value);
        }
        if (sourceClass.equals(LocalTime.class)) {
            LocalTime value = (LocalTime) source;
            LocalDateTime localDateTime = LocalDateTime.of(LocalDate.now(), value);
            return DateUtils.from(localDateTime);
        }
        if (Date.class.isAssignableFrom(sourceClass)) {
            Date value = (Date) source;
            return new Date(value.getTime());
        }
        if (sourceClass.equals(String.class)) {
            String value = source.toString().trim();
            if (StringUtils.isNullOrBlank(value)) {
                return this.getDefaultValue();
            }
            return DateUtils.parseDate(value);
        }
        throw this.throwConvertException(sourceClass, source);
    }

}
