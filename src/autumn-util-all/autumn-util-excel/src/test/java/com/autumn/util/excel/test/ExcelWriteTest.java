package com.autumn.util.excel.test;

import com.autumn.util.excel.QualityTestingItemImportTemplateDto;
import com.autumn.util.excel.exports.AbstractExportInfo;
import com.autumn.util.excel.exports.GenericExportInfo;
import com.autumn.util.excel.exports.MapExportInfo;
import com.autumn.util.excel.sheet.WorkSheetInfo;
import com.autumn.util.excel.utils.ExcelUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.junit.Test;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 测试Excel工具导出
 *
 * @author JuWa ▪ Zhang
 * @date 2017年12月6日
 */
public class ExcelWriteTest extends AbstractTest {

    /**
     * 复杂格式的excel，通过普通java bean 导出
     */
    @Test
    public void templateTest() {
        GenericExportInfo<QualityTestingItemImportTemplateDto> exportInfo = new GenericExportInfo<>();
        List<QualityTestingItemImportTemplateDto> items = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            items.add(new QualityTestingItemImportTemplateDto());
        }
        exportInfo.setItems(items);
        WorkSheetInfo workInfo = ExcelUtils.getWorkSheetInfo(QualityTestingItemImportTemplateDto.class);
        write(exportInfo, workInfo, "template.xlsx", true);
    }

    /**
     * 复杂格式的excel，通过普通java bean 导出
     */
    @Test
    public void complicatedBeanExcel() {
        GenericExportInfo<UserInfo> exportInfo = new GenericExportInfo<UserInfo>();
        List<UserInfo> items = new ArrayList<UserInfo>();
        for (int i = 1; i <= 1000; i++) {
            UserInfo userInfo = new UserInfo();
            userInfo.setUserId(i);
            userInfo.setUserName("TOM," + i);
            userInfo.setA1("A1\"\"" + (i * 2));
            userInfo.setA2("A2" + (i * i));
            items.add(userInfo);
        }
        exportInfo.setItems(items);
        WorkSheetInfo workInfo = createWorkSheetInfo();
        write(exportInfo, workInfo, "complicatedBean.xlsx", false);
        writeCsv(exportInfo, workInfo, "complicatedBean.csv", false);
    }

    /**
     * 通过Map导出
     */
    @Test
    public void complicatedMap() {
        MapExportInfo info = new MapExportInfo();
        List<Map<String, Object>> items = info.getItems();
        items.addAll(getMaps());
        WorkSheetInfo workSheetInfo = createWorkSheetInfo();
        write(info, workSheetInfo, "complicatedMap.xlsx", false);
    }

    /**
     * 简单excel表格，通过Map导出
     */
    @Test
    public void plainMapExcel() {
        MapExportInfo info = new MapExportInfo();
        List<Map<String, Object>> items = info.getItems();
        items.addAll(getMaps());
        WorkSheetInfo workSheetInfo = createPlainWorkSheetInfo();
        write(info, workSheetInfo, "plainMap.xlsx", false);
    }

    /**
     * 简单excel表格，通过普通java bean 导出
     */
    @Test
    public void plainBeanExcel() {
        GenericExportInfo<UserInfo> exportInfo = new GenericExportInfo<UserInfo>();
        List<UserInfo> items = new ArrayList<UserInfo>();
        for (int i = 1; i < TEST_FOR_COUNT; i++) {
            UserInfo userInfo = new UserInfo();
            userInfo.setUserId(i);
            userInfo.setUserName("TOM" + i);
            userInfo.setA1("A1" + (i * 2));
            userInfo.setA2("A2" + (i * i));
            userInfo.setAge(i + 22);
            items.add(userInfo);
        }
        exportInfo.setItems(items);
//		WorkbookInfo workbookInfo = createPlainWorkbookInfo();
        WorkSheetInfo workSheetInfo = ExcelUtils.getWorkSheetInfo(UserInfo.class);
        write(exportInfo, workSheetInfo, "plainBean.xlsx", false);
    }

    /**
     * 通过字节流输出
     *
     * @param exportInfo
     * @param workSheetInfo
     */
    private <T> void write(AbstractExportInfo<T> exportInfo, WorkSheetInfo workSheetInfo, String fileName, boolean isImportTemplate) {
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream("D:\\ExcelTest\\" + fileName);
            /*
             * 若第二个参数为true，则会导出模板
             */
            Workbook workbook = workSheetInfo.createExportWorkbook(exportInfo, isImportTemplate);
            workbook.write(fos);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * @param exportInfo
     * @param workSheetInfo
     * @param fileName
     * @param isImportTemplate
     * @param <T>
     */
    private <T> void writeCsv(AbstractExportInfo<T> exportInfo, WorkSheetInfo workSheetInfo, String fileName, boolean isImportTemplate) {
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream("D:\\ExcelTest\\" + fileName);
            workSheetInfo.createCsv(exportInfo, isImportTemplate, fos);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 获取map元素的集合
     *
     * @return
     */
    private List<Map<String, Object>> getMaps() {
        List<Map<String, Object>> items = new ArrayList<>();
        Map<String, Object> map = new HashMap<>(8);
        map.put("userId", "1001");
        map.put("userName", "Tom");
        map.put("a1", "A1-" + 1001);
        map.put("a2", "A2-" + 1001);
        items.add(map);
        Map<String, Object> map2 = new HashMap<>(8);
        map2.put("userId", "1002");
        map2.put("userName", "Jordan");
        map2.put("a2", "A2-1002");
        items.add(map2);
        return items;
    }
}
