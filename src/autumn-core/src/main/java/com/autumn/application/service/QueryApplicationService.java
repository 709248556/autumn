package com.autumn.application.service;

import com.autumn.application.ApplicationDataCache;
import com.autumn.application.dto.input.AdvancedPageQueryInput;
import com.autumn.application.dto.input.AdvancedQueryInput;
import com.autumn.mybatis.mapper.PageResult;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.Serializable;
import java.util.List;

/**
 * 查询应用服务
 *
 * @param <TKey>           主键类型
 * @param <TOutputItem>    输出项目类型(列表项目)
 * @param <TOutputDetails> 输出详情类型(单条详情项目)
 * @author 老码农 2018-11-16 16:43:52
 */
public interface QueryApplicationService<TKey extends Serializable, TOutputItem, TOutputDetails>
        extends ApplicationService, ApplicationDataCache {

    /**
     * 查询列表
     *
     * @param input 输入
     * @return
     */
    List<TOutputItem> queryForList(AdvancedQueryInput input);

    /**
     * 分页查询
     *
     * @param input 输入
     * @return
     */
    PageResult<TOutputItem> queryForPage(AdvancedPageQueryInput input);

    /**
     * Excel 导出
     *
     * @param input 输入
     * @return
     */
    Workbook exportByExcel(AdvancedQueryInput input);

    /**
     * 根据主键查询
     *
     * @param id 主键
     * @return
     */
    TOutputDetails queryById(TKey id);

}
