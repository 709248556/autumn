package com.autumn.util.excel;

import com.autumn.util.excel.column.ColumnInfo;

/**
 * 适配信息
 */
public abstract class AbstractAdapterInfo {

    public AbstractAdapterInfo() {
    }

    /**
     * 实例化 AdapterInfo 类新实例
     *
     * @param columnIndex 列索引
     * @param columnInfo  列信息
     */
    protected AbstractAdapterInfo(int columnIndex, ColumnInfo columnInfo) {
        this.columnIndex = columnIndex;
        this.columnInfo = columnInfo;
    }

    /**
     * 列索引
     */
    private int columnIndex;

    /**
     * 列信息
     */
    private ColumnInfo columnInfo;


    public int getColumnIndex() {
        return columnIndex;
    }

    public void setColumnIndex(int columnIndex) {
        this.columnIndex = columnIndex;
    }

    public ColumnInfo getColumnInfo() {
        return columnInfo;
    }

    public void setColumnInfo(ColumnInfo columnInfo) {
        this.columnInfo = columnInfo;
    }
}
