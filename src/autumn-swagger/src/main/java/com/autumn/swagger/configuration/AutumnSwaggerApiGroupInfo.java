package com.autumn.swagger.configuration;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * API 文档的分组信息
 *
 * @author xinghua
 * @date 2018/12/21
 * @since 1.0.0
 */
@Setter
@Getter
public class AutumnSwaggerApiGroupInfo implements Serializable {

    private static final long serialVersionUID = 7672594627611848627L;

    /**
     * Bean id
     */
    private String beanId;

    /**
     * 组名称
     */
    private String groupName;

    /**
     * 顺序
     */
    private int order;

    /**
     * 包集合
     */
    private List<String> packages;

    /**
     * 分组注解类型
     */
    private Class<? extends Annotation> annotation;

    /**
     * 实例化
     */
    public AutumnSwaggerApiGroupInfo() {
        this.setBeanId("autumnSwaggerApiGroup_" + UUID.randomUUID().toString().replace("-", ""));
        this.order = 0;
        this.setPackages(new ArrayList<>(5));
    }

}