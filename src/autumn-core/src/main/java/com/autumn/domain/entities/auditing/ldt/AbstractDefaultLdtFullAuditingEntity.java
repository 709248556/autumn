package com.autumn.domain.entities.auditing.ldt;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 具有完全时间审计抽象
 *
 * @author 老码农
 * @date 2020-02-05 19:00
 */
@ToString(callSuper = true)
@Getter
@Setter
public abstract class AbstractDefaultLdtFullAuditingEntity extends AbstractLdtFullAuditingEntity<Long> {

    /**
     *
     */
    private static final long serialVersionUID = 6971167608368223028L;

}
