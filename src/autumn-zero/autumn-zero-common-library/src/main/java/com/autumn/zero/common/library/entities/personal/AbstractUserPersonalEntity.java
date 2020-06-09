package com.autumn.zero.common.library.entities.personal;

/**
 * <p>
 * </p>
 *
 * @description TODO
 * @author: 老码农
 * @create: 2020-03-26 20:13
 **/

import com.autumn.annotation.FriendlyProperty;
import com.autumn.domain.entities.AbstractDefaultEntity;
import com.autumn.mybatis.mapper.annotation.ColumnDocument;
import com.autumn.mybatis.mapper.annotation.ColumnOrder;
import com.autumn.mybatis.mapper.annotation.Index;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;

/**
 * 用户抽象
 * <p>
 * </p>
 *
 * @description TODO
 * @author: 老码农
 * @create: 2020-03-26 16:14
 **/
@ToString(callSuper = true)
@Getter
@Setter
public abstract class AbstractUserPersonalEntity extends AbstractDefaultEntity implements UserPersonalEntity {

    private static final long serialVersionUID = 971669332562331368L;

    /**
     * 用户id
     */
    @NotNull(message = "用户id不能为空。")
    @Column(nullable = false)
    @ColumnOrder(100)
    @Index(unique = false)
    @ColumnDocument("用户id")
    @FriendlyProperty(value = "用户id")
    private Long userId;

}
