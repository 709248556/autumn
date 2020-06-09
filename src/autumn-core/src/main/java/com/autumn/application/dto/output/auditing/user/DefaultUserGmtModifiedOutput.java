package com.autumn.application.dto.output.auditing.user;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 默认用户修改审计输出
 * 
 * @author 老码农 2019-05-23 10:40:12
 */
@ToString(callSuper = true)
@Getter
@Setter
public class DefaultUserGmtModifiedOutput extends UserGmtModifiedOutput<Long> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7728921878679491486L;

}
