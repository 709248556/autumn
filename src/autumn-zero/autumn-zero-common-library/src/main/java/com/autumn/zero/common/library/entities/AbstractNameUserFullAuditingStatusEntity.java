package com.autumn.zero.common.library.entities;

import com.autumn.domain.entities.auditing.gmt.GmtDeleteAuditing;
import com.autumn.domain.entities.auditing.user.UserDeleteAuditing;
import com.autumn.mybatis.mapper.annotation.ColumnDocument;
import com.autumn.mybatis.mapper.annotation.ColumnOrder;
import com.autumn.mybatis.mapper.annotation.Index;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import java.util.Date;

/**
 * 具有完全审计名称状态实体
 *
 * @Description TODO
 * @Author 老码农
 * @Date 2019-07-25 17:30
 */
@ToString(callSuper = true)
@Getter
@Setter
public abstract class AbstractNameUserFullAuditingStatusEntity extends AbstractNameUserAuditingStatusEntity implements UserDeleteAuditing, GmtDeleteAuditing {

    private static final long serialVersionUID = 9213830194641105797L;

    @ColumnOrder(10200)
    @Column(name = COLUMN_IS_DELETE, nullable = false)
    @ColumnDocument("是否删除")
    private boolean delete;

    @ColumnOrder(10201)
    @Column(name = COLUMN_GMT_DELETE)
    @ColumnDocument("删除时间")
    private Date gmtDelete;

    @ColumnOrder(20200)
    @Column(name = COLUMN_DELETED_USER_ID, nullable = true)
    @Index
    @ColumnDocument("删除用户id")
    private Long deletedUserId;

    @ColumnOrder(20201)
    @Length(max = MAX_AUDITING_USER_NAME, message = "删除用户名称 不能超过 " + MAX_AUDITING_USER_NAME + " 个字。")
    @Column(name = COLUMN_DELETED_USER_NAME, nullable = false, length = MAX_AUDITING_USER_NAME)
    @ColumnDocument("删除用户名称")
    private String deletedUserName;
}
