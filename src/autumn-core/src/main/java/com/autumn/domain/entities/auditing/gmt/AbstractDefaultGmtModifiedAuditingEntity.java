package com.autumn.domain.entities.auditing.gmt;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 64位整数主键，具有新建和修改的审计
 * 
 * @author 老码农
 *         <p>
 *         Description
 *         </p>
 * @date 2017-12-31 17:32:46
 */
@ToString(callSuper = true)
@Getter
@Setter
public abstract class AbstractDefaultGmtModifiedAuditingEntity extends AbstractGmtModifiedEntity<Long> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2685802816238802376L;
}
