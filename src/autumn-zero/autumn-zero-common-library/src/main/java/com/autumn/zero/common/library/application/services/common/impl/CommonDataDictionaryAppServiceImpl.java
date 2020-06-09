package com.autumn.zero.common.library.application.services.common.impl;

import com.autumn.application.dto.input.AdvancedQueryInput;
import com.autumn.application.dto.input.AdvancedSearchInput;
import com.autumn.application.dto.input.StatusInput;
import com.autumn.domain.values.IntegerConstantItemValue;
import com.autumn.exception.ExceptionUtils;
import com.autumn.file.storage.FileStorageObject;
import com.autumn.mybatis.wrapper.EntityQueryWrapper;
import com.autumn.mybatis.wrapper.LockModeEnum;
import com.autumn.util.AutoMapUtils;
import com.autumn.util.StringUtils;
import com.autumn.zero.common.library.application.callback.CommonDataDictionaryCallback;
import com.autumn.zero.common.library.application.dto.common.dictionary.CommonDataDictionaryDetailsOutput;
import com.autumn.zero.common.library.application.dto.common.dictionary.CommonDataDictionaryInput;
import com.autumn.zero.common.library.application.dto.common.dictionary.CommonDataDictionaryOutput;
import com.autumn.zero.common.library.application.services.common.CommonDataDictionaryAppService;
import com.autumn.zero.common.library.constants.CommonStatusConstant;
import com.autumn.zero.common.library.entities.common.CommonDataDictionary;
import com.autumn.zero.common.library.entities.common.DataDictionary;
import com.autumn.zero.common.library.repositories.common.CommonDataDictionaryRepository;
import com.autumn.zero.file.storage.application.services.AbstractZeroFileUploadEditAppService;
import com.autumn.zero.file.storage.services.vo.FileAttachmentInformationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.function.Consumer;

/**
 * 公共字典应用服务实现
 *
 * @Description TODO
 * @Author 老码农
 * @Date 2019-07-25 18:57
 */
public class CommonDataDictionaryAppServiceImpl
        extends AbstractZeroFileUploadEditAppService<CommonDataDictionary,
        CommonDataDictionaryRepository,
        CommonDataDictionary,
        CommonDataDictionaryRepository,
        CommonDataDictionaryInput,
        CommonDataDictionaryInput,
        CommonDataDictionaryOutput,
        CommonDataDictionaryDetailsOutput>
        implements CommonDataDictionaryAppService {

    private static final long serialVersionUID = 37277664470005788L;

    @Autowired
    private CommonDataDictionaryCallback commonDataDictionaryCallback;

    /**
     *
     */
    public CommonDataDictionaryAppServiceImpl() {
        this.getSearchMembers().add(CommonDataDictionary.FIELD_NAME);
    }

    @Override
    public int getFileUploadAttachmentType() {
        return this.commonDataDictionaryCallback.getFileUploadAttachmentType();
    }

    @Override
    public String getFileUploadStartPath() {
        return this.commonDataDictionaryCallback.getFileUploadStartPath();
    }

    @Override
    public Set<String> getFileUploadLimitExtensions() {
        return this.commonDataDictionaryCallback.getFileUploadLimitExtensions();
    }

    @Override
    public Long getFileUploadLimitSize() {
        return this.commonDataDictionaryCallback.getFileUploadLimitSize();
    }

    @Override
    public String getModuleId() {
        String id = this.commonDataDictionaryCallback.getModuleId();
        if (StringUtils.isNullOrBlank(id)) {
            return super.getModuleId();
        }
        return id;
    }

    @Override
    public String getModuleName() {
        return this.commonDataDictionaryCallback.getModuleName();
    }

    @Override
    protected void queryInputCriteria(EntityQueryWrapper<CommonDataDictionary> query, AdvancedSearchInput input) {
        if (!(input instanceof DataDictionary)) {
            ExceptionUtils.throwValidationException("提供的查询参数未实现  " + DataDictionary.class.getName() + " 接口");
        }
        DataDictionary dataDictionary = (DataDictionary) input;
        query.where().eq(DataDictionary.FIELD_DICTIONARY_TYPE, dataDictionary.getDictionaryType());
    }

    @Override
    protected void queryByOrder(EntityQueryWrapper<CommonDataDictionary> query) {
        query.orderBy(CommonDataDictionary.FIELD_SORT_ID).orderBy(CommonDataDictionary.FIELD_ID);
    }

    private String customModuleName(CommonDataDictionary entity) {
        return this.commonDataDictionaryCallback.getName(entity.getDictionaryType());
    }

    @Override
    protected void writeAddLog(CommonDataDictionary entity) {
        this.getAuditedLogger().addLog(this.customModuleName(entity), this.getLogAddOperationName(), entity);
    }

    @Override
    protected void writeUpdateLog(CommonDataDictionary oldEntity, CommonDataDictionary newEntity) {
        this.getAuditedLogger().addLog(this.customModuleName(newEntity), this.getLogUpdateOperationName(), this.getUpdateLogDetails(oldEntity, newEntity));
    }

    @Override
    protected void writeDeleteLog(CommonDataDictionary entity) {
        this.getAuditedLogger().addLog(this.customModuleName(entity), this.getLogDeleteOperationName(), entity);
    }

    private void editCheck(EntityQueryWrapper<CommonDataDictionary> query, CommonDataDictionaryInput input,
                           boolean checkName) {
        if (!this.existDictionaryType(input.getDictionaryType())) {
            ExceptionUtils.throwValidationException("指定的字典类型不支持。");
        }
        if (checkName) {
            /*query.lambda().where(q -> q.eq(CommonDataDictionary::getName, input.getName().trim())
                    .eq(CommonDataDictionary::getDictionaryType, input.getDictionaryType())
                    .eq(CommonDataDictionary::isDelete, false));*/

            query.where().eq(CommonDataDictionary.FIELD_NAME, input.getName().trim())
                    .eq(CommonDataDictionary.FIELD_DICTIONARY_TYPE, input.getDictionaryType());
            if (query.exist(this.getRepository())) {
                ExceptionUtils.throwValidationException("相同的字典类型，名称[" + input.getName() + "]已重复。");
            }
        }
    }

    @Override
    protected CommonDataDictionary addBefore(CommonDataDictionaryInput input, EntityQueryWrapper<CommonDataDictionary> query) {
        this.editCheck(query, input, true);
        CommonDataDictionary result = super.addBefore(input, query);
        result.setSysDictionary(false);
        result.setIdentification(null);
        result.setSysDictionaryValue(null);
        return result;
    }

    @Override
    protected void updateBefore(CommonDataDictionaryInput input, CommonDataDictionary entity,
                                EntityQueryWrapper<CommonDataDictionary> query) {
        input.setDictionaryType(entity.getDictionaryType());
        boolean checkName = !entity.getName().equalsIgnoreCase(input.getName().trim());
        this.editCheck(query, input, checkName);
        super.updateBefore(input, entity, query);
    }

    @Override
    protected void deleteBefore(CommonDataDictionary entity) {
        if (entity.isSysDictionary()) {
            ExceptionUtils.throwValidationException("不能删除系统项。");
        }
        super.deleteBefore(entity);
    }

    @Override
    protected CommonDataDictionaryDetailsOutput addAfter(CommonDataDictionaryInput input, CommonDataDictionary entity,
                                                         EntityQueryWrapper<CommonDataDictionary> query) {
        CommonDataDictionaryDetailsOutput result = super.addAfter(input, entity, query);
        this.clearCache();
        return result;
    }

    @Override
    protected CommonDataDictionaryDetailsOutput updateAfter(CommonDataDictionaryInput input,
                                                            CommonDataDictionary entity, CommonDataDictionary oldEntity, EntityQueryWrapper<CommonDataDictionary> query) {
        return super.updateAfter(input, entity, oldEntity, query);
    }

    @Override
    protected String getExportHeaderName(AdvancedQueryInput input) {
        if (input instanceof DataDictionary) {
            DataDictionary dataDictionary = (DataDictionary) input;
            IntegerConstantItemValue item = this.commonDataDictionaryCallback.getItem(dataDictionary.getDictionaryType());
            if (item != null) {
                return item.getName();
            }
        }
        return this.getModuleName();
    }

    @Override
    public Collection<IntegerConstantItemValue> dictionaryTypeList() {
        return this.commonDataDictionaryCallback.items();
    }

    /**
     * @param consumer
     * @return
     */
    private EntityQueryWrapper<CommonDataDictionary> createQuery(Consumer<EntityQueryWrapper<CommonDataDictionary>> consumer) {
        EntityQueryWrapper<CommonDataDictionary> query = new EntityQueryWrapper<>(CommonDataDictionary.class);
        if (consumer != null) {
            consumer.accept(query);
        }
        return query;
    }

    /**
     * 单条启用查询
     *
     * @param condition 条件
     * @return
     */
    private CommonDataDictionary queryByEnableSingle(Consumer<EntityQueryWrapper<CommonDataDictionary>> condition) {
        EntityQueryWrapper<CommonDataDictionary> query = this.createQuery(condition);
        query.where(q -> q.eq(CommonDataDictionary.FIELD_STATUS, CommonStatusConstant.STATUS_ENABLE))
                .orderBy(CommonDataDictionary.FIELD_ID);
        return this.getRepository().selectForFirst(query);
    }

    /**
     * 查询列表
     *
     * @param condition 条件
     * @return
     */
    private List<CommonDataDictionary> queryByList(Consumer<EntityQueryWrapper<CommonDataDictionary>> condition) {
        EntityQueryWrapper<CommonDataDictionary> query = this.createQuery(condition);
        query.orderBy(CommonDataDictionary.FIELD_SORT_ID).orderBy(CommonDataDictionary.FIELD_ID);
        return this.getRepository().selectForList(query);
    }

    @Override
    public boolean isEnableCache() {
        return true;
    }

    @Override
    protected void clearCacheById(Long id) {
        this.clearCache();
    }

    /**
     * @param dictionaryType
     * @param key
     * @param valueLoader
     * @param <T>
     * @return
     */
    private <T> T getOrAddCache(int dictionaryType, Object key, Callable<T> valueLoader) {
        return this.getOrAddCache(dictionaryType + "_" + key.toString(), valueLoader);
    }

    @Override
    public List<CommonDataDictionary> queryForList(int dictionaryType) {
        return this.getOrAddCache(dictionaryType, "queryForList", () ->
                this.queryByList(q -> q.where()
                        .eq(CommonDataDictionary.FIELD_DICTIONARY_TYPE, dictionaryType)));
    }

    @Override
    public List<CommonDataDictionary> queryEnableForList(int dictionaryType) {
        return this.getOrAddCache(dictionaryType, "queryEnableForList", () ->
                this.queryByList(q -> q.where()
                        .eq(CommonDataDictionary.FIELD_DICTIONARY_TYPE, dictionaryType)
                        .eq(CommonDataDictionary.FIELD_STATUS, CommonStatusConstant.STATUS_ENABLE)));
    }

    @Override
    public List<CommonDataDictionaryOutput> chooseForList(int dictionaryType) {
        return this.getOrAddCache(dictionaryType, "chooseForList", () -> {
            List<CommonDataDictionary> items = this.queryEnableForList(dictionaryType);
            return AutoMapUtils.mapForList(items, CommonDataDictionaryOutput.class);
        });
    }

    @Override
    public CommonDataDictionary getEnableDictionary(long id) {
        String key = "getEnableDictionary_" + id;
        return this.getOrAddCache(key, () -> this.queryByEnableSingle(
                q -> q.where().eq(CommonDataDictionary.FIELD_ID, id)));
    }

    @Override
    public CommonDataDictionary getEnableDictionary(int dictionaryType, long id) {
        String key = "getEnableDictionaryById_" + id;
        return this.getOrAddCache(dictionaryType, key, () ->
                this.queryByEnableSingle(q -> q.where()
                        .eq(CommonDataDictionary.FIELD_ID, id)
                        .eq(CommonDataDictionary.FIELD_DICTIONARY_TYPE, dictionaryType))
        );
    }

    @Override
    public CommonDataDictionary getEnableDictionaryByName(int dictionaryType, String name) {
        String key = "getEnableDictionaryByName_" + name;
        return this.getOrAddCache(dictionaryType, key, () ->
                this.queryByEnableSingle(q -> q.where()
                        .eq(CommonDataDictionary.FIELD_NAME, name)
                        .eq(CommonDataDictionary.FIELD_DICTIONARY_TYPE, dictionaryType))
        );
    }

    @Override
    public CommonDataDictionary getEnableDictionaryByIdentification(int dictionaryType, int identification) {
        String key = "getEnableDictionaryByIdentification_" + "_" + identification;
        return this.getOrAddCache(dictionaryType, key, () ->
                this.queryByEnableSingle(q -> q.where()
                        .eq(CommonDataDictionary.FIELD_DICTIONARY_TYPE, dictionaryType)
                        .eq(CommonDataDictionary.FIELD_IDENTIFICATION, identification)));
    }

    @Override
    public CommonDataDictionary getEnableDictionaryBySystemValue(int dictionaryType, int systemValue) {
        String key = "getEnableDictionaryBySystemValue_" + systemValue;
        return this.getOrAddCache(dictionaryType, key, () ->
                this.queryByEnableSingle(q -> q.where()
                        .eq(CommonDataDictionary.FIELD_DICTIONARY_TYPE, dictionaryType)
                        .eq(CommonDataDictionary.FIELD_SYS_DICTIONARY_VALUE, systemValue)));
    }

    @Override
    public boolean existEnableDictionary(int dictionaryType, long id) {
        String key = "existEnableDictionary_" + id;
        return this.getOrAddCache(dictionaryType, key, () -> {
            EntityQueryWrapper<CommonDataDictionary> query = this.createQuery(q ->
                    q.where().eq(CommonDataDictionary.FIELD_ID, id)
                            .eq(CommonDataDictionary.FIELD_DICTIONARY_TYPE, dictionaryType)
                            .eq(CommonDataDictionary.FIELD_STATUS, CommonStatusConstant.STATUS_ENABLE));
            return this.getRepository().countByWhere(query) > 0;
        });
    }

    @Override
    public boolean existDictionaryType(int dictionaryType) {
        return this.commonDataDictionaryCallback.exist(dictionaryType);
    }

    @Override
    public FileStorageObject readFirstFile(int dictionaryType, int sysDictionaryValue, String extensionName, String configName) {
        ExceptionUtils.checkNotNullOrBlank(configName, "configName");
        CommonDataDictionary dataDictionary = this.getEnableDictionaryBySystemValue(dictionaryType, sysDictionaryValue);
        if (dataDictionary == null || !dataDictionary.getStatus().equals(CommonStatusConstant.STATUS_ENABLE)) {
            ExceptionUtils.throwValidationException("未配置" + configName + "或未启用，请到字典中进行配置。");
        }
        List<FileAttachmentInformationResponse> files = fileUploadManager
                .getFileService()
                .queryByTargetList(this.getFileUploadAttachmentType(), dataDictionary.getId());
        FileAttachmentInformationResponse queryFile = null;
        for (FileAttachmentInformationResponse file : files) {
            if (StringUtils.isNullOrBlank(extensionName)) {
                queryFile = file;
                break;
            } else {
                if (file.getExtensionName().equalsIgnoreCase(extensionName)) {
                    queryFile = file;
                    break;
                }
            }
        }
        if (queryFile == null) {
            ExceptionUtils.throwValidationException(configName + "未上传文件，请到字典中进行配置。");
        }
        FileStorageObject fileStorageObject = fileUploadManager.getFileService().getFile(queryFile.getId());
        if (fileStorageObject == null || fileStorageObject.getInputStream() == null) {
            ExceptionUtils.throwValidationException(configName + "未上传文件或已出错，请到字典中进行重新上传。");
        }
        return fileStorageObject;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateStatus(StatusInput<Long> input) {
        ExceptionUtils.checkNotNull(input, "input");
        input.valid();
        if (!CommonStatusConstant.exist(input.getStatus())) {
            ExceptionUtils.throwValidationException("无效的状态或不支持。");
        }
        CommonDataDictionary entity = this.getEntityById(input.getId(), LockModeEnum.UPDATE);
        if (entity == null) {
            ExceptionUtils.throwValidationException("指定字典不存在，无法更新状态。");
        }
        if (entity.getStatus().equals(input.getStatus())) {
            return;
        }
        this.updateInitialize(entity);
        entity.setStatus(input.getStatus());
        this.getRepository().update(entity);
        this.getAuditedLogger().addLog(this.customModuleName(entity), "更新状态", CommonStatusConstant.getName(entity.getStatus()));
        this.clearCache();
    }
}
