package com.autumn.util.excel.sheet;

import com.autumn.util.EnvironmentConstants;
import com.autumn.util.HorizontalAlignment;
import com.autumn.util.StringUtils;
import com.autumn.util.VerticalAlignment;
import com.autumn.util.excel.utils.ExcelUtils;
import com.autumn.util.excel.workbook.WorkbookFactory;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;

/**
 * 工作表头部标题
 */
public class WorkSheetHeader implements Serializable {

    private static final long serialVersionUID = -6133229548099719858L;
    private static final double DEFAULT_FONT_SIZE = 10.0;
    private static final int DEFAULT_ROW_HEIGHT = 50;

    /**
     * 标题名称
     */
    private String name;

    /**
     * 是否显示
     */
    private boolean show;

    /**
     * 字体大小
     */
    private Double fontSize;

    /**
     * 行高
     */
    private Integer rowHeight;

    /**
     * 是否导出头
     *
     * @return
     */
    public boolean isExportHeader() {
        return this.isShow() && !StringUtils.isNullOrBlank(this.getName());
    }

    /**
     * 创建行
     *
     * @param sheet     表格
     * @param alignment 水平对齐方式
     * @param index     索引
     * @param mergeCols 合并行数
     * @param bold      是否加粗
     * @return
     */
    public Row createRow(Sheet sheet, HorizontalAlignment alignment, int index, int mergeCols, Boolean bold) {
        if (this.isExportHeader()) {
            double fontSize = DEFAULT_FONT_SIZE;
            if (this.fontSize != null && this.fontSize > 0) {
                fontSize = this.fontSize;
            }
            int rowHeight = DEFAULT_ROW_HEIGHT;
            if (this.rowHeight != null && this.rowHeight > 0) {
                rowHeight = this.rowHeight;
            }
            return WorkbookFactory.createMergeRow(sheet, index, name, mergeCols, alignment,
                    VerticalAlignment.CENTER, fontSize, bold, rowHeight);
        }
        return null;
    }

    /**
     * 输入Csv格式
     *
     * @param outputStream 输入流
     * @param mergeCols    合并行数
     */
    public void writeCsv(OutputStream outputStream, int mergeCols) throws IOException {
        if (this.isShow() && !StringUtils.isNullOrBlank(this.name)) {
            StringBuilder sb = new StringBuilder(this.name.length() + mergeCols + 1);
            sb.append(ExcelUtils.toCsvValue(this.name));
            if (mergeCols > 1) {
                for (int i = 1; i < mergeCols; i++) {
                    sb.append(",");
                }
            }
            sb.append(EnvironmentConstants.LINE_SEPARATOR);
            ExcelUtils.writeCsvValue(outputStream, sb.toString());
        }
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isShow() {
        return show;
    }

    public void setShow(boolean show) {
        this.show = show;
    }

    public Double getFontSize() {
        return fontSize;
    }

    public void setFontSize(Double fontSize) {
        this.fontSize = fontSize;
    }

    public Integer getRowHeight() {
        return rowHeight;
    }

    public void setRowHeight(Integer rowHeight) {
        this.rowHeight = rowHeight;
    }
}
