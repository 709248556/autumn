package com.autumn.cloud.uid.worker;

import com.autumn.cloud.uid.worker.entities.WorkerNode;
import com.autumn.cloud.uid.worker.entities.WorkerNodeType;
import com.autumn.cloud.uid.worker.repositories.WorkerNodeRepository;
import com.autumn.exception.ExceptionUtils;
import com.autumn.mybatis.event.DataSourceListener;
import com.autumn.mybatis.event.TableAutoDefinitionListener;
import com.autumn.mybatis.factory.DynamicDataSourceRouting;
import com.autumn.mybatis.metadata.EntityTable;
import com.autumn.mybatis.provider.DefinitionBuilder;
import com.autumn.mybatis.wrapper.EntityQueryWrapper;
import com.autumn.runtime.AbstractRuntimeComponent;
import com.autumn.timing.Clock;
import com.autumn.util.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.env.Environment;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * 默认数据库工作分配id
 * <p>
 * 默认从 2019-01-01 开始
 * </p>
 *
 * @Description TODO
 * @Author 老码农
 * @Date 2019-08-25 19:58
 */
public class DefaultWorkerNodeAssigner
        extends AbstractRuntimeComponent
        implements WorkerNodeAssigner, DataSourceListener, Ordered {

    private final Log log = LogFactory.getLog(DefaultWorkerNodeAssigner.class);

    public static final String DEFAULT_LOCAL_PORT = "8080";
    public static final String EVN_LOCAL_PORT = "server.port";

    @Autowired
    protected WorkerNodeRepository repository;

    @Autowired
    protected Environment environment;

    private String epochDateString;
    private long epochSeconds;

    public DefaultWorkerNodeAssigner() {
        this.setEpochDateString(DEFAULT_EPOCH_DATE_STRING);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public AssignNode assignWorkerNode() {
        WorkerNode workerNode = this.queryWorkerNode();
        if (workerNode == null) {
            workerNode = this.buildWorkerNode();
            this.repository.insert(workerNode);
        }
        this.getLogger().info("分配Uid工作机(" + workerNode.getId() + "):" + this.getAuditedLogger().getLogDetails(workerNode));
        return workerNode;
    }

    /**
     * 查询工作节点
     *
     * @return
     */
    protected WorkerNode queryWorkerNode() {
        EntityQueryWrapper<WorkerNode> wrapper = new EntityQueryWrapper<>(WorkerNode.class);
        if (DockerUtils.isDocker()) {
            wrapper.where().eq(WorkerNode.FIELD_HOST_NAME, DockerUtils.getDockerHost())
                    .eq(WorkerNode.FIELD_PORT, DockerUtils.getDockerPort())
                    .eq(WorkerNode.FIELD_TYPE, WorkerNodeType.CONTAINER.value());
        } else {
            wrapper.where().eq(WorkerNode.FIELD_HOST_NAME, NetUtils.getLocalAddressString())
                    .eq(WorkerNode.FIELD_PORT, this.getLocalPort())
                    .eq(WorkerNode.FIELD_TYPE, WorkerNodeType.ACTUAL.value());
        }
        wrapper.orderBy(WorkerNode.FIELD_ID).lockByUpdate();
        return this.repository.selectForFirst(wrapper);
    }

    /**
     * 获取本地端口
     *
     * @return
     */
    protected String getLocalPort() {
        String localPort = environment.getProperty(EVN_LOCAL_PORT);
        if (StringUtils.isNullOrBlank(localPort)) {
            return DEFAULT_LOCAL_PORT;
        }
        return localPort;
    }

    /**
     * 生成工作节点
     *
     * @return
     */
    protected WorkerNode buildWorkerNode() {
        WorkerNode workerNode = new WorkerNode();
        if (DockerUtils.isDocker()) {
            workerNode.setType(WorkerNodeType.CONTAINER.value());
            workerNode.setHostName(DockerUtils.getDockerHost());
            workerNode.setPort(DockerUtils.getDockerPort());
        } else {
            workerNode.setType(WorkerNodeType.ACTUAL.value());
            workerNode.setHostName(NetUtils.getLocalAddressString());
            workerNode.setPort(this.getLocalPort());
        }
        workerNode.setLaunchDate(Clock.now());
        workerNode.setEpochSeconds(this.getEpochSeconds());
        workerNode.setEpochDate(new Date(TimeUnit.SECONDS.toMillis(this.getEpochSeconds())));
        //由于注册Bean启动顺序问题，可能造审计尚未注册，因此采用手动审计
        DbAuditingUtils.insertSetProperty(workerNode);
        workerNode.forNullToDefault();
        return workerNode;
    }

    /**
     * 获取起步日期秒数
     *
     * @return
     */
    public long getEpochSeconds() {
        return this.epochSeconds;
    }

    /**
     * 获取起步日期
     *
     * @return
     */
    @Override
    public String getEpochDateString() {
        return this.epochDateString;
    }

    /**
     * 设置起步日期
     *
     * @param epochDateString 起步日期
     */
    @Override
    public void setEpochDateString(String epochDateString) {
        this.epochSeconds = TimeUnit.MILLISECONDS.toSeconds(DateUtils.parseDate(ExceptionUtils.checkNotNull(epochDateString, "epochDateString")).getTime());
        this.epochDateString = epochDateString;
    }

    @Override
    public void init(DataSource dataSource, DynamicDataSourceRouting dataSourceRouting) {
        Connection conn = null;
        try {
            EntityTable table = EntityTable.getTable(WorkerNode.class);
            if (dataSourceRouting.isIncludeTable(table)) {
                DefinitionBuilder definitionBuilder = dataSourceRouting.getProvider().getDefinitionBuilder();
                conn = dataSource.getConnection();
                if (!definitionBuilder.existTable(conn, table)) {
                    definitionBuilder.createTable(conn, table);
                }
            }
        } catch (Exception err) {
            throw ExceptionUtils.throwConfigureException(err.getMessage(), err);
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {

                }
            }
        }
    }

    @Override
    public void close(DataSource dataSource, DynamicDataSourceRouting dataSourceRouting) {

    }

    @Override
    public int getOrder() {
        return TableAutoDefinitionListener.BEAN_BEGIN_ORDER + 1;
    }
}
