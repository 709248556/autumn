package com.autumn.zero.common.library.entities;

import com.autumn.audited.annotation.LogMessage;
import com.autumn.mybatis.mapper.annotation.ColumnDocument;
import com.autumn.mybatis.mapper.annotation.ColumnOrder;
import com.autumn.mybatis.mapper.annotation.Index;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;

/**
 * 树实体抽象
 *
 * @Description TODO
 * @Author 老码农
 * @Date 2019-08-04 3:12
 */
@ToString(callSuper = true)
@Getter
@Setter
public abstract class AbstractTreeEntity extends AbstractNameUserAuditingStatusEntity {

    private static final long serialVersionUID = -315814204025639783L;

    /**
     * 字段 parentId (父级id)
     */
    public static final String FIELD_PARENT_ID = "parentId";

    /**
     * 字段 level (级别)
     */
    public static final String FIELD_LEVEL = "level";

    /**
     *  字段 childrenCount (子级数量)
     */
    public static final String FIELD_CHILDREN_COUNT = "childrenCount";

    /**
     * 字段 fullId (完整id)
     */
    public static final String FIELD_FULL_ID = "fullId";

    /**
     * 字段 fullName (完整名称)
     */
    public static final String FIELD_FULL_NAME = "fullName";

    /**
     * 字段 fullId (完整id) 最大长度
     */
    public static final int MAX_LENGTH_FULL_ID = 400;

    /**
     * 字段 fullName (完整名称) 最大长度
     */
    public static final int MAX_LENGTH_FULL_NAME = 400;

    /**
     * 父级id
     */
    @Column(nullable = true)
    @ColumnOrder(20)
    @Index(unique = false)
    @ColumnDocument("父级id")
    private Long parentId;

    /**
     * 级别
     */
    @Column(nullable = false)
    @ColumnOrder(21)
    @Index(unique = false)
    @LogMessage(name = "级别", order = 21)
    @ColumnDocument("级别")
    private Integer level;

    /**
     * 子级数量
     */
    @Column(nullable = false)
    @ColumnOrder(22)
    @LogMessage(name = "子级数量", order = 22)
    @ColumnDocument("子级数量")
    private Integer childrenCount;

    /**
     * 完整id
     */
    @Length(max = MAX_LENGTH_FULL_ID, message = "完整id 不能超过 " + MAX_LENGTH_FULL_ID + " 个字。")
    @Column(nullable = false, length = MAX_LENGTH_FULL_ID)
    @ColumnOrder(30)
    @Index(unique = false)
    @ColumnDocument("完整id")
    private String fullId;

    /**
     * 完整名称
     */
    @Length(max = MAX_LENGTH_FULL_NAME, message = "完整名称 不能超过 " + MAX_LENGTH_FULL_NAME + " 个字。")
    @Column(nullable = false, length = MAX_LENGTH_FULL_NAME)
    @ColumnOrder(40)
    @LogMessage(name = "完整名称", order = 40)
    @ColumnDocument("完整名称")
    private String fullName;
}
