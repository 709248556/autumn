package com.autumn.runtime.session.claims;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

/**
 * 身份声明
 * 
 * @author 老码农
 *         <p>
 *         Description
 *         </p>
 * @date 2017-11-04 04:01:21
 */
public interface IdentityClaims extends Map<String, Object>, Serializable {	

	/**
	 * 声明数据源标识(基于会话动态数据源标识时使用)
	 */
	public static final String CLAIMS_DATA_SOURCE_IDENTITY = "data_source_identity";

	/**
	 * 客户端类型
	 */
	public static final String CLAIMS_CLIENT_TYPE = "client_type";

	/**
	 * 获取声明
	 * 
	 * @param claimsName
	 *            声明名称
	 * @return
	 */
	Object getClaims(String claimsName);

	/**
	 * 获取 Long 声明
	 * 
	 * @param claimsName
	 *            声明名称
	 * @return
	 */
	Long getLongClaims(String claimsName);

	/**
	 * 获取 Integer 声明
	 * 
	 * @param claimsName
	 *            声明名称
	 * @return
	 */
	Integer getIntegerClaims(String claimsName);

	/**
	 * 获取 Short 声明
	 * 
	 * @param claimsName
	 *            声明名称
	 * @return
	 */
	Short getShortClaims(String claimsName);

	/**
	 * 获取 Double 声明
	 * 
	 * @param claimsName
	 *            声明名称
	 * @return
	 */
	Double getDoubleClaims(String claimsName);

	/**
	 * 获取 Double 声明
	 * 
	 * @param claimsName
	 *            声明名称
	 * @return
	 */
	Float getFloatClaims(String claimsName);

	/**
	 * 获取 Boolean 声明
	 * 
	 * @param claimsName
	 *            声明名称
	 * @return
	 */
	Boolean getBooleanClaims(String claimsName);

	/**
	 * 获取 Date 声明
	 * 
	 * @param claimsName
	 *            声明名称
	 * @return
	 */
	Date getDateClaims(String claimsName);

	/**
	 * 获取 String 声明
	 * 
	 * @param claimsName
	 *            声明名称
	 * @return
	 */
	String getStringClaims(String claimsName);	

}
