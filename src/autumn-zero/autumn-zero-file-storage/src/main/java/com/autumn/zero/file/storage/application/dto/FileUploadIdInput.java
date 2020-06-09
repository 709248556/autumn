package com.autumn.zero.file.storage.application.dto;

import java.util.List;

/**
 * 上传文件id输入
 * 
 * @author 老码农 2019-03-20 09:47:31
 */
public interface FileUploadIdInput extends FileUploadInput {

	/**
	 * 获取文件上传id集合
	 * 
	 * @return
	 */
	List<Long> getUploadFileIds();
}
