package com.autumn.spring.boot.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Cloud 配置属性
 * 
 * @author 老码农
 *
 *         2018-06-29 17:39:37
 */
@ConfigurationProperties(prefix = AutumnCloudProperties.PREFIX)
public class AutumnCloudProperties extends AbstractAutumnProperties {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2982879227452290402L;
	/**
	 * 属性前缀
	 */
	public final static String PREFIX = "autumn.cloud";

	/**
	 * 
	 */
	public AutumnCloudProperties() {

	}

	/**
	 * 
	 */
	private Config config;

	/**
	 * 获取配置
	 * 
	 * @return
	 *
	 */
	public Config getConfig() {
		if (this.config == null) {
			this.config = new Config();
			this.config.setEnable(false);
		}
		return this.config;
	}

	/**
	 * 设置配置
	 * 
	 * @param config
	 *
	 */
	public void setConfig(Config config) {
		this.config = config;
	}

	/**
	 * 重写
	 * 
	 * @param environment
	 * @return
	 *
	 */
	public static AutumnCloudProperties override(org.springframework.core.env.Environment environment) {
		AutumnCloudProperties override = new AutumnCloudProperties();
		override.getConfig().setName(environment
				.resolvePlaceholders("${" + Config.PREFIX_CONFIG + ".name:${spring.application.name:application}}"));
		if (environment.containsProperty(Config.PREFIX_CONFIG + ".profile")) {
			override.getConfig().setProfile(environment.getProperty(Config.PREFIX_CONFIG + ".profile"));
		}
		if (environment.containsProperty(Config.PREFIX_CONFIG + ".label")) {
			override.getConfig().setLabel(environment.getProperty(Config.PREFIX_CONFIG + ".label"));
		}
		if (environment.containsProperty(Config.PREFIX_CONFIG + ".enable")) {
			if ("true".equals(environment.getProperty(Config.PREFIX_CONFIG + ".enable").trim())) {
				override.getConfig().setEnable(true);
			} else {
				override.getConfig().setEnable(false);
			}
		} else {
			override.getConfig().setEnable(true);
		}
		return override;
	}

	/**
	 * 配置
	 * 
	 * @author 老码农
	 *
	 *         2018-06-29 17:41:17
	 */
	public class Config {
		/**
		 * 默认标签
		 */
		public static final String DEFAULT_LABEL = "master";

		/**
		 * 默认配置
		 */
		public static final String DEFAULT_PROFILE = "pro";

		/**
		 * 配置前缀
		 */
		public static final String PREFIX_CONFIG = AutumnCloudProperties.PREFIX + ".config";

		private boolean enable = true;
		private String name = "";
		private String label = DEFAULT_LABEL;
		private String profile = DEFAULT_PROFILE;

		/**
		 * 获取是否启用
		 * 
		 * @return
		 *
		 */
		public boolean isEnable() {
			return enable;
		}

		/**
		 * 设置是否启用
		 * 
		 * @param enable
		 *
		 */
		public void setEnable(boolean enable) {
			this.enable = enable;
		}

		/**
		 * 获取应用名称
		 * 
		 * @return null 或空白值则到 spring.application.name
		 *
		 */
		public String getName() {
			return this.name;
		}

		/**
		 * 设置应用名称
		 * 
		 * @param name
		 *            应用名称
		 *
		 */
		public void setName(String name) {
			this.name = name;
		}

		/**
		 * 标签(分支名称)
		 * 
		 * @return 如 master 等
		 *
		 */
		public String getLabel() {
			return label;
		}

		/**
		 * 设置标签(分支名称)
		 * 
		 * @param label
		 *            如 master 等
		 *
		 */
		public void setLabel(String label) {
			this.label = label;
		}

		/**
		 * 获取配置名称
		 * 
		 * @return 如 dev、pro
		 *
		 */
		public String getProfile() {
			return profile;
		}

		/**
		 * 设置配置名称
		 * 
		 * @param profile
		 *            如 dev、pro
		 *
		 */
		public void setProfile(String profile) {
			this.profile = profile;
		}
	}

}
