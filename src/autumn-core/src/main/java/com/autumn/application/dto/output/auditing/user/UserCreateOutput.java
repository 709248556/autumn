package com.autumn.application.dto.output.auditing.user;

import com.autumn.annotation.FriendlyProperty;
import com.autumn.application.dto.EntityDto;
import com.autumn.domain.entities.auditing.user.UserCreateAuditing;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * 具用审计用户输出
 * <p>
 * </p>
 *
 * @description TODO
 * @author: 老码农
 * @create: 2020-02-05 19:27
 **/
@ToString(callSuper = true)
@Getter
@Setter
public class UserCreateOutput<TKey extends Serializable> extends EntityDto<TKey> implements UserCreateAuditing {

    private static final long serialVersionUID = -7595907529194774163L;

    /**
     * 创建用户id
     */
    @FriendlyProperty(value = "创建用户id")
    private Long createdUserId;

    /**
     * 创建用户名称
     */
    @FriendlyProperty(value = "创建用户名称")
    private String createdUserName;
}
