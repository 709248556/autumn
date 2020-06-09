package com.autumn.zero.authorization.repositories.common.modules;

import org.springframework.stereotype.Repository;

import com.autumn.domain.repositories.EntityRepository;
import com.autumn.zero.authorization.entities.common.modules.ResourcesModule;

/**
 * 资源模块仓储
 * 
 * @author 老码农 2018-12-05 16:31:56
 */
@Repository
public interface ResourcesModuleRepository extends EntityRepository<ResourcesModule, String> {
   
}
