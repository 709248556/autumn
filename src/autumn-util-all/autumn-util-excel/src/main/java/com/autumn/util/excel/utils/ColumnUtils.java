package com.autumn.util.excel.utils;

import com.autumn.util.CollectionUtils;
import com.autumn.util.excel.column.AbstractColumn;
import com.autumn.util.excel.enums.ColumnType;

import java.util.List;

/**
 * 列的扩展
 */
public class ColumnUtils {

    /**
     * 计算合并列的列数
     *
     * @param columns
     * @return
     */
    public static int mergeCols(List<AbstractColumn> columns) {
        if (columns == null || columns.size() == 0) {
            return 0;
        }
        int count = 0;
        for (AbstractColumn column : columns) {
            if (column.getColumnType().equals(ColumnType.COLUMN)) {
                count++;
            } else {
                count += column.mergeCols();
            }
        }
        return count;
    }

    /**
     * 计算合并行的行数
     *
     * @param columns
     * @return
     */
    public static int mergeRows(List<AbstractColumn> columns) {
        int count = 0;
        if (columns == null || columns.size() == 0) {
            return count;
        }
        boolean any = CollectionUtils.any(columns, (column) -> column.getColumnType() == ColumnType.GROUP);
        if (any) {
            count = 1;
        }
        for (AbstractColumn column : columns) {
            count += column.mergeRows();
        }
        return count;
    }
}
