package com.autumn.domain.entities.auditing.ldt;

import com.autumn.mybatis.mapper.annotation.ColumnDocument;
import com.autumn.mybatis.mapper.annotation.ColumnOrder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 具有修改审计的抽象实体
 *
 * @param <TKey>
 * @author 老码农
 * <p>
 * Description
 * </p>
 * @date 2020-02-05 19:00
 */
@ToString(callSuper = true)
@Getter
@Setter
public abstract class AbstractLdtModifiedEntity<TKey extends Serializable> extends AbstractLdtCreateAuditingEntity<TKey> implements LdtModifiedAuditing {

    /**
     *
     */
    private static final long serialVersionUID = -2679866921633499656L;

    /**
     * 修改时间
     */
    @ColumnOrder(10100)
    @Column(name = COLUMN_LDT_MODIFIED, nullable = true)
    @ColumnDocument("修改时间")
    private LocalDateTime ldtModified;
}
