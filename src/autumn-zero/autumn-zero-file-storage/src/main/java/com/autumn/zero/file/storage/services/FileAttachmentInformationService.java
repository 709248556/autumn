package com.autumn.zero.file.storage.services;

import com.autumn.domain.services.DomainService;
import com.autumn.file.storage.FileStorageObject;
import com.autumn.zero.file.storage.services.vo.FileAttachmentInformationRequest;
import com.autumn.zero.file.storage.services.vo.FileAttachmentInformationResponse;
import com.autumn.zero.file.storage.services.vo.UseUploadFileRequest;

import java.util.List;

/**
 * 文件附件信息抽象服务
 *
 * @author 老码农 2019-03-17 17:43:01
 */
public interface FileAttachmentInformationService extends DomainService {

    /**
     * 保存上传文件
     *
     * @param request 请求
     * @return
     */
    FileAttachmentInformationResponse saveUploadFile(FileAttachmentInformationRequest request) throws Exception;

    /**
     * 使用上传文件
     *
     * @param fileAttachmentType 文件类型id
     * @param targetId           目标id
     * @param uploadIds          上传文件id
     * @return
     */
    List<FileAttachmentInformationResponse> useUploadFile(Integer fileAttachmentType, Long targetId,
                                                          List<Long> uploadIds);

    /**
     * 使用上传文件
     *
     * @param fileAttachmentType   文件类型id
     * @param targetId             目标id
     * @param useUploadFileRequest 使用文件请求
     * @return
     */
    FileAttachmentInformationResponse useUploadFile(Integer fileAttachmentType, Long targetId, UseUploadFileRequest useUploadFileRequest);

    /**
     * 使用上传文件，含标识
     *
     * @param fileAttachmentType 文件类型id
     * @param targetId           目标id
     * @param uploadRequestList  上传文件请求列表
     * @return
     */
    <R extends UseUploadFileRequest> List<FileAttachmentInformationResponse> useUploadFileByIdentification(
            Integer fileAttachmentType, Long targetId, List<R> uploadRequestList);

    /**
     * 查询上传文件
     *
     * @param fileAttachmentType 文件类型id
     * @param targetId           目标id
     * @return
     */
    List<FileAttachmentInformationResponse> queryByTargetList(Integer fileAttachmentType, Long targetId);

    /**
     * 删除上传文件
     *
     * @param fileAttachmentType 文件类型id
     * @param targetId           目标id
     * @return
     */
    void deleteByTarget(Integer fileAttachmentType, Long targetId);

    /**
     * 删除上传文件
     *
     * @param fileAttachmentType 文件类型id
     * @param targetId           目标id
     * @param identification     标识
     * @return
     */
    void deleteByTarget(Integer fileAttachmentType, Long targetId, Integer identification);

    /**
     * 获取文件
     *
     * @param id 文件id
     * @return
     */
    FileStorageObject getFile(long id);

    /**
     * 根据目标首个文件
     *
     * @param fileAttachmentType 附件类型
     * @param targetId           目标id
     * @return
     */
    FileStorageObject queryFirstFileByTarget(Integer fileAttachmentType, Long targetId);

    /**
     * 根据目标首个文件
     *
     * @param fileAttachmentType 附件类型
     * @param targetId           目标id
     * @param identification     标识
     * @return
     */
    FileStorageObject queryFirstFileByTarget(Integer fileAttachmentType, Long targetId, Integer identification);
}
