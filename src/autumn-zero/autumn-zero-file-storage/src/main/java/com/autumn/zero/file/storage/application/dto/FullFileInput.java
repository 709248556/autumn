package com.autumn.zero.file.storage.application.dto;

import com.autumn.validation.annotation.NotNullOrBlank;
import io.swagger.annotations.ApiModelProperty;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.util.Set;

/**
 * 完整文件输入
 * 
 * @author 老码农 2019-03-18 18:30:34
 */
@ToString(callSuper = true)
public class FullFileInput extends FileInput {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8751082051988134043L;

	/**
	 * 附件类型	 *
	 */
	@ApiModelProperty(value = "附件类型")
	@NotNull(message = "附件类型不能为空。")
	private Integer fileAttachmentType;

	/**
	 * 文件上传的限制大小
	 * 
	 * @return
	 */
	@ApiModelProperty(value = "文件上传的限制大小")
	private Long limitSize;

	/**
	 * 模块
	 */
	@ApiModelProperty(value = "模块名称")
	private String moduleName;

	/**
	 * 文件路径(不含文件名称)
	 */
	@ApiModelProperty(value = "上传的路径")
	@NotNullOrBlank(message = "上传的路径不能为空。")
	private String urlPath;

	/**
	 * 限制文件类型
	 */
	@ApiModelProperty(value = "限制文件类型(扩展名集合)")
	private Set<String> limitExtensions;

	public Integer getFileAttachmentType() {
		return fileAttachmentType;
	}

	public void setFileAttachmentType(Integer fileAttachmentType) {
		this.fileAttachmentType = fileAttachmentType;
	}

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public Long getLimitSize() {
		return limitSize;
	}

	public void setLimitSize(Long limitSize) {
		this.limitSize = limitSize;
	}

	public String getUrlPath() {
		return urlPath;
	}

	public void setUrlPath(String urlPath) {
		this.urlPath = urlPath;
	}

	public Set<String> getLimitExtensions() {
		return limitExtensions;
	}

	public void setLimitExtensions(Set<String> limitExtensions) {
		this.limitExtensions = limitExtensions;
	}

	
}
