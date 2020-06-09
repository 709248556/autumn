package com.autumn.word;

import com.autumn.word.pdf.PdfProperties;

import java.io.IOException;

/**
 * Word 选项
 *
 * @author 老码农 2019-04-24 01:34:11
 */
public interface WordWithOptional {

    /**
     * 解析
     *
     * @param obj 对象
     * @return
     * @throws IOException
     */
    WordEvaluateWithOptional evaluate(Object obj) throws IOException;

    /**
     * 解析
     *
     * @param obj     对象
     * @param options 选项
     * @return
     * @throws IOException
     */
    WordEvaluateWithOptional evaluate(Object obj, EvaluateOptions options) throws IOException;

    /**
     * 解析
     *
     * @param obj     对象
     * @param token   标记
     * @param options 选项
     * @return
     * @throws IOException
     */
    WordEvaluateWithOptional evaluate(Object obj, Token token, EvaluateOptions options) throws IOException;

    /**
     * 转为Pdf
     *
     * @return
     */
    WordEvaluateWithOptionalTarget toPdf();

    /**
     * 转为Pdf
     *
     * @param pdfProperties 属性
     * @return
     */
    WordEvaluateWithOptionalTarget toPdf(PdfProperties pdfProperties);

}
