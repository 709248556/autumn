package com.autumn.zero.ueditor.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.autumn.util.StringUtils;
import com.autumn.util.json.JsonUtils;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * 配置信息
 *
 * @Description TODO
 * @Author 老码农
 * @Date 2019-10-15 20:01
 */
@Getter
@Setter
public class UeditorConfig extends ExecuteResult {
    private static final long serialVersionUID = -2444582679854244883L;

    /**
     * 日志
     */
    private static final Log LOGGER = LogFactory.getLog(UeditorConfig.class);

    private static final String FIELD_NAME = "upfile";
    private static final String PATH_FORMAT = "/{yyyy}{mm}{dd}/{time}{rand:6}";

    /**
     * 默认
     */
    public static final UeditorConfig DEFAULT = new UeditorConfig();

    /**
     * @param extensions
     * @return
     */
    private Set<String> toLimitExtensions(List<String> extensions) {
        Set<String> limits;
        if (extensions != null) {
            limits = new HashSet<>(extensions.size());
            for (String extension : extensions) {
                if (extension != null) {
                    limits.add(extension.replace(".", "").trim().toLowerCase());
                }
            }
        } else {
            limits = new HashSet<>(16);
        }
        return limits;
    }

    /**
     * 上传图片配置项
     */
    @JSONField(ordinal = 1)
    private String imageActionName = UeditorActionType.ACTION_UPLOAD_IMAGE;
    @JSONField(ordinal = 2)
    private String imageFieldName = FIELD_NAME;
    @JSONField(ordinal = 3)
    private Integer imageMaxSize = 2048000;
    @JSONField(ordinal = 4)
    private boolean imageCompressEnable = true;
    @JSONField(ordinal = 5)
    private Integer imageCompressBorder = 1600;
    @JSONField(ordinal = 6)
    private String imageInsertAlign = "none";
    @JSONField(ordinal = 7)
    private String imageUrlPrefix = "";
    @JSONField(ordinal = 8)
    private String imageUploadUrlPrefix = UeditorActionMap.UPLOAD_URL_IMAGE_PREFIX;
    @JSONField(ordinal = 9)
    private String imagePathFormat = UeditorActionMap.UPLOAD_URL_IMAGE_PREFIX + PATH_FORMAT;
    @JSONField(ordinal = 99)
    private List<String> imageAllowFiles = Arrays.asList(".png", ".jpg", ".jpeg", ".gif", ".bmp");

    @JSONField(serialize = false, deserialize = false)
    private UeditorActionInfo imageActionInfo;

    /**
     * 创建图片访问信息
     *
     * @return
     */
    @JSONField(serialize = false, deserialize = false)
    public UeditorActionInfo getImageActionInfo() {
        if (this.imageActionInfo == null) {
            this.imageActionInfo = new UeditorActionInfo(this.getImageActionName(),
                    this.getImageFieldName(),
                    this.getImageMaxSize(),
                    this.toLimitExtensions(this.getImageAllowFiles()),
                    this.getImageUploadUrlPrefix());
        }
        return this.imageActionInfo;
    }

    /**
     * 涂鸦图片上传配置项
     */
    @JSONField(ordinal = 101)
    private String scrawlActionName = UeditorActionType.ACTION_UPLOAD_SCRAWL;
    @JSONField(ordinal = 102)
    private String scrawlFieldName = FIELD_NAME;
    @JSONField(ordinal = 104)
    private Integer scrawlMaxSize = 2048000;
    @JSONField(ordinal = 105)
    private String scrawlUrlPrefix = "";
    @JSONField(ordinal = 106)
    private String scrawlUploadUrlPrefix = UeditorActionMap.UPLOAD_URL_SCRAWL_PREFIX;
    @JSONField(ordinal = 107)
    private String scrawlPathFormat = UeditorActionMap.UPLOAD_URL_SCRAWL_PREFIX + PATH_FORMAT;
    @JSONField(ordinal = 108)
    private String scrawlInsertAlign = "none";

    @JSONField(serialize = false, deserialize = false)
    private UeditorActionInfo scrawActionInfo;

    /**
     * 涂鸦访问
     *
     * @return
     */
    @JSONField(serialize = false, deserialize = false)
    public UeditorActionInfo getScrawlActionInfo() {
        if (this.scrawActionInfo == null) {
            this.scrawActionInfo = new UeditorActionInfo(this.getScrawlActionName(),
                    this.getScrawlFieldName(),
                    this.getScrawlMaxSize(),
                    null,
                    this.getScrawlUploadUrlPrefix());
        }
        return this.scrawActionInfo;
    }

    /**
     * 截图工具上传
     */
    @JSONField(ordinal = 201)
    private String snapscreenActionName = UeditorActionType.ACTION_UPLOAD_IMAGE;
    @JSONField(ordinal = 203)
    private String snapscreenUrlPrefix = "";
    @JSONField(ordinal = 204)
    private String snapscreenUploadUrlPrefix = UeditorActionMap.UPLOAD_URL_IMAGE_PREFIX;
    @JSONField(ordinal = 205)
    private String snapscreenPathFormat = UeditorActionMap.UPLOAD_URL_IMAGE_PREFIX + PATH_FORMAT;
    @JSONField(ordinal = 206)
    private String snapscreenInsertAlign = "none";

    /**
     * 抓取远程图片配置
     */
    @JSONField(ordinal = 301)
    private List<String> catcherLocalDomain = Arrays.asList("127.0.0.1", "localhost", "img.baidu.com");
    @JSONField(ordinal = 302)
    private String catcherActionName = UeditorActionType.ACTION_CATCH_IMAGE;
    @JSONField(ordinal = 303)
    private String catcherFieldName = FIELD_NAME;
    @JSONField(ordinal = 305)
    private Integer catcherMaxSize = 2048000;
    @JSONField(ordinal = 306)
    private String catcherUrlPrefix = "";
    @JSONField(ordinal = 307)
    private String catcherUploadUrlPrefix = UeditorActionMap.UPLOAD_URL_CATCH_IMAGE_PREFIX;
    @JSONField(ordinal = 308)
    private String catcherPathFormat = UeditorActionMap.UPLOAD_URL_CATCH_IMAGE_PREFIX + PATH_FORMAT;
    @JSONField(ordinal = 399)
    private List<String> catcherAllowFiles = Arrays.asList(".png", ".jpg", ".jpeg", ".gif", ".bmp");

    @JSONField(serialize = false, deserialize = false)
    private UeditorActionInfo catcherActionInfo;

    /**
     * 抓取远程图片
     *
     * @return
     */
    @JSONField(serialize = false, deserialize = false)
    public UeditorActionInfo getCatcherActionInfo() {
        if (this.catcherActionInfo == null) {
            this.catcherActionInfo = new UeditorActionInfo(this.getCatcherActionName(),
                    this.getCatcherFieldName(),
                    this.getCatcherMaxSize(),
                    this.toLimitExtensions(this.getCatcherAllowFiles()),
                    this.getCatcherUploadUrlPrefix());
        }
        return this.catcherActionInfo;
    }

    /**
     * 上传视频配置
     */
    @JSONField(ordinal = 401)
    private String videoActionName = UeditorActionType.ACTION_UPLOAD_VIDEO;
    @JSONField(ordinal = 402)
    private String videoFieldName = FIELD_NAME;
    @JSONField(ordinal = 404)
    private Integer videoMaxSize = 102400000;
    @JSONField(ordinal = 405)
    private String videoUrlPrefix = "";
    @JSONField(ordinal = 406)
    private String videoUploadUrlPrefix = UeditorActionMap.UPLOAD_URL_VIDEO_PREFIX;
    @JSONField(ordinal = 407)
    private String videoPathFormat = UeditorActionMap.UPLOAD_URL_VIDEO_PREFIX + PATH_FORMAT;
    @JSONField(ordinal = 499)
    private List<String> videoAllowFiles = Arrays.asList(".flv", ".swf", ".mkv", ".avi", ".rm", ".rmvb", ".mpeg", ".mpg",
            ".ogg", ".ogv", ".mov", ".wmv", ".mp4", ".webm", ".mp3", ".wav", ".mid");

    @JSONField(serialize = false, deserialize = false)
    private UeditorActionInfo videoActionInfo;

    /**
     * 视频
     *
     * @return
     */
    @JSONField(serialize = false, deserialize = false)
    public UeditorActionInfo getVideoActionInfo() {
        if (this.videoActionInfo == null) {
            this.videoActionInfo = new UeditorActionInfo(this.getVideoActionName(),
                    this.getVideoFieldName(),
                    this.getVideoMaxSize(),
                    this.toLimitExtensions(this.getVideoAllowFiles()),
                    this.getVideoUploadUrlPrefix());
        }
        return this.videoActionInfo;
    }

    /**
     * 上传文件配置
     */
    @JSONField(ordinal = 501)
    private String fileActionName = UeditorActionType.ACTION_UPLOAD_FILE;
    @JSONField(ordinal = 502)
    private String fileFieldName = FIELD_NAME;
    @JSONField(ordinal = 504)
    private Integer fileMaxSize = 51200000;
    @JSONField(ordinal = 505)
    private String fileUrlPrefix = "";
    @JSONField(ordinal = 506)
    private String fileUploadUrlPrefix = UeditorActionMap.UPLOAD_URL_FILE_PREFIX;
    @JSONField(ordinal = 507)
    private String filePathFormat = UeditorActionMap.UPLOAD_URL_FILE_PREFIX + PATH_FORMAT;
    @JSONField(ordinal = 599)
    private List<String> fileAllowFiles = Arrays.asList(".png", ".jpg", ".jpeg", ".gif", ".bmp",
            ".flv", ".swf", ".mkv", ".avi", ".rm", ".rmvb", ".mpeg", ".mpg",
            ".ogg", ".ogv", ".mov", ".wmv", ".mp4", ".webm", ".mp3", ".wav", ".mid",
            ".rar", ".zip", ".tar", ".gz", ".7z", ".bz2", ".cab", ".iso",
            ".doc", ".docx", ".xls", ".xlsx", ".ppt", ".pptx", ".pdf", ".txt", ".md", ".xml");
    @JSONField(serialize = false, deserialize = false)
    private UeditorActionInfo fileActionInfo;

    /**
     * 文件
     *
     * @return
     */
    @JSONField(serialize = false, deserialize = false)
    public UeditorActionInfo getFileActionInfo() {
        if (this.fileActionInfo == null) {
            this.fileActionInfo = new UeditorActionInfo(this.getFileActionName(),
                    this.getFileFieldName(),
                    this.getFileMaxSize(),
                    this.toLimitExtensions(this.getFileAllowFiles()),
                    this.getFileUploadUrlPrefix());
        }
        return this.fileActionInfo;
    }

    /**
     * 执行图片管理的action名称
     */
    @JSONField(ordinal = 601)
    private String imageManagerActionName = UeditorActionType.ACTION_LIST_IMAGE;
    @JSONField(ordinal = 602)
    private String imageManagerListPath = UeditorActionMap.UPLOAD_URL_IMAGE_PREFIX + "/";
    @JSONField(ordinal = 603)
    private Integer imageManagerListSize = 20;
    @JSONField(ordinal = 604)
    private String imageManagerUrlPrefix = "";
    @JSONField(ordinal = 605)
    private String imageManagerInsertAlign = "none";
    @JSONField(ordinal = 699)
    private List<String> imageManagerAllowFiles = Arrays.asList(".png", ".jpg", ".jpeg", ".gif", ".bmp");

    /**
     * 列出指定目录下的文件
     */
    @JSONField(ordinal = 701)
    private String fileManagerActionName = UeditorActionType.ACTION_LIST_FILE;
    @JSONField(ordinal = 702)
    private String fileManagerListPath = UeditorActionMap.UPLOAD_URL_PREFIX + "/";
    @JSONField(ordinal = 703)
    private String fileManagerUrlPrefix = "";
    @JSONField(ordinal = 704)
    private Integer fileManagerListSize = 20;
    @JSONField(ordinal = 799)
    private List<String> fileManagerAllowFiles = new ArrayList<>(16);


    /**
     * 初始值
     */
    public void initialize() {
        this.setImageActionName(UeditorActionType.ACTION_UPLOAD_IMAGE);
        if (StringUtils.isNullOrBlank(this.getImageFieldName())) {
            this.setImageFieldName(FIELD_NAME);
        }
        if (StringUtils.isNullOrBlank(this.getImageUploadUrlPrefix())) {
            this.setImageUploadUrlPrefix(UeditorActionMap.UPLOAD_URL_IMAGE_PREFIX);
        }

        this.setScrawlActionName(UeditorActionType.ACTION_UPLOAD_SCRAWL);
        if (StringUtils.isNullOrBlank(this.getScrawlFieldName())) {
            this.setScrawlFieldName(FIELD_NAME);
        }
        if (StringUtils.isNullOrBlank(this.getScrawlUploadUrlPrefix())) {
            this.setScrawlUploadUrlPrefix(UeditorActionMap.UPLOAD_URL_SCRAWL_PREFIX);
        }

        this.setSnapscreenActionName(UeditorActionType.ACTION_UPLOAD_IMAGE);
        if (StringUtils.isNullOrBlank(this.getSnapscreenUploadUrlPrefix())) {
            this.setSnapscreenUploadUrlPrefix(UeditorActionMap.UPLOAD_URL_SCRAWL_PREFIX);
        }

        this.setCatcherActionName(UeditorActionType.ACTION_CATCH_IMAGE);
        if (StringUtils.isNullOrBlank(this.getCatcherFieldName())) {
            this.setCatcherFieldName(FIELD_NAME);
        }
        if (StringUtils.isNullOrBlank(this.getCatcherUploadUrlPrefix())) {
            this.setCatcherUploadUrlPrefix(UeditorActionMap.UPLOAD_URL_CATCH_IMAGE_PREFIX);
        }

        this.setVideoActionName(UeditorActionType.ACTION_UPLOAD_VIDEO);
        if (StringUtils.isNullOrBlank(this.getVideoFieldName())) {
            this.setVideoFieldName(FIELD_NAME);
        }
        if (StringUtils.isNullOrBlank(this.getVideoUploadUrlPrefix())) {
            this.setVideoUploadUrlPrefix(UeditorActionMap.UPLOAD_URL_VIDEO_PREFIX);
        }

        this.setFileActionName(UeditorActionType.ACTION_UPLOAD_FILE);
        if (StringUtils.isNullOrBlank(this.getFileFieldName())) {
            this.setFileFieldName(FIELD_NAME);
        }
        if (StringUtils.isNullOrBlank(this.getFileUploadUrlPrefix())) {
            this.setFileUploadUrlPrefix(UeditorActionMap.UPLOAD_URL_FILE_PREFIX);
        }

        this.setImageManagerActionName(UeditorActionType.ACTION_LIST_IMAGE);

        this.setFileManagerActionName(UeditorActionType.ACTION_LIST_FILE);
    }

    /**
     * 读取
     *
     * @param path
     * @return
     */
    public static UeditorConfig readConfig(String path) {
        if (StringUtils.isNullOrBlank(path)) {
            LOGGER.error("读取Html Ueditor 配置出错，文件路径为空。");
            return null;
        }
        InputStreamReader reader = null;
        BufferedReader bfReader = null;
        try {
            File file = new File(path);
            if (!file.isAbsolute()) {
                file = new File(file.getAbsolutePath());
            }
            if (!(file.exists() && file.isFile())) {
                LOGGER.error("读取Html Ueditor 配置出错，文件路径[" + file + "]不存在。");
                return null;
            }
            StringBuilder builder = new StringBuilder();
            reader = new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8);
            bfReader = new BufferedReader(reader);
            String tmpContent;
            while ((tmpContent = bfReader.readLine()) != null) {
                builder.append(tmpContent);
            }
            //去掉注释
            String json = builder.toString().replaceAll("/\\*[\\s\\S]*?\\*/", "");
            if (StringUtils.isNullOrBlank(json)) {
                return null;
            }
            UeditorConfig ueditorConfig = JsonUtils.parseObject(json, UeditorConfig.class);
            ueditorConfig.initialize();
            return ueditorConfig;
        } catch (Exception e) {
            LOGGER.error("读取 Html Ueditor 配置出错：" + e.getMessage(), e);
            return null;
        } finally {
            IOUtils.closeQuietly(reader);
            IOUtils.closeQuietly(bfReader);
        }
    }

}
