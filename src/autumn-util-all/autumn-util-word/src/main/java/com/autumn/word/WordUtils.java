package com.autumn.word;

import java.io.IOException;
import java.io.InputStream;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.xmlbeans.XmlOptions;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTBody;

import com.autumn.exception.ExceptionUtils;

/**
 * Word 帮助工具
 * 
 * @author 老码农 2019-04-22 18:33:53
 */
public class WordUtils {

	/**
	 * 解析文档
	 * 
	 * @param model
	 *            模型
	 * @param inputStream
	 *            输入流
	 * @return
	 * @throws IOException
	 *             流异常
	 */
	public static XWPFDocument evaluateDocument(Object model, InputStream inputStream) throws IOException {
		WordSession session = new WordSession(model);
		return session.evaluate(inputStream);
	}

	/**
	 * 解析文档
	 * 
	 * @param model
	 *            模型
	 * @param filePath
	 *            文件路径
	 * @return
	 * @throws IOException
	 *             流异常
	 */
	public static XWPFDocument evaluateDocument(Object model, String filePath) throws IOException {
		WordSession session = new WordSession(model);
		return session.evaluate(filePath);
	}

	/**
	 * 解析文档
	 * 
	 * @param model
	 *            模型
	 * @param document
	 *            文档
	 * @return
	 * @throws IOException
	 *             流异常
	 */
	public static XWPFDocument evaluateDocument(Object model, XWPFDocument document) {
		WordSession session = new WordSession(model);
		return session.evaluate(document, EvaluateOptions.DEFAULT);
	}

	/**
	 * 合并文档
	 * 
	 * @param mainDocument
	 *            主文档
	 * @param appendDocument
	 *            追加文件
	 * @param isNewPage
	 *            是否在新的页添加新文档
	 */
	public static void mergeDocument(XWPFDocument mainDocument, XWPFDocument appendDocument, boolean isNewPage)
			throws Exception {
		ExceptionUtils.checkNotNull(mainDocument, "mainDocument");
		ExceptionUtils.checkNotNull(appendDocument, "appendDocument");
		if (isNewPage) {
			XWPFParagraph paragraph = mainDocument.createParagraph();
			paragraph.setPageBreak(true);
		}
		appendDocumentBody(mainDocument.getDocument().getBody(), appendDocument.getDocument().getBody());
	}

	/**
	 * 添加文档体
	 * 
	 * @param mainBody
	 *            主文件体
	 * @param appendBody
	 *            追加文件体
	 * @throws Exception
	 */
	private static void appendDocumentBody(CTBody mainBody, CTBody appendBody) throws Exception {
		XmlOptions optionsOuter = new XmlOptions();
		optionsOuter.setSaveOuter();
		String appendString = appendBody.xmlText(optionsOuter);
		String srcString = mainBody.xmlText();
		String prefix = srcString.substring(0, srcString.indexOf(">") + 1);
		String mainPart = srcString.substring(srcString.indexOf(">") + 1, srcString.lastIndexOf("<"));
		String sufix = srcString.substring(srcString.lastIndexOf("<"));
		String addPart = appendString.substring(appendString.indexOf(">") + 1, appendString.lastIndexOf("<"));
		CTBody makeBody = CTBody.Factory.parse(prefix + mainPart + addPart + sufix);
		mainBody.set(makeBody);
	}
}
