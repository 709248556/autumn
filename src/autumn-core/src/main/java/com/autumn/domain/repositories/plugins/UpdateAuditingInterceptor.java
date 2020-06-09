package com.autumn.domain.repositories.plugins;

import com.autumn.domain.entities.auditing.gmt.GmtModifiedAuditing;
import com.autumn.domain.entities.auditing.ldt.LdtModifiedAuditing;
import com.autumn.domain.entities.auditing.SoftDelete;
import com.autumn.domain.entities.auditing.user.UserModifiedAuditing;
import com.autumn.mybatis.mapper.EntityMapper;
import com.autumn.mybatis.mapper.MapperInfo;
import com.autumn.mybatis.wrapper.UpdateWrapper;
import com.autumn.timing.Clock;
import com.autumn.util.DbAuditingUtils;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;

import java.util.Map;

/**
 * 更新审计拦截
 *
 * @Description TODO
 * @Author 老码农
 * @Date 2019-08-27 15:23
 */
@Intercepts({@Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class})})
public class UpdateAuditingInterceptor extends AbstractAuditingInterceptor {

    @Override
    protected boolean isInterceptorAuditingMethod(MapperInfo mapperInfo, String mapperMethodName) {
        return (GmtModifiedAuditing.class.isAssignableFrom(mapperInfo.getEntityClass())
                || LdtModifiedAuditing.class.isAssignableFrom(mapperInfo.getEntityClass())
                || UserModifiedAuditing.class.isAssignableFrom(mapperInfo.getEntityClass()))
                && (mapperMethodName.equals(EntityMapper.UPDATE)
                || mapperMethodName.equals(EntityMapper.UPDATE_AND_RESET_CHILDREN)
                || mapperMethodName.equals(EntityMapper.UPDATE_BY_NOT_NULL)
                || mapperMethodName.equals(EntityMapper.UPDATE_BY_WHERE));
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    @Override
    protected Object intercept(Invocation invocation, Executor executor, MapperInfo mapperInfo) throws Throwable {
        if (mapperInfo.getMethod().getName().equals(EntityMapper.UPDATE)
                || mapperInfo.getMethod().getName().equals(EntityMapper.UPDATE_AND_RESET_CHILDREN)
                || mapperInfo.getMethod().getName().equals(EntityMapper.UPDATE_BY_NOT_NULL)) {
            Object entity = invocation.getArgs()[1];
            DbAuditingUtils.updateSetProperty(entity, this.getSession());
        } else if (mapperInfo.getMethod().getName().equals(EntityMapper.UPDATE_BY_WHERE)) {
            Map<String, Object> argMap = (Map<String, Object>) invocation.getArgs()[1];
            UpdateWrapper wrapper = (UpdateWrapper) argMap.get(EntityMapper.ARG_NAME_WRAPPER);
            //如果是删除审计来源
            if (wrapper.getWrapperStatus() != null
                    && wrapper.getWrapperStatus().equals(DeleteAuditingInterceptor.WRAPPER_STATUS_DELETE_AUDITING)) {
                return invocation.proceed();
            }
            if (SoftDelete.class.isAssignableFrom(mapperInfo.getEntityClass())) {
                wrapper.where().eq(SoftDelete.FIELD_IS_DELETE, false);
            }
            if (GmtModifiedAuditing.class.isAssignableFrom(mapperInfo.getEntityClass())) {
                wrapper.set(GmtModifiedAuditing.FIELD_GMT_MODIFIED, Clock.gmtNow());
            }
            if (LdtModifiedAuditing.class.isAssignableFrom(mapperInfo.getEntityClass())) {
                wrapper.set(LdtModifiedAuditing.FIELD_LDT_MODIFIED, Clock.ldtNow());
            }
            if (UserModifiedAuditing.class.isAssignableFrom(mapperInfo.getEntityClass())) {
                String userName = this.getSession().getUserName();
                if (userName == null) {
                    userName = "";
                }
                wrapper.set(UserModifiedAuditing.FIELD_MODIFIED_USER_ID, this.getSession().getUserId());
                wrapper.set(UserModifiedAuditing.FIELD_MODIFIED_USER_NAME, userName);
            }
        }
        return invocation.proceed();
    }
}
