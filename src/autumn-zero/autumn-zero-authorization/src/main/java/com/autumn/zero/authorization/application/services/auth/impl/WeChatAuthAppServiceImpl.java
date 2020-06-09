package com.autumn.zero.authorization.application.services.auth.impl;

import com.autumn.application.service.AbstractApplicationService;
import com.autumn.exception.AutumnException;
import com.autumn.exception.ExceptionUtils;
import com.autumn.security.constants.UserStatusConstants;
import com.autumn.security.token.ExternalProviderAuthenticationToken;
import com.autumn.util.StringUtils;
import com.autumn.util.http.HttpUtils;
import com.autumn.util.json.JsonUtils;
import com.autumn.zero.authorization.application.dto.auth.UserLoginInfoOutput;
import com.autumn.zero.authorization.application.dto.auth.wechat.WeChatAppTokenInput;
import com.autumn.zero.authorization.application.dto.auth.wechat.WeChatSessionDto;
import com.autumn.zero.authorization.application.dto.auth.wechat.WeChatUserInfoDto;
import com.autumn.zero.authorization.application.services.auth.AuthAppServiceBase;
import com.autumn.zero.authorization.application.services.auth.WeChatAuthAppService;
import com.autumn.zero.authorization.entities.common.AbstractRole;
import com.autumn.zero.authorization.entities.common.AbstractUser;
import com.autumn.zero.authorization.entities.common.UserExternalAuthLogin;
import com.autumn.zero.authorization.properties.WeChatAuthProperties;
import com.autumn.zero.authorization.services.AuthorizationServiceBase;
import com.autumn.zero.authorization.utils.AuthUtils;
import com.google.common.collect.Lists;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.AlgorithmParameters;
import java.security.Security;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * 微信授权登录
 * <p>
 * </p>
 *
 * @description TODO
 * @author: 老码农
 * @create: 2020-03-30 13:05
 **/
public class WeChatAuthAppServiceImpl<TUser extends AbstractUser, TRole extends AbstractRole, TUserLoginInfo extends UserLoginInfoOutput>
        extends AbstractApplicationService
        implements WeChatAuthAppService<UserLoginInfoOutput> {

    private static final String DEFAULT_WE_CHAT_URL_OPEN_ID = "https://api.weixin.qq.com/sns/jscode2session";
    private static final String DEFAULT_WE_CHAT_APP_PROVIDER_NAME = "WeChatApp_Auth";

    private final WeChatAuthProperties weChatAuthProperties;

    private final AuthorizationServiceBase<TUser, TRole> authService;

    private final AuthAppServiceBase<TUser, TUserLoginInfo> authAppService;

    static {
        Security.addProvider(new BouncyCastleProvider());
    }

    /**
     * @param authService          授权服务
     * @param authAppService       授权应用服务
     * @param weChatAuthProperties 微信属性
     */
    public WeChatAuthAppServiceImpl(AuthorizationServiceBase<TUser, TRole> authService,
                                    AuthAppServiceBase<TUser, TUserLoginInfo> authAppService,
                                    WeChatAuthProperties weChatAuthProperties) {
        this.authService = authService;
        this.authAppService = authAppService;
        this.weChatAuthProperties = weChatAuthProperties;
    }

    /**
     * 获取微信OpenId的Url地址
     *
     * @return
     */
    protected String getWeChatOpenIdUrl() {
        return DEFAULT_WE_CHAT_URL_OPEN_ID;
    }

    /**
     * 获取微信 ProviderName
     *
     * @return
     */
    protected String getWeChatProviderName() {
        return DEFAULT_WE_CHAT_APP_PROVIDER_NAME;
    }


    @Override
    public String getModuleName() {
        return "微信授权登录";
    }

    @Override
    public UserLoginInfoOutput loginByWeChatApp(WeChatAppTokenInput input, boolean isRegister) {
        ExceptionUtils.checkNotNull(input, "input");
        input.valid();
        try {
            WeChatSessionDto sessionDto = this.doWeChatSessionDto(input);
            String weChatProviderName = this.getWeChatProviderName();
            TUser user = authService.findUserByExternalProvider(weChatProviderName, sessionDto.getOpenid(), false);
            if (user == null) {
                if (!isRegister) {
                    throw AuthUtils.createAccountNotFoundException("用户未注册。");
                }
                //注册
                WeChatUserInfoDto userInfo = decryptUserInfo(input.getEncryptedData(), sessionDto.getSession_key(), input.getIv());
                user = this.authAppService.createRegisterUser();
                user.setUserName(this.authAppService.createAvailableRandomUserName());
                if (userInfo != null) {
                    user.setNickName(input.getNickName());
                    if (input.getGender() != null) {
                        if (input.getGender() == 1) {
                            user.setSex("男");
                        } else if (input.getGender() == 2) {
                            user.setSex("女");
                        } else {
                            user.setSex("");
                        }
                    } else {
                        user.setSex("");
                    }
                    if (!StringUtils.isNullOrBlank(input.getAvatarUrl())) {
                        user.setHeadPortraitPath(input.getAvatarUrl());
                    }
                } else {
                    user.setRealName("");
                }
                user.setBirthday(null);
                user.setPhoneNumber("");
                user.setIsSysUser(false);
                user.setStatus(UserStatusConstants.NORMAL);
                user.setPassword(AuthUtils.randomCode(AuthUtils.FULL_SEED_CODES, 8));
                user.setRoles(Lists.newArrayList());
                user.forNullToDefault();
                this.authService.addUser(user, false);
                this.getAuditedLogger().addLog(this, "微信注册", user);
                UserExternalAuthLogin externalAuthLogin = new UserExternalAuthLogin();
                externalAuthLogin.setProvider(weChatProviderName);
                externalAuthLogin.setProviderKey(sessionDto.getOpenid());
                externalAuthLogin.setUserId(user.getId());
                externalAuthLogin.forNullToDefault();
                this.authService.addOrUpdateUserExternalProvider(externalAuthLogin);
            }
            ExternalProviderAuthenticationToken providerToken = new ExternalProviderAuthenticationToken(weChatProviderName, sessionDto.getOpenid());
            providerToken.setCredentialsDeviceInfo(input.getCredentialsDeviceInfo());
            Subject subject = SecurityUtils.getSubject();
            //subject.logout();
            subject.login(providerToken);
            return this.authAppService.loginInfo();
        } catch (Exception e) {
            if (e instanceof AutumnException) {
                throw (AutumnException) e;
            }
            throw AuthUtils.createAccountCredentialsException(e.getMessage());
        }
    }

    private WeChatSessionDto doWeChatSessionDto(WeChatAppTokenInput input) throws IOException {
        Map<String, String> param = new HashMap<>(16);
        param.put("appid", weChatAuthProperties.getAppId());
        param.put("secret", weChatAuthProperties.getAppSecret());
        param.put("js_code", input.getJsCode());
        param.put("grant_type", "authorization_code");
        String openIdUrl = this.getWeChatOpenIdUrl();
        String responseString = HttpUtils.doGetForString(openIdUrl, param);
        if (StringUtils.isNullOrBlank(responseString)) {
            this.getLogger().error("调用 " + openIdUrl + " 出错:响应为空白值。");
            throw AuthUtils.createAccountCredentialsException("无法获取微信授权信息");
        }
        WeChatSessionDto sessionDto = JsonUtils.parseObject(responseString, WeChatSessionDto.class);
        if (sessionDto == null) {
            this.getLogger().error("调用 " + openIdUrl + " 出错: errcode 为 null ，" + responseString);
            throw AuthUtils.createAccountCredentialsException("无法获取微信授权信息");
        }
        if (sessionDto.getErrcode() != null) {
            if (sessionDto.getErrcode() == WeChatSessionDto.ERROR_CODE_INVALID_CODE) {
                this.getLogger().error("调用 " + openIdUrl + " 出错:" + sessionDto.getErrmsg());
                ExceptionUtils.throwApplicationException("获取微信授权出错，无效的授权代码(jsCode)");
            }
            if (sessionDto.getErrcode() != 0) {
                this.getLogger().error("调用 " + openIdUrl + " 出错:" + sessionDto.getErrmsg());
                throw AuthUtils.createAccountCredentialsException("获取微信授权出错。");
            }
        }
        if (StringUtils.isNullOrBlank(sessionDto.getOpenid())) {
            throw AuthUtils.createAccountCredentialsException("获取微信授权出错,无法获取 openid。");
        }
        if (StringUtils.isNullOrBlank(sessionDto.getSession_key())) {
            throw AuthUtils.createAccountCredentialsException("获取微信授权出错,无法获取 session_key。");
        }
        return sessionDto;
    }

    private WeChatUserInfoDto decryptUserInfo(String encryptedData, String sessionKey, String iv) {
        // 被加密的数据
        byte[] dataByte = org.springframework.util.Base64Utils.decode(encryptedData.getBytes());
        // 加密秘钥
        byte[] keyByte = org.springframework.util.Base64Utils.decode(sessionKey.getBytes());
        // 偏移量
        byte[] ivByte = org.springframework.util.Base64Utils.decode(iv.getBytes());
        try {
            // 如果密钥不足16位，那么就补足.  这个if 中的内容很重要
            int base = 16;
            if (keyByte.length % base != 0) {
                int groups = keyByte.length / base + 1;
                byte[] temp = new byte[groups * base];
                Arrays.fill(temp, (byte) 0);
                System.arraycopy(keyByte, 0, temp, 0, keyByte.length);
                keyByte = temp;
            }
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding", "BC");
            SecretKeySpec spec = new SecretKeySpec(keyByte, "AES");
            AlgorithmParameters parameters = AlgorithmParameters.getInstance("AES");
            parameters.init(new IvParameterSpec(ivByte));
            // 初始化
            cipher.init(Cipher.DECRYPT_MODE, spec, parameters);
            byte[] resultByte = cipher.doFinal(dataByte);
            if (null != resultByte && resultByte.length > 0) {
                String result = new String(resultByte, StandardCharsets.UTF_8);
                return JsonUtils.parseObject(result, WeChatUserInfoDto.class);
            }
        } catch (Exception e) {
            this.getLogger().error(e.getMessage(), e);
        }
        return null;
    }
}
