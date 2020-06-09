package com.autumn.web.vo;

import com.autumn.annotation.FriendlyProperty;

import java.io.Serializable;

/**
 * 授权结果
 * 
 * @author 老码农
 *
 *         2017-11-04 11:04:05
 */
public class AuthResult implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5112796242528829487L;

	@FriendlyProperty(value = "Token 的 Header参数名称")
	private final String header;
	@FriendlyProperty(value = "Token 值开头")
	private final String head;
	@FriendlyProperty(value = "票据")
	private final String token;

	/**
	 * 请求授权头
	 */
	public final static String HEADER_AUTHORIZATION = "Authorization";

	/**
	 * 
	 * @param head
	 * @param token
	 */
	private AuthResult(String head, String token) {
		this(HEADER_AUTHORIZATION, head, token);
	}

	/**
	 * 
	 * @param header
	 * @param head
	 * @param token
	 */
	private AuthResult(String header, String head, String token) {
		this.header = header;
		this.head = head;
		this.token = token;
	}

	/**
	 * 授权结果
	 * 
	 * @param head
	 *            token 值开头
	 * @param token
	 *            票据
	 * @return
	 */
	public static AuthResult auth(String head, String token) {
		return new AuthResult(head, token);
	}

	/**
	 * 授权结果
	 * 
	 * @param header
	 *            token header 参数名称
	 * @param head
	 *            token 值开头
	 * @param token
	 *            票据
	 * @return
	 */
	public static AuthResult auth(String header, String head, String token) {
		return new AuthResult(header, head, token);
	}

	/**
	 * 获取 Token 的 Header参数名称
	 * 
	 * @return
	 */
	public String getHeader() {
		return header;
	}

	/**
	 * 获取 Token 值开头
	 * 
	 * @return
	 */
	public String getHead() {
		return head;
	}

	/**
	 * 获取票据
	 * 
	 * @return
	 */
	public String getToken() {
		return token;
	}
}
