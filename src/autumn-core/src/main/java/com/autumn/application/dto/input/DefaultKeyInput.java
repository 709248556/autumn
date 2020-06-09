package com.autumn.application.dto.input;

import lombok.ToString;

/**
 * 默认主键输入(64位整数的主键类型)
 * 
 * @author 老码农
 *         <p>
 *         Description
 *         </p>
 * @date 2017-12-31 21:38:22
 */
@ToString(callSuper = true)
public class DefaultKeyInput extends PrimaryKeyInput<Long> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7229428454509054871L;

}
