package com.autumn.zero.common.library.configure;

import com.autumn.zero.common.library.application.callback.BusinessAgreementCallback;
import com.autumn.zero.common.library.application.callback.CommonDataDictionaryCallback;
import com.autumn.zero.common.library.application.callback.SystemInfoConfigCallback;
import com.autumn.zero.common.library.application.callback.impl.DefaultBusinessAgreementCallback;
import com.autumn.zero.common.library.application.callback.impl.DefaultCommonDataDictionaryCallback;
import com.autumn.zero.common.library.application.callback.impl.DefaultSystemInfoConfigCallback;
import com.autumn.zero.common.library.application.services.common.CommonDataDictionaryAppService;
import com.autumn.zero.common.library.application.services.common.RegionAppService;
import com.autumn.zero.common.library.application.services.common.impl.CommonDataDictionaryAppServiceImpl;
import com.autumn.zero.common.library.application.services.common.impl.RegionAppServiceImpl;
import com.autumn.zero.common.library.application.services.sys.BusinessAgreementAppService;
import com.autumn.zero.common.library.application.services.sys.SystemHelpDocumentAppService;
import com.autumn.zero.common.library.application.services.sys.SystemInfoConfigAppService;
import com.autumn.zero.common.library.application.services.sys.impl.BusinessAgreementAppServiceImpl;
import com.autumn.zero.common.library.application.services.sys.impl.SystemHelpDocumentAppServiceImpl;
import com.autumn.zero.common.library.application.services.sys.impl.SystemInfoConfigAppServiceImpl;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 公共运行库配置
 *
 * @Description TODO
 * @Author 老码农
 * @Date 2019-07-25 16:49
 */
@Configuration
public class AutumnZeroCommonLibraryConfiguration {

    /**
     * 公共字典默认回调
     *
     * @return
     */
    @Bean("zeroDefaultCommonDataDictionaryCallback")
    @ConditionalOnMissingBean(CommonDataDictionaryCallback.class)
    public CommonDataDictionaryCallback zeroDefaultCommonDataDictionaryCallback() {
        return new DefaultCommonDataDictionaryCallback();
    }

    /**
     * 系统信息默认回调
     *
     * @return
     */
    @Bean("zeroDefaultSystemInfoConfigCallback")
    @ConditionalOnMissingBean(SystemInfoConfigCallback.class)
    public SystemInfoConfigCallback zeroDefaultSystemInfoConfigCallback() {
        return new DefaultSystemInfoConfigCallback();
    }

    /**
     * 业务协议默认回调
     *
     * @return
     */
    @Bean("zeroDefaultBusinessAgreementCallback")
    @ConditionalOnMissingBean(BusinessAgreementCallback.class)
    public BusinessAgreementCallback zeroDefaultBusinessAgreementCallback() {
        return new DefaultBusinessAgreementCallback();
    }

    /**
     * 系统信息配置应用服务
     *
     * @return
     */
    @Bean("zeroSystemInfoConfigAppService")
    @ConditionalOnMissingBean(SystemInfoConfigAppService.class)
    public SystemInfoConfigAppService zeroSystemInfoConfigAppService() {
        return new SystemInfoConfigAppServiceImpl();
    }

    /**
     * 公共字典应用服务
     *
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(CommonDataDictionaryAppService.class)
    public CommonDataDictionaryAppService zeroCommonDataDictionaryAppService() {
        return new CommonDataDictionaryAppServiceImpl();
    }

    /**
     * 行政区应用服务抽象
     *
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(RegionAppService.class)
    public RegionAppService zeroRegionAppService() {
        return new RegionAppServiceImpl();
    }

    /**
     * 系统帮助文档
     *
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(SystemHelpDocumentAppService.class)
    public SystemHelpDocumentAppService zeroSystemHelpDocumentAppService() {
        return new SystemHelpDocumentAppServiceImpl();
    }

    /**
     * 业务协议
     *
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(BusinessAgreementAppService.class)
    public BusinessAgreementAppService zeroBusinessAgreementAppService() {
        return new BusinessAgreementAppServiceImpl();
    }
}
