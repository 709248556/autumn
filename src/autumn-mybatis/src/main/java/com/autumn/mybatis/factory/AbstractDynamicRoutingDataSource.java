package com.autumn.mybatis.factory;

import com.autumn.mybatis.event.DataSourceListenerContext;
import com.autumn.mybatis.mapper.MapperInfo;
import com.autumn.mybatis.mapper.MapperRegister;
import com.autumn.mybatis.metadata.EntityTable;
import com.autumn.mybatis.provider.DbProvider;
import com.autumn.util.reflect.ReflectUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.Ordered;
import org.springframework.jdbc.datasource.AbstractDataSource;

import javax.annotation.PreDestroy;
import javax.sql.DataSource;
import java.io.Closeable;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 动态路油数据源抽象
 *
 * @author 老码农
 * <p>
 * Description
 * </p>
 * @date 2017-12-30 11:25:20 ApplicationReadyEvent
 */
public abstract class AbstractDynamicRoutingDataSource
        extends AbstractDataSource
        implements DynamicDataSourceRouting,
        Closeable,
        DisposableBean,
        ApplicationListener<ApplicationReadyEvent>,
        Ordered {

    /**
     * 日志
     */
    private final Log logger = LogFactory.getLog(this.getClass());

    /**
     * 获取当前数据源
     *
     * @return
     */
    protected abstract DataSource getCurrentDataSource();

    private Map<DataSource, Method> dataSourceSet = new ConcurrentHashMap<>(16);
    private List<DataSource> notStartDataSources = null;
    private final DataSourceFactory dataSourceFactory;
    private final DbProvider provider;
    private final Set<String> typeAliasesPackages;
    private final Set<String> mapperInterfacePackages;
    private boolean isStartCompleted;

    /**
     * 实例化
     *
     * @param dataSourceFactory
     * @param provider
     * @param typeAliasesPackages
     * @param mapperInterfacePackages
     */
    public AbstractDynamicRoutingDataSource(DataSourceFactory dataSourceFactory,
                                            DbProvider provider,
                                            String[] typeAliasesPackages,
                                            String[] mapperInterfacePackages) {
        this.dataSourceFactory = dataSourceFactory;
        this.provider = provider;
        this.typeAliasesPackages = this.createPackages(typeAliasesPackages);
        this.mapperInterfacePackages = this.createPackages(mapperInterfacePackages);
        this.logger.info("注册 DataSource 路由:" + this);
    }

    @Override
    public int getOrder() {
        return HIGHEST_PRECEDENCE;
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        synchronized (this) {
            if (this.notStartDataSources != null) {
                for (DataSource dataSource : this.notStartDataSources) {
                    DataSourceListenerContext.initEvent(dataSource, this);
                }
                this.notStartDataSources.clear();
                this.notStartDataSources = null;
            }
            this.isStartCompleted = true;
        }
    }

    private Set<String> createPackages(String[] packages) {
        Set<String> packs;
        if (packages == null) {
            packs = new HashSet<>(0);
        } else {
            packs = new HashSet<>(packages.length);
            for (String aPackage : packages) {
                packs.add(aPackage);
            }
        }
        return Collections.unmodifiableSet(packs);
    }

    /**
     * 获取数据源工厂
     *
     * @return
     */
    public DataSourceFactory getDataSourceFactory() {
        return this.dataSourceFactory;
    }

    /**
     * 获取数据驱动提供者
     *
     * @return
     */
    @Override
    public DbProvider getProvider() {
        return this.provider;
    }

    @Override
    public Set<String> getEntityPackages() {
        return this.typeAliasesPackages;
    }

    @Override
    public Set<String> getMapperInterfacePackages() {
        return this.mapperInterfacePackages;
    }

    /**
     * 添加数据源到关闭流中，spring调用 destroy 时应用，会自动搜索 close 函数和 Closeable 接口
     *
     * @param dataSource 数据源
     */
    protected final void addDataSourceDestroy(DataSource dataSource) {
        if (dataSource == null) {
            return;
        }
        synchronized (this) {
            if (dataSource instanceof Closeable) {
                try {
                    Method method = dataSource.getClass().getMethod("close");
                    dataSourceSet.put(dataSource, method);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                Method method = ReflectUtils.findMethod(dataSource.getClass(), "close", void.class);
                if (method == null) {
                    method = ReflectUtils.findMethod(dataSource.getClass(), "destroy", void.class);
                }
                if (method != null) {
                    dataSourceSet.put(dataSource, method);
                }
            }
            if (this.isStartCompleted) {
                DataSourceListenerContext.initEvent(dataSource, this);
            } else {
                if (this.notStartDataSources == null) {
                    this.notStartDataSources = new ArrayList<>(16);
                }
                this.notStartDataSources.add(dataSource);
            }
        }
    }

    @Override
    public boolean isIncludeTable(EntityTable table) {
        MapperInfo mapperInfo = MapperRegister.getMapperInfoByEntityClass(table.getEntityClass());
        return mapperInfo != null && mapperInfo.getDbProvider().equals(this.getProvider());

        /*Set<String> packages = this.getTypeAliasesPackages();
        String entityPackage = table.getEntityClass().getPackage().getName();
        if (packages.contains(entityPackage)) {
            return true;
        }
        for (String pack : packages) {
            if (entityPackage.startsWith(pack + ".")) {
                return true;
            }
        }
        return false;*/
    }

    @Override
    public final Connection getConnection() throws SQLException {
        DataSource ds = getCurrentDataSource();
        return ds.getConnection();
    }

    @Override
    public final Connection getConnection(String username, String password) throws SQLException {
        DataSource ds = getCurrentDataSource();
        return ds.getConnection(username, password);
    }

    @Override
    @SuppressWarnings("unchecked")
    public final <T> T unwrap(Class<T> iface) throws SQLException {
        if (iface.isInstance(this)) {
            return (T) this;
        }
        DataSource ds = getCurrentDataSource();
        return ds.unwrap(iface);
    }

    @Override
    public final boolean isWrapperFor(Class<?> iface) throws SQLException {
        DataSource ds = getCurrentDataSource();
        return (iface.isInstance(this) || ds.isWrapperFor(iface));
    }

    @PreDestroy
    @Override
    public void destroy() {
        this.close();
    }

    /**
     * 关闭
     */
    @Override
    public final void close() {
        synchronized (this) {
            try {
                for (Map.Entry<DataSource, Method> entry : dataSourceSet.entrySet()) {
                    try {
                        if (entry.getKey() instanceof Closeable) {
                            Closeable closeable = (Closeable) entry.getKey();
                            closeable.close();
                        } else {
                            entry.getValue().invoke(entry.getKey());
                        }
                        DataSourceListenerContext.closeEvent(entry.getKey(), this);
                    } catch (Exception e) {
                        logger.error(e.getMessage(), e);
                    }
                }
            } finally {
                dataSourceSet.clear();
                if (this.notStartDataSources != null) {
                    this.notStartDataSources.clear();
                    this.notStartDataSources = null;
                }
            }
        }
    }

}
