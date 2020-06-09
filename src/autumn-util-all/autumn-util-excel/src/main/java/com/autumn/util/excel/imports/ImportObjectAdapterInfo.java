package com.autumn.util.excel.imports;

import com.autumn.util.excel.column.ColumnInfo;
import com.autumn.util.reflect.BeanProperty;

/**
 * 导入对象适配信息
 */
public class ImportObjectAdapterInfo extends ImportAdapterInfo {

    /**
     * 元属性
     */
    private BeanProperty beanProperty;

    protected ImportObjectAdapterInfo(int columnIndex, ColumnInfo columnInfo) {
        super(columnIndex, columnInfo);
    }

    public ImportObjectAdapterInfo(int columnIndex, ColumnInfo columnInfo, BeanProperty beanProperty) {
        super(columnIndex, columnInfo);
        this.beanProperty = beanProperty;
    }

    public BeanProperty getBeanProperty() {
        return beanProperty;
    }

    public void setBeanProperty(BeanProperty beanProperty) {
        this.beanProperty = beanProperty;
    }
}
