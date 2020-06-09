package com.autumn.zero.authorization.application.services.impl;

import com.autumn.application.dto.input.AdvancedQueryInput;
import com.autumn.application.service.AbstractQueryApplicationService;
import com.autumn.exception.ExceptionUtils;
import com.autumn.util.excel.utils.ExcelUtils;
import com.autumn.zero.authorization.application.dto.UserOperationLogOutput;
import com.autumn.zero.authorization.application.services.UserOperationLogAppService;
import com.autumn.zero.authorization.entities.common.log.UserOperationLog;
import com.autumn.zero.authorization.repositories.common.log.UserOperationLogRepository;
import com.autumn.zero.file.storage.application.dto.TemporaryFileInformationDto;
import com.autumn.zero.file.storage.application.services.FileUploadManager;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 用户操作日志实现
 *
 * @Description TODO
 * @Author 老码农
 * @Date 2019-07-28 2:25
 */
public class UserOperationLogAppServiceImpl extends AbstractQueryApplicationService<Long, UserOperationLog, UserOperationLogRepository, UserOperationLogOutput, UserOperationLogOutput>
        implements UserOperationLogAppService {

    @Autowired
    protected FileUploadManager fileUploadManager;

    public UserOperationLogAppServiceImpl() {
        this.getSearchMembers().add(UserOperationLog.FIELD_USER_NAME);
        this.getSearchMembers().add(UserOperationLog.FIELD_MODULE_NAME);
        this.getSearchMembers().add(UserOperationLog.FIELD_OPERATION_NAME);
    }

    @Override
    public String getModuleName() {
        return "用户操作日志";
    }

    @Override
    public void deleteAll() {
        this.getQueryRepository().truncate();
        this.getAuditedLogger().addLog(this, "清除日志", "清除全部操作日志");
    }

    @Override
    public TemporaryFileInformationDto exportFileByExcel(AdvancedQueryInput input) {
        Workbook workbook = this.exportByExcel(input);
        try {
            return fileUploadManager.saveTemporaryFileByWorkbook(this.getModuleName() + ExcelUtils.EXCEL_JOIN_EXTENSION_NAME, workbook);
        } catch (Exception e) {
            throw ExceptionUtils.throwApplicationException(e.getMessage(), e);
        }
    }
}
