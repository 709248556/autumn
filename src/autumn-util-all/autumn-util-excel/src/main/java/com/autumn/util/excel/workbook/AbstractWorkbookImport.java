package com.autumn.util.excel.workbook;

import com.autumn.exception.ExceptionUtils;
import com.autumn.util.CollectionUtils;
import com.autumn.util.StringUtils;
import com.autumn.util.excel.utils.ExcelUtils;
import com.autumn.util.excel.sheet.WorkSheetInfo;
import com.autumn.util.excel.utils.YesOrNoConst;
import com.autumn.util.excel.column.ColumnInfo;
import com.autumn.util.excel.ExcelException;
import com.autumn.util.excel.imports.ImportAdapterInfo;
import com.autumn.util.excel.imports.ImportInfo;
import com.autumn.util.excel.imports.ImportProperty;
import com.autumn.util.function.FunctionResult;
import com.autumn.util.tuple.TupleThree;
import com.autumn.util.tuple.TupleTwo;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFFormulaEvaluator;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.formula.BaseFormulaEvaluator;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFFormulaEvaluator;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFFormulaEvaluator;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;

/**
 * 导入抽象
 *
 * @param <T>                  类型
 * @param <TImportInfo>        导入信息
 * @param <TImportAdapterInfo> 导入适配器
 * @param <TImportProperty>    导入属性
 */
public abstract class AbstractWorkbookImport<T, TImportInfo extends ImportInfo<TImportAdapterInfo>, TImportAdapterInfo extends ImportAdapterInfo, TImportProperty extends ImportProperty> {

    private final Class<T> importClass;

    private final WorkSheetInfo workSheetInfo;

    /**
     * 获取工作表
     *
     * @return
     */
    public final WorkSheetInfo getWorkSheetInfo() {
        return this.workSheetInfo;
    }

    /**
     * 获取导入类型
     *
     * @return
     */
    public Class<T> getImportClass() {
        return this.importClass;
    }

    /**
     * @param workSheetInfo 工作表
     * @param importClass   导入类型
     */
    public AbstractWorkbookImport(WorkSheetInfo workSheetInfo, Class<T> importClass) {
        this.importClass = importClass;
        this.workSheetInfo = ExceptionUtils.checkNotNull(workSheetInfo, "workbookInfo");
    }

    /**
     * 导入数据
     *
     * @param input     导入流
     * @param sheetName 工作表名称,未指定则自动查找符合格式的首个表
     * @return
     */
    public List<T> importData(InputStream input, String sheetName) {
        ExceptionUtils.checkNotNull(input, "input");
        TImportInfo importInfo = createImportInfo(input, sheetName);
        return this.importData(importInfo);
    }

    /**
     * 导入数据
     *
     * @param input 导入流
     * @return
     */
    public List<T> importData(InputStream input) {
        ExceptionUtils.checkNotNull(input, "input");
        TImportInfo importInfo = createImportInfo(input, this.workSheetInfo.getSheetName());
        return this.importData(importInfo);
    }

    /**
     * 导入数据
     *
     * @param workbook  工作簿
     * @param sheetName 工作表名称,未指定则自动查找符合格式的首个表
     * @return
     */
    public List<T> importData(Workbook workbook, String sheetName) {
        ExceptionUtils.checkNotNull(workbook, "workbook");
        TImportInfo importInfo = createImportInfo(workbook, sheetName);
        return this.importData(importInfo);
    }

    /**
     * 导入数据
     *
     * @param workbook 工作簿
     * @return
     */
    public List<T> importData(Workbook workbook) {
        ExceptionUtils.checkNotNull(workbook, "workbook");
        TImportInfo importInfo = createImportInfo(workbook, this.workSheetInfo.getSheetName());
        return this.importData(importInfo);
    }

    /**
     * 导入数据
     *
     * @param importInfo 导入信息
     * @return
     */
    private List<T> importData(TImportInfo importInfo) {
        List<T> items;
        if (importInfo.getSheet().getLastRowNum() >= importInfo.getBeginRowIndex()) {
            items = createItems(importInfo.getSheet(), importInfo.getBeginRowIndex(), importInfo.getAdapters());
        } else {
            items = new ArrayList<>(16);
        }
        return items;
    }

    /**
     * 创建实例委托
     *
     * @return
     */
    protected abstract FunctionResult<T> createInstanceFactory();

    /**
     * 创建适配器信息
     *
     * @param columnIndex    列索引
     * @param importProperty 导入属性
     * @return
     */
    protected abstract TImportAdapterInfo createAdapterInfo(int columnIndex, TImportProperty importProperty);

    /**
     * 检查导入列
     *
     * @param importMap
     * @param sheet
     * @return
     */
    private TupleTwo<List<TImportAdapterInfo>, Integer> checkColumns(Map<String, TImportProperty> importMap, Sheet sheet) {
        int beginRowIndex = 0;
        List<TImportAdapterInfo> items = new ArrayList<>();
        for (int r = 0; r < sheet.getLastRowNum(); r++) {
            Row row = sheet.getRow(r);
            for (int c = 0; c < row.getPhysicalNumberOfCells(); c++) {
                Cell cell = row.getCell(c);
                if (ExcelUtils.isMergedCell(cell.getSheet(), cell.getRowIndex(), cell.getColumnIndex())
                        || !cell.getCellType().equals(org.apache.poi.ss.usermodel.CellType.STRING)) {
                    break;
                }
                String columnName = cell.getStringCellValue().trim();
                TImportProperty importProperty = importMap.get(columnName);
                if (importProperty != null) {
                    items.add(createAdapterInfo(c, importProperty));
                }
            }
            beginRowIndex++;
            if (CollectionUtils.any(items, Objects::nonNull)) {
                break;
            }
        }
        return new TupleTwo<>(items, beginRowIndex);
    }

    /**
     * 查找导入列
     *
     * @param workbook
     * @param sheetName
     * @param importMap
     * @return
     */
    private TupleThree<List<TImportAdapterInfo>, Sheet, Integer> findImportColumns(Workbook workbook, String sheetName, Map<String, TImportProperty> importMap) {
        if (workbook.getNumberOfSheets() <= 0) {
            throw new ExcelException("导入的 Excel 文件至少需要一个工作表");
        }
        List<TImportAdapterInfo> adapters = null;
        Sheet sheet = null;
        int beginRowIndex = 0;
        TupleTwo<List<TImportAdapterInfo>, Integer> tuple2;
        if (!StringUtils.isNullOrBlank(sheetName)) {
            sheet = workbook.getSheet(sheetName);
            if (sheet == null) {
                throw new ExcelException(String.format("Excel 文件不存在表 %s", sheetName));
            }
            tuple2 = checkColumns(importMap, sheet);
            beginRowIndex = tuple2.getItem2();
            adapters = tuple2.getItem1();
            if (adapters.size() == 0) {
                throw new ExcelException(String.format("Excel 文件表 %s 不存在对应的模板或模板格式不正确。", sheetName));
            }
        } else {
            for (int i = 0, j = workbook.getNumberOfSheets(); i < j; i++) {
                sheet = workbook.getSheetAt(i);
                tuple2 = checkColumns(importMap, sheet);
                beginRowIndex = tuple2.getItem2();
                adapters = tuple2.getItem1();
                if (CollectionUtils.any(adapters, Objects::nonNull)) {
                    break;
                }
            }
            if (!CollectionUtils.any(adapters, Objects::nonNull)) {
                throw new ExcelException("自动在 Excel 文件中未找到符合模板的工作表。");
            }
        }
        return new TupleThree<>(adapters, sheet, beginRowIndex);
    }

    /**
     * 创建导入属性Map
     *
     * @param importColumns 导入列
     * @return
     */
    protected abstract Map<String, ImportProperty> createImportPropertyMap(List<ColumnInfo> importColumns);

    /**
     * 导入列
     *
     * @return
     */
    @SuppressWarnings("unchecked")
    private Map<String, TImportProperty> importMap() {
        List<ColumnInfo> columns = workSheetInfo.columnInfos();
        if (columns.size() == 0) {
            throw new ExcelException("导入模板至少需要一个导入列。");
        }
        if (CollectionUtils.any(columns, (columnInfo) -> StringUtils.isNullOrBlank(columnInfo.getPropertyName())
                || StringUtils.isNullOrBlank(columnInfo.getFriendlyName()))) {
            throw new ExcelException("配置列信息中存在 PropertyName 或 FriendlyName 属性为值 null 或空白值。");
        }
        return (Map<String, TImportProperty>) createImportPropertyMap(columns);
    }

    /**
     * 创建导入信息
     *
     * @param input     工作簿
     * @param sheetName 工作表
     * @return
     */
    private TImportInfo createImportInfo(InputStream input, String sheetName) {
        Workbook workbook = ExcelUtils.createWorkbook(input);
        return this.createImportInfo(workbook, sheetName);
    }

    /**
     * 创建导入信息
     *
     * @param workbook  工作簿
     * @param sheetName 工作表
     * @return
     */
    private TImportInfo createImportInfo(Workbook workbook, String sheetName) {
        if (workbook instanceof SXSSFWorkbook) {
            SXSSFWorkbook sxssfWorkbook = (SXSSFWorkbook) workbook;
            workbook = sxssfWorkbook.getXSSFWorkbook();
        }
        if (workbook.getNumberOfSheets() <= 0) {
            throw new ExcelException("导入的 Excel 文件至少需要一个工作表");
        }
        Map<String, TImportProperty> importMap = importMap();
        Sheet sheet;
        TupleThree<List<TImportAdapterInfo>, Sheet, Integer> tuple3 = findImportColumns(workbook, sheetName, importMap);
        List<TImportAdapterInfo> adapters = tuple3.getItem1();
        int beginRowIndex = tuple3.getItem3();
        sheet = tuple3.getItem2();
        for (Entry<String, TImportProperty> entry : importMap.entrySet()) {
            ColumnInfo columninfo = entry.getValue().getColumnInfo();
            if (columninfo.isImportNotNullable() && !CollectionUtils.any(adapters, (adapterInfo) -> {
                return adapterInfo.getColumnInfo().equals(columninfo);
            })) {
                throw new ExcelException(String.format("Excel 文件模板缺少必须列[%s]", entry.getKey()));
            }
        }
        return createImportInfo(sheet, adapters, beginRowIndex);
    }

    /**
     * 创建导入信息
     *
     * @param sheet
     * @param adapters
     * @param beginRowIndex
     * @return
     */
    protected abstract TImportInfo createImportInfo(Sheet sheet, List<TImportAdapterInfo> adapters, int beginRowIndex);

    /**
     * 获取单元格的值
     *
     * @param sheet
     * @param cell
     * @return
     */
    protected Object getCellValue(Sheet sheet, Cell cell) {
        String value;
        if (cell.getCellType().equals(org.apache.poi.ss.usermodel.CellType.BOOLEAN)) {
            return cell.getBooleanCellValue();
        } else if (cell.getCellType().equals(org.apache.poi.ss.usermodel.CellType.NUMERIC)) {
            if (HSSFDateUtil.isCellDateFormatted(cell)) {
                return cell.getDateCellValue();
            }
            return cell.getNumericCellValue();
        } else if (cell.getCellType().equals(org.apache.poi.ss.usermodel.CellType.STRING)) {
            value = cell.getStringCellValue();
            if (StringUtils.isNullOrBlank(value)) {
                return null;
            }
            return value;
        } else if (cell.getCellType().equals(org.apache.poi.ss.usermodel.CellType.FORMULA)) {
            BaseFormulaEvaluator eva = null;
            if (sheet.getWorkbook() instanceof SXSSFWorkbook) {
                eva = new SXSSFFormulaEvaluator((SXSSFWorkbook) sheet.getWorkbook());
            } else if (sheet.getWorkbook() instanceof HSSFWorkbook) {
                eva = new HSSFFormulaEvaluator((HSSFWorkbook) sheet.getWorkbook());
            } else if (sheet.getWorkbook() instanceof XSSFWorkbook) {
                eva = new XSSFFormulaEvaluator((XSSFWorkbook) sheet.getWorkbook());
            }
            if (eva == null) {
                return null;
            }
            return getCellValue(sheet, eva.evaluateInCell(cell));
        } else if (cell.getCellType().equals(org.apache.poi.ss.usermodel.CellType.ERROR)) {
            return null;
        } else if (cell.getCellType().equals(org.apache.poi.ss.usermodel.CellType.BLANK)) {
            return null;
        }
        return null;
    }

    /**
     * 创建项目集合
     *
     * @param sheet
     * @param beginRowIndex
     * @param adapters
     * @return
     */
    private List<T> createItems(Sheet sheet, int beginRowIndex, List<TImportAdapterInfo> adapters) {
        List<T> items = new ArrayList<>();
        FunctionResult<T> factory = createInstanceFactory();
        for (int r = beginRowIndex; r <= sheet.getLastRowNum(); r++) {
            T item = factory.apply();
            Row row = sheet.getRow(r);
            setItem(sheet, row, adapters, item);
            items.add(item);
        }
        return items;
    }

    /**
     * 设置项目
     *
     * @param sheet
     * @param row
     * @param adapters
     * @param item
     */
    protected abstract void setItem(Sheet sheet, Row row, List<TImportAdapterInfo> adapters, T item);

    /**
     * 获取单元格
     *
     * @param value
     * @return
     */
    protected Boolean cellBooleanValue(String value) {
        return YesOrNoConst.TRUE_SET.contains(value);
    }
}
