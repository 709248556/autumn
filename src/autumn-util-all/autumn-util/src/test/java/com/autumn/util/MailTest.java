package com.autumn.util;

import com.autumn.util.mail.MailSendMessage;
import com.autumn.util.mail.MailUtils;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;


/**
 * TODO
 *
 * @Description TODO
 * @Author 老码农
 * @Date 2019-10-09 20:47
 */
public class MailTest {


    @Test
    public void test1() throws Exception {
        MailSendMessage message = new MailSendMessage();
        message.setUser("893@163.com");
        message.setPassword("XVUOCKHXOAGIQQFV");
        message.setSmtpHost("smtp.163.com");
        message.setTo("5457@qq.com");
        message.setPort(25);
        message.setSubject("测试");
        message.setContent("测试邮件");
        // message.addParts("E:\\公司信息\\妙常乐宣传资料.pdf");

        FileInputStream fs = new FileInputStream("E:\\公司信息\\妙常乐宣传资料.pdf");
        message.addParts("妙常乐宣传资料.pdf", readArray(fs));

        MailUtils.sendMail(message);
    }

    private byte[] readArray(InputStream inputStream) throws IOException {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        byte[] bytes = new byte[4096];
        int len;
        while ((len = inputStream.read(bytes, 0, bytes.length)) != -1) {
            output.write(bytes, 0, len);
        }
        return output.toByteArray();
    }
}
