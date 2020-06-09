package com.autumn.zero.common.library.application.dto.sys.config;

import com.autumn.annotation.FriendlyProperty;
import com.autumn.audited.annotation.LogMessage;
import com.autumn.validation.DefaultDataValidation;
import com.autumn.validation.annotation.NotNullOrBlank;
import com.autumn.zero.file.storage.annotation.FileUploadBinding;
import com.autumn.zero.file.storage.application.dto.FileUploadInput;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

/**
 * 系统信息配置输入
 *
 * @Description TODO
 * @Author 老码农
 * @Date 2019-07-31 2:37
 */
@Getter
@Setter
public class SystemInfoConfigInput extends DefaultDataValidation implements FileUploadInput {

    private static final long serialVersionUID = -1401846384352913571L;

    /**
     * logo文件标识
     */
    public static final int IDENTIFICATION_LOGO_FILE = 1;

    /**
     * 后台登录Banner图片上传文件标识
     */
    public static final int IDENTIFICATION_SYSTEM_LOGIN = 2;

    /**
     * 站点登录Banner图片上传文件标识
     */
    public static final int IDENTIFICATION_SITE_LOGO = 3;

    /**
     * 字段 systemName (系统后台名称)
     */
    public static final String FIELD_SYSTEM_NAME = "systemName";

    /**
     * 字段 systemEnglishName (系统后台英文名称)
     */
    public static final String FIELD_SYSTEM_ENGLISH_NAME = "systemEnglishName";

    /**
     * 字段 systemSimpleName (系统后台简称)
     */
    public static final String FIELD_SYSTEM_SIMPLE_NAME = "systemSimpleName";

    /**
     * 字段 systemEnglishSimpleName (系统后台英文简称)
     */
    public static final String FIELD_SYSTEM_ENGLISH_SIMPLE_NAME = "systemEnglishSimpleName";

    /**
     * 字段 systemAddress (网站后台地址)
     */
    public static final String FIELD_SYSTEM_ADDRESS = "systemAddress";

    /**
     * 字段 systemLoginBannerUploadId (后台登录Banner上传id)
     */
    public static final String FIELD_SYSTEM_LOGIN_BANNER_UPLOAD_ID = "systemLoginBannerUploadId";

    /**
     * 字段 systemKeepOnRecordNo (网站后台备案号)
     */
    public static final String FIELD_SYSTEM_KEEP_ON_RECORD_NO = "systemKeepOnRecordNo";

    /**
     * 字段 siteName (站点名称)
     */
    public static final String FIELD_SITE_NAME = "siteName";

    /**
     * 字段 siteEnglishName (站点英文名称)
     */
    public static final String FIELD_SITE_ENGLISH_NAME = "siteEnglishName";

    /**
     * 字段 siteSimpleName (站点简称)
     */
    public static final String FIELD_SITE_SIMPLE_NAME = "siteSimpleName";

    /**
     * 字段 siteEnglishSimpleName (站点英文简称)
     */
    public static final String FIELD_SITE_ENGLISH_SIMPLE_NAME = "siteEnglishSimpleName";

    /**
     * 字段 siteAddress (网站地址)
     */
    public static final String FIELD_SITE_ADDRESS = "siteAddress";

    /**
     * 字段 siteLoginBannerUploadId (站点登录Banner上传id)
     */
    public static final String FIELD_SITE_LOGIN_BANNER_UPLOAD_ID = "siteLoginBannerUploadId";

    /**
     * 字段 siteKeepOnRecordNo (站点备案号)
     */
    public static final String FIELD_SITE_KEEP_ON_RECORD_NO = "siteKeepOnRecordNo";

    /**
     * 字段 logoUploadId (logo上传id)
     */
    public static final String FIELD_LOGO_UPLOAD_ID = "logoUploadId";

    /**
     * 字段 company (单位名称)
     */
    public static final String FIELD_COMPANY = "company";

    /**
     * 字段 companyEnglish (单位英文名称)
     */
    public static final String FIELD_COMPANY_ENGLISH = "companyEnglish";

    /**
     * 字段 companySimpleName (单位简称)
     */
    public static final String FIELD_COMPANY_SIMPLE_NAME = "companySimpleName";

    /**
     * 字段 companyEnglishSimpleName (单位英文简称)
     */
    public static final String FIELD_COMPANY_ENGLISH_SIMPLE_NAME = "companyEnglishSimpleName";

    /**
     * 字段 contactPhone (联系电话)
     */
    public static final String FIELD_CONTACT_PHONE = "contactPhone";

    /**
     * 字段 systemName (系统后台名称) 最大长度
     */
    public static final int MAX_LENGTH_SYSTEM_NAME = 100;

    /**
     * 字段 systemSimpleName (系统后台简称) 最大长度
     */
    public static final int MAX_LENGTH_SYSTEM_SIMPLE_NAME = 50;

    /**
     * 字段 systemAddress (网站后台地址) 最大长度
     */
    public static final int MAX_LENGTH_SYSTEM_ADDRESS = 255;

    /**
     * 字段 systemKeepOnRecordNo (网站后台备案号) 最大长度
     */
    public static final int MAX_LENGTH_SYSTEM_KEEP_ON_RECORD_NO = 100;

    /**
     * 字段 siteName (站点名称) 最大长度
     */
    public static final int MAX_LENGTH_SITE_NAME = 100;

    /**
     * 字段 siteSimpleName (站点简称) 最大长度
     */
    public static final int MAX_LENGTH_SITE_SIMPLE_NAME = 50;

    /**
     * 字段 siteAddress (网站地址) 最大长度
     */
    public static final int MAX_LENGTH_SITE_ADDRESS = 255;

    /**
     * 字段 siteKeepOnRecordNo (站点备案号) 最大长度
     */
    public static final int MAX_LENGTH_SITE_KEEP_ON_RECORD_NO = 100;

    /**
     * 字段 company (单位名称) 最大长度
     */
    public static final int MAX_LENGTH_COMPANY = 100;

    /**
     * 字段 companySimpleName (单位简称) 最大长度
     */
    public static final int MAX_LENGTH_COMPANY_SIMPLE_NAME = 50;

    /**
     * 字段 contactPhone (联系电话) 最大长度
     */
    public static final int MAX_LENGTH_CONTACT_PHONE = 50;

    /**
     * logo上传id
     */
    @ApiModelProperty(value = "logo图片上传id", required = false, dataType = "Long")
    @FriendlyProperty("logo上传id")
    @FileUploadBinding(identification = IDENTIFICATION_LOGO_FILE, explain = "系统logo")
    private Long logoUploadId;

    /**
     * 系统后台名称
     */
    @ApiModelProperty(value = "系统后台名称(必输)(maxLength =" + MAX_LENGTH_SYSTEM_NAME + ")", required = true, dataType = "String")
    @NotNullOrBlank(message = "系统后台名称不能为空。")
    @Length(max = MAX_LENGTH_SYSTEM_NAME, message = "系统后台名称 不能超过 " + MAX_LENGTH_SYSTEM_NAME + " 个字。")
    @LogMessage(name = "系统后台名称", order = 2)
    @FriendlyProperty("系统后台名称")
    private String systemName;

    /**
     * 系统后台英文名称
     */
    @ApiModelProperty(value = "系统后台英文名称(maxLength =" + MAX_LENGTH_SYSTEM_NAME + ")", required = true, dataType = "String")
    @Length(max = MAX_LENGTH_SYSTEM_NAME, message = "系统后台英文名称 不能超过 " + MAX_LENGTH_SYSTEM_NAME + " 个字。")
    @LogMessage(name = "系统后台英文名称", order = 4)
    @FriendlyProperty("系统后台英文名称")
    private String systemEnglishName;

    /**
     * 系统后台简称
     */
    @ApiModelProperty(value = "系统后台简称(必输)(maxLength =" + MAX_LENGTH_SYSTEM_SIMPLE_NAME + ")", required = true, dataType = "String")
    @NotNullOrBlank(message = "系统后台简称不能为空。")
    @Length(max = MAX_LENGTH_SYSTEM_SIMPLE_NAME, message = "系统后台简称 不能超过 " + MAX_LENGTH_SYSTEM_SIMPLE_NAME + " 个字。")
    @LogMessage(name = "系统后台简称", order = 5)
    @FriendlyProperty("系统后台简称")
    private String systemSimpleName;

    /**
     * 系统后台英文简称
     */
    @ApiModelProperty(value = "系统后台英文简称(maxLength =" + MAX_LENGTH_SYSTEM_SIMPLE_NAME + ")", required = true, dataType = "String")
    @Length(max = MAX_LENGTH_SYSTEM_SIMPLE_NAME, message = "系统后台英文简称 不能超过 " + MAX_LENGTH_SYSTEM_SIMPLE_NAME + " 个字。")
    @LogMessage(name = "系统后台英文简称", order = 6)
    @FriendlyProperty("系统后台英文简称")
    private String systemEnglishSimpleName;

    /**
     * 系统后台帮助文档编号
     */
    @ApiModelProperty(value = "系统后台帮助文档编号")
    @LogMessage(name = "系统后台帮助文档编号", order = 6)
    @FriendlyProperty("系统后台帮助文档编号")
    private Long systemHelpDocNo;

    /**
     * 后台登录Banner上传id
     */
    @ApiModelProperty(value = "后台登录Banner图片上传id", required = false, dataType = "Long")
    @FriendlyProperty("后台登录Banner上传id")
    @FileUploadBinding(identification = IDENTIFICATION_SYSTEM_LOGIN, explain = "系统后台登录Banner")
    private Long systemLoginBannerUploadId;

    /**
     * 网站后台地址
     */
    @ApiModelProperty(value = "网站后台地址(maxLength =" + MAX_LENGTH_SYSTEM_ADDRESS + ")", required = false, dataType = "String")
    @Length(max = MAX_LENGTH_SYSTEM_ADDRESS, message = "网站后台地址 不能超过 " + MAX_LENGTH_SYSTEM_ADDRESS + " 个字。")
    @LogMessage(name = "网站后台地址", order = 7)
    @FriendlyProperty("网站后台地址")
    private String systemAddress;

    /**
     * 网站后台备案号
     */
    @ApiModelProperty(value = "网站后台备案号(maxLength =" + MAX_LENGTH_SYSTEM_KEEP_ON_RECORD_NO + ")", required = false, dataType = "String")
    @Length(max = MAX_LENGTH_SYSTEM_KEEP_ON_RECORD_NO, message = "网站后台备案号 不能超过 " + MAX_LENGTH_SYSTEM_KEEP_ON_RECORD_NO + " 个字。")
    @LogMessage(name = "网站后台备案号", order = 8)
    @FriendlyProperty("网站后台备案号")
    private String systemKeepOnRecordNo;

    /**
     * 站点名称
     */
    @ApiModelProperty(value = "站点名称(必输)(maxLength =" + MAX_LENGTH_SITE_NAME + ")", required = true, dataType = "String")
    @NotNullOrBlank(message = "站点名称不能为空。")
    @Length(max = MAX_LENGTH_SITE_NAME, message = "站点名称 不能超过 " + MAX_LENGTH_SITE_NAME + " 个字。")
    @LogMessage(name = "站点名称", order = 9)
    @FriendlyProperty("站点名称")
    private String siteName;

    /**
     * 站点英文名称
     */
    @ApiModelProperty(value = "站点英文名称(maxLength =" + MAX_LENGTH_SITE_NAME + ")", required = true, dataType = "String")
    @Length(max = MAX_LENGTH_SITE_NAME, message = "站点英文名称 不能超过 " + MAX_LENGTH_SITE_NAME + " 个字。")
    @LogMessage(name = "站点英文名称", order = 10)
    @FriendlyProperty("站点英文名称")
    private String siteEnglishName;

    /**
     * 站点简称
     */
    @ApiModelProperty(value = "站点简称(必输)(maxLength =" + MAX_LENGTH_SITE_SIMPLE_NAME + ")", required = true, dataType = "String")
    @NotNullOrBlank(message = "站点简称不能为空。")
    @Length(max = MAX_LENGTH_SITE_SIMPLE_NAME, message = "站点简称 不能超过 " + MAX_LENGTH_SITE_SIMPLE_NAME + " 个字。")
    @LogMessage(name = "站点简称", order = 11)
    @FriendlyProperty("站点简称")
    private String siteSimpleName;

    /**
     * 站点英文简称
     */
    @ApiModelProperty(value = "站点英文简称(maxLength =" + MAX_LENGTH_SITE_SIMPLE_NAME + ")", required = true, dataType = "String")
    @Length(max = MAX_LENGTH_SITE_SIMPLE_NAME, message = "站点英文简称 不能超过 " + MAX_LENGTH_SITE_SIMPLE_NAME + " 个字。")
    @LogMessage(name = "站点英文简称", order = 12)
    @FriendlyProperty("站点英文简称")
    private String siteEnglishSimpleName;

    /**
     * 站点帮助文档编号
     */
    @ApiModelProperty(value = "站点帮助文档编号")
    @LogMessage(name = "站点帮助文档编号", order = 13)
    @FriendlyProperty("站点帮助文档编号")
    private Long siteHelpDocNo;

    /**
     * 站点登录Banner上传id
     */
    @ApiModelProperty(value = "站点登录Banner图片上传id", required = false, dataType = "Long")
    @FriendlyProperty("站点登录Banner上传id")
    @FileUploadBinding(identification = IDENTIFICATION_SITE_LOGO, explain = "站点登录Banner")
    private Long siteLoginBannerUploadId;

    /**
     * 网站地址
     */
    @ApiModelProperty(value = "网站地址(maxLength =" + MAX_LENGTH_SITE_ADDRESS + ")", required = false, dataType = "String")
    @Length(max = MAX_LENGTH_SITE_ADDRESS, message = "网站地址 不能超过 " + MAX_LENGTH_SITE_ADDRESS + " 个字。")
    @LogMessage(name = "网站地址", order = 14)
    @FriendlyProperty("网站地址")
    private String siteAddress;

    /**
     * 站点备案号
     */
    @ApiModelProperty(value = "站点备案号(maxLength =" + MAX_LENGTH_SITE_KEEP_ON_RECORD_NO + ")", required = false, dataType = "String")
    @Length(max = MAX_LENGTH_SITE_KEEP_ON_RECORD_NO, message = "站点备案号 不能超过 " + MAX_LENGTH_SITE_KEEP_ON_RECORD_NO + " 个字。")
    @LogMessage(name = "站点备案号", order = 15)
    @FriendlyProperty("站点备案号")
    private String siteKeepOnRecordNo;

    /**
     * 单位名称
     */
    @ApiModelProperty(value = "单位名称(必输)(maxLength =" + MAX_LENGTH_COMPANY + ")", required = true, dataType = "String")
    @NotNullOrBlank(message = "单位名称不能为空。")
    @Length(max = MAX_LENGTH_COMPANY, message = "单位名称 不能超过 " + MAX_LENGTH_COMPANY + " 个字。")
    @LogMessage(name = "单位名称", order = 16)
    @FriendlyProperty("单位名称")
    private String company;

    /**
     * 单位英文名称
     */
    @ApiModelProperty(value = "单位英文名称(maxLength =" + MAX_LENGTH_COMPANY + ")", required = true, dataType = "String")
    @Length(max = MAX_LENGTH_COMPANY, message = "单位英文名称 不能超过 " + MAX_LENGTH_COMPANY + " 个字。")
    @LogMessage(name = "单位英文名称", order = 17)
    @FriendlyProperty("单位英文名称")
    private String companyEnglish;

    /**
     * 单位简称
     */
    @ApiModelProperty(value = "单位简称(必输)(maxLength =" + MAX_LENGTH_COMPANY_SIMPLE_NAME + ")", required = true, dataType = "String")
    @NotNullOrBlank(message = "单位简称不能为空。")
    @Length(max = MAX_LENGTH_COMPANY_SIMPLE_NAME, message = "单位简称 不能超过 " + MAX_LENGTH_COMPANY_SIMPLE_NAME + " 个字。")
    @LogMessage(name = "单位简称", order = 18)
    @FriendlyProperty("单位简称")
    private String companySimpleName;

    /**
     * 单位英文简称
     */
    @ApiModelProperty(value = "单位英文简称(maxLength =" + MAX_LENGTH_COMPANY_SIMPLE_NAME + ")", required = true, dataType = "String")
    @Length(max = MAX_LENGTH_COMPANY_SIMPLE_NAME, message = "单位英文简称 不能超过 " + MAX_LENGTH_COMPANY_SIMPLE_NAME + " 个字。")
    @LogMessage(name = "单位英文简称", order = 19)
    @FriendlyProperty("单位英文简称")
    private String companyEnglishSimpleName;

    /**
     * 联系电话
     */
    @ApiModelProperty(value = "联系电话(maxLength =" + MAX_LENGTH_CONTACT_PHONE + ")", required = false, dataType = "String")
    @Length(max = MAX_LENGTH_CONTACT_PHONE, message = "联系电话 不能超过 " + MAX_LENGTH_CONTACT_PHONE + " 个字。")
    @LogMessage(name = "联系电话", order = 20)
    @FriendlyProperty("联系电话")
    private String contactPhone;
}
