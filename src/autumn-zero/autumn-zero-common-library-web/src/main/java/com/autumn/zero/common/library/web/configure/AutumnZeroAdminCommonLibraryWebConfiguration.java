package com.autumn.zero.common.library.web.configure;

import com.autumn.zero.common.library.application.services.common.CommonDataDictionaryAppService;
import com.autumn.zero.common.library.application.services.common.RegionAppService;
import com.autumn.zero.common.library.application.services.sys.BusinessAgreementAppService;
import com.autumn.zero.common.library.application.services.sys.SystemHelpDocumentAppService;
import com.autumn.zero.common.library.application.services.sys.SystemInfoConfigAppService;
import com.autumn.zero.common.library.web.controllers.admin.common.AdminDataDictionaryController;
import com.autumn.zero.common.library.web.controllers.admin.common.AdminRegionController;
import com.autumn.zero.common.library.web.controllers.admin.sys.AdminBusinessAgreementController;
import com.autumn.zero.common.library.web.controllers.admin.sys.AdminSystemHelpDocumentController;
import com.autumn.zero.common.library.web.controllers.admin.sys.AdminSystemInfoConfigController;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 管理端公共库配置
 * <p>
 * </p>
 *
 * @description TODO
 * @author: 老码农
 * @create: 2020-03-27 16:59
 **/
@Configuration
public class AutumnZeroAdminCommonLibraryWebConfiguration {

    /**
     * 数据字典管理端控制器
     *
     * @param service
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(AdminDataDictionaryController.class)
    @ConditionalOnBean(value = {CommonDataDictionaryAppService.class})
    public AdminDataDictionaryController autumnZeroDataDictionaryAdminController(CommonDataDictionaryAppService service) {
        return new AdminDataDictionaryController(service);
    }

    /**
     * 行政区管理端控制器
     *
     * @param service
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(AdminRegionController.class)
    @ConditionalOnBean(value = {RegionAppService.class})
    public AdminRegionController autumnZeroRegionAdminController(RegionAppService service) {
        return new AdminRegionController(service);
    }

    /**
     * 系统帮助文档管理
     *
     * @param service
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(AdminSystemHelpDocumentController.class)
    @ConditionalOnBean(value = {SystemHelpDocumentAppService.class})
    public AdminSystemHelpDocumentController autumnZeroAdminSystemHelpDocumentController(SystemHelpDocumentAppService service) {
        return new AdminSystemHelpDocumentController(service);
    }

    /**
     * 系统信息配置
     *
     * @param service            服务
     * @param documentAppService
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(AdminSystemInfoConfigController.class)
    @ConditionalOnBean(value = {SystemInfoConfigAppService.class, SystemHelpDocumentAppService.class})
    public AdminSystemInfoConfigController autumnZeroAdminSystemInfoConfigController(SystemInfoConfigAppService service, SystemHelpDocumentAppService documentAppService) {
        return new AdminSystemInfoConfigController(service, documentAppService);
    }

    /**
     * 用户协议后端管理
     *
     * @param service 服务
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(AdminBusinessAgreementController.class)
    @ConditionalOnBean(value = {BusinessAgreementAppService.class})
    public AdminBusinessAgreementController autumnZeroAdminBusinessAgreementController(BusinessAgreementAppService service) {
        return new AdminBusinessAgreementController(service);
    }



}
