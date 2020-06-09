package com.autumn.domain.entities.auditing.ldt;

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
 * @date 2020-02-05 19:00
 */
@ToString(callSuper = true)
@Getter
@Setter
public abstract class AbstractDefaultLdtModifiedAuditingEntity extends AbstractLdtModifiedEntity<Long> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2685802816238802376L;
}
