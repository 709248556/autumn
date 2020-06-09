package com.autumn.zero.common.library.application.services.sys.impl;

import com.autumn.exception.ExceptionUtils;
import com.autumn.file.storage.FileObject;
import com.autumn.file.storage.StorageClient;
import com.autumn.mybatis.wrapper.EntityQueryWrapper;
import com.autumn.mybatis.wrapper.LockModeEnum;
import com.autumn.util.AutoMapUtils;
import com.autumn.util.EnvironmentConstants;
import com.autumn.util.StringUtils;
import com.autumn.zero.common.library.application.dto.sys.help.*;
import com.autumn.zero.common.library.application.services.sys.SystemHelpDocumentAppService;
import com.autumn.zero.common.library.application.services.common.AbstractTreeAppService;
import com.autumn.zero.common.library.constants.CommonStatusConstant;
import com.autumn.zero.common.library.entities.sys.SystemHelpDocument;
import com.autumn.zero.common.library.repositories.sys.SystemHelpDocumentRepository;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * 帮助文档服务实现
 * <p>
 * </p>
 *
 * @description: TODO
 * @author: 老码农
 * @create: 2019-11-29 11:03
 **/
public class SystemHelpDocumentAppServiceImpl extends AbstractTreeAppService<SystemHelpDocument,
        SystemHelpDocumentRepository,
        SystemHelpDocument,
        SystemHelpDocumentRepository,
        AbstractSystemHelpDocumentInput,
        SystemHelpDocumentListOutput,
        SystemHelpDocumentOutput>
        implements SystemHelpDocumentAppService {

    private static final String HELP_PATH = "help/doc/";

    @Autowired
    private StorageClient storageClient;

    public SystemHelpDocumentAppServiceImpl() {
        this.getSearchMembers().add(SystemHelpDocument.FIELD_FRIENDLY_NAME);
        this.getSearchMembers().add(SystemHelpDocument.FIELD_FULL_NAME);
    }

    @Override
    public String getModuleName() {
        return "系统帮助文档";
    }

    @Override
    protected String getPathSeparate() {
        return "/";
    }

    @Override
    protected void editCheck(AbstractSystemHelpDocumentInput input, SystemHelpDocument entity, SystemHelpDocument prarentEntity, boolean isAddNew) {
        super.editCheck(input, entity, prarentEntity, isAddNew);
        if (prarentEntity != null && !prarentEntity.isDirectory()) {
            ExceptionUtils.throwValidationException("文件下不能添加子级。");
        }
        if (input instanceof SystemHelpDocumentDirectoryInput) {
            entity.setDirectory(true);
            entity.setHtmlContent("");
            entity.setUrlFullPath(HELP_PATH + entity.getFullName());
            entity.setAccessPath(entity.getUrlFullPath());
        } else {
            entity.setDirectory(false);
            if (StringUtils.isNullOrBlank(entity.getHtmlContent())) {
                ExceptionUtils.throwValidationException("文件的Html内容不能为空。");
            }
            if (isAddNew) {
                entity.setUrlFullPath("");
                entity.setAccessPath("");
            }
        }
        if (isAddNew) {
            entity.setGenerate(false);
            entity.setGenerateTime(null);
            entity.setFileFriendlyLength("");
            entity.setFileLength(null);
        }
    }

    @Override
    protected void deleteAfter(SystemHelpDocument entity, boolean isSoftDelete) {
        super.deleteAfter(entity, isSoftDelete);
        if (!entity.isDirectory()) {
            if (StringUtils.isNotNullOrBlank(entity.getUrlFullPath())) {
                this.storageClient.deleteFile(this.storageClient.getDefaultBucketName(), entity.getUrlFullPath());
            }
        }
    }

    @Override
    protected SystemHelpDocumentOutput toOutputByEntity(SystemHelpDocument entity) {
        if (entity == null) {
            return null;
        }
        if (entity.isDirectory()) {
            return AutoMapUtils.map(entity, SystemHelpDocumentOutput.class);
        }
        return AutoMapUtils.map(entity, SystemHelpDocumentFileOutput.class);
    }

    @Override
    protected SystemHelpDocumentOutput toOutputByQuery(SystemHelpDocument entity) {
        return this.toOutputByEntity(entity);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void generateHtml(SystemHelpDocumentGenerateInput input) {
        ExceptionUtils.checkNotNull(input, "input");
        input.valid();
        SystemHelpDocument document = this.getEntityById(input.getId(), LockModeEnum.UPDATE);
        if (document == null) {
            ExceptionUtils.throwValidationException("无法生成不存在的目录或文件");
        }
        if (document.getStatus().equals(CommonStatusConstant.STATUS_ENABLE)) {
            generateHtml(document, input.isResetGenerate());
        } else {
            ExceptionUtils.throwValidationException("停用的目录或文件无法生成。");
        }
        this.clearCache();
    }

    private String createCacheVisitById(Long id) {
        return "query_cache_visit_by_" + id;
    }

    private void clearCacheVisitById(Long id) {
        if (this.isCache()) {
            this.clearCacheKey(this.getCacheName(), this.createCacheVisitById(id));
        }
    }

    @Override
    protected void clearCacheById(Long id) {
        super.clearCacheById(id);
        this.clearCacheVisitById(id);
    }

    @Override
    public SystemHelpDocumentVisitOutput queryVisit(Long id) {
        ExceptionUtils.checkNotNull(id, "id");
        return this.getOrAddCache(this.createCacheVisitById(id), () -> {
            SystemHelpDocumentOutput document = this.queryById(id);
            if (document == null || document.isDirectory() || StringUtils.isNullOrBlank(document.getAccessPath())) {
                return null;
            }
            return AutoMapUtils.map(document, SystemHelpDocumentVisitOutput.class);
        });
    }

    private void generateHtml(SystemHelpDocument document, boolean isResetGenerate) {
        if (!document.isDirectory()) {
            if (isResetGenerate || StringUtils.isNullOrBlank(document.getAccessPath())) {

                StringBuilder builder = new StringBuilder(document.getHtmlContent().length() + 1000);
                builder.append("<!DOCTYPE html>").append(EnvironmentConstants.LINE_SEPARATOR);
                builder.append("<html lang=\"cn\">").append(EnvironmentConstants.LINE_SEPARATOR);
                builder.append("<head>").append(EnvironmentConstants.LINE_SEPARATOR);
                builder.append("<meta charset=\"utf-8\">").append(EnvironmentConstants.LINE_SEPARATOR);
                builder.append("<title>" + document.getFriendlyName() + "</title>").append(EnvironmentConstants.LINE_SEPARATOR);
                builder.append("</head>").append(EnvironmentConstants.LINE_SEPARATOR);
                builder.append("<body>").append(EnvironmentConstants.LINE_SEPARATOR);
                builder.append("<div>").append(EnvironmentConstants.LINE_SEPARATOR);
                builder.append(document.getHtmlContent());
                builder.append("</div>").append(EnvironmentConstants.LINE_SEPARATOR);
                builder.append("</body>").append(EnvironmentConstants.LINE_SEPARATOR);
                builder.append("</html>");

                InputStream inputStream = new ByteArrayInputStream(builder.toString().getBytes(StandardCharsets.UTF_8));

                String fullPath = HELP_PATH + document.getFullName() + ".html";
                try {
                    FileObject fileObject = storageClient.saveFile(storageClient.getDefaultBucketName(), fullPath, inputStream);
                    document.setUrlFullPath(fileObject.getUrl());
                    document.setAccessPath(fileObject.getAccessUrl());
                    document.setFileLength(fileObject.getFileInfo().getLength());
                    document.setFileFriendlyLength(fileObject.getFileInfo().getLengthString());

                } catch (Exception e) {
                    throw ExceptionUtils.throwValidationException("生成出错：" + e.getMessage());
                } finally {
                    IOUtils.closeQuietly(inputStream);
                }
                document.setGenerate(false);
                document.setGenerateTime(null);
                this.getRepository().update(document);
            }
        }
        if (document.getChildrenCount() > 0) {
            EntityQueryWrapper<SystemHelpDocument> wrapper = new EntityQueryWrapper<>(SystemHelpDocument.class);
            wrapper.where().eq(SystemHelpDocument.FIELD_PARENT_ID, document.getId())
                    .eq(SystemHelpDocument.FIELD_STATUS, CommonStatusConstant.STATUS_ENABLE)
                    .of().orderBy(SystemHelpDocument.FIELD_SORT_ID).orderBy(SystemHelpDocument.FIELD_ID);
            List<SystemHelpDocument> documents = this.getRepository().selectForList(wrapper);
            for (SystemHelpDocument systemHelpDocument : documents) {
                this.generateHtml(systemHelpDocument, isResetGenerate);
            }
        }
    }


}
