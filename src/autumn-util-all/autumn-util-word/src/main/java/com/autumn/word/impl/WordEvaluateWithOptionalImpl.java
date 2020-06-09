package com.autumn.word.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

import com.autumn.exception.ExceptionUtils;
import com.autumn.word.WordEvaluateWithOptional;
import com.autumn.word.WordEvaluateWithOptionalTarget;
import com.autumn.word.pdf.PdfConverter;
import com.autumn.word.pdf.PdfProperties;

/**
 * Work 解析选项
 * 
 * @author 老码农 2019-04-24 02:04:10
 */
class WordEvaluateWithOptionalImpl implements WordEvaluateWithOptional {

	private XWPFDocument document;
	private PdfConverter pdfConverter;

	/**
	 * 
	 * @param document
	 * @param pdfConverter
	 */
	public WordEvaluateWithOptionalImpl(XWPFDocument document, PdfConverter pdfConverter) {
		this.document = document;
		this.pdfConverter = pdfConverter;
	}

	private InputStream createInputStream() {
		ByteArrayOutputStream docOutput = new ByteArrayOutputStream();
		try {
			document.write(docOutput);
			return new ByteArrayInputStream(docOutput.toByteArray());
		} catch (IOException e) {
			throw ExceptionUtils.throwValidationException(e.getMessage());
		} finally {
			IOUtils.closeQuietly(docOutput);
		}
	}

	@Override
	public WordEvaluateWithOptionalTarget toDocument() {
		return new WordEvaluateWithOptionalTargetImpl(new DocumentOptionalTargetFormat(this.createInputStream(), true));
	}

	@Override
	public WordEvaluateWithOptionalTarget toPdf(PdfProperties pdfProperties) {
		return new WordEvaluateWithOptionalTargetImpl(
				new PdfOptionalTargetFormat(this.createInputStream(), true, this.pdfConverter, pdfProperties));
	}

	@Override
	public WordEvaluateWithOptionalTarget toPdf() {
		return toPdf(PdfProperties.DEFAULT);
	}

	@Override
	public XWPFDocument getDocument() {
		return this.document;
	}

}
