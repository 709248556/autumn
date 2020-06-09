package com.autumn.util.excel.workbook;

import com.autumn.util.excel.sheet.WorkSheetInfo;
import com.autumn.util.excel.column.ColumnInfo;
import com.autumn.util.excel.enums.CellType;
import com.autumn.util.excel.ExcelException;
import com.autumn.util.excel.imports.ImportAdapterInfo;
import com.autumn.util.excel.imports.ImportInfo;
import com.autumn.util.excel.imports.ImportProperty;
import com.autumn.util.function.FunctionResult;
import org.apache.commons.collections.map.CaseInsensitiveMap;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 工作簿导入字典
 */
public class WorkbookImportMap extends
        AbstractWorkbookImport<Map<String, Object>, ImportInfo<ImportAdapterInfo>, ImportAdapterInfo, ImportProperty> {

    /**
     * 是否区分大小写
     */
    private final boolean isIgnoreCase;

    /**
     * 实例化 WorkbookImportMap 类新实例
     *
     * @param workSheetInfo 工作表
     */
    public WorkbookImportMap(WorkSheetInfo workSheetInfo) {
        this(workSheetInfo, true, Map.class);
    }

    /**
     * 实例化 WorkbookImportMap 类新实例
     *
     * @param workSheetInfo
     * @param isIgnoreCase  忽略大小写
     * @param clazz         类型
     */
    @SuppressWarnings("unchecked")
    public WorkbookImportMap(WorkSheetInfo workSheetInfo, boolean isIgnoreCase, Class<?> clazz) {
        super(workSheetInfo, (Class<Map<String, Object>>) clazz);
        this.isIgnoreCase = isIgnoreCase;
    }

    @Override
    protected ImportAdapterInfo createAdapterInfo(int columnIndex, ImportProperty importProperty) {
        return new ImportAdapterInfo(columnIndex, importProperty.getColumnInfo());
    }

    @SuppressWarnings("unchecked")
    @Override
    protected Map<String, ImportProperty> createImportPropertyMap(List<ColumnInfo> importColumns) {
        Map<String, ImportProperty> map = new CaseInsensitiveMap();
        for (ColumnInfo columnInfo : importColumns) {
            String key = columnInfo.isImportNotNullable() ? String.format("%s(*)", columnInfo.getFriendlyName()) : columnInfo.getFriendlyName();
            map.put(key, new ImportProperty(key, columnInfo));
        }
        return map;
    }

    /**
     * 支持合并导出，增加字典导入
     */
    @Override
    protected void setItem(Sheet sheet, Row row, List<ImportAdapterInfo> adapters, Map<String, Object> item) {
        for (ImportAdapterInfo adapter : adapters) {
            Object value = null;
            Cell cell = row.getCell(adapter.getColumnIndex());
            if (cell != null) {
                value = getCellValue(sheet, cell);
            }
            ColumnInfo columnInfo = adapter.getColumnInfo();
            if (value != null) {
                if (columnInfo.getCellType().equals(CellType.CELL_TYPE_BOOLEAN)) {
                    String strValue = value.toString().trim().toLowerCase();
                    item.put(columnInfo.getPropertyName(), cellBooleanValue(strValue));
                } else {
                    item.put(columnInfo.getPropertyName(), value);
                }
            } else {
                if (columnInfo.isImportNotNullable()) {
                    String cellName = WorkbookFactory.parseExcelColName(row.getRowNum() + 1,
                            adapter.getColumnIndex() + 1) + "(" + adapter.getColumnInfo().getFriendlyName() + ")";
                    throw new ExcelException(String.format("工作表[%s] 单元格 [%s]的值不能为空或空白值。", sheet.getSheetName(), cellName));
                }
            }
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    protected FunctionResult<Map<String, Object>> createInstanceFactory() {
        return () -> {
            if (this.isIgnoreCase) {
                return new CaseInsensitiveMap();
            }
            return new HashMap<>(16);
        };
    }

    @Override
    protected ImportInfo<ImportAdapterInfo> createImportInfo(Sheet sheet, List<ImportAdapterInfo> adapters,
                                                             int beginRowIndex) {
        ImportInfo<ImportAdapterInfo> result = new ImportInfo<>();
        result.setAdapters(adapters);
        result.setBeginRowIndex(beginRowIndex);
        result.setSheet(sheet);
        return result;
    }

}
