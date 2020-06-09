package com.autumn.util.data;

import javax.persistence.Transient;

/**
 * 用户信息
 * 
 * @author 老码农
 *
 *         2017-09-27 17:16:09
 */
public class UserInfo {
	
	@Transient
	private int id;
	private String name;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
