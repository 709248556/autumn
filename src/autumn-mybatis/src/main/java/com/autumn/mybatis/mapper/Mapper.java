package com.autumn.mybatis.mapper;

import com.autumn.exception.ExceptionUtils;
import com.autumn.mybatis.provider.DbProvider;

/**
 * Mapper 抽象
 *
 * @author 老码农 2018-04-01 13:45:59
 */
public interface Mapper {

    /**
     * 获取驱动提供程序
     *
     * @return
     */
    default DbProvider getDbProvider() {
        Class<?>[] intfs = this.getClass().getInterfaces();
        if (intfs.length == 0) {
            throw ExceptionUtils.throwSystemException("无法找到 " + Mapper.class.getName() + " 的继承接口。");
        }
        return MapperUtils.getDbProvider((Class<? extends Mapper>) intfs[0]);
    }
}
