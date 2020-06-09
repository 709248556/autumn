package com.autumn.file.storage;

import com.autumn.exception.ExceptionUtils;
import com.autumn.util.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

/**
 * 存储客户端抽象
 *
 * @author 老码农 2019-03-11 00:32:30
 */
public abstract class AbstractStorageClient implements StorageClient {

    /**
     * 日志
     */
    private final Log logger = LogFactory.getLog(this.getClass());

    /**
     * Url 分隔符
     */
    public static final char URL_SEPARATOR = '/';

    private final String endpoint;
    private final String defaultBucketName;
    private int writeBlockSize = 2048;
    private int readBlockSize = 4096;

    /**
     * AbstractStorageClient
     *
     * @param endpoint
     * @param defaultBucketName
     */
    public AbstractStorageClient(String endpoint, String defaultBucketName) {
        if (StringUtils.isNullOrBlank(endpoint)) {
            ExceptionUtils.throwValidationException("终节点(url根路径)不能为空。");
        }
        this.endpoint = StringUtils.removeStart(StringUtils.removeEnd(endpoint, URL_SEPARATOR), URL_SEPARATOR)
                .toLowerCase();
        if (defaultBucketName == null) {
            defaultBucketName = "";
        } else {
            defaultBucketName = this.checkBucketName(defaultBucketName);
        }
        this.defaultBucketName = defaultBucketName.trim().toLowerCase();
    }

    @Override
    public final String getEndpoint() {
        return this.endpoint;
    }

    @Override
    public final String getDefaultBucketName() {
        return this.defaultBucketName;
    }

    /**
     * 获取日志
     *
     * @return
     */
    public final Log getLogger() {
        return this.logger;
    }

    @Override
    public final FileObject saveFile(String bucketName, String fullPath, InputStream input) throws Exception {
        FileStorageRequest request = new FileStorageRequest(bucketName, fullPath, input);
        return this.saveFile(request);
    }

    @Override
    public final List<FileObject> listFileObjects(String bucketName) {
        return this.listFileObjects(bucketName, null);
    }

    /**
     * 检查分区名称
     *
     * @param bucketName 分区名称
     * @return
     */
    protected String checkBucketName(String bucketName) {
        return ExceptionUtils.checkNotNullOrBlank(bucketName, "bucketName");
    }

    /**
     * 获取路径地址
     *
     * @param args 参数集合
     * @return
     */
    protected final String getPathAddress(String... args) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < args.length; i++) {
            String arg = args[i];
            if (i > 0) {
                builder.append(URL_SEPARATOR);
            }
            builder.append(arg);
        }
        return builder.toString();
    }

    /**
     * 写入到输出流
     *
     * @param inputStream  输入流
     * @param outputStream 输出流
     * @return
     * @throws IOException
     */
    protected final long writeOutputStream(InputStream inputStream, OutputStream outputStream) throws IOException {
        long size = 0;
        int byteCount;
        byte[] bytes = new byte[this.getWriteBlockSize()];
        while ((byteCount = inputStream.read(bytes)) != -1) {
            outputStream.write(bytes, 0, byteCount);
            size += byteCount;
        }
        return size;
    }

    /**
     * 获取读取块大小
     *
     * @return
     */
    @Override
    public int getReadBlockSize() {
        return this.readBlockSize;
    }

    /**
     * 设置读块大小
     *
     * @param readBlockSize 读块大小
     */
    @Override
    public void setReadBlockSize(int readBlockSize) {
        this.readBlockSize = readBlockSize;
    }

    /**
     * 获取写块大小
     *
     * @return
     */
    @Override
    public int getWriteBlockSize() {
        return this.writeBlockSize;
    }

    /**
     * 设置写块大小
     *
     * @param writeBlockSize 写块大小
     */
    @Override
    public void setWriteBlockSize(int writeBlockSize) {
        this.writeBlockSize = writeBlockSize;
    }

    @Override
    public String toString() {
        return "启用 FileStorage " + this.getChannelName() + " 默认分区:" + this.getDefaultBucketName() + " 访问根路径 :"
                + this.getEndpoint();
    }
}
