package com.autumn.word;

import com.autumn.word.impl.AbstractReplaceWordContentEvaluate;
import com.autumn.word.impl.XWPFUtils;
import org.apache.poi.xwpf.usermodel.*;

/**
 * TODO
 *
 * @Description TODO
 * @Author 老码农
 * @Date 2019-07-03 1:00
 */
class CustomWordContentEvaluate extends AbstractReplaceWordContentEvaluate {

    @Override
    public String getBeginMark() {
        return "@{";
    }

    @Override
    public String getEndMark() {
        return "}";
    }

    @Override
    protected void evaluateParagraph(WordSession session, String evlExpression, XWPFRun formatRun, EvaluateParagraphToken token) {
        int beginIndex = token.getBeginIndex() + 1;
        for (int i = 1; i <= 10; i++) {
            XWPFRun newRow = token.getPara().insertNewRun(beginIndex);
            XWPFUtils.copyRun(formatRun, () -> newRow);
            newRow.setText("工" + i);
            newRow.addCarriageReturn();
            beginIndex++;
        }
    }

}
