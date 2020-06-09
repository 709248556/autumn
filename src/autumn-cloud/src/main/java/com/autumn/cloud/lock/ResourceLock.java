package com.autumn.cloud.lock;

import com.autumn.exception.UnableLockTimeoutException;

import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * 资源锁
 *
 * @author 老码农
 *
 *         2017-11-23 12:12:23
 */
interface ResourceLock {

	/**
	 * 加锁
	 * <p>
	 * 加锁，若无法获取锁，则一直等待，直到获取锁为止
	 * </p>
	 *
	 * @param resourceName
	 *            资源名称
	 * @throws Exception
	 *             其他异常
	 */
	void lock(String resourceName) throws Exception;

	/**
	 * 加锁
	 *
	 * @param resourceName
	 *            资源名称
	 * @param timeout
	 *            超时(毫秒)
	 * @throws UnableLockTimeoutException
	 *             无法获取锁而产生的异常
	 * @throws Exception
	 *             其他异常
	 */
	void lock(String resourceName, int timeout) throws UnableLockTimeoutException, Exception;

	/**
	 * 加锁
	 * <p>
	 * 加锁，若无法获取锁，则一直等待，直到获取锁为止
	 * </p>
	 *
	 * @param resourceName
	 *            资源名称
	 * @param lockUnitOfWorker
	 *            工作单元
	 * @throws UnableLockTimeoutException
	 *             无法获取锁而产生的异常
	 * @throws Exception
	 *             其他异常
	 */
	void lock(String resourceName, Consumer<String> lockUnitOfWorker) throws Exception;

	/**
	 * 加锁
	 *
	 * @param resourceName
	 *            资源名称
	 * @param lockUnitOfWorker
	 *            工作单元
	 * @param timeout
	 *            超时(毫秒)
	 * @throws UnableLockTimeoutException
	 *             无法获取锁而产生的异常
	 * @throws Exception
	 *             其他异常
	 */
	void lock(String resourceName, Consumer<String> lockUnitOfWorker, int timeout)
			throws UnableLockTimeoutException, Exception;

	/**
	 * 加锁，若无法获取锁，则一直等待，直到获取锁为止
	 *
	 * @param resourceName
	 *            资源名称
	 * @param lockUnitOfWorker
	 *            锁的工作单元
	 * @return 返回运行结果
	 * @throws Exception
	 *             2017-12-06 14:05:33
	 */
	<TResult> TResult lock(String resourceName, Supplier<TResult> lockUnitOfWorker) throws Exception;

	/**
	 * 加锁
	 *
	 * @param resourceName
	 *            资源名称
	 * @param lockUnitOfWorker
	 *            工作单元
	 * @param timeout
	 *            超时(毫秒)
	 * @throws UnableLockTimeoutException
	 *             无法获取锁而产生的异常
	 * @return 返回运行结果
	 * @throws Exception
	 *             其他异常
	 */
	<TResult> TResult lock(String resourceName, Supplier<TResult> lockUnitOfWorker, int timeout)
			throws UnableLockTimeoutException, Exception;

	/**
	 * 释放锁
	 *
	 * @param resourceName
	 *            资源名称
	 * @throws Exception
	 *             其他异常
	 */
	void unlock(String resourceName) throws Exception;
}
