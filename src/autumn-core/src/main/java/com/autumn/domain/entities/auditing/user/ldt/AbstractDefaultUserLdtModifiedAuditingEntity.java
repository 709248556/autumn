package com.autumn.domain.entities.auditing.user.ldt;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 64位整数主键，具有新建和修改用户的审计
 *
 * @author 老码农 2019-05-13 00:13:31
 */
@ToString(callSuper = true)
@Getter
@Setter
public abstract class AbstractDefaultUserLdtModifiedAuditingEntity extends AbstractUserLdtModifiedAuditingEntity<Long> {

    /**
     *
     */
    private static final long serialVersionUID = -3194769704317063011L;
}
