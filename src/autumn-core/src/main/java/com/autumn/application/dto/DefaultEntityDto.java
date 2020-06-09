package com.autumn.application.dto;

import lombok.ToString;

/**
 * 默认实体 Dto(64位整数的主键类型)
 * 
 * @author 老码农
 *         <p>
 *         Description
 *         </p>
 * @date 2017-12-31 21:35:44
 */
@ToString(callSuper = true)
public class DefaultEntityDto extends EntityDto<Long> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7003933243257145254L;

}
