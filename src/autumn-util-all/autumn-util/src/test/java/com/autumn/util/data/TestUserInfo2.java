package com.autumn.util.data;

import java.util.List;

/**
 * 
 * @author 老码农
 *
 *         2017-11-15 17:46:55
 */
public class TestUserInfo2 extends TestUserInfo<Long> {

	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Long> getUsers() {
		return users;
	}

	public void setUsers(List<Long> users) {
		this.users = users;
	}

	private List<Long> users;
}
