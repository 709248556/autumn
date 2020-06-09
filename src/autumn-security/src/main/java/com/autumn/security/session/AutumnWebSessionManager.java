package com.autumn.security.session;

import com.autumn.runtime.task.TaskExecutorService;
import com.autumn.security.filter.FilterUtils;
import com.autumn.util.StringUtils;
import org.apache.shiro.session.InvalidSessionException;
import org.apache.shiro.session.mgt.SessionKey;
import org.apache.shiro.web.servlet.ShiroHttpServletRequest;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;

/**
 * Web 会话管理
 * <p>
 * </p>
 *
 * @description TODO
 * @author: 老码农
 * @create: 2020-04-21 01:19
 **/
public class AutumnWebSessionManager extends DefaultWebSessionManager {

    /**
     * Token 会话id
     */
    public static final String TOKEN_SESSION_ID = AutumnWebSessionManager.class.getName() + "_TOKEN_SESSION_ID";

    /**
     * 多线程任务执行器
     */
    protected final TaskExecutorService executorService;

    public AutumnWebSessionManager(TaskExecutorService executorService) {
        this.executorService = executorService;
    }

    @Override
    protected Serializable getSessionId(ServletRequest request, ServletResponse response) {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String token = FilterUtils.getRequestToken(httpServletRequest);
        if (StringUtils.isNullOrBlank(token)) {
            return super.getSessionId(request, response);
        }
        String deviceId = FilterUtils.getRequestDeviceId(httpServletRequest);
        if (StringUtils.isNullOrBlank(deviceId)) {
            return super.getSessionId(request, response);
        }
        token = token.trim();
        request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_SOURCE,
                ShiroHttpServletRequest.COOKIE_SESSION_ID_SOURCE);
        request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID, token);
        request.setAttribute(TOKEN_SESSION_ID, token);
        return token;
    }

    /**
     * 转为异步执行
     * <p>
     * 更新会话最后访问时间
     * </p>
     *
     * @param key
     * @throws InvalidSessionException
     */
    @Override
    public void touch(SessionKey key) throws InvalidSessionException {
        this.executorService.getLinkedBlockingQueueService().submit(() -> {
            super.touch(key);
        });
    }
}
