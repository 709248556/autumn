package com.autumn.util.mail;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

/**
 * 邮件授权
 *
 * @Description TODO
 * @Author 老码农
 * @Date 2019-10-09 19:55
 */
public class MailAuthenticator extends Authenticator {

    private final String userName;
    private final String password;

    public MailAuthenticator(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    /**
     * 获取用户名
     *
     * @return
     */
    public String getUserName() {
        return this.userName;
    }

    /**
     * 获取密码
     *
     * @return
     */
    public String getPassword() {
        return this.password;
    }

    @Override
    protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(this.getUserName(), this.getPassword());
    }
}
