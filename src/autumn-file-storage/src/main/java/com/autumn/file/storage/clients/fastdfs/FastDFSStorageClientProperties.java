package com.autumn.file.storage.clients.fastdfs;

import com.autumn.file.storage.properties.AbstractStorageClientProperties;
import com.autumn.file.storage.properties.AutumnStorageProperties;
import org.csource.fastdfs.ClientGlobal;

import java.io.Serializable;
import java.util.Properties;

/**
 * FastDFS 属性
 *
 * @Description TODO
 * @Author 老码农
 * @Date 2019-09-03 4:01
 */
public class FastDFSStorageClientProperties extends AbstractStorageClientProperties {

    private static final long serialVersionUID = -1542557881330522318L;

    /**
     * bean条件属性
     */
    public static final String BEAN_CONDITIONAL_PROPERTY = AutumnStorageProperties.PREFIX + ".fastDFS.enable";

    /**
     * 通道 Bean 名称
     */
    public static final String CHANNEL_BEAN_NAME = CHANNEL_BEAN_PREFIX + "FastDFS" + CHANNEL_BEAN_SUFFIX;

    /**
     * 跟踪服务器列表
     * <p>
     * 示例  10.0.11.201:22122,10.0.11.202:22122,10.0.11.203:22122
     * </p>
     */
    private String trackerServers;

    /**
     * 连接超时(秒)
     */
    private int connectTimeoutSeconds = 5;

    /**
     * 网络超时(秒)
     */
    private int networkTimeoutSeconds = 30;

    /**
     * 编码
     */
    private String charset = "UTF-8";

    /**
     * http配置
     */
    private FastDFSHttp http = new FastDFSHttp();

    /**
     * 获取跟踪服务器列表
     *
     * @return
     */
    public String getTrackerServers() {
        return this.trackerServers;
    }

    /**
     * 设置跟踪服务器列表
     *
     * @param trackerServers
     */
    public void setTrackerServers(String trackerServers) {
        this.trackerServers = trackerServers;
    }


    /**
     * 获取连接超时(秒)
     *
     * @return
     */
    public int getConnectTimeoutSeconds() {
        return connectTimeoutSeconds;
    }

    /**
     * 设置连接超时(秒)
     *
     * @param connectTimeoutSeconds
     */
    public void setConnectTimeoutSeconds(int connectTimeoutSeconds) {
        this.connectTimeoutSeconds = connectTimeoutSeconds;
    }

    /**
     * 获取网络超时(秒)
     *
     * @return
     */
    public int getNetworkTimeoutSeconds() {
        return networkTimeoutSeconds;
    }

    /**
     * 设置网络超时(秒)
     *
     * @param networkTimeoutSeconds
     */
    public void setNetworkTimeoutSeconds(int networkTimeoutSeconds) {
        this.networkTimeoutSeconds = networkTimeoutSeconds;
    }

    /**
     * 获取编码
     *
     * @return
     */
    public String getCharset() {
        return charset;
    }

    /**
     * 设置编码
     *
     * @param charset
     */
    public void setCharset(String charset) {
        this.charset = charset;
    }


    /**
     * 获取http配置
     *
     * @return
     */
    public FastDFSHttp getHttp() {
        return http;
    }

    /**
     * 设置http配置
     *
     * @param http
     */
    public void setHttp(FastDFSHttp http) {
        this.http = http;
    }

    /**
     * 创建FastDFSProperties属性
     *
     * @return
     */
    public Properties createFastDFSProperties() {
        if (this.getHttp() == null) {
            this.setHttp(new FastDFSHttp());
        }
        Properties properties = new Properties();
        properties.put(ClientGlobal.PROP_KEY_TRACKER_SERVERS, this.getTrackerServers());
        properties.put(ClientGlobal.PROP_KEY_CONNECT_TIMEOUT_IN_SECONDS, this.getConnectTimeoutSeconds());
        properties.put(ClientGlobal.PROP_KEY_NETWORK_TIMEOUT_IN_SECONDS, this.getNetworkTimeoutSeconds());
        properties.put(ClientGlobal.PROP_KEY_CHARSET, this.getCharset());
        properties.put(ClientGlobal.PROP_KEY_HTTP_ANTI_STEAL_TOKEN, this.getHttp().isAntiStealToken());
        properties.put(ClientGlobal.PROP_KEY_HTTP_SECRET_KEY, this.getHttp().getSecretKey());
        properties.put(ClientGlobal.PROP_KEY_HTTP_TRACKER_HTTP_PORT, this.getHttp().getTrackerHttpPort());
        return properties;
    }

    /**
     * Http配置
     */
    public static class FastDFSHttp implements Serializable {

        private static final long serialVersionUID = 1665790660513316844L;

        /**
         * 跟踪Http端口
         */
        private int trackerHttpPort = 8080;

        /**
         * 启用票据
         */
        private boolean antiStealToken = false;

        /**
         * 票据Key
         */
        private String secretKey = "FastDFS1234567890";

        /**
         * 获取跟踪Http端口
         *
         * @return
         */
        public int getTrackerHttpPort() {
            return trackerHttpPort;
        }

        /**
         * 设置跟踪Http端口
         *
         * @param trackerHttpPort
         */
        public void setTrackerHttpPort(int trackerHttpPort) {
            this.trackerHttpPort = trackerHttpPort;
        }

        /**
         * 是否启用票据
         *
         * @return
         */
        public boolean isAntiStealToken() {
            return antiStealToken;
        }

        /**
         * 设置启用票据
         *
         * @param antiStealToken
         */
        public void setAntiStealToken(boolean antiStealToken) {
            this.antiStealToken = antiStealToken;
        }

        /**
         * 获取票据Key
         *
         * @return
         */
        public String getSecretKey() {
            return secretKey;
        }

        /**
         * 设置票据Key
         *
         * @param secretKey
         */
        public void setSecretKey(String secretKey) {
            this.secretKey = secretKey;
        }
    }

}
