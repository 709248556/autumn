package com.autumn.zero.authorization.application.services.auth;

import com.autumn.application.service.ApplicationService;
import com.autumn.zero.authorization.application.dto.auth.UpdatePasswordInput;
import com.autumn.zero.authorization.application.dto.auth.UserLoginInfoOutput;
import com.autumn.zero.authorization.application.dto.auth.UserLoginRequestOutput;
import com.autumn.zero.authorization.application.dto.auth.UserNamePasswordLoingInput;
import com.autumn.zero.authorization.entities.common.AbstractUser;
import com.autumn.zero.authorization.values.ResourcesModuleTreeValue;

import java.util.List;

/**
 * 身份认证应服务抽象
 *
 * @param <TUser>          用户类型
 * @param <TUserLoginInfo> 用户登录信息
 * @Description TODO
 * @Author 老码农
 * @Date 2019-07-26 16:01
 */
public interface AuthAppServiceBase<TUser extends AbstractUser, TUserLoginInfo extends UserLoginInfoOutput> extends ApplicationService {

    /**
     * 创建注册用户
     *
     * @return
     */
    TUser createRegisterUser();

    /**
     * 创建可用的随机用户名称
     *
     * @return
     */
    String createAvailableRandomUserName();

    /**
     * 登录请求
     *
     * @return
     */
    UserLoginRequestOutput loginRequest();

    /**
     * 登录
     *
     * @param input 输入
     * @return
     */
    TUserLoginInfo login(UserNamePasswordLoingInput input);

    /**
     * 是否存在账户
     *
     * @param account 账号(手机号、用户名、邮箱）
     * @return
     */
    boolean existUserByAccount(String account);

    /**
     * 注销
     */
    void logout();

    /**
     * 登录用户信息
     *
     * @return
     */
    TUserLoginInfo loginInfo();

    /**
     * 更新密码
     *
     * @param input 输入
     */
    void updatePassword(UpdatePasswordInput input);

    /**
     * 查询用户菜单
     *
     * @return
     */
    List<ResourcesModuleTreeValue> queryUserMenuTree();
}
