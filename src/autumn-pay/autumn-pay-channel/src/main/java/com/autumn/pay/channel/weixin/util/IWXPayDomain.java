package com.autumn.pay.channel.weixin.util;

/**
 * 域名管理，实现主备域名自动切换
 * @author ycg
 */
public abstract interface IWXPayDomain {

    /**
     * 获取域名
     * @param config 配置
     * @return 域名
     */
    abstract DomainInfo getDomain(final WXPayConfig config);

    static class DomainInfo{
        public String domain;          // 域名
        public boolean primaryDomain;  // 该域名是否为主域名。例如:api.mch.weixin.qq.com为主域名
        public DomainInfo(String domain, boolean primaryDomain) {
            this.domain = domain;
            this.primaryDomain = primaryDomain;
        }

        @Override
        public String toString() {
            return "DomainInfo{" +
                    "domain='" + domain + '\'' +
                    ", primaryDomain=" + primaryDomain +
                    '}';
        }
    }

}