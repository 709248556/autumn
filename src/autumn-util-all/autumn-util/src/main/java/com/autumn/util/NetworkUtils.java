package com.autumn.util;

import java.net.InetAddress;

/**
 * 网络工具
 * 
 * @author 老码农
 *         <p>
 *         Description
 *         </p>
 * @date 2018-01-31 20:39:13
 */
public class NetworkUtils {

	/**
	 * 获取主机地址
	 * 
	 * @return
	 */
	public static String getHostAddress() {
		try {
			return InetAddress.getLocalHost().getHostAddress();
		} catch (Exception e) {
			return "127.0.0.1";
		}
	}
	
}
