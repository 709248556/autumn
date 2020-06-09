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
 * 用户声明
 * 
 * @author 老码农 2018-11-25 01:07:21
 */
@ToString(callSuper = true)
@Getter
@Setter
@Table(name = SettingConstants.SYS_TABLE_PREFIX + "_user_claim")
@TableDocument(value = "用户声明", group = "系统表", groupOrder = Integer.MAX_VALUE)
public class UserClaim extends AbstractClaim {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1726633059000151088L;

	/**
	 * 字段 userId
	 */
	public static final String FIELD_USER_ID = "userId";
	
	/**
	 * 用户id
	 */
	@Column(name = "user_id", nullable = false)
	@Index
	@ColumnOrder(1)
	@ColumnDocument("用户id")
	private Long userId;
}
