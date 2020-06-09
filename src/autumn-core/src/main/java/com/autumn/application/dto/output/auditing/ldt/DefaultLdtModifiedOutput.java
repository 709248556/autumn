package com.autumn.application.dto.output.auditing.ldt;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 默认具有修改审计的输出(64位整数的主键类型)
 * <p>
 * </p>
 *
 * @description TODO
 * @author: 老码农
 * @create: 2020-02-05 19:36
 **/
@ToString(callSuper = true)
@Getter
@Setter
public class DefaultLdtModifiedOutput extends LdtModifiedOutput<Long> {
    private static final long serialVersionUID = 7693542514588327309L;
}
