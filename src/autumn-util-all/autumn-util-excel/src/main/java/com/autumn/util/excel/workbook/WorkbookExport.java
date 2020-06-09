package com.autumn.util.excel.workbook;

import com.autumn.exception.ExceptionUtils;
import com.autumn.util.CollectionUtils;
import com.autumn.util.EnvironmentConstants;
import com.autumn.util.HorizontalAlignment;
import com.autumn.util.StringUtils;
import com.autumn.util.excel.column.AbstractColumn;
import com.autumn.util.excel.column.ColumnInfo;
import com.autumn.util.excel.exports.AbstractExportInfo;
import com.autumn.util.excel.exports.ExportAdapterInfo;
import com.autumn.util.excel.sheet.WorkSheetExportInfo;
import com.autumn.util.excel.sheet.WorkSheetHeader;
import com.autumn.util.excel.sheet.WorkSheetInfo;
import com.autumn.util.excel.utils.ExcelUtils;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * 工作表导出
 */
public class WorkbookExport {

    /**
     * 表导出最大行数
     */
    public static final int TABLE_MAX_ROW_COUNT = 1048576;

    /**
     * 表的最大列数
     */
    public static final int TABLE_MAX_COLUMNS = 16384;

    /**
     * 单元格行高
     */
    private static final int CELL_ROW_HEIGHT = 25;

    private final WorkSheetInfo workSheetInfo;

    /**
     * 初始化WorkBookInfo,并验证实例非空
     *
     * @param workSheetInfo 工作表
     */
    public WorkbookExport(WorkSheetInfo workSheetInfo) {
        this.workSheetInfo = ExceptionUtils.checkNotNull(workSheetInfo, "workbookInfo");
    }

    /**
     * 创建导出工作簿
     *
     * @param exportInfo
     * @param isImportTemplate
     * @return
     */
    public <T> Workbook createWorkbook(AbstractExportInfo<T> exportInfo, boolean isImportTemplate) {
        ExceptionUtils.checkNotNull(exportInfo, "exportInfo");
        List<T> dataItems = exportInfo.getItems();
        if (dataItems == null) {
            dataItems = new ArrayList<>(16);
        }
        WorkSheetExportInfo sheetExport = this.workSheetInfo.createWorkSheetExportInfo(isImportTemplate);
        Workbook workbook = ExcelUtils.createWorkbook();
        List<ExportAdapterInfo> exportAdapters = this.workSheetInfo.createExportAdapters(workbook, sheetExport.getDataColumns());
        int pageSize = TABLE_MAX_ROW_COUNT - sheetExport.getTitleRows();
        int pageCount = dataItems.size() / pageSize;
        if (dataItems.size() % pageSize != 0) {
            pageCount++;
        }
        if (pageCount < 1) {
            pageCount = 1;
        }
        boolean autoName = false;
        String sheetName = workSheetInfo.getSheetName();
        if (StringUtils.isNullOrBlank(sheetName)) {
            sheetName = "Sheet";
            autoName = true;
        }
        for (int page = 1; page <= pageCount; page++) {
            String name;
            if (isImportTemplate) {
                if (autoName) {
                    name = sheetName + page;
                } else {
                    name = sheetName;
                }
            } else {
                if (pageCount > 1 || autoName) {
                    name = sheetName + page;
                } else {
                    name = sheetName;
                }
            }
            this.createSheet(workbook, name, page, pageSize, exportInfo, dataItems, sheetExport.getTitleColumns(), exportAdapters, isImportTemplate, sheetExport.getMergeCols(), sheetExport.getMergeRows());
        }
        if (pageCount == 1 && autoName) {
            workbook.createSheet(String.format("Sheet%d", 2));
            workbook.createSheet(String.format("Sheet%d", 3));
        }
        return workbook;
    }

    /**
     * 创建工作表
     *
     * @param workbook         工作簿
     * @param sheetName        工作表名
     * @param exportInfo       导出信息
     * @param isImportTemplate 是否为导入模板
     * @param <T>
     * @return
     */
    public <T> Sheet createSheet(Workbook workbook, String sheetName, AbstractExportInfo<T> exportInfo, boolean isImportTemplate) {
        ExceptionUtils.checkNotNull(workbook, "workbook");
        ExceptionUtils.checkNotNull(exportInfo, "exportInfo");
        List<T> dataItems = exportInfo.getItems();
        if (dataItems == null) {
            dataItems = new ArrayList<>(16);
        }
        WorkSheetExportInfo sheetExport = this.workSheetInfo.createWorkSheetExportInfo(isImportTemplate);
        List<ExportAdapterInfo> exportAdapters = this.workSheetInfo.createExportAdapters(workbook, sheetExport.getDataColumns());
        if (StringUtils.isNullOrBlank(sheetName)) {
            sheetName = "Sheet1";
        }
        return this.createSheet(workbook, sheetName, 1, TABLE_MAX_ROW_COUNT, exportInfo, dataItems,
                sheetExport.getTitleColumns(), exportAdapters,
                isImportTemplate, sheetExport.getMergeCols(),
                sheetExport.getMergeRows());
    }

    /**
     * 创建Csv格式
     *
     * @param exportInfo       导出信息
     * @param isImportTemplate 是否为模板
     * @param outputStream     输出流
     * @param <T>
     */
    public <T> void createCsv(AbstractExportInfo<T> exportInfo, boolean isImportTemplate, OutputStream outputStream) throws IOException {
        ExceptionUtils.checkNotNull(exportInfo, "exportInfo");
        ExceptionUtils.checkNotNull(outputStream, "outputStream");
        List<T> dataItems = exportInfo.getItems();
        if (dataItems == null) {
            dataItems = new ArrayList<>(16);
        }
        WorkSheetExportInfo sheetExport = this.workSheetInfo.createWorkSheetExportInfo(isImportTemplate);
        WorkSheetHeader header = workSheetInfo.getHeader();
        // 若不为空，创建工作薄大标题
        if (header != null) {
            header.writeCsv(outputStream, sheetExport.getMergeCols());
        }
        WorkSheetHeader childHeader = workSheetInfo.getChildHeader();
        // 若不为空，创建工作薄小标题
        if (childHeader != null) {
            childHeader.writeCsv(outputStream, sheetExport.getMergeCols());
        }
        //标题
        this.createTitleCsvRow(sheetExport.getDataColumns(), sheetExport.getMergeRows(), isImportTemplate, outputStream);
        //内容
        for (int i = 0; i < dataItems.size(); i++) {
            T item = dataItems.get(i);
            StringBuilder sbRow = new StringBuilder(sheetExport.getDataColumns().size() * 100);
            if (i > 0) {
                sbRow.append(EnvironmentConstants.LINE_SEPARATOR);
            }
            for (int c = 0; c < sheetExport.getDataColumns().size(); c++) {
                ColumnInfo col = sheetExport.getDataColumns().get(c);
                Object value = exportInfo.read(item, col.getPropertyName());
                if (c > 0) {
                    sbRow.append(",");
                }
                sbRow.append(ExcelUtils.toCsvValue(value));
            }
            ExcelUtils.writeCsvValue(outputStream, sbRow.toString());
        }
    }

    /**
     * 创建工作表
     *
     * @param workbook         工作簿
     * @param sheetName        工作表名称
     * @param currentPage      当前页
     * @param pageSize         页大小
     * @param exportInfo       导出信息
     * @param dataItems        数据项目
     * @param titleColumns     标题列集合
     * @param exportColumns    导出列集合
     * @param isImportTemplate 是否为导入模板
     * @param mergeCols        合并列
     * @param mergeRows        合并行
     * @return
     */
    private <T> Sheet createSheet(Workbook workbook, String sheetName, int currentPage, int pageSize,
                                  AbstractExportInfo<T> exportInfo, List<T> dataItems, List<AbstractColumn> titleColumns,
                                  List<ExportAdapterInfo> exportColumns,
                                  boolean isImportTemplate, int mergeCols, int mergeRows) {
        Sheet sheet = workbook.createSheet(sheetName);
        int beginRowIndex = 0;
        Row row;
        WorkSheetHeader header = workSheetInfo.getHeader();
        // 若不为空，创建工作薄大标题
        if (header != null) {
            row = header.createRow(sheet, HorizontalAlignment.CENTER, beginRowIndex, mergeCols, true);
            if (row != null) {
                beginRowIndex++;
            }
        }
        WorkSheetHeader childHeader = workSheetInfo.getChildHeader();
        // 若不为空，创建工作薄小标题
        if (childHeader != null) {
            row = childHeader.createRow(sheet, HorizontalAlignment.LEFT, beginRowIndex, mergeCols, false);
            if (row != null) {
                beginRowIndex++;
            }
        }
        beginRowIndex = createTitleRow(workbook, sheet, beginRowIndex, titleColumns, mergeRows, isImportTemplate);
        // 设置导出列数据
        createItems(workbook, sheet, currentPage, pageSize, beginRowIndex, exportColumns, exportInfo, dataItems);
        return sheet;
    }

    /**
     * 创建项目集合
     *
     * @param workbook
     * @param sheet
     * @param currentPage
     * @param pageSize
     * @param beginRowIndex
     * @param exportColumns
     * @param exportInfo
     * @param dataItems
     * @param <T>
     */
    private <T> void createItems(Workbook workbook, Sheet sheet, int currentPage, int pageSize, int beginRowIndex,
                                 List<ExportAdapterInfo> exportColumns,
                                 AbstractExportInfo<T> exportInfo, List<T> dataItems) {
        int indexBegin = (currentPage - 1) * pageSize;
        int indexEnd = indexBegin + pageSize - 1;
        int rows = dataItems.size();
        int mergeContentRows = CollectionUtils.count(exportColumns, item -> item.getColumnInfo().isMergeContentRow());
        if (mergeContentRows > 0 && rows > 1) {
            Map<ExportAdapterInfo, MergeContentRowInfo> mergeMap = new HashMap<ExportAdapterInfo, MergeContentRowInfo>(exportColumns.size());
            for (int i = indexBegin; i <= indexEnd; i++) {
                if (rows < i + 1) {
                    break;
                }
                T item = dataItems.get(i);
                Row row = WorkbookFactory.createRow(sheet, beginRowIndex, CELL_ROW_HEIGHT);
                for (ExportAdapterInfo column : exportColumns) {
                    ColumnInfo columnInfo = column.getColumnInfo();
                    Object value = exportInfo.read(item, columnInfo.getPropertyName());
                    if (columnInfo.isMergeContentRow()) {
                        MergeContentRowInfo rowInfo = mergeMap.get(column);
                        if (rowInfo != null) {
                            if (cellValueEquals(value, rowInfo.firstCellValue,
                                    columnInfo.isMergeContentRow())) {
                                rowInfo.setLastRow(row.getRowNum());
                                column.createBlankCell(row);
                            } else {
                                if (rowInfo.getMergeCount() > 0) {
                                    column.mergeRow(sheet, rowInfo.getFirstRow(),
                                            rowInfo.getLastRow());
                                }
                                rowInfo.setFirstRow(row.getRowNum());
                                rowInfo.setFirstCellValue(value);
                                rowInfo.setLastRow(row.getRowNum());
                                column.createValueCell(row, value);
                            }
                        } else {
                            rowInfo = new MergeContentRowInfo();
                            rowInfo.setFirstRow(row.getRowNum());
                            rowInfo.setLastRow(row.getRowNum());
                            rowInfo.setFirstCellValue(value);
                            mergeMap.put(column, rowInfo);
                            column.createValueCell(row, value);
                        }
                    } else {
                        column.createValueCell(row, value);
                    }
                }
                beginRowIndex++;
            }
            for (Entry<ExportAdapterInfo, MergeContentRowInfo> entry : mergeMap.entrySet()) {
                MergeContentRowInfo contentRowInfo = entry.getValue();
                entry.getKey().mergeRow(sheet, contentRowInfo.getFirstRow(), contentRowInfo.getLastRow());
            }
            mergeMap.clear();
        } else {
            for (int i = indexBegin; i <= indexEnd; i++) {
                if (rows < i + 1) {
                    break;
                }
                T item = dataItems.get(i);
                Row row = WorkbookFactory.createRow(sheet, beginRowIndex, CELL_ROW_HEIGHT);
                for (ExportAdapterInfo column : exportColumns) {
                    Object read = exportInfo.read(item, column.getColumnInfo().getPropertyName());
                    column.createValueCell(row, read);
                }
                beginRowIndex++;
            }
        }
    }

    /**
     * 单元格值 是否相等
     *
     * @param left
     * @param right
     * @param isBlank
     * @return
     */
    private boolean cellValueEquals(Object left, Object right, boolean isBlank) {
        String leftValue;
        String rightValue;
        if (left == null) {
            leftValue = "";
        } else {
            leftValue = left.toString();
        }
        if (right == null) {
            rightValue = "";
        } else {
            rightValue = right.toString();
        }
        if (isBlank) {
            return leftValue.trim().equals(rightValue.trim());
        }
        boolean result = leftValue.equals(rightValue);
        if (result && !isBlank && StringUtils.isNullOrBlank(leftValue)) {
            return false;
        }
        return result;
    }

    /**
     * 创建标题行
     *
     * @param workbook
     * @param sheet            工作表
     * @param beginIndex
     * @param columns
     * @param mergeRows
     * @param isImportTemplate
     * @return
     */
    private int createTitleRow(Workbook workbook, Sheet sheet, int beginIndex, List<AbstractColumn> columns,
                               int mergeRows, boolean isImportTemplate) {
        CellStyle titleCellStyle = WorkbookFactory.createCellStyle(workbook, HorizontalAlignment.CENTER, 10D, true,
                true);
        int index = beginIndex;
        if (mergeRows > 0) {
            beginIndex = beginIndex + mergeRows;
        }
        for (int r = index; r <= beginIndex; r++) {
            WorkbookFactory.createRow(sheet, r, 25);
        }
        int columnIndex = 0;
        for (AbstractColumn column : columns) {
            columnIndex = column.createTitleCell(sheet, titleCellStyle, columnIndex, index, mergeRows,
                    isImportTemplate);
        }
        return ++beginIndex;
    }

    /**
     * 创建Csv标题行
     *
     * @param columns
     * @param mergeRows
     * @param isImportTemplate
     * @param outputStream
     * @throws IOException
     */
    private void createTitleCsvRow(List<ColumnInfo> columns, int mergeRows, boolean isImportTemplate,
                                   OutputStream outputStream) throws IOException {
        StringBuilder sbTitle = new StringBuilder(columns.size() * 100);
        for (int i = 0; i < columns.size(); i++) {
            ColumnInfo column = columns.get(i);
            if (i > 0) {
                sbTitle.append(",");
            }
            sbTitle.append(ExcelUtils.toCsvValue(column.getFriendlyName()));
            if (isImportTemplate && column.isImportNotNullable()) {
                sbTitle.append("(*)");
            }
        }
        sbTitle.append(EnvironmentConstants.LINE_SEPARATOR);
        ExcelUtils.writeCsvValue(outputStream, sbTitle.toString());
    }

    /**
     * 合并内容行信息
     */
    private class MergeContentRowInfo {
        /**
         * 首行
         */
        private int firstRow;

        /**
         * 最后一行
         */
        private int lastRow;

        /**
         * 首个单元格值
         */
        private Object firstCellValue;

        /**
         * 获取首行
         *
         * @return
         */
        public int getFirstRow() {
            return firstRow;
        }

        /**
         * 设置首行
         *
         * @param firstRow
         */
        public void setFirstRow(int firstRow) {
            this.firstRow = firstRow;
        }

        /**
         * 获取 最后一行
         *
         * @return
         */
        public int getLastRow() {
            return lastRow;
        }

        /**
         * 设置 最后一行
         *
         * @param lastRow
         */
        public void setLastRow(int lastRow) {
            this.lastRow = lastRow;
        }

        /**
         * 获取首个单元格值
         *
         * @return
         */
        public Object getFirstCellValue() {
            return firstCellValue;
        }

        /**
         * 设置首个单元格值
         *
         * @param firstCellValue
         */
        public void setFirstCellValue(Object firstCellValue) {
            this.firstCellValue = firstCellValue;
        }

        /**
         * 获取合并数量
         *
         * @return
         */
        public int getMergeCount() {
            return lastRow - firstRow;
        }
    }
}
