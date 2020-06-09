package com.autumn.zero.common.library.application.dto.tree.output;

import com.autumn.application.dto.EntityDto;
import com.autumn.util.StringUtils;
import com.autumn.util.excel.annotation.ExcelColumn;
import com.autumn.zero.common.library.constants.CommonStatusConstant;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Locale;

/**
 * 行政区客户端输出
 * <p>
 * </p>
 *
 * @description TODO
 * @author: 老码农
 * @create: 2020-03-28 15:15
 **/
@ToString(callSuper = true)
@Getter
@Setter
public class TreeClientOutput extends EntityDto<Long> {

    private static final long serialVersionUID = -3175432685528343347L;

    /**
     * 名称
     */
    @ApiModelProperty(value = "名称")
    @ExcelColumn(order = 1, friendlyName = "名称", width = 120)
    private String name;

    /**
     * 代码
     */
    @ApiModelProperty(value = "代码", required = true, dataType = "String")
    @ExcelColumn(order = 2, friendlyName = "代码", width = 80)
    private String code;

    /**
     * 顺序
     */
    @ApiModelProperty(value = "顺序")
    @ExcelColumn(order = 3, friendlyName = "顺序", width = 60)
    private Integer sortId;

    /**
     * 父级id
     */
    @ApiModelProperty(value = "父级id", required = false, dataType = "Long")
    private Long parentId;

    /**
     * 级别
     */
    @ApiModelProperty(value = "级别", required = false, dataType = "Integer")
    @ExcelColumn(order = 4, friendlyName = "级别", width = 80)
    private Integer level;

    /**
     * 拼音简写
     */
    @ApiModelProperty(value = "拼音简写", required = false, dataType = "String")
    @ExcelColumn(order = 5, friendlyName = "拼音简写", width = 80)
    private String firstPinyin;

    /**
     * 获取首个拼音字母
     *
     * @return
     */
    @ApiModelProperty(value = "首个拼音字母", required = false, dataType = "String")
    @ExcelColumn(order = 6, friendlyName = "拼音简写", width = 80)
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
    @ExcelColumn(order = 7, friendlyName = "完整拼音", width = 80)
    private String pinyin;

    /**
     * 子级数量
     */
    @ApiModelProperty(value = "子级数量", required = false, dataType = "Integer")
    @ExcelColumn(order = 8, friendlyName = "子级数量", width = 80)
    private Integer childrenCount;

    /**
     * 完整id
     */
    @ApiModelProperty(value = "完整id", required = false, dataType = "String")
    @ExcelColumn(order = 9, friendlyName = "拼音简写", width = 80)
    private String fullId;

    /**
     * 完整代码
     */
    @ApiModelProperty(value = "完整代码", required = false, dataType = "String")
    @ExcelColumn(order = 10, friendlyName = "完整代码", width = 300)
    private String fullCode;

    /**
     * 完整名称
     */
    @ApiModelProperty(value = "完整名称", required = false, dataType = "String")
    @ExcelColumn(order = 11, friendlyName = "完整名称", width = 300)
    private String fullName;

    /**
     * 状态
     */
    @ApiModelProperty(value = "状态")
    private Integer status;

    /**
     * 状态名称
     *
     * @return
     */
    @ExcelColumn(order = 50, friendlyName = "状态名称", width = 300)
    @ApiModelProperty(value = "状态名称")
    public String getStatusName() {
        return CommonStatusConstant.getName(this.getStatus());
    }

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    @ExcelColumn(order = 500, friendlyName = "备注", width = 300)
    private String remarks;

}
