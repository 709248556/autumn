package com.autumn.zero.authorization.utils;

import com.autumn.exception.ExceptionUtils;
import com.autumn.security.AutumnAccountCredentialsException;
import com.autumn.security.AutumnAccountNotFoundException;
import com.autumn.util.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

import java.io.Serializable;
import java.util.Random;

/**
 * 公共实用工具
 *
 * @Description TODO
 * @Author 老码农
 * @Date 2019-07-26 11:45
 */
public class AuthUtils {

    /**
     * 验证码错语代码
     */
    public static final int CAPTCHA_ERROR_CODE = -2;

    /**
     * 数字种子代码
     */
    public static final String NUMBER_SEED_CODES = "0123456789";

    /**
     * 完整种子代码
     */
    public static final String FULL_SEED_CODES = "123456789abcdefghijklmnpqrstuvwxyzABCDEFGHIJKLMNPQRSTUVWXYZ";

    /**
     * 随机用户名称
     *
     * @return
     */
    public static String randomUserName() {
        return randomCode(FULL_SEED_CODES, 12);
    }

    /**
     * 随机短信代码
     *
     * @return
     */
    public static String randomSmsCode() {
        return randomCode(NUMBER_SEED_CODES, 6);
    }

    /**
     * 随机图片代码
     *
     * @param length 长度
     * @return
     */
    public static String randomImageCode(int length) {
        return randomCode(FULL_SEED_CODES, length);
    }

    /**
     * 随机图片代码
     *
     * @return
     */
    public static String randomImageCode() {
        return randomImageCode(5);
    }

    /**
     * 随机代码
     *
     * @param seed   种子
     * @param length 长度
     * @return
     */
    public static String randomCode(String seed, int length) {
        StringBuffer sb = new StringBuffer();
        Random random = new Random();
        for (int i = 1; i <= length; i++) {
            sb.append(seed.charAt(random.nextInt(seed.length())));
        }
        return sb.toString();
    }

    /**
     * 获取会话
     *
     * @return
     */
    public static Session getSession() {
        Subject subject = SecurityUtils.getSubject();
        return subject.getSession(true);
    }

    /**
     * 检查会话id
     *
     * @return
     */
    public static String checkSessionId() {
        Serializable sessionId = AuthUtils.getSession().getId();
        if (sessionId == null || StringUtils.isNullOrBlank(sessionId.toString())) {
            ExceptionUtils.throwValidationException("无效的访问，无会话id");
        }
        return sessionId.toString();
    }

    /**
     * 创建验证码异常
     *
     * @param message 消息
     * @return
     */
    public static AutumnAccountCredentialsException createCaptchaException(String message) {
        AutumnAccountCredentialsException exception = new AutumnAccountCredentialsException(message);
        exception.setCode(CAPTCHA_ERROR_CODE);
        return exception;
    }

    /**
     * 创建认证异常
     *
     * @param message 消息
     * @return
     */
    public static AutumnAccountCredentialsException createAccountCredentialsException(String message) {
        throw new AutumnAccountCredentialsException(message);
    }

    /**
     * 创建 账户不存在异常
     * @param message
     * @return
     */
    public static AutumnAccountNotFoundException createAccountNotFoundException(String message) {
        return new AutumnAccountNotFoundException(message);
    }
}
