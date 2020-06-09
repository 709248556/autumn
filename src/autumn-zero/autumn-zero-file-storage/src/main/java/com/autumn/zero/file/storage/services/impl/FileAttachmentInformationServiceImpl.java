package com.autumn.zero.file.storage.services.impl;

import com.autumn.validation.DataValidation;
import com.autumn.domain.services.AbstractDomainService;
import com.autumn.file.storage.FileObject;
import com.autumn.file.storage.FileStorageObject;
import com.autumn.file.storage.StorageClient;
import com.autumn.mybatis.wrapper.EntityQueryWrapper;
import com.autumn.runtime.session.AutumnSession;
import com.autumn.util.AutoMapUtils;
import com.autumn.exception.ExceptionUtils;
import com.autumn.zero.file.storage.entities.FileAttachmentInformation;
import com.autumn.zero.file.storage.repositories.FileAttachmentInformationRepository;
import com.autumn.zero.file.storage.services.FileAttachmentInformationService;
import com.autumn.zero.file.storage.services.vo.DefaultUseUploadFileRequest;
import com.autumn.zero.file.storage.services.vo.FileAttachmentInformationRequest;
import com.autumn.zero.file.storage.services.vo.FileAttachmentInformationResponse;
import com.autumn.zero.file.storage.services.vo.UseUploadFileRequest;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

/**
 * @author 老码农 2019-03-17 17:43:43
 */
public class FileAttachmentInformationServiceImpl extends AbstractDomainService
        implements FileAttachmentInformationService {

    @Autowired
    private AutumnSession session;

    @Autowired
    private StorageClient storageClient;

    @Autowired
    private FileAttachmentInformationRepository fileAttachmentInformationRepository;

    /**
     * @throws Exception
     */
    @Override
    public FileAttachmentInformationResponse saveUploadFile(FileAttachmentInformationRequest request) throws Exception {
        ExceptionUtils.checkNotNull(request, "request");
        try {
            FileObject fileObject = storageClient.saveFile(storageClient.getDefaultBucketName(),
                    request.getFullUrlPath(), request.getInputStream());
            try {
                FileAttachmentInformation information = new FileAttachmentInformation();
                information.setFileAttachmentType(request.getFileAttachmentType());
                information.setTargetId(null);
                information.setModuleName(request.getModuleName());
                information.setExtensionName(fileObject.getFileInfo().getExtensionName());
                information.setFileFriendlyLength(fileObject.getFileInfo().getLengthString());
                information.setFileFriendlyName(request.getFileFriendlyName());
                information.setFileLength(fileObject.getFileInfo().getLength());
                information.setFileName(fileObject.getFileInfo().getName());
                information.setUrlPath(fileObject.getFileInfo().getPath());
                information.setUrlFullPath(fileObject.getFileInfo().getFullPath());
                information.setUse(false);
                information.setUploadExplain("");
                information.setUploadUserId(this.session.getUserId());
                information.setUploadUserName(this.session.getUserName());
                information.setUploadTime(new Date());
                information.forNullToDefault();
                fileAttachmentInformationRepository.insert(information);
                FileAttachmentInformationResponse response = AutoMapUtils.map(information,
                        FileAttachmentInformationResponse.class);
                assert response != null;
                response.setAccessUrlPath(fileObject.getAccessUrl());
                return response;

            } catch (Exception e) {
                storageClient.deleteFile(storageClient.getDefaultBucketName(), request.getFullUrlPath());
                throw e;
            }
        } finally {
            IOUtils.closeQuietly(request.getInputStream());
        }
    }

    @Override
    public List<FileAttachmentInformationResponse> useUploadFile(Integer fileAttachmentType, Long targetId,
                                                                 List<Long> uploadIds) {
        List<DefaultUseUploadFileRequest> uploadRequestList = new ArrayList<>();
        if (uploadIds != null) {
            for (Long uploadId : uploadIds) {
                DefaultUseUploadFileRequest req = new DefaultUseUploadFileRequest();
                req.setUploadId(uploadId);
                req.setIdentification(null);
                req.setUploadExplain("");
                uploadRequestList.add(req);
            }
        }
        return this.useUploadFileByIdentification(fileAttachmentType, targetId, uploadRequestList);
    }

    @Override
    public FileAttachmentInformationResponse useUploadFile(Integer fileAttachmentType, Long targetId,
                                                           UseUploadFileRequest useUploadFileRequest) {
        ExceptionUtils.checkNotNull(useUploadFileRequest, "useUploadFileRequest");
        List<UseUploadFileRequest> uploadRequestList = new ArrayList<>();
        uploadRequestList.add(useUploadFileRequest);
        List<FileAttachmentInformationResponse> responses = this.useUploadFileByIdentification(fileAttachmentType, targetId, uploadRequestList);
        if (responses.size() == 0) {
            ExceptionUtils.throwValidationException("指定的上传信息无效，无法使用。");
        }
        return responses.get(0);
    }

    private <R extends UseUploadFileRequest> Map<Long, R> getUploadRequestMap(List<R> uploadRequestList) {
        Map<Long, R> map = new HashMap<>(16);
        if (uploadRequestList != null) {
            for (R useUploadFileRequest : uploadRequestList) {
                if (useUploadFileRequest instanceof DataValidation) {
                    DataValidation dataValidation = (DataValidation) useUploadFileRequest;
                    dataValidation.valid();
                }
                map.put(useUploadFileRequest.getUploadId(), useUploadFileRequest);
            }
        }
        return map;
    }

    @Override
    public <R extends UseUploadFileRequest> List<FileAttachmentInformationResponse> useUploadFileByIdentification(
            Integer fileAttachmentType, Long targetId, List<R> uploadRequestList) {
        this.checkCommonInput(fileAttachmentType, targetId);
        Map<Long, R> map = this.getUploadRequestMap(uploadRequestList);
        List<FileAttachmentInformationResponse> responseList = new ArrayList<>();
        if (map.size() == 0) {
            this.deleteByTarget(fileAttachmentType, targetId);
            return responseList;
        }
        List<FileAttachmentInformation> items = this.queryFileList(fileAttachmentType, targetId);
        for (Map.Entry<Long, R> entry : map.entrySet()) {
            FileAttachmentInformation information = fileAttachmentInformationRepository.get(entry.getKey());
            if (information == null || !information.getFileAttachmentType().equals(fileAttachmentType)) {
                ExceptionUtils.throwValidationException("确认使用的上传目标文件id为" + entry.getKey() + "不存在或未上传。");
            }
            if (!information.isUse() || !targetId.equals(information.getTargetId())) {
                information.setTargetId(targetId);
                information.setUse(true);
                information.setIdentification(entry.getValue().getIdentification());
                information.setUploadExplain(entry.getValue().getUploadExplain());
                information.forNullToDefault();
                fileAttachmentInformationRepository.update(information);
            }
            FileAttachmentInformationResponse response = AutoMapUtils.map(information,
                    FileAttachmentInformationResponse.class);
            assert response != null;
            response.setAccessUrlPath(
                    storageClient.getAccessUrl(storageClient.getDefaultBucketName(), information.getUrlFullPath()));
            responseList.add(response);
        }
        for (FileAttachmentInformation item : items) {
            if (!map.containsKey(item.getId())) {
                this.deleteByStorage(item, true);
            }
        }
        return responseList;
    }

    private void checkCommonInput(Integer fileAttachmentType, Long targetId) {
        ExceptionUtils.checkNotNull(fileAttachmentType, "fileAttachmentType");
        ExceptionUtils.checkNotNull(targetId, "targetId");
    }

    private EntityQueryWrapper<FileAttachmentInformation> createQuery(Integer fileAttachmentType, Long targetId) {
        EntityQueryWrapper<FileAttachmentInformation> query = new EntityQueryWrapper<>(FileAttachmentInformation.class);
        query.where().eq(FileAttachmentInformation.FIELD_FILE_ATTACHMENT_TYPE, fileAttachmentType)
                .eq(FileAttachmentInformation.FIELD_TARGET_ID, targetId).of().orderBy(FileAttachmentInformation.FIELD_ID);
        return query;
    }

    private List<FileAttachmentInformation> queryFileList(Integer fileAttachmentType, Long targetId) {
        EntityQueryWrapper<FileAttachmentInformation> query = this.createQuery(fileAttachmentType, targetId);
        return this.fileAttachmentInformationRepository.selectForList(query);
    }

    @Override
    public List<FileAttachmentInformationResponse> queryByTargetList(Integer fileAttachmentType, Long targetId) {
        this.checkCommonInput(fileAttachmentType, targetId);
        List<FileAttachmentInformation> items = this.queryFileList(fileAttachmentType, targetId);
        return AutoMapUtils.mapForList(items, FileAttachmentInformationResponse.class, this::convertApply);
    }

    private void convertApply(FileAttachmentInformation s, FileAttachmentInformationResponse t) {
        t.setAccessUrlPath(storageClient.getAccessUrl(storageClient.getDefaultBucketName(), s.getUrlFullPath()));
    }

    @Override
    public void deleteByTarget(Integer fileAttachmentType, Long targetId) {
        this.checkCommonInput(fileAttachmentType, targetId);
        EntityQueryWrapper<FileAttachmentInformation> query = this.createQuery(fileAttachmentType, targetId);
        this.deleteByQuery(query);
    }

    @Override
    public void deleteByTarget(Integer fileAttachmentType, Long targetId, Integer identification) {
        this.checkCommonInput(fileAttachmentType, targetId);
        ExceptionUtils.checkNotNull(identification, "identification");
        EntityQueryWrapper<FileAttachmentInformation> query = this.createQuery(fileAttachmentType, targetId);
        query.where().eq(FileAttachmentInformation.FIELD_IDENTIFICATION, identification);
        this.deleteByQuery(query);
    }

    private void deleteByQuery(EntityQueryWrapper<FileAttachmentInformation> query) {
        List<FileAttachmentInformation> items = this.fileAttachmentInformationRepository.selectForList(query);
        for (FileAttachmentInformation item : items) {
            this.deleteByStorage(item, false);
        }
        this.fileAttachmentInformationRepository.deleteByWhere(query);
    }

    private void deleteByStorage(FileAttachmentInformation item, boolean isDeleteDb) {
        try {
            storageClient.deleteFile(storageClient.getDefaultBucketName(), item.getUrlFullPath());
        } catch (Exception e) {
            this.getLogger().error("删除文件 " + item.getUrlFullPath() + " 出错:" + e.getMessage());
        }
        if (isDeleteDb) {
            this.fileAttachmentInformationRepository.deleteById(item.getId());
        }
    }

    @Override
    public FileStorageObject getFile(long id) {
        FileAttachmentInformation fileAttachmentInformation = this.fileAttachmentInformationRepository.get(id);
        return this.getFileStorageObject(fileAttachmentInformation);
    }

    private FileStorageObject getFileStorageObject(FileAttachmentInformation fileAttachmentInformation) {
        if (fileAttachmentInformation == null) {
            return null;
        }
        return storageClient.getFile(storageClient.getDefaultBucketName(), fileAttachmentInformation.getUrlFullPath());
    }

    private FileStorageObject queryFileStorageObjectByQuery(EntityQueryWrapper<FileAttachmentInformation> query) {
        FileAttachmentInformation fileAttachmentInformation = fileAttachmentInformationRepository.selectForFirst(query);
        query.orderBy(FileAttachmentInformation.FIELD_ID);
        return this.getFileStorageObject(fileAttachmentInformation);
    }

    @Override
    public FileStorageObject queryFirstFileByTarget(Integer fileAttachmentType, Long targetId) {
        this.checkCommonInput(fileAttachmentType, targetId);
        EntityQueryWrapper<FileAttachmentInformation> query = this.createQuery(fileAttachmentType, targetId);
        return this.queryFileStorageObjectByQuery(query);
    }

    @Override
    public FileStorageObject queryFirstFileByTarget(Integer fileAttachmentType, Long targetId, Integer identification) {
        this.checkCommonInput(fileAttachmentType, targetId);
        ExceptionUtils.checkNotNull(identification, "identification");
        EntityQueryWrapper<FileAttachmentInformation> query = this.createQuery(fileAttachmentType, targetId);
        query.where().eq(FileAttachmentInformation.FIELD_IDENTIFICATION, identification);
        return this.queryFileStorageObjectByQuery(query);
    }

}
