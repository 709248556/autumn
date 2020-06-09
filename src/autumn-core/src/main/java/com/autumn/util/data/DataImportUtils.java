package com.autumn.util.data;

import com.autumn.application.dto.input.DataImportOptionInput;
import com.autumn.application.dto.input.ExcelImportOptionInput;
import com.autumn.application.service.DataImportApplicationService;
import com.autumn.domain.entities.Entity;
import com.autumn.domain.repositories.EntityRepository;
import com.autumn.exception.ExceptionUtils;
import com.autumn.util.StringUtils;
import com.autumn.util.excel.exports.MapExportInfo;
import com.autumn.util.excel.sheet.WorkSheetInfo;
import com.autumn.util.excel.utils.ExcelUtils;
import com.autumn.util.function.FunctionTwoResult;
import org.apache.commons.io.IOUtils;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

/**
 * 数据导入帮助
 *
 * @Description TODO
 * @Author 老码农
 * @Date 2019-08-05 18:59
 */
public class DataImportUtils {

    /**
     * 行分隔正则
     */
    public final static String ROW_SPLIT_REGEX = "\n";

    /**
     * 列分隔正则
     */
    public final static String COL_SPLIT_REGEX = "\\|";

    /**
     * Excel 导入数据开始行
     *
     * @param clazz 类型
     * @return
     */
    public static int excelImportDataBeginRow(Class<?> clazz) {
        WorkSheetInfo workSheetInfo = ExcelUtils.getWorkSheetInfo(clazz);
        return workSheetInfo.getDataBeginRow(true);
    }

    /**
     * Excel 导入数据开始行
     *
     * @param clazz      类型
     * @param headerName 标题名称与表名称
     * @return
     */
    public static int excelImportDataBeginRow(Class<?> clazz, String headerName) {
        WorkSheetInfo workSheetInfo = ExcelUtils.getWorkSheetInfo(clazz, headerName);
        workSheetInfo.setSheetName(headerName);
        return workSheetInfo.getDataBeginRow(true);
    }

    /**
     * Excel 导入模板
     *
     * @param clazz      类型
     * @param headerName 标题名称与表名称
     * @return
     */
    public static Workbook excelImportTemplate(Class<?> clazz, String headerName) {
        WorkSheetInfo workSheetInfo = ExcelUtils.getWorkSheetInfo(clazz, headerName);
        workSheetInfo.setSheetName(headerName);
        MapExportInfo exportInfo = new MapExportInfo();
        List<Map<String, Object>> items = new ArrayList<>(16);
        for (int i = 1; i <= 10; i++) {
            items.add(new HashMap<>(10));
        }
        exportInfo.setItems(items);
        return workSheetInfo.createExportWorkbook(exportInfo, true);
    }

    /**
     * Excel 导入模板
     *
     * @param service           服务
     * @param <TImportTemplate> 模板类型
     * @return
     */
    public static <TImportTemplate> Workbook excelImportTemplate(DataImportApplicationService<TImportTemplate> service) {
        return DataImportUtils.excelImportTemplate(service.getImportTemplateClass(), service.getModuleName());
    }

    /**
     * Excel 导入
     *
     * @param input             输入流
     * @param sheetName         工作表名称，未指定则自动匹配第一个符合的工作表
     * @param clazz             对象类型
     * @param <TImportTemplate> 类型
     * @return
     */
    public static <TImportTemplate> List<TImportTemplate> excelImport(InputStream input, String sheetName, Class<TImportTemplate> clazz) {
        try {
            return ExcelUtils.importData(input, sheetName, clazz);
        } finally {
            IOUtils.closeQuietly(input);
        }
    }

    /**
     * 导入分页
     *
     * @param items    项目集合
     * @param pageSize 页大小
     * @param <T>      类型
     * @return
     */
    public static <T> List<List<T>> importPages(List<T> items, int pageSize) {
        List<List<T>> pages;
        int rows = items.size();
        if (rows <= pageSize) {
            pages = new ArrayList<>(1);
            pages.add(items);
        } else {
            int pageCount = rows / pageSize;
            if (rows % pageSize != 0) {
                pageCount++;
            }
            if (pageCount < 1) {
                pageCount = 1;
            }
            pages = new ArrayList<>(pageCount);
            for (int page = 1; page <= pageCount; page++) {
                int indexBegin = (page - 1) * pageSize;
                int indexEnd = indexBegin + pageSize - 1;
                List<T> pageItems = new ArrayList<>(pageSize);
                for (int i = indexBegin; i <= indexEnd; i++) {
                    if (rows < i + 1) {
                        break;
                    }
                    pageItems.add(items.get(i));
                }
                if (pageItems.size() > 0) {
                    pages.add(pageItems);
                }
            }
        }
        return pages;
    }

    /**
     * 查找工作表
     *
     * @param sheets 工作表集合
     * @param name   表名称
     * @return
     */
    private static final String findSheetName(List<String> sheets, String name) {
        for (String sheet : sheets) {
            if (sheet.trim().equalsIgnoreCase(name.trim())) {
                return sheet;
            }
        }
        return null;
    }

    /**
     * 数据导入并更新
     *
     * @param service           服务
     * @param inputStream       导入流
     * @param option            选项
     * @param <TImportTemplate> 模板类型
     * @return
     */
    public static <TImportTemplate> int excelImportForUpdate(DataImportApplicationService<TImportTemplate> service,
                                                             InputStream inputStream,
                                                             ExcelImportOptionInput option) {
        try {
            if (option == null) {
                option = ExcelImportOptionInput.READ_ONLY;
            }
            Workbook workbook = ExcelUtils.createWorkbook(inputStream);
            List<String> sheets = ExcelUtils.sheets(workbook);
            String sheetName;
            if (!StringUtils.isNullOrBlank(option.getSheetName())) {
                sheetName = findSheetName(sheets, option.getSheetName());
                if (sheetName == null) {
                    ExceptionUtils.throwValidationException("Excel文件不存在工作表[" + option.getSheetName() + "]。");
                }
            } else {
                sheetName = findSheetName(sheets, service.getModuleName());
            }
            WorkSheetInfo workSheetInfo = ExcelUtils.getWorkSheetInfo(service.getImportTemplateClass());
            workSheetInfo.setSheetName(sheetName);
            List<TImportTemplate> items = workSheetInfo.createObjectList(workbook, sheetName, service.getImportTemplateClass());
            return service.dataImport(items, option);
        } finally {
            IOUtils.closeQuietly(inputStream);
        }
    }

    /**
     * 数据导入并更新
     * <p>
     * 不支持一对多导入，是采用批量导入
     * </p>
     *
     * @param service           服务
     * @param repository        仓储
     * @param items             项目集合
     * @param option            选项
     * @param handle            处理器
     * @param <TEntity>         实体类型
     * @param <TKey>            主键类型
     * @param <TRepository>     仓储类型
     * @param <TImportTemplate>
     * @return
     */
    public static <TEntity extends Entity<TKey>, TKey extends Serializable, TRepository extends EntityRepository<TEntity, TKey>, TImportTemplate>
    int excelImportForUpdate(DataImportApplicationService<TImportTemplate> service, TRepository repository, List<TImportTemplate> items, DataImportOptionInput option,
                             FunctionTwoResult<List<TImportTemplate>, DataImportOptionInput, List<TEntity>> handle) {
        return DataImportUtils.excelImportForUpdate(service, repository, items, option, handle, null);
    }

    /**
     * 数据导入并更新
     * <p>
     * 不支持一对多导入，是采用批量导入
     * </p>
     *
     * @param service           服务
     * @param repository        仓储
     * @param items             项目集合
     * @param option            选项
     * @param handle            处理器
     * @param entityConsumer    实体消费
     * @param <TEntity>         实体类型
     * @param <TKey>            主键类型
     * @param <TRepository>     仓储类型
     * @param <TImportTemplate>
     * @return
     */
    public static <TEntity extends Entity<TKey>, TKey extends Serializable, TRepository extends EntityRepository<TEntity, TKey>, TImportTemplate>
    int excelImportForUpdate(DataImportApplicationService<TImportTemplate> service, TRepository repository, List<TImportTemplate> items, DataImportOptionInput option,
                             FunctionTwoResult<List<TImportTemplate>, DataImportOptionInput, List<TEntity>> handle, Consumer<List<TEntity>> entityConsumer) {
        if (items == null || items.size() == 0) {
            return 0;
        }
        List<TEntity> entities = handle.apply(items, option);
        if (entities == null || entities.size() == 0) {
            if (entityConsumer != null) {
                entityConsumer.accept(entities);
            }
            return 0;
        }
        int pageSize = service.getImportPageSize();
        if (entities.size() > pageSize) {
            List<List<TEntity>> pages = DataImportUtils.importPages(entities, pageSize);
            for (List<TEntity> page : pages) {
                repository.insertByList(page);
            }
        } else {
            repository.insertByList(entities);
        }
        if (entityConsumer != null) {
            entityConsumer.accept(entities);
        }
        return entities.size();
    }


    /**
     * 获取Excel行数组
     *
     * @param cellValue 单元格的值
     * @return 根据换行符\n查返回构成的行数组
     */
    public static String[] toExcelRowArray(String cellValue) {
        if (cellValue == null) {
            return new String[0];
        }
        return cellValue.split(ROW_SPLIT_REGEX);
    }

    /**
     * 获取Excel列数组
     *
     * @param rowValue 行值
     * @return 根据符号“|”查返回当前行构成的列数组
     */
    public static String[] toExcelColArray(String rowValue) {
        if (rowValue == null) {
            return new String[0];
        }
        return rowValue.split(COL_SPLIT_REGEX);
    }
}
