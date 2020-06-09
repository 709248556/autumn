package com.autumn.security.session;

import com.autumn.util.WebUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.SessionIdGenerator;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.Locale;
import java.util.UUID;

/**
 * 会话id生成器
 * <p>
 * </p>
 *
 * @description TODO
 * @author: 老码农
 * @create: 2020-04-01 19:37
 **/
public class AutumnSessionIdGenerator implements SessionIdGenerator {

    @Override
    public Serializable generateId(Session session) {
        HttpServletRequest request = WebUtils.getCurrentRequest();
        if (request != null) {
            Object token = request.getAttribute(AutumnWebSessionManager.TOKEN_SESSION_ID);
            if (token != null) {
                return token.toString();
            }
        }
        return UUID.randomUUID().toString().replace("-", "").toUpperCase(Locale.ENGLISH);
    }
}
