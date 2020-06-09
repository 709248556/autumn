package com.autumn.word.impl;

import java.io.InputStream;

/**
 * 
 * @author 老码农 2019-04-24 03:31:01
 */
abstract class AbstractOptionalTargetFormat {

	private final InputStream inputStream;
	private final boolean closeStream;

	/**
	 * 
	 * @param inputStream
	 * @param closeStream
	 */
	public AbstractOptionalTargetFormat(InputStream inputStream, boolean closeStream) {
		this.inputStream = inputStream;
		this.closeStream = closeStream;
	}

	/**
	 * 
	 * @return
	 */
	public InputStream getInputStream() {
		return inputStream;
	}

	/**
	 * 
	 * @return
	 */
	public boolean isCloseStream() {
		return closeStream;
	}

}
