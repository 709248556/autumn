package com.autumn.runtime.env;

/**
 * 运行环境
 * <p>
 * </p>
 *
 * @description TODO
 * @author: 老码农
 * @create: 2020-04-02 02:00
 **/
public interface AutumnEnvironment {

    /**
     * 获取应用名称
     * @return
     */
    String getApplicationName();

    /**
     * 获取端口
     * @return
     */
    int getPort();

}
