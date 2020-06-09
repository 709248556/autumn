package com.autumn.word.impl;

import java.io.OutputStream;

/**
 * pdf工作
 * 
 * @author 老码农 2019-04-24 03:53:44
 */
class WordEvaluatePdfJobImpl extends AbstractWordEvaluateJob<PdfOptionalTargetFormat> {

	/**
	 * 
	 * @param pdfOptionalTargetFormat
	 * @param outputStream
	 * @param closeStream
	 */
	public WordEvaluatePdfJobImpl(PdfOptionalTargetFormat pdfOptionalTargetFormat, OutputStream outputStream,
                                  boolean closeStream) {
		super(pdfOptionalTargetFormat, outputStream, closeStream);
	}

	@Override
	protected void executeInternal() throws Exception {
		this.pptionalTargetFormat.getPdfConverter().toPdf(this.pptionalTargetFormat.getInputStream(), this.outputStream,
				this.pptionalTargetFormat.getPdfProperties(), false);
	}

}
