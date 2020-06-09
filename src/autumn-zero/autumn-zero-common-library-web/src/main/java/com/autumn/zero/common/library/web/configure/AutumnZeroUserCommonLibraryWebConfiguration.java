package com.autumn.zero.common.library.web.configure;

import com.autumn.zero.common.library.application.services.common.CommonDataDictionaryAppService;
import com.autumn.zero.common.library.application.services.common.RegionAppService;
import com.autumn.zero.common.library.application.services.sys.BusinessAgreementAppService;
import com.autumn.zero.common.library.application.services.sys.SystemHelpDocumentAppService;
import com.autumn.zero.common.library.application.services.sys.SystemInfoConfigAppService;
import com.autumn.zero.common.library.web.controllers.user.common.UserDataDictionaryController;
import com.autumn.zero.common.library.web.controllers.user.common.UserRegionController;
import com.autumn.zero.common.library.web.controllers.user.common.UserSystemController;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 用户端公共Web配置
 * <p>
 * </p>
 *
 * @description TODO
 * @author: 老码农
 * @create: 2020-03-28 14:51
 **/
@Configuration
public class AutumnZeroUserCommonLibraryWebConfiguration {

    /**
     * 客户端行政区
     *
     * @param service
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(UserRegionController.class)
    @ConditionalOnBean(value = {RegionAppService.class})
    public UserRegionController autumnZeroUserRegionController(RegionAppService service) {
        return new UserRegionController(service);
    }

    /**
     * 客户端公共字典
     *
     * @param service
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(UserDataDictionaryController.class)
    @ConditionalOnBean(value = {CommonDataDictionaryAppService.class})
    public UserDataDictionaryController autumnZeroUserDataDictionaryController(CommonDataDictionaryAppService service) {
        return new UserDataDictionaryController(service);
    }

    /**
     * 客户端公用系统
     *
     * @param infoConfigAppService
     * @param documentAppService
     * @param agreementAppService
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(UserSystemController.class)
    @ConditionalOnBean(value = {SystemInfoConfigAppService.class, SystemHelpDocumentAppService.class, BusinessAgreementAppService.class})
    public UserSystemController autumnZeroUserSystemController(SystemInfoConfigAppService infoConfigAppService, SystemHelpDocumentAppService documentAppService, BusinessAgreementAppService agreementAppService) {
        return new UserSystemController(infoConfigAppService, documentAppService, agreementAppService);
    }
}
