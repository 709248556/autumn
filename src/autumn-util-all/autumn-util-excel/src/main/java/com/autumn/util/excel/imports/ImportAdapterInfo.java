package com.autumn.util.excel.imports;

import com.autumn.util.excel.AbstractAdapterInfo;
import com.autumn.util.excel.column.ColumnInfo;

/**
 * 导入适配器
 */
public class ImportAdapterInfo extends AbstractAdapterInfo {

    public ImportAdapterInfo() {
    }

    public ImportAdapterInfo(int columnIndex, ColumnInfo columnInfo) {
        super(columnIndex, columnInfo);
    }

}
