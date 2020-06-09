package com.autumn.zero.file.storage.application.dto;

import com.autumn.validation.DefaultDataValidation;
import com.autumn.validation.annotation.NotNullOrBlank;
import io.swagger.annotations.ApiModelProperty;
import lombok.ToString;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.InputStream;

/**
 * 文件输入
 * 
 * @author 老码农 2019-03-18 18:29:03
 */
@ToString(callSuper = true)
public class FileInput extends DefaultDataValidation {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6960071291256606595L;

	@ApiModelProperty(value = "上传的原始文件名称")
	@NotNullOrBlank(message = "上传的原始文件名称不能为空。")
	private String originalFilename;

	@ApiModelProperty(value = "上传的原始文件大小")
	@NotNull(message = "上传的原始文件大小不能为空。")
	@Min(value = 1L, message = "上传的文件最低不能小于1个字节。")
	private Long originalFileSize;

	@ApiModelProperty(value = "上传的原始文件流")
	@NotNull(message = "上传的原始文件流不能为空。")
	private InputStream inputStream;

	public String getOriginalFilename() {
		return originalFilename;
	}

	public void setOriginalFilename(String originalFilename) {
		this.originalFilename = originalFilename;
	}

	public Long getOriginalFileSize() {
		return originalFileSize;
	}

	public void setOriginalFileSize(Long originalFileSize) {
		this.originalFileSize = originalFileSize;
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}
}
