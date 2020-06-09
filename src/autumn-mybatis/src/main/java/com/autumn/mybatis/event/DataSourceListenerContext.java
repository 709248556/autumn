package com.autumn.mybatis.event;

import com.autumn.mybatis.factory.DynamicDataSourceRouting;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * 数据源工厂上下文
 * <p>
 * </p>
 *
 * @description TODO
 * @author: 老码农
 * @create: 2020-04-05 13:20
 **/
public class DataSourceListenerContext {

    private static Collection<DataSourceListener> LISTENERS = Collections.synchronizedCollection(new ArrayList<>(16));

    /**
     * 注册事件
     *
     * @param listener
     */
    public static void registerEvent(DataSourceListener listener) {
        LISTENERS.add(listener);
    }

    private static void callListeners(Consumer<DataSourceListener> consumer, String name) {
        if (LISTENERS.size() > 0) {
            Collection<DataSourceListener> items = LISTENERS.stream()
                    .sorted((s, t) -> Integer.compare(s.getOrder(), s.getOrder())).collect(Collectors.toList());
            for (DataSourceListener listener : items) {
                try {
                    consumer.accept(listener);
                } catch (Exception err) {
                    Log log = LogFactory.getLog(listener.getClass());
                    log.error("调用数据源监听方法[" + listener.getClass() + "]的 [" + name + "]出错:" + err.getMessage());
                    throw err;
                }
            }
        }
    }

    /**
     * 数据源初始化事件
     *
     * @param dataSource        数据源
     * @param dataSourceRouting 数据源路油
     */
    public static void initEvent(DataSource dataSource, DynamicDataSourceRouting dataSourceRouting) {
        callListeners(c -> c.init(dataSource, dataSourceRouting), "init");
    }

    /**
     * 数据源关闭事件
     *
     * @param dataSource        数据源
     * @param dataSourceRouting 数据源路油
     */
    public static void closeEvent(DataSource dataSource, DynamicDataSourceRouting dataSourceRouting) {
        callListeners(c -> c.close(dataSource, dataSourceRouting), "close");
    }
}
