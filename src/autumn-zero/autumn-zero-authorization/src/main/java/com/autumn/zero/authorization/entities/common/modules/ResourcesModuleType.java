package com.autumn.zero.authorization.entities.common.modules;

import com.autumn.audited.annotation.LogMessage;
import com.autumn.constants.SettingConstants;
import com.autumn.domain.entities.AbstractDefaultEntity;
import com.autumn.domain.entities.EntityDataBean;
import com.autumn.mybatis.mapper.annotation.*;
import com.autumn.validation.DataValidation;
import com.autumn.validation.annotation.NotNullOrBlank;
import lombok.ToString;
import org.apache.ibatis.type.JdbcType;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * 资源模块类型
 *
 * @author 老码农 2019-03-06 08:58:18
 */
@ToString(callSuper = true)
@Table(name = SettingConstants.SYS_TABLE_PREFIX + "_res_module_type")
@TableDocument(value = "资源模块类型", group = "系统表", groupOrder = Integer.MAX_VALUE)
public class ResourcesModuleType extends AbstractDefaultEntity implements DataValidation, EntityDataBean {

    /**
     *
     */
    private static final long serialVersionUID = 4312978008653150548L;

    /**
     * 字段 name
     */
    public static final String FIELD_NAME = "name";

    /**
     * 最大名称长度
     */
    public static final int MAX_NAME_LENGTH = 50;

    /**
     * 主键，需手动指定
     *
     * @return
     */
    @NotNull(message = "ResourcesModuleType 的 id 不能为空 null 。")
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Override
    public Long getId() {
        return super.getId();
    }

    @NotNullOrBlank(message = "名称不能为空")
    @Length(max = MAX_NAME_LENGTH, message = "名称长度不能超过" + MAX_NAME_LENGTH + "个字。")
    @Column(name = "name", nullable = false, length = MAX_NAME_LENGTH)
    @ColumnType(jdbcType = JdbcType.VARCHAR)
    @Index(unique = true)
    @ColumnOrder(2)
    @LogMessage(name = "名称", order = 1)
    @ColumnDocument("名称")
    private String name;

    /**
     * 获取资源名称
     *
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * 设置资源名称
     *
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }
}
