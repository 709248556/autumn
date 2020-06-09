package com.autumn.security.constants;

/**
 * 授权常量
 * 
 * @author 老码农 2019-02-25 15:42:52
 */
public class AuthorizationConstants {

	/**
	 * 系统角色 systems
	 */
	public final static String SYSTEM_ROLE_SYSTEMS = "systems";

	/**
	 * 系统角色 administrators
	 */
	public final static String SYSTEM_ROLE_ADMINISTRATORS = "administrators";

	/**
	 * 系统角色集合
	 */
	public final static String[] SYSTEM_ROLES = { SYSTEM_ROLE_SYSTEMS, SYSTEM_ROLE_ADMINISTRATORS };

	/**
	 * 系统用户 system
	 */
	public final static String SYSTEM_USER_SYSTEM = "system";

	/**
	 * 系统用户 admin
	 */
	public final static String SYSTEM_USER_ADMIN = "admin";

	/**
	 * 系统用户 administrator
	 */
	public final static String SYSTEM_USER_ADMINISTRATOR = "administrator";

	/**
	 * 系统用户集合
	 */
	public final static String[] SYSTEM_USERS = { SYSTEM_USER_SYSTEM, SYSTEM_USER_ADMIN, SYSTEM_USER_ADMINISTRATOR };
}
