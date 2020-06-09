package com.autumn.mq;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 通道
 * 
 * @author 老码农
 *         <p>
 *         Description
 *         </p>
 * @date 2017-12-11 03:23:44
 */
public interface AutumnMqChannel<TQueue extends AutumnMqQueue> {

	/**
	 * 确认消息
	 * 
	 * @throws IOException
	 */
	void basicAck() throws IOException;

	/**
	 * 拒绝
	 * 
	 * @param requeue
	 *            重新进入队列
	 * @throws IOException
	 */
	void basicNack(boolean requeue) throws IOException;

	/**
	 * 获取队列
	 * 
	 * @return
	 */
	TQueue getAutumnQueue();

	/**
	 * 获取消息体
	 * 
	 * @param bodyType
	 *            体类型
	 * @return
	 * @throws Exception
	 *             异常
	 *
	 */
	<TBody> TBody getMessageBody(Class<TBody> bodyType) throws Exception;

	/**
	 * 关闭
	 * 
	 * @throws IOException
	 * @throws TimeoutException
	 */
	void close() throws IOException, TimeoutException;

	/**
	 * 关闭
	 * 
	 * @param closeCode
	 * @param closeMessage
	 * @throws IOException
	 * @throws TimeoutException
	 */
	void close(int closeCode, String closeMessage) throws IOException, TimeoutException;

	/**
	 * 终止
	 * 
	 * @throws IOException
	 */
	void abort() throws IOException;

	/**
	 * 终止
	 * 
	 * @param closeCode
	 * @param closeMessage
	 * @throws IOException
	 */
	void abort(int closeCode, String closeMessage) throws IOException;
}
