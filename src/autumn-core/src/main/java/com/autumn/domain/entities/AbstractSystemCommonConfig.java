package com.autumn.domain.entities;

import com.autumn.annotation.FriendlyProperty;
import com.autumn.mybatis.mapper.annotation.ColumnDocument;
import com.autumn.mybatis.mapper.annotation.ColumnOrder;
import com.autumn.mybatis.mapper.annotation.ColumnType;
import com.autumn.mybatis.mapper.annotation.Index;
import com.autumn.validation.annotation.NotNullOrBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.ibatis.type.JdbcType;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;

/**
 * 系统公共配置
 *
 * @Description TODO
 * @Author 老码农
 * @Date 2019-07-31 0:36
 */
@ToString(callSuper = true)
@Getter
@Setter
public abstract class AbstractSystemCommonConfig extends AbstractDefaultEntity {

    private static final long serialVersionUID = 5841732198830412454L;

    /**
     *  字段 configType (配置类型)
     */
    public static final String FIELD_CONFIG_TYPE = "configType";

    /**
     *  字段 moduleName (模块名称)
     */
    public static final String FIELD_MODULE_NAME = "moduleName";

    /**
     *  字段 name (名称)
     */
    public static final String FIELD_NAME = "name";

    /**
     *  字段 type (类型)
     */
    public static final String FIELD_TYPE = "type";

    /**
     *  字段 title (标题)
     */
    public static final String FIELD_TITLE = "title";

    /**
     *  字段 value (配置值)
     */
    public static final String FIELD_VALUE = "value";

    /**
     *  字段 configType (配置类型) 最大长度
     */
    public static final int MAX_LENGTH_CONFIG_TYPE = 100;

    /**
     *  字段 moduleName (模块名称) 最大长度
     */
    public static final int MAX_LENGTH_MODULE_NAME = 50;

    /**
     *  字段 name (名称) 最大长度
     */
    public static final int MAX_LENGTH_NAME = 100;

    /**
     *  字段 type (类型) 最大长度
     */
    public static final int MAX_LENGTH_TYPE = 255;

    /**
     *  字段 title (标题) 最大长度
     */
    public static final int MAX_LENGTH_TITLE = 100;

    /**
     * 配置类型
     */
    @Length(max = MAX_LENGTH_CONFIG_TYPE, message = "配置类型 不能超过 " + MAX_LENGTH_CONFIG_TYPE + " 个字。")
    @Column(nullable = false, length = MAX_LENGTH_CONFIG_TYPE)
    @ColumnOrder(1)
    @Index(unique = false)
    @ColumnDocument("配置类型")
    @FriendlyProperty(value = "配置类型")
    private String configType;

    /**
     * 模块名称
     */
    @NotNullOrBlank(message = "模块名称不能为空。")
    @Length(max = MAX_LENGTH_MODULE_NAME, message = "模块名称 不能超过 " + MAX_LENGTH_MODULE_NAME + " 个字。")
    @Column(nullable = false, length = MAX_LENGTH_MODULE_NAME)
    @ColumnOrder(2)
    @ColumnDocument("模块名称")
    @FriendlyProperty(value = "模块名称")
    private String moduleName;

    /**
     * 名称
     */
    @NotNullOrBlank(message = "名称不能为空。")
    @Length(max = MAX_LENGTH_NAME, message = "名称 不能超过 " + MAX_LENGTH_NAME + " 个字。")
    @Column(nullable = false, length = MAX_LENGTH_NAME)
    @ColumnOrder(3)
    @Index(unique = false)
    @ColumnDocument("名称")
    @FriendlyProperty(value = "名称")
    private String name;

    /**
     * 类型
     */
    @Length(max = MAX_LENGTH_TYPE, message = "类型 不能超过 " + MAX_LENGTH_TYPE + " 个字。")
    @Column(nullable = false, length = MAX_LENGTH_TYPE)
    @ColumnOrder(4)
    @ColumnDocument("类型")
    @FriendlyProperty(value = "类型")
    private String type;

    /**
     * 标题
     */
    @Length(max = MAX_LENGTH_TITLE, message = "标题 不能超过 " + MAX_LENGTH_TITLE + " 个字。")
    @Column(nullable = false, length = MAX_LENGTH_TITLE)
    @ColumnOrder(5)
    @ColumnDocument("标题")
    @FriendlyProperty(value = "标题")
    private String title;

    /**
     * 配置值
     */
    @ColumnType(jdbcType = JdbcType.LONGNVARCHAR)
    @Column(nullable = false)
    @ColumnOrder(6)
    @ColumnDocument("配置值")
    @FriendlyProperty(value = "配置值")
    private String value;
}
