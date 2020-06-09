package com.autumn.domain.entities.auditing.user.gmt;

import com.autumn.domain.entities.auditing.gmt.GmtCreateAuditing;
import com.autumn.domain.entities.auditing.user.AbstractUserCreateAuditingEntity;
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
 * 具有新增用户审计的抽象
 *
 * @param <TKey> 主键类型
 * @author 老码农 2019-05-12 23:32:41
 */
@ToString(callSuper = true)
@Getter
@Setter
public abstract class AbstractUserGmtCreateAuditingEntity<TKey extends Serializable> extends AbstractUserCreateAuditingEntity<TKey>
        implements UserGmtCreateAuditing, GmtCreateAuditing {

    /**
     *
     */
    private static final long serialVersionUID = -5421673043588835289L;

    @ColumnOrder(10000)
    @Column(name = COLUMN_GMT_CREATE, nullable = false)
    @Index
    @ColumnDocument("创建时间")
    private Date gmtCreate;

}
