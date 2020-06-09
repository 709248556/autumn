package com.autumn.zero.common.library.web.controllers.user.common;

import com.autumn.application.dto.DefaultEntityDto;
import com.autumn.domain.values.IntegerConstantItemValue;
import com.autumn.mybatis.mapper.DefaultPageResult;
import com.autumn.mybatis.mapper.PageResult;
import com.autumn.util.AutoMapUtils;
import com.autumn.zero.common.library.application.dto.common.dictionary.*;
import com.autumn.zero.common.library.application.services.common.CommonDataDictionaryAppService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;

/**
 * 用户端字典查询
 * <p>
 * </p>
 *
 * @description TODO
 * @author: 老码农
 * @create: 2020-03-28 16:13
 **/
@RestController
@RequestMapping("/common/dictionary")
@Api(tags = "数据字典查询")
public class UserDataDictionaryController {

    private final CommonDataDictionaryAppService service;

    /**
     * 实例化
     *
     * @param service
     */
    public UserDataDictionaryController(CommonDataDictionaryAppService service) {
        this.service = service;
    }

    /**
     * 字典类型集合
     *
     * @return
     */
    @PostMapping("/types")
    @ApiOperation(value = "字典类型集合")
    public Collection<IntegerConstantItemValue> dictionaryTypes() {
        return service.dictionaryTypeList();
    }

    /**
     * 根据id查询单条数据
     *
     * @param input
     * @return
     */
    @PostMapping("query/single")
    @ApiOperation(value = "根据id查询单条数据")
    public CommonDataDictionaryClientOutput queryById(@Valid @RequestBody DefaultEntityDto input) {
        return AutoMapUtils.map(service.queryById(input.getId()), CommonDataDictionaryClientOutput.class);
    }

    /**
     * 列表查询
     *
     * @param input
     * @return
     */
    @PostMapping("query/list")
    @ApiOperation(value = "列表查询")
    public List<CommonDataDictionaryClientOutput> queryForList(@RequestBody CommonDataDictionaryAdvancedQueryInput input) {
        return AutoMapUtils.mapForList(service.queryForList(input), CommonDataDictionaryClientOutput.class);
    }

    /**
     * 选择列表
     *
     * @param input
     * @return
     */
    @PostMapping("query/list/choose")
    @ApiOperation(value = "选择列表")
    public List<CommonDataDictionaryClientOutput> chooseForList(@RequestBody DictionaryTypeInput input) {
        return AutoMapUtils.mapForList(service.chooseForList(input.getDictionaryType()), CommonDataDictionaryClientOutput.class);
    }

    /**
     * 分页查询
     *
     * @param input
     * @return
     */
    @PostMapping("query/page")
    @ApiOperation(value = "分页查询")
    public PageResult<CommonDataDictionaryClientOutput> queryForPage(@RequestBody CommonDataDictionaryAdvancedPageQueryInput input) {
        PageResult<CommonDataDictionaryOutput> result = service.queryForPage(input);
        DefaultPageResult<CommonDataDictionaryClientOutput> pageResult = new DefaultPageResult(result.getCurrentPage(), result.getPageSize(), result.getRowTotal());
        pageResult.setItems(AutoMapUtils.mapForList(result.getItems(), CommonDataDictionaryClientOutput.class));
        return pageResult;
    }

}
