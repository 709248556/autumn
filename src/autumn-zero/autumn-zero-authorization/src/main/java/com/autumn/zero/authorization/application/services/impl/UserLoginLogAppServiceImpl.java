package com.autumn.zero.authorization.application.services.impl;

import com.autumn.application.dto.input.AdvancedQueryInput;
import com.autumn.application.service.AbstractQueryApplicationService;
import com.autumn.exception.ExceptionUtils;
import com.autumn.util.excel.utils.ExcelUtils;
import com.autumn.zero.authorization.application.dto.UserLoginLogOutput;
import com.autumn.zero.authorization.application.services.UserLoginLogAppService;
import com.autumn.zero.authorization.entities.common.log.UserLoginLog;
import com.autumn.zero.authorization.repositories.common.log.UserLoginLogRepository;
import com.autumn.zero.file.storage.application.dto.TemporaryFileInformationDto;
import com.autumn.zero.file.storage.application.services.FileUploadManager;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 用户登录日志
 *
 * @author 老码农 2018-12-11 16:22:55
 */
public class UserLoginLogAppServiceImpl
        extends AbstractQueryApplicationService<Long, UserLoginLog, UserLoginLogRepository, UserLoginLogOutput, UserLoginLogOutput>
        implements UserLoginLogAppService {

    @Autowired
    protected FileUploadManager fileUploadManager;

    @Override
    public String getModuleName() {
        return "用户登录日志";
    }

    public UserLoginLogAppServiceImpl() {
        this.getSearchMembers().add(UserLoginLog.FIELD_USER_NAME);
        this.getSearchMembers().add(UserLoginLog.FIELD_USER_ACCOUNT);
        this.getSearchMembers().add(UserLoginLog.FIELD_CLIENT_IP_ADDRESS);
    }

    @Override
    public void deleteAll() {
        this.getQueryRepository().truncate();
        this.getAuditedLogger().addLog(this, "清除日志", "清除全部登录日志");
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
