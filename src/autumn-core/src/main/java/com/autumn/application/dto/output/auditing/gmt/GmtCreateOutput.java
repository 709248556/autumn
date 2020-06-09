package com.autumn.application.dto.output.auditing.gmt;

import com.autumn.annotation.FriendlyProperty;
import com.autumn.application.dto.EntityDto;
import com.autumn.domain.entities.auditing.gmt.GmtCreateAuditing;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * 具有新增审计的抽象
 *
 * @author 老码农
 * <p>
 * 2017-11-01 16:59:37
 */
@ToString(callSuper = true)
@Getter
@Setter
public class GmtCreateOutput<TKey extends Serializable> extends EntityDto<TKey> implements GmtCreateAuditing {

    /**
     *
     */
    private static final long serialVersionUID = 2969338981575758840L;

    /**
     * 创建时间
     */
    @FriendlyProperty(value = "创建时间")
    private Date gmtCreate;
}
