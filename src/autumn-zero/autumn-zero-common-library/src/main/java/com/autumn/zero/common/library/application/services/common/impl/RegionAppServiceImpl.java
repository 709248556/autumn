package com.autumn.zero.common.library.application.services.common.impl;

import com.autumn.application.dto.input.DataImportOptionInput;
import com.autumn.application.dto.input.ExcelImportOptionInput;
import com.autumn.application.service.DataImportApplicationService;
import com.autumn.exception.ExceptionUtils;
import com.autumn.mybatis.wrapper.EntityQueryWrapper;
import com.autumn.util.PinYinUtils;
import com.autumn.util.StringUtils;
import com.autumn.util.data.DataImportUtils;
import com.autumn.zero.common.library.application.dto.common.region.RegionImportTemplateDto;
import com.autumn.zero.common.library.application.dto.common.region.RegionInput;
import com.autumn.zero.common.library.application.dto.common.region.RegionOutput;
import com.autumn.zero.common.library.application.dto.tree.input.ChildrenPinyinSortQueryInput;
import com.autumn.zero.common.library.application.dto.tree.input.ChildrenQueryInput;
import com.autumn.zero.common.library.application.services.common.AbstractSimpleTreeCodeAppService;
import com.autumn.zero.common.library.application.services.common.RegionAppService;
import com.autumn.zero.common.library.constants.CommonStatusConstant;
import com.autumn.zero.common.library.entities.AbstractTreeEntity;
import com.autumn.zero.common.library.entities.common.Region;
import com.autumn.zero.common.library.repositories.common.RegionRepository;
import com.autumn.zero.file.storage.FileStorageUtils;
import com.autumn.zero.file.storage.application.dto.TemporaryFileInformationDto;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;
import java.util.*;

/**
 * 行政区应用服务实现
 *
 * @Description TODO
 * @Author 老码农
 * @Date 2019-08-04 21:10
 */
public class RegionAppServiceImpl extends AbstractSimpleTreeCodeAppService<Region, RegionRepository, RegionInput, RegionOutput>
        implements RegionAppService {

    /**
     * 省/市/县/镇/村/组
     */
    private static final int MAX_LEVEL = 6;

    /**
     *
     */
    public RegionAppServiceImpl() {
        this.getSearchMembers().add(Region.FIELD_FIRST_PINYIN);
        this.getSearchMembers().add(Region.FIELD_PINYIN);
    }

    private String getFirstPinyin(String name) {
        return StringUtils.getLeft(PinYinUtils.getFirstPinyinString(name, false, false), Region.MAX_LENGTH_FIRST_PINYIN);
    }

    private String getPinyin(String name) {
        return StringUtils.getLeft(PinYinUtils.getPinyinString(name, false, false), Region.MAX_LENGTH_PINYIN);
    }

    private void setFirstPinyin(RegionInput input, Region entity) {
        input.setFirstPinyin(this.getFirstPinyin(input.getName()));
        entity.setFirstPinyin(input.getFirstPinyin());
    }

    private void setPinyin(RegionInput input, Region entity) {
        input.setPinyin(this.getPinyin(input.getName()));
        entity.setPinyin(input.getPinyin());
    }

    @Override
    protected void editCheck(RegionInput input, Region entity, Region prarentEntity, boolean isAddNew) {
        super.editCheck(input, entity, prarentEntity, isAddNew);
        if (StringUtils.isNullOrBlank(input.getFirstPinyin())) {
            this.setFirstPinyin(input, entity);
        } else {
            if (!isAddNew) {
                if (entity.getFirstPinyin().equalsIgnoreCase(input.getFirstPinyin().trim())
                        && entity.getName().equalsIgnoreCase(input.getName().trim())) {
                    this.setFirstPinyin(input, entity);
                }
            }
        }
        if (StringUtils.isNullOrBlank(input.getPinyin())) {
            this.setPinyin(input, entity);
        } else {
            if (!isAddNew) {
                if (entity.getPinyin().equalsIgnoreCase(input.getPinyin().trim())
                        && entity.getName().equalsIgnoreCase(input.getName().trim())) {
                    this.setPinyin(input, entity);
                }
            }
        }
    }

    @Override
    protected void sortQueryChildren(ChildrenQueryInput input, EntityQueryWrapper<Region> wrapper) {
        if (input instanceof ChildrenPinyinSortQueryInput) {
            ChildrenPinyinSortQueryInput sortQueryInput = (ChildrenPinyinSortQueryInput) input;
            if (sortQueryInput.isPinyinSort()) {
                wrapper.orderBy(Region.FIELD_FIRST_PINYIN)
                        .orderBy(Region.FIELD_SORT_ID)
                        .orderBy(AbstractTreeEntity.FIELD_ID);
            } else {
                super.sortQueryChildren(input, wrapper);
            }
        } else {
            super.sortQueryChildren(input, wrapper);
        }
    }

    @Override
    protected int getMaxLevel() {
        return MAX_LEVEL;
    }

    @Override
    public String getModuleId() {
        return "Region";
    }

    @Override
    public String getModuleName() {
        return "行政区";
    }

    @Override
    public Class<RegionImportTemplateDto> getImportTemplateClass() {
        return RegionImportTemplateDto.class;
    }

    @Override
    public Workbook excelImportTemplate() {
        return DataImportUtils.excelImportTemplate(this);
    }

    @Override
    public TemporaryFileInformationDto excelImportTemplateForFileInformation() {
        return FileStorageUtils.createExcelImportTemplateForFileInformation(this, this.fileUploadManager);
    }

    @Override
    public int getImportPageSize() {
        return DataImportApplicationService.DEFAULT_IMPORT_PAGE_SIZE;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int excelImport(InputStream inputStream, ExcelImportOptionInput option) {
        return DataImportUtils.excelImportForUpdate(this, inputStream, option);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int dataImport(List<RegionImportTemplateDto> items, DataImportOptionInput option) {
        return this.internalDataImport(items, option);
    }

    /**
     * 内部导入数据
     *
     * @param items
     * @param option
     * @return
     */
    private int internalDataImport(List<RegionImportTemplateDto> items, DataImportOptionInput option) {
        if (items == null || items.size() == 0) {
            return 0;
        }
        //为了性能，不采用调用add操作
        Map<String, RegionImportTemplateDto> regionMap = this.checkDataImport(items);
        Map<Long, Region> parentRegionSet = new HashMap<>(items.size());
        for (RegionImportTemplateDto item : items) {
            Region region = new Region();
            region.setCode(item.getCode());
            region.setFullCode(item.getFullCode());
            region.setName(item.getName());
            region.setFullName(item.getFullName());
            region.setLevel(item.getLevel());
            region.setSortId(item.getSortId());
            region.setChildrenCount(item.getChildrenCount());
            region.setRemarks(item.getRemarks());
            if (item.getParentRegion() != null) {
                region.setParentId(item.getParentRegion().getId());
                if (!parentRegionSet.containsKey(item.getParentRegion().getId())) {
                    parentRegionSet.put(item.getParentRegion().getId(), item.getParentRegion());
                }
            } else {
                if (item.getParentDto() != null) {
                    region.setParentId(item.getParentDto().getRegion().getId());
                } else {
                    region.setParentId(null);
                }
            }
            region.setPinyin(this.getPinyin(item.getName()));
            region.setFirstPinyin(this.getFirstPinyin(item.getName()));
            region.setStatus(CommonStatusConstant.STATUS_ENABLE);
            region.setFullId("");
            this.addInitialize(region);
            region.forNullToDefault();
            this.getRepository().insert(region);
            String fullId;
            if (region.getParentId() == null) {
                fullId = Long.toString(region.getId());
            } else {
                if (item.getParentRegion() != null) {
                    fullId = item.getParentRegion().getFullId() + this.getPathSeparate() + region.getId();
                } else {
                    fullId = item.getParentDto().getRegion().getFullId() + this.getPathSeparate() + region.getId();
                }
            }
            region.setFullId(fullId);
            this.updateFullId(region);
            item.setRegion(region);
        }
        for (Region region : parentRegionSet.values()) {
            this.getRepository().update(region);
        }
        this.clearCache();
        return items.size();
    }

    /**
     * 检查导入数据
     *
     * @param items
     */
    private Map<String, RegionImportTemplateDto> checkDataImport(List<RegionImportTemplateDto> items) {
        Map<String, RegionImportTemplateDto> regionMap = new HashMap<>(items.size());
        Map<String, Region> codeRegionMap = new HashMap<>();
        Set<String> fullNameSet = new HashSet<>();
        int rootSortId = 1;
        for (int i = 0; i < items.size(); i++) {
            int row = i + 1;
            String rowMsg = "第" + row + "行的";
            RegionImportTemplateDto item = items.get(i);
            if (item == null) {
                ExceptionUtils.throwValidationException(rowMsg + "对象为null。");
            }
            try {
                item.valid();
            } catch (Exception e) {
                ExceptionUtils.throwValidationException(rowMsg + e.getMessage());
            }
            if (item.getCode().contains(this.getPathSeparate())) {
                ExceptionUtils.throwValidationException(rowMsg + "的行政区代码[" + item.getCode() + "]不能包含 " + this.getPathSeparate() + " 符号。");
            }
            if (item.getName().contains(this.getPathSeparate())) {
                ExceptionUtils.throwValidationException(rowMsg + "的行政区名称[" + item.getName() + "]不能包含 " + this.getPathSeparate() + " 符号。");
            }
            if (regionMap.containsKey(item.getCode().toUpperCase())) {
                ExceptionUtils.throwValidationException(rowMsg + "的行政区代码[" + item.getCode() + "]在当前导入数据中已重复。");
            }
            EntityQueryWrapper<Region> wrapper;
            wrapper = this.createEntityCommonWrapper();
            wrapper.where(s -> s.eq(Region.FIELD_CODE, item.getCode())).lockByUpdate();
            if (wrapper.exist(this.getRepository())) {
                ExceptionUtils.throwValidationException(rowMsg + "的行政区代码[" + item.getCode() + "]在数据库中已重复。");
            }
            Region region;
            if (!StringUtils.isNullOrBlank(item.getParentCode())) {
                RegionImportTemplateDto dto = regionMap.get(item.getParentCode().toUpperCase());
                if (dto == null) {
                    region = codeRegionMap.get(item.getParentCode().toUpperCase());
                    if (region == null) {
                        wrapper = this.createEntityCommonWrapper();
                        wrapper.where(s -> s.eq(Region.FIELD_CODE, item.getParentCode()))
                                .orderBy(Region.FIELD_ID).lockByUpdate();
                        region = this.getRepository().selectForFirst(wrapper);
                        if (region == null) {
                            ExceptionUtils.throwValidationException(rowMsg + "的父级行政区代码[" + item.getParentCode() + "]不存在。");
                        }
                        codeRegionMap.put(item.getParentCode().toUpperCase(), region);
                    }
                    item.setParentRegion(region);
                    item.setLevel(region.getLevel() + 1);
                    item.setFullCode(region.getFullCode() + this.getPathSeparate() + item.getCode());
                    item.setFullName(region.getFullName() + this.getPathSeparate() + item.getName());
                    item.setSortId(region.getChildrenCount() + 1);
                    region.setChildrenCount(region.getChildrenCount() + 1);
                } else {
                    item.setParentDto(dto);
                    item.setLevel(dto.getLevel() + 1);
                    item.setFullCode(dto.getFullCode() + this.getPathSeparate() + item.getCode());
                    item.setFullName(dto.getFullName() + this.getPathSeparate() + item.getName());
                    item.setSortId(dto.getChildrenCount() + 1);
                    dto.setChildrenCount(dto.getChildrenCount() + 1);
                }
            } else {
                item.setLevel(1);
                item.setFullCode(item.getCode());
                item.setFullName(item.getName());
                item.setSortId(rootSortId);
                rootSortId++;
            }
            item.setChildrenCount(0);
            if (this.getMaxLevel() > 0 && item.getLevel() > this.getMaxLevel()) {
                ExceptionUtils.throwValidationException(rowMsg + "的行政区名称[" + item.getName() + "],完整名称[" + item.getFullName() + "]级别太深，最大不能超过 " + this.getMaxLevel() + " 级。");
            }
            if (!fullNameSet.add(item.getFullName().toUpperCase())) {
                ExceptionUtils.throwValidationException(rowMsg + "的行政区名称[" + item.getName() + "],完整名称[" + item.getFullName() + "]在当前导入数据中已重复。");
            }
            wrapper = this.createEntityCommonWrapper();
            wrapper.where(s -> s.eq(Region.FIELD_FULL_NAME, item.getFullName())).lockByUpdate();
            if (wrapper.exist(this.getRepository())) {
                ExceptionUtils.throwValidationException(rowMsg + "的行政区名称[" + item.getName() + "],完整名称[" + item.getFullName() + "]在数据库中已重复。");
            }
            regionMap.put(item.getCode().toUpperCase(), item);
        }
        return regionMap;
    }


}
