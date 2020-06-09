package com.autumn.mybatis.provider.builder;

import com.alibaba.druid.pool.DruidAbstractDataSource;
import com.autumn.exception.ExceptionUtils;
import com.autumn.mybatis.metadata.EntityColumn;
import com.autumn.mybatis.metadata.EntityTable;
import com.autumn.mybatis.provider.DbDocumentInfo;
import com.autumn.mybatis.provider.DbProvider;
import com.autumn.mybatis.provider.DefinitionBuilder;
import com.autumn.util.EnvironmentConstants;
import com.autumn.util.PackageUtils;
import com.autumn.util.StringUtils;
import com.mysql.cj.jdbc.JdbcConnection;
import org.apache.commons.dbcp.BasicDataSource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.datasource.AbstractDriverBasedDataSource;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

import javax.persistence.Table;
import javax.sql.DataSource;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

/**
 * 定义生成器抽象
 *
 * @author 老码农
 * <p>
 * Description
 * </p>
 * @date 2018-01-20 11:54:29
 */
public abstract class AbstractDefinitionBuilder extends AbstractBuilder implements DefinitionBuilder {

    private final Log log = LogFactory.getLog(this.getClass());

    /**
     * @param dbProvider
     */
    public AbstractDefinitionBuilder(DbProvider dbProvider) {
        super(dbProvider);
    }

    @Override
    public final String createAnnotation(String text) {
        if (StringUtils.isNullOrBlank(text)) {
            return "";
        }
        return this.createAnnotation(StringUtils.splitNewLine(text.trim()));
    }

    /**
     * 获取列类型
     *
     * @param column 列
     * @return
     */
    public abstract String getColumnType(EntityColumn column);

    /**
     * 获取包的表集合
     *
     * @param isIncludeView
     * @param entityPackages
     * @return
     */
    protected List<EntityTable> getPackageTables(boolean isIncludeView, String... entityPackages) {
        Set<Class<?>> entityTypeSet = new HashSet<>();
        if (entityPackages != null) {
            for (String entityPackage : entityPackages) {
                if (!StringUtils.isNullOrBlank(entityPackage)) {
                    try {
                        Set<Class<?>> pckClassSet = PackageUtils.getPackageAnnotationClass(entityPackage, Table.class,
                                false, false, false);
                        entityTypeSet.addAll(pckClassSet);
                    } catch (Exception e) {
                        ExceptionUtils.throwSystemException(e.getMessage(), e);
                    }
                }
            }
        }
        List<EntityTable> tables = new ArrayList<>(entityTypeSet.size());
        for (Class<?> entityType : entityTypeSet) {
            EntityTable table = EntityTable.getTable(entityType);
            if (isIncludeView || !table.isView()) {
                tables.add(table);
            }
        }
        return tables;
    }

    /**
     * 创建表脚本集合
     *
     * @param entityPackages 实体包集合
     * @return
     */
    @Override
    public String createTableScripts(String... entityPackages) {
        List<EntityTable> tables = this.getPackageTables(false, entityPackages);
        String lineSeparator = EnvironmentConstants.LINE_SEPARATOR;
        StringBuilder sql = new StringBuilder();
        String rowSeparator = this.getScriptRowSeparator();
        for (int i = 0; i < tables.size(); i++) {
            EntityTable table = tables.get(i);
            if (i > 0) {
                sql.append(lineSeparator);
                sql.append(lineSeparator);
                if (!StringUtils.isNullOrBlank(rowSeparator)) {
                    sql.append(rowSeparator);
                    sql.append(lineSeparator);
                }
            }
            sql.append(this.createAnnotation(this.getAnnotationLineSeparator()));
            sql.append(lineSeparator);
            sql.append(this.createAnnotation(
                    "Table structure for " + table.getName() + " to " + table.getEntityClass().getName()));
            sql.append(lineSeparator);
            sql.append(this.createAnnotation(this.getAnnotationLineSeparator()));
            sql.append(lineSeparator);
            sql.append(this.createTableScript(table));
        }
        return sql.toString();
    }

    /**
     * 创建表脚本
     *
     * @param table 表
     * @return
     */
    @Override
    public final String createTableScript(EntityTable table) {
        return this.createTableScript(table, table.getName());
    }


    @Override
    public String createProjectDocument(DbDocumentInfo documentInfo, String... entityPackages) {
        List<EntityTable> tables = this.getPackageTables(false, entityPackages);
        return this.createProjectDocument(documentInfo, tables);
    }

    @Override
    public String createProjectDocument(DbDocumentInfo documentInfo, Collection<EntityTable> tables) {
        DocumentBuilder documentBuilder = new DocumentBuilder(this);
        return documentBuilder.createProjectDocument(documentInfo, tables);
    }

    @Override
    public final String createTableDocument(EntityTable table, boolean isBindEntityClass) {
        return this.createTableDocument(table, table.getName(), isBindEntityClass);
    }

    @Override
    public String createTableDocument(EntityTable table, String tableName, boolean isBindEntityClass) {
        DocumentBuilder documentBuilder = new DocumentBuilder(this);
        return documentBuilder.createTableDocument(table, tableName, isBindEntityClass);
    }

    /**
     * 注释分隔线
     *
     * @return
     */
    protected String getAnnotationLineSeparator() {
        return "--------------------------------------------------------";
    }

    /**
     * 脚本行分隔
     *
     * @return
     */
    protected String getScriptRowSeparator() {
        return "";
    }

    /**
     * 创建注释
     *
     * @param texts 文本数组
     * @return
     */
    protected abstract String createAnnotation(String[] texts);

    @Override
    public String getDataSourceUrl(DataSource dataSource) {
        if (dataSource instanceof DruidAbstractDataSource) {
            return ((DruidAbstractDataSource) dataSource).getUrl();
        }
        if (dataSource instanceof BasicDataSource) {
            return ((BasicDataSource) dataSource).getUrl();
        }
        if (dataSource instanceof AbstractDriverBasedDataSource) {
            return ((AbstractDriverBasedDataSource) dataSource).getUrl();
        }
        if (dataSource instanceof SimpleDriverDataSource) {
            return ((SimpleDriverDataSource) dataSource).getUrl();
        }
        String result = null;
        Method[] methods = dataSource.getClass().getMethods();
        for (Method method : methods) {
            if (method.getName().equals("getUrl")) {
                try {
                    result = method.invoke(dataSource).toString();
                    break;
                } catch (Exception e) {

                }
            }
        }
        if (result == null) {
            result = dataSource.toString();
        }
        return result;
    }

    /**
     * 获取数据库
     *
     * @param connection 连接
     * @return
     * @throws SQLException
     */
    protected String getDatabase(Connection connection) throws SQLException {
        if (connection instanceof JdbcConnection) {
            return ((JdbcConnection) connection).getDatabase();
        }
        return connection.getCatalog();
    }

    @Override
    public String getDatabase(DataSource dataSource) throws SQLException {
        Connection conn = null;
        try {
            conn = dataSource.getConnection();
            return this.getDatabase(conn);
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {

                }
            }
        }
    }

    @Override
    public boolean existTable(Connection connection, EntityTable table) throws SQLException {
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.createStatement();
            String sql = this.existTableSqlCommand(this.getDatabase(connection), table);
            resultSet = statement.executeQuery(sql);
            resultSet.next();
            return resultSet.getInt(1) > 0;
        } finally {
            if (resultSet != null) {
                resultSet.close();
            }
            if (statement != null) {
                statement.close();
            }
        }
    }

    @Override
    public void createTable(Connection connection, EntityTable table) throws SQLException {
        Statement statement = null;
        try {
            statement = connection.createStatement();
            String sql = this.createTableScript(table);
            log.info("创建表[" + table.getName() + "],实体[" + table.getEntityClass().getName() + "]\r\n" + sql);
            statement.execute(sql);
        } catch (Exception err) {
            log.error("创建表[" + table.getName() + "],实体[" + table.getEntityClass().getName() + "]出错:" + err.getMessage());
            throw err;
        } finally {
            if (statement != null) {
                statement.close();
            }
        }
    }
}
