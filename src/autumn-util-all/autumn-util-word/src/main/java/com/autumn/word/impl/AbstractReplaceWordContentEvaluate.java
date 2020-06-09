package com.autumn.word.impl;

import com.autumn.util.StringUtils;
import com.autumn.word.EvaluateParagraphToken;
import com.autumn.word.WordSession;
import org.apache.poi.xwpf.usermodel.*;

import java.util.List;

/**
 * 替换工作内容解析
 *
 * @Description 实现内容表达式的替换
 * @Author 老码农
 * @Date 2019-07-03 10:14
 */
public abstract class AbstractReplaceWordContentEvaluate extends AbstractWordContentEvaluate {

    /**
     * 获取开始标记
     *
     * @return
     */
    public abstract String getBeginMark();

    /**
     * 获取结束标记
     *
     * @return
     */
    public abstract String getEndMark();

    @Override
    public void evaluateGeneralParagraph(WordSession session, XWPFParagraph para) {
        XWPFUtils.evaluateParagraph(para, this.getBeginMark(), this.getEndMark(), (token) -> evaluateParagraphRun(session, token));
    }

    /**
     * 解析
     *
     * @param session 会话
     * @param token   标记
     * @return
     */
    private boolean evaluateParagraphRun(WordSession session, EvaluateParagraphToken token) {
        EvaluateExpressionResult result = this.createEvaluateExpressionResult(token);
        if (result == null) {
            return false;
        }
        XWPFRun beginRun = token.getRuns().get(token.getBeginIndex());
        XWPFRun formatRun = token.getPara().createRun();
        XWPFUtils.copyRun(beginRun, () -> formatRun);
        beginRun = insertCopyRun(token.getPara(), token.getRuns(), token.getBeginIndex(), result.getBeginRunText());
        beginRun.addCarriageReturn();
        for (int i = token.getBeginIndex() + 1; i <= token.getEndIndex(); i++) {
            token.getPara().removeRun(i);
        }
        if (!StringUtils.isNullOrBlank(result.getEndRunText())) {
            XWPFRun endRow = token.getPara().insertNewRun(token.getBeginIndex());
            XWPFUtils.copyRun(formatRun, () -> endRow);
            endRow.setText(result.getEndRunText());
            endRow.addCarriageReturn();
        }
        this.evaluateParagraph(session, result.getEvlExpression(), formatRun, token);
        token.getPara().removeRun(token.getPara().getRuns().size() - 1);
        return true;
    }

    /**
     * 解析段落
     *
     * @param session       会话
     * @param evlExpression 表达式
     * @param formatRun     格式
     * @param token         标记
     */
    protected abstract void evaluateParagraph(WordSession session, String evlExpression, XWPFRun formatRun, EvaluateParagraphToken token);


    @Override
    public void evaluateTable(WordSession session, XWPFTable table) {
        List<XWPFTableRow> rows = table.getRows();
        for (XWPFTableRow row : rows) {
            for (XWPFTableCell cell : row.getTableCells()) {
                List<XWPFParagraph> paragraphs = cell.getParagraphs();
                for (XWPFParagraph para : paragraphs) {
                    this.evaluateGeneralParagraph(session, para);
                }
            }
        }
    }
}
