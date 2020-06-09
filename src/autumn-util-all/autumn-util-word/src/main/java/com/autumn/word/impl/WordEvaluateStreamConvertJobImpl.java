package com.autumn.word.impl;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * 流转换
 * 
 * @author 老码农 2019-04-24 04:03:33
 */
class WordEvaluateStreamConvertJobImpl extends AbstractWordEvaluateJob<DocumentOptionalTargetFormat> {

	private static final int BLOCK_SIZE = 4096;

	/**
	 * 
	 * @param pptionalTargetFormat
	 * @param outputStream
	 * @param closeStream
	 */
	public WordEvaluateStreamConvertJobImpl(DocumentOptionalTargetFormat pptionalTargetFormat, OutputStream outputStream,
                                            boolean closeStream) {
		super(pptionalTargetFormat, outputStream, closeStream);
	}

	@Override
	protected void executeInternal() throws Exception {
		InputStream input = this.pptionalTargetFormat.getInputStream();
		byte[] b = new byte[BLOCK_SIZE];
		int len;		
		while ((len = input.read(b, 0, BLOCK_SIZE)) > 0) {
			this.outputStream.write(b, 0, len);
		}
	}

}
