package com.autumn.word;

import org.apache.poi.xwpf.usermodel.XWPFDocument;

import com.autumn.word.pdf.PdfProperties;

/**
 * Word 解析选项
 * 
 * @author 老码农 2019-04-24 02:03:22
 */
public interface WordEvaluateWithOptional {

	/**
	 * 获取文档
	 * 
	 * @return
	 */
	XWPFDocument getDocument();

	/**
	 * 转为文档
	 * 
	 * @return
	 */
	WordEvaluateWithOptionalTarget toDocument();

	/**
	 * 转为Pdf
	 * 
	 * @return
	 */
	WordEvaluateWithOptionalTarget toPdf();

	/**
	 * 转为Pdf
	 * 
	 * @param pdfProperties
	 *            属性
	 * @return
	 */
	WordEvaluateWithOptionalTarget toPdf(PdfProperties pdfProperties);
}
