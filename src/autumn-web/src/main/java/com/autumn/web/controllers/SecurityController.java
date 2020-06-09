package com.autumn.web.controllers;

import com.autumn.spring.boot.properties.AutumnSecurityProperties;

import com.autumn.util.StringUtils;
import com.autumn.util.security.RsaUtils;
import com.autumn.web.dto.SecurityKeyOutput;
import com.autumn.web.dto.SecurityRsaKeyInput;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.io.IOException;

/**
 * 安全控制器
 * <p>
 * </p>
 *
 * @description TODO
 * @author: 老码农
 * @create: 2020-04-23 17:38
 **/
@RestController
@RequestMapping("/security")
@Api(tags = "安全")
public class SecurityController {

    /**
     * 安全包路径
     */
    public static final String SECURITY_PACKAGE_PATH = "com.autumn.web.controllers";

    private final AutumnSecurityProperties properties;

    /**
     * 实例化
     *
     * @param properties
     */
    public SecurityController(AutumnSecurityProperties properties) {
        this.properties = properties;
    }

    /**
     * 获取 Rsa 公钥
     *
     * @param input 输入
     * @return
     */
    @PostMapping("/rsa/key/public")
    @ApiOperation(value = "获取 Rsa 公钥")
    public SecurityKeyOutput rsaPublicKey(@Valid @RequestBody SecurityRsaKeyInput input) throws IOException {
        AutumnSecurityProperties.AutumnSecurityRsaProperties rsa = properties.getRsa();
        if (rsa == null || !rsa.isEnable()) {
            return null;
        }
        String publicKey = properties.getRsa().getPublicKey();
        if (StringUtils.isNullOrBlank(publicKey)) {
            publicKey = "";
        } else {
            if (input != null && input.isPkcs1Format()) {
                publicKey = RsaUtils.toPkcs1PublicKey(publicKey);
            }
        }
        return new SecurityKeyOutput(publicKey);
    }

}
