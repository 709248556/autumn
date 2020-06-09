package com.autumn.application.dto.output.auditing.ldt;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 默认具有创建审计输出
 * <p>
 * </p>
 *
 * @description TODO
 * @author: 老码农
 * @create: 2020-02-05 19:35
 **/
@ToString(callSuper = true)
@Getter
@Setter
public class DefaultLdtCreateOutput extends LdtCreateOutput<Long> {

    private static final long serialVersionUID = -1002617085586350494L;
}
