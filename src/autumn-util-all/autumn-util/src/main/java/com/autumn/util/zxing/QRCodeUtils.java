package com.autumn.util.zxing;

import com.autumn.exception.ExceptionUtils;
import com.autumn.util.ImageUtils;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.nio.charset.Charset;

/**
 * 二维码帮助工具
 *
 * @Description TODO
 * @Author 老码农
 * @Date 2019-08-08 12:01
 */
public abstract class QRCodeUtils extends AbstractCode {

    /**
     * 二维码默认尺寸
     */
    public static final int DEFAULT_QRCODE_SIZE = 300;

    /**
     * 默认内部图片尺寸
     */
    public static final int DEFAULT_INSIDE_IMAGE_SIZE = 64;

    private static final int FRAME_WIDTH = 2;


    /**
     * 编码
     *
     * @param content 二维码内容
     * @return 返回二维码的 BufferedImage 对象
     */
    public static BufferedImage encode(String content) {
        return encode(content, DEFAULT_QRCODE_SIZE, null, DEFAULT_INSIDE_IMAGE_SIZE);
    }

    /**
     * 编码
     *
     * @param content    二维码内容
     * @param qrCodeSize 二维码大小(高度与宽度)
     * @return 返回二维码的 BufferedImage 对象
     */
    public static BufferedImage encode(String content, int qrCodeSize) {
        return encode(content, qrCodeSize, null, DEFAULT_INSIDE_IMAGE_SIZE);
    }

    /**
     * 编码
     *
     * @param content 二维码内容
     * @param image   图片
     * @return 返回二维码的 BufferedImage 对象
     */
    public static BufferedImage encode(String content, Image image) {
        return encode(content, DEFAULT_QRCODE_SIZE, image, DEFAULT_INSIDE_IMAGE_SIZE);
    }

    /**
     * 编码
     *
     * @param content    二维码内容
     * @param qrCodeSize 二维码大小(高度与宽度)
     * @param image      图片
     * @param imageSize  图片大小(高度与宽度)
     * @return 返回二维码的 BufferedImage 对象
     */
    public static BufferedImage encode(String content, int qrCodeSize, Image image, int imageSize) {
        return encode(content, qrCodeSize, image, imageSize, DEFAULT_CHARSET);
    }

    /**
     * 编码
     *
     * @param content    二维码内容
     * @param qrCodeSize 二维码大小(高度与宽度)
     * @param image      图片
     * @param imageSize  图片大小(高度与宽度)
     * @param charset    编码
     * @return 返回二维码的 BufferedImage 对象
     */
    public static BufferedImage encode(String content, int qrCodeSize, Image image, int imageSize, Charset charset) {
        ExceptionUtils.checkNotNullOrBlank(content, "content");
        if (qrCodeSize <= 0) {
            ExceptionUtils.throwAutumnException("二维码尽寸必须大小零。 ");
        }
        if (imageSize <= 0) {
            ExceptionUtils.throwAutumnException("图片尽寸必须大小零。 ");
        }
        if (imageSize >= qrCodeSize) {
            ExceptionUtils.throwAutumnException("图片尽寸必须小于二维码尽寸。 ");
        }
        BitMatrix matrix;
        try {
            matrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, qrCodeSize, qrCodeSize, createHints(charset));
        } catch (WriterException e) {
            throw ExceptionUtils.throwApplicationException(e.getMessage(), e);
        }
        int width = matrix.getWidth();
        int height = matrix.getHeight();
        if (image == null) {
            BufferedImage qrCodeImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    qrCodeImage.setRGB(x, y, matrix.get(x, y) ? RGB_BLACK : RGB_WHITE);
                }
            }
            return qrCodeImage;
        }
        int[] pixels = createImagePixels(matrix, image, imageSize);
        BufferedImage qrCodeImage = new BufferedImage(width, height,
                BufferedImage.TYPE_INT_RGB);
        qrCodeImage.getRaster().setDataElements(0, 0, width, height, pixels);
        return qrCodeImage;
    }

    /**
     * 创建图片像素
     *
     * @param matrix
     * @param image
     * @param imageSize
     * @return
     */
    private static int[] createImagePixels(BitMatrix matrix, Image image, int imageSize) {
        int width = matrix.getWidth();
        int height = matrix.getHeight();
        int halfW = matrix.getWidth() / 2;
        int halfH = matrix.getHeight() / 2;
        int half_width = imageSize / 2;
        BufferedImage scaleImage = ImageUtils.scale(image, imageSize, imageSize, true);
        int[][] srcPixels = new int[imageSize][imageSize];
        int[] pixels = new int[width * height];
        for (int i = 0; i < scaleImage.getWidth(); i++) {
            for (int j = 0; j < scaleImage.getHeight(); j++) {
                srcPixels[i][j] = scaleImage.getRGB(i, j);
            }
        }
        int left = halfW - half_width;
        int top = halfH - half_width;
        int centreX = halfW + half_width;
        int centreY = halfH + half_width;
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int index = y * width + x;
                // 读取图片
                if (x > left && x < centreX && y > top && y < centreY) {
                    pixels[index] = srcPixels[x - halfW + half_width][y - halfH + half_width];
                }
                // 在图片四周形成边框
                else if ((x > left - FRAME_WIDTH && x < left + FRAME_WIDTH && y > top - FRAME_WIDTH && y < centreY + FRAME_WIDTH)
                        || (x > centreX - FRAME_WIDTH && x < centreX + FRAME_WIDTH && y > top - FRAME_WIDTH && y < centreY + FRAME_WIDTH)
                        || (x > left - FRAME_WIDTH && x < centreX + FRAME_WIDTH && y > top - FRAME_WIDTH && y < top + FRAME_WIDTH)
                        || (x > left - FRAME_WIDTH && x < centreX + FRAME_WIDTH && y > centreY - FRAME_WIDTH && y < centreY + FRAME_WIDTH)) {
                    pixels[index] = RGB_WHITE;
                } else {
                    pixels[index] = matrix.get(x, y) ? RGB_BLACK : RGB_WHITE;
                }
            }
        }
        return pixels;
    }
}
