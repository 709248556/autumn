package com.autumn.application.dto.output.auditing.gmt;

import com.autumn.annotation.FriendlyProperty;
import com.autumn.domain.entities.auditing.gmt.GmtModifiedAuditing;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * 具修改审计输出
 * 
 * @author 老码农
 *
 *         2017-11-01 17:00:48
 */
@ToString(callSuper = true)
@Getter
@Setter
public class GmtModifiedOutput<TKey extends Serializable> extends GmtCreateOutput<TKey>
		implements GmtModifiedAuditing {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2679866921633499656L;

	/**
	 * 修改时间
	 */
	@FriendlyProperty(value = "修改时间")
	private Date gmtModified;

}
