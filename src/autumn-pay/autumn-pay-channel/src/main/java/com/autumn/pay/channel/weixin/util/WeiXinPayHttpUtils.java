package com.autumn.pay.channel.weixin.util;

import com.autumn.util.http.HttpUtils;
import org.apache.http.conn.HttpClientConnectionManager;

import java.nio.charset.StandardCharsets;


/**
 * 微信 Http 帮助
 *
 * @Description TODO
 * @Author 老码农
 * @Date 2019-09-30 11:39
 */
public class WeiXinPayHttpUtils {

    /**
     * 证书请求并返回xml
     *
     * @param url              url地址
     * @param xmlData          xml数据
     * @param mchId            商户id
     * @param certBase64       Base64证书编码
     * @param connectTimeoutMs 连接超时
     * @param readTimeoutMs    读取超时
     * @return
     * @throws Exception
     */
    public static String requestWithCert(final String url, String xmlData, String mchId, String certBase64, int connectTimeoutMs, int readTimeoutMs) throws Exception {
        HttpClientConnectionManager connectionManager = HttpUtils.createSSLConnectionManager(certBase64, mchId, StandardCharsets.UTF_8);
        com.autumn.util.http.HttpClient httpClient = new com.autumn.util.http.HttpClient();
        httpClient.setConnectTimeoutMs(connectTimeoutMs);
        httpClient.setReadTimeoutMs(readTimeoutMs);
        return httpClient.doPostForString(url, xmlData, connectionManager);

       /* InputStream certStream = null;
        try {
            byte[] keyBytes = Base64Utils.decode(certBase64.getBytes(StandardCharsets.UTF_8));
            certStream = new ByteArrayInputStream(keyBytes);
            char[] password = mchId.toCharArray();
            KeyStore ks = KeyStore.getInstance("PKCS12");
            ks.load(certStream, password);

            // 实例化密钥库 & 初始化密钥工厂
            KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
            kmf.init(ks, password);

            // 创建 SSLContext
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(kmf.getKeyManagers(), null, new SecureRandom());

            SSLConnectionSocketFactory sslConnectionSocketFactory = new SSLConnectionSocketFactory(
                    sslContext,
                    new String[]{"TLSv1"},
                    null,
                    new DefaultHostnameVerifier());

            BasicHttpClientConnectionManager clientConnectionManager = new BasicHttpClientConnectionManager(
                    RegistryBuilder.<ConnectionSocketFactory>create()
                            .register("http", PlainConnectionSocketFactory.getSocketFactory())
                            .register("https", sslConnectionSocketFactory)
                            .build(),
                    null,
                    null,
                    null
            );
            return postRequest(url, xmlData, connectTimeoutMs, readTimeoutMs, clientConnectionManager);
        } finally {
            IOUtils.closeQuietly(certStream);
        }*/
    }

    /**
     * 非证书请求并返回xml
     *
     * @param url              url地址
     * @param xmlData          xml数据
     * @param connectTimeoutMs 连接超时
     * @param readTimeoutMs    读取超时
     * @return
     * @throws Exception
     */
    public static String requestWithoutCert(final String url, String xmlData, int connectTimeoutMs, int readTimeoutMs) throws Exception {
        HttpClientConnectionManager connectionManager = HttpUtils.createDefaultConnectionManager();
        com.autumn.util.http.HttpClient httpClient = new com.autumn.util.http.HttpClient();
        httpClient.setConnectTimeoutMs(connectTimeoutMs);
        httpClient.setReadTimeoutMs(readTimeoutMs);
        return httpClient.doPostForString(url, xmlData, connectionManager);

       /* BasicHttpClientConnectionManager clientConnectionManager = new BasicHttpClientConnectionManager(
                RegistryBuilder.<ConnectionSocketFactory>create()
                        .register("http", PlainConnectionSocketFactory.getSocketFactory())
                        .register("https", SSLConnectionSocketFactory.getSocketFactory())
                        .build(), null, null, null
        );
        return postRequest(url, xmlData, connectTimeoutMs, readTimeoutMs, clientConnectionManager);*/


    }

   /* private static String postRequest(final String url, String xmlData, int connectTimeoutMs, int readTimeoutMs,
                                      BasicHttpClientConnectionManager clientConnectionManager) throws Exception {
        HttpClient httpClient = HttpClientBuilder
                .create()
                .setConnectionManager(clientConnectionManager)
                .build();
        HttpPost httpPost = new HttpPost(url);
        RequestConfig requestConfig = RequestConfig.custom()
                .setSocketTimeout(readTimeoutMs)
                .setConnectTimeout(connectTimeoutMs).build();
        httpPost.setConfig(requestConfig);
        StringEntity postEntity = new StringEntity(xmlData, "UTF-8");
        httpPost.addHeader("Content-Type", "text/xml");
        httpPost.addHeader("User-Agent", USER_AGENT);
        httpPost.setEntity(postEntity);
        HttpResponse httpResponse = httpClient.execute(httpPost);
        if (httpResponse.getStatusLine().getStatusCode() == 200) {
            return EntityUtils.toString(httpResponse.getEntity(), StandardCharsets.UTF_8);
        }
        throw new NetworkException(url + " 网络响应[" + httpResponse.getStatusLine().getStatusCode() + "]。");
    }*/
}
