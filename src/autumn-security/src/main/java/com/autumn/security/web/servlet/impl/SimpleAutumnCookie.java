package com.autumn.security.web.servlet.impl;

import com.autumn.security.web.servlet.AutumnCookie;
import org.apache.shiro.web.servlet.Cookie;
import org.apache.shiro.web.servlet.SimpleCookie;

/**
 * 简单的  Cookie
 * <p>
 * </p>
 *
 * @description TODO
 * @author: 老码农
 * @create: 2020-03-31 00:14
 **/
public class SimpleAutumnCookie extends SimpleCookie implements AutumnCookie {

    /**
     *
     */
    public SimpleAutumnCookie() {

    }

    /**
     * @param name
     */
    public SimpleAutumnCookie(String name) {
        super(name);
    }

    /**
     * @param cookie
     */
    public SimpleAutumnCookie(Cookie cookie) {
        super(cookie);
    }


}
