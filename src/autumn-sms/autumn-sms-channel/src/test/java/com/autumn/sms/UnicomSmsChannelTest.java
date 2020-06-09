package com.autumn.sms;

import com.autumn.sms.channel.DefaultSmsMessage;
import com.autumn.sms.channel.SmsMessage;
import com.autumn.sms.channel.unicom.UnicomSmsChannel;
import com.autumn.sms.channel.unicom.UnicomSmsProperties;
import org.junit.Test;

/**
 * 测试
 * <p>
 * </p>
 *
 * @description TODO
 * @author: 老码农
 * @create: 2020-03-02 10:43
 **/
public class UnicomSmsChannelTest {

    @Test
    public void sendTest() throws Exception {
        UnicomSmsProperties properties = new UnicomSmsProperties();
        properties.setAccount("aa");
        properties.setPassword("acbd1324!");
        properties.setAgent("a");
        properties.setSign("【云浮石检】");
        properties.setServiceUrl("http://58.249.48.146:8080");

        UnicomSmsChannel channel = new UnicomSmsChannel(properties);
        SmsMessage msg = new DefaultSmsMessage();
        msg.setPhoneNumbers("13000000000");
        msg.setTemplateCode("001");
        msg.setMessageContent("您的验证码：1256666");
        channel.send(msg);
    }
}
