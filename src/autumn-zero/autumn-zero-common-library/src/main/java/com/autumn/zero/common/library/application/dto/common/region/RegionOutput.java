package com.autumn.zero.common.library.application.dto.common.region;

import com.autumn.util.StringUtils;
import com.autumn.util.excel.annotation.ExcelColumn;
import com.autumn.zero.common.library.application.dto.tree.output.TreeCodeOutput;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Locale;

/**
 * 行政区输出
 *
 * @Description TODO
 * @Author 老码农
 * @Date 2019-08-05 4:06
 */
@Getter
@Setter
public class RegionOutput extends TreeCodeOutput {
    private static final long serialVersionUID = 8752576878098406777L;

    /**
     * 拼音简写
     */
    @ApiModelProperty(value = "拼音简写", required = false, dataType = "String")
    @ExcelColumn(order = 8, friendlyName = "拼音简写", width = 80)
    private String firstPinyin;

    /**
     * 获取首个拼音字母
     *
     * @return
     */
    @ApiModelProperty(value = "首个拼音字母", required = false, dataType = "String")
    public String getFirstPinyinFetter() {
        if (StringUtils.isNullOrBlank(this.getFirstPinyin())) {
            return "";
        }
        return this.getFirstPinyin().substring(0, 1).toUpperCase(Locale.ENGLISH);
    }

    /**
     * 完整拼音
     */
    @ApiModelProperty(value = "完整拼音", required = false, dataType = "String")
    @ExcelColumn(order = 9, friendlyName = "完整拼音", width = 80)
    private String pinyin;
}
