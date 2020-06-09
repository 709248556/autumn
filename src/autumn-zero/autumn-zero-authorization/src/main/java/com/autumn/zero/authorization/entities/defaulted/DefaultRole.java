package com.autumn.zero.authorization.entities.defaulted;

import com.autumn.constants.SettingConstants;
import com.autumn.mybatis.mapper.annotation.TableDocument;
import com.autumn.zero.authorization.entities.common.AbstractRole;
import lombok.ToString;

import javax.persistence.Table;

/**
 * 默认角色
 * 
 * @author 老码农 2018-11-25 00:06:56
 */
@ToString(callSuper = true)
@Table(name = SettingConstants.SYS_TABLE_PREFIX + "_role")
@TableDocument(value = "系统角色", group = "系统表", groupOrder = Integer.MAX_VALUE)
public class DefaultRole extends AbstractRole {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2463898919743628491L;

}
