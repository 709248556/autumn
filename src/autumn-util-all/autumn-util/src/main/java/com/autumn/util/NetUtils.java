package com.autumn.util;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

/**
 * 网络工具
 *
 * @Description TODO
 * @Author 老码农
 * @Date 2019-08-23 23:40
 */
public class NetUtils {

    /**
     * 默认本机ip
     */
    private static final String DEFAULT_LOCAL_ADDRESS_STRING = "127.0.0.1";

    /**
     * Pre-loaded local address
     */
    private static final InetAddress LOCAL_ADDRESS;

    static {
        try {
            LOCAL_ADDRESS = getLocalInetAddress();
        } catch (SocketException e) {
            throw new RuntimeException("fail to get local ip.");
        }
    }

    /**
     * 获取地址网络地址
     *
     * @return
     * @throws SocketException
     */
    private static InetAddress getLocalInetAddress() throws SocketException {
        // enumerates all network interfaces
        Enumeration<NetworkInterface> enu = NetworkInterface.getNetworkInterfaces();
        while (enu.hasMoreElements()) {
            NetworkInterface ni = enu.nextElement();
            if (ni.isLoopback()) {
                continue;
            }
            Enumeration<InetAddress> addressEnumeration = ni.getInetAddresses();
            while (addressEnumeration.hasMoreElements()) {
                InetAddress address = addressEnumeration.nextElement();
                if (address.isLinkLocalAddress() || address.isLoopbackAddress() || address.isAnyLocalAddress()) {
                    continue;
                }
                return address;
            }
        }
        return null;
    }

    /**
     * 获取本地ip地址
     *
     * @return
     */
    public static String getLocalAddressString() {
        if (LOCAL_ADDRESS != null) {
            return LOCAL_ADDRESS.getHostAddress();
        }
        return DEFAULT_LOCAL_ADDRESS_STRING;
    }

    /**
     * 获取本地ip地址
     *
     * @return
     */
    public static InetAddress getLocalAddress() {
        return LOCAL_ADDRESS;
    }
}
