package com.autumn.file.storage.impl;

import com.autumn.file.storage.StorageClient;
import com.autumn.file.storage.StorageClientContext;
import com.autumn.util.channel.AbstractChannelContext;

/**
 * 存储客户实现
 * <p>
 * </p>
 *
 * @description TODO
 * @author: 老码农
 * @create: 2020-03-02 23:05
 **/
public class StorageClientContextImpl extends AbstractChannelContext<StorageClient> implements StorageClientContext {
    
    /**
     *
     */
    public StorageClientContextImpl() {
        super(16);
    }
}
