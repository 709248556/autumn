package com.autumn.util.excel.exports;

import com.autumn.util.DateUtils;
import com.autumn.util.excel.column.ColumnInfo;
import com.autumn.util.excel.enums.CellType;

import java.util.ArrayList;
import java.util.List;


/**
 * 导出信息
 *
 * @param <T>
 * @author ycg
 */
public abstract class AbstractExportInfo<T> {

    /**
     * 项目集合
     */
    private List<T> items;

    /**
     * 导出信息初始化
     */
    public AbstractExportInfo() {
        this.items = new ArrayList<>();
    }

    /**
     * 读取
     *
     * @param item 项目
     * @param name 名称
     * @return
     */
    public abstract Object read(T item, String name);

    /**
     * 写入
     *
     * @param item  项目
     * @param name  名称
     * @param value 值
     */
    public abstract void write(T item, String name, Object value);

    /**
     * 格式化值
     *
     * @param columnInfo
     * @param value
     * @return
     */
    public String formatValue(ColumnInfo columnInfo, Object value) {
        if (value == null) {
            return "";
        }
        switch (columnInfo.getCellType()) {
            case CellType.CELL_TYPE_STRING:
                return value.equals(true) ? "是" : "否";
            case CellType.CELL_TYPE_DATE:
                return DateUtils.createFormatDate().format(value);
            case CellType.CELL_TYPE_DATETIME:
                return DateUtils.createFormatDateTime().format(value);
            case CellType.CELL_TYPE_TIME:
                return DateUtils.createFormatTime().format(value);
            default:
                return value.toString();
        }
    }


    public List<T> getItems() {
        return items;
    }


    public void setItems(List<T> items) {
        this.items = items;
    }
}
