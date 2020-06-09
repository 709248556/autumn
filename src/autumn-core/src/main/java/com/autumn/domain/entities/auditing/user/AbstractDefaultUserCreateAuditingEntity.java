package com.autumn.domain.entities.auditing.user;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 64位整数主键，具有新建用户的审计
 * <p>
 * </p>
 *
 * @description TODO
 * @author: 老码农
 * @create: 2020-02-05 18:40
 **/
@ToString(callSuper = true)
@Getter
@Setter
public abstract class AbstractDefaultUserCreateAuditingEntity extends AbstractUserCreateAuditingEntity<Long> {

    private static final long serialVersionUID = 4289082033777032623L;


}
