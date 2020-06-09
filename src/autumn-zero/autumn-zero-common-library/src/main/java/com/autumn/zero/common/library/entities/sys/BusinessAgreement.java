package com.autumn.zero.common.library.entities.sys;

import com.autumn.mybatis.mapper.annotation.*;
import com.autumn.validation.annotation.NotNullOrBlank;
import com.autumn.zero.common.library.entities.AbstractNameUserAuditingStatusEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.ibatis.type.JdbcType;

import javax.persistence.Column;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * 业务协议
 * <p>
 * </p>
 *
 * @description: TODO
 * @author: 老码农
 * @create: 2019-12-06 17:49
 **/
@ToString(callSuper = true)
@Getter
@Setter
@Table(name = "sys_business_agreement")
@TableDocument(value = "业务协议", group = "系统表", groupOrder = Integer.MAX_VALUE)
public class BusinessAgreement extends AbstractNameUserAuditingStatusEntity {
    private static final long serialVersionUID = -5794364777531721664L;

    /**
     * 字段 agreementType (协议类型)
     */
    public static final String FIELD_AGREEMENT_TYPE = "agreementType";

    /**
     * 字段 defaultAgreement (默认协议)
     */
    public static final String FIELD_DEFAULT_AGREEMENT = "defaultAgreement";

    /**
     * 字段 agreementContent (协议内容Html)
     */
    public static final String FIELD_AGREEMENT_CONTENT = "agreementContent";

    /**
     * 协议类型
     */
    @NotNull(message = "协议类型不能为空。")
    @Column(nullable = false)
    @ColumnOrder(1)
    @Index(unique = false)
    @ColumnDocument("协议类型")
    private Integer agreementType;

    /**
     * 默认协议
     */
    @Column(name = "is_default_agreement", nullable = false)
    @ColumnOrder(5)
    @ColumnDocument("默认协议")
    private boolean defaultAgreement;

    /**
     * 协议内容Html
     */
    @NotNullOrBlank(message = "协议内容不能为空。")
    @ColumnType(jdbcType = JdbcType.LONGNVARCHAR)
    @Column(nullable = false)
    @ColumnOrder(6)
    @ColumnDocument("协议内容")
    private String agreementContent;
}
