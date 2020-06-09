package com.autumn.application.dto.output.auditing.user;

import com.autumn.annotation.FriendlyProperty;
import com.autumn.domain.entities.auditing.user.UserModifiedAuditing;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * 具有修改用户的审计
 * <p>
 * </p>
 *
 * @description TODO
 * @author: 老码农
 * @create: 2020-02-05 19:28
 **/
@ToString(callSuper = true)
@Getter
@Setter
public class UserModifiedOutput<TKey extends Serializable> extends UserCreateOutput<TKey> implements UserModifiedAuditing {

    private static final long serialVersionUID = 8542445642388630998L;

    /**
     * 最后修改用户id
     */
    @FriendlyProperty(value = "最后修改用户id")
    private Long modifiedUserId;

    /**
     * 最后修改用户名称
     */
    @FriendlyProperty(value = "最后修改用户名称")
    private String modifiedUserName;
}
