package com.autumn.file.storage.properties;

import com.autumn.file.storage.clients.aliyun.AliyunStorageClientProperties;
import com.autumn.file.storage.clients.fastdfs.FastDFSStorageClientProperties;
import com.autumn.file.storage.clients.local.LocalStorageClientProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.io.Serializable;

/**
 * 存储属性
 *
 * @author 老码农 2019-03-11 00:39:13
 */
@ConfigurationProperties(prefix = AutumnStorageProperties.PREFIX)
public class AutumnStorageProperties implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 7010871624244378881L;

    /**
     * 属性前缀
     */
    public final static String PREFIX = "autumn.storage.client";

    private AliyunStorageClientProperties aliyun = new AliyunStorageClientProperties();
    private LocalStorageClientProperties local = new LocalStorageClientProperties();
    private FastDFSStorageClientProperties fastDFS = new FastDFSStorageClientProperties();


    /**
     *
     */
    public AutumnStorageProperties() {

    }

    /**
     * 获取阿里云属性
     *
     * @return
     */
    public AliyunStorageClientProperties getAliyun() {
        return this.aliyun;
    }

    /**
     * 阿里云属性
     *
     * @param aliyun
     */
    public void setAliyun(AliyunStorageClientProperties aliyun) {
        this.aliyun = aliyun;
    }

    /**
     * 获取本地磁盘属性
     *
     * @return
     */
    public LocalStorageClientProperties getLocal() {
        return this.local;
    }

    /**
     * 设置本地磁盘属性
     *
     * @param local
     */
    public void setLocal(LocalStorageClientProperties local) {
        this.local = local;
    }

    /**
     * 获取 FastDFS 属性
     *
     * @return
     */
    public FastDFSStorageClientProperties getFastDFS() {
        return this.fastDFS;
    }

    /**
     * FastDFS 属性
     *
     * @param fastDFS
     */
    public void setFastDFS(FastDFSStorageClientProperties fastDFS) {
        this.fastDFS = fastDFS;
    }

}
