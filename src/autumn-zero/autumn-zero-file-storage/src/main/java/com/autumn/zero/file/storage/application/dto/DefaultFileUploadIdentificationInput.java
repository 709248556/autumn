package com.autumn.zero.file.storage.application.dto;

import com.autumn.application.dto.DefaultEntityDto;
import com.autumn.zero.file.storage.services.vo.DefaultUseUploadFileRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

/**
 * 默认文件标识上传输入
 *
 * @author 老码农 2019-05-10 04:08:34
 */
@ToString(callSuper = true)
@Setter
@Getter
public class DefaultFileUploadIdentificationInput extends DefaultEntityDto implements FileUploadIdentificationInput {

    /**
     *
     */
    private static final long serialVersionUID = 2461028958736690118L;

    /**
     * 上传文件集合
     */
    @ApiModelProperty(value = "上传文件集合")
    private List<DefaultUseUploadFileRequest> uploadFiles;

    /**
     *
     */
    public DefaultFileUploadIdentificationInput() {
        this.setUploadFiles(new ArrayList<>(16));
    }

    /**
     * @param uploadFiles
     */
    public DefaultFileUploadIdentificationInput(List<DefaultUseUploadFileRequest> uploadFiles) {
        this.setUploadFiles(uploadFiles);
    }
}
