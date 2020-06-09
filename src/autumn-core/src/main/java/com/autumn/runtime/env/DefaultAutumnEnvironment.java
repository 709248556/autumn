package com.autumn.runtime.env;

import org.springframework.core.env.ConfigurableEnvironment;

/**
 * 默认运行环境
 * <p>
 * </p>
 *
 * @description TODO
 * @author: 老码农
 * @create: 2020-04-02 02:00
 **/
public class DefaultAutumnEnvironment implements AutumnEnvironment {

    private final ConfigurableEnvironment environment;

    /**
     * 实例化
     *
     * @param environment
     */
    public DefaultAutumnEnvironment(ConfigurableEnvironment environment) {
        this.environment = environment;
    }

    /**
     * 获取环境
     *
     * @return
     */
    public ConfigurableEnvironment getEnvironment() {
        return this.environment;
    }

    private String applicationName = null;

    @Override
    public String getApplicationName() {
        if (this.applicationName == null) {
            this.applicationName = this.getEnvironment().getProperty("spring.application.name", String.class, "application");
        }
        return this.applicationName;
    }

    private Integer port = null;

    @Override
    public int getPort() {
        if (port == null) {
            port = this.getEnvironment().getProperty("server.port", Integer.class, 8080);
        }
        return port;
    }




}
