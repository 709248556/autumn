package com.autumn.domain.entities.auditing.user;

import com.autumn.domain.entities.AbstractEntity;
import com.autumn.domain.entities.auditing.AuditingConstants;
import com.autumn.mybatis.mapper.annotation.ColumnDocument;
import com.autumn.mybatis.mapper.annotation.ColumnOrder;
import com.autumn.mybatis.mapper.annotation.Index;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import java.io.Serializable;

/**
 * 用户创建审计
 * <p>
 * </p>
 *
 * @param <TKey> 主键类型
 * @description TODO
 * @author: 老码农
 * @create: 2020-02-05 18:15
 **/
@ToString(callSuper = true)
@Getter
@Setter
public abstract class AbstractUserCreateAuditingEntity<TKey extends Serializable> extends AbstractEntity<TKey> implements UserCreateAuditing {

    private static final long serialVersionUID = -4347762554936318344L;

    /**
     * 创建用户id
     */
    @ColumnOrder(20000)
    @Column(name = COLUMN_CREATED_USER_ID, nullable = true)
    @Index
    @ColumnDocument(AuditingConstants.CN_NAME_CREATED_USER_ID)
    private Long createdUserId;

    /**
     * 创建用户名称
     */
    @ColumnOrder(20001)
    @Length(max = MAX_AUDITING_USER_NAME, message = "创建用户名称 不能超过 " + MAX_AUDITING_USER_NAME + " 个字。")
    @Column(name = COLUMN_CREATED_USER_NAME, nullable = false, length = MAX_AUDITING_USER_NAME)
    @ColumnDocument(AuditingConstants.CN_NAME_CREATED_USER_NAME)
    private String createdUserName;
}
