package com.autumn.util.mail;

import com.autumn.exception.ExceptionUtils;

import javax.activation.DataSource;
import java.io.Serializable;

/**
 * 数据源部份
 *
 * @Description TODO
 * @Author 老码农
 * @Date 2019-10-09 22:22
 */
public class DataSourcePart implements Serializable {

    private static final long serialVersionUID = -7728291339627540516L;

    private final String name;
    private final DataSource dataSource;

    public DataSourcePart(String name, DataSource dataSource) {
        ExceptionUtils.checkNotNullOrBlank(name, "name");
        ExceptionUtils.checkNotNull(dataSource, "dataSource");
        this.name = name;
        this.dataSource = dataSource;
    }

    /**
     * 获取名称
     *
     * @return
     */
    public String getName() {
        return this.name;
    }

    /**
     * 获取数据源
     *
     * @return
     */
    public DataSource getDataSource() {
        return this.dataSource;
    }

}
