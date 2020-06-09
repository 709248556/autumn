package com.autumn.file.storage.clients.aliyun;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSBuilder;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.common.auth.DefaultCredentialProvider;
import com.aliyun.oss.model.Bucket;
import com.aliyun.oss.model.*;
import com.autumn.file.storage.*;
import com.autumn.exception.ExceptionUtils;
import com.autumn.util.StringUtils;
import org.apache.commons.io.IOUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 阿里云oos
 * 
 * @author 老码农 2019-03-11 00:47:17
 */
public class AliyunStorageClient extends AbstractStorageClient {

	/**
	 * 
	 * 存储代码
	 */
	public static final String CHANNEL_ID = "aliyunOOS";

	/**
	 * 
	 * 存储名称
	 */
	public static final String CHANNEL_NAME = "阿里云对象存储";

	private final OSS oss;
	private final String httpProtocol;
	private final String httpDomainName;
	private CannedAccessControlList controlList = CannedAccessControlList.PublicRead;

	/**
	 * 实例化 AliyunStorageClient
	 * 
	 * @param endpoint
	 * @param defaultBucketName
	 * @param accessKeyId
	 * @param accessKeySecret
	 */
	public AliyunStorageClient(String endpoint, String defaultBucketName, String accessKeyId, String accessKeySecret) {
		this(endpoint, defaultBucketName, accessKeyId, accessKeySecret, CannedAccessControlList.PublicRead);
	}

	/**
	 * 实例化 AliyunStorageClient
	 * 
	 * @param endpoint
	 * @param defaultBucketName
	 * @param accessKeyId
	 * @param accessKeySecret
	 * @param controlList
	 */
	public AliyunStorageClient(String endpoint, String defaultBucketName, String accessKeyId, String accessKeySecret,
			CannedAccessControlList controlList) {
		super(StringUtils.removeALLWhitespace(endpoint), defaultBucketName);
		DefaultCredentialProvider credsProvider = new DefaultCredentialProvider(
				ExceptionUtils.checkNotNullOrBlank(accessKeyId, "accessKeyId"),
				ExceptionUtils.checkNotNullOrBlank(accessKeySecret, "accessKeySecret"));
		OSSBuilder ossBuilder = new OSSClientBuilder();
		this.oss = ossBuilder.build(endpoint, credsProvider);
		int index = this.getEndpoint().indexOf("//");
		if (index > 2) {
			this.httpProtocol = this.getEndpoint().substring(0, index - 1);
			this.httpDomainName = this.getEndpoint().substring(index + 2);
		} else {
			this.httpProtocol = "http";
			this.httpDomainName = this.getEndpoint();
		}
		this.setControlList(controlList);
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
	 * 
	 * @return
	 */
	public CannedAccessControlList getControlList() {
		return controlList;
	}

	/**
	 * 
	 * @param controlList
	 */
	public void setControlList(CannedAccessControlList controlList) {
		if (controlList == null) {
			controlList = CannedAccessControlList.Default;
		}
		this.controlList = controlList;
	}

	/**
	 * 获取访问Url
	 * 
	 * @param bucketName
	 *            分区名称
	 * @param fileInfo
	 * @return
	 */
	private String getAccessUrl(String bucketName, FileInfo fileInfo) {
		String domainName = this.httpProtocol + "://" + bucketName + "." + this.httpDomainName;
		return this.getPathAddress(domainName, fileInfo.getFullPath());
	}

	@Override
	public boolean existBucket(String bucketName) {
		return this.oss.doesBucketExist(this.checkBucketName(bucketName));
	}

	private AliyunBucket createBucket(Bucket bucket) {
		AliyunBucket partition = new AliyunBucket(bucket.getName());
		partition.setCreationDate(bucket.getCreationDate());
		partition.setExtranetEndpoint(bucket.getExtranetEndpoint());
		partition.setIntranetEndpoint(bucket.getIntranetEndpoint());
		partition.setLocation(bucket.getLocation());
		return partition;
	}

	@Override
	public AliyunBucket createBucket(String bucketName) {
		bucketName = this.checkBucketName(bucketName);
		Bucket bucket = this.oss.createBucket(bucketName);
		this.oss.setBucketAcl(bucketName, this.getControlList());
		if (bucket != null) {
			return this.createBucket(bucket);
		}
		return null;
	}

	@Override
	public AliyunBucket getBucket(String bucketName) {
		BucketInfo bucketInfo = this.oss.getBucketInfo(this.checkBucketName(bucketName));
		if (bucketInfo != null) {
			return this.createBucket(bucketInfo.getBucket());
		}
		return null;
	}

	@Override
	public FileObject saveFile(FileStorageRequest request) {
		ExceptionUtils.checkNotNull(request, "request");
		try {
			String bucketName = this.checkBucketName(request.getBucketName());
			if (!this.oss.doesBucketExist(bucketName)) {
				this.oss.createBucket(bucketName);
				this.oss.setBucketAcl(bucketName, this.getControlList());
				this.getLogger().info("创建阿里云 bucket：" + bucketName + " 权限：" + this.getControlList());
			}
			this.oss.putObject(bucketName, request.getFileInfo().getFullPath(), request.getInputStream());
			FileObject fileObject = new FileObject();
			fileObject.setFileInfo(request.getFileInfo());
			OSSObject ossObject = this.oss.getObject(bucketName, request.getFileInfo().getFullPath());
			IOUtils.closeQuietly(ossObject.getObjectContent());
			fileObject.getFileInfo().setLength(ossObject.getObjectMetadata().getContentLength());
			fileObject.setUrl(request.getFileInfo().getFullPath());
			fileObject.setAccessUrl(this.getAccessUrl(request.getBucketName(), request.getFileInfo()));
			return fileObject;
		} finally {
			IOUtils.closeQuietly(request.getInputStream());
		}
	}

	@Override
	public FileStorageObject getFile(String bucketName, String fullPath) {
		FileInfo fileInfo = new FileInfo(fullPath, true);
		OSSObject ossObject = this.oss.getObject(this.checkBucketName(bucketName), fileInfo.getFullPath());
		if (ossObject == null) {
			return null;
		}
		FileStorageObject fileStorageObject = new FileStorageObject();
		fileStorageObject.setFileInfo(new FileInfo(fullPath, true));
		fileStorageObject.setAccessUrl(this.getAccessUrl(bucketName, fileInfo));
		fileStorageObject.getFileInfo().setLength(ossObject.getObjectMetadata().getContentLength());
		fileStorageObject.setUrl(fileInfo.getFullPath());
		fileStorageObject.setInputStream(ossObject.getObjectContent());
		return fileStorageObject;
	}

	@Override
	public boolean existFile(String bucketName, String fullPath) {
		FileInfo fileInfo = new FileInfo(fullPath, true);
		return this.oss.doesObjectExist(this.checkBucketName(bucketName), fileInfo.getFullPath());
	}

	@Override
	public String getAccessUrl(String bucketName, String fullPath) {
		FileInfo fileInfo = new FileInfo(fullPath, true);
		return this.getAccessUrl(this.checkBucketName(bucketName), fileInfo);
	}

	@Override
	public void deleteFile(String bucketName, String fullPath) {
		FileInfo fileInfo = new FileInfo(fullPath, true);
		this.oss.deleteObject(this.checkBucketName(bucketName), fileInfo.getFullPath());
	}

	@Override
	public List<FileObject> listFileObjects(String bucketName, String prefix) {
		if (prefix != null && "".equals(prefix.trim())) {
			prefix = null;
		}
		ObjectListing objectListing = this.oss.listObjects(this.checkBucketName(bucketName), prefix);
		List<FileObject> fileObjects = new ArrayList<>();
		if (objectListing.getObjectSummaries() != null) {
			for (OSSObjectSummary oosSummary : objectListing.getObjectSummaries()) {
				FileObject fileObject = new FileObject();
				FileInfo fileInfo = new FileInfo(oosSummary.getKey(), true, oosSummary.getSize());
				fileObject.setFileInfo(fileInfo);
				fileObject.setUrl(fileInfo.getFullPath());
				fileObject.setAccessUrl(this.getAccessUrl(bucketName, fileInfo));
				fileObjects.add(fileObject);
			}
		}
		return fileObjects;
	}

}
