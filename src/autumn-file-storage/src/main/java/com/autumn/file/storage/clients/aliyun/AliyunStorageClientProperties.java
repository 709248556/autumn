package com.autumn.file.storage.clients.aliyun;

import com.aliyun.oss.model.CannedAccessControlList;
import com.autumn.file.storage.properties.AbstractStorageClientProperties;
import com.autumn.file.storage.properties.AutumnStorageProperties;
import com.autumn.util.StringUtils;

/**
 * 阿里云存储属性
 *
 * @Description TODO
 * @Author 老码农
 * @Date 2019-09-03 3:52
 */
public class AliyunStorageClientProperties extends AbstractStorageClientProperties {
    private static final long serialVersionUID = -8875686980640182432L;

    /**
     * bean条件属性
     */
    public static final String BEAN_CONDITIONAL_PROPERTY = AutumnStorageProperties.PREFIX + ".aliyun.enable";

    /**
     * 通道 Bean 名称
     */
    public static final String CHANNEL_BEAN_NAME = CHANNEL_BEAN_PREFIX + "Aliyun" + CHANNEL_BEAN_SUFFIX;

    private String accessKeyId;
    private String accessKeySecret;
    private String cannedACL = "public-read"; // default、private、public-read、public-read-write

    public String getAccessKeyId() {
        return accessKeyId;
    }

    public void setAccessKeyId(String accessKeyId) {
        this.accessKeyId = accessKeyId;
    }

    public String getAccessKeySecret() {
        return accessKeySecret;
    }

    public void setAccessKeySecret(String accessKeySecret) {
        this.accessKeySecret = accessKeySecret;
    }

    /**
     * 获取acl权限
     *
     * @return default、private、public-read、public-read-write
     */
    public String getCannedACL() {
        return cannedACL;
    }

    /**
     * acl权限
     *
     * @param cannedACL
     *            default、private、public-read、public-read-write
     */
    public void setCannedACL(String cannedACL) {
        this.cannedACL = cannedACL;
    }

    /**
     *
     * @return
     */
    public CannedAccessControlList toCannedAccessControlList() {
        if (StringUtils.isNullOrBlank(this.getCannedACL())) {
            return CannedAccessControlList.PublicRead;
        }
        try {
            return CannedAccessControlList.parse(this.getCannedACL());
        } catch (Exception e) {
            return CannedAccessControlList.PublicRead;
        }
    }
}
