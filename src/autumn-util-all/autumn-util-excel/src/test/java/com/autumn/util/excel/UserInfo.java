package com.autumn.util.excel;

import com.autumn.util.excel.annotation.ExcelColumn;
import com.autumn.util.excel.annotation.ExcelWorkSheet;

@ExcelWorkSheet(exportTitle = "用户信息")
public class UserInfo
{
    @ExcelColumn(groupName = "用户", friendlyName = "用户Id")
    private int userId;

    @ExcelColumn(groupName = "用户", friendlyName = "用户名称", isMergeContentRow = true)
    private String userName;

    @ExcelColumn(groupName = "用户")
    private String age;

    @ExcelColumn(groupName = "A")
    private String a1;

    // 空白也合并
    @ExcelColumn(groupName = "A", isMergeContentRow = true, isMergeBlankContentRow = true)
    private String a2;

    // 空白不合并
    @ExcelColumn(groupName = "A", isMergeContentRow = true)
    private String a3;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getA1() {
        return a1;
    }

    public void setA1(String a1) {
        this.a1 = a1;
    }

    public String getA2() {
        return a2;
    }

    public void setA2(String a2) {
        this.a2 = a2;
    }

    public String getA3() {
        return a3;
    }

    public void setA3(String a3) {
        this.a3 = a3;
    }
}