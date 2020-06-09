package com.autumn.domain.entities.auditing.user;

import com.autumn.domain.entities.auditing.Auditing;

/**
 * 具用户审计
 * 
 * @author 老码农 2019-05-12 22:05:48
 */
public interface UserAuditing extends Auditing {

	/**
	 * 最大 createdUserName、deleteUserName、modifiedUserName 长度(审计用户名称)
	 * <p>
	 * {@link UserCreateAuditing}
	 * </p>
	 * <p>
	 * {@link UserModifiedAuditing}
	 * </p>
	 * <p>
	 * {@link UserModifiedAuditing}
	 * </p>
	 */
	public static final int MAX_AUDITING_USER_NAME = 50;

}
