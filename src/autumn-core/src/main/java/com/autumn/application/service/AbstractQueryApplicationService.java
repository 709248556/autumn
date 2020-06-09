package com.autumn.application.service;

import com.autumn.application.dto.input.*;
import com.autumn.constants.GenericParameterConstant;
import com.autumn.domain.entities.Entity;
import com.autumn.domain.repositories.EntityRepository;
import com.autumn.exception.ExceptionUtils;
import com.autumn.mybatis.mapper.PageResult;
import com.autumn.mybatis.metadata.EntityColumn;
import com.autumn.mybatis.metadata.EntityTable;
import com.autumn.mybatis.wrapper.EntityQueryWrapper;
import com.autumn.mybatis.wrapper.LockModeEnum;
import com.autumn.util.AutoMapUtils;
import com.autumn.util.ServiceUtils;
import com.autumn.util.StringUtils;
import com.autumn.util.data.EntityQueryBuilder;
import com.autumn.util.data.PageQueryBuilder;
import com.autumn.util.excel.exports.GenericExportInfo;
import com.autumn.util.excel.sheet.WorkSheetInfo;
import com.autumn.util.excel.utils.ExcelUtils;
import com.autumn.util.reflect.ReflectUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;

/**
 * 查询抽象
 *
 * @param <TKey>             主键类型
 * @param <TQueryEntity>     查询实体
 * @param <TQueryRepository> 查询仓储
 * @param <TOutputItem>      输出项目类型(列表项目)
 * @param <TOutputDetails>   输出详情类型(单条详情项目)
 * @author 老码农 2018-11-16 17:39:48
 */
public abstract class AbstractQueryApplicationService<TKey extends Serializable,
        TQueryEntity extends Entity<TKey>,
        TQueryRepository extends EntityRepository<TQueryEntity, TKey>,
        TOutputItem,
        TOutputDetails>
        extends AbstractApplicationService implements QueryApplicationService<TKey, TOutputItem, TOutputDetails> {

    /**
     *
     */
    public static final int EXCEL_EXPORT_MAX_ROW_COUNT = 100000;

    /**
     *
     */
    public static final int PAGE_MAX_ROW_COUNT = 200;

    /**
     * 日志导出操作名称
     */
    public static final String LOG_OPERATION_NAME_EXPORT = "导出";

    /**
     * 缓存名称前缀
     */
    public static final String CACHE_NAME_PREFIX = "cache_";

    /**
     * 缓存查询键id前缀
     */
    public static final String CACHE_QUERY_BY_ID_PREFIX = "query_by_id_";

    @Autowired
    private TQueryRepository queryRepository;

    private final Class<TQueryEntity> queryEntityClass;
    private final Class<TOutputItem> outputItemClass;
    private final Class<TOutputDetails> outputDetailsClass;

    /**
     * 搜索成员集合
     */
    private Set<String> searchMembers = new HashSet<>();

    /**
     * 获取查询实体类型
     *
     * @return
     */
    public Class<TQueryEntity> getQueryEntityClass() {
        return this.queryEntityClass;
    }

    /**
     * 获取输出项目类型
     *
     * @return
     */
    public Class<TOutputItem> getOutputItemClass() {
        return this.outputItemClass;
    }

    /**
     * 获取详情输出类型
     *
     * @return
     */
    public Class<TOutputDetails> getOutputDetailsClass() {
        return this.outputDetailsClass;
    }

    private EntityTable queryEntityTable = null;


    /**
     * 获取查询表
     */
    public EntityTable getQueryEntityTable() {
        if (this.queryEntityTable == null) {
            synchronized (this) {
                if (this.queryEntityTable == null) {
                    this.queryEntityTable = EntityTable.getTable(this.getQueryEntityClass());
                }
            }
        }
        return this.queryEntityTable;
    }


    /**
     * 实例化
     */
    @SuppressWarnings("unchecked")
    public AbstractQueryApplicationService() {
        this(GenericParameterConstant.QUERY_ENTITY, GenericParameterConstant.OUTPUT_ITEM, GenericParameterConstant.OUTPUT_DETAILS);
    }

    /**
     * 实例化
     *
     * @param queryEntityArgName   查询实体参数名称
     * @param outputItemArgName    输出项目参数名称
     * @param outputDetailsArgName 输出详情参数名称
     */
    public AbstractQueryApplicationService(String queryEntityArgName, String outputItemArgName, String outputDetailsArgName) {
        this.queryEntityClass = this.getGenericActualClass(queryEntityArgName);
        this.outputItemClass = this.getGenericActualClass(outputItemArgName);
        this.outputDetailsClass = this.getGenericActualClass(outputDetailsArgName);
    }

    /**
     * 实例化
     *
     * @param queryEntityClass   查询实体类型
     * @param outputItemClass    输出项目类型
     * @param outputDetailsClass 输出详情类型
     */
    public AbstractQueryApplicationService(Class<TQueryEntity> queryEntityClass,
                                           Class<TOutputItem> outputItemClass,
                                           Class<TOutputDetails> outputDetailsClass) {
        this.queryEntityClass = ExceptionUtils.checkNotNull(queryEntityClass, "queryEntityClass");
        this.outputItemClass = ExceptionUtils.checkNotNull(outputItemClass, "outputItemClass");
        this.outputDetailsClass = ExceptionUtils.checkNotNull(outputDetailsClass, "outputDetailsClass");
    }

    private Map<String, Class<?>> genericActualArgumentsTypeMap = null;

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

    @Override
    public String getModuleId() {
        return this.getQueryEntityClass().getSimpleName();
    }

    /**
     * 获取查询仓储
     *
     * @return
     */
    public TQueryRepository getQueryRepository() {
        return this.queryRepository;
    }

    /**
     * 查询排序处理
     *
     * @param query 查询
     */
    protected void queryByOrder(EntityQueryWrapper<TQueryEntity> query) {
        query.orderBy(Entity.FIELD_ID);
    }

    /**
     * 系统条件处理
     *
     * @param wrapper 包装
     */
    protected void systemByCriteria(EntityQueryWrapper<TQueryEntity> wrapper) {

    }

    /**
     * 获取搜索成员
     *
     * @return
     */
    public final Set<String> getSearchMembers() {
        return this.searchMembers;
    }

    /**
     * 获取Excel导出的最大行数
     *
     * @return
     */
    public int getExcelExportMaxRowCount() {
        return EXCEL_EXPORT_MAX_ROW_COUNT;
    }

    /**
     * 获取页的最大行数
     *
     * @return
     */
    public int getPageMaxRowCount() {
        return PAGE_MAX_ROW_COUNT;
    }

    /**
     * 查询输入条件
     *
     * @param query 查询
     * @param input 输入
     */
    protected void queryInputCriteria(EntityQueryWrapper<TQueryEntity> query, AdvancedSearchInput input) {

    }

    /**
     * 创建实体查询
     *
     * @param input 输入
     * @return
     */
    protected EntityQueryBuilder<TQueryEntity> createEntityQuery(AdvancedQueryInput input) {
        if (input == null) {
            input = new DefaultAdvancedQueryInput();
        }
        EntityQueryBuilder<TQueryEntity> queryBuilder = new EntityQueryBuilder<>(this.getQueryEntityClass());
        this.systemByCriteria(queryBuilder.getQuery());
        this.queryInputCriteria(queryBuilder.getQuery(), input);
        queryBuilder.searchMembers(input.getSearchKeyword(), this.getSearchMembers());
        queryBuilder.criteriaByItem(input.getCriterias());
        this.queryByOrder(queryBuilder.getQuery());
        return queryBuilder;
    }

    /**
     * 项目项目转换处理器
     *
     * @param source
     * @param target
     */
    protected void itemConvertHandle(TQueryEntity source, TOutputItem target) {

    }

    /**
     * 获取查询列表列集合
     *
     * @return
     */
    public List<EntityColumn> getQueryListColumns() {
        return null;
    }

    /**
     * 生成查询列
     *
     * @param wrapper 映射
     */
    protected final void generateQueryListColumn(EntityQueryWrapper<TQueryEntity> wrapper) {
        List<EntityColumn> columns = this.getQueryListColumns();
        if (columns != null && columns.size() > 0) {
            for (EntityColumn column : columns) {
                wrapper.select().column(column.getPropertyName());
            }
        }
    }

    @Override
    public List<TOutputItem> queryForList(AdvancedQueryInput input) {
        EntityQueryBuilder<TQueryEntity> queryBuilder = this.createEntityQuery(input);
        this.generateQueryListColumn(queryBuilder.getQuery());
        return queryBuilder.toResult(getQueryRepository(), this.getOutputItemClass(), this::itemConvertHandle);
    }

    @Override
    public PageResult<TOutputItem> queryForPage(AdvancedPageQueryInput input) {
        if (input == null) {
            input = new DefaultAdvancedPageQueryInput();
        }
        PageQueryBuilder<TQueryEntity> queryBuilder = new PageQueryBuilder<>(this.getQueryEntityClass());
        this.generateQueryListColumn(queryBuilder.getQuery());
        this.systemByCriteria(queryBuilder.getQuery());
        this.queryInputCriteria(queryBuilder.getQuery(), input);
        queryBuilder.searchMembers(input.getSearchKeyword(), this.getSearchMembers()).page(input,
                this.getPageMaxRowCount());
        this.queryByOrder(queryBuilder.getQuery());
        return queryBuilder.toPageResult(getQueryRepository(), this.getOutputItemClass(), this::itemConvertHandle);
    }


    /**
     * 获取日志导出操作名称
     *
     * @return
     */
    protected String getLogExportOperationName() {
        return LOG_OPERATION_NAME_EXPORT;
    }

    @Override
    public Workbook exportByExcel(AdvancedQueryInput input) {
        EntityQueryBuilder<TQueryEntity> queryBuilder = this.createEntityQuery(input);
        TQueryRepository repository = getQueryRepository();
        int count = queryBuilder.toCount(repository);
        int maxRowCount = this.getExcelExportMaxRowCount();
        if (count > maxRowCount) {
            ExceptionUtils.throwValidationException(String.format("需要导出的数据超过 %s 行，请通过条件限制。", maxRowCount));
        }
        this.generateQueryListColumn(queryBuilder.getQuery());
        List<TOutputItem> items = queryBuilder.toResult(repository, this.getOutputItemClass(), this::itemConvertHandle);
        GenericExportInfo<TOutputItem> exportInfo = new GenericExportInfo<>(this.getOutputItemClass());
        exportInfo.setItems(items);
        String haderName = this.getExportHeaderName(input);
        WorkSheetInfo workSheetInfo = ExcelUtils.getWorkSheetInfo(this.getOutputItemClass(), haderName);
        this.setExportWorkSheetInfo(input, workSheetInfo, exportInfo);
        Workbook workbook = workSheetInfo.createExportWorkbook(exportInfo, false);
        this.getAuditedLogger().addLog(haderName, this.getLogExportOperationName(),
                this.getLogExportOperationName() + "到Excel文件，记录数:" + count);
        return workbook;
    }

    /**
     * 获取导出的标题名称
     *
     * @param input 输入
     * @return
     */
    protected String getExportHeaderName(AdvancedQueryInput input) {
        return this.getModuleName();
    }

    /**
     * 设置导出工作簿信息
     *
     * @param input         输入
     * @param workSheetInfo 工作簿
     * @param exportInfo    导出信息
     */
    protected void setExportWorkSheetInfo(AdvancedQueryInput input, WorkSheetInfo workSheetInfo,
                                          GenericExportInfo<TOutputItem> exportInfo) {

    }

    /**
     * 获取缓存名称
     *
     * @return
     */
    protected String getCacheName() {
        return CACHE_NAME_PREFIX + this.getModuleId();
    }

    /**
     * 是否启用缓存
     * <p>
     * 默认不启用
     * </p>
     *
     * @return
     */
    @Override
    public boolean isEnableCache() {
        return false;
    }

    /**
     * 清除缓存
     */
    @Override
    public void clearCache() {
        if (this.isCache()) {
            this.clearCache(this.getCacheName());
        }
    }

    /**
     * 是否缓存
     *
     * @return
     */
    protected final boolean isCache() {
        return this.isEnableCache() && !StringUtils.isNullOrBlank(this.getCacheName());
    }

    /**
     * 根据id 创建缓存键
     *
     * @param id
     * @return
     */
    protected String createCacheKeyById(TKey id) {
        if (id == null) {
            return CACHE_QUERY_BY_ID_PREFIX;
        }
        return CACHE_QUERY_BY_ID_PREFIX + id;
    }

    /**
     * 根据id清除缓存
     *
     * @param id 主键
     */
    protected void clearCacheById(TKey id) {
        if (this.isCache()) {
            this.clearCacheKey(this.getCacheName(), this.createCacheKeyById(id));
        }
    }

    /**
     * 获取或添加缓存
     *
     * @param key         键
     * @param valueLoader 生成
     * @return
     */
    protected final <T> T getOrAddCache(Object key, Callable<T> valueLoader) {
        if (this.isCache()) {
            return this.getOrAddCache(this.getCacheName(), key, valueLoader);
        }
        if (valueLoader != null) {
            try {
                return valueLoader.call();
            } catch (Exception e) {
                throw ExceptionUtils.throwValidationException(e.getMessage(), e);
            }
        }
        return null;
    }

    /**
     * 详情查询输出
     *
     * @param queryEntity 查询实体
     * @return
     */
    protected TOutputDetails toOutputByQuery(TQueryEntity queryEntity) {
        if (queryEntity == null) {
            return null;
        }
        return AutoMapUtils.map(queryEntity, this.getOutputDetailsClass());
    }

    /**
     * 按id查询是否加载子级
     *
     * @return
     */
    protected boolean isQueryByIdLoadChildren() {
        return true;
    }

    /**
     * 创建基于Id的业务查询
     *
     * @param id 主键id值
     * @return
     */
    protected final EntityQueryWrapper<TQueryEntity> createQueryWrapperById(TKey id) {
        EntityQueryWrapper<TQueryEntity> query = new EntityQueryWrapper<>(this.getQueryEntityClass());
        query.where().eq(Entity.FIELD_ID, id);
        this.systemByCriteria(query);
        return query;
    }

    /**
     * 获取查询实体
     *
     * @param id
     * @return
     */
    protected final TQueryEntity getQueryEntityById(TKey id) {
        return this.getQueryEntityById(id, LockModeEnum.NONE);
    }


    /**
     * 获取查询实体
     *
     * @param id
     * @param mode 锁定
     * @return
     */
    protected TQueryEntity getQueryEntityById(TKey id, LockModeEnum mode) {
        EntityQueryWrapper<TQueryEntity> query = this.createQueryWrapperById(id);
        if (mode != null) {
            query.lock(mode);
        } else {
            query.lock(LockModeEnum.NONE);
        }
        return this.getQueryRepository().selectForFirst(query);
    }


    /**
     * 获取更新锁实体
     *
     * @param id
     * @return
     */
    protected final TQueryEntity getQueryEntityByUpdateLock(TKey id) {
        return this.getQueryEntityById(id, LockModeEnum.UPDATE);
    }

    @Override
    public TOutputDetails queryById(TKey id) {
        ExceptionUtils.checkNotNull(id, "id");
        return this.getOrAddCache(this.createCacheKeyById(id), () -> {
            TQueryEntity entity = this.getQueryEntityById(id);
            if (this.isQueryByIdLoadChildren() && entity != null) {
                this.getQueryRepository().load(entity);
            }
            return this.toOutputByQuery(entity);
        });
    }

}
