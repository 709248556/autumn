package com.autumn.domain.entities.auditing.user;

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
 * 用户完整审计
 * <p>
 * </p>
 *
 * @param <TKey> 主键类型
 * @description TODO
 * @author: 老码农
 * @create: 2020-02-05 18:19
 **/
@ToString(callSuper = true)
@Getter
@Setter
public class AbstractUserFullAuditingEntity<TKey extends Serializable> extends AbstractUserModifiedAuditingEntity<TKey> implements UserFullAuditing {
    private static final long serialVersionUID = 725446200293519034L;

    /**
     * 是否删除
     */
    @ColumnOrder(10200)
    @Column(name = COLUMN_IS_DELETE, nullable = false)
    @ColumnDocument("是否删除")
    private boolean delete;

    /**
     * 删除用户Id
     */
    @ColumnOrder(20200)
    @Column(name = COLUMN_DELETED_USER_ID, nullable = true)
    @Index
    @ColumnDocument("删除用户Id")
    private Long deletedUserId;

    /**
     * 删除用户名称
     */
    @ColumnOrder(20201)
    @Length(max = MAX_AUDITING_USER_NAME, message = "删除用户名称 不能超过 " + MAX_AUDITING_USER_NAME + " 个字。")
    @Column(name = COLUMN_DELETED_USER_NAME, nullable = false, length = MAX_AUDITING_USER_NAME)
    @ColumnDocument("删除用户名称")
    private String deletedUserName;

}
