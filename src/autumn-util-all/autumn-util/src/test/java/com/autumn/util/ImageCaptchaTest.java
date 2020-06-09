package com.autumn.util;

import org.junit.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * TODO
 *
 * @Description TODO
 * @Author 老码农
 * @Date 2019-08-08 17:06
 */
public class ImageCaptchaTest {

    /**
     *
     */
    @Test
    public void test1() throws IOException {
        ImageCaptcha captcha = new ImageCaptcha(4, 20);

        captcha.write(new FileOutputStream("D:\\javaTest\\图片验证码.jpg"));

        String a = ImageUtils.toBase64(captcha.getImage(), captcha.getImageFormat());

        System.out.println(a);

        BufferedImage image = ImageUtils.formBase64(a);

        String b = ImageUtils.toBase64(image, captcha.getImageFormat());

        System.out.println(b);

        ImageIO.write(image, "jpg", new FileOutputStream("D:\\javaTest\\图片验证码(1).jpg"));

        //org.junit.Assert.assertEquals(a, b);
    }
}
