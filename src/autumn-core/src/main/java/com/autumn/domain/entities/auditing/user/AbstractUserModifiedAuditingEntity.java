package com.autumn.domain.entities.auditing.user;

import com.autumn.annotation.FriendlyProperty;
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
 * 用户修改审计
 * <p>
 * </p>
 *
 * @param <TKey> 主键类型
 * @description TODO
 * @author: 老码农
 * @create: 2020-02-05 18:18
 **/
@ToString(callSuper = true)
@Getter
@Setter
public abstract class AbstractUserModifiedAuditingEntity<TKey extends Serializable> extends AbstractUserCreateAuditingEntity<TKey> implements UserModifiedAuditing {

    private static final long serialVersionUID = 6764820830170683529L;

    @ColumnOrder(20100)
    @Column(name = COLUMN_MODIFIED_USER_ID, nullable = true)
    @Index
    @ColumnDocument("修改用户id")
    @FriendlyProperty(value = "修改用户id")
    private Long modifiedUserId;

    @ColumnOrder(20101)
    @Length(max = MAX_AUDITING_USER_NAME, message = "修改用户名称 不能超过 " + MAX_AUDITING_USER_NAME + " 个字。")
    @Column(name = COLUMN_MODIFIED_USER_NAME, nullable = false, length = MAX_AUDITING_USER_NAME)
    @FriendlyProperty(value = "修改用户")
    @ColumnDocument("修改用户名称")
    private String modifiedUserName;

}
