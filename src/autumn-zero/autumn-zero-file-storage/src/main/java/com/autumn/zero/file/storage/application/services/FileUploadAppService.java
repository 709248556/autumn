package com.autumn.zero.file.storage.application.services;

import java.util.Set;

import com.autumn.application.ApplicationModule;
import com.autumn.zero.file.storage.application.dto.FileInput;
import com.autumn.zero.file.storage.services.vo.FileAttachmentInformationResponse;

/**
 * 文件上传服务
 * 
 * @author 老码农 2019-03-18 18:19:39
 */
public interface FileUploadAppService extends ApplicationModule {

	/**
	 * 获取文件上传附件类型
	 * 
	 * 
	 * @return
	 */
	int getFileUploadAttachmentType();

	/**
	 * 获取文件上传开始路径
	 * 
	 * @return
	 */
	String getFileUploadStartPath();

	/**
	 * 获取文件传限制的扩展名类型
	 * 
	 * @return
	 */
	Set<String> getFileUploadLimitExtensions();

	/**
	 * 获取文件上传的限制大小
	 * 
	 * @return
	 */
	Long getFileUploadLimitSize();

	/**
	 * 保存上传文件
	 * 
	 * @param input
	 *            输入
	 * @return
	 * @throws Exception
	 */
	FileAttachmentInformationResponse saveUploadFile(FileInput input) throws Exception;
}
