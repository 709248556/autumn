package com.autumn.security.session;

import com.autumn.runtime.session.AutumnSessionUser;
import org.apache.shiro.session.mgt.eis.SessionDAO;

import java.io.Serializable;
import java.util.Collection;
import java.util.function.Predicate;

/**
 * <p>
 * </p>
 *
 * @description TODO
 * @author: 老码农
 * @create: 2020-04-07 20:18
 **/
public interface AutumnSessionDAO extends SessionDAO {

    /**
     * 在线会话数量
     *
     * @return
     */
    long onLineSessions();

    /**
     * 在线用户量
     *
     * @return
     */
    long onLineUsers();

    /**
     * 读取会话用户
     *
     * @param sessionId 会话id
     * @return
     */
    AutumnSessionUser readSessionUser(Serializable sessionId);

    /**
     * 读取在线用户集合
     *
     * @param userId 用户id
     * @return
     */
    Collection<AutumnSessionUser> readSessionUsers(Long userId);

    /**
     * 删除会话用户
     *
     * @param userId 用户id
     * @return
     */
    int deleteSessionUser(Long userId);

    /**
     * 删除会话用户
     *
     * @param userId    用户id
     * @param condition 条件
     * @return
     */
    int deleteSessionUser(Long userId, Predicate<AutumnSessionUser> condition);

    /**
     * 删除所有会话用户
     */
    void deleteAllSessionUser();

    /**
     * 同步会话
     */
    void synSession();
}
