package com.autumn.util.excel.column;

import com.autumn.util.StringUtils;
import com.autumn.util.excel.enums.ColumnType;
import com.autumn.util.excel.ExcelException;
import com.autumn.util.excel.utils.ColumnUtils;
import com.autumn.util.excel.workbook.WorkbookFactory;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * 列组
 */
public class ColumnGroup extends AbstractColumn {

    /**
     * 序列号
     */
    private static final long serialVersionUID = -5387160041128794906L;

    public ColumnGroup() {
        columns = new ArrayList<>();
    }

    /**
     * 获取列集合
     */
    private List<AbstractColumn> columns;

    @Override
    public final ColumnType getColumnType() {
        return ColumnType.GROUP;
    }

    @Override
    public void check() {
        if (StringUtils.isNullOrBlank(this.getFriendlyName())) {
            throw new ExcelException("FriendlyName 为 null 或空白值");
        }
        if (getColumns().size() == 0) {
            throw new ExcelException(getClass().getName() + " 至少需要一列以上。");
        }
        for (AbstractColumn column : getColumns()) {
            column.check();
        }
    }

    @Override
    public int mergeRows() {
        return ColumnUtils.mergeRows(getColumns());
    }

    @Override
    public void orderColumns() {
        columns.sort(Comparator.comparingInt(AbstractColumn::getOrder));
        for (AbstractColumn column : getColumns()) {
            column.orderColumns();
        }
    }

    @Override
    public List<ColumnInfo> columnInfos() {
        List<ColumnInfo> items = new ArrayList<>(getColumns().size());
        for (AbstractColumn column : getColumns()) {
            items.addAll(column.columnInfos());
        }
        return items;
    }

    @Override
    public Integer createTitleCell(Sheet sheet, CellStyle cellStyle, Integer columnIndex, Integer beginRow,
                                   Integer mergeRows, Boolean isImportTemplate) {
        Row row = sheet.getRow(beginRow);
        if (row == null) {
            row = sheet.createRow(beginRow);
        }
        if (mergeRows >= 1) {
            mergeRows--;
        }
        beginRow += 1;
        int colIndex = columnIndex;
        for (AbstractColumn column : getColumns()) {
            columnIndex = column.createTitleCell(sheet, cellStyle, columnIndex, beginRow, mergeRows, isImportTemplate);
        }
        int mergeCols = this.mergeCols();
        if (mergeCols >= 1) {
            mergeCols--;
        }
        WorkbookFactory.createTitleCell(row, colIndex, getFriendlyName(), cellStyle, 0, mergeCols);
        return columnIndex;
    }

    /**
     * 获取获取列集合
     *
     * @return
     */
    public List<AbstractColumn> getColumns() {
        return columns;
    }

    /**
     * 设置列集合
     *
     * @param columns
     */
    @SuppressWarnings("unused")
    private void setColumns(List<AbstractColumn> columns) {
        this.columns = columns;
    }

    @Override
    public int mergeCols() {
        return ColumnUtils.mergeCols(getColumns());
    }

}
