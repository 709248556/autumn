package com.autumn.file.storage.clients.aliyun;

import com.autumn.file.storage.AbstractBucket;

import java.util.Date;

/**
 * 阿里去分区信息
 * 
 * @author 老码农 2019-03-13 17:01:34
 */
public class AliyunBucket extends AbstractBucket {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6403443988253891221L;

	/**
	 * 
	 * @param name
	 */
	public AliyunBucket(String name) {
		super(name);
	}

	private String location;

	// Created date.
	private Date creationDate;

	private String extranetEndpoint;

	// Internal endpoint. It could be accessed within AliCloud under the same
	// location.
	private String intranetEndpoint;

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public String getExtranetEndpoint() {
		return extranetEndpoint;
	}

	public void setExtranetEndpoint(String extranetEndpoint) {
		this.extranetEndpoint = extranetEndpoint;
	}

	public String getIntranetEndpoint() {
		return intranetEndpoint;
	}

	public void setIntranetEndpoint(String intranetEndpoint) {
		this.intranetEndpoint = intranetEndpoint;
	}

}
