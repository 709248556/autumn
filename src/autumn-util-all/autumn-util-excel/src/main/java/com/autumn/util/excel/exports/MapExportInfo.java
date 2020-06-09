package com.autumn.util.excel.exports;

import java.util.Map;

/**
 * 字典导出信息
 */
public class MapExportInfo extends AbstractExportInfo<Map<String, Object>> {

    /**
     * 读取
     *
     * @param item 项目
     * @param name 名称
     */
    @Override
    public Object read(Map<String, Object> item, String name) {
        if (item == null || name == null) {
            return null;
        }
        return item.get(name);
    }

    /**
     * 写入
     *
     * @param item 项目
     * @param name 名称
     */
    @Override
    public void write(Map<String, Object> item, String name, Object value) {
        if (item == null || name == null) {
            return;
        }
        item.put(name, value);
    }

}
