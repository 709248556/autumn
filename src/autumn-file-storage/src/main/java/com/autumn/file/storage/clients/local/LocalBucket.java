package com.autumn.file.storage.clients.local;

import com.autumn.file.storage.AbstractBucket;

/**
 * 本地分区
 * 
 * @author 老码农 2019-03-13 11:43:32
 */
public class LocalBucket extends AbstractBucket {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4634807388895689305L;

	private final String path;

	/**
	 * 
	 * @param name
	 * @param path
	 */
	public LocalBucket(String name, String path) {
		super(name);
		this.path = path;
	}

	/**
	 * 获取路径
	 * 
	 * @return
	 */
	public String getPath() {
		return path;
	}

	@Override
	public String toString() {
		return "name = " + this.getName() + " path = " + this.getPath();
	}

}
