package com.autumn.zero.common.library.application.services.sys.impl;

import com.autumn.zero.common.library.application.callback.CommonFileUploadConfigCallback;
import com.autumn.zero.common.library.application.callback.SystemInfoConfigCallback;
import com.autumn.zero.common.library.application.dto.sys.config.SystemInfoConfigInput;
import com.autumn.zero.common.library.application.dto.sys.config.SystemInfoConfigOutput;
import com.autumn.zero.common.library.application.services.sys.SystemInfoConfigAppService;
import com.autumn.zero.common.library.application.services.sys.config.AbstractCommonFileUploadConfigAppService;
import com.autumn.zero.file.storage.application.services.FileUploadAppService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 系统信息配置应用服务实现
 *
 * @Description TODO
 * @Author 老码农
 * @Date 2019-07-31 2:55
 */
public class SystemInfoConfigAppServiceImpl extends AbstractCommonFileUploadConfigAppService<SystemInfoConfigInput, SystemInfoConfigOutput>
        implements SystemInfoConfigAppService, FileUploadAppService {

    @Autowired
    private SystemInfoConfigCallback callback;

    public SystemInfoConfigAppServiceImpl() {

    }

    @Override
    protected CommonFileUploadConfigCallback getConfigCallback() {
        return this.callback;
    }

    @Override
    public long getUploadTargetId() {
        return 1L;
    }


    @Override
    protected SystemInfoConfigOutput createOutputInstance() {
        return new SystemInfoConfigOutput();
    }



}
