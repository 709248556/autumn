package com.autumn.util.mail;

import com.autumn.exception.ExceptionUtils;

import javax.mail.Address;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;

/**
 * 接收邮件消息
 *
 * @Description TODO
 * @Author 老码农
 * @Date 2019-10-09 23:40
 */
public class ReceivedMailMessage {

    private final MimeMessage message;

    /**
     * 实例化
     *
     * @param message
     */
    public ReceivedMailMessage(MimeMessage message) {
        this.message = message;
    }

    private String subject = null;

    /**
     * 获取主题
     *
     * @return
     */
    public String getSubject() {
        if (this.subject == null) {
            try {
                this.subject = MailUtils.decodeText(this.message.getSubject());
            } catch (MessagingException e) {
                throw ExceptionUtils.throwSystemException(e.getMessage(), e);
            }
        }
        return this.subject;
    }

    private Date sendDate = null;

    /**
     * 获取发送日期
     *
     * @return
     */
    public Date getSendDate() {
        if (this.sendDate == null) {
            try {
                this.sendDate = this.message.getSentDate();
            } catch (MessagingException e) {
                throw ExceptionUtils.throwSystemException(e.getMessage(), e);
            }
        }
        return this.sendDate;
    }

    private String from = null;

    /**
     * 获取发件人
     *
     * @return
     */
    public String getFrom() {
        if (this.from == null) {
            try {
                Address[] address = this.message.getFrom();
                if (address.length == 0) {
                    this.from = "";
                } else {
                    InternetAddress firstAddress = ((InternetAddress) address[0]);
                    String person = firstAddress.getPersonal();
                    if (person == null) {
                        person = "";
                    }
                    String fromAddress = firstAddress.getAddress();
                    if (fromAddress == null) {
                        fromAddress = "";
                    }
                    this.from = person + "[" + fromAddress + "]";
                }
            } catch (MessagingException e) {
                throw ExceptionUtils.throwSystemException(e.getMessage(), e);
            }
        }
        return this.from;
    }

    private String messageID = null;

    /**
     * 获取消息id
     *
     * @return
     */
    public String getMessageID() {
        if (this.messageID == null) {
            try {
                this.messageID = this.message.getMessageID();
            } catch (MessagingException e) {
                throw ExceptionUtils.throwSystemException(e.getMessage(), e);
            }
        }
        return this.messageID;
    }

    private Boolean replySign = null;

    /**
     * 是否需要回执
     *
     * @return
     */
    public boolean getReplySign() {
        if (this.replySign == null) {
            try {
                String[] reply = this.message.getHeader("Disposition-Notification-To");
                this.replySign = reply != null;
            } catch (MessagingException ignored) {
                this.replySign = false;
            }
        }
        return this.replySign;
    }




}
