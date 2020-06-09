package com.autumn.zero.authorization.entities.common;

import com.autumn.audited.annotation.LogMessage;
import com.autumn.domain.entities.AbstractDefaultEntity;
import com.autumn.mybatis.mapper.annotation.ColumnDocument;
import com.autumn.mybatis.mapper.annotation.ColumnOrder;
import com.autumn.mybatis.mapper.annotation.ColumnType;
import com.autumn.mybatis.mapper.annotation.Index;
import com.autumn.validation.annotation.NotNullOrBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.ibatis.type.JdbcType;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;

/**
 * 声明抽象
 * 
 * @author 老码农 2018-11-25 00:51:34
 */
@ToString(callSuper = true)
@Setter
@Getter
public abstract class AbstractClaim extends AbstractDefaultEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3825266046102144730L;

	/**
	 * 最大声明类型长度
	 */
	public static final int MAX_CLAIM_TYPE_LENGTH = 255;
	
	/**
	 * 字段 claimType
	 */
	public static final String FIELD_CLAIM_TYPE= "claimType";
	
	/**
	 * 字段 claimValue
	 */
	public static final String FIELD_CLAIM_VALUE= "claimValue";

	/**
	 * 声明类型
	 */
	@NotNullOrBlank(message = "用户声明类型不能为空")
	@Length(max = MAX_CLAIM_TYPE_LENGTH, message = "用户声明类型长度不能超过" + MAX_CLAIM_TYPE_LENGTH + "个字。")
	@Column(name = "claim_type", nullable = false, length = MAX_CLAIM_TYPE_LENGTH)
	@ColumnType(jdbcType = JdbcType.VARCHAR)
	@Index
	@ColumnOrder(10)
	@LogMessage(name = "声明类型", order = 1)
	@ColumnDocument("声明类型")
	private String claimType;

	/**
	 * 声明值
	 */
	@NotNullOrBlank(message = "声明值不能为空")
	@Column(name = "claim_value", nullable = false)
	@ColumnType(jdbcType = JdbcType.LONGVARCHAR)
	@ColumnOrder(11)
	@LogMessage(name = "声明值", order = 2)
	@ColumnDocument("声明值")
	private String claimValue;
}
