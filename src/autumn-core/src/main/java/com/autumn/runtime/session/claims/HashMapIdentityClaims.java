package com.autumn.runtime.session.claims;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.autumn.util.DateUtils;

/**
 * 基于 HashMap 的身份声明
 *
 * @author 老码农 2018-12-06 00:15:55
 */
public class HashMapIdentityClaims extends HashMap<String, Object> implements IdentityClaims {

    /**
     *
     */
    private static final long serialVersionUID = 4039792406747952299L;

    /**
     *
     */
    public HashMapIdentityClaims() {
        super(16);
    }

    /**
     * @param map
     */
    public HashMapIdentityClaims(Map<String, Object> map) {
        super(map);
    }

    @Override
    public final Object getClaims(String claimsName) {
        return this.get(claimsName);
    }

    @Override
    public final Long getLongClaims(String claimsName) {
        Object value = this.get(claimsName);
        if (value != null) {
            if (value instanceof Number) {
                return ((Number) value).longValue();
            }
            return Long.parseLong(value.toString());
        } else {
            return null;
        }
    }

    @Override
    public final Integer getIntegerClaims(String claimsName) {
        Object value = this.get(claimsName);
        if (value != null) {
            if (value instanceof Number) {
                return ((Number) value).intValue();
            }
            return Integer.parseInt(value.toString());
        } else {
            return null;
        }
    }

    @Override
    public final Short getShortClaims(String claimsName) {
        Object value = this.get(claimsName);
        if (value != null) {
            if (value instanceof Number) {
                return ((Number) value).shortValue();
            }
            return Short.parseShort(value.toString());
        } else {
            return null;
        }
    }

    @Override
    public final Double getDoubleClaims(String claimsName) {
        Object value = this.get(claimsName);
        if (value != null) {
            if (value instanceof Number) {
                return ((Number) value).doubleValue();
            }
            return Double.parseDouble(value.toString());
        } else {
            return null;
        }
    }

    @Override
    public final Float getFloatClaims(String claimsName) {
        Object value = this.get(claimsName);
        if (value != null) {
            if (value instanceof Number) {
                return ((Number) value).floatValue();
            }
            return Float.parseFloat(value.toString());
        } else {
            return null;
        }
    }

    @Override
    public final Boolean getBooleanClaims(String claimsName) {
        Object value = this.get(claimsName);
        if (value != null) {
            if (value instanceof Boolean) {
                return (Boolean) value;
            }
            return Boolean.parseBoolean(value.toString());
        } else {
            return null;
        }
    }

    @Override
    public final Date getDateClaims(String claimsName) {
        Object value = this.get(claimsName);
        if (value != null) {
            if (value instanceof Date || Date.class.isAssignableFrom(value.getClass())) {
                return (Date) value;
            }
            if (value instanceof Number) {
                new Date(((Number) value).longValue());
            }
            return DateUtils.parseDate(value.toString());
        } else {
            return null;
        }
    }

    @Override
    public final String getStringClaims(String claimsName) {
        Object value = this.get(claimsName);
        if (value != null) {
            return value.toString();
        } else {
            return null;
        }
    }
}
