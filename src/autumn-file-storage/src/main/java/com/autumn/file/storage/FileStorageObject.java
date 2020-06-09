package com.autumn.file.storage;

import java.io.InputStream;

/**
 * 文件存储对象
 * 
 * @author 老码农 2019-03-10 23:29:22
 */
public class FileStorageObject extends FileObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2107478581741859300L;

	private InputStream inputStream;

	/**
	 * 获取流
	 * 
	 * @return
	 */
	public InputStream getInputStream() {
		return inputStream;
	}

	/**
	 * 设置流
	 * 
	 * @param inputStream
	 */
	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

}
