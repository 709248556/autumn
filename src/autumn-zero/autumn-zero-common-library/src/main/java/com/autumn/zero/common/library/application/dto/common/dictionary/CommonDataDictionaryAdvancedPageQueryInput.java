package com.autumn.zero.common.library.application.dto.common.dictionary;

import com.autumn.application.dto.input.DefaultAdvancedPageQueryInput;
import com.autumn.zero.common.library.entities.common.DataDictionary;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;

/**
 * 字典高级分页查询
 *
 * @Description TODO
 * @Author 老码农
 * @Date 2019-07-25 18:48
 */
@ToString(callSuper = true)
@Getter
@Setter
public class CommonDataDictionaryAdvancedPageQueryInput extends DefaultAdvancedPageQueryInput implements DataDictionary {

    private static final long serialVersionUID = -2023466271935237038L;

    /**
     * 字典类型
     */
    @ApiModelProperty(value = "字典类型类型")
    @NotNull(message = "字典类型不能为空")
    private Integer dictionaryType;

}
