package com.autumn.zero.authorization.application.services;

import com.autumn.application.service.QueryApplicationService;
import com.autumn.zero.authorization.application.dto.UserOperationLogOutput;
import com.autumn.zero.file.storage.application.services.FileExportAppService;

/**
 * 用户操作日志应服务
 *
 * @Description TODO
 * @Author 老码农
 * @Date 2019-07-28 2:24
 */
public interface UserOperationLogAppService extends QueryApplicationService<Long, UserOperationLogOutput, UserOperationLogOutput> , FileExportAppService {

    /**
     * 全部清除
     */
    void deleteAll();
}
