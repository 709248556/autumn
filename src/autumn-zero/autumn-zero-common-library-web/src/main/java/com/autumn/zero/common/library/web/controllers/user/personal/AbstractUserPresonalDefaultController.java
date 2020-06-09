package com.autumn.zero.common.library.web.controllers.user.personal;

import com.autumn.application.dto.DefaultEntityDto;
import com.autumn.application.dto.input.DefaultAdvancedQueryInput;
import com.autumn.web.controllers.AbstractEditController;
import com.autumn.zero.common.library.application.services.personal.UserPresonalDefaultService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.List;

/**
 * 个人具有默认数据抽象
 * <p>
 * </p>
 *
 * @description TODO
 * @author: 老码农
 * @create: 2020-03-31 11:41
 **/
public abstract class AbstractUserPresonalDefaultController<TAddInput, TUpdateInput, TOutputItem, TOutputDetails,
        TService extends UserPresonalDefaultService<TAddInput, TUpdateInput, TOutputItem, TOutputDetails>>
        extends AbstractEditController<TAddInput, TUpdateInput, TOutputItem, TOutputDetails, TService> {

    /**
     * 实例化
     *
     * @param service 服务
     */
    public AbstractUserPresonalDefaultController(TService service) {
        super(service);
    }

    /**
     * 默认更新
     *
     * @param input
     * @return
     */
    @PostMapping("/update/default")
    @ApiOperation(value = "默认更新")
    public void defaultUpdate(@Valid @RequestBody DefaultEntityDto input) {
        service.defaultUpdateById(input.getId());
    }

    /**
     * 查询默认发票
     *
     * @param input
     * @return
     */
    @PostMapping("/query/default")
    @ApiOperation(value = "查询默认发票")
    public TOutputDetails queryDefault() {
        return service.queryDefault();
    }

    /**
     * 列表查询
     *
     * @param input
     * @return
     */
    @PostMapping("/query/list")
    @ApiOperation(value = "列表查询")
    public List<TOutputItem> queryForList(@Valid @RequestBody DefaultAdvancedQueryInput input) {
        return service.queryForList(input);
    }

}
