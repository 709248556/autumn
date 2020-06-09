package com.autumn.application.dto.output.auditing.user;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 默认用户修改审计输出
 * <p>
 * </p>
 *
 * @description TODO
 * @author: 老码农
 * @create: 2020-02-05 19:42
 **/
@ToString(callSuper = true)
@Getter
@Setter
public class DefaultUserModifiedOutput extends UserModifiedOutput<Long> {
    private static final long serialVersionUID = 4167906076558176092L;
}
