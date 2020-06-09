package com.autumn.domain.entities.auditing.user;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 具有完全用户审计抽象
 * <p>
 * </p>
 *
 * @description TODO
 * @author: 老码农
 * @create: 2020-02-05 18:44
 **/
@ToString(callSuper = true)
@Getter
@Setter
public abstract class AbstractDefaultUserFullAuditingEntity extends AbstractUserFullAuditingEntity<Long> {

    private static final long serialVersionUID = 3801808430114058423L;
}
