package com.autumn.zero.authorization.entities.common.query;

import com.autumn.mybatis.mapper.annotation.ViewTable;
import lombok.ToString;

import javax.persistence.Table;
import java.io.Serializable;

/**
 * 用户声明查询
 * 
 * @author 老码农 2018-12-07 18:03:20
 */
@ToString(callSuper = true)
@Table
@ViewTable
public class UserClaimQuery implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8590572899771332610L;
	
	/**
	 * 字段 userId
	 */
	public static final String FIELD_USER_ID = "userId";
	
	/**
	 * 字段 claimType
	 */
	public static final String FIELD_CLAIM_TYPE = "claimType";
	
	/**
	 * 字段 claimValue
	 */
	public static final String FIELD_CLAIM_VALUE = "claimValue";

	/**
	 * 用户id
	 */
	private Long userId;

	/**
	 * 声明类型
	 */
	private String claimType;

	/**
	 * 声明值
	 */
	private String claimValue;

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
	 * 获取声明类型
	 * 
	 * @return
	 */
	public String getClaimType() {
		return claimType;
	}

	/**
	 * 设置声明类型
	 * 
	 * @param claimType 声明类型
	 */
	public void setClaimType(String claimType) {
		this.claimType = claimType;
	}

	/**
	 * 获取声明值
	 * 
	 * @return
	 */
	public String getClaimValue() {
		return claimValue;
	}

	/**
	 * 设置声明值
	 * 
	 * @param claimValue 声明值
	 */
	public void setClaimValue(String claimValue) {
		this.claimValue = claimValue;
	}

}
