package com.autumn.domain.entities.auditing.user.ldt;

import com.autumn.domain.entities.auditing.ldt.LdtFullAuditing;
import com.autumn.domain.entities.auditing.user.AbstractUserFullAuditingEntity;
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
 * 具有用户完全审计的实体抽象
 *
 * @param <TKey> 主键类型
 * @author 老码农 2019-05-12 23:53:56
 */
@ToString(callSuper = true)
@Getter
@Setter
public abstract class AbstractUserLdtFullAuditingEntity<TKey extends Serializable> extends AbstractUserFullAuditingEntity<TKey>
        implements UserLdtFullAuditing, LdtFullAuditing {

    /**
     *
     */
    private static final long serialVersionUID = -3208733924557858644L;

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

    /**
     * 删除时间
     */
    @ColumnOrder(10201)
    @Column(name = COLUMN_LDT_DELETE)
    @ColumnDocument("删除时间")
    private LocalDateTime ldtDelete;
}
