package com.autumn.domain.repositories.plugins;

import com.autumn.domain.entities.Entity;
import com.autumn.domain.entities.auditing.SoftDelete;
import com.autumn.domain.entities.auditing.gmt.GmtDeleteAuditing;
import com.autumn.domain.entities.auditing.ldt.LdtDeleteAuditing;
import com.autumn.domain.entities.auditing.user.UserDeleteAuditing;
import com.autumn.mybatis.mapper.EntityMapper;
import com.autumn.mybatis.mapper.MapperInfo;
import com.autumn.mybatis.mapper.MapperUtils;
import com.autumn.mybatis.wrapper.UpdateWrapper;
import com.autumn.mybatis.wrapper.Wrapper;
import com.autumn.timing.Clock;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;

import java.io.Serializable;
import java.util.Map;
import java.util.function.Consumer;

/**
 * 删除审计拦截
 *
 * @Description TODO
 * @Author 老码农
 * @Date 2019-08-27 15:24
 */
@Intercepts({@Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class})})
public class DeleteAuditingInterceptor extends AbstractAuditingInterceptor {

    /**
     * 包装器删除状态
     */
    public static final int WRAPPER_STATUS_DELETE_AUDITING = -254562782;

    @Override
    protected boolean isInterceptorAuditingMethod(MapperInfo mapperInfo, String mapperMethodName) {
        return SoftDelete.class.isAssignableFrom(mapperInfo.getEntityClass())
                && (mapperMethodName.equals(EntityMapper.DELETE_BY_ALL)
                || mapperMethodName.equals(EntityMapper.DELETE_BY_ID)
                || mapperMethodName.equals(EntityMapper.DELETE_BY_WHERE));
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    @Override
    protected Object intercept(Invocation invocation, Executor executor, MapperInfo mapperInfo) throws Throwable {
        switch (mapperInfo.getMethod().getName()) {
            case EntityMapper.DELETE_BY_ALL: {
                return this.execute(mapperInfo, null);
            }
            case EntityMapper.DELETE_BY_WHERE: {
                Map<String, Object> argMap = (Map<String, Object>) invocation.getArgs()[1];
                Wrapper wrapper = (Wrapper) argMap.get(EntityMapper.ARG_NAME_WRAPPER);
                return this.execute(mapperInfo, w -> w.where().copyCriteria(wrapper));
            }
            case EntityMapper.DELETE_BY_ID: {
                Serializable keyValue = (Serializable) invocation.getArgs()[1];
                return this.execute(mapperInfo, w -> w.where().eq(Entity.FIELD_ID, keyValue));
            }
            default: {
                return invocation.proceed();
            }
        }
    }

    /**
     * 执行
     * <p>
     * 由删除转更新操作
     * </p>
     *
     * @param mapperInfo
     * @param consumer
     * @return
     */
    private int execute(MapperInfo mapperInfo, Consumer<UpdateWrapper> consumer) {
        UpdateWrapper updateWrapper = new UpdateWrapper(mapperInfo.getEntityClass());
        if (consumer != null) {
            consumer.accept(updateWrapper);
        }
        updateWrapper.where().eq(SoftDelete.FIELD_IS_DELETE, false);
        updateWrapper.set(SoftDelete.FIELD_IS_DELETE, true);
        if (GmtDeleteAuditing.class.isAssignableFrom(mapperInfo.getEntityClass())) {
            updateWrapper.set(GmtDeleteAuditing.FIELD_GMT_DELETE, Clock.gmtNow());
        }
        if (LdtDeleteAuditing.class.isAssignableFrom(mapperInfo.getEntityClass())) {
            updateWrapper.set(LdtDeleteAuditing.FIELD_LDT_DELETE, Clock.ldtNow());
        }
        if (UserDeleteAuditing.class.isAssignableFrom(mapperInfo.getEntityClass())) {
            String userName = this.getSession().getUserName();
            if (userName == null) {
                userName = "";
            }
            updateWrapper.set(UserDeleteAuditing.FIELD_DELETED_USER_ID, this.getSession().getUserId());
            updateWrapper.set(UserDeleteAuditing.FIELD_DELETED_USER_NAME, userName);
        }
        //设置确保 UpdateAuditingInterceptor 不再拦截
        updateWrapper.setWrapperStatus(WRAPPER_STATUS_DELETE_AUDITING);
        EntityMapper entityMapper = MapperUtils.resolveEntityMapper(mapperInfo.getEntityClass());
        return entityMapper.updateByWhere(updateWrapper);
    }

}
