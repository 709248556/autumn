package com.autumn.cloud.uid.worker.repositories;

import com.autumn.cloud.uid.worker.entities.WorkerNode;
import com.autumn.domain.repositories.DefaultEntityRepository;
import org.springframework.stereotype.Repository;

/**
 * 工作节点仓储
 *
 * @Description TODO
 * @Author 老码农
 * @Date 2019-08-25 19:17
 */
@Repository
public interface WorkerNodeRepository extends DefaultEntityRepository<WorkerNode> {

}
