package com.autumn.zero.common.library.application.services.sys.config;

import com.autumn.application.service.AbstractCommonConfigApplicationService;
import com.autumn.constants.GenericParameterConstant;
import com.autumn.zero.common.library.entities.sys.SystemCommonConfig;
import com.autumn.zero.common.library.repositories.sys.SystemCommonConfigRepository;

import java.io.Serializable;

/**
 * 公共配置基础抽象
 *
 * @param <TInput>  输入类型
 * @param <TOutput> 输出类型
 * @Description TODO
 * @Author 老码农
 * @Date 2019-07-31 2:22
 */
public abstract class AbstractCommonConfigAppServiceBase<TInput extends Serializable, TOutput extends Serializable>
        extends AbstractCommonConfigApplicationService<SystemCommonConfig, SystemCommonConfigRepository, TInput, TOutput> {

    /**
     * 实例化
     */
    public AbstractCommonConfigAppServiceBase() {
        super(SystemCommonConfig.class, GenericParameterConstant.OUTPUT);
    }

    @Override
    protected final SystemCommonConfig createEntityInstance() {
        return new SystemCommonConfig();
    }
}
