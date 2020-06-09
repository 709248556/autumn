package com.autumn.security.token;

/**
 * 第三方登录票据
 *
 * @author 老码农 2018-12-06 02:41:50
 */
public class ExternalProviderAuthenticationToken extends AbstractAutumnAuthenticationToken {

    /**
     *
     */
    private static final long serialVersionUID = 4240355263745558327L;

    private final String provider;
    private final String providerKey;

    /**
     * @param provider
     * @param providerKey
     */
    public ExternalProviderAuthenticationToken(String provider, String providerKey) {
        this.provider = provider;
        this.providerKey = providerKey;
    }

    /**
     * 获取提供程序
     *
     * @return
     */
    public String getProvider() {
        return provider;
    }

    /**
     * 获取提供程序键
     *
     * @return
     */
    public String getProviderKey() {
        return providerKey;
    }

    @Override
    public Object getPrincipal() {
        return this.getProvider();
    }

    @Override
    public Object getCredentials() {
        return this.getProviderKey();
    }

    @Override
    public ToeknAuditedLog createAuditedLog() {
        ToeknAuditedLog log = new ToeknAuditedLog();
        log.setUserAccount("");
        log.setProvider(this.getProvider());
        log.setProviderKey(this.getProviderKey());
        log.setFailStatusMessage("提供程序与键不匹配。");
        return log;
    }
}
