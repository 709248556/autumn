package com.autumn.zero.file.storage.services.vo;

import java.io.Serializable;
import java.util.Date;

import com.autumn.domain.entities.Entity;

import io.swagger.annotations.ApiModelProperty;

/**
 * 文件附件信息响应
 * 
 * @author 老码农 2019-03-18 14:10:16
 */
public class FileAttachmentInformationResponse implements Entity<Long>, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6358023520024949311L;

	/**
	 * 文件
	 */
	@ApiModelProperty(value = "文件id")
	private Long id;

	/**
	 * 文件附件类型
	 */
	@ApiModelProperty(value = "文件附件类型")
	private Integer fileAttachmentType;

	/**
	 * 目标Id
	 */
	@ApiModelProperty(value = "目标Id")
	private Long targetId;

	/**
	 * 标识
	 */
	@ApiModelProperty(value = "标识")
	private Integer identification;

	/**
	 * 模块名称
	 */
	@ApiModelProperty(value = "模块名称")
	private String moduleName;
	/**
	 * 上传说明
	 */
	@ApiModelProperty(value = "上传说明")
	private String uploadExplain;

	/**
	 * 文件友好名称
	 */
	@ApiModelProperty(value = "文件友好名称")
	private String fileFriendlyName;

	/**
	 * 文件名称
	 */
	@ApiModelProperty(value = "文件名称")
	private String fileName;

	/**
	 * 扩展名
	 */
	@ApiModelProperty(value = "扩展名")
	private String extensionName;

	/**
	 * url路径
	 */
	@ApiModelProperty(value = "url路径")
	private String urlPath;

	/**
	 * url完成路径
	 */
	@ApiModelProperty(value = "url完成路径")
	private String urlFullPath;

	/**
	 * 文件长度
	 */
	@ApiModelProperty(value = "文件长度")
	private Long fileLength;

	/**
	 * 文件友好长度
	 */
	@ApiModelProperty(value = "文件友好长度")
	private String fileFriendlyLength;

	/**
	 * 获取访问路径
	 */
	@ApiModelProperty(value = "访问Url路径")
	private String accessUrlPath;

	/**
	 * 上传用户
	 */
	@ApiModelProperty(value = "上传用户")
	private Long uploadUserId;

	/**
	 * 上传用户名称
	 */
	@ApiModelProperty(value = "上传用户名称")
	private String uploadUserName;

	/**
	 * 上传时间
	 */
	@ApiModelProperty(value = "上传时间")
	private Date uploadTime;

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public Integer getFileAttachmentType() {
		return fileAttachmentType;
	}

	public void setFileAttachmentType(Integer fileAttachmentType) {
		this.fileAttachmentType = fileAttachmentType;
	}

	public Long getTargetId() {
		return targetId;
	}

	public void setTargetId(Long targetId) {
		this.targetId = targetId;
	}

	public Integer getIdentification() {
		return identification;
	}

	public void setIdentification(Integer identification) {
		this.identification = identification;
	}

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public String getUploadExplain() {
		return uploadExplain;
	}

	public void setUploadExplain(String uploadExplain) {
		this.uploadExplain = uploadExplain;
	}

	public String getFileFriendlyName() {
		return fileFriendlyName;
	}

	public void setFileFriendlyName(String fileFriendlyName) {
		this.fileFriendlyName = fileFriendlyName;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getExtensionName() {
		return extensionName;
	}

	public void setExtensionName(String extensionName) {
		this.extensionName = extensionName;
	}

	public String getUrlPath() {
		return urlPath;
	}

	public void setUrlPath(String urlPath) {
		this.urlPath = urlPath;
	}

	public String getUrlFullPath() {
		return urlFullPath;
	}

	public void setUrlFullPath(String urlFullPath) {
		this.urlFullPath = urlFullPath;
	}

	public Long getFileLength() {
		return fileLength;
	}

	public void setFileLength(Long fileLength) {
		this.fileLength = fileLength;
	}

	public String getFileFriendlyLength() {
		return fileFriendlyLength;
	}

	public void setFileFriendlyLength(String fileFriendlyLength) {
		this.fileFriendlyLength = fileFriendlyLength;
	}

	public String getAccessUrlPath() {
		return accessUrlPath;
	}

	public void setAccessUrlPath(String accessUrlPath) {
		this.accessUrlPath = accessUrlPath;
	}

	public Long getUploadUserId() {
		return uploadUserId;
	}

	public void setUploadUserId(Long uploadUserId) {
		this.uploadUserId = uploadUserId;
	}

	public String getUploadUserName() {
		return uploadUserName;
	}

	public void setUploadUserName(String uploadUserName) {
		this.uploadUserName = uploadUserName;
	}

	public Date getUploadTime() {
		return uploadTime;
	}

	public void setUploadTime(Date uploadTime) {
		this.uploadTime = uploadTime;
	}

}
