package com.autumn.runtime.session.claims;

import com.autumn.exception.ExceptionUtils;

import java.util.*;

/**
 * 默认身份声明实现
 *
 * @author 老码农
 * <p>
 * Description
 * </p>
 * @date 2017-11-04 04:12:03
 */
public final class DefaultIdentityClaims implements IdentityClaims {

    /**
     *
     */
    private static final long serialVersionUID = -1713603062816255375L;

    private static final Map<String, Object> EMPTY_CLAIMS_MAP = Collections
            .unmodifiableMap(new HashMap<String, Object>());

    /**
     * 默认声明
     */
    public static final IdentityClaims DEFAULT_IDENTITY_CLAIMS = new DefaultIdentityClaims(EMPTY_CLAIMS_MAP);

    private final HashMapIdentityClaims claimsMap;

    /**
     * @param claimsMap
     */
    public DefaultIdentityClaims(Map<String, Object> claimsMap) {
        if (claimsMap == null) {
            this.claimsMap = new HashMapIdentityClaims();
        } else {
            this.claimsMap = new HashMapIdentityClaims(claimsMap);
        }
    }

    @Override
    public int size() {
        return claimsMap.size();
    }

    @Override
    public boolean isEmpty() {
        return claimsMap.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return claimsMap.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return claimsMap.containsValue(value);
    }

    @Override
    public Object get(Object key) {
        return claimsMap.get(key);
    }

    private final static String READ_ERROR_MESSAGE = "只读 Map 不支持的操作";

    @Override
    public Object put(String key, Object value) {
        throw ExceptionUtils.throwNotSupportException(READ_ERROR_MESSAGE);
    }

    @Override
    public Object remove(Object key) {
        throw ExceptionUtils.throwNotSupportException(READ_ERROR_MESSAGE);
    }

    @Override
    public void putAll(Map<? extends String, ? extends Object> m) {
        throw ExceptionUtils.throwNotSupportException(READ_ERROR_MESSAGE);
    }

    @Override
    public void clear() {
        throw ExceptionUtils.throwNotSupportException(READ_ERROR_MESSAGE);
    }

    @Override
    public Set<String> keySet() {
        return claimsMap.keySet();
    }

    @Override
    public Collection<Object> values() {
        return claimsMap.values();
    }

    @Override
    public Set<java.util.Map.Entry<String, Object>> entrySet() {
        return claimsMap.entrySet();
    }

    /**
     * 获取声明
     *
     * @param claimsName 声明名称
     * @return
     */
    @Override
    public final Object getClaims(String claimsName) {
        return this.claimsMap.get(claimsName);
    }

    /**
     * 获取 Long 声明
     *
     * @param claimsName 声明名称
     * @return
     */
    @Override
    public final Long getLongClaims(String claimsName) {
        return this.claimsMap.getLongClaims(claimsName);
    }

    /**
     * 获取 Integer 声明
     *
     * @param claimsName 声明名称
     * @return
     */
    @Override
    public final Integer getIntegerClaims(String claimsName) {
        return this.claimsMap.getIntegerClaims(claimsName);
    }

    /**
     * 获取 Short 声明
     *
     * @param claimsName 声明名称
     * @return
     */
    @Override
    public final Short getShortClaims(String claimsName) {
        return this.claimsMap.getShortClaims(claimsName);
    }

    /**
     * 获取 Double 声明
     *
     * @param claimsName 声明名称
     * @return
     */
    @Override
    public final Double getDoubleClaims(String claimsName) {
        return this.claimsMap.getDoubleClaims(claimsName);
    }

    /**
     * 获取 Double 声明
     *
     * @param claimsName 声明名称
     * @return
     */
    @Override
    public final Float getFloatClaims(String claimsName) {
        return this.claimsMap.getFloatClaims(claimsName);
    }

    /**
     * 获取 Boolean 声明
     *
     * @param claimsName 声明名称
     * @return
     */
    @Override
    public final Boolean getBooleanClaims(String claimsName) {
        return this.claimsMap.getBooleanClaims(claimsName);
    }

    /**
     * 获取 Date 声明
     *
     * @param claimsName 声明名称
     * @return
     */
    @Override
    public final Date getDateClaims(String claimsName) {
        return this.claimsMap.getDateClaims(claimsName);
    }

    /**
     * 获取 String 声明
     *
     * @param claimsName 声明名称
     * @return
     */
    @Override
    public final String getStringClaims(String claimsName) {
        return this.claimsMap.getStringClaims(claimsName);
    }
}
