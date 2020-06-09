package com.autumn.util.excel.sheet;

import com.autumn.util.excel.column.AbstractColumn;
import com.autumn.util.excel.column.ColumnInfo;

import java.io.Serializable;
import java.util.List;

/**
 * 工作表导出信息
 * <p>
 * </p>
 *
 * @description TODO
 * @author: 老码农
 * @create: 2020-02-24 12:44
 **/
public class WorkSheetExportInfo implements Serializable {

    private static final long serialVersionUID = 7152563557771006667L;

    private final List<AbstractColumn> titleColumns;
    private final List<ColumnInfo> dataColumns;
    private final int mergeRows;
    private final int mergeCols;
    private final int titleRows;

    public WorkSheetExportInfo(List<AbstractColumn> titleColumns,
                               List<ColumnInfo> dataColumns,
                               int mergeRows,
                               int mergeCols,
                               int titleRows) {
        this.titleColumns = titleColumns;
        this.dataColumns = dataColumns;
        this.mergeRows = mergeRows;
        this.mergeCols = mergeCols;
        this.titleRows = titleRows;
    }

    /**
     * 获取标题列集合
     *
     * @return
     */
    public List<AbstractColumn> getTitleColumns() {
        return this.titleColumns;
    }

    /**
     * 获取数据列集合
     *
     * @return
     */
    public List<ColumnInfo> getDataColumns() {
        return this.dataColumns;
    }

    /**
     * 获取合并行数
     *
     * @return
     */
    public int getMergeRows() {
        return this.mergeRows;
    }

    /**
     * 获取标题列数
     *
     * @return
     */
    public int getMergeCols() {
        return this.mergeCols;
    }

    /**
     * 获取标题行数
     *
     * @return
     */
    public int getTitleRows() {
        return this.titleRows;
    }


}
