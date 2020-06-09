package com.autumn.zero.file.storage.application.dto;

import com.autumn.application.dto.DefaultEntityDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.ToString;

import java.util.List;

/**
 * 默认文件id上传
 * 
 * @author 老码农 2019-05-10 04:04:53
 */
@ToString(callSuper = true)
public class DefaultFileUploadIdInput extends DefaultEntityDto implements FileUploadIdInput {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1899160119430514845L;

	/**
	 * 上传文件id集合
	 */
	@ApiModelProperty(value = "上传文件id集合")
	private List<Long> uploadFileIds;

	/**
	 * 获取上传文件id集合
	 */
	@Override
	public List<Long> getUploadFileIds() {
		return uploadFileIds;
	}

	/**
	 * 设置文件上传id集合
	 * 
	 * @param uploadFileIds
	 */
	public void setUploadFileIds(List<Long> uploadFileIds) {
		this.uploadFileIds = uploadFileIds;
	}
}
