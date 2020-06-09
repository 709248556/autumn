package com.autumn.file.storage.clients.local;

import com.autumn.file.storage.properties.AbstractStorageClientProperties;
import com.autumn.file.storage.properties.AutumnStorageProperties;

/**
 * 本地存储属性
 *
 * @Description TODO
 * @Author 老码农
 * @Date 2019-09-03 3:51
 */
public class LocalStorageClientProperties extends AbstractStorageClientProperties {
    private static final long serialVersionUID = -8781765375269535591L;

    /**
     * bean条件属性
     */
    public static final String BEAN_CONDITIONAL_PROPERTY = AutumnStorageProperties.PREFIX + ".local.enable";

    /**
     * 通道 Bean 名称
     */
    public static final String CHANNEL_BEAN_NAME = CHANNEL_BEAN_PREFIX + "Local" + CHANNEL_BEAN_SUFFIX;

    /**
     * 默认终节点
     */
    public static final String DEFAULT_ENDPOINT = "/";

    /**
     * 默认分区
     */
    public static final String DEFAULT_BUCKET_NAME = "files";

    private String rootFilePath = "";

    public LocalStorageClientProperties() {
        this.setEndpoint(DEFAULT_ENDPOINT);
        this.setRootFilePath("");
        this.setDefaultBucketName(DEFAULT_BUCKET_NAME);
    }

    /**
     * 获取根文件路径
     *
     * @return
     */
    public String getRootFilePath() {
        return rootFilePath;
    }

    /**
     * 设置根文件路径
     *
     * @param rootFilePath
     *            根文件路径
     */
    public void setRootFilePath(String rootFilePath) {
        this.rootFilePath = rootFilePath;
    }
}
