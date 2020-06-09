package com.autumn.zero.authorization.entities.common;

import com.autumn.constants.SettingConstants;
import com.autumn.mybatis.mapper.annotation.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Table;

/**
 * 角色授权
 * 
 * @author 老码农 2018-11-25 01:24:43
 */
@ToString(callSuper = true)
@Setter
@Getter
@Table(name = SettingConstants.SYS_TABLE_PREFIX + "_role_permission")
@ComplexIndexs(indexs = { @ComplexIndex(propertys = { AbstractPermission.FIELD_RESOURCES_ID,
		RolePermission.FIELD_ROLE_ID, AbstractPermission.FIELD_NAME }, unique = true) })
@TableDocument(value = "角色权限", group = "系统表", groupOrder = Integer.MAX_VALUE)
public class RolePermission extends AbstractPermission {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1733646331395396175L;

	/**
	 * 字段 roleId
	 */
	public static final String FIELD_ROLE_ID = "roleId";

	/**
	 * 角色Id
	 */
	@Column(name = "role_id", nullable = false)
	@Index
	@ColumnOrder(1)
	@ColumnDocument("角色Id")
	private Long roleId;
}
