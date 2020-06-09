package com.autumn.mq.event;

import java.util.Collection;

import com.autumn.mq.AutumnMqDelayQueue;

/**
 * 事件Bean工厂
 * 
 * @author 老码农
 *
 *         2017-12-16 14:30:07
 */
public interface AutumnMqEventHandlerBeanFactory {

	/**
	 * 获取延迟队列
	 * 
	 * @param queunName
	 *            队列名称
	 * @return
	 *
	 */
	AutumnMqDelayQueue getDelayQueue(String queunName);

	/**
	 * 获取延迟队列
	 * 
	 * @param eventDataType
	 *            事件数据类型
	 * @return
	 *
	 */
	AutumnMqDelayQueue getDelayQueue(Class<? extends AutumnMqEventData> eventDataType);

	/**
	 * 设置事件处理器包
	 * 
	 * @param eventHandlerPackages
	 *            事件处理器包
	 *
	 */
	void setEventHandlerPackages(String[] eventHandlerPackages);

	/**
	 * 设置事件数据包
	 * 
	 * @param eventDataPackages
	 *            事件数据包
	 *
	 */
	void setEventDataPackages(String[] eventDataPackages);

	/**
	 * 设置事件处理器信息集合
	 * 
	 * @param eventHandlerInfos
	 *            事件处理器信息集合
	 *
	 */
	void setEventHandlerInfos(Collection<AutumnMqEventHandlerConfigureInfo> eventHandlerInfos);

	/**
	 * 设置事件数据信息集合
	 * 
	 * @param eventDataInfos
	 *            事件数据信息集合
	 *
	 */
	void setEventDataInfos(Collection<AutumnMqEventDataConfigureInfo> eventDataInfos);

	/**
	 * 初始化
	 * 
	 *
	 */
	void initialize();

	/**
	 * 注册
	 * 
	 * @param eventHandlerPackages
	 * @param eventDataPackages
	 *
	 */
	void register(String[] eventHandlerPackages, String[] eventDataPackages);

	/**
	 * 注册配置
	 * 
	 * @param eventHandlerInfos
	 *            事件处理器信息集合
	 * @param eventDataInfos
	 *            事件数据信息集合
	 *
	 */
	void registerConfigure(Collection<AutumnMqEventHandlerConfigureInfo> eventHandlerInfos,
			Collection<AutumnMqEventDataConfigureInfo> eventDataInfos);
}
