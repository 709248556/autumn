package com.autumn.zero.authorization.entities.common;

import com.autumn.domain.entities.AbstractDefaultEntity;
import com.autumn.mybatis.mapper.annotation.ColumnDocument;
import com.autumn.mybatis.mapper.annotation.ColumnOrder;
import com.autumn.mybatis.mapper.annotation.Index;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;

/**
 * 用户子级
 * <p>
 * </p>
 *
 * @description TODO
 * @author: 老码农
 * @create: 2020-04-20 00:03
 **/
@Getter
@Setter
public abstract class AbstractUserChild extends AbstractDefaultEntity {

    private static final long serialVersionUID = -3798642258496694313L;

    /**
     * 字段 userId
     */
    public static final String FIELD_USER_ID = "userId";

    /**
     * 用户id
     */
    @NotNull(message = "用户id不能为空。")
    @Column(name = "user_id", nullable = false)
    @Index
    @ColumnOrder(1)
    @ColumnDocument("用户id")
    private Long userId;
}
