package com.autumn.evaluator;

/**
 * 解析上下文
 */
public class DefaultParseContext implements ParseContext {

	/**
	 * 获取默认解析上下文
	 */
	public static DefaultParseContext Default = new DefaultParseContext(new GeneralContext());

	/**
	 * 实例化 ParseContext 类新实例
	 *
	 * @param context
	 *            上下文
	 */
	public DefaultParseContext(Context context) {
		this.setContext(context);

	}

	/**
	 * 获取上下文
	 */
	private Context context;

	@Override
	public final Context getContext() {
		return context;
	}

	private void setContext(Context value) {
		context = value;
	}
}