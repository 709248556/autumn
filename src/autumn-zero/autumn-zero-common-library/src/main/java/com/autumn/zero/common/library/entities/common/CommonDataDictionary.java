package com.autumn.zero.common.library.entities.common;

import com.autumn.audited.OperationLog;
import com.autumn.mybatis.mapper.annotation.ColumnDocument;
import com.autumn.mybatis.mapper.annotation.ColumnOrder;
import com.autumn.mybatis.mapper.annotation.Index;
import com.autumn.mybatis.mapper.annotation.TableDocument;
import com.autumn.zero.common.library.constants.CommonStatusConstant;
import com.autumn.zero.common.library.entities.AbstractNameUserFullAuditingStatusEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * 公共字典
 *
 * @Description TODO
 * @Author 老码农
 * @Date 2019-07-25 17:46
 */
@ToString(callSuper = true)
@Getter
@Setter
@Table(name = "common_data_dictionary")
@TableDocument(value = "公共字典", group = "公共表", groupOrder = Integer.MAX_VALUE - 100, explain = "统一的通用字典")
public class CommonDataDictionary extends AbstractNameUserFullAuditingStatusEntity implements DataDictionary, OperationLog {

    private static final long serialVersionUID = 3721073838453828850L;

    /**
     * 字段 identification
     */
    public static final String FIELD_IDENTIFICATION = "identification";

    /**
     * 字段 sysDictionary
     */
    public static final String FIELD_IS_SYS_DICTIONARY = "sysDictionary";

    /**
     * 字段 sysDictionaryValue
     */
    public static final String FIELD_SYS_DICTIONARY_VALUE = "sysDictionaryValue";

    /**
     * 字典类型
     */
    @Column(nullable = false)
    @Index(unique = false)
    @NotNull(message = "字典类型不能为空")
    @ColumnOrder(20)
    @ColumnDocument("字典类型")
    private Integer dictionaryType;

    /**
     * 标识
     */
    @ColumnOrder(21)
    @ColumnDocument("标识")
    private Integer identification;

    /**
     * 是否是系统字典
     */
    @Column(name = "is_sys_dictionary", nullable = false)
    @ColumnOrder(22)
    @ColumnDocument("是否是系统字典")
    private boolean sysDictionary;

    /**
     * 系统字典值
     */
    @ColumnOrder(23)
    @ColumnDocument("系统字典值")
    private Integer sysDictionaryValue;

    @Override
    public String logMessage() {
        return "名称=" + this.getName() + ",顺序=" + this.getSortId() + ",状态=" + CommonStatusConstant.getName(this.getStatus());
    }
}
