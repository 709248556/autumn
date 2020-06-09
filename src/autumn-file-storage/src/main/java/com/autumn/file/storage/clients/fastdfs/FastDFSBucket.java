package com.autumn.file.storage.clients.fastdfs;

import com.autumn.file.storage.AbstractBucket;

/**
 * FastDFS 分区信息
 *
 * @Description TODO
 * @Author 老码农
 * @Date 2019-09-03 4:37
 */
public class FastDFSBucket extends AbstractBucket {
    private static final long serialVersionUID = -1799448666278756043L;

    /**
     * @param name
     */
    public FastDFSBucket(String name) {
        super(name);
    }
}
