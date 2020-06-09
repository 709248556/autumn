package com.autumn.zero.authorization.configure;

import com.autumn.mybatis.mapper.EntityCustomKeyGenerationTypeMapper;
import com.autumn.zero.authorization.entities.common.*;
import com.autumn.zero.authorization.entities.common.log.UserLoginLog;
import com.autumn.zero.authorization.entities.common.log.UserOperationLog;
import com.autumn.zero.authorization.entities.common.modules.ResourcesModulePermission;
import com.autumn.zero.authorization.entities.defaulted.DefaultRole;
import com.autumn.zero.authorization.entities.defaulted.DefaultUser;

import javax.persistence.GenerationType;
import java.util.HashMap;
import java.util.Map;

/**
 * 授权自定义实体主键生成器
 * <p>
 * </p>
 *
 * @description: TODO
 * @author: 老码农
 * @create: 2019-12-06 19:54
 **/
public class AuthorizationEntityCustomKeyGenerationTypeMapperConfigure implements EntityCustomKeyGenerationTypeMapper {

    private final GenerationType generationType;

    public AuthorizationEntityCustomKeyGenerationTypeMapperConfigure(GenerationType generationType) {
        this.generationType = generationType;
    }

    @Override
    public Map<Class<?>, GenerationType> bindGenerationTypeMapper() {
        Map<Class<?>, GenerationType> map = new HashMap<>(20);
        map.put(ResourcesModulePermission.class, this.generationType);
        map.put(RoleClaim.class, this.generationType);
        map.put(RolePermission.class, this.generationType);
        map.put(UserClaim.class, this.generationType);
        map.put(UserExternalAuthLogin.class, this.generationType);
        map.put(UserDeviceAuthLogin.class, this.generationType);
        map.put(UserLoginLog.class, this.generationType);
        map.put(UserOperationLog.class, this.generationType);
        map.put(UserPermission.class, this.generationType);
        map.put(DefaultUser.class, this.generationType);
        map.put(DefaultRole.class, this.generationType);
        map.put(UserRole.class, this.generationType);
        return map;
    }
}
