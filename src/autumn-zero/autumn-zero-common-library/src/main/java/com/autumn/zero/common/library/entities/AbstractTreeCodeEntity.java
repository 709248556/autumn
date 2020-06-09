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
 * 具有代码的树实体抽象
 *
 * @Description TODO
 * @Author 老码农
 * @Date 2019-08-04 3:15
 */
@ToString(callSuper = true)
@Getter
@Setter
public abstract class AbstractTreeCodeEntity extends AbstractTreeEntity {

    private static final long serialVersionUID = -8940976291185350428L;

    /**
     * 字段 code (代码)
     */
    public static final String FIELD_CODE = "code";

    /**
     * 字段 fullCode (完整代码)
     */
    public static final String FIELD_FULL_CODE = "fullCode";

    /**
     * 字段 code (代码) 最大长度
     */
    public static final int MAX_LENGTH_CODE = 100;

    /**
     * 字段 fullCode (完整代码) 最大长度
     */
    public static final int MAX_LENGTH_FULL_CODE = 400;

    /**
     * 代码
     */
    @Length(max = MAX_LENGTH_CODE, message = "代码 不能超过 " + MAX_LENGTH_CODE + " 个字。")
    @Column(nullable = false, length = MAX_LENGTH_CODE)
    @ColumnOrder(10)
    @Index(unique = false)
    @LogMessage(name = "代码", order = 10)
    @ColumnDocument("代码")
    private String code;

    /**
     * 完整代码
     */
    @Length(max = MAX_LENGTH_FULL_CODE, message = "完整代码 不能超过 " + MAX_LENGTH_FULL_CODE + " 个字。")
    @Column(nullable = false, length = MAX_LENGTH_FULL_CODE)
    @ColumnOrder(31)
    @LogMessage(name = "完整代码", order = 31)
    @ColumnDocument("完整代码")
    private String fullCode;

}
