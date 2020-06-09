package com.autumn.mybatis.plugins;

import com.autumn.mybatis.mapper.EntityMapper;
import com.autumn.mybatis.mapper.MapperInfo;
import com.autumn.mybatis.mapper.MapperRegister;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Invocation;

/**
 * 基于 EntityMapper 映谢的  Executor 执行器拦器
 * <p>
 * 引用 {@link EntityMapper} 的实体映谢对象
 * </p>
 *
 * @author 老码农 2019-06-11 02:34:26
 */
public abstract class AbstractEntityMapperExecutorInterceptor extends AbstractInterceptor {

    @Override
    public final Object intercept(Invocation invocation) throws Throwable {
        MappedStatement ms = (MappedStatement) invocation.getArgs()[0];
        Executor executor = (Executor) invocation.getTarget();
        MapperInfo mapperInfo = MapperRegister.getMapperInfo(ms.getId());
        if (mapperInfo != null && mapperInfo.getEntityClass() != null
                && this.isInterceptorMethod(mapperInfo, mapperInfo.getMethod().getName())) {
            return this.intercept(invocation, executor, mapperInfo);
        }
        return invocation.proceed();
    }

    /**
     * 是否拦截方法
     * @param mapperInfo
     * @param mapperMethodName
     * @return
     */
    protected abstract boolean isInterceptorMethod(MapperInfo mapperInfo, String mapperMethodName);

    /**
     * 拦截
     *
     * @param invocation 拦截器
     * @param executor   执行器
     * @param mapperInfo 映射信息
     * @return
     * @throws Throwable 异常
     */
    protected abstract Object intercept(Invocation invocation, Executor executor, MapperInfo mapperInfo)
            throws Throwable;
}
