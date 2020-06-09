package com.autumn.zero.authorization.entities.common;

import com.autumn.audited.annotation.LogMessage;
import com.autumn.domain.entities.AbstractDefaultEntity;
import com.autumn.mybatis.mapper.annotation.ColumnDocument;
import com.autumn.mybatis.mapper.annotation.ColumnOrder;
import com.autumn.mybatis.mapper.annotation.ColumnType;
import com.autumn.mybatis.mapper.annotation.Index;
import com.autumn.validation.annotation.NotNullOrBlank;
import com.autumn.zero.authorization.entities.common.modules.ResourcesModule;
import lombok.ToString;
import org.apache.ibatis.type.JdbcType;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;

/**
 * 权限配置抽象
 *
 * @author 老码农 2018-11-25 01:15:30
 */
@ToString(callSuper = true)
public abstract class AbstractPermission extends AbstractDefaultEntity implements PermissionEntity {

    /**
     *
     */
    private static final long serialVersionUID = -3523626192276391093L;

    /**
     * 最大权限名称长度
     */
    public static final int MAX_NAME_LENGTH = 50;

    @NotNullOrBlank(message = "资源id不能为空")
    @Length(max = ResourcesModule.MAX_ID_LENGTH, message = "资源id不能超过" + ResourcesModule.MAX_ID_LENGTH + "个字。")
    @Column(name = "resources_id", nullable = false, length = ResourcesModule.MAX_ID_LENGTH)
    @ColumnType(jdbcType = JdbcType.VARCHAR)
    @Index
    @ColumnOrder(10)
    @ColumnDocument("资源id")
    private String resourcesId;

    /**
     * 授权名称
     */
    @Length(max = MAX_NAME_LENGTH, message = "权限名称长度不能超过" + MAX_NAME_LENGTH + "个字。")
    @Column(name = "name", nullable = false, length = MAX_NAME_LENGTH)
    @ColumnType(jdbcType = JdbcType.VARCHAR)
    @Index
    @ColumnOrder(12)
    @LogMessage(name = "权限名称", order = 1)
    @ColumnDocument("权限名称")
    private String name;

    /**
     * 是否授权
     */
    @ColumnOrder(13)
    @Column(name = "is_granted", nullable = false)
    @ColumnType(jdbcType = JdbcType.BOOLEAN)
    @LogMessage(name = "是否授权", order = 2)
    @ColumnDocument("是否授权")
    private boolean isGranted;

    /**
     * 获取资源id
     *
     * @return
     */
    @Override
    public String getResourcesId() {
        return resourcesId;
    }

    /**
     * 设置资源id
     *
     * @param resourcesId 资源id
     */
    @Override
    public void setResourcesId(String resourcesId) {
        this.resourcesId = resourcesId;
    }

    /**
     * 获取授权名称
     *
     * @return
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * 设置授权名称
     *
     * @param name 授权名称
     */
    @Override
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取是否授权
     *
     * @return
     */
    @Override
    public boolean getIsGranted() {
        return isGranted;
    }

    /**
     * 设置是否授权
     *
     * @param isGranted 是否授权
     */
    @Override
    public void setIsGranted(boolean isGranted) {
        this.isGranted = isGranted;
    }
}
