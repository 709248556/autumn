package com.autumn.mybatis.mapper.annotation;

import com.autumn.mybatis.provider.ProviderDriveType;

/**
 * 根据提供者的 Mapper 视图查询
 * <p>
 * </p>
 *
 * @description TODO
 * @author: 老码农
 * @create: 2020-05-07 17:14
 **/
public @interface MapperProviderViewSelect {

    /**
     * 提供者类型
     *
     * @return
     */
    ProviderDriveType driveType();

    /**
     * 查询 Sql代码
     *
     * @return
     */
    String value();
}
