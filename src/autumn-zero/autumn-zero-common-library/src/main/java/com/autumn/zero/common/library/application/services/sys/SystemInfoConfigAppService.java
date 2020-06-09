package com.autumn.zero.common.library.application.services.sys;

import com.autumn.application.service.ConfigApplicationService;
import com.autumn.zero.common.library.application.dto.sys.config.SystemInfoConfigInput;
import com.autumn.zero.common.library.application.dto.sys.config.SystemInfoConfigOutput;
import com.autumn.zero.file.storage.application.services.FileUploadAppService;

/**
 * 系统信息配置应用服务
 *
 * @Description TODO
 * @Author 老码农
 * @Date 2019-07-31 2:53
 */
public interface SystemInfoConfigAppService extends ConfigApplicationService<SystemInfoConfigInput, SystemInfoConfigOutput>, FileUploadAppService {



}
