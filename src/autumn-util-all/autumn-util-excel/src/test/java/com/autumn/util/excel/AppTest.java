package com.autumn.util.excel;

import com.autumn.util.CollectionUtils;
import com.autumn.util.excel.column.ColumnGroup;
import com.autumn.util.excel.column.ColumnInfo;
import com.autumn.util.excel.enums.CellType;
import com.autumn.util.HorizontalAlignment;
import com.autumn.util.excel.exports.GenericExportInfo;
import com.autumn.util.excel.exports.MapExportInfo;
import com.autumn.util.excel.sheet.WorkSheetInfo;
import com.autumn.util.excel.utils.ExcelUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

public class AppTest {
    /**
     * 导出测试（泛型）
     */
    @Test
    public void test1() {
        GenericExportInfo<EmpInfo> empInfos = new GenericExportInfo<>();

        EmpInfo tempVar = new EmpInfo();
        tempVar.setEmpCode("001");
        tempVar.setEmpName("张三");
        tempVar.setAge(20);
        tempVar.setBirthday(new Date());
        tempVar.setBirthday2(new Date());
        tempVar.setIsEnable(true);
        tempVar.setWages(new BigDecimal(5000));
        empInfos.getItems().add(tempVar);

        EmpInfo tempVar2 = new EmpInfo();
        tempVar2.setEmpCode("002");
        tempVar2.setEmpName("李四");
        tempVar2.setAge(20);
        tempVar2.setBirthday(new Date());
        tempVar2.setIsEnable(false);
        tempVar2.setWages(new BigDecimal(200));
        empInfos.getItems().add(tempVar2);

        EmpInfo tempVar3 = new EmpInfo();
        tempVar3.setEmpCode("003");
        tempVar3.setEmpName("王五");
        tempVar3.setAge(20);
        tempVar3.setBirthday(new Date());
        tempVar3.setIsEnable(true);
        tempVar3.setWages(new BigDecimal(30152));
        empInfos.getItems().add(tempVar3);

        EmpInfo tempVar4 = new EmpInfo();
        tempVar4.setEmpCode("004");
        tempVar4.setEmpName("88888888888小六");
        tempVar4.setAge(20);
        tempVar4.setBirthday(new Date());
        tempVar4.setBirthday3(new Date());
        tempVar4.setIsEnable(true);
        tempVar4.setWages(new BigDecimal(15246));
        empInfos.getItems().add(tempVar4);

        FileOutputStream fos = null;
        try {
            WorkSheetInfo workInfo = ExcelUtils.getWorkSheetInfo(EmpInfo.class);
            fos = new FileOutputStream("D:\\empInfo.xlsx");
            Workbook work = workInfo.createExportWorkbook(empInfos, true);
            work.write(fos);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (fos != null)
                    fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }


    /**
     * 导入测试
     */
    @Test
    public void test2() {
        WorkSheetInfo workInfo = ExcelUtils.getWorkSheetInfo(EmpInfo.class);
        FileInputStream fos = null;
        try {
            fos = new FileInputStream("D:\\empInfo.xlsx");
            List<EmpInfo> list = workInfo.createObjectList(fos, null, EmpInfo.class);

            System.out.println(list);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (fos != null)
                    fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * 分组测试
     */
    @Test
    public void test3() {
        WorkSheetInfo workInfo = createWorkSheetInfo();

        List<Map<String, Object>> lstItems = CollectionUtils.newArrayList();
        Map<String, Object> dic = CollectionUtils.newHashMap();
        dic.put("UserId", 5588L);
        dic.put("UserName", "88888888888888888");

        lstItems.add(dic);

        dic = new HashMap<>();
        dic.put("UserId", 859);
        dic.put("UserName", "rrrrrrrrrrrrrrrrrrrrr");

        lstItems.add(dic);

        MapExportInfo esx = new MapExportInfo();
        esx.setItems(lstItems);


        FileOutputStream fos = null;
        try {
            Workbook work = workInfo.createExportWorkbook(esx, false);
            fos = new FileOutputStream("D:\\userInfo.xlsx");
            work.write(fos);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (fos != null)
                    fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private WorkSheetInfo createWorkSheetInfo() {
        WorkSheetInfo work = new WorkSheetInfo();

        work.getHeader().setShow(true);
        work.getHeader().setName("用户信息");
        work.getChildHeader().setShow(true);
        work.getChildHeader().setName("条件：");

        ColumnInfo tempVar = new ColumnInfo();
        tempVar.setAlignment(HorizontalAlignment.LEFT);
        tempVar.setCellType(CellType.CELL_TYPE_INTEGER);
        tempVar.setFriendlyName("User Id");
        tempVar.setOrder(1);
        tempVar.setPropertyName("UserId");
        tempVar.setWidth(80);
        work.getColumns().add(tempVar);

        //  ImportNotNullable = true,
        ColumnInfo tempVar2 = new ColumnInfo();
        tempVar2.setAlignment(HorizontalAlignment.LEFT);
        tempVar2.setCellType(CellType.CELL_TYPE_STRING);
        tempVar2.setFriendlyName("User Name");
        tempVar2.setOrder(2);
        tempVar2.setPropertyName("UserName");
        tempVar2.setWidth(80);
        tempVar2.setImportColumn(true);
        work.getColumns().add(tempVar2);

        ColumnGroup tempVar3 = new ColumnGroup();
        tempVar3.setOrder(3);
        tempVar3.setFriendlyName("A");
        ColumnGroup group = tempVar3;
        ColumnInfo tempVar4 = new ColumnInfo();
        tempVar4.setAlignment(HorizontalAlignment.LEFT);
        tempVar4.setCellType(CellType.CELL_TYPE_STRING);
        tempVar4.setFriendlyName("A-1");
        tempVar4.setOrder(1);
        tempVar4.setPropertyName("A1");
        tempVar4.setWidth(80);
        tempVar4.setImportNotNullable(true);
        tempVar4.setImportColumn(true);
        group.getColumns().add(tempVar4);

        ColumnInfo tempVar5 = new ColumnInfo();
        tempVar5.setAlignment(HorizontalAlignment.LEFT);
        tempVar5.setCellType(CellType.CELL_TYPE_STRING);
        tempVar5.setFriendlyName("A-2");
        tempVar5.setOrder(2);
        tempVar5.setPropertyName("A2");
        tempVar5.setWidth(80);
        group.getColumns().add(tempVar5);
        work.getColumns().add(group);

        ColumnGroup tempVar6 = new ColumnGroup();
        tempVar6.setOrder(4);
        tempVar6.setFriendlyName("A2");
        group = tempVar6;
        ColumnInfo tempVar7 = new ColumnInfo();
        tempVar7.setAlignment(HorizontalAlignment.LEFT);
        tempVar7.setCellType(CellType.CELL_TYPE_STRING);
        tempVar7.setFriendlyName("A-3");
        tempVar7.setOrder(1);
        tempVar7.setPropertyName("A1");
        tempVar7.setWidth(80);
        group.getColumns().add(tempVar7);
        ColumnInfo tempVar8 = new ColumnInfo();
        tempVar8.setAlignment(HorizontalAlignment.LEFT);
        tempVar8.setCellType(CellType.CELL_TYPE_STRING);
        tempVar8.setFriendlyName("A-4");
        tempVar8.setOrder(2);
        tempVar8.setPropertyName("A2");
        tempVar8.setWidth(80);
        group.getColumns().add(tempVar8);

        ColumnGroup tempVar9 = new ColumnGroup();
        tempVar9.setOrder(4);
        tempVar9.setFriendlyName("A2-1");
        ColumnGroup group1 = tempVar9;

        ColumnInfo tempVar10 = new ColumnInfo();
        tempVar10.setAlignment(HorizontalAlignment.LEFT);
        tempVar10.setCellType(CellType.CELL_TYPE_STRING);
        tempVar10.setFriendlyName("A-5");
        tempVar10.setOrder(1);
        tempVar10.setPropertyName("A1");
        tempVar10.setWidth(80);
        tempVar10.setImportNotNullable(true);
        tempVar10.setImportColumn(true);
        group1.getColumns().add(tempVar10);
        ColumnInfo tempVar11 = new ColumnInfo();
        tempVar11.setAlignment(HorizontalAlignment.LEFT);
        tempVar11.setCellType(CellType.CELL_TYPE_STRING);
        tempVar11.setFriendlyName("A-6");
        tempVar11.setOrder(2);
        tempVar11.setPropertyName("A2");
        tempVar11.setWidth(80);
        group1.getColumns().add(tempVar11);

        group.getColumns().add(group1);

        work.getColumns().add(group);

        return work;
    }
}
