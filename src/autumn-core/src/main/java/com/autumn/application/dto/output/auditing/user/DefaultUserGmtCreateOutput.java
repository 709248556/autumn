package com.autumn.application.dto.output.auditing.user;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 默认具有创建用户审计输出
 * 
 * @author 老码农 2019-05-23 10:38:49
 */
@ToString(callSuper = true)
@Getter
@Setter
public class DefaultUserGmtCreateOutput extends UserGmtCreateOutput<Long> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8959359063557446410L;

}
