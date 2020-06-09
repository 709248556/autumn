package com.autumn.application.dto.output.auditing.ldt;

import com.autumn.annotation.FriendlyProperty;
import com.autumn.domain.entities.auditing.ldt.LdtModifiedAuditing;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 具修改审计输出
 * <p>
 * </p>
 *
 * @description TODO
 * @author: 老码农
 * @create: 2020-02-05 19:25
 **/
@ToString(callSuper = true)
@Getter
@Setter
public class LdtModifiedOutput<TKey extends Serializable> extends LdtCreateOutput<TKey> implements LdtModifiedAuditing {
    private static final long serialVersionUID = -1147517141122039756L;

    /**
     * 修改时间
     */
    @FriendlyProperty("修改时间")
    private LocalDateTime ldtModified;
}
