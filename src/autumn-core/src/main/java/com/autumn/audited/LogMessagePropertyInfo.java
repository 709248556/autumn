package com.autumn.audited;

import lombok.Getter;

import java.io.Serializable;

/**
 * 日志消息属性配置
 *
 * @Description TODO
 * @Author 老码农
 * @Date 2019-08-04 2:25
 */
@Getter
public class LogMessagePropertyInfo implements Serializable {

    private static final long serialVersionUID = -3752248639153739936L;

    /**
     * 名称
     */
    private final String name;

    /**
     * 顺序
     */
    private final int order;

    public LogMessagePropertyInfo(String name, int order) {
        this.name = name;
        this.order = order;
    }

    @Override
    public int hashCode() {
        return this.getName().hashCode() ^ this.order;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof LogMessagePropertyInfo) {
            LogMessagePropertyInfo info = (LogMessagePropertyInfo) obj;
            return this.getName().equals(this.name) && this.getOrder() == info.getOrder();
        }
        return false;
    }
}
