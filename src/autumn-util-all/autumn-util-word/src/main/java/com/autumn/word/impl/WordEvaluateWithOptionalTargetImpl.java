package com.autumn.word.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;

import com.autumn.exception.ExceptionUtils;
import com.autumn.word.WordEvaluateJob;
import com.autumn.word.WordEvaluateWithOptionalTarget;

/**
 * 
 * @author 老码农 2019-04-24 03:15:58
 */
class WordEvaluateWithOptionalTargetImpl implements WordEvaluateWithOptionalTarget {

	private AbstractOptionalTargetFormat optionalTargetFormat;

	/**
	 * 
	 * @param optionalTargetFormat
	 */
	public WordEvaluateWithOptionalTargetImpl(AbstractOptionalTargetFormat optionalTargetFormat) {
		this.optionalTargetFormat = optionalTargetFormat;
	}

	@Override
	public WordEvaluateJob to(File targetFile) throws FileNotFoundException {
		ExceptionUtils.checkNotNull(targetFile, "targetFile");
		if (!targetFile.exists()) {
			File parentFile = targetFile.getParentFile();
			if (parentFile != null && !parentFile.exists()) {
				parentFile.mkdirs();
			}
		}
		return to(new FileOutputStream(targetFile));
	}

	@Override
	public WordEvaluateJob to(String targetFilePath) throws FileNotFoundException {
		ExceptionUtils.checkNotNullOrBlank(targetFilePath, "targetFilePath");
		return to(new File(targetFilePath));
	}

	@Override
	public WordEvaluateJob to(OutputStream targetOutputStream) {
		return to(targetOutputStream, true);
	}

	@Override
	public WordEvaluateJob to(OutputStream targetOutputStream, boolean closeStream) {
		ExceptionUtils.checkNotNull(targetOutputStream, "targetOutputStream");
		if (optionalTargetFormat instanceof PdfOptionalTargetFormat) {
			return new WordEvaluatePdfJobImpl((PdfOptionalTargetFormat) optionalTargetFormat, targetOutputStream,
					closeStream);
		}
		if (optionalTargetFormat instanceof DocumentOptionalTargetFormat) {
			return new WordEvaluateStreamConvertJobImpl((DocumentOptionalTargetFormat) optionalTargetFormat, targetOutputStream,
					closeStream);
		}
		throw ExceptionUtils.throwApplicationException("不支持格式[" + optionalTargetFormat.getClass().getName() + "]格式类型。");
	}
}
