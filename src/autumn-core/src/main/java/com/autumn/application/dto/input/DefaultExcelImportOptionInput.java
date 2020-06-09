package com.autumn.application.dto.input;

import com.autumn.annotation.FriendlyProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 默认Excel导入选项输入
 * <p>
 * </p>
 *
 * @description TODO
 * @author: 老码农
 * @create: 2020-03-07 17:50
 **/
@Getter
@Setter
public class DefaultExcelImportOptionInput extends DefaultDataImportOptionInput implements ExcelImportOptionInput {

    private static final long serialVersionUID = -6614583918204027147L;

    /**
     * 工作表名
     */
    @FriendlyProperty(value = "工作表名称，未指定则自动匹配")
    private String sheetName;

}