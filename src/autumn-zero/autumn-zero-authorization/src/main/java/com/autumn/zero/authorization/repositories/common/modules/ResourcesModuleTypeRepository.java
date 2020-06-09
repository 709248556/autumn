package com.autumn.zero.authorization.repositories.common.modules;

import org.springframework.stereotype.Repository;

import com.autumn.domain.repositories.DefaultEntityRepository;
import com.autumn.zero.authorization.entities.common.modules.ResourcesModuleType;

/**
 * 资源模块类型
 * 
 * @author 老码农 2019-03-06 09:07:51
 */
@Repository
public interface ResourcesModuleTypeRepository extends DefaultEntityRepository<ResourcesModuleType> {
   
}
