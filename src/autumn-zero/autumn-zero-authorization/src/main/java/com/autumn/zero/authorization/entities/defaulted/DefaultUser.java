package com.autumn.zero.authorization.entities.defaulted;

import com.autumn.constants.SettingConstants;
import com.autumn.mybatis.mapper.annotation.TableDocument;
import com.autumn.zero.authorization.entities.common.AbstractUser;
import lombok.ToString;

import javax.persistence.Table;

/**
 * 默认用户
 * 
 * @author 老码农 2018-11-24 23:26:42
 */
@ToString(callSuper = true)
@Table(name =SettingConstants.SYS_TABLE_PREFIX + "_user")
@TableDocument(value = "系统用户", group = "系统表", groupOrder = Integer.MAX_VALUE)
public class DefaultUser extends AbstractUser {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6472326718168693394L;

}
