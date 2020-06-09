package com.autumn.util;


import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;

/**
 * 图片验证码
 *
 * @Description TODO
 * @Author 老码农
 * @Date 2019-06-19 2:54
 */
public class ImageCaptcha {

    /**
     * 默认宽度
     */
    public static final int DEFAULT_WIDTH = 160;

    /**
     * 默认高度
     */
    public static final int DEFAULT_HEIGHT = 40;

    /**
     * 默认图片格式
     */
    public static final String DEFAULT_IMAGE_FORMAT = "jpg";

    // 图片的宽度。
    private final int width;

    // 图片的高度。
    private final int height;

    // 验证码字符个数
    private final int codeCount;

    // 验证码干扰线数
    private final int lineCount;


    private final String imageFormat;

    // 验证码
    private String code = null;

    private static final int RGB_MAX = 255;

    /**
     * 种子(无O与0)
     */
    private static char[] CODE_SEQUENCE = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
            '1', '2', '3', '4', '5', '6', '7', '8', '9',
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};

    private BufferedImage bufferedImage;

    /**
     * 实例化
     *
     * @param codeCount 验证码数量
     * @param lineCount 干扰线数量
     */
    public ImageCaptcha(int codeCount, int lineCount) {
        this(DEFAULT_WIDTH, DEFAULT_HEIGHT, codeCount, lineCount);
    }

    /**
     * 实例化
     *
     * @param width     宽度
     * @param height    高度
     * @param codeCount 验证码数量
     * @param lineCount 干扰线数量
     */
    public ImageCaptcha(int width, int height, int codeCount, int lineCount) {
        this(width, height, codeCount, lineCount, DEFAULT_IMAGE_FORMAT);
    }

    /**
     * 实例化
     *
     * @param width       宽度
     * @param height      高度
     * @param codeCount   验证码数量
     * @param lineCount   干扰线数量
     * @param imageFormat 图片格式(jpg、png)
     */
    public ImageCaptcha(int width, int height, int codeCount, int lineCount, String imageFormat) {
        this.width = width;
        this.height = height;
        this.codeCount = codeCount;
        this.lineCount = lineCount;
        this.imageFormat = StringUtils.isNullOrBlank(imageFormat) ? DEFAULT_IMAGE_FORMAT : imageFormat;
        this.generate();
    }

    /**
     * 生成
     */
    public void generate() {
        Graphics2D graphics2D = null;
        try {
            //字符所在x坐标
            int x;
            //字体高度
            int fontHeight;
            //字符所在y坐标
            int codeY;
            int red;
            int green;
            int blue;
            x = width / (codeCount + 2);
            fontHeight = height - 2;
            codeY = height - 4;
            bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_BGR);
            graphics2D = bufferedImage.createGraphics();
            Random random = new Random();
            graphics2D.setColor(Color.WHITE);
            graphics2D.fillRect(0, 0, width, height);
            Font sourceFont = FontUtils.getFontOrDefault("Fixedays");
            if (sourceFont != null) {
                Font font = new Font(sourceFont.getFontName(), Font.PLAIN, fontHeight);
                graphics2D.setFont(font);
            }
            for (int i = 0; i < lineCount; i++) {
                //x轴第一个点的位置
                int x1 = random.nextInt(width);
                //y轴第一个点的位置
                int y1 = random.nextInt(height);
                //x轴第二个点的位置
                int x2 = x1 + random.nextInt(width >> 2);
                //y轴第二个点的位置
                int y2 = y1 + random.nextInt(height >> 2);

                red = random.nextInt(RGB_MAX);
                green = random.nextInt(RGB_MAX);
                blue = random.nextInt(RGB_MAX);

                graphics2D.setColor(new Color(red, green, blue));
                graphics2D.drawLine(x1, y1, x2, y2);
            }
            StringBuffer randomCode = new StringBuffer(codeCount);
            for (int i = 0; i < codeCount; i++) {
                String strRand = String.valueOf(CODE_SEQUENCE[random.nextInt(CODE_SEQUENCE.length)]);
                red = random.nextInt(RGB_MAX);
                green = random.nextInt(RGB_MAX);
                blue = random.nextInt(RGB_MAX);
                graphics2D.setColor(new Color(red, green, blue));
                graphics2D.drawString(strRand, (i + 1) * x, codeY);
                randomCode.append(strRand);
            }
            code = randomCode.toString();
        } finally {
            if (graphics2D != null) {
                graphics2D.dispose();
            }
        }
    }

    /**
     * 写入路径
     *
     * @param path
     * @throws IOException
     */
    public void write(String path) throws IOException {
        OutputStream outputStream = new FileOutputStream(path);
        this.write(outputStream);
        outputStream.flush();
        outputStream.close();
    }

    /**
     * 写入输出
     *
     * @param outputStream
     * @throws IOException
     */
    public void write(OutputStream outputStream) throws IOException {
        ImageIO.write(this.getImage(), this.getImageFormat(), outputStream);
    }

    /**
     * 获取图片
     *
     * @return
     */
    public BufferedImage getImage() {
        return this.bufferedImage;
    }

    /**
     * 获取图片格式
     *
     * @return
     */
    public String getImageFormat() {
        return imageFormat;
    }

    /**
     * 获取验证码
     *
     * @return
     */
    public String getCode() {
        return this.code;
    }

    /**
     * 转换为 Base64 编码
     *
     * @return
     */
    public String toBase64() {
        return ImageUtils.toBase64(this.getImage(), this.getImageFormat());
    }
}
