package com.autumn.zero.common.library.web.controllers.user.common;

import com.autumn.application.dto.DefaultEntityDto;
import com.autumn.application.dto.input.DefaultAdvancedPageQueryInput;
import com.autumn.mybatis.mapper.DefaultPageResult;
import com.autumn.mybatis.mapper.PageResult;
import com.autumn.util.AutoMapUtils;
import com.autumn.zero.common.library.application.dto.common.region.RegionOutput;
import com.autumn.zero.common.library.application.dto.tree.input.DefaultChildrenPinyinSortQueryInput;
import com.autumn.zero.common.library.application.dto.tree.output.TreeClientOutput;
import com.autumn.zero.common.library.application.services.common.RegionAppService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * 用户端
 * <p>
 * </p>
 *
 * @description TODO
 * @author: 老码农
 * @create: 2020-03-28 14:51
 **/
@RestController
@RequestMapping("/common/region")
@Api(tags = "行政区查询")
public class UserRegionController {

    private final RegionAppService service;

    /**
     * 实例化
     *
     * @param service
     */
    public UserRegionController(RegionAppService service) {
        this.service = service;
    }

    /**
     * 根据id查询单条数据
     *
     * @param input
     * @return
     */
    @PostMapping("query/single")
    @ApiOperation(value = "根据id查询单条数据")
    public TreeClientOutput queryById(@Valid @RequestBody DefaultEntityDto input) {
        return AutoMapUtils.map(service.queryById(input.getId()), TreeClientOutput.class);
    }

    /**
     * 查询子级
     *
     * @param input
     * @return
     */
    @PostMapping("/query/children")
    @ApiOperation(value = "查询子级")
    public List<TreeClientOutput> queryChildren(@Valid @RequestBody DefaultChildrenPinyinSortQueryInput input) {
        return AutoMapUtils.mapForList(service.queryChildren(input), TreeClientOutput.class);
    }

    /**
     * 分页查询
     *
     * @param input
     * @return
     */
    @PostMapping("query/page")
    @ApiOperation(value = "分页查询")
    public PageResult<TreeClientOutput> queryForPage(@RequestBody DefaultAdvancedPageQueryInput input) {
        PageResult<RegionOutput> result = service.queryForPage(input);
        DefaultPageResult<TreeClientOutput> pageResult = new DefaultPageResult(result.getCurrentPage(), result.getPageSize(), result.getRowTotal());
        pageResult.setItems(AutoMapUtils.mapForList(result.getItems(), TreeClientOutput.class));
        return pageResult;
    }

}
