package com.autumn.file.storage;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.List;

import org.junit.Test;

import com.aliyun.oss.model.CannedAccessControlList;
import com.autumn.file.storage.clients.aliyun.AliyunStorageClient;

public class AliyunStorageClientTest {

	private StorageClient storageClient = new AliyunStorageClient("https://oss-cn-shenzhen.aliyuncs.com",
			"autumn-oos-test", "accessKeyId", "accessKeySecret", CannedAccessControlList.PublicRead);

	/**
	 * @throws Exception
	 * 
	 */
	@Test
	public void saveFileTest() throws Exception {
		InputStream is = new ByteArrayInputStream("StorageClient 测试 aaaa".getBytes(Charset.forName("utf-8")));
		storageClient.saveFile(this.storageClient.getDefaultBucketName(), "test/a.txt", is);
	}

	@Test
	public void getFileTest() {
		FileStorageObject fileStorageObject = storageClient.getFile(storageClient.getDefaultBucketName(), "test/a.txt");
		if (fileStorageObject != null) {
			System.out.println("name = " + fileStorageObject.getFileInfo().getName() + " path = "
					+ fileStorageObject.getFileInfo().getFullPath() + " size = "
					+ fileStorageObject.getFileInfo().getLength());
		} else {
			System.out.println("文件不存在");
		}
	}

	@Test
	public void existPartitionTest() {
		System.out.println(storageClient.existBucket(storageClient.getDefaultBucketName()));
	}

	/**
	 * 
	 */
	@Test
	public void getAccessUrlTest() {
		System.out.println(storageClient.getAccessUrl(storageClient.getDefaultBucketName(), "test/a.txt"));
	}

	@Test
	public void deleteFileTest() {
		storageClient.deleteFile(storageClient.getDefaultBucketName(), "test/a.txt");
	}

	@Test
	public void getPartitionTest() {
		AbstractBucket partition = storageClient.getBucket(storageClient.getDefaultBucketName());
		System.out.println(partition.toString());
	}

	@Test
	public void createPartitionTest() {
		if (!storageClient.existBucket("f")) {
			AbstractBucket partition = storageClient.createBucket("f");
			System.out.println(partition.toString());
		}
	}

	@Test
	public void listFileObjectsTest() {
		List<FileObject> objs = storageClient.listFileObjects(storageClient.getDefaultBucketName(), "test");
		for (FileObject obj : objs) {
			System.out.println(obj.toString());
		}
	}
}
