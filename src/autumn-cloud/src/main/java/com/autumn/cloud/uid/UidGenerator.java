package com.autumn.cloud.uid;

import com.autumn.cloud.DistributedIdentification;

/**
 * Uid 生成器
 *
 * @Description TODO
 * @Author 老码农
 * @Date 2019-08-25 18:04
 */
public interface UidGenerator extends DistributedIdentification {

    /**
     * 新的唯一Uid
     *
     * @return UID
     * @throws UidGenerateException
     */
    UID newUID() throws UidGenerateException;

    /**
     * 解析id
     *
     * @param uid 需要解析的uid
     * @return 返回解析的 UID
     */
    UID parseUID(long uid);

}
