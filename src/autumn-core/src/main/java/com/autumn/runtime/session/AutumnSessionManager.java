package com.autumn.runtime.session;

import java.io.Serializable;
import java.util.Collection;
import java.util.function.Predicate;

/**
 * 会话管理
 * <p>
 * </p>
 *
 * @description TODO
 * @author: 老码农
 * @create: 2020-04-01 22:49
 **/
public interface AutumnSessionManager {

    /**
     * 在线会话数
     *
     * @return
     */
    long onLineSessions();

    /**
     * 在线用户数
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
     * 读取会话用户集合
     *
     * @param userId 用户id
     * @return
     */
    Collection<AutumnSessionUser> readSessionUsers(Long userId);

    /**
     * 删除会话会话
     *
     * @param sessionId 会话id
     * @return
     */
    void deleteSession(Serializable sessionId);

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
