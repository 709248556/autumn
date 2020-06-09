package com.autumn.zero.authorization.application.dto.captcha;

import com.autumn.validation.DataValidation;
import com.autumn.validation.ValidationUtils;
import com.autumn.validation.annotation.NotNullOrBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 短信发送模板
 *
 * @Description TODO
 * @Author 老码农
 * @Date 2019-07-26 13:13
 */
@ToString(callSuper = true)
@Getter
@Setter
public class SmsSendTemplateDto implements DataValidation {

    private static final long serialVersionUID = -3488398942795230033L;

    private static final int DEFAULT_EXPIRE = 300;

    /**
     * 模板代码
     */
    @NotNullOrBlank(message = "短信模板代码未配置")
    private String templateCode;

    /**
     * 签名
     */
    private String signName;

    /**
     * 参数名称
     */
    @NotNullOrBlank(message = "短信模板参数名称未配置")
    private String paramName;

    /**
     * 短信内容
     */
    private String messageContent;

    /**
     * 过期时间(秒)
     */
    private int expire = DEFAULT_EXPIRE;


    @Override
    public void valid() {
        ValidationUtils.validation(this);
        if (this.getExpire() <= 0) {
            this.setExpire(DEFAULT_EXPIRE);
        }
    }
}
