package com.autumn.util.excel.imports;

import com.autumn.util.excel.column.ColumnInfo;

/**
 * 导入属性
 */
public class ImportProperty {

    /**
     * Excel列名称
     */
    private String excelColumnName;

    private ColumnInfo columnInfo;

    /**
     * 实例化 ImportProperty 类新实例
     *
     * @param excelColumnName Excel 列名
     * @param columnInfo      列信息
     */
    public ImportProperty(String excelColumnName, ColumnInfo columnInfo) {
        this.excelColumnName = excelColumnName;
        this.columnInfo = columnInfo;
    }

    public String getExcelColumnName() {
        return excelColumnName;
    }

    public void setExcelColumnName(String excelColumnName) {
        this.excelColumnName = excelColumnName;
    }

    public ColumnInfo getColumnInfo() {
        return columnInfo;
    }

    public void setColumnInfo(ColumnInfo columnInfo) {
        this.columnInfo = columnInfo;
    }

}
