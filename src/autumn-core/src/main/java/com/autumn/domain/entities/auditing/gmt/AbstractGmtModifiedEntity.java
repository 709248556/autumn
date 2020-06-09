package com.autumn.domain.entities.auditing.gmt;

import com.autumn.mybatis.mapper.annotation.ColumnDocument;
import com.autumn.mybatis.mapper.annotation.ColumnOrder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import java.io.Serializable;
import java.util.Date;

/**
 * 具有修改审计的抽象实体
 *
 * @param <TKey>
 * @author 老码农
 * <p>
 * Description
 * </p>
 * @date 2017-12-31 21:24:10
 */
@ToString(callSuper = true)
@Getter
@Setter
public abstract class AbstractGmtModifiedEntity<TKey extends Serializable> extends AbstractGmtCreateAuditingEntity<TKey> implements GmtModifiedAuditing {

    /**
     *
     */
    private static final long serialVersionUID = -2679866921633499656L;

    /**
     * 修改时间
     */
    @ColumnOrder(10100)
    @Column(name = COLUMN_GMT_MODIFIED, nullable = true)
    @ColumnDocument("修改时间")
    private Date gmtModified;
}
