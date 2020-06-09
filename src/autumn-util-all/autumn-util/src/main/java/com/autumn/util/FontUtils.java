package com.autumn.util;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

/**
 * 字体帮助
 *
 * @Description TODO
 * @Author 老码农
 * @Date 2019-06-19 3:23
 */
public class FontUtils {

    private static final Map<String, Font> FONT_MAP = new HashMap<>();

    /**
     * 默认字体
     */
    public static final Font DEFAULT_FONT;

    static {
        Font temp;
        try {
            GraphicsEnvironment environment = GraphicsEnvironment.getLocalGraphicsEnvironment();
            Font[] fonts = environment.getAllFonts();
            for (Font font : fonts) {
                if (font.getFontName() != null) {
                    FONT_MAP.put(font.getFontName().toUpperCase(), font);
                }
            }
            Font defaultFont = getMapDefaultFont("微软雅黑", "Microsoft Yahei", "宋体", "SimSun", "Monaco", "Arial", "Calibri", "Calibri");
            if (defaultFont == null && fonts.length > 0) {
                defaultFont = fonts[0];
            }
            temp = defaultFont;
        } catch (Exception err) {
            temp = null;
        }
        DEFAULT_FONT = temp;
    }

    private static Font getMapDefaultFont(String... names) {
        for (String name : names) {
            Font font = FONT_MAP.get(name.toUpperCase());
            if (font != null) {
                return font;
            }
        }
        return null;
    }

    /**
     * 获取字体
     *
     * @param fontName 字体名称
     * @return
     */
    public static Font getFont(String fontName) {
        return FontUtils.getFontOrDefault(fontName, null);
    }

    /**
     * 获取字体或默认
     *
     * @param fontName 字体名称
     * @return
     */
    public static Font getFontOrDefault(String fontName) {
        return FontUtils.getFontOrDefault(fontName, DEFAULT_FONT);
    }

    /**
     * 获取字体
     *
     * @param fontName    字体名称
     * @param defaultFont 默认字体
     * @return
     */
    public static Font getFontOrDefault(String fontName, Font defaultFont) {
        assert fontName != null;
        Font font = FONT_MAP.get(fontName.toUpperCase());
        return font != null ? font : defaultFont;
    }

}