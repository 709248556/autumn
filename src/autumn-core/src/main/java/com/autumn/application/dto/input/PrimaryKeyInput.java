package com.autumn.application.dto.input;

import com.autumn.application.dto.EntityDto;
import lombok.ToString;

import java.io.Serializable;

/**
 * 主键输入
 *
 * @author 老码农
 * <p>
 * Description
 * </p>
 * @date 2017-12-31 21:37:16
 */
@ToString(callSuper = true)
public class PrimaryKeyInput<TKey extends Serializable> extends EntityDto<TKey> {

    /**
     *
     */
    private static final long serialVersionUID = -5749573289733604407L;
}
