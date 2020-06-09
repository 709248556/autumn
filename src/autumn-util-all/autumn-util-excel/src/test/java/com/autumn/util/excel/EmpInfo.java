package com.autumn.util.excel;

import com.autumn.util.excel.annotation.ExcelColumn;
import com.autumn.util.excel.annotation.ExcelWorkSheet;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 员工信息
 */
@ExcelWorkSheet(exportTitle = "员工信息")
public class EmpInfo {
    @ExcelColumn(friendlyName = "工号", order = 1, isImportColumn = true, importNotNullable = true)
    private String empCode;

    @ExcelColumn(friendlyName = "姓名", order = 2, width = 200, isImportColumn = true)
    private String empName;

    @ExcelColumn(friendlyName = "出生日期", order = 3, width = 200, format = "yyyy-MM-dd", isImportColumn = true)
    private Date birthday = new Date(0);

    @ExcelColumn(friendlyName = "出生日期2", order = 3, width = 200, format = "yyyy/MM/dd", isImportColumn = true)
    private Date birthday2;

    @ExcelColumn(friendlyName = "出生日期3", order = 3, width = 200, format = "yyyy/MM/dd HH:mm:ss", isImportColumn = true)
    private Date birthday3;

    @ExcelColumn(friendlyName = "年龄", order = 4, isImportColumn = true)
    private int age;

    @ExcelColumn(friendlyName = "启用", order = 5, isImportColumn = true)
    private boolean isEnable;

    @ExcelColumn(friendlyName = "工资", order = 6, width = 120, isImportColumn = true)
    private BigDecimal wages = new BigDecimal(0);

    public String getEmpCode() {
        return empCode;
    }

    public void setEmpCode(String empCode) {
        this.empCode = empCode;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Date getBirthday2() {
        return birthday2;
    }

    public void setBirthday2(Date birthday2) {
        this.birthday2 = birthday2;
    }

    public Date getBirthday3() {
        return birthday3;
    }

    public void setBirthday3(Date birthday3) {
        this.birthday3 = birthday3;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public boolean getIsEnable() {
        return isEnable;
    }

    public void setIsEnable(boolean enable) {
        isEnable = enable;
    }

    public BigDecimal getWages() {
        return wages;
    }

    public void setWages(BigDecimal wages) {
        this.wages = wages;
    }
}