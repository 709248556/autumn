package com.autumn.mybatis.plugins;

import com.autumn.mybatis.mapper.EntityMapper;
import com.autumn.mybatis.mapper.MapperInfo;
import com.autumn.mybatis.mapper.TableIdAssigner;
import com.autumn.mybatis.metadata.EntityColumn;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;

import java.util.List;
import java.util.Map;

/**
 * 内置主键分配拦截
 *
 * @Description TODO
 * @Author 老码农
 * @Date 2019-08-26 18:07
 */
@Intercepts({@Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class})})
class KeyAssignerInterceptor extends AbstractEntityMapperExecutorInterceptor {

    private TableIdAssigner tableIdAssigner = null;

    public KeyAssignerInterceptor() {

    }

    /**
     * 获取表id分配器
     *
     * @return
     */
    protected TableIdAssigner getTableIdAssigner() {
        if (this.tableIdAssigner != null) {
            return this.tableIdAssigner;
        }
        synchronized (this) {
            if (this.tableIdAssigner == null) {
                this.tableIdAssigner = this.getBean(TableIdAssigner.class);
            }
            return this.tableIdAssigner;
        }
    }

    /**
     * 设置表分配主键
     *
     * @param mapperInfo
     * @param entity
     */
    private void setTableAssignKey(MapperInfo mapperInfo, Object entity) {
        EntityColumn keyColumn = mapperInfo.getEntityTable().getKeyColumns().get(0);
        Object nextId = this.getTableIdAssigner().nextId(mapperInfo.getEntityTable(), keyColumn);
        keyColumn.setValue(entity, nextId);
    }

    @Override
    protected boolean isInterceptorMethod(MapperInfo mapperInfo, String mapperMethodName) {
        return mapperInfo.getEntityTable().isTableAssignKey() &&
                (mapperMethodName.equals(EntityMapper.INSERT) || mapperMethodName.equals(EntityMapper.INSERT_BY_LIST));
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    @Override
    protected Object intercept(Invocation invocation, Executor executor, MapperInfo mapperInfo) throws Throwable {
        if (mapperInfo.getMethod().getName().equals(EntityMapper.INSERT)) {
            Object entity = invocation.getArgs()[1];
            this.setTableAssignKey(mapperInfo, entity);
        } else if (mapperInfo.getMethod().getName().equals(EntityMapper.INSERT_BY_LIST)) {
            Map<String, Object> argMap = (Map<String, Object>) invocation.getArgs()[1];
            List entitys = (List) argMap.get(EntityMapper.ARG_NAME_LIST);
            if (entitys != null) {
                for (Object entity : entitys) {
                    this.setTableAssignKey(mapperInfo, entity);
                }
            }
        }
        return invocation.proceed();
    }
}
