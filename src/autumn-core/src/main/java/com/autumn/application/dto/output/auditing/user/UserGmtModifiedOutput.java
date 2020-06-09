package com.autumn.application.dto.output.auditing.user;

import com.autumn.annotation.FriendlyProperty;
import com.autumn.domain.entities.auditing.gmt.GmtModifiedAuditing;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * 具有修改用户的审计
 *
 * @param <TKey>
 * @author 老码农 2019-05-23 10:33:12
 */
@ToString(callSuper = true)
@Getter
@Setter
public class UserGmtModifiedOutput<TKey extends Serializable> extends UserModifiedOutput<TKey>
        implements GmtModifiedAuditing {

    /**
     *
     */
    private static final long serialVersionUID = 5641020719146975561L;

    /**
     * 创建时间
     */
    @FriendlyProperty(value = "创建时间")
    private Date gmtCreate;

    /**
     * 最后修改时间
     */
    @FriendlyProperty(value = "最后修改时间")
    private Date gmtModified;


}
