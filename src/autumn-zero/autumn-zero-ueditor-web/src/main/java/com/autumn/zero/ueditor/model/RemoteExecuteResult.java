package com.autumn.zero.ueditor.model;

import lombok.Getter;
import lombok.Setter;

/**
 * 远程执行结果
 *
 * @Description TODO
 * @Author 老码农
 * @Date 2019-10-16 16:56
 */
@Getter
@Setter
public class RemoteExecuteResult extends ExecuteStateResult {

    private static final long serialVersionUID = -1048593915619913681L;

    /**
     *
     */
    private String url;

    /**
     *
     */
    private String source;
}
