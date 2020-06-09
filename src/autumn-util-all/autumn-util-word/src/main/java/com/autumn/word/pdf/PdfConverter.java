package com.autumn.word.pdf;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.poi.xwpf.usermodel.XWPFDocument;

/**
 * pdf 转换抽象
 * 
 * @author 老码农 2019-04-23 15:14:40
 */
public interface PdfConverter {

	/**
	 * 转为pdf
	 * 
	 * @param document
	 *            文档
	 * @param pdfFilePath
	 *            pdf文件路径
	 * @throws Exception
	 */
	void toPdf(XWPFDocument document, String pdfFilePath) throws Exception;

	/**
	 * 转为pdf
	 * 
	 * @param document
	 *            文档
	 * @param pdfFilePath
	 *            pdf文件路径
	 * @param properties
	 *            属性
	 * @throws Exception
	 */
	void toPdf(XWPFDocument document, String pdfFilePath, PdfProperties properties) throws Exception;

	/**
	 * 转为pdf
	 * 
	 * @param document
	 *            文档
	 * @param pdfFile
	 *            pdf文件
	 * @throws Exception
	 */
	void toPdf(XWPFDocument document, File pdfFile) throws Exception;

	/**
	 * 转为pdf
	 * 
	 * @param document
	 *            文档
	 * @param pdfFile
	 *            pdf文件
	 * @param properties
	 *            属性
	 * @throws Exception
	 */
	void toPdf(XWPFDocument document, File pdfFile, PdfProperties properties) throws Exception;

	/**
	 * 转为pdf
	 * 
	 * @param document
	 *            文档
	 * @param output
	 *            输出
	 * 
	 * @throws Exception
	 */
	void toPdf(XWPFDocument document, OutputStream output) throws Exception;

	/**
	 * 转为pdf
	 * 
	 * @param document
	 *            文档
	 * @param output
	 *            输出流
	 * @param properties
	 *            属性
	 * @throws Exception
	 */
	void toPdf(XWPFDocument document, OutputStream output, PdfProperties properties) throws Exception;

	/**
	 * 转为pdf
	 * 
	 * @param docFilePath
	 *            文档文件路径
	 * @param pdfFilePath
	 *            pdf文件路径
	 * @throws Exception
	 *             异常
	 */
	void toPdf(String docFilePath, String pdfFilePath) throws Exception;

	/**
	 * 转为pdf
	 * 
	 * @param docFilePath
	 *            文档文件路径
	 * @param pdfFilePath
	 *            pdf文件路径
	 * @param properties
	 *            属性
	 * @throws Exception
	 *             异常
	 */
	void toPdf(String docFilePath, String pdfFilePath, PdfProperties properties) throws Exception;

	/**
	 * 转为pdf
	 * 
	 * @param docFilePath
	 *            文档文件
	 * @param pdfFile
	 *            pdf文件
	 * @throws Exception
	 *             异常
	 */
	void toPdf(File docFile, File pdfFile) throws Exception;

	/**
	 * 转为pdf
	 * 
	 * @param docFilePath
	 *            文档文件
	 * @param pdfFile
	 *            pdf文件
	 * @param properties
	 *            属性
	 * @throws Exception
	 *             异常
	 */
	void toPdf(File docFile, File pdfFile, PdfProperties properties) throws Exception;

	/**
	 * 转为pdf
	 * 
	 * @param input
	 *            输入流
	 * @param output
	 *            输出流
	 * @throws Exception
	 */
	void toPdf(InputStream input, OutputStream output) throws Exception;

	/**
	 * 转为pdf
	 * 
	 * @param input
	 *            输入流
	 * @param output
	 *            输出流
	 * @param properties
	 *            属性
	 * @throws Exception
	 */
	void toPdf(InputStream input, OutputStream output, PdfProperties properties) throws Exception;

	/**
	 * 转为pdf
	 * 
	 * @param input
	 *            输入流
	 * @param output
	 *            输出流
	 * @param properties
	 *            属性
	 * @param closeStream
	 *            关闭流
	 * @throws Exception
	 */
	void toPdf(InputStream input, OutputStream output, PdfProperties properties, boolean closeStream) throws Exception;
}
