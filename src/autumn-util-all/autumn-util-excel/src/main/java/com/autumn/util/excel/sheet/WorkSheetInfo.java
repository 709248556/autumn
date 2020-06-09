package com.autumn.util.excel.sheet;

import com.autumn.util.CollectionUtils;
import com.autumn.util.StringUtils;
import com.autumn.util.excel.column.AbstractColumn;
import com.autumn.util.excel.column.ColumnInfo;
import com.autumn.util.excel.ExcelException;
import com.autumn.util.excel.exports.AbstractExportInfo;
import com.autumn.util.excel.exports.ExportAdapterInfo;
import com.autumn.util.excel.utils.ColumnUtils;
import com.autumn.util.excel.workbook.WorkbookExport;
import com.autumn.util.excel.workbook.WorkbookImportMap;
import com.autumn.util.excel.workbook.WorkbookImportObject;
import com.autumn.util.tuple.TupleTwo;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

/**
 * 工作薄信息
 */
public class WorkSheetInfo implements Serializable {

    private static final long serialVersionUID = 3297327427921385280L;

    /**
     * 工作表名称
     */
    private String sheetName;

    /**
     * 主标题
     */
    private WorkSheetHeader header;

    /**
     * 子标题
     */
    private WorkSheetHeader childHeader;

    /**
     * 获取列集合
     */
    private List<AbstractColumn> columns;

    public WorkSheetInfo() {
        initialize();
    }

    private void initialize() {
        if (header == null) {
            this.header = new WorkSheetHeader();
            header.setShow(true);
            header.setRowHeight(50);
            header.setFontSize(18.0);
        }
        if (childHeader == null) {
            childHeader = new WorkSheetHeader();
            childHeader.setShow(true);
            childHeader.setRowHeight(25);
        }
        if (columns == null) {
            this.columns = new ArrayList<>();
        }
    }

    /**
     * 获取合并行数
     *
     * @return
     */
    public int mergeRows() {
        return ColumnUtils.mergeRows(getColumns());
    }

    /**
     * 获取合并列数
     *
     * @return
     */
    public int mergeCols() {
        return ColumnUtils.mergeCols(getColumns());
    }

    /**
     * 列排序
     */
    public void orderColumns() {
        List<AbstractColumn> columns = getColumns();
        if (columns != null) {
            columns.sort(Comparator.comparingInt(AbstractColumn::getOrder));
            for (AbstractColumn column : columns) {
                column.orderColumns();
            }
        }
    }

    /**
     * 列信息列表
     *
     * @return
     */
    public List<ColumnInfo> columnInfos() {
        List<ColumnInfo> items = new ArrayList<>();
        getColumns().sort(Comparator.comparingInt(AbstractColumn::getOrder));
        for (AbstractColumn column : getColumns()) {
            items.addAll(column.columnInfos());
        }
        return items;
    }

    /**
     * 创建导出工作簿
     *
     * @param exportInfo       导出信息
     * @param isImportTemplate 是否为模板
     * @return
     */
    public <T> Workbook createExportWorkbook(AbstractExportInfo<T> exportInfo, boolean isImportTemplate) {
        WorkbookExport work = new WorkbookExport(this);
        return work.createWorkbook(exportInfo, isImportTemplate);
    }

    /**
     * 创建导出工作表
     *
     * @param workbook         工作簿
     * @param sheetName        工作表名称
     * @param exportInfo       导出信息
     * @param isImportTemplate 是否为导入模板
     * @param <T>
     * @return
     */
    public <T> Sheet createSheet(Workbook workbook, String sheetName, AbstractExportInfo<T> exportInfo, boolean isImportTemplate) {
        WorkbookExport work = new WorkbookExport(this);
        return work.createSheet(workbook, sheetName, exportInfo, isImportTemplate);
    }

    /**
     * 创建 Csv 格式
     *
     * @param exportInfo       导出信息
     * @param isImportTemplate 是否为模板
     * @param outputStream     输出流
     * @param <T>
     * @throws IOException 流异常
     */
    public <T> void createCsv(AbstractExportInfo<T> exportInfo, boolean isImportTemplate, OutputStream outputStream) throws IOException {
        WorkbookExport work = new WorkbookExport(this);
        work.createCsv(exportInfo, isImportTemplate, outputStream);
    }

    /**
     * 创建对象列表
     *
     * @param input     输入流
     * @param sheetName 工作表名称,未指定则自动查找符合格式的首个表
     * @return
     */
    public <T> List<T> createObjectList(InputStream input, String sheetName, Class<T> clazz) {
        WorkbookImportObject<T> work = new WorkbookImportObject<>(this, clazz);
        return work.importData(input, sheetName);
    }

    /**
     * 创建对象列表
     *
     * @param workbook  工作簿
     * @param sheetName 工作表名称,未指定则自动查找符合格式的首个表
     * @return
     */
    public <T> List<T> createObjectList(Workbook workbook, String sheetName, Class<T> clazz) {
        WorkbookImportObject<T> work = new WorkbookImportObject<>(this, clazz);
        return work.importData(workbook, sheetName);
    }

    /**
     * 创建字典列表
     *
     * @param input     输入流
     * @param sheetName 工作表名称,未指定则自动查找符合格式的首个表
     * @return
     */
    public List<Map<String, Object>> createMapList(InputStream input, String sheetName) {
        WorkbookImportMap work = new WorkbookImportMap(this);
        return work.importData(input, sheetName);
    }

    /**
     * 创建字典列表
     *
     * @param workbook  工作簿
     * @param sheetName 工作表名称,未指定则自动查找符合格式的首个表
     * @return
     */
    public List<Map<String, Object>> createMapList(Workbook workbook, String sheetName) {
        WorkbookImportMap work = new WorkbookImportMap(this);
        return work.importData(workbook, sheetName);
    }

    /**
     * 创建字典列表
     *
     * @param input        输入流
     * @param sheetName    工作表名称,未指定则自动查找符合格式的首个表
     * @param isIgnoreCase 忽略大小写
     * @return
     */
    public List<Map<String, Object>> createMapList(InputStream input, String sheetName, boolean isIgnoreCase) {
        WorkbookImportMap work = new WorkbookImportMap(this, isIgnoreCase, Map.class);
        return work.importData(input, sheetName);
    }

    /**
     * 获取工作表
     *
     * @return
     */
    public String getSheetName() {
        return sheetName;
    }

    /**
     * 设置工作表
     *
     * @param sheetName 工作表
     */
    public void setSheetName(String sheetName) {
        this.sheetName = sheetName;
    }

    public WorkSheetHeader getHeader() {
        return header;
    }

    public void setHeader(WorkSheetHeader header) {
        this.header = header;
    }

    public WorkSheetHeader getChildHeader() {
        return childHeader;
    }

    public void setChildHeader(WorkSheetHeader childHeader) {
        this.childHeader = childHeader;
    }

    public List<AbstractColumn> getColumns() {
        return columns;
    }

    public void setColumns(List<AbstractColumn> columns) {
        this.columns = columns;
    }

    /**
     * 检查
     */
    private void check() {
        if (getColumns().size() == 0) {
            throw new ExcelException(" 至少需要一列以上。");
        }
        for (AbstractColumn column : columns) {
            column.check();
        }
    }

    /**
     * 创建工作表导出信息
     *
     * @param isImportTemplate 是否为导入模板
     * @return
     */
    public WorkSheetExportInfo createWorkSheetExportInfo(boolean isImportTemplate) {
        check();
        this.orderColumns();
        List<ColumnInfo> columns = this.columnInfos();
        List<ColumnInfo> dataColumns;
        int mergeCols, mergeRows;
        List<AbstractColumn> titleColumns = new ArrayList<AbstractColumn>(columns.size());
        if (isImportTemplate) {
            dataColumns = CollectionUtils.findCollection(columns, (col) -> col.isImportColumn());
            if (!CollectionUtils.any(dataColumns, (col) -> col != null)) {
                throw new ExcelException("输出导入模板时，至少需要一个导入列以上。");
            }
            mergeRows = 0;
            for (AbstractColumn column : dataColumns) {
                titleColumns.add(column);
            }
            mergeCols = titleColumns.size();
        } else {
            dataColumns = columns;
            mergeCols = this.mergeCols();
            mergeRows = this.mergeRows();
            titleColumns = this.getColumns();
        }
        int titleRows = getTitleRows(mergeRows);
        return new WorkSheetExportInfo(titleColumns, dataColumns, mergeRows, mergeCols, titleRows);
    }

    /**
     * 创建导出适配
     *
     * @param workbook
     * @param dataColumns
     * @return
     */
    public List<ExportAdapterInfo> createExportAdapters(Workbook workbook, List<ColumnInfo> dataColumns) {
        int size = dataColumns.size();
        if (size == 0) {
            throw new ExcelException("至少需要一列以上。");
        }
        List<ExportAdapterInfo> columns = new ArrayList<>(dataColumns.size());
        int index = 0;
        for (int i = 0; i < size; i++) {
            ColumnInfo column = dataColumns.get(i);
            if (StringUtils.isNullOrBlank(column.getFriendlyName())) {
                throw new ExcelException(String.format("第{%d}列的 FriendlyName 为 null 或空白值", (i + 1)));
            }
            TupleTwo<List<ExportAdapterInfo>, Integer> tuple2 = column.createExportAdapters(workbook, index);
            index = tuple2.getItem2();
            columns.addAll(tuple2.getItem1());
        }
        if (!CollectionUtils.any(columns, (exportAdapterInfo) -> exportAdapterInfo != null)) {
            throw new ExcelException("至少需要一个导出模板列。");
        }
        return columns;
    }

    /**
     * 获取数据开始行
     *
     * @param isImportTemplate 是否为导入模板
     * @return
     */
    public int getDataBeginRow(boolean isImportTemplate) {
        int mergeRows;
        if (isImportTemplate) {
            mergeRows = 0;
        } else {
            mergeRows = this.mergeRows();
        }
        return this.getTitleRows(mergeRows);
    }

    /**
     * 获取标题的总行数
     *
     * @param mergeRows
     * @return
     */
    private int getTitleRows(int mergeRows) {
        int count = 0;
        WorkSheetHeader header = this.getHeader();
        if (header != null && header.isExportHeader()) {
            count++;
        }
        WorkSheetHeader childHeader = this.getChildHeader();
        if (childHeader != null && childHeader.isExportHeader()) {
            count++;
        }
        count += mergeRows;
        count++;
        return count;
    }
}
