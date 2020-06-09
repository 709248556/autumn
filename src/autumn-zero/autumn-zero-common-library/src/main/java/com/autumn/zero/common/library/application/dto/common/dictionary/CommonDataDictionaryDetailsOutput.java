package com.autumn.zero.common.library.application.dto.common.dictionary;

import com.autumn.zero.file.storage.application.dto.FileUploadAttachmentOutput;
import com.autumn.zero.file.storage.services.vo.FileAttachmentInformationResponse;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * 公共字典详情输出
 *
 * @Description TODO
 * @Author 老码农
 * @Date 2019-07-25 18:50
 */
@ToString(callSuper = true)
@Getter
@Setter
public class CommonDataDictionaryDetailsOutput extends CommonDataDictionaryOutput implements FileUploadAttachmentOutput {

    private static final long serialVersionUID = -8124352363262574780L;


    /**
     * 上传的文件集合
     */
    @ApiModelProperty(value = "上传的文件集合")
    private List<FileAttachmentInformationResponse> uploadFiles;

}
