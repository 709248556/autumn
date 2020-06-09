package com.autumn.runtime.exception.filter;


import com.autumn.web.vo.ErrorInfoResult;
import org.springframework.core.annotation.Order;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * 默认异常过滤上下文
 * 
 * @author 老码农 2018-12-07 01:09:47
 */
public class DefaultExceptionFilterContext implements ExceptionFilterContext {

	private List<OrderExceptionFilter> expFilters;
	private List<ExceptionFilter> filters;

	/**
	 * 
	 */
	public DefaultExceptionFilterContext() {
		this.expFilters = new ArrayList<>();
		this.filters = new ArrayList<>();
	}

	@Override
	public void addFilter(ExceptionFilter filter) {
		synchronized (this) {
			int orderId = 0;
			Order order = filter.getClass().getAnnotation(Order.class);
			if (order != null) {
				orderId = order.value();
			}
			this.expFilters.add(new OrderExceptionFilter(orderId, filter));
		}
	}

	/**
	 * 排序过滤
	 */
	private void sortFilters() {
		synchronized (this) {
			expFilters.sort(Comparator.comparingInt(OrderExceptionFilter::getOrder));
			for (OrderExceptionFilter orderExceptionFilter : expFilters) {
				this.filters.add(orderExceptionFilter.getFilter());
			}
		}
	}

	@Override
	public List<ExceptionFilter> getFilters() {
		if (this.expFilters.size() > 0 && this.filters.size() != this.expFilters.size()) {
			synchronized (this) {
				sortFilters();
				this.expFilters.clear();
			}
		}
		return this.filters;
	}

	@Override
	public ErrorInfoResult doFilter(Throwable e) {
		ProxyExceptionFilterChain proxy = new ProxyExceptionFilterChain(this.getFilters());
		return proxy.doFilter(e);
	}

	/**
	 * 
	 * @author 老码农 2018-12-07 01:22:20
	 */
	private class OrderExceptionFilter {
		private final int order;
		private final ExceptionFilter filter;

		/**
		 * 
		 * @param order
		 * @param filter
		 */
		public OrderExceptionFilter(int order, ExceptionFilter filter) {
			super();
			this.order = order;
			this.filter = filter;
		}

		public int getOrder() {
			return order;
		}

		public ExceptionFilter getFilter() {
			return filter;
		}

	}

}
