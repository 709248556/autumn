package com.autumn.word;

import java.util.ArrayList;
import java.util.List;

/**
 * 测试模型
 */
public class TestModel {

	public Long getUserId() {
		return userId;
	}

	private Long userId;

	private String userName;

	private List<TestModelExpense> expenses;

	private List<TestModelEmployee> employees;
	
	private List<TestModelEmployee> users;

	public TestModel() {
		this.setExpenses(new ArrayList<>(0));
		this.setEmployees(new ArrayList<>());
		this.setUsers(new ArrayList<>());
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public List<TestModelExpense> getExpenses() {
		return expenses;
	}

	public void setExpenses(List<TestModelExpense> expenses) {
		this.expenses = expenses;
	}

	public List<TestModelEmployee> getEmployees() {
		return employees;
	}

	public void setEmployees(List<TestModelEmployee> employees) {
		this.employees = employees;
	}

	public List<TestModelEmployee> getUsers() {
		return users;
	}

	public void setUsers(List<TestModelEmployee> users) {
		this.users = users;
	}

}