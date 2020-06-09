package com.autumn.domain.entities.auditing.user.gmt;

import com.autumn.domain.entities.auditing.gmt.GmtCreateAuditing;
import com.autumn.domain.entities.auditing.gmt.GmtModifiedAuditing;
import com.autumn.domain.entities.auditing.user.AbstractUserModifiedAuditingEntity;
import com.autumn.mybatis.mapper.annotation.ColumnDocument;
import com.autumn.mybatis.mapper.annotation.ColumnOrder;
import com.autumn.mybatis.mapper.annotation.Index;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import java.io.Serializable;
import java.util.Date;

/**
 * 具有修改用户的审计抽象
 *
 * @param <TKey>
 * @author 老码农 2019-05-12 23:33:15
 */
@ToString(callSuper = true)
@Getter
@Setter
public abstract class AbstractUserGmtModifiedAuditingEntity<TKey extends Serializable> extends AbstractUserModifiedAuditingEntity<TKey>
        implements UserGmtModifiedAuditing, GmtCreateAuditing, GmtModifiedAuditing {

    /**
     *
     */
    private static final long serialVersionUID = -7858557187374618604L;

    @ColumnOrder(10000)
    @Column(name = COLUMN_GMT_CREATE, nullable = false)
    @Index
    @ColumnDocument("创建时间")
    private Date gmtCreate;

    /**
     * 修改时间
     */
    @ColumnOrder(10100)
    @Column(name = COLUMN_GMT_MODIFIED, nullable = true)
    @ColumnDocument("修改时间")
    private Date gmtModified;

}
