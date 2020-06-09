package com.autumn.zero.file.storage.repositories;

import org.springframework.stereotype.Repository;

import com.autumn.domain.repositories.DefaultEntityRepository;
import com.autumn.zero.file.storage.entities.FileAttachmentInformation;

/**
 * 文件附件信息仓储
 * 
 * @author 老码农 2019-03-17 17:35:55
 */
@Repository
public interface FileAttachmentInformationRepository extends DefaultEntityRepository<FileAttachmentInformation> {

}

