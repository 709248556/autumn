package com.autumn.util;

import java.security.CodeSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 版本
 *
 * @author 老码农
 * <p>
 * 2018-06-27 17:17:10
 */
public class Version {

    private static final Log logger = LogFactory.getLog(Version.class);

    /**
     * 版本
     */
    public static final String VERSION = getVersion(Version.class, "3.0");

    //private static final boolean INTERNAL = hasResource("com/alibaba/dubbo/registry/internal/RemoteRegistry.class");
    //private static final boolean COMPATIBLE = hasResource("com/taobao/remoting/impl/ConnectionRequest.class");

    private Version() {
    }

	/*public static boolean isInternalVersion() {
		return INTERNAL;
	}

	public static boolean isCompatibleVersion() {
		return COMPATIBLE;
	}*/

/*	private static boolean hasResource(String path) {
		try {
			return Version.class.getClassLoader().getResource(path) != null;
		} catch (Throwable t) {
			return false;
		}
	}*/

    /**
     * 获取版本
     *
     * @param cls            类型
     * @param defaultVersion 默认
     * @return
     */
    public static String getVersion(Class<?> cls, String defaultVersion) {
        try {
            // find version info from MANIFEST.MF first
            String version = cls.getPackage().getImplementationVersion();
            if (version == null || version.length() == 0) {
                version = cls.getPackage().getSpecificationVersion();
            }
            if (version == null || version.length() == 0) {
                // guess version fro jar file name if nothing's found from MANIFEST.MF
                CodeSource codeSource = cls.getProtectionDomain().getCodeSource();
                if (codeSource == null) {
                    logger.info("No codeSource for class " + cls.getName() + " when getVersion, use default version "
                            + defaultVersion);
                } else {
                    String file = codeSource.getLocation().getFile();
                    if (file != null && file.length() > 0 && file.endsWith(".jar")) {
                        file = file.substring(0, file.length() - 4);
                        int i = file.lastIndexOf('/');
                        if (i >= 0) {
                            file = file.substring(i + 1);
                        }
                        i = file.indexOf("-");
                        if (i >= 0) {
                            file = file.substring(i + 1);
                        }
                        while (file.length() > 0 && !Character.isDigit(file.charAt(0))) {
                            i = file.indexOf("-");
                            if (i >= 0) {
                                file = file.substring(i + 1);
                            } else {
                                break;
                            }
                        }
                        version = file;
                    }
                }
            }
            // return default version if no version info is found
            return version == null || version.length() == 0 ? defaultVersion : version;
        } catch (Throwable e) {
            // return default version when any exception is thrown
            logger.error("return default version, ignore exception " + e.getMessage(), e);
            return defaultVersion;
        }
    }

}
