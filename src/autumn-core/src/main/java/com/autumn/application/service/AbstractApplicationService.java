package com.autumn.application.service;

import com.autumn.application.ApplicationModule;
import com.autumn.runtime.AbstractRuntimeComponent;
import com.autumn.runtime.session.AutumnSession;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 应用服务抽象
 *
 * @author 老码农
 * <p>
 * 2017-09-22 20:50:37
 */
public abstract class AbstractApplicationService extends AbstractRuntimeComponent implements ApplicationService, ApplicationModule {

    private final String moduleId;

    /**
     * 实例化 AbstractApplicationService 新实例
     */
    public AbstractApplicationService() {
        this.moduleId = this.getClass().getSimpleName();
    }

    @Autowired
    private AutumnSession session;

    /**
     * 获取会话
     *
     * @return
     */
    @Override
    public AutumnSession getSession() {
        return this.session;
    }

    @Override
    public String getModuleId() {
        return this.moduleId;
    }
}
