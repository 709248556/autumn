package com.autumn.application.dto.output.auditing.user;

import com.autumn.annotation.FriendlyProperty;
import com.autumn.domain.entities.auditing.gmt.GmtCreateAuditing;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * 具用审计用户输出
 *
 * @param <TKey>
 * @author 老码农 2019-05-23 10:30:21
 */
@ToString(callSuper = true)
@Getter
@Setter
public class UserGmtCreateOutput<TKey extends Serializable> extends UserCreateOutput<TKey>
        implements GmtCreateAuditing {

    /**
     *
     */
    private static final long serialVersionUID = -7185028732649870527L;

    /**
     * 创建时间
     */
    @FriendlyProperty(value = "创建时间")
    private Date gmtCreate;

}
