package com.autumn.file.storage;

import java.io.Serializable;

/**
 * 分区信息抽象
 * 
 * @author 老码农 2019-03-10 23:22:49
 */
public abstract class AbstractBucket implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2761765610399715980L;
	private final String name;

	/**
	 * 
	 * @param name
	 */
	public AbstractBucket(String name) {
		this.name = name;
	}

	/**
	 * 获取分区名称
	 * 
	 * @return
	 */
	public final String getName() {
		return this.name;
	}

	@Override
	public String toString() {
		return "name = " + this.getName();
	}

}
