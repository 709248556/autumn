package com.autumn.file.storage.clients.fastdfs;

import com.autumn.exception.ExceptionUtils;
import com.autumn.file.storage.*;
import org.apache.commons.io.IOUtils;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * FastDFS 存储客户端
 *
 * @Description TODO
 * @Author 老码农
 * @Date 2019-09-03 4:33
 */
public class FastDFSStorageClient extends AbstractStorageClient {

    /**
     * 存储代码
     */
    public static final String CHANNEL_ID = "fastDFS";

    /**
     * 存储名称
     */
    public static final String CHANNEL_NAME = "FastDFS对象存储";

    /**
     * 实例化
     *
     * @param endpoint
     * @param defaultBucketName
     */
    public FastDFSStorageClient(String endpoint, String defaultBucketName) {
        super(endpoint, defaultBucketName);
    }

    @Override
    public String getChannelId() {
        return CHANNEL_ID;
    }

    @Override
    public String getChannelName() {
        return CHANNEL_NAME;
    }

    @Override
    public boolean existBucket(String bucketName) {
        throw ExceptionUtils.throwNotSupportException("不支持的操作");
    }

    @Override
    public FastDFSBucket createBucket(String bucketName) {
        throw ExceptionUtils.throwNotSupportException("不支持的操作");
    }

    @Override
    public FastDFSBucket getBucket(String bucketName) {
        return new FastDFSBucket(bucketName);
    }

    private StorageClient getStorageClient() {
        TrackerClient trackerClient = new TrackerClient();
        TrackerServer trackerServer;
        try {
            trackerServer = trackerClient.getConnection();
        } catch (IOException e) {
            throw ExceptionUtils.throwSystemException(e.getMessage(), e);
        }
        return new StorageClient(trackerServer, null);
    }

    @Override
    public FileObject saveFile(FileStorageRequest request) throws Exception {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            StorageClient storageClient = this.getStorageClient();
            this.writeOutputStream(request.getInputStream(), outputStream);
            byte[] bytes = outputStream.toByteArray();
            String[] files = storageClient.upload_file(request.getBucketName(), bytes,
                    request.getFileInfo().getExtensionName(), null);
            if (files == null || files.length < 2) {
                throw ExceptionUtils.throwSystemException("上传失败，获取上传文件信息不正确。");
            }
            FileObject fileObject = new FileObject();
            FileInfo fileInfo = new FileInfo(files[1], true, bytes.length);
            fileObject.setAccessUrl(this.getAccessUrl(files[0], fileInfo.getFullPath()));
            fileObject.setUrl(fileInfo.getFullPath());
            fileObject.setFileInfo(fileInfo);
            return fileObject;
        } finally {
            IOUtils.closeQuietly(request.getInputStream());
            IOUtils.closeQuietly(outputStream);
        }
    }

    @Override
    public FileStorageObject getFile(String bucketName, String fullPath) {
        StorageClient storageClient = this.getStorageClient();
        try {
            byte[] bytes = storageClient.download_file(bucketName, fullPath);
            FileInfo fileInfo = new FileInfo(fullPath, true, bytes.length);
            FileStorageObject storageObject = new FileStorageObject();
            storageObject.setInputStream(new ByteArrayInputStream(bytes));
            storageObject.setAccessUrl(this.getAccessUrl(bucketName, fullPath));
            storageObject.setUrl(fullPath);
            storageObject.setFileInfo(fileInfo);
            return storageObject;
        } catch (Exception e) {
            throw ExceptionUtils.throwSystemException(e.getMessage(), e);
        }
    }

    @Override
    public boolean existFile(String bucketName, String fullPath) {
        StorageClient storageClient = this.getStorageClient();
        try {
            return storageClient.query_file_info(bucketName, fullPath) != null;
        } catch (Exception e) {
            throw ExceptionUtils.throwSystemException(e.getMessage(), e);
        }
    }

    /**
     * 获取访问Url
     *
     * @param bucketName 分区名称
     * @param fileInfo
     * @return
     */
    private String getAccessUrl(String bucketName, FileInfo fileInfo) {
        return this.getPathAddress(this.getEndpoint(), bucketName, fileInfo.getFullPath());
    }

    @Override
    public String getAccessUrl(String bucketName, String fullPath) {
        FileInfo fileInfo = new FileInfo(fullPath, true);
        return this.getAccessUrl(bucketName, fileInfo);
    }

    @Override
    public void deleteFile(String bucketName, String fullPath) {
        StorageClient storageClient = this.getStorageClient();
        try {
            storageClient.delete_file(bucketName, fullPath);
        } catch (Exception e) {
            throw ExceptionUtils.throwSystemException(e.getMessage(), e);
        }
    }

    @Override
    public List<FileObject> listFileObjects(String bucketName, String prefix) {
        throw ExceptionUtils.throwNotSupportException("不支持的操作");
    }
}
