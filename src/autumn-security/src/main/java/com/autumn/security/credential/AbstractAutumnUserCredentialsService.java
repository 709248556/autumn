package com.autumn.security.credential;

import com.autumn.audited.ClientInfoProvider;
import com.autumn.audited.LoginAuditedLog;
import com.autumn.runtime.session.AutumnUser;
import com.autumn.security.crypto.AutumnDeviceEncode;
import com.autumn.security.crypto.AutumnPasswordEncode;
import com.autumn.security.token.AutumnAuthenticationToken;
import com.autumn.security.token.DeviceAuthenticationToken;
import com.autumn.security.token.UserNamePasswordAuthenticationToken;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 认证比较服务抽象
 * <p>
 * </p>
 *
 * @description TODO
 * @author: 老码农
 * @create: 2020-04-20 13:01
 **/
public abstract class AbstractAutumnUserCredentialsService implements AutumnUserCredentialsService {

    @Autowired
    protected LoginAuditedLog loginAuditedLog;

    @Autowired
    protected AutumnDeviceEncode deviceEncode;

    @Autowired
    protected AutumnPasswordEncode passwordEncode;

    @Override
    public boolean matches(AutumnUser user, AutumnAuthenticationToken token) {
        boolean isMatch;
        if (token instanceof UserNamePasswordAuthenticationToken) {
            UserNamePasswordAuthenticationToken userNamePasswordToken = (UserNamePasswordAuthenticationToken) token;
            String rawPassword = String.valueOf(userNamePasswordToken.getPassword());
            isMatch = this.passwordEncode.matches(user, rawPassword);
        } else if (token instanceof DeviceAuthenticationToken) {
            DeviceAuthenticationToken deviceAuthenticationToken = (DeviceAuthenticationToken) token;
            isMatch = this.deviceEncode.matches(user, deviceAuthenticationToken.getCredentialsDeviceInfo());
        } else {
            isMatch = true;
        }
        return isMatch;
    }

    @Override
    public String generateIdentityType(AutumnAuthenticationToken token, ClientInfoProvider clientInfoProvider) {
        if (token.getCredentialsDeviceInfo() != null) {
            return token.getCredentialsDeviceInfo().getPlatformName();
        }
        return clientInfoProvider.getClientPlatformName();
    }


}
