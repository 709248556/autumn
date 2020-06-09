package com.autumn.security.utils;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Jwt 声明
 * 
 * @author 老码农
 *
 *         2017-12-12 17:57:56
 */
public class JwtClaims implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3961554200204557942L;
	private String subject;
	private String audience;
	private Date expiration;
	private Date notBefore;
	private String issuer;
	private Date issuedAt;
	private String id;
	private Map<String, Object> properties;

	/**
	 * 
	 */
	public JwtClaims() {
		this.properties = new HashMap<>();
	}

	/**
	 * 获取所面向的用户
	 * 
	 * @return
	 */
	public String getSubject() {
		return subject;
	}

	/**
	 * 设置所面向的用户
	 * 
	 * @param subject
	 *            所面向的用户
	 */
	public void setSubject(String subject) {
		this.subject = subject;
	}

	/**
	 * 获取接收的一方
	 * 
	 * @return
	 */
	public String getAudience() {
		return audience;
	}

	/**
	 * 设置接收的一方
	 * 
	 * @param audience
	 *            接收的一方
	 */
	public void setAudience(String audience) {
		this.audience = audience;
	}

	/**
	 * 获取过期时间
	 * 
	 * @return
	 */
	public Date getExpiration() {
		return expiration;
	}

	/**
	 * 设置获取过期时间
	 * 
	 * @param expiration
	 *            过期时间
	 */
	public void setExpiration(Date expiration) {
		this.expiration = expiration;
	}

	/**
	 * 获取有效时间之前
	 * 
	 * @return
	 */
	public Date getNotBefore() {
		return notBefore;
	}

	/**
	 * 设置有效时间之前
	 * 
	 * @param notBefore
	 *            有效时间之前(定义在什么时间之前，该jwt都是不可用的.)
	 */
	public void setNotBefore(Date notBefore) {
		this.notBefore = notBefore;
	}

	/**
	 * 获取签发人
	 * 
	 * @return
	 */
	public String getIssuer() {
		return issuer;
	}

	/**
	 * 设置签发人
	 * 
	 * @param issuer
	 *            签发人
	 */
	public void setIssuer(String issuer) {
		this.issuer = issuer;
	}

	/**
	 * 获取签发时间
	 * 
	 * @return
	 */
	public Date getIssuedAt() {
		return issuedAt;
	}

	/**
	 * 设置签发时间
	 * 
	 * @param issuedAt
	 *            签发时间
	 */
	public void setIssuedAt(Date issuedAt) {
		this.issuedAt = issuedAt;
	}

	/**
	 * 获取id(设置唯一身份标识)
	 * 
	 * @return
	 */
	public String getId() {
		return id;
	}

	/**
	 * 设置id
	 * 
	 * @param id
	 *            id(设置唯一身份标识)
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * 获取属性
	 * 
	 * @return
	 */
	public Map<String, Object> getProperties() {
		return properties;
	}

	/**
	 * 设置属性
	 * 
	 * @param properties
	 *            属性
	 */
	public void setProperties(Map<String, Object> properties) {
		this.properties = properties;
	}

}
