package com.autumn.zero.common.library.application.dto.common.dictionary;

import com.autumn.exception.ExceptionUtils;
import com.autumn.zero.common.library.application.dto.input.CommonNameSortInput;
import com.autumn.zero.common.library.constants.CommonStatusConstant;
import com.autumn.zero.common.library.entities.common.DataDictionary;
import com.autumn.zero.file.storage.application.dto.FileUploadIdInput;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 公共字典输入
 *
 * @Description TODO
 * @Author 老码农
 * @Date 2019-07-25 18:17
 */
@ToString(callSuper = true)
@Getter
@Setter
public class CommonDataDictionaryInput extends CommonNameSortInput implements DataDictionary, FileUploadIdInput {

    private static final long serialVersionUID = 7088702289610881234L;

    /**
     * 状态
     * {@link com.autumn.zero.common.library.constants.CommonStatusConstant}
     */
    @ApiModelProperty(value = CommonStatusConstant.API_MODEL_PROPERTY)
    private Integer status;

    /**
     * 字典类型
     */
    @ApiModelProperty(value = "字典类型类型")
    @NotNull(message = "字典类型不能为空")
    private Integer dictionaryType;

    /**
     * 上传文件id集合
     */
    @ApiModelProperty(value = "上传文件id集合")
    private List<Long> uploadFileIds;

    @Override
    public void valid() {
        super.valid();
        if (this.getStatus() == null) {
            this.setStatus(CommonStatusConstant.STATUS_ENABLE);
        } else {
            if (!CommonStatusConstant.exist(this.getStatus())) {
                ExceptionUtils.throwValidationException("无效的状态，只能支持启用或禁用");
            }
        }
    }
}
