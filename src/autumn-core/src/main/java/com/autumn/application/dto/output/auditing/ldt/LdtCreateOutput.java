package com.autumn.application.dto.output.auditing.ldt;

import com.autumn.annotation.FriendlyProperty;
import com.autumn.application.dto.EntityDto;
import com.autumn.domain.entities.auditing.ldt.LdtCreateAuditing;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 具有新增审计的抽象
 * <p>
 * </p>
 *
 * @description TODO
 * @author: 老码农
 * @create: 2020-02-05 19:22
 **/
@ToString(callSuper = true)
@Getter
@Setter
public class LdtCreateOutput<TKey extends Serializable> extends EntityDto<TKey> implements LdtCreateAuditing {

    private static final long serialVersionUID = 7197968750443194269L;

    /**
     * 创建时间
     */
    @FriendlyProperty(value = "创建时间")
    private LocalDateTime ldtCreate;
}
