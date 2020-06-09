package com.autumn.zero.ueditor.model;

import com.autumn.util.json.JsonUtils;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 响应
 *
 * @Description TODO
 * @Author 老码农
 * @Date 2019-10-16 1:19
 */
@Getter
@Setter
public class ExecuteResult implements Serializable {

    private static final long serialVersionUID = 7451836976963048682L;

    /**
     * 转换为 Json
     *
     * @return
     */
    @Override
    public String toString() {
        return this.toJson();
    }

    /**
     * 转为json
     * @return
     */
    public String toJson(){
        return JsonUtils.toJSONString(this);
    }

}
