package com.autumn.util.excel.test;

import com.autumn.util.excel.utils.ExcelUtils;
import com.autumn.util.excel.sheet.WorkSheetInfo;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

/**
 * excel导入测试
 * @author JuWa ▪ Zhang
 * @date 2017年12月15日
 */
public class ExcelReadTest extends AbstractTest {

	/**
	 * 简单格式的Excel文件导入
	 */
	@Test
	public void plainExcelimport() {
		FileInputStream fis = null;
		try {
			fis = new FileInputStream("D:\\ExcelTest\\plainBean.xlsx");
//			WorkbookInfo info = createWorkbookInfo();
			WorkSheetInfo workSheetInfo = ExcelUtils.getWorkSheetInfo(UserInfo.class);
			//WorkbookInfo info = createPlainWorkbookInfo();
			List<UserInfo> imports = workSheetInfo.createObjectList(fis, "Sheet1", UserInfo.class);
			for (UserInfo userInfo : imports) {
				System.out.println(userInfo);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			if (fis != null) {
				try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
