package com.autumn.zero.common.library.entities.sys;

import com.autumn.audited.annotation.LogMessage;
import com.autumn.mybatis.mapper.annotation.*;
import com.autumn.validation.annotation.NotNullOrBlank;
import com.autumn.zero.common.library.entities.AbstractTreeEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.ibatis.type.JdbcType;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.Table;
import java.util.Date;

/**
 * 系统帮助文档
 * <p>
 * </p>
 *
 * @description: TODO
 * @author: 老码农
 * @create: 2019-11-29 10:07
 **/
@ToString(callSuper = true)
@Getter
@Setter
@Table(name = "sys_help_document")
@TableDocument(value = "系统帮助文档", group = "系统表", groupOrder = Integer.MAX_VALUE)
public class SystemHelpDocument extends AbstractTreeEntity {

    private static final long serialVersionUID = 2282610011058852843L;

    /**
     * 字段 friendlyName (友好名称)
     */
    public static final String FIELD_FRIENDLY_NAME = "friendlyName";

    /**
     * 字段 directory (是否为目录)
     */
    public static final String FIELD_DIRECTORY = "directory";

    /**
     * 字段 urlFullPath (url路径)
     */
    public static final String FIELD_URL_FULL_PATH = "urlFullPath";

    /**
     * 字段 accessPath (访问路径)
     */
    public static final String FIELD_ACCESS_PATH = "accessPath";

    /**
     * 字段 generate (是否生成)
     */
    public static final String FIELD_GENERATE = "generate";

    /**
     * 字段 generateTime (生成时间)
     */
    public static final String FIELD_GENERATE_TIME = "generateTime";

    /**
     * 字段 fileLength (文件大小)
     */
    public static final String FIELD_FILE_LENGTH = "fileLength";

    /**
     * 字段 fileFriendlyLength (文件友好大小)
     */
    public static final String FIELD_FILE_FRIENDLY_LENGTH = "fileFriendlyLength";

    /**
     * 字段 htmlContent (Html内容)
     */
    public static final String FIELD_HTML_CONTENT = "htmlContent";

    /**
     * 字段 urlFullPath (url路径) 最大长度
     */
    public static final int MAX_LENGTH_URL_FULL_PATH = 500;

    /**
     * 字段 accessPath (访问路径) 最大长度
     */
    public static final int MAX_LENGTH_ACCESS_PATH = 500;

    /**
     * 字段 fileFriendlyLength (文件友好大小) 最大长度
     */
    public static final int MAX_LENGTH_FILE_FRIENDLY_LENGTH = 50;

    /**
     * 友好名称
     */
    @Column(nullable = false, length = MAX_NAME_LENGTH)
    @NotNullOrBlank(message = "友好名称不能为空")
    @ColumnOrder(1)
    @Index(unique = false)
    @LogMessage(name = "友好名称", order = 1)
    @ColumnDocument("友好名称")
    private String friendlyName;

    /**
     * 是否为目录
     */
    @Column(name = "is_directory", nullable = false)
    @ColumnOrder(2)
    @ColumnDocument("是否为目录")
    private boolean directory;

    /**
     * url路径
     */
    @Length(max = MAX_LENGTH_URL_FULL_PATH, message = "url路径 不能超过 " + MAX_LENGTH_URL_FULL_PATH + " 个字。")
    @Column(nullable = false, length = MAX_LENGTH_URL_FULL_PATH)
    @ColumnOrder(3)
    @ColumnDocument("url路径")
    private String urlFullPath;

    /**
     * 访问路径
     */
    @Length(max = MAX_LENGTH_ACCESS_PATH, message = "访问路径 不能超过 " + MAX_LENGTH_ACCESS_PATH + " 个字。")
    @Column(nullable = false, length = MAX_LENGTH_ACCESS_PATH)
    @ColumnOrder(4)
    @ColumnDocument("访问路径")
    private String accessPath;

    /**
     * 是否生成
     */
    @Column(name = "is_generate", nullable = false)
    @ColumnOrder(5)
    @ColumnDocument("是否生成")
    private boolean generate;

    /**
     * 生成时间
     */
    @Column(nullable = true)
    @ColumnOrder(6)
    @ColumnDocument("生成时间")
    private Date generateTime;

    /**
     * 文件大小
     */
    @Column(nullable = true)
    @ColumnOrder(7)
    @ColumnDocument("文件大小(字节数)")
    private Long fileLength;

    /**
     * 文件友好大小
     */
    @Length(max = MAX_LENGTH_FILE_FRIENDLY_LENGTH, message = "文件友好大小 不能超过 " + MAX_LENGTH_FILE_FRIENDLY_LENGTH + " 个字。")
    @Column(nullable = false, length = MAX_LENGTH_FILE_FRIENDLY_LENGTH)
    @ColumnOrder(8)
    @ColumnDocument("文件友好大小")
    private String fileFriendlyLength;

    /**
     * Html内容
     */
    @ColumnType(jdbcType = JdbcType.LONGNVARCHAR)
    @Column(nullable = false)
    @ColumnOrder(9)
    @ColumnDocument("Html内容")
    private String htmlContent;
}
