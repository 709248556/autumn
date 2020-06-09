package com.autumn.file.storage;

import com.autumn.file.storage.clients.fastdfs.FastDFSStorageClient;
import com.autumn.file.storage.clients.fastdfs.FastDFSStorageClientProperties;
import org.csource.fastdfs.ClientGlobal;
import org.junit.Test;

import java.io.FileInputStream;

/**
 * TODO
 *
 * @Description TODO
 * @Author 老码农
 * @Date 2019-09-03 9:59
 */
public class FastDFSStorageClientTest {

    private StorageClient createStorageClient() throws Exception {
        FastDFSStorageClientProperties properties = new FastDFSStorageClientProperties();
        properties.setTrackerServers("47.106.110.85:22122");
        properties.setConnectTimeoutSeconds(10);
        properties.setNetworkTimeoutSeconds(300);
        properties.getHttp().setTrackerHttpPort(8888);
        properties.setEnable(true);
        properties.setEndpoint("http://47.106.110.85:8888");
        properties.setDefaultBucketName("group1");
        ClientGlobal.initByProperties(properties.createFastDFSProperties());
        FastDFSStorageClient client = new FastDFSStorageClient(properties.getEndpoint(), properties.getDefaultBucketName());
        client.setReadBlockSize(properties.getReadBlockSize());
        client.setWriteBlockSize(properties.getWriteBlockSize());
        return client;
    }

    @Test
    public void saveFileTest() throws Exception {
        StorageClient client = createStorageClient();
        FileInputStream inputStream = new FileInputStream("D:\\javaTest\\login-banner.75b1dc77.png");
        FileObject fileObject = client.saveFile(client.getDefaultBucketName(), "test/b.png", inputStream);
        System.out.println(fileObject.getAccessUrl() + " length=" + fileObject.getFileInfo().getLength());
    }

    @Test
    public void getFileTest() throws Exception {
        StorageClient client = createStorageClient();
        FileStorageObject fileObject = client.getFile("group1", "M00/00/00/rBMAE11uDqeAYwoiAAOorcSTuOM971.png");
        if (fileObject != null) {
            System.out.println(fileObject.getAccessUrl() + " length=" + fileObject.getFileInfo().getLength());
        } else {
            System.out.println("文件不存在");
        }
    }

}
