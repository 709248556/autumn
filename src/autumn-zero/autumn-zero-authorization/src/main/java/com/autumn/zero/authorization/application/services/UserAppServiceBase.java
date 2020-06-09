package com.autumn.zero.authorization.application.services;

import com.autumn.application.service.EditApplicationService;
import com.autumn.zero.authorization.application.dto.PermissionDto;
import com.autumn.zero.authorization.application.dto.PermissionGrantedInput;
import com.autumn.zero.authorization.application.dto.modules.ResourcesTypeInput;
import com.autumn.zero.authorization.application.dto.users.*;
import com.autumn.zero.authorization.values.ResourcesModulePermissionTreeValue;
import com.autumn.zero.authorization.values.ResourcesModuleTreeValue;
import com.autumn.zero.file.storage.application.services.FileExportAppService;

import java.util.List;

/**
 * 用户应服务服务
 *
 * @param <TAddInput>      添加输入类型
 * @param <TUpdateInput>   更新输入类型
 * @param <TOutputItem>    输出项目类型
 * @param <TOutputDetails> 输出详情类型
 * @author 老码农 2018-12-10 15:42:03
 */
public interface UserAppServiceBase<TAddInput extends UserAddInput, TUpdateInput extends UserInput, TOutputItem extends UserOutput, TOutputDetails extends UserDetailsOutput>
        extends EditApplicationService<Long, TAddInput, TUpdateInput, TOutputItem, TOutputDetails>, FileExportAppService {

    /**
     * 重置密码
     *
     * @param input 输入
     */
    void resetPassword(ResetPasswordInput input);

    /**
     * 授权
     *
     * @param input 输入
     * @return
     */
    UserPermissionOutput authorize(PermissionGrantedInput input);

    /**
     * 授权查询
     *
     * @param input 输入
     * @return
     */
    UserPermissionOutput authorizeByQuery(PermissionDto input);

    /**
     * 用户授权模块权限树
     *
     * @param input 输入
     * @return
     */
    List<ResourcesModulePermissionTreeValue> authorizeByModulePermissionTree(PermissionDto input);

    /**
     * 查询用户菜单树
     *
     * @param input 输入
     * @return
     */
    List<ResourcesModuleTreeValue> queryUserByMenuTree(ResourcesTypeInput input);

    /**
     * 用户授权模板所有权限
     *
     * @param input 输入
     * @return
     */
    void authorizeByAllPermission(PermissionDto input);


}
