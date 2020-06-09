package com.autumn.util.excel;

import com.autumn.util.excel.annotation.ExcelColumn;
import com.autumn.util.excel.annotation.ExcelWorkSheet;
import com.autumn.validation.annotation.NotNullOrBlank;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
 * </p>
 *
 * @description TODO
 * @author: 老码农
 * @create: 2020-02-25 07:54
 **/

@ExcelWorkSheet(exportTitle = "质检项目", isExportTitle = true, exportExplainRowHeight = 120, exportExplain = "质检项目导入说明，(*)表示必填项，删除多余的空白行；" +
        "\r\n1、分类名称：指二级分类的完整名称，分类用符号/进行分隔连接，如 ASTM 天然石材检测/花岗石规格板技术规程；" +
        "\r\n2、标准分类：数据来源在(系统管理/基础管理/公共字典/质检项目标准分类) 如 中国标准；" +
        "\r\n3、状态：会自动变为启用；" +
        "\r\n4、顺序：如果未填写则自动排序。")
public class QualityTestingItemImportTemplateDto implements Serializable {
    private static final long serialVersionUID = -7256674999571086921L;

    /**
     * 名称
     */
    @NotNullOrBlank(message = "项目名称不能为空")
    @ExcelColumn(friendlyName = "项目名称", width = 150, order = 1, importNotNullable = true, isImportColumn = true)
    private String name;

    /**
     * 顺序
     */
    @ExcelColumn(friendlyName = "顺序", width = 80, order = 2, importNotNullable = false, isImportColumn = true)
    private Integer sortId;

    /**
     * 分类名称
     */
    @ExcelColumn(friendlyName = "分类名称", width = 300, order = 3, importNotNullable = true, isImportColumn = true)
    @NotNull(message = "分类名称不能为空。")
    private String fullCategoryName;

    /**
     * 标准分类
     */
    @ExcelColumn(friendlyName = "标准分类", width = 100, order = 4, importNotNullable = false, isImportColumn = true)
    private String standardCategoryName;

    /**
     * 检验内容
     */
    @ExcelColumn(friendlyName = "检验内容", width = 200, order = 5, importNotNullable = false, isImportColumn = true)
    private String inspectionContents;

    /**
     * 检验依据
     */
    @ExcelColumn(friendlyName = "检验依据", width = 200, order = 6, importNotNullable = true, isImportColumn = true)
    @NotNull(message = "检验依据不能为空。")
    private String inspectionBasis;

    /**
     * 样品要求
     */
    @ExcelColumn(friendlyName = "样品要求", width = 200, order = 7, importNotNullable = false, isImportColumn = true)
    private String sampleRequirements;

    /**
     * 样品数量
     */
    @ExcelColumn(friendlyName = "样品数量", width = 100, order = 8, importNotNullable = true, isImportColumn = true)
    @NotNull(message = "样品数量不能为空。")
    private Integer sampleQuantity;

    /**
     * 耗时工作日
     */
    @ExcelColumn(friendlyName = "耗时工作日", width = 100, order = 9, importNotNullable = true, isImportColumn = true)
    @NotNull(message = "耗时工作日不能为空。")
    private Integer workingDay;

    /**
     * 费用
     */
    @ExcelColumn(friendlyName = "费用", width = 120, order = 10, importNotNullable = true, isImportColumn = true)
    @NotNull(message = "费用不能为空。")
    private BigDecimal fee;

    /**
     * 备注
     */
    @ExcelColumn(friendlyName = "备注", width = 300, order = 100, importNotNullable = false, isImportColumn = true)
    private String remarks;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSortId() {
        return sortId;
    }

    public void setSortId(Integer sortId) {
        this.sortId = sortId;
    }

    public String getFullCategoryName() {
        return fullCategoryName;
    }

    public void setFullCategoryName(String fullCategoryName) {
        this.fullCategoryName = fullCategoryName;
    }

    public String getStandardCategoryName() {
        return standardCategoryName;
    }

    public void setStandardCategoryName(String standardCategoryName) {
        this.standardCategoryName = standardCategoryName;
    }

    public String getInspectionContents() {
        return inspectionContents;
    }

    public void setInspectionContents(String inspectionContents) {
        this.inspectionContents = inspectionContents;
    }

    public String getInspectionBasis() {
        return inspectionBasis;
    }

    public void setInspectionBasis(String inspectionBasis) {
        this.inspectionBasis = inspectionBasis;
    }

    public String getSampleRequirements() {
        return sampleRequirements;
    }

    public void setSampleRequirements(String sampleRequirements) {
        this.sampleRequirements = sampleRequirements;
    }

    public Integer getSampleQuantity() {
        return sampleQuantity;
    }

    public void setSampleQuantity(Integer sampleQuantity) {
        this.sampleQuantity = sampleQuantity;
    }

    public Integer getWorkingDay() {
        return workingDay;
    }

    public void setWorkingDay(Integer workingDay) {
        this.workingDay = workingDay;
    }

    public BigDecimal getFee() {
        return fee;
    }

    public void setFee(BigDecimal fee) {
        this.fee = fee;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}
