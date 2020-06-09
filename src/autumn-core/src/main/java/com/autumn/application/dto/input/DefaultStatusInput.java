package com.autumn.application.dto.input;


import com.autumn.annotation.FriendlyProperty;
import com.autumn.domain.entities.Entity;
import com.autumn.validation.DefaultDataValidation;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 状态输入
 *
 * @param <TKey>
 * @author 老码农 2018-12-20 11:29:58
 */
@ToString(callSuper = true)
public class DefaultStatusInput<TKey extends Serializable> extends DefaultDataValidation implements Entity<TKey>, StatusInput<TKey> {

    /**
     *
     */
    private static final long serialVersionUID = 7385599534958746728L;

    @FriendlyProperty(value = "主键id")
    @NotNull(message = "主键id不能为空")
    private TKey id;

    /**
     * 状态
     */
    @FriendlyProperty(value = "状态")
    @NotNull(message = "状态不能为空")
    private Integer status;

    @Override
    public TKey getId() {
        return this.id;
    }

    @Override
    public void setId(TKey id) {
        this.id = id;
    }

    @Override
    public Integer getStatus() {
        return status;
    }

    @Override
    public void setStatus(Integer status) {
        this.status = status;
    }
}
