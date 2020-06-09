package com.autumn.util.http;


import com.autumn.exception.ExceptionUtils;
import com.autumn.util.Base64Utils;
import org.apache.commons.io.IOUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.DefaultHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.conn.BasicHttpClientConnectionManager;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.util.Map;

/**
 * Http 实例工具
 *
 * @Description TODO
 * @Author 老码农
 * @Date 2019-07-26 16:40
 */
public class HttpUtils {

    /**
     * 创建 Http 默认连接管理
     *
     * @return
     */
    public static HttpClientConnectionManager createDefaultConnectionManager() {
        return new BasicHttpClientConnectionManager();
    }

    /**
     * 创建 Http SSL连接管理
     *
     * @param certBase64 证书的Base64
     * @param password   密码
     * @return
     * @throws Exception
     */
    public static HttpClientConnectionManager createSSLConnectionManager(String certBase64, String password) throws Exception {
        return createSSLConnectionManager(certBase64, password, StandardCharsets.UTF_8);
    }

    /**
     * 创建 Http SSL连接管理
     *
     * @param certBase64 证书的Base64
     * @param password   密码
     * @param charset    编码
     * @return
     * @throws Exception
     */
    public static HttpClientConnectionManager createSSLConnectionManager(String certBase64, String password, Charset charset) throws Exception {
        ExceptionUtils.checkNotNullOrBlank(certBase64, "certBase64");
        if (charset == null) {
            charset = StandardCharsets.UTF_8;
        }
        InputStream certStream = null;
        try {
            byte[] keyBytes = Base64Utils.decode(certBase64.getBytes(charset));
            certStream = new ByteArrayInputStream(keyBytes);
            return createSSLConnectionManager(certStream, password);
        } finally {
            IOUtils.closeQuietly(certStream);
        }
    }

    /**
     * 创建 Http SSL连接管理
     *
     * @param certStream 证书流
     * @param password   密码
     * @return
     * @throws Exception
     */
    public static HttpClientConnectionManager createSSLConnectionManager(InputStream certStream, String password) throws Exception {
        ExceptionUtils.checkNotNull(certStream, "certStream");
        if (password == null) {
            password = "";
        }
        char[] passwordArray = password.toCharArray();
        KeyStore keyStore = KeyStore.getInstance("PKCS12");
        keyStore.load(certStream, passwordArray);
        return createSSLConnectionManager(keyStore, password);
    }

    /**
     * 创建 Http SSL连接管理
     *
     * @param keyStore 键
     * @param password 密码
     * @return
     * @throws Exception
     */
    public static HttpClientConnectionManager createSSLConnectionManager(KeyStore keyStore, String password)
            throws Exception {
        ExceptionUtils.checkNotNull(keyStore, "keyStore");
        if (password == null) {
            password = "";
        }
        // 实例化密钥库 & 初始化密钥工厂
        KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
        kmf.init(keyStore, password.toCharArray());

        // 创建 SSLContext
        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(kmf.getKeyManagers(), null, new SecureRandom());
        SSLConnectionSocketFactory sslConnectionSocketFactory = new SSLConnectionSocketFactory(
                sslContext,
                new String[]{"TLSv1"},
                null,
                new DefaultHostnameVerifier());
        return new BasicHttpClientConnectionManager(
                RegistryBuilder.<ConnectionSocketFactory>create()
                        .register("http", PlainConnectionSocketFactory.getSocketFactory())
                        .register("https", sslConnectionSocketFactory)
                        .build(),
                null,
                null,
                null
        );
    }

    /**
     * 创建 Http 连接池管理
     *
     * @param maxPerRoute
     * @param maxTotal
     * @return
     */
    public static HttpClientConnectionManager createPoolingConnectionManager(int maxPerRoute, int maxTotal) {
        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();
        connectionManager.setDefaultMaxPerRoute(maxPerRoute);
        connectionManager.setMaxTotal(maxTotal);
        return connectionManager;
    }


    /**
     * 创建客户端
     *
     * @return
     */
    private static HttpClient createHttpClient() {
        return new HttpClient();
    }

    /**
     * get 调用并返回 String
     *
     * @param url url地址
     * @return
     * @throws IOException 网络出错引发异常
     */
    public static String doGetForString(String url) throws IOException {
        return createHttpClient().doGetForString(url);
    }

    /**
     * get 调用并返回 Response
     *
     * @param url url地址
     * @return
     * @throws IOException 网络出错引发异常
     */
    public static CloseableHttpResponse doGetForResponse(String url) throws IOException {
        return createHttpClient().doGetForResponse(url);
    }

    /**
     * get 调用并返回 String
     *
     * @param url    url地址
     * @param params 参数
     * @return
     * @throws IOException 网络出错引发异常
     */
    public static String doGetForString(String url, Map<String, String> params) throws IOException {
        return createHttpClient().doGetForString(url, params);
    }

    /**
     * get 调用并返回 Response
     *
     * @param url    url地址
     * @param params 参数
     * @return
     * @throws IOException 网络出错引发异常
     */
    public static CloseableHttpResponse doGetForResponse(String url, Map<String, String> params) throws IOException {
        return createHttpClient().doGetForResponse(url, params);
    }

    /**
     * get 调用并返回 String
     *
     * @param url               url地址
     * @param params            参数
     * @param connectionManager 连接管理
     * @return
     * @throws IOException 网络出错引发异常
     */
    public static String doGetForString(String url, Map<String, String> params, HttpClientConnectionManager connectionManager) throws IOException {
        return createHttpClient().doGetForString(url, params, connectionManager);
    }

    /**
     * get 调用并返回 Response
     *
     * @param url               url地址
     * @param params            参数
     * @param connectionManager 连接管理
     * @return
     * @throws IOException 网络出错引发异常
     */
    public static CloseableHttpResponse doGetForResponse(String url, Map<String, String> params, HttpClientConnectionManager connectionManager) throws IOException {
        return createHttpClient().doGetForResponse(url, params, connectionManager);
    }

    /**
     * post 调用并返回 String
     *
     * @param url url地址
     * @return
     * @throws IOException 网络引发异常
     */
    public static String doPostForString(String url) throws IOException {
        return createHttpClient().doPostForString(url);
    }

    /**
     * post 调用并返回 Response
     *
     * @param url url地址
     * @return
     * @throws IOException 网络引发异常
     */
    public static CloseableHttpResponse doPostForResponse(String url) throws IOException {
        return createHttpClient().doPostForResponse(url);
    }

    /**
     * post 调用并返回 String
     *
     * @param url    url地址
     * @param params 参数
     * @return
     * @throws IOException 网络引发异常
     */
    public static String doPostForString(String url, Map<String, String> params) throws IOException {
        return createHttpClient().doPostForString(url, params);
    }

    /**
     * post 调用并返回 Response
     *
     * @param url    url地址
     * @param params 参数
     * @return
     * @throws IOException 网络引发异常
     */
    public static CloseableHttpResponse doPostForResponse(String url, Map<String, String> params) throws IOException {
        return createHttpClient().doPostForResponse(url, params);
    }

    /**
     * post 调用并返回 String
     *
     * @param url               url地址
     * @param params            参数
     * @param connectionManager 连接管理
     * @return
     * @throws IOException 网络引发异常
     */
    public static String doPostForString(String url, Map<String, String> params, HttpClientConnectionManager connectionManager) throws IOException {
        return createHttpClient().doPostForString(url, params, connectionManager);
    }

    /**
     * post 调用并返回 Response
     *
     * @param url               url地址
     * @param params            参数
     * @param connectionManager 连接管理
     * @return
     * @throws IOException 网络引发异常
     */
    public static CloseableHttpResponse doPostForResponse(String url, Map<String, String> params, HttpClientConnectionManager connectionManager) throws IOException {
        return createHttpClient().doPostForResponse(url, params, connectionManager);
    }

}
