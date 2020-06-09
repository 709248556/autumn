package com.autumn.application.dto.output.auditing.user;

import com.autumn.annotation.FriendlyProperty;
import com.autumn.domain.entities.auditing.ldt.LdtCreateAuditing;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 具用审计用户输出
 * <p>
 * </p>
 *
 * @description TODO
 * @author: 老码农
 * @create: 2020-02-05 19:33
 **/
@ToString(callSuper = true)
@Getter
@Setter
public class UserLdtCreateOutput<TKey extends Serializable> extends UserCreateOutput<TKey>
        implements LdtCreateAuditing {

    private static final long serialVersionUID = 1945172773885342317L;

    /**
     * 创建时间
     */
    @FriendlyProperty(value = "创建时间")
    private LocalDateTime ldtCreate;
}
