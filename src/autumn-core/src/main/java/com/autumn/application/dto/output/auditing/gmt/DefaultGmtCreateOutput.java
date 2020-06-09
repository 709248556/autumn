package com.autumn.application.dto.output.auditing.gmt;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 默认具有创建审计输出
 * 
 * @author 老码农 2019-05-23 10:37:39
 */
@ToString(callSuper = true)
@Getter
@Setter
public class DefaultGmtCreateOutput extends GmtCreateOutput<Long> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2761221969334893359L;

}
