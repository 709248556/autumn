package com.autumn.cloud.lock.impl;


import com.autumn.exception.UnableLockTimeoutException;

/**
 * 资源锁抽象
 * 
 * @author 老码农
 *
 *         2017-11-23 13:41:47
 */
public abstract class AbstractResourceLock {

	/**
	 * 抛出超时无法获取锁
	 * 
	 * @param resourceName
	 *            资源名称
	 * @param timeout
	 *            超时毫秒
	 * @return
	 */
	protected UnableLockTimeoutException throwUnableLockTimeoutException(String resourceName, int timeout) {
		throw new UnableLockTimeoutException(String.format("资源锁 %s 超过 %s 毫秒无法获取而超时 。", resourceName, timeout));
	}

}
