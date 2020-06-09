package com.autumn.zero.file.storage.configure;

import com.autumn.mybatis.mapper.EntityCustomKeyGenerationTypeMapper;
import com.autumn.zero.file.storage.entities.FileAttachmentInformation;

import javax.persistence.GenerationType;
import java.util.HashMap;
import java.util.Map;

/**
 * 存储自定义键生成器
 * <p>
 * </p>
 *
 * @description: TODO
 * @author: 老码农
 * @create: 2019-12-06 19:58
 **/
public class StorageEntityCustomKeyGenerationTypeMapperConfigure  implements EntityCustomKeyGenerationTypeMapper {

    private final GenerationType generationType;

    public StorageEntityCustomKeyGenerationTypeMapperConfigure(GenerationType generationType){
        this.generationType = generationType;
    }

    @Override
    public Map<Class<?>, GenerationType> bindGenerationTypeMapper() {
        Map<Class<?>, GenerationType> map = new HashMap<>(20);
        map.put(FileAttachmentInformation.class, this.generationType);
        return map;
    }

}
