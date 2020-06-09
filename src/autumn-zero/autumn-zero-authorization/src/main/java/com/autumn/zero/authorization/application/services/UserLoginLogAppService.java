package com.autumn.zero.authorization.application.services;

import com.autumn.application.service.QueryApplicationService;
import com.autumn.zero.authorization.application.dto.UserLoginLogOutput;
import com.autumn.zero.file.storage.application.services.FileExportAppService;

/**
 * 用户登录日志应用服务
 *
 * @author 老码农 2018-12-11 15:58:02
 */
public interface UserLoginLogAppService extends QueryApplicationService<Long, UserLoginLogOutput, UserLoginLogOutput> , FileExportAppService {

    /**
     * 全部清除
     */
    void deleteAll();
}
