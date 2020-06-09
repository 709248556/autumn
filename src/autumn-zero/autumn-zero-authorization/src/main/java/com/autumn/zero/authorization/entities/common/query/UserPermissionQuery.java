package com.autumn.zero.authorization.entities.common.query;

import com.autumn.mybatis.mapper.annotation.ViewTable;
import com.autumn.zero.authorization.entities.common.PermissionEntity;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 用户权限查询
 *
 * @author 老码农 2018-12-07 18:02:51
 */
@ToString(callSuper = true)
@Table
@ViewTable
public class UserPermissionQuery implements Serializable, PermissionEntity {

    /**
     *
     */
    private static final long serialVersionUID = 2037260267225169811L;

    /**
     * 字段 userId
     */
    public static final String FIELD_USER_ID = "userId";

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 资源
     */
    private String resourcesId;

    /**
     * 授权名称
     */
    private String name;

    /**
     * 是否授权
     */
    @Column(name = "is_granted", nullable = false)
    private boolean isGranted;

    /**
     * 获取用户Id
     *
     * @return
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * 设置用户Id
     *
     * @param userId 用户Id
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

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
