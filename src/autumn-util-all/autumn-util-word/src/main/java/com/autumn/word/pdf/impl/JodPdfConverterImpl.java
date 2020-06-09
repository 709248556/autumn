package com.autumn.word.pdf.impl;

import java.io.InputStream;
import java.io.OutputStream;

import org.jodconverter.DocumentConverter;
import org.jodconverter.document.DefaultDocumentFormatRegistry;

import com.autumn.word.pdf.PdfProperties;

/**
 * JodConverter + OpenOffice 转换
 * 
 * @author 老码农 2019-04-23 15:07:58
 */
public class JodPdfConverterImpl extends AbstractPdfConverter {

	private final DocumentConverter documentConverter;

	/**
	 * 
	 * @param documentConverter
	 */
	public JodPdfConverterImpl(DocumentConverter documentConverter) {
		this.documentConverter = documentConverter;
	}

	/**
	 * 
	 */
	@Override
	protected void toPdfInternal(InputStream input, OutputStream output, PdfProperties properties, boolean closeStream) throws Exception {
		documentConverter.convert(input, true).as(DefaultDocumentFormatRegistry.DOCX).to(output, true)
				.as(DefaultDocumentFormatRegistry.PDF).execute();
	}
}
