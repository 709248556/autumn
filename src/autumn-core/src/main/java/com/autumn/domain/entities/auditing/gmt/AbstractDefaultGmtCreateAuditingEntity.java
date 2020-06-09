package com.autumn.domain.entities.auditing.gmt;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 64位整数主键，具有新建的审计
 * @author 老码农
 * 2019-05-12 23:52:33
 */
@ToString(callSuper = true)
@Getter
@Setter
public abstract class AbstractDefaultGmtCreateAuditingEntity extends AbstractGmtCreateAuditingEntity<Long> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8809644964125014629L;

}
