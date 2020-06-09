package com.autumn.zero.file.storage.entities;

import com.autumn.domain.entities.AbstractDefaultEntity;
import com.autumn.mybatis.mapper.annotation.ColumnDocument;
import com.autumn.mybatis.mapper.annotation.ColumnOrder;
import com.autumn.mybatis.mapper.annotation.Index;
import com.autumn.mybatis.mapper.annotation.TableDocument;
import com.autumn.validation.annotation.NotNullOrBlank;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * 文件附件信息
 * 
 * @author 老码农 2019-03-18 17:36:16
 */
@ToString(callSuper = true)
@Table(name = "sys_file_attachment_information")
@TableDocument(value = "文件附件信息", group = "系统表", groupOrder = Integer.MAX_VALUE, explain = "记录文件上传与使用状态")
public class FileAttachmentInformation extends AbstractDefaultEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9187321929476353461L;

	/**
	 * 字段 fileAttachmentType (文件附件类型)
	 */
	public static final String FIELD_FILE_ATTACHMENT_TYPE = "fileAttachmentType";

	/**
	 * 字段 targetId (目标Id)
	 */
	public static final String FIELD_TARGET_ID = "targetId";

	/**
	 * 字段 identification (标识)
	 */
	public static final String FIELD_IDENTIFICATION = "identification";
	
	 /**
     *  字段 moduleName (模块名称)
     */
    public static final String FIELD_MODULE_NAME = "moduleName";

	/**
	 * 字段 uploadExplain (上传说明)
	 */
	public static final String FIELD_UPLOAD_EXPLAIN = "uploadExplain";

	/**
	 * 字段 fileFriendlyName (文件友好名称)
	 */
	public static final String FIELD_FILE_FRIENDLY_NAME = "fileFriendlyName";

	/**
	 * 字段 fileName (文件名称)
	 */
	public static final String FIELD_FILE_NAME = "fileName";

	/**
	 * 字段 extensionName (扩展名)
	 */
	public static final String FIELD_EXTENSION_NAME = "extensionName";

	/**
	 * 字段 urlPath (url路径)
	 */
	public static final String FIELD_URL_PATH = "urlPath";

	/**
	 * 字段 urlFullPath (url完成路径)
	 */
	public static final String FIELD_URL_FULL_PATH = "urlFullPath";

	/**
	 * 字段 fileLength (文件长度)
	 */
	public static final String FIELD_FILE_LENGTH = "fileLength";

	/**
	 * 字段 fileFriendlyLength (文件友好长度)
	 */
	public static final String FIELD_FILE_FRIENDLY_LENGTH = "fileFriendlyLength";

	/**
	 * 字段 use (是否使用)
	 */
	public static final String FIELD_USE = "use";

	/**
	 * 字段 uploadUserId (上传用户)
	 */
	public static final String FIELD_UPLOAD_USER_ID = "uploadUserId";

	/**
	 * 字段 uploadUserName (上传用户名称)
	 */
	public static final String FIELD_UPLOAD_USER_NAME = "uploadUserName";

	/**
	 * 字段 uploadTime (上传时间)
	 */
	public static final String FIELD_UPLOAD_TIME = "uploadTime";

	/**
	 * 字段 fileFriendlyName (文件友好名称) 最大长度
	 */
	public static final int MAX_LENGTH_FILE_FRIENDLY_NAME = 255;
	
	 /**
     *  字段 moduleName (模块名称) 最大长度
     */
    public static final int MAX_LENGTH_MODULE_NAME = 50;

	/**
	 * 字段 uploadExplain (上传说明) 最大长度
	 */
	public static final int MAX_LENGTH_UPLOAD_EXPLAIN = 255;

	/**
	 * 字段 fileName (文件名称) 最大长度
	 */
	public static final int MAX_LENGTH_FILE_NAME = 255;

	/**
	 * 字段 extensionName (扩展名) 最大长度
	 */
	public static final int MAX_LENGTH_EXTENSION_NAME = 20;

	/**
	 * 字段 urlPath (url路径) 最大长度
	 */
	public static final int MAX_LENGTH_URL_PATH = 255;

	/**
	 * 字段 urlFullPath (url完成路径) 最大长度
	 */
	public static final int MAX_LENGTH_URL_FULL_PATH = 500;

	/**
	 * 字段 fileFriendlyLength (文件友好长度) 最大长度
	 */
	public static final int MAX_LENGTH_FILE_FRIENDLY_LENGTH = 50;

	/**
	 * 字段 uploadUserName (上传用户名称) 最大长度
	 */
	public static final int MAX_LENGTH_UPLOAD_USER_NAME = 50;

	/**
	 * 文件附件类型
	 */
	@NotNull(message = "文件附件类型不能为空。")
	@Column(nullable = false)
	@ColumnOrder(1)
	@Index(unique = false, name = "ix_common_file_attachment_type")
	@ColumnDocument("文件附件类型")
	private Integer fileAttachmentType;

	/**
	 * 目标Id
	 */
	@Column(nullable = true)
	@ColumnOrder(2)
	@Index(unique = false, name = "ix_common_file_attachment_target_id")
	@ColumnDocument("目标Id")
	private Long targetId;

	/**
	 * 标识
	 */
	@Column(nullable = true)
	@ColumnOrder(3)
	@ColumnDocument("标识")
	private Integer identification;
	
	 /**
     * 模块名称
     */
    @Length(max = MAX_LENGTH_MODULE_NAME, message = "模块名称 不能超过 " + MAX_LENGTH_MODULE_NAME + " 个字。")
    @Column(nullable = false, length = MAX_LENGTH_MODULE_NAME)
    @ColumnOrder(4)
    @Index(unique = false, name = "ix_common_file_attachment_modulename")
	@ColumnDocument("模块名称")
    private String moduleName; 

	/**
	 * 上传说明
	 */
	@Length(max = MAX_LENGTH_UPLOAD_EXPLAIN, message = "上传说明 不能超过 " + MAX_LENGTH_UPLOAD_EXPLAIN + " 个字。")
	@Column(nullable = false, length = MAX_LENGTH_UPLOAD_EXPLAIN)
	@ColumnOrder(4)
	@ColumnDocument("上传说明")
	private String uploadExplain;

	/**
	 * 文件友好名称
	 */
	@NotNullOrBlank(message = "文件友好名称不能为空。")
	@Length(max = MAX_LENGTH_FILE_FRIENDLY_NAME, message = "文件友好名称 不能超过 " + MAX_LENGTH_FILE_FRIENDLY_NAME + " 个字。")
	@Column(nullable = false, length = MAX_LENGTH_FILE_FRIENDLY_NAME)
	@ColumnOrder(5)
	@ColumnDocument("文件友好名称")
	private String fileFriendlyName;

	/**
	 * 文件名称
	 */
	@NotNullOrBlank(message = "文件名称不能为空。")
	@Length(max = MAX_LENGTH_FILE_NAME, message = "文件名称 不能超过 " + MAX_LENGTH_FILE_NAME + " 个字。")
	@Column(nullable = false, length = MAX_LENGTH_FILE_NAME)
	@ColumnOrder(6)
	@ColumnDocument("文件名称")
	private String fileName;

	/**
	 * 扩展名
	 */
	@NotNullOrBlank(message = "扩展名不能为空。")
	@Length(max = MAX_LENGTH_EXTENSION_NAME, message = "扩展名 不能超过 " + MAX_LENGTH_EXTENSION_NAME + " 个字。")
	@Column(nullable = false, length = MAX_LENGTH_EXTENSION_NAME)
	@ColumnOrder(7)
	@ColumnDocument("扩展名")
	private String extensionName;

	/**
	 * url路径
	 */
	@NotNullOrBlank(message = "url路径不能为空。")
	@Length(max = MAX_LENGTH_URL_PATH, message = "url路径 不能超过 " + MAX_LENGTH_URL_PATH + " 个字。")
	@Column(nullable = false, length = MAX_LENGTH_URL_PATH)
	@ColumnOrder(8)
	@Index(unique = false, name = "ix_common_file_attachment_url_path")
	@ColumnDocument("url路径")
	private String urlPath;

	/**
	 * url完成路径
	 */
	@Length(max = MAX_LENGTH_URL_FULL_PATH, message = "url完成路径 不能超过 " + MAX_LENGTH_URL_FULL_PATH + " 个字。")
	@Column(nullable = false, length = MAX_LENGTH_URL_FULL_PATH)
	@ColumnOrder(9)
	@ColumnDocument("url完成路径")
	private String urlFullPath;

	/**
	 * 文件长度
	 */
	@NotNull(message = "文件长度不能为空。")
	@Column(nullable = false)
	@ColumnOrder(10)
	@ColumnDocument("文件长度(字节数)")
	private Long fileLength;

	/**
	 * 文件友好长度
	 */
	@NotNullOrBlank(message = "文件友好长度不能为空。")
	@Length(max = MAX_LENGTH_FILE_FRIENDLY_LENGTH, message = "文件友好长度 不能超过 " + MAX_LENGTH_FILE_FRIENDLY_LENGTH + " 个字。")
	@Column(nullable = false, length = MAX_LENGTH_FILE_FRIENDLY_LENGTH)
	@ColumnOrder(11)
	@ColumnDocument("文件友好长度")
	private String fileFriendlyLength;

	/**
	 * 是否使用
	 */
	@Column(name = "is_use", nullable = false)
	@ColumnOrder(12)
	@ColumnDocument("是否使用")
	private boolean use;

	/**
	 * 上传用户
	 */
	@Column(nullable = true)
	@ColumnOrder(13)
	@ColumnDocument("上传用户id")
	private Long uploadUserId;

	/**
	 * 上传用户名称
	 */
	@Length(max = MAX_LENGTH_UPLOAD_USER_NAME, message = "上传用户名称 不能超过 " + MAX_LENGTH_UPLOAD_USER_NAME + " 个字。")
	@Column(nullable = false, length = MAX_LENGTH_UPLOAD_USER_NAME)
	@ColumnOrder(14)
	@ColumnDocument("上传用户名称")
	private String uploadUserName;

	/**
	 * 上传时间
	 */
	@Column(nullable = true)
	@ColumnOrder(15)
	@ColumnDocument("上传时间")
	private Date uploadTime;

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

	public boolean isUse() {
		return use;
	}

	public void setUse(boolean use) {
		this.use = use;
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
