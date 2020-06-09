package com.autumn.zero.authorization.entities.common.log;

import com.autumn.constants.SettingConstants;
import com.autumn.mybatis.mapper.annotation.*;
import com.autumn.util.StringUtils;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.ibatis.type.JdbcType;

import javax.persistence.Column;
import javax.persistence.Table;

/**
 * 用户操作日志
 *
 * @Description TODO
 * @Author 老码农
 * @Date 2019-07-28 2:08
 */
@Getter
@Setter
@ToString(callSuper = true)
@Table(name = SettingConstants.SYS_TABLE_PREFIX + "_user_operation_log")
@TableEngine("MyISAM")
@TableDocument(value = "用户操作日志", group = "系统表", groupOrder = Integer.MAX_VALUE, explain = "记录用户的操作行为")
public class UserOperationLog extends AbstractLog {

    private static final long serialVersionUID = 5840285781662855345L;

    /**
     * 字段 moduleName (模块名称)
     */
    public static final String FIELD_MODULE_NAME = "moduleName";

    /**
     * 字段 operationName (操作名称)
     */
    public static final String FIELD_OPERATION_NAME = "operationName";

    /**
     * 字段 logDetails (操作详情)
     */
    public static final String FIELD_LOG_DETAILS = "logDetails";

    /**
     * 字段 moduleName (模块名称) 最大长度
     */
    public static final int MAX_LENGTH_MODULE_NAME = 100;

    /**
     * 字段 operationName (操作名称) 最大长度
     */
    public static final int MAX_LENGTH_OPERATION_NAME = 100;

    /**
     * 模块名称
     */
    @Column(nullable = false, length = MAX_LENGTH_MODULE_NAME)
    @ColumnOrder(10)
    @Index(unique = false)
    @ColumnDocument("模块名称")
    private String moduleName;

    /**
     * 操作名称
     */
    @Column(nullable = false, length = MAX_LENGTH_OPERATION_NAME)
    @ColumnOrder(11)
    @Index(unique = false)
    @ColumnDocument("操作名称")
    private String operationName;

    /**
     * 操作详情
     */
    @ColumnType(jdbcType = JdbcType.LONGNVARCHAR)
    @Column(nullable = false)
    @ColumnOrder(12)
    @ColumnDocument("操作详情")
    private String logDetails;

    @Override
    public void forNullToDefault() {
        super.forNullToDefault();
        this.setModuleName(StringUtils.getLeft(this.getModuleName(), MAX_LENGTH_MODULE_NAME));
        this.setOperationName(StringUtils.getLeft(this.getOperationName(), MAX_LENGTH_OPERATION_NAME));
    }
}
