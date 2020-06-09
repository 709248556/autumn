package com.autumn.zero.common.library.application.services.sys.impl;

import com.autumn.application.dto.input.StatusInput;
import com.autumn.domain.values.IntegerConstantItemValue;
import com.autumn.exception.ExceptionUtils;
import com.autumn.mybatis.wrapper.EntityQueryWrapper;
import com.autumn.mybatis.wrapper.EntityUpdateWrapper;
import com.autumn.util.AutoMapUtils;
import com.autumn.zero.common.library.application.callback.BusinessAgreementCallback;
import com.autumn.zero.common.library.application.dto.sys.agreement.BusinessAgreementClientOutput;
import com.autumn.zero.common.library.application.dto.sys.agreement.BusinessAgreementDetailsOutput;
import com.autumn.zero.common.library.application.dto.sys.agreement.BusinessAgreementInput;
import com.autumn.zero.common.library.application.dto.sys.agreement.BusinessAgreementListOutput;
import com.autumn.zero.common.library.application.services.sys.BusinessAgreementAppService;
import com.autumn.zero.common.library.constants.CommonStatusConstant;
import com.autumn.zero.common.library.entities.sys.BusinessAgreement;
import com.autumn.zero.common.library.repositories.sys.BusinessAgreementRepository;
import com.autumn.zero.file.storage.application.services.AbstractZeroFileExportEditAppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

/**
 * 业务协议实现
 * <p>
 * </p>
 *
 * @description: TODO
 * @author: 老码农
 * @create: 2019-12-06 18:36
 **/
public class BusinessAgreementAppServiceImpl extends AbstractZeroFileExportEditAppService<BusinessAgreement,
        BusinessAgreementRepository,
        BusinessAgreement,
        BusinessAgreementRepository,
        BusinessAgreementInput,
        BusinessAgreementInput,
        BusinessAgreementListOutput,
        BusinessAgreementDetailsOutput>
        implements BusinessAgreementAppService {

    @Autowired
    private BusinessAgreementCallback agreementCallback;

    public BusinessAgreementAppServiceImpl() {
        this.getSearchMembers().add(BusinessAgreement.FIELD_NAME);
    }

    @Override
    public String getModuleId() {
        return agreementCallback.getModuleId();
    }

    @Override
    public String getModuleName() {
        return agreementCallback.getModuleName();
    }

    @Override
    public boolean isEnableCache() {
        return true;
    }

    @Override
    protected void clearCacheById(Long id) {
        this.clearCache();
    }

    private void checkInput(BusinessAgreementInput input, boolean checkName) {
        if (!agreementCallback.exist(input.getAgreementType())) {
            ExceptionUtils.throwValidationException("指定的协议类型不存在。");
        }
        if (checkName) {
            EntityQueryWrapper<BusinessAgreement> wrapper = new EntityQueryWrapper<>(BusinessAgreement.class);
            wrapper.where().eq(BusinessAgreement.FIELD_NAME, input.getName().trim());
            if (this.getRepository().countByWhere(wrapper) > 0) {
                ExceptionUtils.throwValidationException("名称[" + input.getName() + "]已重复。");
            }
        }
    }

    @Override
    protected BusinessAgreement addBefore(BusinessAgreementInput input, EntityQueryWrapper<BusinessAgreement> query) {
        this.checkInput(input, true);
        return super.addBefore(input, query);
    }

    @Override
    protected BusinessAgreementDetailsOutput addAfter(BusinessAgreementInput input, BusinessAgreement entity, EntityQueryWrapper<BusinessAgreement> query) {
        BusinessAgreementDetailsOutput result = super.addAfter(input, entity, query);
        this.updateDefault(entity);
        return result;
    }

    @Override
    protected void updateBefore(BusinessAgreementInput input, BusinessAgreement entity, EntityQueryWrapper<BusinessAgreement> query) {
        this.checkInput(input, !entity.getName().equalsIgnoreCase(input.getName().trim()));
        super.updateBefore(input, entity, query);
    }

    @Override
    protected BusinessAgreementDetailsOutput updateAfter(BusinessAgreementInput input, BusinessAgreement entity, BusinessAgreement oldEntity, EntityQueryWrapper<BusinessAgreement> query) {
        BusinessAgreementDetailsOutput result = super.updateAfter(input, entity, oldEntity, query);
        this.updateDefault(entity);
        return result;
    }

    /**
     * 更新默认
     *
     * @param entity
     */
    private void updateDefault(BusinessAgreement entity) {
        if (entity.isDefaultAgreement()) {
            EntityUpdateWrapper<BusinessAgreement> wrapper = new EntityUpdateWrapper<>(BusinessAgreement.class);
            wrapper.where().notEq(BusinessAgreement.FIELD_ID, entity.getId())
                    .eq(BusinessAgreement.FIELD_AGREEMENT_TYPE, entity.getAgreementType())
                    .of().set(BusinessAgreement.FIELD_DEFAULT_AGREEMENT, false);
            this.getRepository().updateByWhere(wrapper);
        }
    }

    @Override
    protected BusinessAgreementDetailsOutput toOutputByQuery(BusinessAgreement businessAgreement) {
        BusinessAgreementDetailsOutput result = super.toOutputByQuery(businessAgreement);
        if (result != null) {
            result.setAgreementTypeName(agreementCallback.getName(result.getAgreementType()));
        }
        return result;
    }

    @Override
    protected void itemConvertHandle(BusinessAgreement source, BusinessAgreementListOutput target) {
        super.itemConvertHandle(source, target);
        target.setAgreementTypeName(agreementCallback.getName(target.getAgreementType()));
    }

    @Override
    public BusinessAgreementClientOutput queryAgreementById(Long id) {
        BusinessAgreementDetailsOutput result = this.queryById(id);
        if (result == null || !result.getStatus().equals(CommonStatusConstant.STATUS_ENABLE)) {
            return null;
        }
        return AutoMapUtils.map(result, BusinessAgreementClientOutput.class);
    }

    @Override
    public BusinessAgreementClientOutput queryDefaultAgreement(Integer agreementType) {
        ExceptionUtils.checkNotNull(agreementType, "agreementType");
        String key = "query_default_agreement_agreement_type_" + agreementType;
        return this.getOrAddCache(key, () -> {
            EntityQueryWrapper<BusinessAgreement> wrapper = new EntityQueryWrapper<>(BusinessAgreement.class);
            wrapper.where().eq(BusinessAgreement.FIELD_STATUS, CommonStatusConstant.STATUS_ENABLE)
                    .eq(BusinessAgreement.FIELD_AGREEMENT_TYPE, agreementType)
                    .eq(BusinessAgreement.FIELD_DEFAULT_AGREEMENT, true)
                    .of().orderBy(BusinessAgreement.FIELD_ID);
            BusinessAgreement entity = this.getRepository().selectForFirst(wrapper);
            return AutoMapUtils.map(entity, BusinessAgreementClientOutput.class);
        });
    }

    @Override
    public Collection<IntegerConstantItemValue> agreementTypeList() {
        return agreementCallback.items();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateStatus(StatusInput<Long> input) {
        ExceptionUtils.checkNotNull(input, "input");
        input.valid();
        if (!CommonStatusConstant.exist(input.getStatus())) {
            ExceptionUtils.throwValidationException("指定的状态无效。");
        }
        EntityUpdateWrapper<BusinessAgreement> wrapper = new EntityUpdateWrapper<>(BusinessAgreement.class);
        wrapper.where().eq(BusinessAgreement.FIELD_ID, input.getId())
                .of().set(BusinessAgreement.FIELD_STATUS, input.getStatus());
        if (this.getRepository().updateByWhere(wrapper) == 0) {
            ExceptionUtils.throwValidationException("指定的业务协议不存在。");
        }
        this.clearCacheById(input.getId());
    }
}
