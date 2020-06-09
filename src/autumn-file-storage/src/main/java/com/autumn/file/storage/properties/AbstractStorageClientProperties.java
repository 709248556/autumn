package com.autumn.file.storage.properties;

import java.io.Serializable;

/**
 * 存储客户端抽象属性
 *
 * @Description TODO
 * @Author 老码农
 * @Date 2019-09-03 3:50
 */
public class AbstractStorageClientProperties implements Serializable {
    private static final long serialVersionUID = -225640249927480330L;

    /**
     * 通道 Bean 前缀
     */
    public static final String CHANNEL_BEAN_PREFIX = "autumn";

    /**
     * 通道 Bean 后缀
     */
    public static final String CHANNEL_BEAN_SUFFIX = "StorageClient";

    private boolean enable = false;
    private String endpoint;
    private String defaultBucketName;
    private int readBlockSize = 2048;
    private int writeBlockSize = 2048;

    /**
     * 获取是否启用
     *
     * @return
     */
    public boolean isEnable() {
        return enable;
    }

    /**
     * 设置是否启用
     *
     * @param enable 是否启用
     */
    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    /**
     * 获取终节点
     *
     * @return
     */
    public String getEndpoint() {
        return endpoint;
    }

    /**
     * 设置终节点
     *
     * @param endpoint 终节点
     */
    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    /**
     * 获取默认分区
     *
     * @return
     */
    public String getDefaultBucketName() {
        return defaultBucketName;
    }

    /**
     * 设置默认分区
     *
     * @param defaultBucketName 默认分区
     */
    public void setDefaultBucketName(String defaultBucketName) {
        this.defaultBucketName = defaultBucketName;
    }

    /**
     * 获取读数据块大小
     *
     * @return
     */
    public int getReadBlockSize() {
        return readBlockSize;
    }

    /**
     * 设置读数据块大小
     *
     * @param readBlockSize 读数据块大小
     */
    public void setReadBlockSize(int readBlockSize) {
        this.readBlockSize = readBlockSize;
    }

    /**
     * 获取写数据块大小
     *
     * @return
     */
    public int getWriteBlockSize() {
        return writeBlockSize;
    }

    /**
     * 设置写数据块大小
     *
     * @param writeBlockSize 写数据块大小
     */
    public void setWriteBlockSize(int writeBlockSize) {
        this.writeBlockSize = writeBlockSize;
    }
}
