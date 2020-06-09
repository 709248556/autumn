package com.autumn.util;

import com.autumn.util.zxing.QRCodeUtils;
import org.junit.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * TODO
 *
 * @Description TODO
 * @Author 老码农
 * @Date 2019-08-08 17:41
 */
public class QRCodeTest {

    @Test
    public void test1() throws IOException {

        BufferedImage image = QRCodeUtils.encode("https://www.baidu.com");
        ImageIO.write(image, "jpg", new FileOutputStream("D:\\javaTest\\二维码1.jpg"));

    }

    @Test
    public void test2() throws IOException {
        String value = "hotapp_1572573521";
        BufferedImage image = QRCodeUtils.encode(value);
        ImageIO.write(image, "jpg", new FileOutputStream("D:\\javaTest\\" + value + ".jpg"));

        String eq = QRCodeUtils.decodeString(image);
        System.out.println(eq);
       // org.junit.Assert.assertEquals(value, eq);
    }

    @Test
    public void logoTest1() throws IOException {
        String value = "妙常乐网络有限公司";

        BufferedImage logo = ImageIO.read(new FileInputStream("D:\\javaTest\\hotapp_1572573521.png"));

       // BufferedImage image = QRCodeUtils.encode(value, logo);
       // ImageIO.write(image, "jpg", new FileOutputStream("D:\\javaTest\\" + value + ".jpg"));

        String eq = QRCodeUtils.decodeString(logo);
        System.out.println(eq);
       // org.junit.Assert.assertEquals(value, eq);
    }

}
