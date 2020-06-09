package com.autumn.zero.common.library.entities;

import com.autumn.audited.annotation.LogMessage;
import com.autumn.domain.entities.DefaultNameEntity;
import com.autumn.domain.entities.DefaultSortEntity;
import com.autumn.domain.entities.DefaultStatusEntity;
import com.autumn.domain.entities.auditing.user.gmt.AbstractDefaultUserGmtModifiedAuditingEntity;
import com.autumn.mybatis.mapper.annotation.ColumnDocument;
import com.autumn.mybatis.mapper.annotation.ColumnOrder;
import com.autumn.mybatis.mapper.annotation.ColumnType;
import com.autumn.mybatis.mapper.annotation.Index;
import com.autumn.validation.annotation.NotNullOrBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.ibatis.type.JdbcType;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;

/**
 * 名称审计状态实体抽象
 *
 * @Description TODO
 * @Author 老码农
 * @Date 2019-07-25 17:23
 */
@ToString(callSuper = true)
@Getter
@Setter
public abstract class AbstractNameUserAuditingStatusEntity extends AbstractDefaultUserGmtModifiedAuditingEntity
        implements DefaultNameEntity, DefaultSortEntity, DefaultStatusEntity {

    private static final long serialVersionUID = -7489417560212340038L;

    /**
     * 名称
     */
    @Column(nullable = false, length = MAX_NAME_LENGTH)
    @NotNullOrBlank(message = "名称不能为空")
    @ColumnOrder(1)
    @Index(unique = false)
    @LogMessage(name = "名称", order = 3)
    @ColumnDocument("名称")
    private String name;

    /**
     * 顺序
     */
    @Column(nullable = false)
    @Index(unique = false)
    @NotNull(message = "顺序不能为空")
    @ColumnOrder(2)
    @LogMessage(name = "顺序", order = 4)
    @ColumnDocument("顺序")
    private Integer sortId;

    /**
     * 状态
     */
    @Column(nullable = false)
    @NotNull(message = "状态不能为空")
    @ColumnOrder(100)
    @ColumnDocument("状态")
    private Integer status;

    /**
     * 备注
     */
    @Column(nullable = false)
    @ColumnOrder(200)
    @ColumnType(jdbcType = JdbcType.LONGNVARCHAR)
    @LogMessage(name = "备注", order = 200)
    @ColumnDocument("备注")
    private String remarks;
}
