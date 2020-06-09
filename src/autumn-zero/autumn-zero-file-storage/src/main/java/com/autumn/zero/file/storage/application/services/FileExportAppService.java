package com.autumn.zero.file.storage.application.services;

import com.autumn.application.dto.input.AdvancedQueryInput;
import com.autumn.zero.file.storage.application.dto.TemporaryFileInformationDto;

/**
 * 文件导出应用服务
 *
 * @Description TODO
 * @Author 老码农
 * @Date 2019-07-24 18:10
 */
public interface FileExportAppService {

    /**
     * Excel文件 导出
     *
     * @param input 输入
     * @return
     */
    TemporaryFileInformationDto exportFileByExcel(AdvancedQueryInput input);
}
