package com.autumn.util.excel.imports;

import com.autumn.util.excel.column.ColumnInfo;
import org.apache.poi.ss.usermodel.Sheet;

import java.util.List;

/**
 * 导入信息
 */
public class ImportInfo<T> extends ImportAdapterInfo {

    public ImportInfo() {
    }

    /**
     * @param columnIndex
     * @param columnInfo
     */
    protected ImportInfo(int columnIndex, ColumnInfo columnInfo) {
        super(columnIndex, columnInfo);
    }

    /**
     * 工作表
     */
    private Sheet sheet;

    /**
     * 导入开始行
     */
    private int beginRowIndex;

    /**
     * 导入适配器信息
     */
    private List<T> adapters;

    public Sheet getSheet() {
        return sheet;
    }

    public void setSheet(Sheet sheet) {
        this.sheet = sheet;
    }

    public List<T> getAdapters() {
        return adapters;
    }

    public void setAdapters(List<T> adapters) {
        this.adapters = adapters;
    }

    public int getBeginRowIndex() {
        return beginRowIndex;
    }

    public void setBeginRowIndex(int beginRowIndex) {
        this.beginRowIndex = beginRowIndex;
    }

}
