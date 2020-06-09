package com.autumn.mq.event;

import com.autumn.util.PackageUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.HashSet;
import java.util.Set;

/**
 * 事件
 * 
 * @author 老码农
 *
 *         2017-12-16 14:35:56
 */
public abstract class AbstractAutumnMqEventHandlerBeanFactory implements AutumnMqEventHandlerBeanFactory {

	/**
	 * 获取日志
	 */
	protected final Log logger = LogFactory.getLog(this.getClass());

	/**
	 * 获取事件处理器包
	 * 
	 * @param basePackages
	 *            包集合
	 * @return
	 */
	protected Set<Class<?>> getEventHandlers(String[] basePackages) {
		Set<Class<?>> handlerSet = new HashSet<>();
		if (basePackages != null) {
			try {
				for (String basePackage : basePackages) {
					Set<Class<?>> types = PackageUtils.getPackageTargetClass(basePackage, AutumnMqEventHandler.class, false,
							false, false);
					for (Class<?> type : types) {
						if (type.getAnnotation(AutumnMqEventHandlerConfigure.class) != null) {
							handlerSet.add(type);
						}
					}
				}
			} catch (Exception e) {
				logger.error("扫描事件处理器(EventHandler)出错:" + e.getMessage(), e);
			}
		}
		return handlerSet;
	}

	/**
	 * 获取事件数据类型集
	 * 
	 * @param basePackages
	 *            包集合
	 * @return
	 */
	protected Set<Class<?>> getEventDatas(String[] basePackages) {
		Set<Class<?>> handlerSet = new HashSet<>();
		if (basePackages != null) {
			try {
				for (String basePackage : basePackages) {
					Set<Class<?>> types = PackageUtils.getPackageAnnotationClass(basePackage, AutumnMqEventDataConfigure.class,
							false, false, false);
					handlerSet.addAll(types);
				}
			} catch (Exception e) {
				logger.error("扫描事件处理器(EventData)出错:" + e.getMessage(), e);
			}
		}
		return handlerSet;
	}
}
