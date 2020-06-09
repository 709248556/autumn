package com.autumn.security.context;

import com.autumn.runtime.session.AutumnUser;
import com.autumn.runtime.session.claims.DefaultIdentityClaims;
import com.autumn.runtime.session.claims.IdentityClaims;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ThreadContext;

/**
 * 安全上下文
 *
 * @author 老码农
 * <p>
 * 2018-04-09 16:27:10
 */
public class AutumnSecurityContextHolder {

    /**
     * 日志
     */
    private static final Log logger = LogFactory.getLog(AutumnSecurityContextHolder.class);

    /**
     * 是否已认证
     *
     * @return
     */
    public static boolean isAuthenticated() {
        try {
            Subject subject = ThreadContext.getSubject();
            if (subject == null) {
                return false;
            }
            return subject.isAuthenticated();
        } catch (Exception err) {
            return false;
        }
    }

    /**
     * 获取会话
     *
     * @return
     */
    public static Subject getSubject() {
        try {
            return ThreadContext.getSubject();
        } catch (Exception err) {
            return null;
        }
    }

    /**
     * 获取会话
     *
     * @return
     */
    public static Session getSession() {
        try {
            Subject subject = ThreadContext.getSubject();
            if (subject == null) {
                return null;
            }
            return subject.getSession();
        } catch (Exception err) {
            return null;
        }
    }

    /**
     * 获取用户
     *
     * @return
     */
    public static AutumnUser getAutumnUser() {
        try {
            Subject subject = ThreadContext.getSubject();
            if (subject != null && subject.isAuthenticated()) {
                Object principal = subject.getPrincipal();
                if (principal instanceof AutumnUser) {
                    return (AutumnUser) principal;
                }
            }
            return null;
        } catch (Exception err) {
            return null;
        }
    }

    /**
     * 获取声明集合
     *
     * @return
     */
    public static IdentityClaims getIdentityClaims() {
        AutumnUser user = getAutumnUser();
        if (user != null && user.getIdentityClaims() != null) {
            return user.getIdentityClaims();
        }
        return DefaultIdentityClaims.DEFAULT_IDENTITY_CLAIMS;
    }

}
