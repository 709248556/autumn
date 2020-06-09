package com.autumn.util;

import com.autumn.util.zxing.BarcodeUtils;
import com.autumn.util.zxing.QRCodeUtils;
import com.google.zxing.BarcodeFormat;
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
 * @Date 2019-08-08 18:33
 */
public class BarcodeTest {

    @Test
    public void test1() throws IOException {
        BufferedImage image = BarcodeUtils.encode(BarcodeFormat.CODE_39, "JNC589664569", 250, 60);
        ImageIO.write(image, "jpg", new FileOutputStream("D:\\javaTest\\CODE_39.jpg"));
        String eq = QRCodeUtils.decodeString(image);
        System.out.println(eq);
    }

    @Test
    public void test2() throws IOException {
        BufferedImage image = BarcodeUtils.encode(BarcodeFormat.EAN_13, "6923450657713", 96, 30);
        ImageIO.write(image, "jpg", new FileOutputStream("D:\\javaTest\\EAN_13.jpg"));

        // String eq = QRCodeUtils.decodeString(image);
        //System.out.println(eq);
    }
}
