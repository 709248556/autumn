package com.autumn.application.dto.output.auditing.gmt;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 默认具有修改审计的输出(64位整数的主键类型)
 * 
 * @author 老码农
 *         <p>
 *         Description
 *         </p>
 * @date 2017-12-31 21:46:28
 */
@ToString(callSuper = true)
@Getter
@Setter
public class DefaultGmtModifiedOutput extends GmtModifiedOutput<Long> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9016514971164313348L;

}
