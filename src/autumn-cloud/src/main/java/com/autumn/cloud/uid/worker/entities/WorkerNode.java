package com.autumn.cloud.uid.worker.entities;

import com.autumn.audited.annotation.LogMessage;
import com.autumn.cloud.uid.worker.AssignNode;
import com.autumn.domain.entities.auditing.gmt.AbstractDefaultGmtModifiedAuditingEntity;
import com.autumn.mybatis.mapper.annotation.ColumnDocument;
import com.autumn.mybatis.mapper.annotation.ColumnOrder;
import com.autumn.mybatis.mapper.annotation.Index;
import com.autumn.mybatis.mapper.annotation.TableDocument;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.Table;
import java.util.Date;

/**
 * 工作节点
 *
 * @Description TODO
 * @Author 老码农
 * @Date 2019-08-25 19:15
 */
@ToString(callSuper = true)
@Getter
@Setter
@Table(name = "sys_uid_worker_node")
@TableDocument(value = "UID工作节点分配表", group = "系统表", groupOrder = Integer.MAX_VALUE)
public class WorkerNode extends AbstractDefaultGmtModifiedAuditingEntity implements AssignNode {

    private static final long serialVersionUID = -8666357977710346213L;

    /**
     * 字段 hostName (主机名称)
     */
    public static final String FIELD_HOST_NAME = "hostName";

    /**
     * 字段 port (主机端口)
     */
    public static final String FIELD_PORT = "port";

    /**
     * 字段 type (类型)
     */
    public static final String FIELD_TYPE = "type";

    /**
     * 字段 epochDate (起步日期)
     */
    public static final String FIELD_EPOCH_DATE = "epochDate";

    /**
     * 字段 epochSeconds (起步日期秒数)
     */
    public static final String FIELD_EPOCH_SECONDS = "epochSeconds";

    /**
     * 字段 launchDate 启动日期)
     */
    public static final String FIELD_LAUNCH_DATE = "launchDate";

    /**
     * 字段 hostName (主机名称或ip) 最大长度
     */
    public static final int MAX_LENGTH_HOST_NAME = 64;

    /**
     * 字段 port (主机端口) 最大长度
     */
    public static final int MAX_LENGTH_PORT = 64;

    /**
     * 主机名称
     */
    @Length(max = MAX_LENGTH_HOST_NAME, message = "主机名称 不能超过 " + MAX_LENGTH_HOST_NAME + " 个字。")
    @Column(nullable = false, length = MAX_LENGTH_HOST_NAME)
    @ColumnOrder(1)
    @Index(unique = false)
    @LogMessage(name = "主机名称", order = 1)
    @ColumnDocument("主机名称")
    private String hostName;

    /**
     * 主机端口 of CONTAINER: Port, ACTUAL : Timestamp + Random(0-10000)
     */
    @Length(max = MAX_LENGTH_PORT, message = "主机端口 不能超过 " + MAX_LENGTH_PORT + " 个字。")
    @Column(nullable = false, length = MAX_LENGTH_PORT)
    @ColumnOrder(2)
    @Index(unique = false)
    @LogMessage(name = "主机端口", order = 2)
    @ColumnDocument("主机端口")
    private String port;

    /**
     * 起步日期
     */
    @Column(nullable = false)
    @ColumnOrder(3)
    @LogMessage(name = "起步日期", order = 3)
    @ColumnDocument("起步日期")
    private Date epochDate;

    /**
     * 起步日期秒数
     */
    @Column(nullable = false)
    @ColumnOrder(4)
    @LogMessage(name = "起步日期秒数", order = 4)
    @ColumnDocument("起步日期秒数")
    private Long epochSeconds;

    /**
     * 类型 of {@link WorkerNodeType}
     */
    @Column(nullable = false)
    @ColumnOrder(5)
    @LogMessage(name = "类型", order = 5)
    @ColumnDocument("类型")
    private int type;

    /**
     * 启动日期
     */
    @Column(nullable = false)
    @ColumnOrder(6)
    @Index(unique = false)
    @LogMessage(name = "启动日期", order = 6)
    @ColumnDocument("启动日期")
    private Date launchDate;

}
