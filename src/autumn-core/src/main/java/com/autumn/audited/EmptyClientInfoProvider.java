package com.autumn.audited;

/**
 * 只读的客户端信息
 * 
 * @author 老码农
 *
 *         2017-10-20 11:03:30
 */
public class EmptyClientInfoProvider implements ClientInfoProvider {

	@Override
	public ClientBrowserInfo getBrowserInfo() {
		return WebClientBrowserInfo.INSTANCE;
	}

	@Override
	public String getClientIpAddress() {
		return "";
	}

	@Override
	public String getClientPlatformVersion() {
		return "";
	}

	@Override
	public String getClientPlatformName() {
		return "";
	}

}
