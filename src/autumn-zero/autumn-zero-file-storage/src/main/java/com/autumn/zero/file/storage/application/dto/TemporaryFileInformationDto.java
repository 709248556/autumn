package com.autumn.zero.file.storage.application.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * 临时文件信息
 *
 * @Description TODO
 * @Author 老码农
 * @Date 2019-07-24 16:12
 */
@ToString(callSuper = true)
@Getter
@Setter
public class TemporaryFileInformationDto implements Serializable {

    private static final long serialVersionUID = -6185026534670764450L;

    /**
     * 文件友好名称
     */
    @ApiModelProperty(value = "文件友好名称")
    private String fileFriendlyName;

    /**
     * 文件名称
     */
    @ApiModelProperty(value = "文件名称")
    private String fileName;

    /**
     * 扩展名
     */
    @ApiModelProperty(value = "扩展名")
    private String extensionName;

    /**
     * url路径
     */
    @ApiModelProperty(value = "url路径")
    private String urlPath;

    /**
     * url完成路径
     */
    @ApiModelProperty(value = "url完成路径")
    private String urlFullPath;

    /**
     * 文件长度
     */
    @ApiModelProperty(value = "文件长度")
    private Long fileLength;

    /**
     * 文件友好长度
     */
    @ApiModelProperty(value = "文件友好长度")
    private String fileFriendlyLength;

    /**
     * 获取访问路径
     */
    @ApiModelProperty(value = "访问Url路径")
    private String accessUrlPath;

}
