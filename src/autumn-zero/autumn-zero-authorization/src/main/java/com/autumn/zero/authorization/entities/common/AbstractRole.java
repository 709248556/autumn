package com.autumn.zero.authorization.entities.common;

import com.autumn.audited.annotation.LogMessage;
import com.autumn.domain.entities.auditing.gmt.AbstractDefaultGmtModifiedAuditingEntity;
import com.autumn.exception.ExceptionUtils;
import com.autumn.mybatis.mapper.annotation.ColumnDocument;
import com.autumn.mybatis.mapper.annotation.ColumnOrder;
import com.autumn.mybatis.mapper.annotation.ColumnType;
import com.autumn.mybatis.mapper.annotation.Index;
import com.autumn.security.constants.RoleStatusConstants;
import com.autumn.validation.annotation.NotNullOrBlank;
import lombok.ToString;
import org.apache.ibatis.type.JdbcType;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 角色抽象
 *
 * @author 老码农 2018-11-24 23:58:26
 */
@ToString(callSuper = true)
public abstract class AbstractRole extends AbstractDefaultGmtModifiedAuditingEntity {

    /**
     *
     */
    private static final long serialVersionUID = 3723034609942719590L;

    /**
     * 最大名称长度
     */
    public static final int MAX_NAME_LENGTH = 50;

    /**
     * 最大摘要长度
     */
    public static final int MAX_SUMMARY_LENGTH = 255;

    /**
     * 字段 sortId
     */
    public static final String FIELD_SORT_ID = "sortId";

    /**
     * 字段 name
     */
    public static final String FIELD_NAME = "name";

    /**
     * 字段 status
     */
    public static final String FIELD_STATUS = "status";

    /**
     * 字段 isDefault
     */
    public static final String FIELD_IS_DEFAULT = "isDefault";

    /**
     * 字段 isSysRole
     */
    public static final String FIELD_IS_SYS_ROLE = "isSysRole";

    /**
     * 字段 summary
     */
    public static final String FIELD_SUMMARY = "summary";

    /**
     *
     */
    @NotNull(message = "顺序不能为空")
    @Column(name = "sort_id", nullable = false)
    @Index
    @ColumnOrder(1)
    @LogMessage(name = "顺序", order = 1)
    @ColumnDocument("顺序")
    private Integer sortId;

    /**
     * 角色名称
     */
    @NotNullOrBlank(message = "角色名称不能为空")
    @Length(max = MAX_NAME_LENGTH, message = "角色名称长度不能超过" + MAX_NAME_LENGTH + "个字。")
    @Column(name = "name", nullable = false, length = MAX_NAME_LENGTH)
    @ColumnType(jdbcType = JdbcType.VARCHAR)
    @Index(unique = false)
    @ColumnOrder(2)
    @LogMessage(name = "角色名称", order = 1)
    @ColumnDocument("角色名称")
    private String name;

    /**
     * 状态 {@link com.autumn.security.constants.RoleStatusConstants}
     */
    @Column(name = "status", nullable = false)
    @ColumnType(jdbcType = JdbcType.INTEGER)
    @ColumnOrder(3)
    @ColumnDocument("状态")
    private Integer status;

    /**
     * 是否默认角色
     */
    @ColumnOrder(4)
    @Column(name = "is_default", nullable = false)
    @ColumnType(jdbcType = JdbcType.BOOLEAN)
    @LogMessage(name = "默认角色", order = 2)
    @ColumnDocument("默认角色")
    private boolean isDefault;

    /**
     * 是否系统角色
     */
    @ColumnOrder(5)
    @Column(name = "is_sys_role", nullable = false)
    @ColumnType(jdbcType = JdbcType.BOOLEAN)
    @ColumnDocument("系统角色")
    private boolean isSysRole;

    /**
     * 摘要
     */
    @Length(max = MAX_SUMMARY_LENGTH, message = "角色摘要长度不能超过" + MAX_SUMMARY_LENGTH + "个字。")
    @Column(name = "summary", nullable = false, length = MAX_SUMMARY_LENGTH)
    @ColumnType(jdbcType = JdbcType.VARCHAR)
    @ColumnOrder(100)
    @LogMessage(name = "摘要", order = 100)
    @ColumnDocument("摘要")
    private String summary;

    /**
     * 用户集合
     */
    private List<UserRole> users;

    /**
     * 实例化 AbstractRole
     */
    public AbstractRole() {
        this.setUsers(new ArrayList<>());
    }

    /**
     * 获取顺序
     *
     * @return
     */
    public Integer getSortId() {
        return sortId;
    }

    /**
     * 设置顺序
     *
     * @param sortId
     */
    public void setSortId(Integer sortId) {
        this.sortId = sortId;
    }

    /**
     * 获取角色名称
     *
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * 设置角色名称
     *
     * @param name 角色名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取状态
     *
     * @return {@link com.autumn.security.constants.RoleStatusConstants}
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置状态
     *
     * @param status 状态 {@link com.autumn.security.constants.RoleStatusConstants}
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 获取是否默认
     *
     * @return
     */
    public boolean getIsDefault() {
        return isDefault;
    }

    /**
     * 设置是否默认
     *
     * @param isDefault 是否默认
     */
    public void setIsDefault(boolean isDefault) {
        this.isDefault = isDefault;
    }

    /**
     * 获取是否是系统角色
     *
     * @return
     */
    public boolean getIsSysRole() {
        return isSysRole;
    }

    /**
     * 设置是否是系统角色
     *
     * @param isSysRole
     */
    public void setIsSysRole(boolean isSysRole) {
        this.isSysRole = isSysRole;
    }

    /**
     * 获取摘要
     *
     * @return
     */
    public String getSummary() {
        return summary;
    }

    /**
     * 设置摘要
     *
     * @param summary 摘要
     */
    public void setSummary(String summary) {
        this.summary = summary;
    }

    /**
     * 获取用户集合
     *
     * @return
     */
    public List<UserRole> getUsers() {
        return users;
    }

    /**
     * 设置用户集合
     *
     * @param users
     */
    public void setUsers(List<UserRole> users) {
        this.users = users;
    }

    @Override
    public void valid() {
        super.valid();
        if (this.getUsers() == null) {
            this.setUsers(new ArrayList<>());
        }
        if (this.getStatus() == null) {
            this.setStatus(RoleStatusConstants.STATUS_ENABLE);
        } else {
            if (!RoleStatusConstants.exist(this.getStatus())) {
                ExceptionUtils.throwValidationException("指定的状态无效或不支持。");
            }
        }
        Set<Long> userSet = new HashSet<>();
        for (UserRole role : this.getUsers()) {
            role.valid();
            if (!userSet.add(role.getUserId())) {
                ExceptionUtils.throwValidationException("同一角色用户不能重复。");
            }
        }
    }

    @Override
    public void forNullToDefault() {
        super.forNullToDefault();
        if (this.getUsers() == null) {
            this.setUsers(new ArrayList<>());
        }
        if (this.getStatus() == null) {
            this.setStatus(RoleStatusConstants.STATUS_ENABLE);
        }
    }
}
