package com.autumn.web.controllers;

import com.autumn.application.dto.DefaultEntityDto;
import com.autumn.application.dto.input.DefaultAdvancedPageQueryInput;
import com.autumn.application.service.EditApplicationService;
import com.autumn.mybatis.mapper.PageResult;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

/**
 * 具有编辑的控制器
 * <p>
 * </p>
 *
 * @param <TKey>           主键类型
 * @param <TAddInput>      添加输入类型
 * @param <TUpdateInput>   更新输入类型
 * @param <TOutputItem>    输出项目类型
 * @param <TOutputDetails> 输出详情类型
 * @param <TService>       服务
 * @description TODO
 * @author: 老码农
 * @create: 2020-03-31 11:44
 **/
public abstract class AbstractEditController<TAddInput, TUpdateInput, TOutputItem, TOutputDetails, TService extends EditApplicationService<Long, TAddInput, TUpdateInput, TOutputItem, TOutputDetails>> {

    /**
     * 查询服务
     */
    protected final TService service;

    /**
     * 实例化
     *
     * @param service 服务
     */
    public AbstractEditController(TService service) {
        this.service = service;
    }

    /**
     * 添加
     *
     * @param input
     * @return
     */
    @PostMapping("/add")
    @ApiOperation(value = "添加")
    public TOutputDetails add(@RequestBody TAddInput input) {
        return service.add(input);
    }

    /**
     * 修改
     *
     * @param input
     * @return
     */
    @PostMapping("/update")
    @ApiOperation(value = "修改")
    public TOutputDetails update(@RequestBody TUpdateInput input) {
        return service.update(input);
    }

    /**
     * 删除
     *
     * @param input
     * @return
     */
    @PostMapping("/delete")
    @ApiOperation(value = "删除")
    public void deleteById(@Valid @RequestBody DefaultEntityDto input) {
        service.deleteById(input.getId());
    }

    /**
     * 根据id查询单条数据
     *
     * @param input
     * @return
     */
    @PostMapping("/query/single")
    @ApiOperation(value = "根据id查询单条数据")
    public TOutputDetails queryById(@Valid @RequestBody DefaultEntityDto input) {
        return service.queryById(input.getId());
    }

    /**
     * 分页查询
     *
     * @param input
     * @return
     */
    @PostMapping("/query/page")
    @ApiOperation(value = "分页查询")
    public PageResult<TOutputItem> queryForPage(@Valid @RequestBody DefaultAdvancedPageQueryInput input) {
        return service.queryForPage(input);
    }

}
