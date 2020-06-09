package com.autumn.zero.common.library.application.dto.common.region;

import com.autumn.validation.DefaultDataValidation;
import com.autumn.exception.ExceptionUtils;
import com.autumn.util.excel.annotation.ExcelColumn;
import com.autumn.util.excel.annotation.ExcelWorkSheet;
import com.autumn.validation.annotation.NotNullOrBlank;
import com.autumn.zero.common.library.entities.AbstractNameUserAuditingStatusEntity;
import com.autumn.zero.common.library.entities.AbstractTreeCodeEntity;
import com.autumn.zero.common.library.entities.common.Region;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

/**
 * 行政区导入模板数据
 *
 * @Description TODO
 * @Author 老码农
 * @Date 2019-08-05 21:02
 */
@Getter
@Setter
@ExcelWorkSheet(exportTitle = "行政区", isExportTitle = true, exportExplain = "行政区导入说明，(*)表示必填项，删除多余的空白行。")
public class RegionImportTemplateDto extends DefaultDataValidation {

    private static final long serialVersionUID = -7378008049323123859L;

    /**
     * 行政区代码
     */
    @ExcelColumn(friendlyName = "行政区代码", width = 150, order = 1, importNotNullable = true, isImportColumn = true)
    @NotNullOrBlank(message = "行政区代码不能为空。")
    @Length(max = AbstractTreeCodeEntity.MAX_LENGTH_CODE, message = "行政区代码 不能超过 " + AbstractTreeCodeEntity.MAX_LENGTH_CODE + " 个字。")
    private String code;

    /**
     * 行政区父级代码
     */
    @ExcelColumn(friendlyName = "行政区父级代码", width = 150, order = 2, importNotNullable = false, isImportColumn = true)
    private String parentCode;

    /**
     * 行政区名称
     */
    @ExcelColumn(friendlyName = "行政区名称", width = 200, order = 3, importNotNullable = true, isImportColumn = true)
    @NotNullOrBlank(message = "行政区代码不能为空。")
    @Length(max = AbstractNameUserAuditingStatusEntity.MAX_NAME_LENGTH, message = "行政区名称长度过"
            + AbstractNameUserAuditingStatusEntity.MAX_NAME_LENGTH + "字。")
    private String name;

    /**
     * 备注
     */
    @ExcelColumn(friendlyName = "备注", width = 300, order = 4, importNotNullable = false, isImportColumn = true)
    private String remarks;

    /**
     * 主键
     */
    private Region region;

    /**
     * 父级行政区
     */
    private Region parentRegion;

    /**
     * 父级Dto
     */
    private RegionImportTemplateDto parentDto;

    /**
     * 级别
     */
    private int level;

    /**
     * 顺序
     */
    private int sortId;

    /**
     * 子级数量
     */
    private int childrenCount;

    /**
     * 完整代码
     */
    private String fullCode;

    /**
     * 完整名称
     */
    private String fullName;

    @Override
    public void valid() {
        super.valid();
        this.setCode(this.getCode().trim());
        if (this.getParentCode() != null) {
            this.setParentCode(this.getParentCode());
            if (this.getCode().equalsIgnoreCase(this.getParentCode())) {
                ExceptionUtils.throwValidationException("父级行政区代码不能与行政区代码相同。");
            }
        }
        this.setName(this.getName().trim());
        this.setRegion(null);
        this.setParentRegion(null);
        this.setParentDto(null);
        this.setLevel(1);
        this.setSortId(1);
        this.setChildrenCount(0);
        this.setFullCode("");
        this.setFullName("");
    }
}
