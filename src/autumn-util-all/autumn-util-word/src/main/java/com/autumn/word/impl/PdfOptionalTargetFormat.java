package com.autumn.word.impl;

import java.io.InputStream;

import com.autumn.word.pdf.PdfConverter;
import com.autumn.word.pdf.PdfProperties;

/**
 * 
 * @author 老码农 2019-04-24 03:31:43
 */
class PdfOptionalTargetFormat extends AbstractOptionalTargetFormat {

	private final PdfProperties pdfProperties;

	private final PdfConverter pdfConverter;

	/**
	 * 
	 * @param inputStream
	 * @param closeStream
	 * @param pdfProperties
	 */
	public PdfOptionalTargetFormat(InputStream inputStream, boolean closeStream, PdfConverter pdfConverter,
			PdfProperties pdfProperties) {
		super(inputStream, closeStream);
		this.pdfConverter = pdfConverter;
		this.pdfProperties = pdfProperties;
	}

	public PdfProperties getPdfProperties() {
		return pdfProperties;
	}

	public PdfConverter getPdfConverter() {
		return pdfConverter;
	}

}
