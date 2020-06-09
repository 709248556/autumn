package com.autumn.zero.authorization.application.dto.roles;

import com.autumn.domain.entities.auditing.gmt.GmtCreateAuditing;
import com.autumn.domain.entities.auditing.gmt.GmtModifiedAuditing;
import com.autumn.security.constants.RoleStatusConstants;
import com.autumn.util.excel.annotation.ExcelColumn;
import io.swagger.annotations.ApiModelProperty;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 角色输出
 *
 * @author 老码农 2018-12-10 13:18:04
 */
@ToString(callSuper = true)
public class RoleOutput extends RoleDto implements GmtCreateAuditing, GmtModifiedAuditing {

    /**
     *
     */
    private static final long serialVersionUID = -5251353722583717932L;

    /**
     * 获取状态名称
     *
     * @return
     */
    @ExcelColumn(order = 3, friendlyName = "状态", width = 80)
    public String getStatusName() {
        return RoleStatusConstants.getName(this.getStatus());
    }

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    @ExcelColumn(order = 100, friendlyName = "创建时间", width = 145)
    private Date gmtCreate;

    /**
     * 修改时间
     */
    @ApiModelProperty(value = "修改时间")
    @ExcelColumn(order = 101, friendlyName = "修改时间", width = 145)
    private Date gmtModified;

    /**
     * 用户集合
     */
    @ApiModelProperty(value = "用户集合")
    private List<RoleUserOutput> users;

    /**
     *
     */
    public RoleOutput() {
        this.setUsers(new ArrayList<>());
    }

    @Override
    public Date getGmtCreate() {
        return gmtCreate;
    }

    @Override
    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    @Override
    public Date getGmtModified() {
        return gmtModified;
    }

    @Override
    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }

    /**
     * 获取用户集合
     *
     * @return
     */
    public List<RoleUserOutput> getUsers() {
        return users;
    }

    /**
     * 设置用户集合
     *
     * @param users 用户集合
     */
    public void setUsers(List<RoleUserOutput> users) {
        this.users = users;
    }

}
