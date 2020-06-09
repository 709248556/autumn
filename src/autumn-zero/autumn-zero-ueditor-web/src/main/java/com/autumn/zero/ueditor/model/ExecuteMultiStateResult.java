package com.autumn.zero.ueditor.model;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * 多状态
 *
 * @Description TODO
 * @Author 老码农
 * @Date 2019-10-16 16:29
 */
@Getter
@Setter
public class ExecuteMultiStateResult extends ExecuteStateResult {

    private static final long serialVersionUID = -2569973989199641353L;

    /**
     * 列表
     */
    private List<ExecuteResult> list = new ArrayList<>(16);
}
