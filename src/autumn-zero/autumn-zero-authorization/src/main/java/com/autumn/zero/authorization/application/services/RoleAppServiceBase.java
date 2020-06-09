package com.autumn.zero.authorization.application.services;

import com.autumn.application.service.EditApplicationService;
import com.autumn.zero.authorization.application.dto.PermissionDto;
import com.autumn.zero.authorization.application.dto.PermissionGrantedInput;
import com.autumn.zero.authorization.application.dto.roles.RoleDto;
import com.autumn.zero.authorization.application.dto.roles.RoleInput;
import com.autumn.zero.authorization.application.dto.roles.RoleOutput;
import com.autumn.zero.authorization.application.dto.roles.RolePermissionOutput;
import com.autumn.zero.authorization.values.ResourcesModulePermissionTreeValue;
import com.autumn.zero.file.storage.application.services.FileExportAppService;

import java.util.List;

/**
 * 角色应用服务
 *
 * @param <TAddInput>      添加输入类型
 * @param <TUpdateInput>   更新输入类型
 * @param <TOutputItem>    输出项目类型
 * @param <TOutputDetails> 输出详情类型
 * @author 老码农 2018-12-10 15:42:16
 */
public interface RoleAppServiceBase<TAddInput extends RoleInput, TUpdateInput extends RoleInput, TOutputItem extends RoleDto, TOutputDetails extends RoleOutput>
        extends EditApplicationService<Long, TAddInput, TUpdateInput, TOutputItem, TOutputDetails>, FileExportAppService {

    /**
     * 授权
     *
     * @param input 输入
     * @return
     */
    RolePermissionOutput authorize(PermissionGrantedInput input);

    /**
     * 授权查询
     *
     * @param input 输入
     * @return
     */
    RolePermissionOutput authorizeByQuery(PermissionDto input);

    /**
     * 角色授权模块树
     *
     * @param input 输入
     * @return
     */
    List<ResourcesModulePermissionTreeValue> authorizeByModulePermissionTree(PermissionDto input);

    /**
     * 角色授权模板所有权限
     *
     * @param input 输入
     * @return
     */
    void authorizeByAllPermission(PermissionDto input);
}
