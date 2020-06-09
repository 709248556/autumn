package com.autumn.application.service;

import com.autumn.application.ApplicationModule;
import com.autumn.application.dto.input.DataImportOptionInput;
import com.autumn.application.dto.input.ExcelImportOptionInput;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.InputStream;
import java.util.List;

/**
 * 数据导入应用服务
 *
 * @param <TImportTemplate> 导入模板类型
 * @Description TODO
 * @Author 老码农
 * @Date 2019-08-05 18:35
 */
public interface DataImportApplicationService<TImportTemplate> extends ApplicationModule {

    /**
     * 默认导入页大小
     */
    public static final int DEFAULT_IMPORT_PAGE_SIZE = 1000;

    /**
     * 获取导入类型
     *
     * @return
     */
    Class<TImportTemplate> getImportTemplateClass();

    /**
     * Excel 导入模板
     *
     * @return
     */
    Workbook excelImportTemplate();

    /**
     * 获取导入的页大小
     * <p>
     * 采用批量导入，数据量过多时则可采用分页方式导入，默认每页的大小
     * </p>
     *
     * @return
     */
    int getImportPageSize();

    /**
     * Excel 导入
     *
     * @param inputStream Excel流
     * @param option      选项
     * @return 返回成功的记录数
     */
    int excelImport(InputStream inputStream, ExcelImportOptionInput option);

    /**
     * 数据导入
     *
     * @param items  项目集合
     * @param option 选项
     * @return 返回成功的记录数
     */
    int dataImport(List<TImportTemplate> items, DataImportOptionInput option);


}
