package com.autumn.zero.ueditor.model;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Getter;
import lombok.Setter;

/**
 * 具有状态的结果
 *
 * @Description TODO
 * @Author 老码农
 * @Date 2019-10-16 14:51
 */
@Getter
@Setter
public class ExecuteStateResult extends ExecuteResult {

    private static final long serialVersionUID = -3132235699309799999L;

    /**
     * 成功状态
     */
    public static final String STATE_SUCCESS = "SUCCESS";

    /**
     * 失败
     *
     * @param stateMessage 消息
     * @return
     */
    public static ExecuteStateResult fail(String stateMessage) {
        ExecuteStateResult result = new ExecuteStateResult();
        result.setState(stateMessage);
        return result;
    }

    /**
     * 状态
     */
    @JSONField(ordinal = 1)
    private String state;


}
