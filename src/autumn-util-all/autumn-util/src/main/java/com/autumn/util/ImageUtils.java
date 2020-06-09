package com.autumn.util;

import com.autumn.exception.ExceptionUtils;
import org.apache.commons.io.IOUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * 图像帮助
 *
 * @Description TODO
 * @Author 老码农
 * @Date 2019-08-08 20:19
 */
public class ImageUtils {

    /**
     * 默认图片格式
     */
    public static final String DEFAULT_IMAGE_FORMAT = "jpg";

    /**
     * 默认编码
     */
    public static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;

    /**
     * 将图片编码
     *
     * @param image       图片
     * @param imageformat 图片格式
     * @return
     */
    public static String toBase64(Image image, String imageformat) {
        return toBase64(image, imageformat, DEFAULT_CHARSET);
    }

    /**
     * 将图片转为64编码
     *
     * @param image       图片
     * @param imageformat 图片格式
     * @param charset     编码
     * @return
     */
    public static String toBase64(Image image, String imageformat, Charset charset) {
        ExceptionUtils.checkNotNull(image, "image");
        if (StringUtils.isNullOrBlank(imageformat)) {
            imageformat = DEFAULT_IMAGE_FORMAT;
        }
        RenderedImage renderedImage;
        if (image instanceof RenderedImage) {
            renderedImage = (RenderedImage) image;
        } else {
            int width = image.getWidth(null);
            int height = image.getHeight(null);
            BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            Graphics g = bufferedImage.getGraphics();
            g.drawImage(image, 0, 0, width, height, null);
            g.dispose();
            renderedImage = bufferedImage;
        }
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            ImageIO.write(renderedImage, imageformat, outputStream);
            String value = Base64Utils.encodeToString(outputStream.toByteArray(), charset);
            return value.trim();
        } catch (IOException e) {
            throw ExceptionUtils.throwValidationException(e.getMessage(), e);
        } finally {
            IOUtils.closeQuietly(outputStream);
        }
    }

    /**
     * 将Base64转为图片
     *
     * @param base64 图片64编码
     * @return
     */
    public static BufferedImage formBase64(String base64) {
        return formBase64(base64, DEFAULT_CHARSET);
    }

    /**
     * 将Base64转为图片
     *
     * @param base64  图片64编码
     * @param charset 编码
     * @return
     */
    public static BufferedImage formBase64(String base64, Charset charset) {
        ExceptionUtils.checkNotNullOrBlank(base64, "base64");
        try {
            byte[] bytes = Base64Utils.decodeFromString(base64.trim(), charset);
            ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);
            return ImageIO.read(inputStream);
        } catch (IOException e) {
            throw ExceptionUtils.throwValidationException(e.getMessage(), e);
        }
    }

    /**
     * 转换图片
     *
     * @param image 图片
     * @return
     */
    public static BufferedImage convertBufferedImage(Image image) {
        ExceptionUtils.checkNotNull(image, "image");
        if (image instanceof BufferedImage) {
            return (BufferedImage) image;
        }
        int width = image.getWidth(null);
        int height = image.getHeight(null);
        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics g = bufferedImage.getGraphics();
        g.drawImage(image, 0, 0, width, height, null);
        g.dispose();
        return bufferedImage;
    }

    /**
     * 缩放图片
     *
     * @param image     源图
     * @param height    目标高度
     * @param width     目标宽度
     * @param hasFiller 比例不对时是否需要补白：true为补白; false为不补白;
     */
    public static BufferedImage scale(Image image, int height, int width, boolean hasFiller) {
        ExceptionUtils.checkNotNull(image, "image");
        BufferedImage srcImage = convertBufferedImage(image);
        double ratio; // 缩放比例
        Image destImage =  srcImage.getScaledInstance(width, height, BufferedImage.SCALE_SMOOTH);
        // 计算比例
        if (srcImage.getHeight(null) > height) {
            if (srcImage.getHeight(null) > srcImage.getWidth(null)) {
                ratio = (new Integer(height)).doubleValue() / srcImage.getHeight(null);
            } else {
                ratio = (new Integer(width)).doubleValue() / srcImage.getWidth(null);
            }
            AffineTransformOp op = new AffineTransformOp(AffineTransform.getScaleInstance(ratio, ratio), null);
            destImage = op.filter(srcImage, null);
        } else if (srcImage.getWidth(null) > width) {
            if (srcImage.getHeight(null) > srcImage.getWidth(null)) {
                ratio = (new Integer(height)).doubleValue() / srcImage.getHeight(null);
            } else {
                ratio = (new Integer(width)).doubleValue() / srcImage.getWidth(null);
            }
            AffineTransformOp op = new AffineTransformOp(AffineTransform.getScaleInstance(ratio, ratio), null);
            destImage = op.filter(srcImage, null);
        }
        // 补白
        if (hasFiller) {
            BufferedImage temp = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            Graphics2D graphic = temp.createGraphics();
            graphic.setColor(Color.white);
            graphic.fillRect(0, 0, width, height);
            if (width == destImage.getWidth(null)) {
                graphic.drawImage(destImage, 0, (height - destImage.getHeight(null)) / 2,
                        destImage.getWidth(null), destImage.getHeight(null), Color.white, null);
            }
            else {
                graphic.drawImage(destImage, (width - destImage.getWidth(null)) / 2, 0,
                        destImage.getWidth(null), destImage.getHeight(null),
                        Color.white, null);
            }
            graphic.dispose();
            destImage = temp;
        }
        return convertBufferedImage(destImage);
    }
}
