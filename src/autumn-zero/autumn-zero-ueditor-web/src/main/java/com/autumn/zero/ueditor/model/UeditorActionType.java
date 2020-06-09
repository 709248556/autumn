package com.autumn.zero.ueditor.model;

import com.autumn.util.ValuedEnum;

/**
 * 动作类型
 * <p>
 * </p>
 *
 * @description TODO
 * @author: 老码农
 * @create: 2020-03-24 18:35
 **/
public enum UeditorActionType implements ValuedEnum<Integer> {

    /**
     * 配置
     */
    CONFIG(UeditorActionType.ACTION_CONFIG, UeditorActionType.CODE_CONFIG),
    /**
     * 上传图片
     */
    UPLOAD_IMAGE(UeditorActionType.ACTION_UPLOAD_IMAGE, UeditorActionType.CODE_UPLOAD_IMAGE),
    /**
     * 上传涂鸦
     */
    UPLOAD_SCRAWL(UeditorActionType.ACTION_UPLOAD_SCRAWL, UeditorActionType.CODE_UPLOAD_SCRAWL),
    /**
     * 上传视频
     */
    UPLOAD_VIDEO(UeditorActionType.ACTION_UPLOAD_VIDEO, UeditorActionType.CODE_UPLOAD_VIDEO),
    /**
     * 上传文件
     */
    UPLOAD_FILE(UeditorActionType.ACTION_UPLOAD_FILE, UeditorActionType.CODE_UPLOAD_FILE),
    /**
     * 抓取远程图片
     */
    CATCH_IMAGE(UeditorActionType.ACTION_CATCH_IMAGE, UeditorActionType.CODE_CATCH_IMAGE),
    /**
     * 请求文件列表
     */
    LIST_FILE(UeditorActionType.ACTION_LIST_FILE, UeditorActionType.CODE_LIST_FILE),
    /**
     * 请求图片列表
     */
    LIST_IMAGE(UeditorActionType.ACTION_LIST_IMAGE, UeditorActionType.CODE_LIST_IMAGE);

    /**
     * 配置
     */
    public static final int CODE_CONFIG = 0;
    /**
     * 上传图片
     */
    public static final int CODE_UPLOAD_IMAGE = 1;
    /**
     * 上传涂鸦
     */
    public static final int CODE_UPLOAD_SCRAWL = 2;
    /**
     * 上传视频
     */
    public static final int CODE_UPLOAD_VIDEO = 3;
    /**
     * 上传文件
     */
    public static final int CODE_UPLOAD_FILE = 4;
    /**
     * 抓取远程图片
     */
    public static final int CODE_CATCH_IMAGE = 5;
    /**
     * 请求文件列表
     */
    public static final int CODE_LIST_FILE = 6;
    /**
     * 请求图片列表
     */
    public static final int CODE_LIST_IMAGE = 7;

    /**
     * 配置
     */
    public static final String ACTION_CONFIG = "config";
    /**
     * 上传图片
     */
    public static final String ACTION_UPLOAD_IMAGE = "uploadimage";
    /**
     * 上传涂鸦
     */
    public static final String ACTION_UPLOAD_SCRAWL = "uploadscrawl";
    /**
     * 上传视频
     */
    public static final String ACTION_UPLOAD_VIDEO = "uploadvideo";
    /**
     * 上传文件
     */
    public static final String ACTION_UPLOAD_FILE = "uploadfile";
    /**
     * 抓取远程图片
     */
    public static final String ACTION_CATCH_IMAGE = "catchimage";
    /**
     * 请求文件列表
     */
    public static final String ACTION_LIST_FILE = "listfile";
    /**
     * 请求图片列表
     */
    public static final String ACTION_LIST_IMAGE = "listimage";


    private final String name;
    private final int value;

    /**
     * 实例化
     *
     * @param name  名称
     * @param value 值
     */
    private UeditorActionType(String name, int value) {
        this.name = name;
        this.value = value;
    }

    /**
     * 获取名称
     *
     * @return
     */
    public String getName() {
        return this.name;
    }

    @Override
    public Integer value() {
        return this.value;
    }
}
