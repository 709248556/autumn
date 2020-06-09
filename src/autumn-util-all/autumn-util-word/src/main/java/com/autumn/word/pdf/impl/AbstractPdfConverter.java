package com.autumn.word.pdf.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.io.IOUtils;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

import com.autumn.exception.ExceptionUtils;
import com.autumn.word.pdf.PdfConverter;
import com.autumn.word.pdf.PdfProperties;

/**
 * pdf转换抽象
 * 
 * @author 老码农 2019-04-23 14:49:17
 */
public abstract class AbstractPdfConverter implements PdfConverter {

	@Override
	public void toPdf(XWPFDocument document, String pdfFilePath) throws Exception {
		this.toPdf(document, pdfFilePath, PdfProperties.DEFAULT);
	}

	@Override
	public void toPdf(XWPFDocument document, String pdfFilePath, PdfProperties properties) throws Exception {
		ExceptionUtils.checkNotNull(document, "document");
		ExceptionUtils.checkNotNullOrBlank(pdfFilePath, "pdfFilePath");
		this.toPdf(document, new File(pdfFilePath), properties);
	}

	@Override
	public void toPdf(XWPFDocument document, File pdfFile) throws Exception {
		this.toPdf(document, pdfFile, PdfProperties.DEFAULT);
	}

	@Override
	public void toPdf(XWPFDocument document, File pdfFile, PdfProperties properties) throws Exception {
		ExceptionUtils.checkNotNull(document, "document");
		ExceptionUtils.checkNotNull(pdfFile, "pdfFile");
		initializePdfFileDirectory(pdfFile);
		this.toPdf(document, new FileOutputStream(pdfFile), properties);
	}

	@Override
	public void toPdf(XWPFDocument document, OutputStream output) throws Exception {
		this.toPdf(document, output, PdfProperties.DEFAULT);
	}

	@Override
	public void toPdf(XWPFDocument document, OutputStream output, PdfProperties properties) throws Exception {
		ExceptionUtils.checkNotNull(document, "document");
		ExceptionUtils.checkNotNull(output, "output");
		ByteArrayOutputStream docOutput = new ByteArrayOutputStream();
		try {
			document.write(docOutput);
			ByteArrayInputStream input = new ByteArrayInputStream(docOutput.toByteArray());
			toPdfInternal(input, output, properties, true);

		} finally {
			IOUtils.closeQuietly(docOutput);
		}
	}

	@Override
	public void toPdf(String docFilePath, String pdfFilePath) throws Exception {
		this.toPdf(docFilePath, pdfFilePath, PdfProperties.DEFAULT);
	}

	@Override
	public void toPdf(String docFilePath, String pdfFilePath, PdfProperties properties) throws Exception {
		ExceptionUtils.checkNotNullOrBlank(docFilePath, "docFilePath");
		ExceptionUtils.checkNotNullOrBlank(pdfFilePath, "pdfFilePath");
		this.toPdf(new File(docFilePath), new File(pdfFilePath), properties);
	}

	@Override
	public void toPdf(File docFile, File pdfFile) throws Exception {
		this.toPdf(docFile, pdfFile, PdfProperties.DEFAULT);
	}

	private void initializePdfFileDirectory(File pdfFile) {
		if (!pdfFile.exists()) {
			File parentFile = pdfFile.getParentFile();
			if (parentFile != null && !parentFile.exists()) {
				parentFile.mkdirs();
			}
		}
	}

	@Override
	public void toPdf(File docFile, File pdfFile, PdfProperties properties) throws Exception {
		ExceptionUtils.checkNotNull(docFile, "docFile");
		ExceptionUtils.checkNotNull(pdfFile, "pdfFile");
		if (!docFile.isFile()) {
			ExceptionUtils.throwValidationException("路径 " + docFile.getPath() + " 不是文件。");
		}
		if (!docFile.exists()) {
			ExceptionUtils.throwValidationException("文件 " + docFile.getPath() + " 不存在。");
		}
		initializePdfFileDirectory(pdfFile);
		this.toPdfInternal(new FileInputStream(docFile), new FileOutputStream(pdfFile), properties, true);
	}

	@Override
	public void toPdf(InputStream input, OutputStream output) throws Exception {
		this.toPdf(input, output, PdfProperties.DEFAULT);
	}

	@Override
	public void toPdf(InputStream input, OutputStream output, PdfProperties properties) throws Exception {
		this.toPdf(input, output, properties, true);
	}

	@Override
	public void toPdf(InputStream input, OutputStream output, PdfProperties properties, boolean closeStream)
			throws Exception {
		ExceptionUtils.checkNotNull(input, "input");
		ExceptionUtils.checkNotNull(output, "output");
		this.toPdfInternal(input, output, properties, closeStream);
	}

	/**
	 * 转为pdf
	 * 
	 * @param input
	 *            输入流
	 * @param output
	 *            输出流
	 * @param properties
	 * @param closeStream
	 *            关闭流 属性
	 * @throws Exception
	 */
	protected abstract void toPdfInternal(InputStream input, OutputStream output, PdfProperties properties,
			boolean closeStream) throws Exception;
}
