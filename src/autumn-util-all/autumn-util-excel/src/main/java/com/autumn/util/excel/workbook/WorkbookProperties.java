package com.autumn.util.excel.workbook;

import java.io.Serializable;

/**
 * 工作簿属性
 * <p>
 * </p>
 *
 * @description TODO
 * @author: 老码农
 * @create: 2020-02-24 11:03
 **/
public class WorkbookProperties implements Serializable {

    private static final long serialVersionUID = 118807353995775868L;

    /**
     * 组织机构名称
     */
    private String company;

    /**
     * 类别
     */
    private String category;

    /**
     * 作者
     */
    private String author;

    /**
     * 主题
     */
    private String subject;

    /**
     * 应用程序名称
     */
    private String applicationName;

    /**
     * 标题
     */
    private String title;

    public String getCompany() {
        return company;
    }


    public void setCompany(String company) {
        this.company = company;
    }


    public String getCategory() {
        return category;
    }


    public void setCategory(String category) {
        this.category = category;
    }


    public String getAuthor() {
        return author;
    }


    public void setAuthor(String author) {
        this.author = author;
    }


    public String getSubject() {
        return subject;
    }


    public void setSubject(String subject) {
        this.subject = subject;
    }


    public String getApplicationName() {
        return applicationName;
    }


    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }


    public String getTitle() {
        return title;
    }


    public void setTitle(String title) {
        this.title = title;
    }
}
