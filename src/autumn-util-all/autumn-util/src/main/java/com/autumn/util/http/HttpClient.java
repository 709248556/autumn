package com.autumn.util.http;

import com.autumn.exception.ExceptionUtils;
import com.autumn.exception.NetworkException;
import com.autumn.util.StringUtils;
import org.apache.commons.io.IOUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpMessage;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.*;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Http Client 客户端
 *
 * @Description TODO
 * @Author 老码农
 * @Date 2019-10-02 10:13
 */
public class HttpClient {

    /**
     * Sdk版本
     */
    public static final String SDK_VERSION = "AutunmSDK/" + HttpUtils.class.getPackage().getImplementationVersion();

    /**
     * 用户 agent
     */
    public static final String USER_AGENT = SDK_VERSION + " (" + System.getProperty("os.arch") + " " + System.getProperty("os.name") + " " + System.getProperty("os.version") +
            ") Java/" + System.getProperty("java.version") + " HttpClient/" + org.apache.http.client.HttpClient.class.getPackage().getImplementationVersion();

    /**
     * 连接超时(默认15秒)
     */
    private int connectTimeoutMs = 15000;
    /**
     * 读取超时(默认60秒)
     */
    private int readTimeoutMs = 60000;
    private String userAgent = USER_AGENT;
    private String contentType = "text/xml";
    private Charset charset = StandardCharsets.UTF_8;
    private List<Header> headers = new ArrayList<>(16);

    /**
     * 获取连接超时时间，单位毫秒
     *
     * @return
     */
    public int getConnectTimeoutMs() {
        return this.connectTimeoutMs;
    }

    /**
     * 设置连接超时时间，单位毫秒
     *
     * @param connectTimeoutMs 连接超时时间，单位毫秒
     */
    public void setConnectTimeoutMs(int connectTimeoutMs) {
        this.connectTimeoutMs = connectTimeoutMs;
    }

    /**
     * 获取读数据超时时间，单位毫秒
     *
     * @return
     */
    public int getReadTimeoutMs() {
        return this.readTimeoutMs;
    }

    /**
     * 设置读数据超时时间，单位毫秒
     *
     * @param readTimeoutMs 读数据超时时间，单位毫秒
     */
    public void setReadTimeoutMs(int readTimeoutMs) {
        this.readTimeoutMs = readTimeoutMs;
    }

    /**
     * 获取用户 agent
     *
     * @return
     */
    public String getUserAgent() {
        return this.userAgent;
    }

    /**
     * 设置用户 agent
     *
     * @param userAgent
     */
    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    /**
     * 获取 ContentType 类型
     *
     * @return
     */
    public String getContentType() {
        return this.contentType;
    }

    /**
     * 设置 ContentType 类型
     *
     * @param contentType ContentType 类型
     */
    public void setContentType(String contentType) {
        this.contentType = ExceptionUtils.checkNotNullOrBlank(contentType, "contentType");
    }

    /**
     * 获取编码
     *
     * @return
     */
    public Charset getCharset() {
        return this.charset;
    }

    /**
     * 设置编码
     *
     * @param charset
     */
    public void setCharset(Charset charset) {
        this.charset = ExceptionUtils.checkNotNull(charset, "charset");
    }

    /**
     *
     */
    public HttpClient() {

    }

    /**
     * 添加头
     *
     * @param header 头
     */
    public void addHeader(Header header) {
        this.headers.add(ExceptionUtils.checkNotNull(header, "header"));
    }

    /**
     * 添加头
     *
     * @param name  名称
     * @param value 值
     */
    public void addHeader(String name, String value) {
        this.headers.add(new BasicHeader(name, value));
    }

    /**
     * 创建 Http 客户端
     *
     * @return
     */
    protected final CloseableHttpClient createHttpClient(HttpClientConnectionManager connectionManager) {
        HttpClientBuilder builder = HttpClientBuilder.create();
        if (connectionManager != null) {
            builder.setConnectionManager(connectionManager);
        }
        return builder.build();
    }

    /**
     * 创建请求配置
     *
     * @return
     */
    protected final RequestConfig createRequestConfig() {
        return RequestConfig.custom()
                .setSocketTimeout(this.getReadTimeoutMs())
                .setConnectTimeout(this.getConnectTimeoutMs()).build();
    }

    /**
     * 设置Http消息
     *
     * @param httpMessage
     */
    protected final void setHttpMessage(HttpMessage httpMessage) {
        httpMessage.addHeader("Content-Type", this.getContentType() + ";" + this.getCharset().name());
        httpMessage.addHeader("User-Agent", this.getUserAgent());
        for (Header header : this.headers) {
            httpMessage.addHeader(header);
        }
    }

    /**
     * post 调用
     *
     * @param url url地址
     * @return
     * @throws IOException 网络引发异常
     */
    public String doPostForString(String url) throws IOException {
        return doPostForString(url, (String) null, null);
    }

    /**
     * post 调用并返回字符
     *
     * @param url url地址
     * @return
     * @throws IOException 网络引发异常
     */
    public String doPostForString(String url, Map<String, String> params) throws IOException {
        return doPostForString(url, params, null);
    }

    /**
     * post 调用并返回字符
     *
     * @param url  url地址
     * @param data 数据
     * @return
     * @throws IOException 网络引发异常
     */
    public String doPostForString(String url, String data) throws IOException {
        return doPostForString(url, data, null);
    }

    /**
     * post 调用并返回字符
     *
     * @param url    url地址
     * @param params 参数
     * @return
     * @throws IOException 网络引发异常
     */
    public String doPostForString(String url, Map<String, String> params, HttpClientConnectionManager connectionManager) throws IOException {
        return httpResponseString(url, doPostForResponse(url, params, connectionManager));
    }

    /**
     * post 调用并返回字符
     *
     * @param url  url地址
     * @param data 数据
     * @return
     * @throws IOException 网络引发异常
     */
    public String doPostForString(String url, String data, HttpClientConnectionManager connectionManager) throws IOException {
        return httpResponseString(url, doPostForResponse(url, data, connectionManager));
    }

    /**
     * post 调用并返回 Response
     *
     * @param url url地址
     * @return
     * @throws IOException 网络引发异常
     */
    public CloseableHttpResponse doPostForResponse(String url) throws IOException {
        return doPostForResponseByHttpEntity(url, null, null);
    }

    /**
     * post 调用并返回 Response
     *
     * @param url    url地址
     * @param params 参数
     * @return
     * @throws IOException 网络引发异常
     */
    public CloseableHttpResponse doPostForResponse(String url, Map<String, String> params) throws IOException {
        return doPostForResponse(url, params, null);
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
    public CloseableHttpResponse doPostForResponse(String url, Map<String, String> params, HttpClientConnectionManager connectionManager) throws IOException {
        if (params != null) {
            List<NameValuePair> paramList = new ArrayList<>();
            for (String key : params.keySet()) {
                String value = params.get(key);
                if (StringUtils.isNotNullOrBlank(key) && StringUtils.isNotNullOrBlank(value)) {
                    paramList.add(new BasicNameValuePair(key, value));
                }
            }
            return doPostForResponseByHttpEntity(url, new UrlEncodedFormEntity(paramList, this.getCharset()), connectionManager);
        }
        return doPostForResponseByHttpEntity(url, null, connectionManager);
    }

    /**
     * post 调用并返回 Response
     *
     * @param url  url地址
     * @param data 数据
     * @return
     * @throws IOException 网络引发异常
     */
    public CloseableHttpResponse doPostForResponse(String url, String data) throws IOException {
        if (StringUtils.isNotNullOrBlank(data)) {
            return doPostForResponseByHttpEntity(url, new StringEntity(data, this.getCharset()), null);
        }
        return doPostForResponseByHttpEntity(url, null, null);
    }

    /**
     * post 调用并返回 Response
     *
     * @param url               url地址
     * @param data              数据
     * @param connectionManager 连接管理
     * @return
     * @throws IOException 网络引发异常
     */
    public CloseableHttpResponse doPostForResponse(String url, String data, HttpClientConnectionManager connectionManager) throws IOException {
        if (StringUtils.isNotNullOrBlank(data)) {
            return doPostForResponseByHttpEntity(url, new StringEntity(data, this.getCharset()), connectionManager);
        }
        return doPostForResponseByHttpEntity(url, null, connectionManager);
    }

    /**
     * post 调用并返回 Response
     *
     * @param url               url地址
     * @param httpEntity        数据
     * @param connectionManager 连接管理
     * @return
     * @throws IOException 网络引发异常
     */
    private CloseableHttpResponse doPostForResponseByHttpEntity(String url, HttpEntity httpEntity, HttpClientConnectionManager connectionManager) throws IOException {
        ExceptionUtils.checkNotNullOrBlank(url, "url");
        HttpPost httpPost = new HttpPost(url);
        if (httpEntity != null) {
            httpPost.setEntity(httpEntity);
        }
        return doForResponse(httpPost, connectionManager);
    }

    /**
     * get 调用并返回字符
     *
     * @param url url地址
     * @return
     * @throws IOException 网络引发异常
     */
    public String doGetForString(String url) throws IOException {
        return doGetForString(url, null, null);
    }

    /**
     * get 调用并返回字符
     *
     * @param url    url地址
     * @param params 参数
     * @return
     * @throws IOException 网络引发异常
     */
    public String doGetForString(String url, Map<String, String> params) throws IOException {
        return doGetForString(url, params, null);
    }

    /**
     * get 调用并返回字符
     *
     * @param url               url地址
     * @param params            参数
     * @param connectionManager 连接管理
     * @return
     * @throws IOException 网络引发异常
     */
    public String doGetForString(String url, Map<String, String> params, HttpClientConnectionManager connectionManager) throws IOException {
        return httpResponseString(url, doGetForResponse(url, params, connectionManager));
    }

    /**
     * get 调用并返回 Response
     *
     * @param url url地址
     * @return
     * @throws IOException 网络引发异常
     */
    public CloseableHttpResponse doGetForResponse(String url) throws IOException {
        return doGetForResponse(url, null, null);
    }

    /**
     * get 调用并返回 Response
     *
     * @param url    url地址
     * @param params 参数
     * @return
     * @throws IOException 网络引发异常
     */
    public CloseableHttpResponse doGetForResponse(String url, Map<String, String> params) throws IOException {
        return doGetForResponse(url, params, null);
    }

    /**
     * get 调用并返回 Response
     *
     * @param url               url地址
     * @param params            参数
     * @param connectionManager 连接管理
     * @return
     * @throws IOException 网络引发异常
     */
    public CloseableHttpResponse doGetForResponse(String url, Map<String, String> params, HttpClientConnectionManager connectionManager) throws IOException {
        ExceptionUtils.checkNotNullOrBlank(url, "url");
        try {
            URIBuilder builder = new URIBuilder(url);
            if (params != null) {
                for (String key : params.keySet()) {
                    String value = params.get(key);
                    if (StringUtils.isNotNullOrBlank(key) && StringUtils.isNotNullOrBlank(value)) {
                        builder.addParameter(key, value);
                    }
                }
            }
            HttpGet httpGet = new HttpGet(builder.build());
            return doForResponse(httpGet, connectionManager);
        } catch (URISyntaxException err) {
            throw ExceptionUtils.throwValidationException("无效的Url地址：" + url);
        }
    }

    /**
     * 调用并响应
     *
     * @param request           请求
     * @param connectionManager 连接
     * @return
     * @throws IOException
     */
    private CloseableHttpResponse doForResponse(HttpRequestBase request, HttpClientConnectionManager connectionManager) throws IOException {
        CloseableHttpClient httpClient = null;
        try {
            httpClient = this.createHttpClient(connectionManager);
            this.setHttpMessage(request);
            request.setConfig(this.createRequestConfig());
            return httpClient.execute(request);
        } catch (IOException err) {
            if (httpClient != null) {
                IOUtils.closeQuietly(httpClient);
            }
            throw err;
        } catch (Exception err) {
            if (httpClient != null) {
                IOUtils.closeQuietly(httpClient);
            }
            throw new NetworkException(err.getMessage(), err);
        }
    }

    private String httpResponseString(String url, CloseableHttpResponse response) throws IOException {
        if (response == null) {
            throw new NetworkException(url + " 网络响应为 null。");
        }
        HttpEntity entity = null;
        try {
            if (response.getStatusLine().getStatusCode() == 200) {
                entity = response.getEntity();
                if (entity == null) {
                    return null;
                }
                return EntityUtils.toString(entity, this.getCharset());
            }
            throw new NetworkException(url + " 网络响应[" + response.getStatusLine().getStatusCode() + "]。");
        } finally {
            EntityUtils.consume(entity);
            IOUtils.closeQuietly(response);
        }
    }
}
