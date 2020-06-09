package com.autumn.zero.file.storage.services.vo;

import java.io.InputStream;
import java.io.Serializable;
import java.util.UUID;

import com.autumn.exception.ExceptionUtils;
import com.autumn.util.StringUtils;

/**
 * 文件附件信息请求
 * 
 * @author 老码农 2019-03-18 14:09:14
 */
public class FileAttachmentInformationRequest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8925642290143781225L;

	/**
	 * 附件类型
	 * {@link com.autumn.health.prevention.constants.FileAttachmentTypeConstant}
	 */
	private final Integer fileAttachmentType;

	/**
	 * 文件路径(不含文件名称)
	 */
	private final String urlPath;

	/**
	 * 完整的文件名称
	 */
	private final String fullUrlPath;

	/**
	 * 上传的文件名称
	 */
	private final String originalFilename;

	/**
	 * 文件流
	 */
	private final InputStream inputStream;

	/**
	 * 获取友好名称
	 */
	private final String fileFriendlyName;

	/**
	 * 获取扩展名
	 */
	private final String extensionName;

	/**
	 * 模块
	 */
	private String moduleName;

	/**
	 * 
	 * @param fileAttachmentType
	 *            {@link com.autumn.health.prevention.constants.FileAttachmentTypeConstant}
	 * @param urlPath
	 *            文件路径(不含文件名称)
	 * @param originalFilename
	 *            上传的文件名称
	 * @param inputStream
	 */
	public FileAttachmentInformationRequest(Integer fileAttachmentType, String urlPath, String originalFilename,
			InputStream inputStream) {
		super();
		this.fileAttachmentType = ExceptionUtils.checkNotNull(fileAttachmentType, "fileAttachmentType");
		this.urlPath = StringUtils.removeEnd(StringUtils
				.removeStart(ExceptionUtils.checkNotNullOrBlank(urlPath, "urlPath").trim().toLowerCase(), '/'), '/');
		this.originalFilename = ExceptionUtils.checkNotNullOrBlank(originalFilename, "originalFilename").trim();
		this.inputStream = ExceptionUtils.checkNotNull(inputStream, "inputStream");
		int index = originalFilename.lastIndexOf(".");
		String id = UUID.randomUUID().toString().replace("-", "");
		if (index >= 0) {
			this.fileFriendlyName = originalFilename.substring(0, index);
			this.extensionName = originalFilename.substring(index + 1).toLowerCase();
			this.fullUrlPath = this.urlPath + "/" + id + "." + this.extensionName;
		} else {
			this.fileFriendlyName = originalFilename;
			this.extensionName = "";
			this.fullUrlPath = this.urlPath + "/" + id;
		}
	}

	/**
	 * 获取文件类型
	 * 
	 * @return {@link com.autumn.health.prevention.constants.FileAttachmentTypeConstant}
	 */
	public Integer getFileAttachmentType() {
		return fileAttachmentType;
	}

	/**
	 * 文件路径(不含文件名称)
	 * 
	 * @return
	 */
	public String getUrlPath() {
		return urlPath;
	}

	/**
	 * 获取完成的url(含文件名称)
	 * 
	 * @return
	 */
	public String getFullUrlPath() {
		return fullUrlPath;
	}

	/**
	 * 获取模块名称
	 * 
	 * @return
	 */
	public String getModuleName() {
		return moduleName;
	}

	/**
	 * 设置模块名称
	 * 
	 * @param moduleName
	 *            模块名称
	 */
	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	/**
	 * 获取上传的文件名称
	 * 
	 * @return
	 */
	public String getOriginalFilename() {
		return originalFilename;
	}

	/**
	 * 获取友好名称
	 * 
	 * @return
	 */
	public String getFileFriendlyName() {
		return fileFriendlyName;
	}

	/**
	 * 获取扩展名
	 * 
	 * @return
	 */
	public String getExtensionName() {
		return extensionName;
	}

	/**
	 * 获取文件流
	 * 
	 * @return
	 */
	public InputStream getInputStream() {
		return this.inputStream;
	}
}
