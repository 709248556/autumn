package com.autumn.word.pdf;

import java.util.HashMap;

/**
 * Pdf属性
 */
public final class PdfProperties {
	/**
	 * 默认
	 */
	public static PdfProperties DEFAULT = new PdfProperties("autumn");

	/**
	 * 实例化 PdfProperties 类新实例
	 */
	public PdfProperties() {
		this.setCustomProperties(new HashMap<>());
	}

	public PdfProperties(String author) {
		this.author = author;
		this.setCustomProperties(new HashMap<>());
	}

	/**
	 * 获取或设置标题
	 */
	private String title;

	public String getTitle() {
		return title;
	}

	public void setTitle(String value) {
		title = value;
	}

	/**
	 * 获取或设置作者
	 */
	private String author;

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String value) {
		author = value;
	}

	/**
	 * 获取或设置主题
	 */
	private String subject;

	public String getSubject() {
		return subject;
	}

	public void setSubject(String value) {
		subject = value;
	}

	/**
	 * 获取或设置关键字
	 */
	private String keywords;

	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String value) {
		keywords = value;
	}

	/**
	 * 自定义属性集合
	 */
	private HashMap<String, String> customProperties;

	public HashMap<String, String> getCustomProperties() {
		return customProperties;
	}

	private void setCustomProperties(HashMap<String, String> value) {
		customProperties = value;
	}
}