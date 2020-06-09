package com.autumn.zero.file.storage.application.services;

import com.autumn.zero.file.storage.application.dto.FileInput;
import com.autumn.zero.file.storage.application.dto.FileUploadInput;
import com.autumn.zero.file.storage.application.dto.FullFileInput;
import com.autumn.zero.file.storage.application.dto.TemporaryFileInformationDto;
import com.autumn.zero.file.storage.services.FileAttachmentInformationService;
import com.autumn.zero.file.storage.services.vo.FileAttachmentInformationResponse;
import com.autumn.zero.file.storage.services.vo.UseUploadFileRequest;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.function.Consumer;

/**
 * 文件上传管理
 *
 * @author 老码农 2019-03-18 18:20:45
 */
public interface FileUploadManager {

    /**
     * 临时文件路径
     */
    public static final String TEMP_PATH = "temp";

    /**
     * 获取文件服务
     *
     * @return
     */
    FileAttachmentInformationService getFileService();

    /**
     * 保存上传文件
     *
     * @param input
     * @return
     * @throws Exception
     */
    FileAttachmentInformationResponse saveUploadFile(FullFileInput input) throws Exception;

    /**
     * 保存上传文件
     *
     * @param service        服务
     * @param input          输入
     * @param currentUrlPath 当前路径
     * @return
     * @throws Exception
     */
    FileAttachmentInformationResponse saveUploadFile(FileUploadAppService service, FileInput input,
                                                     String currentUrlPath) throws Exception;

    /**
     * 保存临时文件服务
     *
     * @param originalFilename
     * @param inputStream
     * @return
     * @throws Exception
     */
    TemporaryFileInformationDto saveTemporaryFile(String originalFilename, InputStream inputStream) throws Exception;

    /**
     * 保存临时工作薄
     *
     * @param originalFilename 文件名称
     * @param workbook         工作薄
     * @return
     * @throws Exception
     */
    TemporaryFileInformationDto saveTemporaryFileByWorkbook(String originalFilename, Workbook workbook) throws Exception;

    /**
     * 写入临时文件
     *
     * @param originalFilename 文件名称
     * @param streamConsumer   消费流
     * @throws Exception
     * @return
     */
    TemporaryFileInformationDto writeTemporaryFile(String originalFilename, Consumer<OutputStream> streamConsumer) throws Exception;

    /**
     * 使用上传文件
     *
     * @param fileUploadAttachmentType 文件上传附件类型
     * @param targetId                 目标id
     * @param fileUploadInput          上传文件输入
     * @return
     */
    List<FileAttachmentInformationResponse> useUploadFiles(int fileUploadAttachmentType, Long targetId, FileUploadInput fileUploadInput);

    /**
     * 使用上传文件
     *
     * @param service         服务
     * @param targetId        目标id
     * @param fileUploadInput 上传文件输入
     * @return
     */
    List<FileAttachmentInformationResponse> useUploadFiles(FileUploadAppService service, Long targetId, FileUploadInput fileUploadInput);

    /**
     * 使用上传文件
     *
     * @param fileUploadAttachmentType 文件上传附件类型
     * @param targetId                 目标id
     * @param fileRequestItems         文件请求集合
     * @return
     */
    <E extends UseUploadFileRequest> List<FileAttachmentInformationResponse> useUploadFiles(int fileUploadAttachmentType, Long targetId, List<E> fileRequestItems);

    /**
     * 使用上传文件
     *
     * @param service          服务
     * @param targetId         目标id
     * @param fileRequestItems 文件请求集合
     * @return
     */
    <E extends UseUploadFileRequest> List<FileAttachmentInformationResponse> useUploadFiles(FileUploadAppService service, Long targetId, List<E> fileRequestItems);

    /**
     * 加载上传文件输出
     *
     * @param uploadFiles      上传文件集合
     * @param fileUploadOutput 文件上传输出
     */
    void loadUploadFileOutput(List<FileAttachmentInformationResponse> uploadFiles, Object fileUploadOutput);

    /**
     * 加载上传文件输出
     *
     * @param service          服务
     * @param targetId         目标id
     * @param fileUploadOutput 文件上传输出
     */
    void loadUploadFileOutput(FileUploadAppService service, Long targetId, Object fileUploadOutput);

    /**
     * 删除上传文件
     *
     * @param service  服务
     * @param targetId 目标id
     */
    void deleteUploadFiles(FileUploadAppService service, Long targetId);

}
