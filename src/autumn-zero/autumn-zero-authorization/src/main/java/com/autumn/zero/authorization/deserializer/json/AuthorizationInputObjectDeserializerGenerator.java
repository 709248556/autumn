package com.autumn.zero.authorization.deserializer.json;

import com.autumn.security.token.CredentialsDeviceInfo;
import com.autumn.security.token.DefaultCredentialsDeviceInfo;
import com.autumn.util.json.JsonObjectDeserializerGenerator;
import com.autumn.zero.authorization.application.dto.captcha.SmsCaptchaCheck;
import com.autumn.zero.authorization.application.dto.captcha.SmsCaptchaCheckInput;

import java.util.HashMap;
import java.util.Map;

/**
 * 授权输入对象序列化
 * <p>
 * </p>
 *
 * @description TODO
 * @author: 老码农
 * @create: 2020-03-28 18:37
 **/
public class AuthorizationInputObjectDeserializerGenerator implements JsonObjectDeserializerGenerator {

    @Override
    public Map<Class<?>, Class<?>> generate() {
        Map<Class<?>, Class<?>> generateMap = new HashMap<>(16);
        generateMap.put(SmsCaptchaCheck.class, SmsCaptchaCheckInput.class);
        generateMap.put(CredentialsDeviceInfo.class, DefaultCredentialsDeviceInfo.class);
        return generateMap;
    }
}
