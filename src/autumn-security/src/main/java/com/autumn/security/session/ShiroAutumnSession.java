package com.autumn.security.session;

import com.autumn.exception.ExceptionUtils;
import com.autumn.runtime.session.AbstractAutumnSession;
import com.autumn.runtime.session.AutumnSession;
import com.autumn.runtime.session.AutumnUser;
import com.autumn.runtime.session.claims.IdentityClaims;
import com.autumn.security.AutumnNotLoginException;
import com.autumn.security.context.AutumnSecurityContextHolder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.Subject;

import java.io.Serializable;
import java.util.*;
import java.util.function.Function;

/**
 * 安全 AutumnSession 会话
 *
 * @author 老码农
 * <p>
 * Description
 * </p>
 * @date 2017-11-04 04:07:47
 */
public class ShiroAutumnSession extends AbstractAutumnSession implements AutumnSession {

    private static final Log LOG = LogFactory.getLog(ShiroAutumnSession.class);

    private static final Set<String> ROLES = Collections.synchronizedSet(Collections.unmodifiableSet(new HashSet<>(0)));
    private static final Set<String> RERMISSIONS = Collections.synchronizedSet(Collections.unmodifiableSet(new HashSet<>(0)));

    /**
     *
     */
    public ShiroAutumnSession() {

    }

    /**
     * 获取会话值
     *
     * @param fun
     * @param defaultValue
     * @return
     */
    protected <TValue> TValue getSessionValue(Function<Session, TValue> fun, TValue defaultValue) {
        Session session = AutumnSecurityContextHolder.getSession();
        if (session == null) {
            return defaultValue;
        }
        return fun.apply(session);
    }

    /**
     * 获取用户值
     *
     * @param fun
     * @param defaultValue
     * @return
     */
    protected <TValue> TValue getAutumnUserValue(Function<AutumnUser, TValue> fun, TValue defaultValue) {
        AutumnUser user = AutumnSecurityContextHolder.getAutumnUser();
        if (user == null) {
            return defaultValue;
        }
        return fun.apply(user);
    }

    @Override
    public Serializable getId() {
        return this.getSessionValue(Session::getId, null);
    }

    @Override
    public Date getStartTime() {
        return this.getSessionValue(Session::getStartTimestamp, null);
    }

    @Override
    public Date getLastAccessTime() {
        return this.getSessionValue(Session::getLastAccessTime, null);
    }

    @Override
    public String getHost() {
        return this.getSessionValue(Session::getHost, "");
    }

    @Override
    public Collection<Object> getAttributeKeys() {
        return this.getSessionValue(Session::getAttributeKeys, Collections.EMPTY_LIST);
    }

    @Override
    public Object getAttribute(Object key) {
        Session session = AutumnSecurityContextHolder.getSession();
        if (session == null) {
            return session.getAttribute(key);
        }
        return null;
    }

    @Override
    public void setAttribute(Object key, Object value) {
        Session session = AutumnSecurityContextHolder.getSession();
        if (session == null) {
            session.setAttribute(key, value);
        }
    }

    @Override
    public Object removeAttribute(Object key) {
        Session session = AutumnSecurityContextHolder.getSession();
        if (session == null) {
            return session.removeAttribute(key);
        }
        return null;
    }

    @Override
    public final Long getUserId() {
        return this.getAutumnUserValue(AutumnUser::getId, null);
    }

    @Override
    public final boolean isAuthenticated() {
        return AutumnSecurityContextHolder.isAuthenticated();
    }

    @Override
    public final String getUserName() {
        return this.getAutumnUserValue(AutumnUser::getUserName, null);
    }

    @Override
    public IdentityClaims getIdentityClaims() {
        return AutumnSecurityContextHolder.getIdentityClaims();
    }

    @Override
    public Set<String> getRoles() {
        return this.getAutumnUserValue(AutumnUser::getRoles, ROLES);
    }

    @Override
    public Set<String> getPermissions() {
        return this.getAutumnUserValue(AutumnUser::getPermissions, RERMISSIONS);
    }

    @Override
    public void logout() {
        Subject subject = AutumnSecurityContextHolder.getSubject();
        if (subject != null) {
            try {
                subject.logout();
            } catch (Exception err) {
                LOG.error("注销会话出错:" + err.getMessage(), err);
            }
        }
    }

    @Override
    public void updateUserInfo() {
        Subject subject = SecurityUtils.getSubject();
        if (subject.isAuthenticated()) {
            Object principal = subject.getPrincipal();
            if (principal instanceof AutumnUser) {
                AutumnUser user = (AutumnUser) principal;
                PrincipalCollection principalCollection = subject.getPrincipals();
                String realmName = principalCollection.getRealmNames().iterator().next();
                PrincipalCollection newPrincipalCollection = new SimplePrincipalCollection(user, realmName);
                subject.runAs(newPrincipalCollection);
            } else {
                throw ExceptionUtils.throwSystemException("用户绑定出错，会话的用户不是绑定的类型[" + AutumnUser.class.getName() + "]。");
            }
        } else {
            throw new AutumnNotLoginException();
        }
    }

    @Override
    public AutumnUser getUserInfo() {
        return AutumnSecurityContextHolder.getAutumnUser();
    }

    @Override
    public String getIdentityType() {
        return this.getAutumnUserValue(AutumnUser::getIdentityType, null);
    }

}
