package com.autumn.application.dto.input;

import com.autumn.annotation.FriendlyProperty;

/**
 * Excel导入选项输入
 *
 * @Description TODO
 * @Author 老码农
 * @Date 2019-08-05 21:12
 */
public interface ExcelImportOptionInput extends DataImportOptionInput {

    /**
     * Excel导入默认开始行号
     * <p>
     * 准确计算应当需要算出是否显示标题、是否显示说明行，标题的合并行后才得到准确的行号，可调用 {@link com.autumn.util.data.DataImportUtils} 工具算出，但一般情况下导入均显示标题与说明，无合并标题，因此为默认4
     * </p>
     */
    public static final int DEFAULT_EXCEL_IMPORT_BEGIN_ROW = 4;

    /**
     * 只读Excel导入选项
     * <p>
     * 线程是安全的，任何设置均无效
     * </p>
     */
    public static final ExcelImportOptionInput READ_ONLY = new ExcelImportOptionInput() {

        private static final long serialVersionUID = -3363413213302106463L;

        @Override
        public String getSheetName() {
            return null;
        }

        @Override
        public void setSheetName(String sheetName) {

        }

        @Override
        public void valid() {

        }
    };

    /**
     * 获取工作表名称
     *
     * @return
     */
    @FriendlyProperty(value = "工作表名称，未指定则自动匹配")
    String getSheetName();

    /**
     * 设置工作表名称
     *
     * @param sheetName
     */
    void setSheetName(String sheetName);
}
