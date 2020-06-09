package com.autumn.application.service;

import com.autumn.application.dto.input.DataImportOptionInput;
import com.autumn.application.dto.input.ExcelImportOptionInput;
import com.autumn.constants.GenericParameterConstant;
import com.autumn.domain.entities.Entity;
import com.autumn.domain.repositories.EntityRepository;
import com.autumn.exception.ExceptionUtils;
import com.autumn.util.data.DataImportUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;
import java.io.Serializable;
import java.util.List;

/**
 * 数据编辑并导入抽象
 * <p>
 * </p>
 *
 * @param <TKey>             主键类型
 * @param <TEntity>          实体类型
 * @param <TRepository>      仓储类型
 * @param <TQueryEntity>     查询实体类型
 * @param <TQueryRepository> 查询仓储类型
 * @param <TAddInput>        添加输入类型
 * @param <TUpdateInput>     更新输入类型
 * @param <TOutputItem>      输出项目类型
 * @param <TOutputDetails>   输出详情类型
 * @param <TImportTemplate>  导入模板类型
 * @description TODO
 * @author: 老码农
 * @create: 2020-03-05 19:23
 **/
public abstract class AbstractEditDataImportApplicationService<TKey extends Serializable,
        TEntity extends Entity<TKey>,
        TRepository extends EntityRepository<TEntity, TKey>,
        TQueryEntity extends Entity<TKey>,
        TQueryRepository extends EntityRepository<TQueryEntity, TKey>,
        TAddInput,
        TUpdateInput extends Entity<TKey>,
        TOutputItem,
        TOutputDetails,
        TImportTemplate>
        extends AbstractEditApplicationService<TKey, TEntity, TRepository, TQueryEntity, TQueryRepository, TAddInput, TUpdateInput, TOutputItem, TOutputDetails>
        implements DataImportApplicationService<TImportTemplate> {

    private final Class<TImportTemplate> importTemplateClass;

    /**
     *
     */
    @SuppressWarnings("unchecked")
    public AbstractEditDataImportApplicationService() {
        super();
        this.importTemplateClass = this.getGenericActualClass(GenericParameterConstant.IMPORT_TEMPLATE);
    }

    /**
     * @param entityClass
     * @param queryEntityClass
     * @param outputItemClass
     * @param outputDetailsClass
     * @param importTemplateClass
     */
    public AbstractEditDataImportApplicationService(Class<TEntity> entityClass,
                                                    Class<TQueryEntity> queryEntityClass,
                                                    Class<TOutputItem> outputItemClass,
                                                    Class<TOutputDetails> outputDetailsClass,
                                                    Class<TImportTemplate> importTemplateClass) {
        super(entityClass, queryEntityClass, outputItemClass, outputDetailsClass);
        this.importTemplateClass = ExceptionUtils.checkNotNull(importTemplateClass, "importTemplateClass");
    }

    /**
     * @param entityClassArgumentsName
     * @param outputClassArgumentsName
     * @param queryEntityClassArgumentsName
     * @param queryItemResultClassArgumentsName
     * @param importTemplateClassArgumentsName
     */
    @SuppressWarnings("unchecked")
    protected AbstractEditDataImportApplicationService(String entityArgName,
                                                       String queryEntityArgName,
                                                       String outputItemArgName,
                                                       String outputDetailsArgName,
                                                       String importTemplateArgName) {
        super(entityArgName, queryEntityArgName, outputItemArgName, outputDetailsArgName);
        this.importTemplateClass = this.getGenericActualClass(importTemplateArgName);
    }

    /**
     * 获取导入模板类型
     *
     * @return
     */
    @Override
    public Class<TImportTemplate> getImportTemplateClass() {
        return this.importTemplateClass;
    }

    @Override
    public Workbook excelImportTemplate() {
        return DataImportUtils.excelImportTemplate(this);
    }

    @Override
    public int getImportPageSize() {
        return DataImportApplicationService.DEFAULT_IMPORT_PAGE_SIZE;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int excelImport(InputStream inputStream, ExcelImportOptionInput option) {
        return DataImportUtils.excelImportForUpdate(this, inputStream, option);
    }

    /**
     * 是否批量导入数据
     *
     * @return
     */
    protected boolean isBatchDataImport() {
        return true;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int dataImport(List<TImportTemplate> items, DataImportOptionInput option) {
        if (this.isBatchDataImport()) {
            int result = DataImportUtils.excelImportForUpdate(this, this.getRepository(), items, option, this::dataImportHandle, this::dataImportAfter);
            if (result > 0) {
                this.clearCache();
            }
            return result;
        } else {
            List<TEntity> entities = this.dataImportHandle(items, option);
            if (entities == null || entities.size() == 0) {
                this.dataImportAfter(entities);
                return 0;
            }
            for (TEntity entity : entities) {
                this.getRepository().insert(entity);
            }
            this.dataImportAfter(entities);
            this.clearCache();
            return entities.size();
        }
    }

    /**
     * 数据导入之后
     *
     * @param entities 实体集合
     */
    protected void dataImportAfter(List<TEntity> entities) {

    }

    /**
     * 数据导入处理
     *
     * @param items  项目集合
     * @param option 选项
     * @return
     */
    protected abstract List<TEntity> dataImportHandle(List<TImportTemplate> items, DataImportOptionInput option);

}
