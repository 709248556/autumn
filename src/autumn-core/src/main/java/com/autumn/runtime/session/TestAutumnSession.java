package com.autumn.runtime.session;

import com.autumn.runtime.session.claims.DefaultIdentityClaims;
import com.autumn.runtime.session.claims.IdentityClaims;

import java.util.Collection;
import java.util.Map;

/**
 * 测试会话
 *
 * @author 老码农 2018-04-09 20:39:49
 */
public class TestAutumnSession extends NullAutumnSession implements AutumnSession {

    private Long userId;
    private String userName;
    private String identityType;
    private IdentityClaims identityClaims;
    private Map<String, Object> identityClaimsMap;

    /**
     *
     */
    public TestAutumnSession() {
        this.identityClaims = DefaultIdentityClaims.DEFAULT_IDENTITY_CLAIMS;
    }

    /**
     * @param userId
     * @param userName
     */
    public TestAutumnSession(Long userId, String userName) {
        this.userId = userId;
        this.userName = userName;
        this.identityClaims = DefaultIdentityClaims.DEFAULT_IDENTITY_CLAIMS;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public Long getUserId() {
        return this.userId;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public String getUserName() {
        return this.userName;
    }

    public void setIdentityType(String identityType) {
        this.identityType = identityType;
    }

    @Override
    public String getIdentityType() {
        return this.identityType;
    }

    /**
     * 获取身份声明
     *
     * @return
     */
    public Map<String, Object> getIdentityClaimsMap() {
        return identityClaimsMap;
    }

    /**
     * 设置身份声明
     *
     * @param identityClaimsMap
     */
    public void setIdentityClaimsMap(Map<String, Object> identityClaimsMap) {
        this.identityClaimsMap = identityClaimsMap;
        this.setIdentityAuthoritys();
    }

    private void setIdentityAuthoritys() {
        this.identityClaims = new DefaultIdentityClaims(this.getIdentityClaimsMap());
    }

    @Override
    public IdentityClaims getIdentityClaims() {
        return this.identityClaims;
    }

    @Override
    public boolean isOrPermittedAll(String... permissions) {
        return true;
    }

    @Override
    public boolean isOrPermittedAll(Collection<String> permissions) {
        return true;
    }

    @Override
    public boolean isAuthenticated() {
        return true;
    }

    @Override
    public boolean isPermitted(String permission) {
        return true;
    }

    @Override
    public boolean isPermittedAll(String... permissions) {
        return true;
    }

    @Override
    public boolean isPermittedAll(Collection<String> permissions) {
        return true;
    }

    @Override
    public boolean hasOrAllRoles(String... roles) {
        return true;
    }

    @Override
    public boolean hasOrAllRoles(Collection<String> roles) {
        return true;
    }

    @Override
    public boolean hasAllRoles(String... roles) {
        return true;
    }

    @Override
    public boolean hasAllRoles(Collection<String> roles) {
        return true;
    }

    @Override
    public boolean hasRole(String role) {
        return true;
    }
}
