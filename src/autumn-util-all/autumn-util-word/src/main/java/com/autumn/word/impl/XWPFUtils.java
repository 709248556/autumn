package com.autumn.word.impl;

import com.autumn.word.EvaluateParagraphToken;
import com.autumn.word.WordEvaluateException;
import org.apache.poi.xwpf.usermodel.UnderlinePatterns;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * TODO
 *
 * @Description TODO
 * @Author 老码农
 * @Date 2019-07-02 23:18
 */
public class XWPFUtils {

    /**
     * 解析 XWPFParagraph
     *
     * @param para      段
     * @param beginMark 开始标识
     * @param endMark   结束标识
     */
    public static void evaluateParagraph(XWPFParagraph para, String beginMark, String endMark,
                                         Function<EvaluateParagraphToken, Boolean> evaluateResult) {
        List<XWPFRun> runs = new ArrayList<>(para.getRuns());
        StringBuilder sb = new StringBuilder();
        int beginIndex = -1;
        int endIndex = -1;
        String paraText = "";
        String beginRunText = "";
        String endRunText = "";
        EvaluateParagraphToken token;
        for (int i = 0; i < runs.size(); i++) {
            XWPFRun run = runs.get(i);
            String runText = run.toString();
            sb.append(runText);
            paraText = sb.toString();
            if (beginIndex == -1 && paraText.contains(beginMark)) {
                if (runText.contains(beginMark)) {
                    beginIndex = i;
                    beginRunText = runText;
                } else {
                    throw new WordEvaluateException(
                            String.format("包含的表达式  %s 格式出错，请记事本编辑公式，所在段落 %s。", paraText, para.getText()));
                }
            }
            if (endIndex == -1 && paraText.contains(endMark)) {
                endIndex = i;
                endRunText = runText;
            }
            token = new EvaluateParagraphToken(para, runs, paraText, beginIndex, beginRunText, endIndex, endRunText,
                    beginMark, endMark);
            boolean result = evaluateResult.apply(token);
            if (result) {
                beginIndex = -1;
                endIndex = -1;
                sb = new StringBuilder();
                beginRunText = "";
                endRunText = "";
            }
        }
        token = new EvaluateParagraphToken(para, runs, paraText, beginIndex, beginRunText, endIndex, endRunText,
                beginMark, endMark);
        evaluateResult.apply(token);
    }

    /**
     * 复制 Run
     *
     * @param sourceRun         源
     * @param targetRunSupplier 新
     */
    public static XWPFRun copyRun(XWPFRun sourceRun, Supplier<XWPFRun> targetRunSupplier) {
        // CTR ctr= sourceRun.getCTR();

        boolean isBold = sourceRun.isBold();
        String color = sourceRun.getColor();
        String fontFamily = sourceRun.getFontFamily();
        int fontSize = sourceRun.getFontSize();
        boolean isItalic = sourceRun.isItalic();
        UnderlinePatterns underline = sourceRun.getUnderline();

        boolean capitalized = sourceRun.isCapitalized();
        int characterSpacing = sourceRun.getCharacterSpacing();
        boolean doubleStrikethrough = sourceRun.isDoubleStrikeThrough();
        boolean embossed = sourceRun.isEmbossed();
        boolean imprinted = sourceRun.isImprinted();
        int kerning = sourceRun.getKerning();
        String lang = sourceRun.getLang();
        boolean shadow = sourceRun.isShadowed();
        boolean smallCaps = sourceRun.isSmallCaps();
        boolean strikeThrough = sourceRun.isStrikeThrough();
        int textScale = sourceRun.getTextScale();
        boolean vanish = sourceRun.isVanish();

        XWPFRun targetRun = targetRunSupplier.get();

        targetRun.setBold(isBold);
        if (color != null) {
            targetRun.setColor(color);
        }
        if (fontFamily != null) {
            targetRun.setFontFamily(fontFamily);
        }
        if (fontSize > 0) {
            targetRun.setFontSize(fontSize);
        }
        targetRun.setItalic(isItalic);
        if (underline != null) {
            targetRun.setUnderline(underline);
        }

        targetRun.setCapitalized(capitalized);
        targetRun.setCharacterSpacing(characterSpacing);
        targetRun.setDoubleStrikethrough(doubleStrikethrough);
        targetRun.setEmbossed(embossed);
        targetRun.setImprinted(imprinted);
        targetRun.setKerning(kerning);
        if (lang != null) {
            targetRun.setLang(lang);
        }
        targetRun.setShadow(shadow);
        targetRun.setSmallCaps(smallCaps);
        targetRun.setStrikeThrough(strikeThrough);
        targetRun.setTextScale(textScale);
        if (color != null) {
            targetRun.setUnderlineColor(color);
        }
        targetRun.setVanish(vanish);

        return targetRun;
    }
}
