package com.autumn.zero.common.library.application.services.common;

import com.autumn.application.service.EditByStatusApplicationService;
import com.autumn.domain.values.IntegerConstantItemValue;
import com.autumn.file.storage.FileStorageObject;
import com.autumn.zero.common.library.application.dto.common.dictionary.CommonDataDictionaryDetailsOutput;
import com.autumn.zero.common.library.application.dto.common.dictionary.CommonDataDictionaryInput;
import com.autumn.zero.common.library.application.dto.common.dictionary.CommonDataDictionaryOutput;
import com.autumn.zero.common.library.entities.common.CommonDataDictionary;
import com.autumn.zero.file.storage.application.services.FileExportAppService;
import com.autumn.zero.file.storage.application.services.FileUploadAppService;

import java.util.Collection;
import java.util.List;

/**
 * 共公字典应用服务抽象
 *
 * @Description TODO
 * @Author 老码农
 * @Date 2019-07-25 18:56
 */
public interface CommonDataDictionaryAppService extends
        EditByStatusApplicationService<Long,
                CommonDataDictionaryInput,
                CommonDataDictionaryInput,
                CommonDataDictionaryOutput,
                CommonDataDictionaryDetailsOutput>,
        FileUploadAppService, FileExportAppService {

    /**
     * 字典类型列表
     *
     * @return
     */
    Collection<IntegerConstantItemValue> dictionaryTypeList();

    /**
     * 选择列表
     *
     * @param dictionaryType 字典类型
     * @return
     */
    List<CommonDataDictionaryOutput> chooseForList(int dictionaryType);

    /**
     * 查询列表
     *
     * @param dictionaryType
     * @return
     */
    List<CommonDataDictionary> queryForList(int dictionaryType);

    /**
     * 查询启用列表
     *
     * @param dictionaryType
     * @return
     */
    List<CommonDataDictionary> queryEnableForList(int dictionaryType);

    /**
     * 获取字典
     *
     * @param id 主键
     * @return
     */
    CommonDataDictionary getEnableDictionary(long id);

    /**
     * 获取字典
     *
     * @param dictionaryType 字典类型
     * @param id             主键
     * @return
     */
    CommonDataDictionary getEnableDictionary(int dictionaryType, long id);

    /**
     * 根据名称获取字典
     *
     * @param dictionaryType 字典类型
     * @param name           名称
     * @return
     */
    CommonDataDictionary getEnableDictionaryByName(int dictionaryType, String name);

    /**
     * 根据字典类型与标识获取启用字典
     *
     * @param dictionaryType 字典类型
     * @param identification 标识
     * @return
     */
    CommonDataDictionary getEnableDictionaryByIdentification(int dictionaryType, int identification);

    /**
     * 根据字典类型与系统值获取启用字典
     *
     * @param dictionaryType 字典类型
     * @param systemValue    系统值
     * @return
     */
    CommonDataDictionary getEnableDictionaryBySystemValue(int dictionaryType, int systemValue);

    /**
     * 存在字典
     *
     * @param dictionaryType 字典类型
     * @param id
     * @return
     */
    boolean existEnableDictionary(int dictionaryType, long id);

    /**
     * 存在字典类型
     *
     * @param dictionaryType 字典类型
     * @return
     */
    boolean existDictionaryType(int dictionaryType);

    /**
     * 读取首个文件
     *
     * @param dictionaryType     字典类型
     * @param sysDictionaryValue 系统值
     * @param extensionName      扩展名称
     * @param configName         配置名称(用于消息提示)
     * @return
     */
    FileStorageObject readFirstFile(int dictionaryType, int sysDictionaryValue, String extensionName, String configName);
}
