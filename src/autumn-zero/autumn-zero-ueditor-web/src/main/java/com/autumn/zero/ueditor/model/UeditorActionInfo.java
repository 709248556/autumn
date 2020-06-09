package com.autumn.zero.ueditor.model;


import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * 访问信息名称
 *
 * @Description TODO
 * @Author 老码农
 * @Date 2019-10-15 20:43
 */
@Getter
@Setter
public class UeditorActionInfo implements Serializable {

    private static final long serialVersionUID = 4628825896591245252L;

    /**
     * @param actionName
     * @param fieldName
     * @param maxSize
     * @param limitExtensions
     * @param uploadPath
     */
    public UeditorActionInfo(String actionName, String fieldName, Integer maxSize, Set<String> limitExtensions, String uploadPath) {
        this.actionName = actionName;
        this.fieldName = fieldName;
        this.maxSize = maxSize != null ? maxSize : 0L;
        this.limitExtensions = limitExtensions != null ? limitExtensions : new HashSet<>(16);
        this.uploadPath = uploadPath;
    }

    /**
     * 请求名称
     */
    private String actionName;

    /**
     * 提交的图片表单名称
     */
    private String fieldName;

    /**
     * 上传大小限制，单位B
     */
    private long maxSize;

    /**
     * 限制扩展名称
     */
    private Set<String> limitExtensions;

    /**
     * 上传路径
     */
    private String uploadPath;

}