package com.autumn.domain.entities.auditing.user.gmt;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 64位整数主键，具有新建用户的审计
 * 
 * @author 老码农 2019-05-13 00:11:45
 */
@ToString(callSuper = true)
@Getter
@Setter
public abstract class AbstractDefaultUserGmtCreateAuditingEntity extends AbstractUserGmtCreateAuditingEntity<Long> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5619687841575587895L;

}
