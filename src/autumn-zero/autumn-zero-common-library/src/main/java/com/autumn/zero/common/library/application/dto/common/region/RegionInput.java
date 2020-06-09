package com.autumn.zero.common.library.application.dto.common.region;

import com.autumn.zero.common.library.application.dto.tree.input.TreeCodeInput;
import com.autumn.zero.common.library.entities.common.Region;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

/**
 * 行政区输入
 *
 * @Description TODO
 * @Author 老码农
 * @Date 2019-08-05 4:05
 */
@Getter
@Setter
public class RegionInput extends TreeCodeInput {
    private static final long serialVersionUID = -2501084597090271331L;

    /**
     * 拼音简写
     */
    @ApiModelProperty(value = "拼音简写(maxLength =" + Region.MAX_LENGTH_FIRST_PINYIN + "),为空则自动生成", required = false, dataType = "String")
    @Length(max = Region.MAX_LENGTH_FIRST_PINYIN, message = "拼音简写 不能超过 " + Region.MAX_LENGTH_FIRST_PINYIN + " 个字。")
    private String firstPinyin;

    /**
     * 完整拼音
     */
    @ApiModelProperty(value = "完整拼音(maxLength =" + Region.MAX_LENGTH_PINYIN + "),为空则自动生成", required = false, dataType = "String")
    @Length(max = Region.MAX_LENGTH_PINYIN, message = "完整拼音 不能超过 " + Region.MAX_LENGTH_PINYIN + " 个字。")
    private String pinyin;
}
