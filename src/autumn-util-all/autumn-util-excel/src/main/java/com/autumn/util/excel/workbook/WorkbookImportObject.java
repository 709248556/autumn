package com.autumn.util.excel.workbook;

import com.autumn.util.TypeUtils;
import com.autumn.util.excel.sheet.WorkSheetInfo;
import com.autumn.util.excel.column.ColumnInfo;
import com.autumn.util.excel.ExcelException;
import com.autumn.util.excel.imports.ImportInfo;
import com.autumn.util.excel.imports.ImportObjectAdapterInfo;
import com.autumn.util.excel.imports.ImportObjectProperty;
import com.autumn.util.excel.imports.ImportProperty;
import com.autumn.util.function.FunctionResult;
import com.autumn.util.reflect.BeanProperty;
import com.autumn.util.reflect.ReflectUtils;
import com.autumn.validation.ValidationUtils;
import com.esotericsoftware.reflectasm.ConstructorAccess;
import org.apache.commons.collections.map.CaseInsensitiveMap;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import java.util.List;
import java.util.Map;

/**
 * 工作簿导入对象
 *
 * @param <T>
 */
public class WorkbookImportObject<T> extends
        AbstractWorkbookImport<T, ImportInfo<ImportObjectAdapterInfo>, ImportObjectAdapterInfo, ImportObjectProperty> {

    private final Map<String, BeanProperty> properties;

    /**
     * @param workSheetInfo
     * @param tClass
     */
    public WorkbookImportObject(WorkSheetInfo workSheetInfo, Class<T> tClass) {
        super(workSheetInfo, tClass);
        properties = ReflectUtils.getBeanPropertyMap(tClass);
    }

    @Override
    protected FunctionResult<T> createInstanceFactory() {
        Class<T> clazz = getImportClass();
        final ConstructorAccess<T> constructor = ReflectUtils.getConstructorAccess(clazz);
        return constructor::newInstance;
    }

    @Override
    protected ImportObjectAdapterInfo createAdapterInfo(int columnIndex, ImportObjectProperty importProperty) {
        return new ImportObjectAdapterInfo(columnIndex, importProperty.getColumnInfo(),
                importProperty.getBeanProperty());
    }

    @SuppressWarnings("unchecked")
    @Override
    protected Map<String, ImportProperty> createImportPropertyMap(List<ColumnInfo> importColumns) {
        Class<?> type = getImportClass();
        Map<String, ImportProperty> map = new CaseInsensitiveMap();
        for (ColumnInfo columnInfo : importColumns) {
            BeanProperty beanProperty = this.properties.get(columnInfo.getPropertyName());
            if (beanProperty == null) {
                throw new ExcelException(
                        String.format("类型 %s 无属性 %s", type.getName(), columnInfo.getPropertyName()));
            }
            String key = columnInfo.isImportNotNullable() ? String.format("%s(*)", columnInfo.getFriendlyName())
                    : columnInfo.getFriendlyName();
            map.put(key, new ImportObjectProperty(key, columnInfo, beanProperty));
        }
        return map;
    }

    @Override
    protected ImportInfo<ImportObjectAdapterInfo> createImportInfo(Sheet sheet,
                                                                   List<ImportObjectAdapterInfo> adapters, int beginRowIndex) {
        ImportInfo<ImportObjectAdapterInfo> result = new ImportInfo<>();
        result.setAdapters(adapters);
        result.setBeginRowIndex(beginRowIndex);
        result.setSheet(sheet);
        return result;
    }

    @Override
    protected void setItem(Sheet sheet, Row row, List<ImportObjectAdapterInfo> adapters, T item) {
        for (ImportObjectAdapterInfo adapter : adapters) {
            Object value = null;
            Cell cell = row.getCell(adapter.getColumnIndex());
            if (cell != null) {
                value = getCellValue(sheet, cell);
            }
            if (value != null) {
                BeanProperty beanProperty = adapter.getBeanProperty();
                if (beanProperty.getType().equals(boolean.class)) {
                    String strValue = value.toString().trim().toLowerCase();
                    beanProperty.setValue(item, cellBooleanValue(strValue));
                } else {
                    try {
                        beanProperty.setValue(item, TypeUtils.toConvert(beanProperty.getType(), value));
                    } catch (Exception err) {
                        String cellName = WorkbookFactory.parseExcelColName(row.getRowNum() + 1,
                                adapter.getColumnIndex() + 1) + "(" + adapter.getColumnInfo().getFriendlyName() + ")";
                        throw new ExcelException(String.format("工作表[%s] 单元格 %s 的值 %s 无法转换为 %s 类型，即数据格式不正确。",
                                sheet.getSheetName(), cellName, value, beanProperty.getType().getName()), err);
                    }
                }
            } else {
                if (adapter.getColumnInfo().isImportNotNullable()) {
                    String cellName = WorkbookFactory.parseExcelColName(row.getRowNum() + 1,
                            adapter.getColumnIndex() + 1) + "(" + adapter.getColumnInfo().getFriendlyName() + ")";
                    throw new ExcelException(String.format("工作表[%s] 单元格 %s 的值不能为空或空白值。", sheet.getSheetName(), cellName));
                }
            }
        }
        ValidationUtils.validation(item);
    }
}
