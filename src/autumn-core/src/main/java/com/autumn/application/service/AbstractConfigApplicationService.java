package com.autumn.application.service;

import com.autumn.constants.GenericParameterConstant;
import com.autumn.exception.ExceptionUtils;
import com.autumn.util.ServiceUtils;
import com.autumn.util.StringUtils;
import com.autumn.util.reflect.ReflectUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 配置应用服务抽象
 *
 * @param <TInput>  输入类型
 * @param <TOutput> 输出类型
 * @Description TODO
 * @Author 老码农
 * @Date 2019-07-31 0:19
 */
public abstract class AbstractConfigApplicationService<TInput extends Serializable, TOutput extends Serializable>
        extends AbstractApplicationService implements ConfigApplicationService<TInput, TOutput> {

    /**
     * 缓存名称
     */
    public static final String CACHE_NAME = "cache_application_configure";

    private final Class<TOutput> outputClass;
    private Map<String, Class<?>> genericActualArgumentsTypeMap = null;

    /**
     * 实例化
     */
    public AbstractConfigApplicationService() {
        this(GenericParameterConstant.OUTPUT);
    }

    /**
     * 实例化
     *
     * @param outputArgName 输出参数名称
     */
    @SuppressWarnings("unchecked")
    public AbstractConfigApplicationService(String outputArgName) {
        this.outputClass = this.getGenericActualClass(outputArgName);
    }

    /**
     * @param outputClass
     */
    public AbstractConfigApplicationService(Class<TOutput> outputClass) {
        this.outputClass = ExceptionUtils.checkNotNull(outputClass, "outputClass");
    }

    /**
     * 获取输出类型
     *
     * @return
     */
    public final Class<TOutput> getOutputClass() {
        return this.outputClass;
    }

    /**
     * 获取泛型参数类型Map
     *
     * @return
     */
    protected final Map<String, Class<?>> getGenericActualArgumentsTypeMap() {
        synchronized (this) {
            if (this.genericActualArgumentsTypeMap == null) {
                this.genericActualArgumentsTypeMap = ReflectUtils.getGenericActualArgumentsTypeMap(this.getClass());
            }
            return this.genericActualArgumentsTypeMap;
        }
    }

    /**
     * 获取泛型实际类型
     *
     * @param genericArgTypeName 泛型参数类型名称
     * @return
     */
    @SuppressWarnings("unchecked")
    protected final <TArg> Class<TArg> getGenericActualClass(String genericArgTypeName) {
        return ServiceUtils.getGenericActualClass(this.getGenericActualArgumentsTypeMap(), genericArgTypeName);
    }

    /**
     * 获取缓存名称
     *
     * @return
     */
    protected String getCacheName() {
        return CACHE_NAME;
    }

    /**
     * 获取缓存键
     *
     * @return
     */
    protected abstract String getCacheKey();

    /**
     * 是否启用缓存
     * <p>
     * 默认启用
     * </p>
     *
     * @return
     */
    @Override
    public boolean isEnableCache() {
        return true;
    }

    /**
     * 清除缓存
     */
    @Override
    public void clearCache() {
        if (this.isCache()) {
            List<String> keys = new ArrayList<>(16);
            keys.add(this.getCacheKey());
            String[] otherCacheKeys = this.getOtherCacheKeys();
            if (otherCacheKeys != null && otherCacheKeys.length > 0) {
                keys.addAll(Arrays.asList(otherCacheKeys));
            }
            this.clearCacheKeys(this.getCacheName(), keys);
        }
    }

    /**
     * 是否缓存
     *
     * @return
     */
    protected final boolean isCache() {
        return this.isEnableCache() && !StringUtils.isNullOrBlank(this.getCacheKey());
    }


    /**
     * 获取其他缓存键
     *
     * @return
     */
    protected String[] getOtherCacheKeys() {
        return null;
    }

    /**
     * 查询并创建输出
     *
     * @return
     */
    protected abstract TOutput queryByCreateOutput();

    /**
     * 写入保存操作日志
     *
     * @param inputOrEntity 输入或实体
     */
    protected void writeSaveLog(Object inputOrEntity) {
        this.getAuditedLogger().addLog(this, "保存", inputOrEntity);
    }

    @Override
    public TOutput queryForOutput() {
        if (!this.isCache()) {
            return this.queryByCreateOutput();
        }
        return this.getOrAddCache(this.getCacheName(), this.getCacheKey(), this::queryByCreateOutput);
    }
}
