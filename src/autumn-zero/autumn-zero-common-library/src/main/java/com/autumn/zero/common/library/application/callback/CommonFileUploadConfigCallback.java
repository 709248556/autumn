package com.autumn.zero.common.library.application.callback;

/**
 * 公共文件上传配置回调
 *
 * @Description TODO
 * @Author 老码农
 * @Date 2019-08-04 1:43
 */
public interface CommonFileUploadConfigCallback extends FileUploadCallback {

    /**
     * 获取配置类型
     *
     * @return
     */
    String getConfigType();
}
