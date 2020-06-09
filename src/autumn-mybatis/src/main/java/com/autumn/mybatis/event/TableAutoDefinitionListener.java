package com.autumn.mybatis.event;

/**
 * 数据表自动定义
 * <p>
 * </p>
 *
 * @description TODO
 * @author: 老码农
 * @create: 2020-04-05 14:25
 **/
public interface TableAutoDefinitionListener extends DataSourceListener {

    /**
     * Bean 注册开始顺序
     */
    public static final int BEAN_BEGIN_ORDER = Integer.MIN_VALUE;
}
