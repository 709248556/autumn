package com.autumn.zero.common.library.application.dto.sys.help;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 系统文档访问信息输出
 * <p>
 * </p>
 *
 * @description: TODO
 * @author: 老码农
 * @create: 2019-11-30 04:35
 **/
@Getter
@Setter
public class SystemHelpDocumentVisitOutput implements Serializable {

    private static final long serialVersionUID = -7459555548929944289L;

    /**
     * 名称
     */
    @ApiModelProperty(value = "名称")
    private String name;

    /**
     * 友好名称
     */
    @ApiModelProperty(value = "友好名称")
    private String friendlyName;

    /**
     * url路径
     */
    @ApiModelProperty(value = "url路径")
    private String urlFullPath;

    /**
     * 访问路径
     */
    @ApiModelProperty(value = "访问路径")
    private String accessPath;
}
