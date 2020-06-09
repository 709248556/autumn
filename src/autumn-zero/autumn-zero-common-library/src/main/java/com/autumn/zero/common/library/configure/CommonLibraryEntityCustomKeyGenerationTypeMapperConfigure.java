package com.autumn.zero.common.library.configure;

import com.autumn.mybatis.mapper.EntityCustomKeyGenerationTypeMapper;
import com.autumn.zero.common.library.entities.common.CommonDataDictionary;
import com.autumn.zero.common.library.entities.common.Region;
import com.autumn.zero.common.library.entities.personal.UserPersonalInvoiceAddress;
import com.autumn.zero.common.library.entities.personal.UserPersonalReceivingAddress;
import com.autumn.zero.common.library.entities.sys.BusinessAgreement;
import com.autumn.zero.common.library.entities.sys.SystemCommonConfig;
import com.autumn.zero.common.library.entities.sys.SystemHelpDocument;

import javax.persistence.GenerationType;
import java.util.HashMap;
import java.util.Map;

/**
 * 公区库自定义实体主键生成器
 * <p>
 * </p>
 *
 * @description: TODO
 * @author: 老码农
 * @create: 2019-12-06 20:03
 **/
public class CommonLibraryEntityCustomKeyGenerationTypeMapperConfigure implements EntityCustomKeyGenerationTypeMapper {

    private final GenerationType generationType;

    public CommonLibraryEntityCustomKeyGenerationTypeMapperConfigure(GenerationType generationType){
        this.generationType = generationType;
    }

    @Override
    public Map<Class<?>, GenerationType> bindGenerationTypeMapper() {
        Map<Class<?>, GenerationType> map = new HashMap<>(20);
        map.put(CommonDataDictionary.class, this.generationType);
        map.put(Region.class, this.generationType);
        map.put(SystemCommonConfig.class, this.generationType);
        map.put(SystemHelpDocument.class, this.generationType);
        map.put(BusinessAgreement.class, this.generationType);
        map.put(UserPersonalInvoiceAddress.class, this.generationType);
        map.put(UserPersonalReceivingAddress.class, this.generationType);
        return map;
    }
}
