package com.autumn.application.dto.output.auditing.user;

import com.autumn.annotation.FriendlyProperty;
import com.autumn.domain.entities.auditing.ldt.LdtModifiedAuditing;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 具有修改用户的审计
 * <p>
 * </p>
 *
 * @description TODO
 * @author: 老码农
 * @create: 2020-02-05 19:34
 **/
@ToString(callSuper = true)
@Getter
@Setter
public class UserLdtModifiedOutput<TKey extends Serializable> extends UserModifiedOutput<TKey>
        implements LdtModifiedAuditing {

    private static final long serialVersionUID = 3930947045729881043L;

    /**
     * 创建时间
     */
    @FriendlyProperty(value = "创建时间")
    private LocalDateTime ldtCreate;

    /**
     * 修改时间
     */
    @FriendlyProperty("修改时间")
    private LocalDateTime ldtModified;
}
