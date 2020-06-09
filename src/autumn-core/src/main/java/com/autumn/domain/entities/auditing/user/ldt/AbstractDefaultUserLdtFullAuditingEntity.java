package com.autumn.domain.entities.auditing.user.ldt;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 具有完全用户审计抽象
 * 
 * @author 老码农 2019-05-13 00:14:06
 */
@ToString(callSuper = true)
@Getter
@Setter
public abstract class AbstractDefaultUserLdtFullAuditingEntity extends AbstractUserLdtFullAuditingEntity<Long> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7210034890931005445L;

}
