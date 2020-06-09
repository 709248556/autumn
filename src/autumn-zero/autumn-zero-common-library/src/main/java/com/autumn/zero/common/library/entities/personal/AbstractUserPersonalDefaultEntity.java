package com.autumn.zero.common.library.entities.personal;

import com.autumn.annotation.FriendlyProperty;
import com.autumn.audited.annotation.LogMessage;
import com.autumn.mybatis.mapper.annotation.ColumnDocument;
import com.autumn.mybatis.mapper.annotation.ColumnOrder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;

/**
 * 用户个人具有默认记录的实体
 * <p>
 * </p>
 *
 * @description TODO
 * @author: 老码农
 * @create: 2020-03-26 20:14
 **/
@ToString(callSuper = true)
@Getter
@Setter
public abstract class AbstractUserPersonalDefaultEntity extends AbstractUserPersonalEntity
        implements UserPersonalDefaultEntity {

    private static final long serialVersionUID = -7724389686552321173L;

    /**
     * 默认
     */
    @Column(name = "is_default", nullable = false)
    @ColumnOrder(7)
    @ColumnDocument("是否默认")
    @FriendlyProperty(value = "是否默认")
    @LogMessage(order = 30)
    private boolean defaulted;

}
