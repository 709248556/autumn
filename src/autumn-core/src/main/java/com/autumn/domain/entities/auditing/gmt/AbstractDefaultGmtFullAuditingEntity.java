package com.autumn.domain.entities.auditing.gmt;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 具有完全时间审计抽象
 * 
 * @author 老码农
 *
 *         2017-10-30 17:22:34
 */
@ToString(callSuper = true)
@Getter
@Setter
public abstract class AbstractDefaultGmtFullAuditingEntity extends AbstractGmtFullAuditingEntity<Long> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6971167608368223028L;

}
