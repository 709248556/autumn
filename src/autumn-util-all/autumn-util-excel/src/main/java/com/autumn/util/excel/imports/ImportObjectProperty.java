package com.autumn.util.excel.imports;

import com.autumn.util.excel.column.ColumnInfo;
import com.autumn.util.reflect.BeanProperty;

/**
 * 导入对象属性
 */
public class ImportObjectProperty extends ImportProperty {

    /**
     * 元属性
     */
    private BeanProperty beanProperty;

    /**
     * 实例化 ImportObjectProperty 类新实例
     *
     * @param excelColumnName Excel 列名称
     * @param columnInfo      列信息
     */
    public ImportObjectProperty(String excelColumnName, ColumnInfo columnInfo) {
        super(excelColumnName, columnInfo);
    }

    /**
     * 实例化 ImportObjectProperty 类新实例
     *
     * @param excelColumnName Excel 列名称
     * @param columnInfo      列信息
     * @param beanProperty    元属性
     */
    public ImportObjectProperty(String excelColumnName, ColumnInfo columnInfo, BeanProperty beanProperty) {
        super(excelColumnName, columnInfo);
        this.setBeanProperty(beanProperty);
    }

    public BeanProperty getBeanProperty() {
        return beanProperty;
    }

    public void setBeanProperty(BeanProperty beanProperty) {
        this.beanProperty = beanProperty;
    }

}
