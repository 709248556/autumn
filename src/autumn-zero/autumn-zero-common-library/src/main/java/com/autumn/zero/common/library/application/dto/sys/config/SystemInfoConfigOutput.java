package com.autumn.zero.common.library.application.dto.sys.config;

import com.autumn.zero.file.storage.annotation.FileOutputBinding;
import com.autumn.zero.file.storage.application.dto.FileUploadOutput;
import com.autumn.zero.file.storage.services.vo.FileAttachmentInformationResponse;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 系统信息配置输出
 *
 * @Description TODO
 * @Author 老码农
 * @Date 2019-07-31 2:38
 */
@Getter
@Setter
public class SystemInfoConfigOutput extends SystemInfoConfigInput implements FileUploadOutput {

    private static final long serialVersionUID = 2242012963673914514L;

    /**
     * logo图片上传文件
     */
    @ApiModelProperty(value = "logo图片上传文件")
    @FileOutputBinding(identification = IDENTIFICATION_LOGO_FILE)
    private FileAttachmentInformationResponse logoUploadFile;

    /**
     * 后台登录Banner图片上传文件
     */
    @ApiModelProperty(value = "后台登录Banner图片上传文件")
    @FileOutputBinding(identification = IDENTIFICATION_SYSTEM_LOGIN)
    private FileAttachmentInformationResponse systemLoginBannerUploadFile;

    /**
     * 站点登录Banner图片上传文件
     */
    @ApiModelProperty(value = "站点登录Banner图片上传文件")
    @FileOutputBinding(identification = IDENTIFICATION_SITE_LOGO)
    private FileAttachmentInformationResponse siteLoginBannerUploadFile;

}
