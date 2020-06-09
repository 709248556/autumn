package com.autumn.domain.entities.auditing.ldt;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 64位整数主键，具有新建的审计
 *
 * @author 老码农
 * @date 2020-02-05 19:00
 */
@ToString(callSuper = true)
@Getter
@Setter
public abstract class AbstractDefaultLdtCreateAuditingEntity extends AbstractLdtCreateAuditingEntity<Long> {

    /**
     *
     */
    private static final long serialVersionUID = -8809644964125014629L;
}
