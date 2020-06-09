package com.autumn.security.crypto;

import com.autumn.runtime.session.AutumnUser;
import com.autumn.runtime.session.AutumnUserDeviceInfo;
import com.autumn.security.token.CredentialsDeviceInfo;

/**
 * 设备编码提供者
 * <p>
 * </p>
 *
 * @description TODO
 * @author: 老码农
 * @create: 2020-04-20 00:49
 **/
public interface AutumnDeviceEncode {

    /**
     * 票据检查
     *
     * @param user          用户
     * @param rawDeviceInfo 原始设备信息
     * @return
     */
    boolean matches(AutumnUser user, CredentialsDeviceInfo rawDeviceInfo);

    /**
     * 创建存储
     *
     * @param user          用户
     * @param rawDeviceInfo 原始设备信息
     * @return
     */
    AutumnUserDeviceInfo createStorage(AutumnUser user, CredentialsDeviceInfo rawDeviceInfo);

    /**
     * 是否传输加密设备id
     *
     * @return
     */
    boolean isTransferEncryptDeviceId();

}
