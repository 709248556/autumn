package com.autumn.cloud.uid.buffer;

import com.autumn.cloud.uid.UID;

import java.util.List;

/**
 * Buffered UID provider(Lambda supported), which provides UID in the same one second
 *
 * @author yutianbao
 */
@FunctionalInterface
public interface BufferedUidProvider {

    /**
     * Provides UID in one second
     *
     * @param momentInSecond
     * @return
     */
    List<UID> provide(long momentInSecond);
}
