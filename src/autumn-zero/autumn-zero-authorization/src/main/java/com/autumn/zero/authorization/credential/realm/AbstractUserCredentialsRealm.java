package com.autumn.zero.authorization.credential.realm;

import com.autumn.audited.ClientInfoProvider;
import com.autumn.audited.LoginAuditedLog;
import com.autumn.audited.LoginAuditedLogMessage;
import com.autumn.runtime.session.AutumnUser;
import com.autumn.runtime.session.AutumnUserDeviceInfo;
import com.autumn.runtime.session.DefaultAutumnUser;
import com.autumn.runtime.session.DefaultAutumnUserDeviceInfo;
import com.autumn.runtime.session.claims.HashMapIdentityClaims;
import com.autumn.runtime.session.claims.IdentityClaims;
import com.autumn.security.AutumnAccountCredentialsException;
import com.autumn.security.constants.UserStatusConstants;
import com.autumn.security.credential.AutumnUserCredentialsService;
import com.autumn.security.crypto.AutumnDeviceEncode;
import com.autumn.security.token.AutumnAuthenticationToken;
import com.autumn.security.token.DeviceAuthenticationToken;
import com.autumn.security.token.ToeknAuditedLog;
import com.autumn.util.AutoMapUtils;
import com.autumn.util.ServiceUtils;
import com.autumn.util.reflect.ReflectUtils;
import com.autumn.util.tuple.TupleTwo;
import com.autumn.zero.authorization.entities.common.AbstractRole;
import com.autumn.zero.authorization.entities.common.AbstractUser;
import com.autumn.zero.authorization.entities.common.UserDeviceAuthLogin;
import com.autumn.zero.authorization.services.AuthorizationServiceBase;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

/**
 * 用户认证抽象领域
 * <p>
 * </p>
 *
 * @description TODO
 * @author: 老码农
 * @create: 2020-04-21 13:27
 **/
public abstract class AbstractUserCredentialsRealm<TAuthenticationToken extends AutumnAuthenticationToken>
        implements UserCredentialsRealm {

    /**
     * 日志
     */
    protected final Log logger = LogFactory.getLog(this.getClass());

    /**
     * 登录日志
     */
    @Autowired
    protected LoginAuditedLog loginAuditedLog;

    /**
     * 设置驱动提供
     */
    @Autowired
    protected AutumnDeviceEncode autumnDeviceEncode;

    /**
     * 客户端信息提供程序
     */
    @Autowired
    protected ClientInfoProvider clientInfoProvider;

    /**
     * 获取授权服务
     *
     * @return
     */
    protected final AuthorizationServiceBase authorizationService;

    private final Class<TAuthenticationToken> authenticationTokenClass;

    /**
     * 实例化
     *
     * @param authorizationService 授权服务
     */
    public AbstractUserCredentialsRealm(AuthorizationServiceBase authorizationService) {
        this.authorizationService = authorizationService;
        Map<String, Class<?>> map = ReflectUtils.getGenericActualArgumentsTypeMap(this.getClass());
        this.authenticationTokenClass = ServiceUtils.getGenericActualClass(map, "TAuthenticationToken");
    }

    /**
     * 是否保存设备信息
     *
     * @param token
     * @return
     */
    protected boolean isSaveDeviceInfo(TAuthenticationToken token) {
        return token.getCredentialsDeviceInfo() != null && !(token instanceof DeviceAuthenticationToken);
    }

    /**
     * 调用信息
     *
     * @param token 票据
     * @return
     */
    protected abstract TupleTwo<AbstractUser, DefaultAutumnUser> doGetUser(TAuthenticationToken token);

    /**
     * 获取默认Token类型
     *
     * @return
     */
    @Override
    public final Class<TAuthenticationToken> getDefaultTokenClass() {
        return this.authenticationTokenClass;
    }

    @Override
    public boolean supports(AutumnAuthenticationToken token) {
        return this.getDefaultTokenClass().isAssignableFrom(token.getClass());
    }

    @Override
    public TupleTwo<AbstractUser, AutumnUser> doGetUser(AutumnUserCredentialsService service, AutumnAuthenticationToken token) {
        TAuthenticationToken authenticationToken = (TAuthenticationToken) token;
        boolean isSaveDevice = this.isSaveDeviceInfo(authenticationToken);
        if (isSaveDevice) {
            this.authorizationService.saveCheckCredentialsDeviceInfo(token.getCredentialsDeviceInfo());
        }
        TupleTwo<AbstractUser, DefaultAutumnUser> tupleTwo = this.doGetUser(authenticationToken);
        AbstractUser user = tupleTwo.getItem1();
        DefaultAutumnUser sessionUser = tupleTwo.getItem2();
        sessionUser.setIdentityType(service.generateIdentityType(token, this.clientInfoProvider));
        this.checkUserState(user, authenticationToken);
        boolean isMatch = service.matches(sessionUser, token);
        if (!isMatch) {
            ToeknAuditedLog auditedLog = token.createAuditedLog();
            LoginAuditedLogMessage logMsg = createFailLogMessage(user, authenticationToken, auditedLog.getFailStatusMessage());
            this.loginAuditedLog.addLog(logMsg);
            throw this.createCredentialsException("账户[" + user.getUserName() + "]" + logMsg.getStatusMessage(), logMsg.getStatusMessage());
        }
        this.setUserCommonInfo(user, sessionUser);
        if (isSaveDevice) {
            this.saveDeviceInfoAndLoad(user, sessionUser, authenticationToken);
        }
        return new TupleTwo<>(user, sessionUser);
    }

    /**
     * 检查用户状态
     *
     * @param user  用户
     * @param token 票据
     */
    private void checkUserState(AbstractUser user, TAuthenticationToken token) {
        LoginAuditedLogMessage logMsg;
        switch (user.getStatus()) {
            case UserStatusConstants.LOCKING:
                logMsg = createFailLogMessage(user, token, "账户已锁定");
                this.loginAuditedLog.addLog(logMsg);
                throw this.createCredentialsException("账户[" + user.getUserName() + "]已锁定", logMsg.getStatusMessage());
            case UserStatusConstants.NOT_ACTIVATE:
                logMsg = createFailLogMessage(user, token, "账户未激活");
                this.loginAuditedLog.addLog(logMsg);
                throw this.createCredentialsException("账户[" + user.getUserName() + "]未激活", logMsg.getStatusMessage());
            case UserStatusConstants.EXPIRED:
                logMsg = createFailLogMessage(user, token, "账户已过期");
                this.loginAuditedLog.addLog(logMsg);
                throw this.createCredentialsException("账户[" + user.getUserName() + "]已过期", logMsg.getStatusMessage());
            default:
                break;
        }
    }

    /**
     * 设置用户公共信息
     *
     * @param user
     * @param autumnUser
     */
    private void setUserCommonInfo(AbstractUser user, DefaultAutumnUser autumnUser) {
        List roles = this.authorizationService.queryUserByRoles(user.getId());
        for (Object role : roles) {
            autumnUser.getRoles().add(((AbstractRole) role).getName().toLowerCase());
        }
        IdentityClaims identityClaims = autumnUser.getIdentityClaims();
        if (identityClaims == null) {
            identityClaims = new HashMapIdentityClaims();
            autumnUser.setIdentityClaims(identityClaims);
        }
        identityClaims.put(AbstractUser.USER_SESSION_CLAIM_USER_NAME, user.getUserName());
        identityClaims.put(AbstractUser.USER_SESSION_CLAIM_REAL_NAME, user.getRealName());
        identityClaims.put(AbstractUser.USER_SESSION_CLAIM_NICK_NAME, user.getNickName());
        identityClaims.put(AbstractUser.USER_SESSION_CLAIM_PHONE_NUMBER, user.getPhoneNumber());
        identityClaims.put(AbstractUser.USER_SESSION_CLAIM_EMAIL_ADDRESS, user.getEmailAddress());
        identityClaims.put(AbstractUser.USER_SESSION_CLAIM_SEX, user.getSex());
        identityClaims.put(AbstractUser.USER_SESSION_CLAIM_HEAD_PORTRAIT_PATH, user.getHeadPortraitPath());
        identityClaims.put(AbstractUser.USER_SESSION_CLAIM_BIRTHDAY, user.getBirthday());
    }

    /**
     * 保存设备信息并加载
     *
     * @param user  用户
     * @param token 票据
     */
    private void saveDeviceInfoAndLoad(AbstractUser user, DefaultAutumnUser sessionUser, TAuthenticationToken token) {
        AutumnUserDeviceInfo userDeviceInfo = this.autumnDeviceEncode.createStorage(sessionUser, token.getCredentialsDeviceInfo());
        UserDeviceAuthLogin authLogin = AutoMapUtils.map(userDeviceInfo, UserDeviceAuthLogin.class);
        authLogin.setToken(userDeviceInfo.getDeviceToken());
        authLogin.setUserId(user.getId());
        authLogin = this.authorizationService.addOrUpdateUserDeviceInfo(authLogin);
        this.setSessionUserDeviceInfo(sessionUser, authLogin);
    }

    /**
     * 设置会话用户设置
     *
     * @param sessionUser 会话
     * @param authLogin   授权登录
     */
    protected void setSessionUserDeviceInfo(DefaultAutumnUser sessionUser, UserDeviceAuthLogin authLogin) {
        DefaultAutumnUserDeviceInfo deviceInfo = AutoMapUtils.map(authLogin, DefaultAutumnUserDeviceInfo.class);
        deviceInfo.setDeviceToken(authLogin.getToken());
        sessionUser.setDeviceInfo(deviceInfo);
    }

    /**
     * 创建错误消息
     *
     * @param user          用户
     * @param token         票据
     * @param statusMessage 状态消息
     * @return
     */
    protected LoginAuditedLogMessage createFailLogMessage(AbstractUser user, TAuthenticationToken token, String statusMessage) {
        ToeknAuditedLog auditedLog = token.createAuditedLog();
        LoginAuditedLogMessage msg = new LoginAuditedLogMessage();
        msg.setUserId(user.getId());
        msg.setUserName(user.getUserName());
        msg.setUserAccount(auditedLog.getUserAccount());
        msg.setProvider(auditedLog.getProvider());
        msg.setProviderKey(auditedLog.getProviderKey());
        msg.setSuccess(false);
        msg.setStatusMessage(statusMessage);
        return msg;
    }

    /**
     * 创建用户
     *
     * @param user 用户
     * @return
     */
    protected TupleTwo<AbstractUser, DefaultAutumnUser> createUser(AbstractUser user) {
        DefaultAutumnUser autumnUser = new DefaultAutumnUser();
        autumnUser.setId(user.getId());
        autumnUser.setPassword(user.getPassword());
        autumnUser.setStatus(user.getStatus());
        autumnUser.setUserName(user.getUserName());
        return new TupleTwo<>(user, autumnUser);
    }

    /**
     * 创建认证异常
     *
     * @param msg
     * @param throwMsg
     * @return
     */
    protected AutumnAccountCredentialsException createCredentialsException(String msg, String throwMsg) {
        logger.error(msg + ",无法登录,IP地址:" + this.clientInfoProvider.getClientIpAddress() + " 客户信息:"
                + this.clientInfoProvider.getBrowserInfo().getBrowserInfo());
        return new AutumnAccountCredentialsException(throwMsg);
    }
}
