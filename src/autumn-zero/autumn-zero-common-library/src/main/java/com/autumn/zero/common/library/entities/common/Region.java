package com.autumn.zero.common.library.entities.common;

import com.autumn.audited.annotation.LogMessage;
import com.autumn.domain.entities.auditing.gmt.GmtDeleteAuditing;
import com.autumn.domain.entities.auditing.user.UserDeleteAuditing;
import com.autumn.mybatis.mapper.annotation.ColumnDocument;
import com.autumn.mybatis.mapper.annotation.ColumnOrder;
import com.autumn.mybatis.mapper.annotation.Index;
import com.autumn.mybatis.mapper.annotation.TableDocument;
import com.autumn.zero.common.library.entities.AbstractTreeCodeEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.Table;
import java.util.Date;

/**
 * 行政区
 *
 * @Description TODO
 * @Author 老码农
 * @Date 2019-08-04 21:04
 */
@ToString(callSuper = true)
@Getter
@Setter
@Table(name = "common_region")
@TableDocument(value = "行政区", group = "公共表", groupOrder = Integer.MAX_VALUE - 100)
public class Region extends AbstractTreeCodeEntity implements GmtDeleteAuditing, UserDeleteAuditing {

    private static final long serialVersionUID = -4561236930421300146L;

    /**
     * 字段 fullName (完整名称)
     */
    public static final String FIELD_FULL_NAME = "fullName";

    /**
     * 字段 firstPinyin (拼音首个字母)
     */
    public static final String FIELD_FIRST_PINYIN = "firstPinyin";

    /**
     * 字段 pinyin (完整拼音)
     */
    public static final String FIELD_PINYIN = "pinyin";

    /**
     * 字段 firstPinyin (拼音首个字母) 最大长度
     */
    public static final int MAX_LENGTH_FIRST_PINYIN = 50;

    /**
     * 字段 pinyin (完整拼音) 最大长度
     */
    public static final int MAX_LENGTH_PINYIN = 100;

    /**
     * 拼音首个字母
     */
    @Length(max = MAX_LENGTH_FIRST_PINYIN, message = "拼音首个字母 不能超过 " + MAX_LENGTH_FIRST_PINYIN + " 个字。")
    @Column(nullable = false, length = MAX_LENGTH_FIRST_PINYIN)
    @ColumnOrder(80)
    @Index(unique = false)
    @LogMessage(name = "拼音首个字母", order = 80)
    @ColumnDocument("拼音首个字母")
    private String firstPinyin;

    /**
     * 完整拼音
     */
    @Length(max = MAX_LENGTH_PINYIN, message = "完整拼音 不能超过 " + MAX_LENGTH_PINYIN + " 个字。")
    @Column(nullable = false, length = MAX_LENGTH_PINYIN)
    @ColumnOrder(81)
    @Index(unique = false)
    @LogMessage(name = "完整拼音", order = 81)
    @ColumnDocument("完整拼音")
    private String pinyin;

    @ColumnOrder(10200)
    @Column(name = COLUMN_IS_DELETE, nullable = false)
    @ColumnDocument("是否删除")
    private boolean delete;

    @ColumnOrder(10201)
    @Column(name = COLUMN_GMT_DELETE)
    @ColumnDocument("删除时间")
    private Date gmtDelete;

    @ColumnOrder(20200)
    @Column(name = COLUMN_DELETED_USER_ID, nullable = true)
    @Index
    @ColumnDocument("删除用户id")
    private Long deletedUserId;

    @ColumnOrder(20201)
    @Length(max = MAX_AUDITING_USER_NAME, message = "删除用户名称 不能超过 " + MAX_AUDITING_USER_NAME + " 个字。")
    @Column(name = COLUMN_DELETED_USER_NAME, nullable = false, length = MAX_AUDITING_USER_NAME)
    @ColumnDocument("删除用户名称")
    private String deletedUserName;

}
