package com.autumn.zero.ueditor.model;


import java.util.HashMap;
import java.util.Map;

/**
 * TODO
 *
 * @Description TODO
 * @Author 老码农
 * @Date 2019-10-16 7:56
 */
public class UeditorActionMap {

    private static final Map<String, UeditorActionType> ACTION_MAP = new HashMap<>(16);

    static {
        for (UeditorActionType actionType : UeditorActionType.values()) {
            ACTION_MAP.put(actionType.getName().toLowerCase(), actionType);
        }
    }

    public static final String UPLOAD_URL_PREFIX = "/html/ueditor";
    public static final String UPLOAD_URL_IMAGE_PREFIX = UPLOAD_URL_PREFIX + "/image";
    public static final String UPLOAD_URL_SCRAWL_PREFIX = UPLOAD_URL_IMAGE_PREFIX;
    public static final String UPLOAD_URL_VIDEO_PREFIX = UPLOAD_URL_PREFIX + "/video";
    public static final String UPLOAD_URL_FILE_PREFIX = UPLOAD_URL_PREFIX + "/file";
    public static final String UPLOAD_URL_CATCH_IMAGE_PREFIX = UPLOAD_URL_IMAGE_PREFIX;


    /**
     * 获取动作类型
     *
     * @param key
     * @return
     */
    public static UeditorActionType getActionType(String key) {
        return ACTION_MAP.get(key.trim().toLowerCase());
    }
}

