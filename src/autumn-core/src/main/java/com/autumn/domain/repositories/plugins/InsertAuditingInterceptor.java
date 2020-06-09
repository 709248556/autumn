package com.autumn.domain.repositories.plugins;

import com.autumn.mybatis.mapper.EntityMapper;
import com.autumn.mybatis.mapper.MapperInfo;
import com.autumn.util.DbAuditingUtils;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;

import java.util.List;
import java.util.Map;

/**
 * 插入审计拦截
 *
 * @Description TODO
 * @Author 老码农
 * @Date 2019-08-27 15:16
 */
@Intercepts({@Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class})})
public class InsertAuditingInterceptor extends AbstractAuditingInterceptor {

    @Override
    protected boolean isInterceptorAuditingMethod(MapperInfo mapperInfo, String mapperMethodName) {
        return mapperMethodName.equals(EntityMapper.INSERT)
                || mapperMethodName.equals(EntityMapper.INSERT_BY_LIST);
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    @Override
    protected Object intercept(Invocation invocation, Executor executor, MapperInfo mapperInfo) throws Throwable {
        if (mapperInfo.getMethod().getName().equals(EntityMapper.INSERT)) {
            Object entity = invocation.getArgs()[1];
            DbAuditingUtils.insertSetProperty(entity, this.getSession());
        } else if (mapperInfo.getMethod().getName().equals(EntityMapper.INSERT_BY_LIST)) {
            Map<String, Object> argMap = (Map<String, Object>) invocation.getArgs()[1];
            List entices = (List) argMap.get(EntityMapper.ARG_NAME_LIST);
            if (entices != null) {
                for (Object entity : entices) {
                    DbAuditingUtils.insertSetProperty(entity, this.getSession());
                }
            }
        }
        return invocation.proceed();
    }
}
