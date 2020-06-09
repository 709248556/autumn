package com.autumn.security.crypto;

import com.autumn.runtime.session.AutumnUser;

/**
 * 密码编码
 *
 * @author 老码农 2018-04-14 17:57:32
 */
public interface AutumnPasswordEncode {

    /**
     * 编码
     *
     * @param user        用户详情
     * @param rawPassword 密码
     * @return
     */
    String encode(AutumnUser user, String rawPassword);

    /**
     * 密码检查
     *
     * @param user        用户
     * @param rawPassword 原始密码
     * @return
     */
    boolean matches(AutumnUser user, String rawPassword);

    /**
     * 是否传输加密用户密码
     *
     * @return
     */
    boolean isTransferEncryptUserPassword();

}
