package com.autumn.util.excel.utils;

import com.autumn.exception.ExceptionUtils;
import com.autumn.util.HorizontalAlignment;
import com.autumn.util.StringUtils;
import com.autumn.util.TimeSpan;
import com.autumn.util.excel.sheet.WorkSheetHeader;
import com.autumn.util.excel.sheet.WorkSheetInfo;
import com.autumn.util.excel.annotation.ExcelColumn;
import com.autumn.util.excel.annotation.ExcelNonColumn;
import com.autumn.util.excel.annotation.ExcelWorkSheet;
import com.autumn.util.excel.column.AbstractColumn;
import com.autumn.util.excel.column.ColumnGroup;
import com.autumn.util.excel.column.ColumnInfo;
import com.autumn.util.excel.enums.CellType;
import com.autumn.util.reflect.BeanProperty;
import com.autumn.util.reflect.ReflectUtils;

import java.lang.annotation.Annotation;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * 工作薄帮助类
 */
public class WorkbookUtils {

    private final static Map<Class<?>, ColumnInfo> TYPE_FORMAT_MAP = new HashMap<>(64);

    static {
        ColumnInfo info = new ColumnInfo();
        info.setAlignment(HorizontalAlignment.RIGHT);
        info.setCellType(CellType.CELL_TYPE_BIGDECIMAL);
        info.setFormat("#,##0.00");
        TYPE_FORMAT_MAP.put(BigDecimal.class, info);

        info = new ColumnInfo();
        info.setAlignment(HorizontalAlignment.RIGHT);
        info.setCellType(CellType.CELL_TYPE_INTEGER);
        info.setFormat("");
        TYPE_FORMAT_MAP.put(byte.class, info);
        TYPE_FORMAT_MAP.put(Byte.class, info);
        TYPE_FORMAT_MAP.put(short.class, info);
        TYPE_FORMAT_MAP.put(Short.class, info);
        TYPE_FORMAT_MAP.put(int.class, info);
        TYPE_FORMAT_MAP.put(Integer.class, info);
        TYPE_FORMAT_MAP.put(long.class, info);
        TYPE_FORMAT_MAP.put(Long.class, info);

        info = new ColumnInfo();
        info.setAlignment(HorizontalAlignment.RIGHT);
        info.setCellType(CellType.CELL_TYPE_DOUBLE);
        info.setFormat("");
        TYPE_FORMAT_MAP.put(double.class, info);
        TYPE_FORMAT_MAP.put(Double.class, info);
        TYPE_FORMAT_MAP.put(float.class, info);
        TYPE_FORMAT_MAP.put(Float.class, info);

        info = new ColumnInfo();
        info.setAlignment(HorizontalAlignment.LEFT);
        info.setCellType(CellType.CELL_TYPE_DATETIME);
        info.setFormat("yyyy-MM-dd HH:mm:ss");
        TYPE_FORMAT_MAP.put(java.util.Date.class, info);
        TYPE_FORMAT_MAP.put(java.sql.Date.class, info);

        info = new ColumnInfo();
        info.setAlignment(HorizontalAlignment.LEFT);
        info.setCellType(CellType.CELL_TYPE_TIME);
        info.setFormat("HH:mm:ss");
        TYPE_FORMAT_MAP.put(TimeSpan.class, info);

        info = new ColumnInfo();
        info.setAlignment(HorizontalAlignment.CENTER);
        info.setCellType(CellType.CELL_TYPE_BOOLEAN);
        info.setFormat("");
        TYPE_FORMAT_MAP.put(boolean.class, info);
        TYPE_FORMAT_MAP.put(Boolean.class, info);
    }


    private static ExcelWorkSheet createExcelWorkbook(Class<?> type) {
        return new ExcelWorkSheet() {
            @Override
            public Class<? extends Annotation> annotationType() {
                return ExcelWorkSheet.class;
            }

            @Override
            public String sheetName() {
                return "";
            }

            @Override
            public String exportTitle() {
                return type.getSimpleName();
            }

            @Override
            public boolean isExportTitle() {
                return true;
            }

            @Override
            public String exportExplain() {
                return "";
            }

            @Override
            public double exportExplainFontSize() {
                return ExcelWorkSheet.DEFAULT_EXPORT_EXPLAIN_FONT_SIZE;
            }

            @Override
            public int exportExplainRowHeight() {
                return ExcelWorkSheet.DEFAULT_EXPORT_EXPLAIN_ROW_HEIGHT;
            }
        };
    }

    /**
     * 创建工作薄信息
     *
     * @param type
     * @return
     */
    public static WorkSheetInfo createWorkSheetInfo(Class<?> type) {
        ExceptionUtils.checkNotNull(type, type.getName());
        ExcelWorkSheet excelWorkSheet = type.getAnnotation(ExcelWorkSheet.class);
        if (excelWorkSheet == null) {
            excelWorkSheet = createExcelWorkbook(type);
        }
        WorkSheetInfo info = new WorkSheetInfo();
        info.setSheetName(excelWorkSheet.sheetName());
        WorkSheetHeader header = info.getHeader();
        header.setName(excelWorkSheet.exportTitle());
        header.setShow(excelWorkSheet.isExportTitle());
        header = info.getChildHeader();
        header.setShow(true);
        header.setName(excelWorkSheet.exportExplain());
        header.setFontSize(excelWorkSheet.exportExplainFontSize() <= 0.0 ? ExcelWorkSheet.DEFAULT_EXPORT_EXPLAIN_FONT_SIZE : excelWorkSheet.exportExplainFontSize());
        header.setRowHeight(excelWorkSheet.exportExplainRowHeight() <= 0 ? ExcelWorkSheet.DEFAULT_EXPORT_EXPLAIN_ROW_HEIGHT : excelWorkSheet.exportExplainRowHeight());

        Map<String, BeanProperty> propertyMap = ReflectUtils.getBeanPropertyMap(type);
        List<AbstractColumn> columns = info.getColumns();

        Map<String, ColumnGroup> groupMap = new HashMap<>(propertyMap.size());
        for (Entry<String, BeanProperty> p : propertyMap.entrySet()) {
            BeanProperty value = p.getValue();
            ExcelNonColumn non = value.getAnnotation(ExcelNonColumn.class);
            if (non == null) {
                ExcelColumn excelColumn = value.getAnnotation(ExcelColumn.class);
                if (excelColumn == null) {
                    continue;
                    //excelColumn = ExcelAnnotationAssist.class.getAnnotation(ExcelColumn.class);  // TODO
                }
                ColumnInfo columnInfo = createColumnInfo(value, excelColumn);
                AbstractColumn column = null;
                String groupName = excelColumn.groupName();
                if (!StringUtils.isNullOrBlank(groupName)) {
                    groupName = groupName.trim().toUpperCase();
                    ColumnGroup group = groupMap.get(groupName);
                    if (group == null) {
                        group = new ColumnGroup();
                        group.setOrder(columnInfo.getOrder());
                        group.setFriendlyName(excelColumn.groupName());
                        groupMap.put(groupName, group);
                        column = group;
                    }
                    group.getColumns().add(columnInfo);
                } else {
                    column = columnInfo;
                }
                if (column != null) {
                    columns.add(column);
                }
            }
        }
        groupMap.clear();
        // info.setColumns(Collections.unmodifiableList(columns));
        return info;
    }

    /**
     * 创建列信息
     *
     * @param propertyInfo 属性信息
     * @param excelColumn
     * @return
     */
    public static ColumnInfo createColumnInfo(BeanProperty propertyInfo, ExcelColumn excelColumn) {
        ExceptionUtils.checkNotNull(propertyInfo, propertyInfo.getName());
        ColumnInfo info = new ColumnInfo();
        info.setPropertyName(propertyInfo.getName());
        info.setOrder(excelColumn.order());
        info.setImportNotNullable(excelColumn.importNotNullable());
        info.setImportColumn(excelColumn.isImportColumn());
        info.setMergeContentRow(excelColumn.isMergeContentRow());
        info.setMergeBlankContentRow(excelColumn.isMergeBlankContentRow());
        String friendlyName = excelColumn.friendlyName().trim();
        info.setFriendlyName(
                StringUtils.isNullOrBlank(friendlyName) ? propertyInfo.getName() : friendlyName);
        int width = excelColumn.width();
        info.setWidth(width > 0 ? width : 80);
        ColumnInfo outInfo = TYPE_FORMAT_MAP.get(propertyInfo.getType());
        if (outInfo != null) {
            info.setCellType(outInfo.getCellType());
            info.setAlignment(outInfo.getAlignment());
            info.setFormat(outInfo.getFormat());
        } else {
            info.setCellType(CellType.CELL_TYPE_STRING);
            info.setAlignment(HorizontalAlignment.LEFT);
            info.setFormat("");
        }
        HorizontalAlignment alignment = excelColumn.alignment();
        if (alignment != null) {
            info.setAlignment(alignment);
        }
        if (!StringUtils.isNullOrBlank(excelColumn.format())) {
            info.setFormat(excelColumn.format());
        }
        return info;
    }
}
