package com.autumn.zero.common.library.application.dto.common.dictionary;

import com.autumn.validation.DefaultDataValidation;
import com.autumn.zero.common.library.entities.common.DataDictionary;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;

/**
 * 字典类型输入
 *
 * @Description TODO
 * @Author 老码农
 * @Date 2019-07-25 18:36
 */
@ToString(callSuper = true)
@Getter
@Setter
public class DictionaryTypeInput extends DefaultDataValidation implements DataDictionary {

    private static final long serialVersionUID = 8539851814513371959L;

    /**
     * 字典类型
     */
    @ApiModelProperty(value = "字典类型类型")
    @NotNull(message = "字典类型不能为空")
    private Integer dictionaryType;


}
