package com.autumn.zero.authorization.credential;

import com.autumn.runtime.session.AutumnUser;
import com.autumn.runtime.session.claims.IdentityClaims;
import com.autumn.security.AutumnAccountCredentialsException;
import com.autumn.security.credential.AbstractAutumnUserCredentialsService;
import com.autumn.security.token.AutumnAuthenticationToken;
import com.autumn.util.StringUtils;
import com.autumn.util.tuple.TupleTwo;
import com.autumn.zero.authorization.credential.realm.UserCredentialsRealm;
import com.autumn.zero.authorization.entities.common.AbstractRole;
import com.autumn.zero.authorization.entities.common.AbstractUser;
import com.autumn.zero.authorization.entities.common.query.UserClaimQuery;
import com.autumn.zero.authorization.entities.common.query.UserPermissionQuery;
import com.autumn.zero.authorization.services.AuthorizationServiceBase;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.DisposableBean;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 用户认证服务
 *
 * @param <TUser> 用户类型
 * @param <TRole> 角色类型
 * @author 老码农 2018-12-08 13:40:11
 */
public class UserCredentialsService<TUser extends AbstractUser, TRole extends AbstractRole>
        extends AbstractAutumnUserCredentialsService
        implements AuthUserCredentialsService, DisposableBean {

    /**
     * 日志
     */
    protected final Log logger = LogFactory.getLog(this.getClass());

    /**
     * 获取授权服务
     *
     * @return
     */
    protected final AuthorizationServiceBase<TUser, TRole> authorizationService;

    /**
     * 认证 Realm Map(key is AutumnAuthenticationToken class)
     */
    private final Map<Class, UserCredentialsRealm> credentialsRealmMap = new ConcurrentHashMap<>(5);

    /**
     * 实例化
     *
     * @param authorizationService 授权服务
     */
    public UserCredentialsService(AuthorizationServiceBase<TUser, TRole> authorizationService) {
        this.authorizationService = authorizationService;
    }

    @Override
    public void registerRealm(UserCredentialsRealm realm) {
        this.credentialsRealmMap.put(realm.getDefaultTokenClass(), realm);
    }

    /**
     * 获取认证 Realm
     *
     * @param authTokenClass
     * @return
     */
    protected final UserCredentialsRealm getCredentialsRealm(Class<?> authTokenClass) {
        return this.credentialsRealmMap.get(authTokenClass);
    }

    /**
     * 获取认证 Realm 集合
     *
     * @return
     */
    protected final Collection<UserCredentialsRealm> getCredentialsRealms() {
        return this.credentialsRealmMap.values();
    }

    /**
     * 查询认证 Realm
     *
     * @param token 票据
     * @return
     */
    protected final UserCredentialsRealm findCredentialsRealm(AutumnAuthenticationToken token) {
        UserCredentialsRealm realm = this.getCredentialsRealm(token.getClass());
        if (realm == null) {
            Collection<UserCredentialsRealm> realms = this.getCredentialsRealms();
            for (UserCredentialsRealm credentialsRealm : realms) {
                if (credentialsRealm.supports(token)) {
                    realm = credentialsRealm;
                    break;
                }
            }
        }
        return realm;
    }

    @Override
    public final AutumnUser doGetUserByToken(AutumnAuthenticationToken token) throws AutumnAccountCredentialsException {
        UserCredentialsRealm realm = this.findCredentialsRealm(token);
        if (realm == null) {
            throw new AutumnAccountCredentialsException("不支持的认证类型:" + token.getClass().getName());
        }
        TupleTwo<AbstractUser, AutumnUser> tupleTwo = realm.doGetUser(this, token);
        TUser user = (TUser) tupleTwo.getItem1();
        AutumnUser sessionUser = tupleTwo.getItem2();
        if (this.isLoadPermission()) {
            this.loadUserPermission(sessionUser);
        }
        if (this.isLoadClaim()) {
            this.loadUserClaim(sessionUser);
        }
        this.onLoadUser(token, user, sessionUser);
        return sessionUser;
    }

    /**
     * 加载用户
     *
     * @param token
     * @param user
     * @param sessionUser
     */
    protected void onLoadUser(AutumnAuthenticationToken token, TUser user, AutumnUser sessionUser) {

    }

    /**
     * 是否加载权限
     *
     * @return
     */
    protected boolean isLoadPermission() {
        return true;
    }

    /**
     * 是否加载声明
     *
     * @return
     */
    protected boolean isLoadClaim() {
        return true;
    }

    /**
     * 加载权限
     *
     * @param sessionUser
     */
    private void loadUserPermission(AutumnUser sessionUser) {
        Set<String> userPermissions = sessionUser.getPermissions();
        if (userPermissions == null) {
            return;
        }
        List<UserPermissionQuery> permissions = this.authorizationService.queryUserAllPermissions(sessionUser.getId());
        for (UserPermissionQuery permission : permissions) {
            if (permission.getIsGranted()) {
                if (StringUtils.isNullOrBlank(permission.getName())) {
                    userPermissions.add(permission.getResourcesId().toLowerCase());
                } else {
                    String permissionName = String.format("%s:%s", permission.getResourcesId(), permission.getName());
                    userPermissions.add(permissionName.toLowerCase());
                }
            }
        }
    }

    /**
     * 加载声明
     *
     * @param sessionUser
     */
    private void loadUserClaim(AutumnUser sessionUser) {
        IdentityClaims identityClaims = sessionUser.getIdentityClaims();
        if (identityClaims == null) {
            return;
        }
        List<UserClaimQuery> claims = this.authorizationService.queryUserAllClaims(sessionUser.getId());
        for (UserClaimQuery claim : claims) {
            identityClaims.put(claim.getClaimType().toLowerCase(), claim.getClaimValue());
        }
    }

    @Override
    public void destroy() throws Exception {
        this.credentialsRealmMap.clear();
    }
}
