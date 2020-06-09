package com.autumn.zero.file.storage.services.vo;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.autumn.validation.DefaultDataValidation;

/**
 * 使用上传文件请求
 * 
 * @author 老码农 2019-03-19 16:06:34
 */
public class DefaultUseUploadFileRequest extends DefaultDataValidation implements UseUploadFileRequest {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1436290662438876503L;

	@NotNull(message = "上传id不能为空。")
	private Long uploadId;

	private Integer identification;

	/**
	 * 上传说明
	 */
	@Length(max = 255, message = "上传说明 不能超过  255 个字。")
	private String uploadExplain;

	/**
	 * 获取上传id
	 * 
	 * @return
	 */
	@Override
	public Long getUploadId() {
		return uploadId;
	}

	/**
	 * 设置上传id
	 * 
	 * @param uploadId
	 *            上传id
	 */
	@Override
	public void setUploadId(Long uploadId) {
		this.uploadId = uploadId;
	}

	/**
	 * 获取标识id
	 * 
	 * @return
	 */
	@Override
	public Integer getIdentification() {
		return identification;
	}

	/**
	 * 设置标识id
	 * 
	 * @param identification
	 *            标识id
	 */
	@Override
	public void setIdentification(Integer identification) {
		this.identification = identification;
	}

	@Override
	public String getUploadExplain() {
		return uploadExplain;
	}

	@Override
	public void setUploadExplain(String uploadExplain) {
		this.uploadExplain = uploadExplain;
	}

	@Override
	public void valid() {
		super.valid();
	}

}
