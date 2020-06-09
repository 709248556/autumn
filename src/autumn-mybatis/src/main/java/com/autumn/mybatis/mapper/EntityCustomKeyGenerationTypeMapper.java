package com.autumn.mybatis.mapper;

import javax.persistence.GenerationType;
import java.util.Map;

/**
 * 实体表自定义主键生成映射
 * <p>
 * 该接口将会产生的主键生成方式将会覆盖配置
 * </p>
 *
 * @Description TODO
 * @Author 老码农
 * @Date 2019-09-02 19:14
 */
public interface EntityCustomKeyGenerationTypeMapper {

    /**
     * 绑定主键生成方式映射
     *
     * @return key is Entity Class
     */
    Map<Class<?>, GenerationType> bindGenerationTypeMapper();
}
