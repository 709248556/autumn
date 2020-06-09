package com.autumn.util.excel.workbook;

import com.autumn.exception.ExceptionUtils;
import com.autumn.util.StringUtils;
import com.autumn.util.TypeUtils;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;

import java.awt.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 工作簿工厂
 */
public class WorkbookFactory {

    /**
     * 默认字体名称
     */
    public static String DEFAULT_FONT_NAME;

    /**
     * 行高比率
     */
    public static final double ROW_HEIGHT_RATE = 15.15;
    /**
     * 列宽比率
     */
    public static final double COLUMN_WIDTH_RATE = 36.7;
    /**
     * 字体大小比率
     */
    public static final double FONT_SIZE_RATE = 20;

    public static final String WEI_RUAN_YA_HEI = "微软雅黑";
    public static final String SONG_TI = "宋体";

    static {
        String[] values = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
        for (String item : values) {
            if (item.equalsIgnoreCase(WEI_RUAN_YA_HEI)) {
                DEFAULT_FONT_NAME = WEI_RUAN_YA_HEI;
                break;
            }
        }
        if (DEFAULT_FONT_NAME == null) {
            DEFAULT_FONT_NAME = SONG_TI;
        }
    }

    /**
     * 解析Excel列名称
     *
     * @param col 输入列号,始终从1开始
     * @return 返回列名
     */
    public static String parseExcelColName(int col) {
        if (col < 1) {
            col = 1;
        }
        int bit;
        StringBuilder result = new StringBuilder();
        int value;
        while (col > 0) {
            bit = col % 26;
            col /= 26;
            if (bit == 0) {
                value = 26;
                col--;
            } else {
                value = bit;
            }
            result.insert(0, (char) (value + 64));
        }
        return result.toString();
    }

    /**
     * 解析Excel列名称
     *
     * @param row 输入行号,始终从1开始
     * @param col 输入列号,始终从1开始
     * @return
     */
    public static String parseExcelColName(int row, int col) {
        return parseExcelColName(col) + row;
    }

    /**
     * 创建工作表集合，并返回第一个工作表
     *
     * @param workbook   工作簿
     * @param sheetCount 工作表数量
     * @return
     */
    public static Sheet createSheets(Workbook workbook, Integer sheetCount) {
        if (sheetCount == null) {
            sheetCount = 3;
        }
        if (sheetCount < 1) {
            sheetCount = 3;
        }
        Sheet sheet = null;
        for (int i = 1; i <= sheetCount; i++) {
            Sheet s = workbook.createSheet(String.format("Sheet%d", i));
            if (sheet == null) {
                sheet = s;
            }
        }
        return sheet;
    }

    /**
     * 创建合并行
     *
     * @param sheet               表格
     * @param rowIndex            行索引
     * @param content             内容
     * @param mergedColumnCount   合并列数量
     * @param horizontalAlignment 水平对齐方式
     * @param verticalAlignment   垂直对齐方式
     * @param fontSize            字体尺寸
     * @param bold                是否加粗
     * @param height              行高
     * @return
     */
    public static Row createMergeRow(Sheet sheet, int rowIndex, String content, int mergedColumnCount,
                                     com.autumn.util.HorizontalAlignment horizontalAlignment,
                                     com.autumn.util.VerticalAlignment verticalAlignment, double fontSize, Boolean bold,
                                     int height) {
        return createMergeRow(sheet, rowIndex, content, 0, mergedColumnCount, horizontalAlignment, verticalAlignment,
                fontSize, bold, height);
    }

    /**
     * 创建合并行
     *
     * @param sheet               表格
     * @param rowIndex            行索引
     * @param content             内容
     * @param mergedColumnCount   合并列数量
     * @param horizontalAlignment 水平对齐方式
     * @param verticalAlignment   垂直对齐方式
     * @param fontSize            字体尺寸
     * @param bold                是否加粗
     * @param height              行高
     * @param beginColumnIndex    起始列索引
     * @return
     */
    public static Row createMergeRow(Sheet sheet, int rowIndex, String content, int beginColumnIndex,
                                     int mergedColumnCount, com.autumn.util.HorizontalAlignment horizontalAlignment,
                                     com.autumn.util.VerticalAlignment verticalAlignment, double fontSize, boolean bold,
                                     int height) {
        if (content == null) {
            content = "";
        }
        if (height < 1) {
            height = 25;
        }
        if (fontSize < 1) {
            fontSize = 16;
        }
        Row row = createRow(sheet, rowIndex, height);
        if (mergedColumnCount > 1) {
            mergedColumnCount--;
            sheet.addMergedRegion(
                    new CellRangeAddress(rowIndex, rowIndex, beginColumnIndex, beginColumnIndex + mergedColumnCount));
        }
        Workbook workbook = sheet.getWorkbook();
        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setAlignment(
                org.apache.poi.ss.usermodel.HorizontalAlignment.valueOf(horizontalAlignment.name().toUpperCase()));
        cellStyle.setVerticalAlignment(
                org.apache.poi.ss.usermodel.VerticalAlignment.valueOf(verticalAlignment.name().toUpperCase()));
        cellStyle.setWrapText(true);
        Font font = workbook.createFont();
        font.setFontHeight((short) (fontSize * FONT_SIZE_RATE));
        font.setBold(bold);
        font.setFontName(DEFAULT_FONT_NAME);
        cellStyle.setFont(font);
        Cell cell = row.createCell(0);
        cell.setCellValue(content);
        cell.setCellStyle(cellStyle);
        return row;
    }

    /**
     * 创建行并指定高度
     *
     * @param sheet    工作表
     * @param rowIndex 行索引
     * @param height   高度(像素)
     * @return
     */
    public static Row createRow(Sheet sheet, int rowIndex, int height) {
        Row row = sheet.createRow(rowIndex);
        row.setHeight(rowHeight(height));
        return row;
    }

    /**
     * 行高计算
     *
     * @param height
     * @return
     */
    public static short rowHeight(int height) {
        double value = height * ROW_HEIGHT_RATE;
        if (value >= Short.MAX_VALUE) {
            return Short.MAX_VALUE;
        }
        return (short) value;
    }

    /**
     * 列宽计算
     *
     * @param width
     * @return
     */
    public static int columnWidth(int width) {
        double value = width * COLUMN_WIDTH_RATE;
        if (value >= Integer.MAX_VALUE) {
            return Integer.MAX_VALUE;
        }
        return (int) value;
    }

    /**
     * 设置列高
     */
    public static void setHeight(Row row, int height) {
        ExceptionUtils.checkNotNull(row, "row");
        row.setHeight(rowHeight(height));
    }

    /**
     * 设置列宽
     */
    public static void setWidth(Sheet sheet, int columnIndex, int width) {
        sheet.setColumnWidth(columnIndex, columnWidth(width));
    }

    /**
     * 水平对齐方式 把Poi里的枚举转换成本项目的同等的枚举类
     *
     * @param alignment
     * @return
     */
    public static org.apache.poi.ss.usermodel.HorizontalAlignment poiHorizontalAlignment(
            com.autumn.util.HorizontalAlignment alignment) {
        switch (alignment) {
            case CENTER:
                return org.apache.poi.ss.usermodel.HorizontalAlignment.CENTER;
            case LEFT:
                return org.apache.poi.ss.usermodel.HorizontalAlignment.LEFT;
            case RIGHT:
                return org.apache.poi.ss.usermodel.HorizontalAlignment.RIGHT;
            default:
                return org.apache.poi.ss.usermodel.HorizontalAlignment.GENERAL;
        }
    }

    /**
     * 把poi里的枚举转换成本项目的同等的枚举类
     *
     * @param alignment
     * @return
     */
    public static org.apache.poi.ss.usermodel.VerticalAlignment poiVerticalAlignment(
            com.autumn.util.VerticalAlignment alignment) {
        switch (alignment) {
            case BOTTOM:
                return org.apache.poi.ss.usermodel.VerticalAlignment.BOTTOM;
            case TOP:
                return org.apache.poi.ss.usermodel.VerticalAlignment.TOP;
            case CENTER:
            default:
                return org.apache.poi.ss.usermodel.VerticalAlignment.CENTER;
        }
    }

    /**
     * 创建的单元格样式
     *
     * @param workbook  工作薄
     * @param alignment 对齐方式
     * @param fontSize  字体大小
     * @param boldFont  粗体
     * @param border    绘制边框 10 false true
     * @return
     */
    public static CellStyle createCellStyle(Workbook workbook,
                                            com.autumn.util.HorizontalAlignment alignment, Double fontSize, Boolean boldFont,
                                            Boolean border) {
        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setVerticalAlignment(poiVerticalAlignment(com.autumn.util.VerticalAlignment.CENTER));
        cellStyle.setAlignment(org.apache.poi.ss.usermodel.HorizontalAlignment.valueOf(alignment.name().toUpperCase()));
        if (border == null) {
            border = true;
        }
        if (border) {
            cellStyle.setBorderLeft(org.apache.poi.ss.usermodel.BorderStyle.THIN);
            cellStyle.setBorderTop(org.apache.poi.ss.usermodel.BorderStyle.THIN);
            cellStyle.setBorderRight(org.apache.poi.ss.usermodel.BorderStyle.THIN);
            cellStyle.setBorderBottom(org.apache.poi.ss.usermodel.BorderStyle.THIN);
        }
        Font font = workbook.createFont();
        if (fontSize == null) {
            fontSize = 10D;
        }
        font.setFontHeight((short) (fontSize * FONT_SIZE_RATE));
        if (boldFont == null) {
            boldFont = false;
        }
        font.setBold(boldFont);
        font.setFontName(DEFAULT_FONT_NAME);
        cellStyle.setFont(font);
        return cellStyle;
    }

    /**
     * 创建单元格格式
     *
     * @param workbook 工作薄
     * @param format   格式
     * @return
     */
    public static short createCellFormat(Workbook workbook, String format) {
        DataFormat dataFormat = workbook.createDataFormat();
        return dataFormat.getFormat(format);
    }

    /**
     * 创建十进制格式
     *
     * @return
     */
    public static short createDecimalDataFormat(Workbook workbook, int decimalLength) {
        DataFormat format = workbook.createDataFormat();
        return format.getFormat("#,##0." + StringUtils.padLeft("", decimalLength, '0'));
    }

    /**
     * 创建日期格式
     *
     * @return
     */
    public static short createDateDataFormat(Workbook workbook) {
        DataFormat format = workbook.createDataFormat();
        return format.getFormat("yyyy-MM-dd");
    }

    /**
     * 创建日期时间格式
     *
     * @return
     */
    public static short createDateTimeDataFormat(Workbook workbook) {
        DataFormat format = workbook.createDataFormat();
        return format.getFormat("yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 创建时间格式
     *
     * @return
     */
    public static short createTimeDataFormat(Workbook workbook) {
        DataFormat format = workbook.createDataFormat();
        return format.getFormat("HH:mm:ss");
    }

    /**
     * 创建标题单元格
     *
     * @return
     */
    public static Cell createTitleCell(Row row, int columnIndex, String title, CellStyle cellStyle) {
        return createTitleCell(row, columnIndex, title, cellStyle, 0, 0);
    }

    /**
     * @return
     */
    public static Cell createTitleCell(Row row, int columnIndex, String title, CellStyle cellStyle, int mergedRows,
                                       int mergeCols) {
        Cell cell;
        if (mergedRows > 0 || mergeCols > 0) {
            int rowNum = row.getRowNum();
            for (int r = rowNum; r <= rowNum + mergedRows; r++) {
                Row cellRow = row.getSheet().getRow(r);
                if (cellRow == null) {
                    cellRow = row.getSheet().createRow(r);
                }
                for (int c = columnIndex; c <= columnIndex + mergeCols; c++) {
                    cell = cellRow.createCell(c);
                    cell.setCellStyle(cellStyle);
                }
            }
            row.getSheet().addMergedRegion(
                    new CellRangeAddress(rowNum, rowNum + mergedRows, columnIndex, columnIndex + mergeCols));
        }
        cell = row.getCell(columnIndex);
        if (cell == null) {
            cell = row.createCell(columnIndex);
        }
        cell.setCellValue(title);
        cell.setCellStyle(cellStyle);
        return cell;
    }

    /**
     * 创建日期单元格
     *
     * @param cellStyle 对齐方式
     * @param value     值
     * @return
     */
    public static Cell createValueCell(Row row, int columnIndex, Date value, CellStyle cellStyle) {
        Cell cell = row.createCell(columnIndex);
        cell.setCellValue(value);
        cell.setCellStyle(cellStyle);
        return cell;
    }

    /**
     * 创建十进制单元格
     *
     * @return
     */
    public static Cell createValueCell(Row row, int columnIndex, BigDecimal value, CellStyle cellStyle) {
        Cell cell = row.createCell(columnIndex);
        cell.setCellValue(TypeUtils.toConvert(double.class, value));
        cell.setCellStyle(cellStyle);
        return cell;
    }

    /**
     * 创建值单元格
     *
     * @return
     */
    public static Cell createValueCell(Row row, int columnIndex, double value, CellStyle cellStyle) {
        Cell cell = row.createCell(columnIndex);
        cell.setCellValue(value);
        cell.setCellStyle(cellStyle);
        return cell;
    }

    /**
     * 创建布尔制单元格
     *
     * @return
     */
    public static Cell createValueCell(Row row, int columnIndex, boolean value, CellStyle cellStyle) {
        Cell cell = row.createCell(columnIndex);
        cell.setCellValue(value);
        cell.setCellStyle(cellStyle);
        return cell;
    }

    /**
     * 创建字符窜制单元格
     *
     * @return
     */
    public static Cell createValueCell(Row row, int columnIndex, String value, CellStyle cellStyle) {
        Cell cell = row.createCell(columnIndex);
        cell.setCellValue(value);
        cell.setCellStyle(cellStyle);
        return cell;
    }

    public static String getDefaultFontName() {
        return DEFAULT_FONT_NAME;
    }

    @SuppressWarnings("unused")
    private static void setDefaultFontName(String defaultFontName) {
        WorkbookFactory.DEFAULT_FONT_NAME = defaultFontName;
    }
}
