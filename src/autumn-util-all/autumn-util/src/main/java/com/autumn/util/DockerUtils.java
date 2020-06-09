package com.autumn.util;

import com.autumn.exception.SystemException;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Docker 工具
 *
 * @Description TODO
 * @Author 老码农
 * @Date 2019-08-25 18:18
 */
public abstract class DockerUtils {
    /**
     * 日志
     */
    private static final Log LOGGER = LogFactory.getLog(DockerUtils.class);

    /**
     * Environment param keys
     */
    private static final String ENV_KEY_HOST = "JPAAS_HOST";
    private static final String ENV_KEY_PORT = "JPAAS_HTTP_PORT";
    private static final String ENV_KEY_PORT_ORIGINAL = "JPAAS_HOST_PORT_8080";

    /**
     * Docker host & port
     */
    private static String DOCKER_HOST = "";
    private static String DOCKER_PORT = "";

    /**
     * Whether is docker
     */
    private static boolean IS_DOCKER;

    static {
        retrieveFromEnv();
    }

    /**
     * 获取 Docker 主机
     *
     * @return empty string if not a docker
     */
    public static String getDockerHost() {
        return DOCKER_HOST;
    }

    /**
     * 获取 Docker 端口
     *
     * @return empty string if not a docker
     */
    public static String getDockerPort() {
        return DOCKER_PORT;
    }

    /**
     * 是否为 Docker 容器
     *
     * @return
     */
    public static boolean isDocker() {
        return IS_DOCKER;
    }

    /**
     * 初始化环境
     */
    private static void retrieveFromEnv() {
        // retrieve host & port from environment
        DOCKER_HOST = System.getenv(ENV_KEY_HOST);
        DOCKER_PORT = System.getenv(ENV_KEY_PORT);

        // not found from 'JPAAS_HTTP_PORT', then try to find from 'JPAAS_HOST_PORT_8080'
        if (StringUtils.isBlank(DOCKER_PORT)) {
            DOCKER_PORT = System.getenv(ENV_KEY_PORT_ORIGINAL);
        }

        boolean hasEnvHost = StringUtils.isNotBlank(DOCKER_HOST);
        boolean hasEnvPort = StringUtils.isNotBlank(DOCKER_PORT);

        // docker can find both host & port from environment
        if (hasEnvHost && hasEnvPort) {
            IS_DOCKER = true;

            // found nothing means not a docker, maybe an actual machine
        } else if (!hasEnvHost && !hasEnvPort) {
            IS_DOCKER = false;

        } else {
            String msg = String.format("Missing host or port from env for Docker. host:%s, port:%s", DOCKER_HOST, DOCKER_PORT);
            LOGGER.error(msg);
            throw new SystemException(msg);
        }
    }
}
