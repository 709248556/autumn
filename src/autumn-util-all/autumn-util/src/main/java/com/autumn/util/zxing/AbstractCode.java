package com.autumn.util.zxing;

import com.autumn.exception.ExceptionUtils;
import com.google.zxing.*;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * 编码抽象
 *
 * @Description TODO
 * @Author 老码农
 * @Date 2019-08-08 18:13
 */
public abstract class AbstractCode {

    /**
     * 白色
     */
    public static final int RGB_WHITE = 0xFFFFFFFF;

    /**
     * 黑色
     */
    public static final int RGB_BLACK = 0xFF000000;

    /**
     * 默认编码
     */
    public static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;

    /**
     * 创建  Hints
     *
     * @param charset 编码
     * @return
     */
    protected static Map<EncodeHintType, Object> createHints(Charset charset) {
        if (charset == null) {
            charset = DEFAULT_CHARSET;
        }
        Map<EncodeHintType, Object> hints = new HashMap<>();
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        hints.put(EncodeHintType.CHARACTER_SET, charset.name());
        hints.put(EncodeHintType.MARGIN, 1);
        return hints;
    }

    /**
     * 解码返回字符
     *
     * @param image 图片
     * @return
     */
    public static String decodeString(Image image) {
        return decode(image).getText();
    }

    /**
     * 解码返回字符
     *
     * @param image   图片
     * @param charset 编码
     * @return
     */
    public static String decodeString(Image image, Charset charset) {
        return decode(image, charset).getText();
    }

    /**
     * 解码
     *
     * @param image 图片
     * @return
     */
    public static Result decode(Image image) {
        return decode(image, DEFAULT_CHARSET);
    }

    /**
     * 解码
     * <p>
     * 解析ENA_13会出错
     * </p>
     *
     * @param image   图片
     * @param charset 编码
     * @return
     */
    public static Result decode(Image image, Charset charset) {
        ExceptionUtils.checkNotNull(image, "image");
        if (charset == null) {
            charset = DEFAULT_CHARSET;
        }
        BufferedImage bufferedImage;
        if (image instanceof BufferedImage) {
            bufferedImage = (BufferedImage) image;
        } else {
            int width = image.getWidth(null);
            int height = image.getHeight(null);
            bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            Graphics g = bufferedImage.getGraphics();
            g.drawImage(image, 0, 0, width, height, null);
            g.dispose();
        }
        BufferedImageLuminanceSource source = new BufferedImageLuminanceSource(bufferedImage);
        Map<DecodeHintType, Object> hints = new HashMap<>();
        hints.put(DecodeHintType.CHARACTER_SET, charset.name());
        try {
            BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
            return new MultiFormatReader().decode(bitmap, hints);
        } catch (Exception e) {
            throw ExceptionUtils.throwValidationException("指定的图片无法识别的编码。", e);
        }
    }
}
