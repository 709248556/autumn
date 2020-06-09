package com.autumn.util.excel.exports;

import com.autumn.util.DateUtils;
import com.autumn.util.TimeSpan;
import com.autumn.util.TypeUtils;
import com.autumn.util.excel.column.ColumnInfo;
import com.autumn.util.excel.enums.CellType;
import com.autumn.util.excel.AbstractAdapterInfo;
import com.autumn.util.excel.workbook.WorkbookFactory;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;

import java.util.Date;

/**
 * 导出适配信息
 */
public class ExportAdapterInfo extends AbstractAdapterInfo {

    protected ExportAdapterInfo(int columnIndex, ColumnInfo columnInfo) {
        super(columnIndex, columnInfo);
    }

    /**
     * 实例化 ExportAdapterInfo 类新实例
     *
     * @param columnIndex
     * @param columnInfo
     * @param cellStyle
     */
    public ExportAdapterInfo(int columnIndex, ColumnInfo columnInfo, CellStyle cellStyle) {
        super(columnIndex, columnInfo);
        this.setCellStyle(cellStyle);
    }

    /**
     * 单元格样式
     */
    private CellStyle cellStyle;

    /**
     * 创建标题单元格
     *
     * @param sheet          工作表
     * @param row            行
     * @param titleCellStyle 标题单元格式
     * @return
     */
    public Cell createTitleCell(Sheet sheet, Row row, CellStyle titleCellStyle) {
        ColumnInfo columnInfo = this.getColumnInfo();
        String title = columnInfo.getFriendlyName();
        Cell cell = WorkbookFactory.createTitleCell(row, this.getColumnIndex(), title, titleCellStyle);
        WorkbookFactory.setWidth(sheet, this.getColumnIndex(), columnInfo.getWidth());
        return cell;
    }

    /**
     * 创建空白单元格
     *
     * @param row 行
     * @return
     */
    public Cell createBlankCell(Row row) {
        return WorkbookFactory.createValueCell(row, getColumnIndex(), "", getCellStyle());
    }

    /**
     * 最大整数值
     */
    private final long MAX_LONG_VALUE = 99999999999L;

    /**
     * 创建单元格
     *
     * @param row   行
     * @param value 值
     * @return
     */
    public Cell createValueCell(Row row, Object value) {
        if (value == null) {
            return WorkbookFactory.createValueCell(row, getColumnIndex(), "", getCellStyle());
        }
        if (getColumnInfo().getCellType() == null) {
            return WorkbookFactory.createValueCell(row, getColumnIndex(), value.toString(), getCellStyle());
        }
        switch (getColumnInfo().getCellType()) {
            case CellType.CELL_TYPE_BOOLEAN:
                return WorkbookFactory.createValueCell(row, getColumnIndex(),
                        TypeUtils.toConvert(boolean.class, value) ? "是" : "否", getCellStyle());
            case CellType.CELL_TYPE_DATE:
            case CellType.CELL_TYPE_DATETIME:
                return WorkbookFactory.createValueCell(row, getColumnIndex(), TypeUtils.toConvert(Date.class, value),
                        getCellStyle());
            case CellType.CELL_TYPE_TIME:
                if (value instanceof TimeSpan) {
                    return WorkbookFactory.createValueCell(row, getColumnIndex(), DateUtils.createFormatTime().format(value),
                            getCellStyle());
                } else {
                    return WorkbookFactory.createValueCell(row, getColumnIndex(), value.toString(), getCellStyle());
                }
            case CellType.CELL_TYPE_DOUBLE:
            case CellType.CELL_TYPE_BIGDECIMAL:
                return WorkbookFactory.createValueCell(row, getColumnIndex(), TypeUtils.toConvert(double.class, value),
                        getCellStyle());
            case CellType.CELL_TYPE_INTEGER:
                if (value instanceof Long) {
                    //如果整数过大，则会造成科学记数
                    Long longValue = (Long) value;
                    if (longValue <= MAX_LONG_VALUE) {
                        return WorkbookFactory.createValueCell(row, getColumnIndex(), longValue, getCellStyle());
                    }
                    return WorkbookFactory.createValueCell(row, getColumnIndex(), longValue.toString(), getCellStyle());
                } else {
                    return WorkbookFactory.createValueCell(row, getColumnIndex(), TypeUtils.toConvert(int.class, value), getCellStyle());
                }
            default:
                return WorkbookFactory.createValueCell(row, getColumnIndex(), value.toString(), getCellStyle());
        }
    }

    /**
     * 合并行
     *
     * @param sheet    工作表
     * @param firstRow 首行
     * @param lastRow  最后一行
     */
    public void mergeRow(Sheet sheet, int firstRow, int lastRow) {
        if (lastRow > firstRow) {
            sheet.addMergedRegion(new CellRangeAddress(firstRow, lastRow, getColumnIndex(), getColumnIndex()));
        }
    }

    /**
     * 获取单元格样式
     *
     * @return
     */
    public CellStyle getCellStyle() {
        return cellStyle;
    }

    /**
     * 设置单元格样式
     *
     * @param cellStyle
     */
    public void setCellStyle(CellStyle cellStyle) {
        this.cellStyle = cellStyle;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}
