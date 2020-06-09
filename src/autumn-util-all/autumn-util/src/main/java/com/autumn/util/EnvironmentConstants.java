package com.autumn.util;

/**
 * 环境常量
 * 
 * @author 老码农
 *         <p>
 *         Description
 *         </p>
 * @date 2018-01-20 12:30:54
 */
public class EnvironmentConstants {
	
	/**
	 * 换行符
	 */
	public final static String PROPERTY_KEY_LINE_SEPARATOR = "line.separator";

	/**
	 * 路径符号
	 */
	public final static String PROPERTY_KEY_PATH_SEPARATOR = "path.separator";

	/**
	 * 文件符号
	 */
	public final static String PROPERTY_KEY_FILE_SEPARATOR = "file.separator";

	/**
	 * Java 版本
	 */
	public final static String PROPERTY_KEY_JAVA_VERSION = "java.version";

	/**
	 * Java 提供者
	 */
	public final static String PROPERTY_KEY_JAVA_VENDOR = "java.vendor";

	/**
	 * Java 提供者 Url
	 */
	public final static String PROPERTY_KEY_JAVA_VENDOR_URL = "java.vendor.url";

	/**
	 * Java 主页
	 */
	public final static String PROPERTY_KEY_JAVA_HOME = "java.home";

	/**
	 * Java 类版本
	 */
	public final static String PROPERTY_KEY_JAVA_CLASS_VERSION = "java.class.version";

	/**
	 * Java 类路径
	 */
	public final static String PROPERTY_KEY_JAVA_CLASS_PATH = "java.class.path";

	/**
	 * Java 操作系统名称
	 */
	public final static String PROPERTY_KEY_OS_NAME = "os.name";

	/**
	 * Java 操作系统结构
	 */
	public final static String PROPERTY_KEY_OS_ARCH = "os.arch";

	/**
	 * Java 操作系统版本
	 */
	public final static String PROPERTY_KEY_OS_VERSION = "os.version";

	/**
	 * Java 获取用户名称
	 */
	public final static String PROPERTY_KEY_USER_NAME = "user.name";

	/**
	 * Java 获取用户主页
	 */
	public final static String PROPERTY_KEY_USER_HOME = "user.home";

	/**
	 * Java 获取用户工作路径
	 */
	public final static String PROPERTY_KEY_USER_DIR = "user.dir";

	/**
	 * Java vm 规范版本
	 */
	public final static String PROPERTY_KEY_JAVA_VM_SPECIFICATION_VERSION = "java.vm.specification.version";

	/**
	 * Java vm 规范提供者
	 */
	public final static String PROPERTY_KEY_JAVA_VM_SPECIFICATION_VENDOR = "java.vm.specification.vendor";

	/**
	 * Java vm 规范名称
	 */
	public final static String PROPERTY_KEY_JAVA_VM_SPECIFICATION_NAME = "java.vm.specification.name";

	/**
	 * Java 规范版本
	 */
	public final static String PROPERTY_KEY_JAVA_SPECIFICATION_VERSION = "java.specification.version";

	/**
	 * Java 规范提供者
	 */
	public final static String PROPERTY_KEY_JAVA_SPECIFICATION_VENDOR = "java.specification.vendor";

	/**
	 * Java 规范名称
	 */
	public final static String PROPERTY_KEY_JAVA_SPECIFICATION_NAME = "java.specification.name";

	/**
	 * Java vm 版本
	 */
	public final static String PROPERTY_KEY_JAVA_VM_VERSION = "java.vm.version";

	/**
	 * Java vm 提供者
	 */
	public final static String PROPERTY_KEY_JAVA_VM_VENDOR = "java.vm.vendor";

	/**
	 * Java vm 名称
	 */
	public final static String PROPERTY_KEY_JAVA_VM_NAME = "java.vm.name";

	/**
	 * 获取系统属性
	 * 
	 * @param key
	 *            键
	 * @return null 时返回空白字符
	 */
	public static String getSystemProperty(String key) {
		String value = System.getProperty(key);
		if (value == null) {
			return "";
		}
		return value;
	}

	/**
	 * 换行符 Line separator ("\n" on Unix)
	 */
	public static final String LINE_SEPARATOR = getSystemProperty(PROPERTY_KEY_LINE_SEPARATOR);

	/**
	 * 路径分隔符 Path separator (":" on Unix , ";" on windows)
	 */
	public static final String PATH_SEPARATOR = getSystemProperty(PROPERTY_KEY_PATH_SEPARATOR);

	/**
	 * 文件分隔符 File separator ("/" on Unix, "\" on windows)
	 */
	public static final String FILE_SEPARATOR = getSystemProperty(PROPERTY_KEY_FILE_SEPARATOR);

	/**
	 * Java 版本
	 */
	public static final String JAVA_VERSION = getSystemProperty(PROPERTY_KEY_JAVA_VERSION);

	/**
	 * Java vendor
	 */
	public static final String JAVA_VENDOR = getSystemProperty(PROPERTY_KEY_JAVA_VENDOR);

	/**
	 * Java vendor url
	 */
	public static final String JAVA_VENDOR_URL = getSystemProperty(PROPERTY_KEY_JAVA_VENDOR_URL);

	/**
	 * Java 主页
	 */
	public static final String JAVA_HOME = getSystemProperty(PROPERTY_KEY_JAVA_HOME);

	/**
	 * Java 类版本
	 */
	public static final String JAVA_CLASS_VERSION = getSystemProperty(PROPERTY_KEY_JAVA_CLASS_VERSION);

	/**
	 * Java 类路径
	 */
	public static final String JAVA_CLASS_PATH = getSystemProperty(PROPERTY_KEY_JAVA_CLASS_PATH);

	/**
	 * 操作系统名称
	 */
	public static final String OS_NAME = getSystemProperty(PROPERTY_KEY_OS_NAME);

	/**
	 * 操作系统结构
	 */
	public static final String OS_ARCH = getSystemProperty(PROPERTY_KEY_OS_ARCH);

	/**
	 * 操作系统版本
	 */
	public static final String OS_VERSION = getSystemProperty(PROPERTY_KEY_OS_VERSION);

	/**
	 * 用户名称
	 */
	public static final String USER_NAME = getSystemProperty(PROPERTY_KEY_USER_NAME);

	/**
	 * 用户主页
	 */
	public static final String USER_HOME = getSystemProperty(PROPERTY_KEY_USER_HOME);

	/**
	 * 用户工作目录
	 */
	public static final String USER_DIR = getSystemProperty(PROPERTY_KEY_USER_DIR);

	/**
	 * vm 规范版本
	 */
	public static final String JAVA_VM_SPECIFICATION_VERSION = getSystemProperty(
			PROPERTY_KEY_JAVA_VM_SPECIFICATION_VERSION);

	/**
	 * vm 规范提供者
	 */
	public static final String JAVA_VM_SPECIFICATION_VENDOR = getSystemProperty(
			PROPERTY_KEY_JAVA_VM_SPECIFICATION_VENDOR);

	/**
	 * vm 规范名称
	 */
	public static final String JAVA_VM_SPECIFICATION_NAME = getSystemProperty(PROPERTY_KEY_JAVA_VM_SPECIFICATION_NAME);

	/**
	 * Java 规范版本
	 */
	public static final String JAVA_SPECIFICATION_VERSION = getSystemProperty(PROPERTY_KEY_JAVA_SPECIFICATION_VERSION);

	/**
	 * Java 规范提供者
	 */
	public static final String JAVA_SPECIFICATION_VENDOR = getSystemProperty(PROPERTY_KEY_JAVA_SPECIFICATION_VENDOR);

	/**
	 * Java 规范名称
	 */
	public static final String JAVA_SPECIFICATION_NAME = getSystemProperty(PROPERTY_KEY_JAVA_SPECIFICATION_NAME);

	/**
	 * vm 版本
	 */
	public static final String JAVA_VM_VERSION = getSystemProperty(PROPERTY_KEY_JAVA_VM_VERSION);

	/**
	 * vm 提供者
	 */
	public static final String JAVA_VM_VENDOR = getSystemProperty(PROPERTY_KEY_JAVA_VM_VENDOR);

	/**
	 * vm 名称
	 */
	public static final String JAVA_VM_NAME = getSystemProperty(PROPERTY_KEY_JAVA_VM_NAME);

}
