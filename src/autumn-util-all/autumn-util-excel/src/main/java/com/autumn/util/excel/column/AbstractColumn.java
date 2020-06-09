package com.autumn.util.excel.column;

import com.autumn.util.excel.enums.ColumnType;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Sheet;

import java.io.Serializable;
import java.util.List;

/**
 * Excel列
 */
public abstract class AbstractColumn implements Serializable {

    /**
     * 序列号
     */
    private static final long serialVersionUID = 8021259866848960039L;

    /**
     * 友好名称
     */
    private String friendlyName;

    /**
     * 顺序
     */
    private Integer order;


    public String getFriendlyName() {
        return friendlyName;
    }

    public void setFriendlyName(String friendlyName) {
        this.friendlyName = friendlyName;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    /**
     * 获取列类型
     *
     * @return
     */
    public abstract ColumnType getColumnType();

    /**
     * 检查
     */
    public abstract void check();

    /**
     * 合并行数
     *
     * @return
     */
    public abstract int mergeRows();

    /**
     * 合并列数
     *
     * @return
     */
    public abstract int mergeCols();

    /**
     * 列排序
     */
    public abstract void orderColumns();

    /**
     * 顺序列信息排序
     *
     * @return
     */
    public abstract List<ColumnInfo> columnInfos();

    /**
     * 创建标题单元格
     * @param sheet
     * @param cellStyle
     * @param columnIndex 列索引
     * @param beginRow 开始行
     * @param mergeRows 合并行数
     * @param isImportTemplate 是否导入模板
     * @return
     */
    public abstract Integer createTitleCell(Sheet sheet, CellStyle cellStyle, Integer columnIndex, Integer beginRow, Integer mergeRows, Boolean isImportTemplate);
}
