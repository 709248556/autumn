package com.autumn.word;

import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFTable;

/**
 * Work 内容解析
 *
 * @Description TODO
 * @Author 老码农
 * @Date 2019-07-03 0:13
 */
public interface WordContentEvaluate {

    /**
     * 解析常规段落
     *
     * @param session 会话
     * @param para    段落
     */
    void evaluateGeneralParagraph(WordSession session, XWPFParagraph para);

    /**
     * 解析表格
     *
     * @param session 会话
     * @param table   表格
     */
    void evaluateTable(WordSession session, XWPFTable table);
}
