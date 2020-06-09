package com.autumn.word.impl;

import com.autumn.word.EvaluateParagraphToken;
import com.autumn.word.WordContentEvaluate;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import java.util.List;

/**
 * Word 内容解析抽象
 *
 * @Description TODO
 * @Author 老码农
 * @Date 2019-07-03 12:11
 */
public abstract class AbstractWordContentEvaluate implements WordContentEvaluate {


    /**
     * 创建解析表达式结果
     *
     * @param token 标记
     * @return
     */
    protected EvaluateExpressionResult createEvaluateExpressionResult(EvaluateParagraphToken token) {
        if (token.getBeginIndex() >= 0 && token.getEndIndex() >= token.getBeginIndex()) {
            int a = token.getParaText().indexOf(token.getBeginMark());
            int b = token.getParaText().indexOf(token.getEndMark());
            String expression = token.getParaText().substring(a, b + 1);
            String evlExpression;
            if (expression.length() > token.getBeginMark().length() + token.getEndMark().length()) {
                evlExpression = expression.substring(token.getBeginMark().length(), expression.length() - 1);
            } else {
                evlExpression = "";
            }
            String beginRunText = token.getBeginRunText();
            String endRunText = token.getEndRunText();
            int index = beginRunText.lastIndexOf(token.getBeginMark());
            if (index >= 0) {
                beginRunText = beginRunText.substring(0, index);
            }
            index = endRunText.indexOf(token.getEndMark());
            if (index >= 0) {
                endRunText = endRunText.substring(index + token.getEndMark().length());
            }
            return new EvaluateExpressionResult(evlExpression, beginRunText, endRunText);
        }
        return null;
    }

    /**
     * 插入复制
     *
     * @param para
     * @param runs
     * @param runIndex
     * @param text
     */
    protected XWPFRun insertCopyRun(XWPFParagraph para, List<XWPFRun> runs, int runIndex, String text) {
        XWPFRun run = runs.get(runIndex);
        XWPFRun targetRun = XWPFUtils.copyRun(run, () -> {
            para.removeRun(runIndex);
            return para.insertNewRun(runIndex);
        });
        targetRun.setText(text);
        return targetRun;
    }
}
