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
 * 具有完全审计的实体抽象
 *
 * @param <TKey> 主键类型
 * @author 老码农
 * <p>
 * Description
 * </p>
 * @date 2017-12-31 21:25:04
 */
@ToString(callSuper = true)
@Getter
@Setter
public abstract class AbstractGmtFullAuditingEntity<TKey extends Serializable> extends AbstractGmtModifiedEntity<TKey> implements GmtFullAuditing {

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
    @Column(name = COLUMN_GMT_DELETE)
    @ColumnDocument("删除时间")
    private Date gmtDelete;
}
