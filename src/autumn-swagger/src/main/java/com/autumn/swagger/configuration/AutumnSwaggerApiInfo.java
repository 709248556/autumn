package com.autumn.swagger.configuration;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * API 文档的主要 配置信息
 *
 * @author xinghua
 * @date 2018/12/21
 * @since 1.0.0
 */
@Setter
@Getter
public class AutumnSwaggerApiInfo implements Serializable {

    private static final long serialVersionUID = 4992282835133138163L;

    /**
     * 标题
     */
    private String title;

    /**
     * 说明
     */
    private String description;

    /**
     * 版本
     */
    private String version;

    /**
     * 许可证
     */
    private String license;

    /**
     * 许可证url
     */
    private String licenseUrl;

    /**
     * 启用 Authorize 授权
     */
    private boolean enableAuthorize;

    /**
     * Authorize 头参数名
     */
    private String authorizeHeader;

    /**
     * 作者
     */
    private String authorName;

    /**
     * 作者url
     */
    private String authorUrl;

    /**
     * 作者电子邮件
     */
    private String authorEmail;

    /**
     * 组集合
     */
    private List<AutumnSwaggerApiGroupInfo> groups;

    /**
     * 请问请求参数
     */
    private List<AutumnSwaggerApiHeaderParameterInfo> headerParameters;

    /**
     * 实例化
     */
    public AutumnSwaggerApiInfo() {
        this.setGroups(new ArrayList<>(16));
        this.setHeaderParameters(new ArrayList<>(5));
    }
}