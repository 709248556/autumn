package com.autumn.util.mail;

import com.autumn.exception.ExceptionUtils;
import com.autumn.util.StringUtils;

import javax.activation.DataHandler;
import javax.mail.*;
import javax.mail.internet.*;
import java.util.Date;
import java.util.Properties;

/**
 * 邮件帮助
 *
 * @Description TODO
 * @Author 老码农
 * @Date 2019-10-09 20:29
 */
public class MailUtils {

    /**
     * 端口
     */
    public static final String PROPERTY_MAIL_SMTP_HOST = "mail.smtp.host";

    /**
     * 授权
     */
    public static final String PROPERTY_MAIL_SMTP_AUTH = "mail.smtp.auth";

    /**
     * 启用协议
     */
    public static final String PROPERTY_MAIL_SMTP_SSL_ENABLE = "mail.smtp.ssl.enable";

    /**
     * 协议
     */
    public static final String PROPERTY_MAIL_TRANSPORT_PROTOCOL = "mail.transport.protocol";

    /**
     * 端口
     */
    public static final String PROPERTY_MAIL_SMTP_PORT = "mail.smtp.port";

    /**
     * smtp 协议
     */
    public static final String PROTOCOL_SMTP = "smtp";

    /**
     * 解码
     *
     * @param str
     * @return
     */
    public static String decodeText(String str) {
        try {
            if (StringUtils.isNullOrBlank(str)) {
                return "";
            }
            return MimeUtility.decodeText(str);
        } catch (Exception ignored) {
            return str;
        }
    }

    /**
     * 编码
     *
     * @param str
     * @return
     */
    public static String encodeText(String str) {
        try {
            if (StringUtils.isNullOrBlank(str)) {
                return "";
            }
            return MimeUtility.encodeText(str);
        } catch (Exception ignored) {
            return str;
        }
    }

    /**
     * 发送邮件
     *
     * @param sendMessage 消息
     */
    public static void sendMail(MailSendMessage sendMessage) throws Exception {
        ExceptionUtils.checkNotNull(sendMessage, "sendMessage");
        sendMessage.valid();
        Properties props = new Properties();
        props.put(PROPERTY_MAIL_SMTP_HOST, sendMessage.getSmtpHost());
        props.put(PROPERTY_MAIL_SMTP_AUTH, "true");
        if (sendMessage.isEnableSSL()) {
            props.put(PROPERTY_MAIL_TRANSPORT_PROTOCOL, PROTOCOL_SMTP);
            props.put(PROPERTY_MAIL_SMTP_SSL_ENABLE, "true");
        }
        if (sendMessage.getPort() != null) {
            props.put(PROPERTY_MAIL_SMTP_PORT, sendMessage.getPort());
        }
        MailAuthenticator auth = new MailAuthenticator(sendMessage.getUser(), sendMessage.getPassword());
        Session session = Session.getInstance(props, auth);
        MimeMessage message = new MimeMessage(session);
        if (StringUtils.isNotNullOrBlank(sendMessage.getFrom())) {
            message.setFrom(new InternetAddress(sendMessage.getFrom()));
        } else {
            message.setFrom(new InternetAddress(sendMessage.getUser()));
        }
        String[] addresses = sendMessage.getTo().split(",");
        Address[] internetAddresses = new Address[addresses.length];
        for (int i = 0; i < addresses.length; i++) {
            internetAddresses[i] = new InternetAddress(addresses[i]);
        }
        message.addRecipients(Message.RecipientType.TO, internetAddresses);
        message.setSubject(encodeText(sendMessage.getSubject()));
        // 整个邮件：正文+附件
        Multipart multipart = new MimeMultipart();
        // 正文
        MimeBodyPart contentPart = new MimeBodyPart();
        String content = sendMessage.getContent();
        if (content == null) {
            content = "<p></p>";
        } else {
            content = content.trim();
            if (!content.startsWith("<")) {
                content = "<p>" + content + "</p>";
            }
        }
        contentPart.setContent(content, "text/html;charset=utf-8");
        multipart.addBodyPart(contentPart);
        //附件
        if (sendMessage.getParts().size() > 0) {
            for (DataSourcePart part : sendMessage.getParts()) {
                BodyPart bodyPart = new MimeBodyPart();
                bodyPart.setDataHandler(new DataHandler(part.getDataSource()));
                bodyPart.setFileName(encodeText(part.getName()));
                multipart.addBodyPart(bodyPart);
            }
        }
        message.setContent(multipart);
        message.setSentDate(new Date());
        message.saveChanges();
        try (Transport trans = session.getTransport(PROTOCOL_SMTP)) {
            trans.connect();
            trans.sendMessage(message, message.getAllRecipients());
        }
    }

}
