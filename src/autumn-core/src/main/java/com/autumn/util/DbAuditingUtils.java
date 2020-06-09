package com.autumn.util;

import com.autumn.domain.entities.auditing.*;
import com.autumn.domain.entities.auditing.gmt.GmtCreateAuditing;
import com.autumn.domain.entities.auditing.gmt.GmtDeleteAuditing;
import com.autumn.domain.entities.auditing.gmt.GmtModifiedAuditing;
import com.autumn.domain.entities.auditing.ldt.LdtCreateAuditing;
import com.autumn.domain.entities.auditing.ldt.LdtDeleteAuditing;
import com.autumn.domain.entities.auditing.ldt.LdtModifiedAuditing;
import com.autumn.domain.entities.auditing.user.UserCreateAuditing;
import com.autumn.domain.entities.auditing.user.UserDeleteAuditing;
import com.autumn.domain.entities.auditing.user.UserModifiedAuditing;
import com.autumn.runtime.session.AutumnSession;
import com.autumn.spring.boot.context.AutumnApplicationContext;
import com.autumn.timing.Clock;

import java.util.List;

/**
 * 数据审计帮助
 *
 * @author 老码农
 * <p>
 * Description
 * </p>
 * @date 2017-12-31 14:02:08
 */
public class DbAuditingUtils {

    /**
     * 获取当前会话
     *
     * @return
     */
    private static AutumnSession getCurrentSession() {
        if (AutumnApplicationContext.getContext() == null) {
            return null;
        }
        return AutumnApplicationContext.getContext().getBean(AutumnSession.class);
    }

    /**
     * 插入设置属性
     *
     * @param entity 实体
     */
    public static void insertSetProperty(Object entity) {
        if (entity == null) {
            return;
        }
        AutumnSession session = getCurrentSession();
        insertSetProperty(entity, session);
    }

    /**
     * 插入设置属性
     *
     * @param entity  实体
     * @param session 会话
     */
    public static void insertSetProperty(Object entity, AutumnSession session) {
        if (entity == null || entity instanceof AuditingTransient) {
            return;
        }
        if (entity instanceof GmtCreateAuditing) {
            ((GmtCreateAuditing) entity).setGmtCreate(Clock.gmtNow());
        }
        if (entity instanceof LdtCreateAuditing) {
            ((LdtCreateAuditing) entity).setLdtCreate(Clock.ldtNow());
        }
        if (entity instanceof GmtModifiedAuditing) {
            ((GmtModifiedAuditing) entity).setGmtModified(null);
        }
        if (entity instanceof LdtModifiedAuditing) {
            ((LdtModifiedAuditing) entity).setLdtModified(null);
        }
        if (entity instanceof GmtDeleteAuditing) {
            GmtDeleteAuditing gmt = (GmtDeleteAuditing) entity;
            gmt.setGmtDelete(null);
        }
        if (entity instanceof LdtDeleteAuditing) {
            LdtDeleteAuditing gmt = (LdtDeleteAuditing) entity;
            gmt.setLdtDelete(null);
        }
        if (entity instanceof SoftDelete) {
            ((SoftDelete) entity).setDelete(false);
        }
        if (session != null) {
            if (entity instanceof UserCreateAuditing) {
                UserCreateAuditing auditingEntity = (UserCreateAuditing) entity;
                auditingEntity.setCreatedUserId(session.getUserId());
                auditingEntity.setCreatedUserName(session.getUserName());
                if (auditingEntity.getCreatedUserName() == null) {
                    auditingEntity.setCreatedUserName("");
                }
            }
            if (entity instanceof UserModifiedAuditing) {
                UserModifiedAuditing auditingEntity = (UserModifiedAuditing) entity;
                auditingEntity.setModifiedUserId(null);
                auditingEntity.setModifiedUserName("");
            }
            if (entity instanceof UserDeleteAuditing) {
                UserDeleteAuditing auditingEntity = (UserDeleteAuditing) entity;
                auditingEntity.setDeletedUserId(null);
                auditingEntity.setDeletedUserName("");
            }
        }

    }

    /**
     * 插入设置属性列表
     *
     * @param entices 实体集合
     */
    public static <T> void insertSetPropertyList(List<T> entices) {
        AutumnSession session = getCurrentSession();
        insertSetPropertyList(entices, session);
    }

    /**
     * 插入设置属性列表
     *
     * @param entices 实体集合
     */
    public static <T> void insertSetPropertyList(List<T> entices, AutumnSession session) {
        if (entices == null || entices.size() == 0) {
            return;
        }
        for (T entity : entices) {
            if (entity instanceof AuditingTransient) {
                break;
            }
            insertSetProperty(entity, session);
        }
    }

    /**
     * 更新设置属性
     *
     * @param entity 实体
     */
    public static void updateSetProperty(Object entity) {
        if (entity == null) {
            return;
        }
        AutumnSession session = getCurrentSession();
        updateSetProperty(entity, session);
    }

    /**
     * 更新设置属性
     *
     * @param entity  实体
     * @param session 会话
     */
    public static void updateSetProperty(Object entity, AutumnSession session) {
        if (entity == null || entity instanceof AuditingTransient) {
            return;
        }
        if (entity instanceof GmtDeleteAuditing) {
            GmtDeleteAuditing gmt = (GmtDeleteAuditing) entity;
            if (!gmt.isDelete()) {
                gmt.setGmtDelete(null);
                gmt.setDelete(false);
            }
        }
        if (entity instanceof GmtModifiedAuditing) {
            GmtModifiedAuditing gmtModified = (GmtModifiedAuditing) entity;
            if (entity instanceof SoftDelete) {
                SoftDelete soft = (SoftDelete) entity;
                if (!soft.isDelete()) {
                    gmtModified.setGmtModified(Clock.gmtNow());
                }
            } else {
                gmtModified.setGmtModified(Clock.gmtNow());
            }
        }
        if (entity instanceof LdtModifiedAuditing) {
            LdtModifiedAuditing gmtModified = (LdtModifiedAuditing) entity;
            if (entity instanceof SoftDelete) {
                SoftDelete soft = (SoftDelete) entity;
                if (!soft.isDelete()) {
                    gmtModified.setLdtModified(Clock.ldtNow());
                }
            } else {
                gmtModified.setLdtModified(Clock.ldtNow());
            }
        }
        if (session != null) {
            if (entity instanceof UserModifiedAuditing) {
                UserModifiedAuditing auditingEntity = (UserModifiedAuditing) entity;
                auditingEntity.setModifiedUserId(session.getUserId());
                auditingEntity.setModifiedUserName(session.getUserName());
                if (auditingEntity.getModifiedUserName() == null) {
                    auditingEntity.setModifiedUserName("");
                }
                if (entity instanceof UserDeleteAuditing) {
                    UserDeleteAuditing delEntity = (UserDeleteAuditing) entity;
                    delEntity.setDeletedUserId(null);
                    delEntity.setDeletedUserName("");
                }
            }
        }
    }

    /**
     * 更新设置属性列表
     *
     * @param entices 实体集合
     */
    public static <T> void updateSetPropertyList(List<T> entices) {
        if (entices == null || entices.size() == 0) {
            return;
        }
        AutumnSession session = getCurrentSession();
        for (T entity : entices) {
            if (entity instanceof AuditingTransient) {
                break;
            }
            updateSetProperty(entity, session);
        }
    }

    /**
     * 更新设置属性列表
     *
     * @param entices 实体集合
     * @param session 会话
     */
    public static <T> void updateSetPropertyList(List<T> entices, AutumnSession session) {
        if (entices == null || entices.size() == 0) {
            return;
        }
        for (T entity : entices) {
            if (entity instanceof AuditingTransient) {
                break;
            }
            updateSetProperty(entity, session);
        }
    }

    /**
     * 删除设置属性
     *
     * @param entity 实体
     */
    public static void deleteSetProperty(Object entity) {
        if (entity == null) {
            return;
        }
        AutumnSession session = getCurrentSession();
        deleteSetProperty(entity, session);
    }

    /**
     * 删除设置属性
     *
     * @param entity  实体
     * @param session 会话
     */
    public static void deleteSetProperty(Object entity, AutumnSession session) {
        if (entity == null || entity instanceof AuditingTransient) {
            return;
        }
        if (session != null) {
            if (entity instanceof UserDeleteAuditing) {
                UserDeleteAuditing delEntity = (UserDeleteAuditing) entity;
                if (!delEntity.isDelete()) {
                    delEntity.setDeletedUserId(session.getUserId());
                    delEntity.setDeletedUserName(session.getUserName());
                    if (delEntity.getDeletedUserName() == null) {
                        delEntity.setDeletedUserName("");
                    }
                }
            }
        }
        if (entity instanceof GmtDeleteAuditing) {
            GmtDeleteAuditing deleteAuditing = (GmtDeleteAuditing) entity;
            if (!deleteAuditing.isDelete()) {
                deleteAuditing.setGmtDelete(Clock.gmtNow());
                deleteAuditing.setDelete(true);
            }
        }
        if (entity instanceof LdtDeleteAuditing) {
            LdtDeleteAuditing deleteAuditing = (LdtDeleteAuditing) entity;
            if (!deleteAuditing.isDelete()) {
                deleteAuditing.setLdtDelete(Clock.ldtNow());
                deleteAuditing.setDelete(true);
            }
        }
        if (entity instanceof SoftDelete) {
            SoftDelete soft = (SoftDelete) entity;
            if (!soft.isDelete()) {
                soft.setDelete(true);
            }
        }
    }

    /**
     * 删除设置属性列表
     *
     * @param entices 实体集合
     */
    public static <T> void deleteSetPropertyList(List<T> entices) {
        if (entices == null || entices.size() == 0) {
            return;
        }
        AutumnSession session = getCurrentSession();
        for (T entity : entices) {
            if (entity instanceof AuditingTransient) {
                break;
            }
            deleteSetProperty(entity, session);
        }
    }

    /**
     * 删除设置属性列表
     *
     * @param entices 实体集合
     * @param session 会话
     */
    public static <T> void deleteSetPropertyList(List<T> entices, AutumnSession session) {
        if (entices == null || entices.size() == 0) {
            return;
        }
        for (T entity : entices) {
            if (entity instanceof AuditingTransient) {
                break;
            }
            deleteSetProperty(entity, session);
        }
    }
}
