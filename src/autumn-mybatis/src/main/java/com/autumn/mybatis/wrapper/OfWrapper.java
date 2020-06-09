package com.autumn.mybatis.wrapper;

/**
 * 跳转所属 Wrapper
 *
 * @param <TSourceWrapper> 来源类型
 * @Description TODO
 * @Author 老码农
 * @Date 2019-06-12 0:04
 */
public interface OfWrapper<TSourceWrapper> {

    /**
     * 跳转出到所属来源
     *
     * @return 返回来源
     */
    TSourceWrapper of();
}
