package com.autumn.file.storage;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;

import org.junit.Test;

import com.autumn.file.storage.clients.local.LocalStorageClient;

/**
 * 本地客户端测式
 * 
 * @author 老码农 2019-03-13 11:14:30
 */
public class LocalStorageClientTest {

	private StorageClient storageClient = new LocalStorageClient("/", "localstorage", "D:\\LocalStorageTest");

	/**
	 * @throws Exception
	 * 
	 */
	@Test
	public void saveFileTest() throws Exception {
		InputStream is = new ByteArrayInputStream("StorageClient 测试 ".getBytes());
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
