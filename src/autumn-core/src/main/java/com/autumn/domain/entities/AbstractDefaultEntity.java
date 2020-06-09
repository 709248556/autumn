package com.autumn.domain.entities;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 抽象默认实体(64位整数的主键类型)
 * 
 * @author 老码农
 *         <p>
 *         Description
 *         </p>
 * @date 2017-12-31 21:20:14
 */
@ToString(callSuper = true)
@Getter
@Setter
public abstract class AbstractDefaultEntity extends AbstractEntity<Long> implements DefaultEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7633466100723072032L;

}
