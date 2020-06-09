package com.autumn.domain.entities.auditing.ldt;

import com.autumn.domain.entities.AbstractEntity;
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
 * 具有新增审计的抽象
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
public abstract class AbstractLdtCreateAuditingEntity<TKey extends Serializable> extends AbstractEntity<TKey> implements LdtCreateAuditing {

    /**
     *
     */
    private static final long serialVersionUID = 2969338981575758840L;

    @ColumnOrder(10000)
    @Column(name = COLUMN_LDT_CREATE, nullable = false)
    @Index
    @ColumnDocument("创建时间")
    private LocalDateTime ldtCreate;
}
