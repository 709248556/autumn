package com.autumn.util;

import com.autumn.exception.ExceptionUtils;
import com.autumn.util.json.JsonUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.util.ClassUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * 资源帮助
 *
 * @author 老码农 2019-04-21 17:45:22
 */
public class ResourceUtils {

    /**
     * 日志
     */
    private static final Log logger = LogFactory.getLog(ResourceUtils.class);

    /**
     * 获取资源的根路径
     *
     * @return
     */
    public static String getResourceRootPath() {
        try {
            Resource[] resources = new PathMatchingResourcePatternResolver()
                    .getResources(ResourceLoader.CLASSPATH_URL_PREFIX);
            for (Resource resource : resources) {
                if (resource.exists()) {
                    String rootPath = resource.getFile().getAbsolutePath().replace("\\", "/");
                    File dir = new File(rootPath);
                    if (!dir.exists() || !dir.isDirectory()) {
                        dir.mkdirs();
                    }
                    return rootPath;
                }
            }
            return "";
        } catch (IOException e) {
            return "";
        }
    }

    /**
     * 将类名称转换为瓷源路径
     *
     * @param className
     * @return
     */
    public static String convertClassNameToResourcePath(String className) {
        return ClassUtils.convertClassNameToResourcePath(className);
    }

    /**
     * 读取资源列表
     *
     * @param resPath  资源路径
     * @param resClass 资源类型
     * @return
     * @throws IOException
     */
    public static <TRes> List<TRes> readResList(String resPath, Class<TRes> resClass) {
        String json = readResString(resPath);
        if (StringUtils.isNullOrBlank(json)) {
            return new ArrayList<>();
        }
        return JsonUtils.parseList(json, resClass);
    }

    /**
     * 读取资源对象
     *
     * @param resPath  资源路径
     * @param resClass 资源类型
     * @return
     * @throws IOException
     */
    public static <TRes> TRes readResObject(String resPath, Class<TRes> resClass) {
        String json = readResString(resPath);
        return JsonUtils.parseObject(json, resClass);
    }

    /**
     * 读取资源字符
     *
     * @param resPath 资源路径
     * @return
     */
    public static String readResString(String resPath) {
        String json = "";
        InputStream input = null;
        try {
            input = ClassLoader.getSystemResourceAsStream(resPath);
            if (input != null) {
                json = IOUtils.toString(input, StandardCharsets.UTF_8);
            } else {
                logger.error("读取资源[" + resPath + "]的结果:null");
            }
        } catch (IOException e) {
            logger.error("读取资源[" + resPath + "]出错:\r\n" + e.getMessage());
            throw ExceptionUtils.throwSystemException(e.getMessage(), e);
        } finally {
            IOUtils.closeQuietly(input);
        }
        return json;
    }

}
