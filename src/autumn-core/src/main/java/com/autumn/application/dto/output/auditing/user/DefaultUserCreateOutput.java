package com.autumn.application.dto.output.auditing.user;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 具用审计用户输出
 * <p>
 * </p>
 *
 * @description TODO
 * @author: 老码农
 * @create: 2020-02-05 19:41
 **/
@ToString(callSuper = true)
@Getter
@Setter
public class DefaultUserCreateOutput extends UserCreateOutput<Long> {

    private static final long serialVersionUID = -5378303771303050048L;
}
