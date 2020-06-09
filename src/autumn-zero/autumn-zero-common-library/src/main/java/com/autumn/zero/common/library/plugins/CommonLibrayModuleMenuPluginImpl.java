package com.autumn.zero.common.library.plugins;

import com.autumn.exception.ExceptionUtils;
import com.autumn.mybatis.event.DataSourceListener;
import com.autumn.mybatis.event.TableAutoDefinitionListener;
import com.autumn.mybatis.factory.DynamicDataSourceRouting;
import com.autumn.mybatis.mapper.EntityMapper;
import com.autumn.mybatis.mapper.MapperUtils;
import com.autumn.mybatis.metadata.EntityTable;
import com.autumn.mybatis.provider.DbProvider;
import com.autumn.mybatis.provider.DefinitionBuilder;
import com.autumn.mybatis.wrapper.EntityUpdateWrapper;
import com.autumn.runtime.cache.DataCache;
import com.autumn.runtime.cache.DataCacheManager;
import com.autumn.zero.authorization.entities.common.AbstractUser;
import com.autumn.zero.authorization.plugins.AbstractResourcesModulePlugin;
import com.autumn.zero.authorization.plugins.data.ResourcesModuleData;
import com.autumn.zero.authorization.services.AuthorizationServiceBase;
import com.autumn.zero.authorization.services.UserRoleDefinition;
import com.autumn.zero.common.library.application.services.common.RegionAppService;
import com.autumn.zero.common.library.entities.common.Region;
import com.autumn.zero.common.library.res.CommonLibraryResUtls;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 公共库资源插件
 * <p>
 * </p>
 *
 * @description TODO
 * @author: 老码农
 * @create: 2020-04-19 02:05
 **/
public class CommonLibrayModuleMenuPluginImpl extends AbstractResourcesModulePlugin
        implements DataSourceListener {

    private final Log log = LogFactory.getLog(CommonLibrayModuleMenuPluginImpl.class);

    @Autowired
    private DataCacheManager cacheManager;

    @Autowired
    private AuthorizationServiceBase authorizationService;

    @Override
    public List<ResourcesModuleData> createModules() {
        return CommonLibraryResUtls.readResourcesModuleDatas();
    }

    @Override
    public String getName() {
        return "autumnCommonLibrayResourcesModuleMenu";
    }

    @Override
    public String getDescribe() {
        return "Autumn 公共库资源与菜单";
    }

    @Override
    public void init(DataSource dataSource, DynamicDataSourceRouting dataSourceRouting) {
        Connection conn = null;
        try {
            EntityTable table = EntityTable.getTable(Region.class);
            if (dataSourceRouting.isIncludeTable(table)) {
                DefinitionBuilder definitionBuilder = dataSourceRouting.getProvider().getDefinitionBuilder();
                conn = dataSource.getConnection();
                if (!definitionBuilder.existTable(conn, table)) {
                    definitionBuilder.createTable(conn, table);
                }
                this.initRegion(conn, dataSourceRouting.getProvider());
            }
        } catch (Exception err) {
            throw ExceptionUtils.throwConfigureException(err.getMessage(), err);
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {

                }
            }
        }
    }

    private void initRegion(Connection connection, DbProvider dbProvider) throws SQLException {
        EntityMapper regionMapper = MapperUtils.resolveEntityMapper(Region.class);
        if (regionMapper.count() == 0) {
            Statement statement = null;
            try {
                log.info("开始默认行政区初始化....");
                statement = connection.createStatement();
                String sql = CommonLibraryResUtls.readRegionInitScript(dbProvider);
                statement.execute(sql);

                UserRoleDefinition definition = this.authorizationService.createAdministratorDefinition();
                if (definition != null) {
                    AbstractUser user = this.authorizationService.checkOrCreateUserAndRole(definition);
                    if (user != null) {
                        EntityUpdateWrapper<Region> wrapper = new EntityUpdateWrapper<>(Region.class);
                        wrapper.set(Region.FIELD_GMT_CREATE, new Date())
                                .set(Region.FIELD_CREATED_USER_ID, user.getId())
                                .set(Region.FIELD_CREATED_USER_NAME, user.getUserName());
                        regionMapper.updateByWhere(wrapper);
                    }
                }

                log.info("完成默认行政区初始化，并开始清除缓存....");
                Collection<DataCache> dataCaches = cacheManager.dataCaches();
                List<RegionAppService> appServices = dataCaches.stream().filter(s -> s instanceof RegionAppService)
                        .map(s -> (RegionAppService) s).collect(Collectors.toList());
                for (RegionAppService appService : appServices) {
                    appService.clearCache();
                }
                log.info("完成行政区清除缓存....");
            } catch (Exception err) {
                log.error("初始化行政区出错:" + err.getMessage());
                throw err;
            } finally {
                if (statement != null) {
                    statement.close();
                }
            }
        }
    }

    @Override
    public void close(DataSource dataSource, DynamicDataSourceRouting dataSourceRouting) {

    }

    @Override
    public int getOrder() {
        return TableAutoDefinitionListener.BEAN_BEGIN_ORDER + 30;
    }
}
