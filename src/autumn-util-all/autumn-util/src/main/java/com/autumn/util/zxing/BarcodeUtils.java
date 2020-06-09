package com.autumn.util.zxing;

import com.autumn.exception.ExceptionUtils;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import java.awt.image.BufferedImage;
import java.nio.charset.Charset;

/**
 * 条形码帮助
 *
 * @Description TODO
 * @Author 老码农
 * @Date 2019-08-08 18:24
 */
public abstract class BarcodeUtils extends AbstractCode {

    /**
     * ean13的最小宽度
     */
    private static final int ENA_13_MIN_WIDTH = 98;

    /**
     * 编码
     *
     * @param barcodeFormat 编码格式
     * @param content       内容
     * @param width         宽度
     * @param height        高度
     * @return
     */
    public static BufferedImage encode(BarcodeFormat barcodeFormat, String content, int width, int height) {
        return encode(barcodeFormat, content, width, height, DEFAULT_CHARSET);
    }

    /**
     * 编码
     *
     * @param barcodeFormat 编码格式
     * @param content       内容
     * @param width         宽度
     * @param height        高度
     * @param charset       编码
     * @return
     */
    public static BufferedImage encode(BarcodeFormat barcodeFormat, String content, int width, int height, Charset charset) {
        ExceptionUtils.checkNotNull(barcodeFormat, "barcodeFormat");
        if (barcodeFormat.equals(BarcodeFormat.EAN_13)) {
            width = Math.max(width, ENA_13_MIN_WIDTH);
        }
        ExceptionUtils.checkNotNullOrBlank(content, "content");
        if (width <= 0) {
            ExceptionUtils.throwAutumnException("width必须大小零。 ");
        }
        if (height <= 0) {
            ExceptionUtils.throwAutumnException("height必须大小零。 ");
        }
        BitMatrix bitMatrix;
        try {
            bitMatrix = new MultiFormatWriter().encode(content, barcodeFormat, width, height, createHints(charset));
        } catch (WriterException e) {
            throw ExceptionUtils.throwApplicationException(e.getMessage(), e);
        }
        BufferedImage qrCodeImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                qrCodeImage.setRGB(x, y, bitMatrix.get(x, y) ? RGB_BLACK : RGB_WHITE);
            }
        }
        return qrCodeImage;
    }

}
