package com.autumn.util.excel.utils;

import com.autumn.exception.ExceptionUtils;
import com.autumn.util.DateUtils;
import com.autumn.util.StringUtils;
import com.autumn.util.Time;
import com.autumn.util.excel.ExcelException;
import com.autumn.util.excel.exports.GenericExportInfo;
import com.autumn.util.excel.sheet.WorkSheetInfo;
import com.autumn.util.excel.workbook.WorkbookProperties;
import org.apache.commons.io.IOUtils;
import org.apache.poi.hpsf.DocumentSummaryInformation;
import org.apache.poi.hpsf.SummaryInformation;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Excel帮助
 */
public class ExcelUtils {

    /**
     * Excel扩展名
     */
    public static final String EXCEL_EXTENSION_NAME = "xlsx";

    /**
     * Excel 连接扩展名,包括点(.)
     */
    public static final String EXCEL_JOIN_EXTENSION_NAME = "." + EXCEL_EXTENSION_NAME;

    /**
     * GBK 编码名称
     */
    public static final String CHARSET_NAME_GBK = "GBK";

    /**
     * GBK编码
     */
    public static final Charset CHARSET_GBK;

    static {
        if (Charset.isSupported(CHARSET_NAME_GBK)) {
            CHARSET_GBK = Charset.forName(CHARSET_NAME_GBK);
        } else {
            CHARSET_GBK = StandardCharsets.ISO_8859_1;
        }
    }

    /**
     * 写入 Csv值
     *
     * @param outputStream
     * @param value
     * @throws IOException
     */
    public static void writeCsvValue(OutputStream outputStream, String value) throws IOException {
        IOUtils.write(value, outputStream, ExcelUtils.CHARSET_GBK);
    }

    /**
     * 转换为 Csv值
     *
     * @param value
     * @throws IOException
     */
    public static String toCsvValue(Object value) {
        if (value != null) {
            String str;
            if (value instanceof String) {
                str = (String) value;
                if (str.indexOf("\"") > -1) {
                    str = "\"" + str.replace("\"", "\"\"") + "\"";
                } else {
                    if (str.indexOf(",") > -1) {
                        str = "\"" + str + "\"";
                    }
                }
            } else {
                if (value instanceof Date) {
                    Date date = (Date) value;
                    Time t = DateUtils.getTime(date);
                    if (t.getHour() == 0 && t.getMinute() == 0 && t.getSecond() == 0) {
                        str = DateUtils.dateFormat(date, "yyyy-MM-dd");
                    } else {
                        str = DateUtils.dateFormat(date, "yyyy-MM-dd HH:mm:ss");
                    }
                } else {
                    str = value.toString();
                }
            }
            return str;
        }
        return "";
    }

    /**
     * 获取工作表信息
     *
     * @param type 类型
     * @return
     */
    public static WorkSheetInfo getWorkSheetInfo(Class<?> type) {
        return getWorkSheetInfo(type, null);
    }

    /**
     * 获取工作表信息
     *
     * @param type       类型
     * @param headerName 标题
     * @return
     */
    public static WorkSheetInfo getWorkSheetInfo(Class<?> type, String headerName) {
        ExceptionUtils.checkNotNull(type, "type");
        WorkSheetInfo info = WorkbookUtils.createWorkSheetInfo(type);
        if (!StringUtils.isNullOrBlank(headerName)) {
            info.getHeader().setShow(true);
            info.getHeader().setName(headerName);
        }
        return info;
    }

    /**
     * 导出
     *
     * @param exportInfo       导出信息
     * @param isImportTemplate 是否为导入模板 默认值= false
     * @param <E>
     * @return
     */
    public static <E> Workbook export(GenericExportInfo<E> exportInfo) {
        return export(exportInfo, false);
    }

    /**
     * 导出
     *
     * @param exportInfo       导出信息
     * @param isImportTemplate 是否为导入模板
     * @param <E>
     * @return
     */
    public static <E> Workbook export(GenericExportInfo<E> exportInfo, boolean isImportTemplate) {
        ExceptionUtils.checkNotNull(exportInfo, "exportInfo");
        WorkSheetInfo workSheetInfo = getWorkSheetInfo(exportInfo.getBeanClass());
        return workSheetInfo.createExportWorkbook(exportInfo, isImportTemplate);
    }

    /**
     * 导出
     *
     * @param beanClass 类型
     * @param items     项目集合
     * @param <E>
     * @return
     */
    public static <E> Workbook export(Class<E> beanClass, List<E> items) {
        return export(beanClass, items, false);
    }

    /**
     * 导出
     *
     * @param beanClass        类型
     * @param items            项目集合
     * @param isImportTemplate 是否为导入模板
     * @param <E>
     * @return
     */
    public static <E> Workbook export(Class<E> beanClass, List<E> items, boolean isImportTemplate) {
        GenericExportInfo<E> genericExportInfo = new GenericExportInfo<>(beanClass);
        genericExportInfo.setItems(items);
        return export(genericExportInfo, isImportTemplate);
    }

    /**
     * 导入
     *
     * @param input        导入流
     * @param genericClass 类型
     * @param <E>
     * @return
     */
    public static <E> List<E> importData(InputStream input, Class<E> genericClass) {
        WorkSheetInfo workSheetInfo = getWorkSheetInfo(genericClass);
        return workSheetInfo.createObjectList(input, workSheetInfo.getSheetName(), genericClass);
    }

    /**
     * 导入
     *
     * @param input        导入流
     * @param sheetName    工作表名称,未指定则自动查找符合格式的首个表
     * @param genericClass
     * @param <E>
     * @return
     */
    public static <E> List<E> importData(InputStream input, String sheetName, Class<E> genericClass) {
        WorkSheetInfo workSheetInfo = getWorkSheetInfo(genericClass);
        return workSheetInfo.createObjectList(input, sheetName, genericClass);
    }

    /**
     * 导入
     *
     * @param workbook     工作簿
     * @param genericClass 类型
     * @param <E>
     * @return
     */
    public static <E> List<E> importData(Workbook workbook, Class<E> genericClass) {
        WorkSheetInfo workSheetInfo = getWorkSheetInfo(genericClass);
        return workSheetInfo.createObjectList(workbook, workSheetInfo.getSheetName(), genericClass);
    }

    /**
     * 导入
     *
     * @param workbook     工作簿
     * @param sheetName    工作表名称,未指定则自动查找符合格式的首个表
     * @param genericClass 类型
     * @param <E>
     * @return
     */
    public static <E> List<E> importData(Workbook workbook, String sheetName, Class<E> genericClass) {
        WorkSheetInfo workSheetInfo = getWorkSheetInfo(genericClass);
        return workSheetInfo.createObjectList(workbook, sheetName, genericClass);
    }

    /**
     * 创建工作簿
     *
     * @return
     */
    public static Workbook createWorkbook() {
        return new SXSSFWorkbook();
    }

    /**
     * 创建工作簿
     *
     * @param input 输入流
     * @return
     */
    public static Workbook createWorkbook(InputStream input) {
        ExceptionUtils.checkNotNull(input, "input");
        SXSSFWorkbook workbook;
        try {
            workbook = new SXSSFWorkbook(new XSSFWorkbook(input));
        } catch (Exception err) {
            throw new ExcelException("导入的文件格式不正确或不支持,仅支持Excel 2010以上版本", err);
        }
        return workbook;
    }

    /**
     * 设置属性
     *
     * @param workbook   工作簿
     * @param properties 属性
     */
    public static void setProperties(Workbook workbook, WorkbookProperties properties) {
        ExceptionUtils.checkNotNull(workbook, "workbook");
        if (properties != null) {
            if (workbook instanceof HSSFWorkbook) {
                HSSFWorkbook hssfWorkbook = (HSSFWorkbook) workbook;
                hssfWorkbook.createInformationProperties();
                DocumentSummaryInformation summary = hssfWorkbook.getDocumentSummaryInformation();
                String filed = properties.getCompany();
                summary.setCompany(filed != null ? filed : "");
                filed = properties.getCategory();
                summary.setCategory(filed != null ? filed : "");
                SummaryInformation info = hssfWorkbook.getSummaryInformation();
                filed = properties.getAuthor();
                info.setAuthor(filed != null ? filed : "");
                filed = properties.getSubject();
                info.setSubject(filed != null ? filed : "");
                filed = properties.getApplicationName();
                info.setApplicationName(filed != null ? filed : "");
                filed = properties.getTitle();
                info.setTitle(filed != null ? filed : "");
            } else {
                XSSFWorkbook xssfWorkbook;
                if (workbook instanceof SXSSFWorkbook) {
                    xssfWorkbook = ((SXSSFWorkbook) workbook).getXSSFWorkbook();
                } else if (workbook instanceof XSSFWorkbook) {
                    xssfWorkbook = (XSSFWorkbook) workbook;
                } else {
                    xssfWorkbook = null;
                }
                if (xssfWorkbook != null) {
                    xssfWorkbook.getProperties().getCoreProperties().setCategory(properties.getCategory() != null ? properties.getCategory() : "");
                    xssfWorkbook.getProperties().getCoreProperties().setSubjectProperty(properties.getSubject() != null ? properties.getSubject() : "");
                    xssfWorkbook.getProperties().getCoreProperties().setCreator(properties.getAuthor() != null ? properties.getAuthor() : "");
                    xssfWorkbook.getProperties().getCoreProperties().setTitle(properties.getTitle() != null ? properties.getTitle() : "");
                }
            }
        }
    }

    /**
     * 获取工作表集合
     *
     * @param inputStream 输入流
     * @return
     */
    public static List<String> sheets(InputStream inputStream) {
        return sheets(createWorkbook(inputStream));
    }

    /**
     * 获取工作表集合
     *
     * @param workbook 工作簿
     * @return
     */
    public static List<String> sheets(Workbook workbook) {
        ExceptionUtils.checkNotNull(workbook, "workbook");
        if (workbook instanceof SXSSFWorkbook) {
            SXSSFWorkbook sxssfWorkbook = (SXSSFWorkbook) workbook;
            workbook = sxssfWorkbook.getXSSFWorkbook();
        }
        List<String> tables = new ArrayList<>(16);
        for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
            Sheet sheet = workbook.getSheetAt(i);
            tables.add(sheet.getSheetName());
        }
        return tables;
    }

    /**
     * 判断是否是合并单元格
     *
     * @param sheet
     * @param row
     * @param column
     * @return
     */
    public static boolean isMergedCell(Sheet sheet, int row, int column) {
        int sheetMergeCount = sheet.getNumMergedRegions();
        for (int i = 0; i < sheetMergeCount; i++) {
            CellRangeAddress range = sheet.getMergedRegion(i);
            int firstColumn = range.getFirstColumn();
            int lastColumn = range.getLastColumn();
            int firstRow = range.getFirstRow();
            int lastRow = range.getLastRow();
            if (row >= firstRow && row <= lastRow) {
                if (column >= firstColumn && column <= lastColumn) {
                    return true;
                }
            }
        }
        return false;
    }

}
