package com.autumn.application.service;

import com.autumn.constants.GenericParameterConstant;
import com.autumn.domain.entities.AbstractSystemCommonConfig;
import com.autumn.domain.repositories.EntityRepository;
import com.autumn.exception.ExceptionUtils;
import com.autumn.mybatis.wrapper.EntityQueryWrapper;
import com.autumn.util.StringUtils;
import com.autumn.util.TypeUtils;
import com.autumn.util.json.JsonUtils;
import com.autumn.util.reflect.BeanProperty;
import com.autumn.util.reflect.ReflectUtils;
import com.autumn.validation.DataValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.*;

/**
 * 公共配置抽象
 * <p>
 * 提供单表保存各种配置。
 * </p>
 *
 * @param <TEntity>     实体类型
 * @param <TRepository> 仓储类型
 * @param <TInput>      输入类型
 * @param <TOutput>     输出类型
 * @Description TODO
 * @Author 老码农
 * @Date 2019-07-31 0:50
 */
public abstract class AbstractCommonConfigApplicationService<TEntity extends AbstractSystemCommonConfig,
        TRepository extends EntityRepository<TEntity, Long>, TInput extends Serializable, TOutput extends Serializable>
        extends AbstractConfigApplicationService<TInput, TOutput> {

    private final Class<TEntity> entityClass;

    @Autowired
    private TRepository repository;

    /**
     * 实例化
     */
    public AbstractCommonConfigApplicationService() {
        super();
        this.entityClass = this.getGenericActualClass(GenericParameterConstant.OUTPUT);
    }

    /**
     * 实例化
     *
     * @param entitArgName
     * @param outputArgName
     */
    @SuppressWarnings("unchecked")
    public AbstractCommonConfigApplicationService(String entitArgName, String outputArgName) {
        super(outputArgName);
        this.entityClass = this.getGenericActualClass(entitArgName);
    }

    /**
     * 实例化
     *
     * @param entityClass   实体类型
     * @param outputArgName 输出参数名称
     */
    public AbstractCommonConfigApplicationService(Class<TEntity> entityClass, String outputArgName) {
        super(outputArgName);
        this.entityClass = ExceptionUtils.checkNotNull(entityClass, "entityClass");
    }

    /**
     * @param entityClass
     * @param outputClass
     */
    public AbstractCommonConfigApplicationService(Class<TEntity> entityClass, Class<TOutput> outputClass) {
        super(outputClass);
        this.entityClass = ExceptionUtils.checkNotNull(entityClass, "entityClass");
    }

    /**
     * 获取配置类型
     *
     * @return
     */
    protected abstract String getConfigType();

    @Override
    protected final String getCacheKey() {
        return "system_common_" + this.getConfigType();
    }


    /**
     * 获取实体类型
     *
     * @return
     */
    public final Class<TEntity> getEntityClass() {
        return this.entityClass;
    }

    /**
     * 获取仓储
     *
     * @return
     */
    public TRepository getRepository() {
        return this.repository;
    }

    /**
     * 创建对象Map
     *
     * @param obj 对象
     * @return
     */
    protected final Map<BeanProperty, Object> createObjectMap(Object obj) {
        Map<String, BeanProperty> inputProperty = ReflectUtils.getBeanPropertyMap(obj.getClass());
        Map<BeanProperty, Object> objMap = new HashMap<>(inputProperty.size());
        for (BeanProperty property : inputProperty.values()) {
            if (property.isBaseType()) {
                objMap.put(property, property.getValue(obj));
            }
        }
        return objMap;
    }

    /**
     * 创建对象Map
     *
     * @param entities 实体集合
     * @return
     */
    protected Map<String, Object> createObjectMapByEntitys(List<TEntity> entities) {
        Map<String, Object> objMap = new HashMap<>(16);
        if (entities != null) {
            for (TEntity entity : entities) {
                objMap.put(entity.getName(), entity.getValue());
            }
        }
        return objMap;
    }

    /**
     * 值转换为文本
     *
     * @param value
     * @return
     */
    protected String toValueString(Object value) {
        if (value == null) {
            return "";
        }
        if (value instanceof Date) {
            return Long.toString(((Date) value).getTime());
        } else if (value instanceof Boolean) {
            return ((Boolean) value).toString();
        }
        if (TypeUtils.isBaseType(value.getClass())) {
            return value.toString().trim();
        }
        return JsonUtils.toJSONString(value);
    }

    /**
     * 转换对象
     *
     * @param targetClass
     * @param value
     * @return
     */
    protected Object toConvertObject(Class<?> targetClass, Object value) {
        if (value == null) {
            return null;
        }
        String str = value.toString();
        if (StringUtils.isNullOrBlank(str)) {
            return null;
        }
        if (Date.class.isAssignableFrom(targetClass)) {
            return new Date(Long.parseLong(str.trim()));
        }
        if (TypeUtils.isBaseType(targetClass)) {
            return TypeUtils.toConvert(targetClass, str.trim());
        }
        return JsonUtils.parseObject(str, targetClass);
    }

    /**
     * 创建实体实例
     *
     * @return
     */
    protected TEntity createEntityInstance() {
        try {
            return this.getEntityClass().newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            this.getLogger().error(e.getMessage(), e);
            throw ExceptionUtils.throwValidationException("类型　" + this.getEntityClass().getName() + " 未提供默认构造函数。");
        }
    }

    /**
     * 创建输出实例
     *
     * @return
     */
    protected TOutput createOutputInstance() {
        try {
            return this.getOutputClass().newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            this.getLogger().error(e.getMessage(), e);
            throw ExceptionUtils.throwValidationException("类型　" + this.getOutputClass().getName() + " 未提供默认构造函数。");
        }
    }

    /**
     * 获取字段标题Map
     * <p>
     * key 字段名称
     * value 标题名称
     * </p>
     *
     * @return
     */
    protected Map<String, String> getFieldTitleMap() {
        return null;
    }

    /**
     * 创建实体
     *
     * @param obj 映射
     * @return
     */
    protected List<TEntity> createEntitys(Object obj) {
        Class<?> beanClass = obj.getClass();
        Map<String, String> titleMap = this.getFieldTitleMap();
        Map<BeanProperty, String> propertyFriendlyMap = TypeUtils.getPropertyFriendlyMap(beanClass);
        Map<BeanProperty, Object> map = this.createObjectMap(obj);
        List<TEntity> entities = new ArrayList<>(map.size());
        for (Map.Entry<BeanProperty, Object> entry : map.entrySet()) {
            TEntity entity = this.createEntityInstance();
            Object value = entry.getValue();
            String felid = entry.getKey().getName();
            entity.setName(felid);
            entity.setType(entry.getKey().getType().getSimpleName());
            String title = null;
            if (titleMap != null) {
                title = titleMap.get(felid);
            }
            if (StringUtils.isNullOrBlank(title)) {
                title = propertyFriendlyMap.get(entry.getKey());
            }
            if (title == null) {
                title = "";
            }
            entity.setTitle(title);
            entity.setConfigType(this.getConfigType());
            entity.setModuleName(this.getModuleName());
            entity.setValue(this.toValueString(value));
            entities.add(entity);
        }
        return entities;
    }

    /**
     * 保存之前
     *
     * @param input 输入
     * @return
     */
    protected List<TEntity> saveBefore(TInput input) {
        return this.createEntitys(input);
    }

    /**
     * 保存之后
     *
     * @param input    输入
     * @param entities 实体集合
     * @return
     */
    protected void saveAfter(TInput input, List<TEntity> entities) {

    }

    /**
     * 创建查询 QueryWapper
     *
     * @return
     */
    protected EntityQueryWrapper<TEntity> createQueryWrapper() {
        EntityQueryWrapper<TEntity> wrapper = new EntityQueryWrapper<>(this.getEntityClass());
        wrapper.where().eq(AbstractSystemCommonConfig.FIELD_CONFIG_TYPE, this.getConfigType());
        wrapper.orderBy(AbstractSystemCommonConfig.FIELD_ID);
        return wrapper;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public TOutput save(TInput input) {
        ExceptionUtils.checkNotNull(input, "input");
        if (input instanceof DataValidation) {
            DataValidation validation = (DataValidation) input;
            validation.valid();
        }
        EntityQueryWrapper<TEntity> wrapper = this.createQueryWrapper();
        this.getRepository().deleteByWhere(wrapper);
        List<TEntity> entities = this.saveBefore(input);
        for (TEntity entity : entities) {
            entity.setConfigType(this.getConfigType());
            entity.setModuleName(this.getModuleName());
            entity.forNullToDefault();
            this.getRepository().insert(entity);
        }
        this.saveAfter(input, entities);
        this.clearCache();
        TOutput result = this.queryForOutput();
        this.writeSaveLog(input);
        return result;
    }

    /**
     * 系统条件处理
     *
     * @param wrapper 查询
     */
    protected void systemByCriteria(EntityQueryWrapper<TEntity> wrapper) {

    }

    /**
     * 创建输出
     *
     * @param objMap
     * @return
     */
    protected TOutput createOutputInstance(Map<String, Object> objMap) {
        TOutput output = this.createOutputInstance();
        Map<String, BeanProperty> propertyMap = ReflectUtils.getBeanPropertyMap(this.getOutputClass());
        for (Map.Entry<String, Object> entry : objMap.entrySet()) {
            BeanProperty property = propertyMap.get(entry.getKey());
            if (property != null && property.canWrite()) {
                property.setValue(output, toConvertObject(property.getType(), entry.getValue()));
            }
        }
        return output;
    }

    @Override
    protected TOutput queryByCreateOutput() {
        EntityQueryWrapper<TEntity> wrapper = this.createQueryWrapper();
        this.systemByCriteria(wrapper);
        List<TEntity> entities = this.getRepository().selectForList(wrapper);
        Map<String, Object> objMap = this.createObjectMapByEntitys(entities);
        return this.createOutputInstance(objMap);
    }
}
