package com.autumn.application.service;

import com.autumn.application.ApplicationModule;
import com.autumn.runtime.RuntimeComponent;
import com.autumn.runtime.session.AutumnSession;

/**
 * 应用服务抽象
 *
 * @author 老码农
 * <p>
 * 2017-09-22 19:23:56
 */
public interface ApplicationService extends RuntimeComponent, ApplicationModule {

    /**
     * 获取会话
     *
     * @return
     */
    AutumnSession getSession();
}
