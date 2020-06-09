package com.autumn.mybatis.mapper;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Objects;

/**
 * 上下文映射 Bean 工厂
 *
 * @author 老码农
 * <p>
 * 2017-11-01 20:34:24
 */
public class AutumnMapperFactoryBean<T> extends org.mybatis.spring.mapper.MapperFactoryBean<T> {

    /**
     * 日志
     */
    private final static Log logger = LogFactory.getLog(AutumnMapperFactoryBean.class);

    private MapperRegister mapperRegister;

    /**
     *
     */
    public AutumnMapperFactoryBean() {
    }

    /**
     * @param mapperInterface
     */
    public AutumnMapperFactoryBean(Class<T> mapperInterface) {
        super(mapperInterface);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void checkDaoConfig() {
        super.checkDaoConfig();
        if (logger.isDebugEnabled()) {
            logger.debug("配置仓储接口 " + Objects.requireNonNull(getObjectType()).getName());
        }
        if (mapperRegister != null && mapperRegister.isRegisterMapperInterface(getObjectType())) {
            mapperRegister.configure(getSqlSession().getConfiguration());
            if (logger.isDebugEnabled()) {
                logger.debug("注册 " + Objects.requireNonNull(getObjectType()).getName() + " 拦截方法。");
            }
        }
    }

    /**
     * @param mapperRegister
     */
    public void setMapperRegister(MapperRegister mapperRegister) {
        this.mapperRegister = mapperRegister;
        if (this.mapperRegister != null) {
            this.mapperRegister.registerWithMapper();
            this.mapperRegister.registerMapperDbProviderMap((Class<? extends Mapper>) this.getMapperInterface());
        }
    }

}
