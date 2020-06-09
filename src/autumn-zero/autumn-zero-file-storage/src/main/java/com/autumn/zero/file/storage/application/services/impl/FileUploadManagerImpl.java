package com.autumn.zero.file.storage.application.services.impl;

import com.autumn.file.storage.FileObject;
import com.autumn.file.storage.StorageClient;
import com.autumn.util.AutoMapUtils;
import com.autumn.util.ByteUtils;
import com.autumn.exception.ExceptionUtils;
import com.autumn.util.StringUtils;
import com.autumn.zero.file.storage.FileStorageUtils;
import com.autumn.zero.file.storage.application.dto.*;
import com.autumn.zero.file.storage.application.services.FileUploadAppService;
import com.autumn.zero.file.storage.application.services.FileUploadManager;
import com.autumn.zero.file.storage.services.FileAttachmentInformationService;
import com.autumn.zero.file.storage.services.vo.DefaultUseUploadFileRequest;
import com.autumn.zero.file.storage.services.vo.FileAttachmentInformationRequest;
import com.autumn.zero.file.storage.services.vo.FileAttachmentInformationResponse;
import com.autumn.zero.file.storage.services.vo.UseUploadFileRequest;
import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;

/**
 * 文件上传管理
 *
 * @author 老码农 2019-03-18 18:21:34
 */
public class FileUploadManagerImpl implements FileUploadManager {

    /**
     * 日志
     */
    private static final Log logger = LogFactory.getLog(FileUploadManagerImpl.class);

    @Autowired
    private FileAttachmentInformationService fileService;

    @Autowired
    private StorageClient storageClient;

    @Override
    public final FileAttachmentInformationService getFileService() {
        return this.fileService;
    }

    @Override
    public FileAttachmentInformationResponse saveUploadFile(FullFileInput input) throws Exception {
        ExceptionUtils.checkNotNull(input, "input");
        input.valid();
        if (input.getOriginalFileSize() != null && input.getOriginalFileSize() > 0 && input.getLimitSize() != null
                && input.getLimitSize() > 0 && input.getOriginalFileSize() > input.getLimitSize()) {
            IOUtils.closeQuietly(input.getInputStream());
            ExceptionUtils
                    .throwValidationException("上传文件太大，超过限制大小[" + ByteUtils.getFileSize(input.getLimitSize()) + "]");
        }
        input.setUrlPath(StringUtils.removeStart(StringUtils.removeEnd(input.getUrlPath(), '/'), '/'));
        FileAttachmentInformationRequest request = new FileAttachmentInformationRequest(input.getFileAttachmentType(),
                input.getUrlPath(), input.getOriginalFilename(), input.getInputStream());
        if (input.getLimitExtensions() != null && input.getLimitExtensions().size() > 0
                && !input.getLimitExtensions().contains("*")) {
            if (StringUtils.isNullOrBlank(request.getExtensionName())
                    || !input.getLimitExtensions().contains(request.getExtensionName())) {
                IOUtils.closeQuietly(input.getInputStream());
                ExceptionUtils.throwValidationException("上传的文件类型不支持。");
            }
        }
        request.setModuleName(input.getModuleName());
        return fileService.saveUploadFile(request);
    }

    @Override
    public FileAttachmentInformationResponse saveUploadFile(FileUploadAppService service, FileInput input,
                                                            String currentUrlPath) throws Exception {
        ExceptionUtils.checkNotNull(input, "input");
        input.valid();
        if (service == null) {
            IOUtils.closeQuietly(input.getInputStream());
            ExceptionUtils.throwValidationException("应用服务不能为 null。");
        }
        FullFileInput fuleInput = new FullFileInput();
        fuleInput.setFileAttachmentType(service.getFileUploadAttachmentType());
        fuleInput.setInputStream(input.getInputStream());
        fuleInput.setLimitExtensions(service.getFileUploadLimitExtensions());
        fuleInput.setLimitSize(service.getFileUploadLimitSize());
        fuleInput.setOriginalFilename(input.getOriginalFilename());
        fuleInput.setOriginalFileSize(input.getOriginalFileSize());
        fuleInput.setModuleName(service.getModuleName());
        String url;
        if (StringUtils.isNullOrBlank(currentUrlPath)) {
            url = StringUtils.removeStart(service.getFileUploadStartPath(), '/');
        } else {
            url = StringUtils.removeEnd(service.getFileUploadStartPath(), '/') + "/"
                    + StringUtils.removeStart(currentUrlPath, '/');
        }
        fuleInput.setUrlPath(url);
        return this.saveUploadFile(fuleInput);
    }

    @Override
    public TemporaryFileInformationDto saveTemporaryFile(String originalFilename, InputStream inputStream) throws Exception {
        ExceptionUtils.checkNotNullOrBlank(originalFilename, "originalFilename");
        ExceptionUtils.checkNotNull(inputStream, "inputStream");
        int index = originalFilename.lastIndexOf(".");
        String id = UUID.randomUUID().toString().replace("-", "");
        String fileFriendlyName;
        String fullUrlPath;
        if (index >= 0) {
            fileFriendlyName = originalFilename.substring(0, index);
            String extensionName = originalFilename.substring(index + 1).toLowerCase();
            fullUrlPath = TEMP_PATH + "/" + id + "." + extensionName;
        } else {
            fileFriendlyName = originalFilename;
            fullUrlPath = TEMP_PATH + "/" + id;
        }
        try {
            TemporaryFileInformationDto information = new TemporaryFileInformationDto();
            FileObject fileObject = storageClient.saveFile(storageClient.getDefaultBucketName(), fullUrlPath, inputStream);
            information.setAccessUrlPath(fileObject.getAccessUrl());
            information.setUrlPath(fileObject.getFileInfo().getPath());
            information.setUrlFullPath(fileObject.getFileInfo().getFullPath());
            information.setExtensionName(fileObject.getFileInfo().getExtensionName());
            information.setFileFriendlyLength(fileObject.getFileInfo().getLengthString());
            information.setFileFriendlyName(fileFriendlyName);
            information.setFileName(fileObject.getFileInfo().getName());
            information.setFileLength(fileObject.getFileInfo().getLength());
            return information;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public TemporaryFileInformationDto saveTemporaryFileByWorkbook(String originalFilename, Workbook workbook) throws Exception {
        ExceptionUtils.checkNotNull(workbook, "workbook");
        return this.writeTemporaryFile(originalFilename, stream ->
                {
                    try {
                        workbook.write(stream);
                    } catch (IOException e) {
                        throw ExceptionUtils.throwApplicationException(e.getMessage(), e);
                    }
                }
        );
    }

    @Override
    public TemporaryFileInformationDto writeTemporaryFile(String originalFilename, Consumer<OutputStream> streamConsumer) throws Exception {
        ExceptionUtils.checkNotNullOrBlank(originalFilename, "originalFilename");
        ExceptionUtils.checkNotNull(streamConsumer, "streamConsumer");
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ByteArrayInputStream inputStream = null;
        try {
            streamConsumer.accept(outputStream);
            inputStream = new ByteArrayInputStream(outputStream.toByteArray());
            return this.saveTemporaryFile(originalFilename, inputStream);
        } finally {
            IOUtils.closeQuietly(outputStream);
            IOUtils.closeQuietly(inputStream);
        }
    }

    private boolean existUploadFile(List<DefaultUseUploadFileRequest> uploadFiles, Long uploadId) {
        for (DefaultUseUploadFileRequest uploadFile : uploadFiles) {
            if (uploadFile.getUploadId() != null && uploadFile.getUploadId().equals(uploadId)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public List<FileAttachmentInformationResponse> useUploadFiles(int fileUploadAttachmentType, Long targetId, FileUploadInput fileUploadInput) {
        List<DefaultUseUploadFileRequest> uploadFiles;
        FileUploadIdentificationInput bindingInput = FileStorageUtils.createBindingFileUploadIdentificationInput(fileUploadInput);
        if (bindingInput != null) {
            uploadFiles = bindingInput.getUploadFiles();
        } else {
            uploadFiles = new ArrayList<>(16);
        }
        if (fileUploadInput instanceof FileUploadIdentificationInput) {
            FileUploadIdentificationInput identificationInput = (FileUploadIdentificationInput) fileUploadInput;
            if (identificationInput.getUploadFiles() != null) {
                for (DefaultUseUploadFileRequest uploadFile : identificationInput.getUploadFiles()) {
                    if (uploadFile.getUploadId() != null && !existUploadFile(uploadFiles, uploadFile.getUploadId())) {
                        uploadFiles.add(uploadFile);
                    }
                }
            }
        }
        if (fileUploadInput instanceof FileUploadIdInput) {
            FileUploadIdInput idInput = (FileUploadIdInput) fileUploadInput;
            if (idInput.getUploadFileIds() != null) {
                for (Long uploadFileId : idInput.getUploadFileIds()) {
                    if (uploadFileId != null && !existUploadFile(uploadFiles, uploadFileId)) {
                        DefaultUseUploadFileRequest request = new DefaultUseUploadFileRequest();
                        request.setUploadId(uploadFileId);
                        uploadFiles.add(request);
                    }
                }
            }
        }
        return this.getFileService().useUploadFileByIdentification(fileUploadAttachmentType, targetId, uploadFiles);
    }

    @Override
    public List<FileAttachmentInformationResponse> useUploadFiles(FileUploadAppService service, Long targetId,
                                                                  FileUploadInput fileUploadInput) {
        return this.useUploadFiles(service.getFileUploadAttachmentType(), targetId, fileUploadInput);
    }

    @Override
    public <E extends UseUploadFileRequest> List<FileAttachmentInformationResponse> useUploadFiles(int fileUploadAttachmentType, Long targetId, List<E> fileRequestItems) {
        List<DefaultUseUploadFileRequest> items = new ArrayList<>();
        if (fileRequestItems != null) {
            for (E fileRequestItem : fileRequestItems) {
                if (fileRequestItem instanceof DefaultUseUploadFileRequest) {
                    items.add((DefaultUseUploadFileRequest) fileRequestItem);
                } else {
                    DefaultUseUploadFileRequest request = AutoMapUtils.map(fileRequestItem, DefaultUseUploadFileRequest.class);
                    items.add(request);
                }
            }
        }
        return this.useUploadFiles(fileUploadAttachmentType, targetId, new DefaultFileUploadIdentificationInput(items));
    }

    @Override
    public <E extends UseUploadFileRequest> List<FileAttachmentInformationResponse> useUploadFiles(FileUploadAppService service, Long targetId, List<E> fileRequestItems) {
        return this.useUploadFiles(service.getFileUploadAttachmentType(), targetId, fileRequestItems);
    }

    @Override
    public void loadUploadFileOutput(List<FileAttachmentInformationResponse> uploadFiles, Object fileUploadOutput) {
        FileStorageUtils.loadUploadFileOutput(uploadFiles, fileUploadOutput);
    }

    @Override
    public void loadUploadFileOutput(FileUploadAppService service, Long targetId, Object fileUploadOutput) {
        List<FileAttachmentInformationResponse> uploadFiles = this.getFileService()
                .queryByTargetList(service.getFileUploadAttachmentType(), targetId);
        this.loadUploadFileOutput(uploadFiles, fileUploadOutput);
    }

    @Override
    public void deleteUploadFiles(FileUploadAppService service, Long targetId) {
        this.getFileService().deleteByTarget(service.getFileUploadAttachmentType(), targetId);
    }

}
