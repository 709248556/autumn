package com.autumn.zero.file.storage.application.services;

import com.autumn.application.service.DataImportApplicationService;
import com.autumn.zero.file.storage.application.dto.TemporaryFileInformationDto;

/**
 * 数据导入文件应用服务
 *
 * @param <ImportTemplate> 导入模板
 * @Description TODO
 * @Author 老码农
 * @Date 2019-08-06 3:56
 */
public interface DataImportFileAppService<ImportTemplate> extends DataImportApplicationService<ImportTemplate> {

    /**
     * Excel 导入模板的文件信息
     *
     * @return
     */
    TemporaryFileInformationDto excelImportTemplateForFileInformation();
}
