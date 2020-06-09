package com.autumn.zero.authorization.entities.common;

import com.autumn.constants.SettingConstants;
import com.autumn.mybatis.mapper.annotation.ColumnDocument;
import com.autumn.mybatis.mapper.annotation.ColumnOrder;
import com.autumn.mybatis.mapper.annotation.Index;
import com.autumn.mybatis.mapper.annotation.TableDocument;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Table;

/**
 * 角色声明
 * 
 * @author 老码农 2018-11-25 01:00:59
 */
@ToString(callSuper = true)
@Setter
@Getter
@Table(name = SettingConstants.SYS_TABLE_PREFIX + "_role_claim")
@TableDocument(value = "角色声明", group = "系统表", groupOrder = Integer.MAX_VALUE)
public class RoleClaim extends AbstractClaim {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8952747802702831985L;

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
