package com.autumn.util.mail;

import com.autumn.exception.ExceptionUtils;
import com.autumn.validation.DefaultDataValidation;
import com.autumn.validation.annotation.NotNullOrBlank;

import javax.activation.FileDataSource;
import javax.activation.URLDataSource;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * 邮件发送消息
 *
 * @Description TODO
 * @Author 老码农
 * @Date 2019-10-09 20:13
 */
public class MailSendMessage extends DefaultDataValidation {

    private static final long serialVersionUID = 5923279227549779072L;

    /**
     * 发送的用户邮箱
     */
    @NotNullOrBlank(message = "发送的用户邮箱不能为空")
    private String user;

    /**
     * 密码或授权码
     */
    @NotNullOrBlank(message = "密码或授权码不能为空")
    private String password;

    /**
     * smtp主机
     */
    @NotNullOrBlank(message = "smtp主机不能为空")
    private String smtpHost;

    /**
     * 启用SSL协议
     */
    private boolean enableSSL;

    /**
     * 端口
     */
    private Integer port;

    /**
     * 发送邮箱
     */
    private String from;

    /**
     * 接收邮箱，多个邮箱用逗号分隔
     */
    @NotNullOrBlank(message = "接收邮箱不能为空")
    private String to;

    /**
     * 主题
     */
    @NotNullOrBlank(message = "主题不能为空")
    private String subject;

    /**
     * 内容
     */
    private String content;

    private final List<DataSourcePart> parts = new ArrayList<>(16);

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSmtpHost() {
        return smtpHost;
    }

    public void setSmtpHost(String smtpHost) {
        this.smtpHost = smtpHost;
    }

    public boolean isEnableSSL() {
        return enableSSL;
    }

    public void setEnableSSL(boolean enableSSL) {
        this.enableSSL = enableSSL;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public final List<DataSourcePart> getParts() {
        return this.parts;
    }

    /**
     * 清除
     */
    public void clearParts() {
        this.getParts().clear();
    }

    /**
     * 添加附件
     *
     * @param file 文件
     */
    public void addParts(File file) {
        ExceptionUtils.checkNotNull(file, "file");
        FileDataSource fileDataSource = new FileDataSource(file);
        this.getParts().add(new DataSourcePart(fileDataSource.getName(), fileDataSource));
    }

    /**
     * 添加附件
     *
     * @param fileName 文件名称
     */
    public void addParts(String fileName) {
        ExceptionUtils.checkNotNullOrBlank(fileName, "fileName");
        FileDataSource fileDataSource = new FileDataSource(fileName);
        this.getParts().add(new DataSourcePart(fileDataSource.getName(), fileDataSource));
    }

    /**
     * 添加附件
     *
     * @param fileName 文件名称
     */
    public void addParts(String fileName, byte[] bytes) {
        ExceptionUtils.checkNotNullOrBlank(fileName, "fileName");
        ExceptionUtils.checkNotNull(bytes, "bytes");
        this.getParts().add(new DataSourcePart(fileName, new ByteArrayDataSource(bytes, "application/octet-stream")));
    }

    /**
     * 添加附件
     *
     * @param fileName 文件名称
     */
    public void addParts(String fileName, InputStream inputStream) throws IOException {
        ExceptionUtils.checkNotNullOrBlank(fileName, "fileName");
        ExceptionUtils.checkNotNull(inputStream, "inputStream");
        this.getParts().add(new DataSourcePart(fileName, new ByteArrayDataSource(inputStream, "application/octet-stream")));
    }

    /**
     * 添加附件
     *
     * @param url 文件名称
     */
    public void addParts(URL url) {
        ExceptionUtils.checkNotNull(url, "url");
        URLDataSource urlDataSource = new URLDataSource(url);
        this.getParts().add(new DataSourcePart(urlDataSource.getName(), urlDataSource));
    }

    @Override
    public void valid() {
        super.valid();
        if (this.isEnableSSL() && this.getPort() == null) {
            ExceptionUtils.throwValidationException("启用 SSL 时，端口不能为空。");
        }
    }
}
