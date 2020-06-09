package com.autumn.runtime.session;

import com.autumn.runtime.session.claims.DefaultIdentityClaims;
import com.autumn.runtime.session.claims.IdentityClaims;
import com.autumn.timing.Clock;

import java.io.Serializable;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 空会话实现
 *
 * @author 老码农
 * <p>
 * 2017-11-03 10:56:50
 */
public class NullAutumnSession extends AbstractAutumnSession implements AutumnSession {

    private final Set<String> roles = Collections.synchronizedSet(new HashSet<>(16));
    private final Set<String> rermissions = Collections.synchronizedSet(new HashSet<>(16));
    private final Map<Object, Object> attribute = new ConcurrentHashMap<>(16);

    private final Date startTime;

    /**
     *
     */
    public NullAutumnSession() {
        this.startTime = Clock.gmtNow();
    }

    /**
     * 获取实例
     */
    public static final AutumnSession INSTANCE = new NullAutumnSession();

    @Override
    public Serializable getId() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    @Override
    public Date getStartTime() {
        return this.startTime;
    }

    @Override
    public Date getLastAccessTime() {
        return Clock.gmtNow();
    }

    @Override
    public String getHost() {
        return "127.0.0.1";
    }

    @Override
    public Collection<Object> getAttributeKeys() {
        return this.attribute.keySet();
    }

    @Override
    public Object getAttribute(Object key) {
        return this.attribute.get(key);
    }

    @Override
    public void setAttribute(Object key, Object value) {
        this.attribute.put(key, value);
    }

    @Override
    public Object removeAttribute(Object key) {
        return this.attribute.remove(key);
    }

    @Override
    public Long getUserId() {
        return null;
    }

    @Override
    public String getUserName() {
        return null;
    }

    @Override
    public IdentityClaims getIdentityClaims() {
        return DefaultIdentityClaims.DEFAULT_IDENTITY_CLAIMS;
    }

    @Override
    public Set<String> getRoles() {
        return roles;
    }

    @Override
    public Set<String> getPermissions() {
        return rermissions;
    }

    @Override
    public void logout() {

    }

    @Override
    public void updateUserInfo() {

    }

    @Override
    public AutumnUser getUserInfo() {
        return null;
    }

    @Override
    public String getIdentityType() {
        return null;
    }
}
