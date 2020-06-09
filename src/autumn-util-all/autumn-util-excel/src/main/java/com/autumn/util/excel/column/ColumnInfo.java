package com.autumn.util.excel.column;

import com.autumn.util.StringUtils;
import com.autumn.util.excel.enums.CellType;
import com.autumn.util.excel.enums.ColumnType;
import com.autumn.util.HorizontalAlignment;
import com.autumn.util.excel.ExcelException;
import com.autumn.util.excel.exports.ExportAdapterInfo;
import com.autumn.util.excel.workbook.WorkbookFactory;
import com.autumn.util.tuple.TupleTwo;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.util.ArrayList;
import java.util.List;

/**
 * 列信息
 */
public class ColumnInfo extends AbstractColumn {
    /**
     * 序列号
     */
    private static final long serialVersionUID = -6663962464878773892L;

    private final List<ColumnInfo> columnInfos;

    /**
     * 初始化列信息
     */
    public ColumnInfo() {
        this.columnInfos = new ArrayList<>(16);
        this.setAlignment(HorizontalAlignment.LEFT);
        this.columnInfos.add(this);
    }

    /**
     * 属性名称
     */
    private String propertyName;

    /**
     * 宽度
     */
    private int width;

    /**
     * 单元格类型
     */
    private Integer cellType;

    /**
     * 对齐方式
     */
    private HorizontalAlignment alignment;

    /**
     * 格式
     */
    private String format;

    /**
     * 获取或设置导入非空值
     */
    private boolean importNotNullable;

    /**
     * 获取或设置是否导入列
     */
    private boolean isImportColumn;

    /**
     * 是否合并内容相同的行
     */
    private boolean isMergeContentRow;

    /**
     * 是否合并空白内容相同的行
     */
    private boolean isMergeBlankContentRow;

    @Override
    public final ColumnType getColumnType() {
        return ColumnType.COLUMN;
    }

    /**
     * 创建导出适配器
     *
     * @param workbook 工作薄
     * @param index
     * @return
     */
    public TupleTwo<List<ExportAdapterInfo>, Integer> createExportAdapters(Workbook workbook, Integer index) {
        List<ExportAdapterInfo> items = new ArrayList<>();
        items.add(new ExportAdapterInfo(index, this, createCellStyle(workbook)));
        index++;
        return new TupleTwo<>(items, index);
    }

    /**
     * 创建样式
     *
     * @param workbook 工作薄
     * @return
     */
    public CellStyle createCellStyle(Workbook workbook) {
        CellStyle cellStyle = WorkbookFactory.createCellStyle(workbook, getAlignment(), 10.0, false, true);
        cellStyle.setAlignment(org.apache.poi.ss.usermodel.HorizontalAlignment.valueOf(this.getAlignment().name()));
        cellStyle.setWrapText(true);
        if (!StringUtils.isNullOrBlank(this.getFormat())) {
            cellStyle.setDataFormat(WorkbookFactory.createCellFormat(workbook, this.getFormat()));
        } else {
            if (this.getCellType() != null) {
                switch (this.getCellType()) {
                    case CellType.CELL_TYPE_DATE:
                        cellStyle.setDataFormat(WorkbookFactory.createDateDataFormat(workbook));
                        break;
                    case CellType.CELL_TYPE_DATETIME:
                        cellStyle.setDataFormat(WorkbookFactory.createDateTimeDataFormat(workbook));
                        break;
                    case CellType.CELL_TYPE_BIGDECIMAL:
                        cellStyle.setDataFormat(WorkbookFactory.createDecimalDataFormat(workbook, 2));
                        break;
                    default:
                        break;
                }
            }
        }
        return cellStyle;
    }

    @Override
    public final void orderColumns() {
    }

    @Override
    public final List<ColumnInfo> columnInfos() {
        return this.columnInfos;
    }

    @Override
    public final void check() {
        if (StringUtils.isNullOrBlank(this.getFriendlyName())) {
            throw new ExcelException("FriendlyName 为 null 或空白值");
        }
        if (StringUtils.isNullOrBlank(this.getPropertyName())) {
            throw new ExcelException("PropertyName 为 null 或空白值");
        }
    }

    @Override
    public final int mergeRows() {
        return 0;
    }

    @Override
    public final int mergeCols() {
        return 0;
    }

    @Override
    public Integer createTitleCell(Sheet sheet, CellStyle cellStyle, Integer columnIndex, Integer beginRow,
                                   Integer mergeRows, Boolean isImportTemplate) {
        Row row = sheet.getRow(beginRow);
        if (row == null) {
            row = sheet.createRow(beginRow);
        }
        String name;
        if (isImportTemplate && importNotNullable) {
            name = String.format("%s(*)", this.getFriendlyName());
        } else {
            name = this.getFriendlyName();
        }
        if (mergeRows > 0) {
            WorkbookFactory.createTitleCell(row, columnIndex, name, cellStyle, mergeRows, 0);
        } else {
            WorkbookFactory.createTitleCell(row, columnIndex, name, cellStyle);
        }
        if (this.getWidth() <= 0) {
            this.setWidth(80);
        }
        WorkbookFactory.setWidth(sheet, columnIndex, this.getWidth());
        return ++columnIndex;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public Integer getCellType() {
        return cellType;
    }

    public void setCellType(Integer cellType) {
        this.cellType = cellType;
    }

    public HorizontalAlignment getAlignment() {
        return alignment;
    }

    public void setAlignment(HorizontalAlignment alignment) {
        this.alignment = alignment;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public boolean isImportNotNullable() {
        return importNotNullable;
    }

    public void setImportNotNullable(boolean importNotNullable) {
        this.importNotNullable = importNotNullable;
    }

    public boolean isImportColumn() {
        return isImportColumn;
    }

    public void setImportColumn(boolean isImportColumn) {
        this.isImportColumn = isImportColumn;
    }

    public boolean isMergeContentRow() {
        return isMergeContentRow;
    }

    public void setMergeContentRow(boolean isMergeContentRow) {
        this.isMergeContentRow = isMergeContentRow;
    }

    public boolean isMergeBlankContentRow() {
        return isMergeBlankContentRow;
    }

    public void setMergeBlankContentRow(boolean isMergeBlankContentRow) {
        this.isMergeBlankContentRow = isMergeBlankContentRow;
    }
}
