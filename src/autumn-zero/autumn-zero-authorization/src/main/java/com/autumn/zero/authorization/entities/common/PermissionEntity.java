package com.autumn.zero.authorization.entities.common;

/**
 * 权限实体
 * 
 * @author 老码农
 * 2018-12-11 18:12:06
 */
public interface PermissionEntity {

	/**
	 * 字段 resourcesId
	 */
	public static final String FIELD_RESOURCES_ID = "resourcesId";

	/**
	 * 字段 name
	 */
	public static final String FIELD_NAME = "name";

	/**
	 * 字段 isGranted
	 */
	public static final String FIELD_IS_GRANTED = "isGranted";
	
	/**
	 * 获取资源id
	 * @return
	 */
	String getResourcesId();
	
	/**
	 * 设置资源id
	 * @param resourcesId
	 */
	void setResourcesId(String resourcesId);
	
	/**
	 * 获取权限名称
	 * @return
	 */
	String getName();
	
	/**
	 * 设置权限名称
	 * @param name
	 */
	void setName(String name);
	
	/**
	 * 获取是否授权
	 * @return
	 */
	boolean getIsGranted();
	
	/**
	 * 设置是否授权
	 * @param isGranted
	 */
	void setIsGranted(boolean isGranted);
}
