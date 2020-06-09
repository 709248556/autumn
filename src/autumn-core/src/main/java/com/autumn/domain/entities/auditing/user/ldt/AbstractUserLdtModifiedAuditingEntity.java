package com.autumn.domain.entities.auditing.user.ldt;

import com.autumn.domain.entities.auditing.ldt.LdtCreateAuditing;
import com.autumn.domain.entities.auditing.ldt.LdtModifiedAuditing;
import com.autumn.domain.entities.auditing.user.AbstractUserModifiedAuditingEntity;
import com.autumn.mybatis.mapper.annotation.ColumnDocument;
import com.autumn.mybatis.mapper.annotation.ColumnOrder;
import com.autumn.mybatis.mapper.annotation.Index;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 具有修改用户的审计抽象
 *
 * @param <TKey>
 * @author 老码农 2019-05-12 23:33:15
 */
@ToString(callSuper = true)
@Getter
@Setter
public abstract class AbstractUserLdtModifiedAuditingEntity<TKey extends Serializable> extends AbstractUserModifiedAuditingEntity<TKey>
        implements UserLdtModifiedAuditing, LdtCreateAuditing, LdtModifiedAuditing {

    /**
     *
     */
    private static final long serialVersionUID = -7858557187374618604L;

    @ColumnOrder(10000)
    @Column(name = COLUMN_LDT_CREATE, nullable = false)
    @Index
    @ColumnDocument("创建时间")
    private LocalDateTime ldtCreate;

    /**
     * 修改时间
     */
    @ColumnOrder(10100)
    @Column(name = COLUMN_LDT_MODIFIED, nullable = true)
    @ColumnDocument("修改时间")
    private LocalDateTime ldtModified;

}
