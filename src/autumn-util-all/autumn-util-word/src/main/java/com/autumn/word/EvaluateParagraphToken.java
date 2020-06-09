package com.autumn.word;

import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import java.util.List;

/**
 * 解析段标记
 *
 * @Description TODO
 * @Author 老码农
 * @Date 2019-07-02 23:40
 */
public class EvaluateParagraphToken {

    private final XWPFParagraph para;
    private final List<XWPFRun> runs;
    private final String paraText;
    private final int beginIndex;
    private final String beginRunText;
    private final int endIndex;
    private final String endRunText;
    private final String beginMark;
    private final String endMark;

    public EvaluateParagraphToken(XWPFParagraph para,
                                  List<XWPFRun> runs,
                                  String paraText,
                                  int beginIndex,
                                  String beginRunText,
                                  int endIndex,
                                  String endRunText,
                                  String beginMark,
                                  String endMark) {
        this.para = para;
        this.runs = runs;
        this.paraText = paraText;
        this.beginIndex = beginIndex;
        this.beginRunText = beginRunText;
        this.endIndex = endIndex;
        this.endRunText = endRunText;
        this.beginMark = beginMark;
        this.endMark = endMark;
    }

    /**
     * 获取Para
     *
     * @return
     */
    public XWPFParagraph getPara() {
        return para;
    }

    /**
     * 获取运行集合
     *
     * @return
     */
    public List<XWPFRun> getRuns() {
        return runs;
    }

    /**
     * 获取 Para 文本
     *
     * @return
     */
    public String getParaText() {
        return paraText;
    }

    /**
     * 获取开始索引
     *
     * @return
     */
    public int getBeginIndex() {
        return beginIndex;
    }

    /**
     * 获取开始文本
     *
     * @return
     */
    public String getBeginRunText() {
        return beginRunText;
    }

    /**
     * 获取结束索引
     *
     * @return
     */
    public int getEndIndex() {
        return endIndex;
    }

    /**
     * 获取结束规则文本
     *
     * @return
     */
    public String getEndRunText() {
        return endRunText;
    }

    /**
     * 获取开始标记
     *
     * @return
     */
    public String getBeginMark() {
        return beginMark;
    }

    /**
     * 获取结束标记
     *
     * @return
     */
    public String getEndMark() {
        return endMark;
    }
}
