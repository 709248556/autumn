package com.autumn.mybatis.wrapper;

import java.io.Serializable;

/**
 * 锁模式
 * 
 * @author 老码农
 *
 *         2017-11-13 11:46:05
 */
public enum LockModeEnum implements Serializable {

	/**
	 * 无锁，无任可锁,任何时候无均查询和更新，若更新冲突时会采进行更新锁,若有更新时，可能会产生不安全的更新。
	 */
	NONE(0),
	/**
	 * 共享锁，所有加锁的查询均可获取结果，但更新时，只要任何一个之前的共享锁未释放则需要等于，若出现两新同时的更新锁可能会导致死锁或超时退出。
	 * <p>
	 * MySQL: LOCK IN SHARE MODE
	 * </p>
	 */
	SHARE(1),
	/**
	 * 更新锁(排他锁),其他事务可以无更新锁查询，若其他事务添加排他锁，无论是查询还是更新，需等待。
	 * <p>
	 * MySQL: FOR UPDATE
	 * </p>
	 */
	UPDATE(2);

	private final int value;

	/**
	 * 实例化 LockModeEnum 类新实例
	 * 
	 * @param value
	 *            值
	 */
	private LockModeEnum(int value) {
		this.value = value;
	}

	/**
	 * 获取值
	 * 
	 * @return
	 */
	public int getValue() {
		return value;
	}
}
