package com.autumn.domain.entities;


import com.autumn.annotation.FriendlyProperty;
import com.autumn.mybatis.mapper.annotation.ColumnDocument;
import com.autumn.mybatis.mapper.annotation.ColumnOrder;
import com.autumn.util.BeanUtils;
import com.autumn.validation.DataValidation;
import com.autumn.validation.ValidationUtils;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * 实体抽象
 *
 * @param <TKey> 主键类型
 * @author 老码农
 * <p>
 * Description
 * </p>
 * @date 2017-12-31 21:17:09
 */
@ToString(callSuper = true)
@Getter
@Setter
public abstract class AbstractEntity<TKey extends Serializable> implements Entity<TKey>, DataValidation, EntityDataBean {

    /**
     *
     */
    private static final long serialVersionUID = 5901548351739663626L;

    @Id
    @ColumnDocument("主键")
    @FriendlyProperty(value = "id")
    @ColumnOrder(-10000)
    @Column(name = Entity.COLUMN_ID, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private TKey id;

    @Override
    public void valid() {
        ValidationUtils.validation(this);
    }

    /**
     * 默认转换
     */
    @Override
    public void forNullToDefault() {
        BeanUtils.setDbEntityforNullToDefault(this);
    }
}
