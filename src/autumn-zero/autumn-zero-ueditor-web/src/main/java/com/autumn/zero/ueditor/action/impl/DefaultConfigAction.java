package com.autumn.zero.ueditor.action.impl;

import com.autumn.exception.ExceptionUtils;
import com.autumn.util.ResourceUtils;
import com.autumn.zero.ueditor.action.ConfigAction;
import com.autumn.zero.ueditor.model.UeditorConfig;

/**
 * 文件执行器
 * <p>
 * </p>
 *
 * @description TODO
 * @author: 老码农
 * @create: 2020-03-24 17:57
 **/
public class DefaultConfigAction extends AbstractAction implements ConfigAction {

    /**
     * 默认配置文件路径
     */
    public static final String DEFAULT_CONFIG_FILE_PATH = "/static/ueditor/config.json";

    private final String configFilePath;
    private static final Object LOCK_OBJECT = new Object();
    private volatile UeditorConfig ueditorConfig;


    /**
     * 实例化
     *
     * @param configFilePath 配置文件路径
     */
    public DefaultConfigAction(String configFilePath) {
        this.configFilePath = ExceptionUtils.checkNotNullOrBlank(configFilePath, "configFilePath");
        this.ueditorConfig = null;
    }

    /**
     * 默认配置文件实例化
     */
    public DefaultConfigAction() {
        this(ResourceUtils.getResourceRootPath() + DEFAULT_CONFIG_FILE_PATH);
    }

    /**
     * 读取配置文件路径
     *
     * @return
     */
    public final String getConfigFilePath() {
        return this.configFilePath;
    }

    @Override
    public void refreshConfig() {
        if (this.isCheckLogin() && !this.isLogin()) {
            ExceptionUtils.throwValidationException("未登录,无权操作。");
        }
        synchronized (LOCK_OBJECT) {
            this.updateConfig();
        }
    }

    @Override
    public UeditorConfig readConfig() {
        if (this.ueditorConfig == null) {
            synchronized (LOCK_OBJECT) {
                if (this.ueditorConfig == null) {
                    this.updateConfig();
                }
            }
        }
        return this.ueditorConfig;
    }

    /**
     * 更新配置
     */
    private void updateConfig() {
        this.ueditorConfig = UeditorConfig.readConfig(this.configFilePath);
        if (this.ueditorConfig == null) {
            this.ueditorConfig = new UeditorConfig();
        }
    }
}
