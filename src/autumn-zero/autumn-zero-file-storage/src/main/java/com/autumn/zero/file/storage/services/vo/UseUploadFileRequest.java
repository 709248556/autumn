package com.autumn.zero.file.storage.services.vo;

import java.io.Serializable;

/**
 * 使用上传文件请求抽象
 * 
 * @author 老码农 2019-03-20 09:03:57
 */
public interface UseUploadFileRequest extends Serializable {

	/**
	 * 获取上传id
	 * 
	 * @return
	 */
	Long getUploadId();

	/**
	 * 设置上传id
	 * 
	 * @param uploadId
	 */
	void setUploadId(Long uploadId);

	/**
	 * 获取标识
	 * 
	 * @return
	 */
	Integer getIdentification();

	/**
	 * 设置标识
	 * 
	 * @param identification
	 */
	void setIdentification(Integer identification);

	/**
	 * 获取上传说明
	 * 
	 * @return
	 */
	String getUploadExplain();

	/**
	 * 设置上传说明
	 * 
	 * @param uploadExplain
	 *            上传说明
	 */
	void setUploadExplain(String uploadExplain);

}
