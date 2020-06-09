package com.autumn.application.dto.output.auditing.user;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 默认具有创建用户审计输出
 * <p>
 * </p>
 *
 * @description TODO
 * @author: 老码农
 * @create: 2020-02-05 19:43
 **/
@ToString(callSuper = true)
@Getter
@Setter
public class DefaultUserLdtCreateOutput extends UserLdtCreateOutput<Long> {
    private static final long serialVersionUID = 2463644683357289937L;
}
