package com.autumn.zero.authorization.plugins.impl;

import com.autumn.zero.authorization.plugins.AbstractResourcesModulePlugin;
import com.autumn.zero.authorization.plugins.data.ResourcesModuleData;
import com.autumn.zero.authorization.res.AuthorizationResUtls;

import java.util.List;

/**
 * 授权资源模块插件
 * <p>
 * </p>
 *
 * @description TODO
 * @author: 老码农
 * @create: 2020-04-18 19:33
 **/
public class AuthorizationCommonModuleMenuPluginImpl extends AbstractResourcesModulePlugin {

    @Override
    public List<ResourcesModuleData> createModules() {
        // select replace( CONCAT('{"name": "' , name  , '","friendlyName":"',friendly_name,'","sortId":', sort_id, ',"permissionUrl":"',permission_url,'","summary":"',summary, '"}'),'\n','|') as json
        // from sys_res_module_permission WHERE resources_id = 'sys_log_login' ORDER BY sort_id ASC
        return AuthorizationResUtls.readResourcesModuleDatas();
    }

    @Override
    public String getName() {
        return "autumnAuthorizationResourcesModuleMenu";
    }

    @Override
    public String getDescribe() {
        return "Autumn 授权资源与菜单";
    }
}
