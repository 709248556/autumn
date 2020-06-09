package com.autumn.word;

/**
 * 解析选项
 */
public class EvaluateOptions {
	/**
	 * 默认
	 */
	public static EvaluateOptions DEFAULT = new EvaluateOptions(-1);

	/**
	 * 实例化 EvaluateOptions 类新实例
	 *
	 * @param protectionType
	 *            保护类型
	 * @param protectionPassword
	 *            密码
	 */
	public EvaluateOptions(int protectionType, String protectionPassword) {
		this.setProtectionType(protectionType);
		this.setProtectionPassword(protectionPassword);
	}

	/**
	 * 实例化 EvaluateOptions 类新实例
	 *
	 * @param editLock
	 *            锁
	 */
	public EvaluateOptions(int editLock) {
		this(editLock, null);
	}

	/**
	 * 保护类型
	 */
	private int protectionType = -1;

	/**
	 * 保护密码
	 */
	private String protectionPassword;

	/**
	 * 文档密码
	 */
	private String documentPassword;

	private String category;

	private String title = "autumn 生成Word文档";

	private String contentType = "";

	private String subject;

	private String description = "autumn evaluator 动态表达式解析生成 Word 文档。";

	public final int getProtectionType() {
		return protectionType;
	}

	private void setProtectionType(int value) {
		protectionType = value;
	}

	public final String getProtectionPassword() {
		return protectionPassword;
	}

	private void setProtectionPassword(String value) {
		protectionPassword = value;
	}

	public final String getDocumentPassword() {
		return documentPassword;
	}

	public final void setDocumentPassword(String value) {
		documentPassword = value;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}