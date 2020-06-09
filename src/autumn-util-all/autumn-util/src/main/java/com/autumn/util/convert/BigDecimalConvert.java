package com.autumn.util.convert;

import java.math.BigDecimal;

import com.autumn.util.StringUtils;
import com.autumn.util.TypeUtils;

/**
 * @author 老码农
 * <p>
 * 2017-10-10 12:42:28
 */
public class BigDecimalConvert extends AbstractNumberConvert<BigDecimal> {

    /**
     *
     */
    public BigDecimalConvert() {
        super(BigDecimal.class);
    }

    @Override
    public BigDecimal getDefaultValue() {
        return null;
    }


    @Override
    protected BigDecimal convert(Class<?> targetClass, Class<?> sourceClass, Object source) {
        if (TypeUtils.isNumberType(sourceClass)) {
            if (TypeUtils.isIntegerType(sourceClass)) {
                return BigDecimal.valueOf(((Number) source).longValue());
            }
            return BigDecimal.valueOf(((Number) source).doubleValue());
        }
        if (sourceClass.equals(String.class)) {
            String value = source.toString().trim();
            if (StringUtils.isNullOrBlank(value)) {
                return this.getDefaultValue();
            }
            return new BigDecimal(value);
        }
        throw this.throwConvertException(sourceClass, source);
    }

}
