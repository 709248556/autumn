package com.autumn.file.storage;

import java.io.InputStream;

import com.autumn.exception.ExceptionUtils;
import com.autumn.util.StringUtils;

/**
 * 文件存储请求
 * 
 * @author 老码农 2019-03-10 23:38:21
 */
public class FileStorageRequest {
	private final String bucketName;
	private final InputStream inputStream;
	private final FileInfo fileInfo;

	/**
	 * 
	 * @param bucketName
	 *            分区名称
	 * @param fullPath
	 *            完整路径，包括文件名称
	 * @param inputStream
	 *            输入流
	 */
	public FileStorageRequest(String bucketName, String fullPath, InputStream inputStream) {
		this(bucketName, new FileInfo(fullPath, true, 0L), inputStream);
	}

	/**
	 * 
	 * @param bucketName
	 *            分区名称
	 * @param fileInfo
	 *            文件信息
	 * @param inputStream
	 *            输入流
	 */
	public FileStorageRequest(String bucketName, FileInfo fileInfo, InputStream inputStream) {
		if (StringUtils.isNullOrBlank(bucketName)) {
			ExceptionUtils.throwValidationException("分区名称不能为空。");
		}
		if (fileInfo == null) {
			ExceptionUtils.throwValidationException("文件信息fileInfo不能为null。");
		}
		this.fileInfo = fileInfo;
		if (inputStream == null) {
			ExceptionUtils.throwValidationException("文件流不能为空。");
		}
		this.bucketName = bucketName;
		this.inputStream = inputStream;
	}

	/**
	 * 获取分区名称
	 * 
	 * @return
	 */
	public String getBucketName() {
		return bucketName;
	}

	/**
	 * 获取输入流
	 * 
	 * @return
	 */
	public InputStream getInputStream() {
		return inputStream;
	}

	/**
	 * 获取文件信息
	 * 
	 * @return
	 */
	public FileInfo getFileInfo() {
		return fileInfo;
	}
}
