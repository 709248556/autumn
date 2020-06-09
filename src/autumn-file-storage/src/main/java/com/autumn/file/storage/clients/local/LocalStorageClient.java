package com.autumn.file.storage.clients.local;

import com.autumn.file.storage.*;
import com.autumn.exception.ExceptionUtils;
import com.autumn.util.ResourceUtils;
import com.autumn.util.StringUtils;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * 本地存储客户端
 *
 * @author 老码农 2019-03-11 00:31:10
 */
public class LocalStorageClient extends AbstractStorageClient {

    /**
     * 存储代码
     */
    public static final String CHANNEL_ID = "local";

    /**
     * 存储名称
     */
    public static final String CHANNEL_NAME = "本地储存";

    private final String rootFilePath;

    /**
     * 实例化 LocalStorageClient
     *
     * @param endpoint          终节点(url根路径)
     * @param defaultbucketName 默认分区
     * @param rootFilePath      根文件路径
     */
    public LocalStorageClient(String endpoint, String defaultbucketName, String rootFilePath) {
        super(endpoint, defaultbucketName);
        if (StringUtils.isNullOrBlank(rootFilePath)) {
            this.rootFilePath = getDefaultLocalPath();
        } else {
            this.rootFilePath = StringUtils.removeEnd(rootFilePath.replace('\\', URL_SEPARATOR), URL_SEPARATOR);
        }
    }

    /**
     * 获取默认本地文件路径
     *
     * @return
     */
    public static String getDefaultLocalPath() {
        return ResourceUtils.getResourceRootPath() + "/static";
    }

    @Override
    public String getChannelId() {
        return CHANNEL_ID;
    }

    @Override
    public String getChannelName() {
        return CHANNEL_NAME;
    }

    /**
     * 获取根路径
     *
     * @return
     */
    public final String getRootFilePath() {
        return this.rootFilePath;
    }

    @Override
    public boolean existBucket(String bucketName) {
        bucketName = this.checkBucketName(bucketName);
        File dir = new File(this.getPathAddress(this.getRootFilePath(), bucketName));
        return dir.exists() && dir.isDirectory();
    }

    @Override
    public LocalBucket createBucket(String bucketName) {
        bucketName = this.checkBucketName(bucketName);
        File dir = new File(this.getPathAddress(this.getRootFilePath(), bucketName));
        if (dir.exists() && dir.isDirectory()) {
            ExceptionUtils.throwValidationException("分区 " + bucketName + " 已经存在。");
        }
        dir.mkdirs();
        return new LocalBucket(bucketName, dir.getPath());
    }

    @Override
    public LocalBucket getBucket(String bucketName) {
        bucketName = this.checkBucketName(bucketName);
        File dir = new File(this.getPathAddress(this.getRootFilePath(), bucketName));
        if (dir.exists() && dir.isDirectory()) {
            return new LocalBucket(bucketName, dir.getPath());
        }
        return null;
    }

    /**
     * 获取文件路径
     *
     * @param bucketName 分区名称
     * @param fileInfo   文件信息
     * @return
     */
    private String filePath(String bucketName, FileInfo fileInfo) {
        return this.getPathAddress(this.getRootFilePath(), bucketName, fileInfo.getFullPath());
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
    public FileObject saveFile(FileStorageRequest request) throws Exception {
        ExceptionUtils.checkNotNull(request, "request");
        FileOutputStream outputStream = null;
        try {
            String savePath = this.filePath(this.checkBucketName(request.getBucketName()), request.getFileInfo());
            File file = new File(savePath);
            if (file.exists() && file.isFile()) {
                file.delete();
            }
            file = new File(this.getPathAddress(this.getRootFilePath(), request.getBucketName(),
                    request.getFileInfo().getPath()));
            if (!file.exists()) {
                file.mkdirs();
            } else {
                if (!file.isDirectory()) {
                    file.mkdirs();
                }
            }
            file = new File(savePath);
            file.createNewFile();
            outputStream = new FileOutputStream(file);
            long fileSize = this.writeOutputStream(request.getInputStream(), outputStream);
            FileObject fileObject = new FileObject();
            fileObject.setFileInfo(request.getFileInfo());
            fileObject.getFileInfo().setLength(fileSize);
            fileObject.setUrl(request.getFileInfo().getFullPath());
            fileObject.setAccessUrl(this.getAccessUrl(request.getBucketName(), request.getFileInfo()));
            return fileObject;

        } finally {
            IOUtils.closeQuietly(outputStream);
            IOUtils.closeQuietly(request.getInputStream());
        }
    }

    @Override
    public FileStorageObject getFile(String bucketName, String fullPath) {
        FileInfo fileInfo = new FileInfo(fullPath, true);
        String filePath = this.filePath(this.checkBucketName(bucketName), fileInfo);
        File file = new File(filePath);
        if (file.exists() && file.isFile()) {
            FileStorageObject fileStorageObject = new FileStorageObject();
            fileStorageObject.setFileInfo(new FileInfo(fullPath, true));
            fileStorageObject.setAccessUrl(this.getAccessUrl(bucketName, fileInfo));
            fileStorageObject.getFileInfo().setLength(file.length());
            fileStorageObject.setUrl(fileInfo.getFullPath());
            FileInputStream input;
            try {
                input = new FileInputStream(file);
            } catch (FileNotFoundException e) {
                throw ExceptionUtils.throwValidationException("文件不存在。");
            }
            fileStorageObject.setInputStream(input);
            return fileStorageObject;
        }
        return null;
    }

    private File getLocalFile(String bucketName, String fullPath) {
        FileInfo fileInfo = new FileInfo(fullPath, true);
        String filePath = this.filePath(this.checkBucketName(bucketName), fileInfo);
        File file = new File(filePath);
        if (file.exists() && file.isFile()) {
            return file;
        }
        return null;
    }

    @Override
    public boolean existFile(String bucketName, String fullPath) {
        File file = this.getLocalFile(bucketName, fullPath);
        return file != null;
    }

    @Override
    public String getAccessUrl(String bucketName, String fullPath) {
        bucketName = this.checkBucketName(bucketName);
        FileInfo fileInfo = new FileInfo(fullPath, true);
        return this.getAccessUrl(bucketName, fileInfo);
    }

    @Override
    public void deleteFile(String bucketName, String fullPath) {
        File file = this.getLocalFile(bucketName, fullPath);
        if (file != null) {
            file.delete();
        }
    }

    @Override
    public List<FileObject> listFileObjects(String bucketName, String prefix) {
        bucketName = this.checkBucketName(bucketName);
        File dir;
        String url;
        if (StringUtils.isNullOrBlank(prefix)) {
            dir = new File(this.getPathAddress(this.getRootFilePath(), bucketName));
            url = Character.toString(URL_SEPARATOR);
        } else {
            url = StringUtils.removeEnd(StringUtils.removeStart(prefix, URL_SEPARATOR), URL_SEPARATOR);
            dir = new File(this.getPathAddress(this.getRootFilePath(), bucketName, prefix));
        }
        List<FileObject> fileObjects = new ArrayList<>();
        if (dir.isDirectory()) {
            File[] files = dir.listFiles(); // 该文件目录下文件全部放入数组
            for (File file : files) {
                FileObject fileObject = new FileObject();
                FileInfo fileInfo;
                if (file.isDirectory()) {
                    fileInfo = new FileInfo(this.getPathAddress(url, file.getName()), false, 0);
                } else {
                    fileInfo = new FileInfo(this.getPathAddress(url, file.getName()), true, file.length());
                }
                fileObject.setFileInfo(fileInfo);
                fileObject.setUrl(fileInfo.getFullPath());
                fileObject.setAccessUrl(this.getAccessUrl(bucketName, fileInfo));
                fileObjects.add(fileObject);
            }
        }
        return fileObjects;
    }

    @Override
    public String toString() {
        return super.toString() + " 文件保存根路径:" + this.getRootFilePath();
    }
}
