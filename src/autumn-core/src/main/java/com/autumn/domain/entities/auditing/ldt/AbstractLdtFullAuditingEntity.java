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
 * 具有完全审计的实体抽象
 *
 * @param <TKey> 主键类型
 * @author 老码农
 * <p>
 * Description
 * </p>
 * @date 2020-02-05 19:00
 */
@ToString(callSuper = true)
@Getter
@Setter
public abstract class AbstractLdtFullAuditingEntity<TKey extends Serializable> extends AbstractLdtModifiedEntity<TKey> implements LdtFullAuditing {

    /**
     *
     */
    private static final long serialVersionUID = 3260509509017805235L;


    /**
     * 是否删除
     */
    @ColumnOrder(10200)
    @Column(name = COLUMN_IS_DELETE, nullable = false)
    @ColumnDocument("是否删除")
    private boolean delete;

    /**
     * 删除时间
     */
    @ColumnOrder(10201)
    @Column(name = COLUMN_LDT_DELETE)
    @ColumnDocument("删除时间")
    private LocalDateTime ldtDelete;
}
