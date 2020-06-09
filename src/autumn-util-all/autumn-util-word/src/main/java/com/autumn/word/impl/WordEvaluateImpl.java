package com.autumn.word.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import com.autumn.word.WordContentEvaluate;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

import com.autumn.exception.ExceptionUtils;
import com.autumn.word.WordEvaluate;
import com.autumn.word.WordWithOptional;
import com.autumn.word.pdf.PdfConverter;

/**
 * Word 解析实现
 *
 * @author 老码农 2019-04-24 01:17:41
 */
public class WordEvaluateImpl implements WordEvaluate {

    private PdfConverter pdfConverter;

    /**
     * @param pdfConverter
     */
    public WordEvaluateImpl(PdfConverter pdfConverter) {
        this.pdfConverter = pdfConverter;
    }

    @Override
    public WordWithOptional with(File sourceFile, WordContentEvaluate... customEvaluates) throws FileNotFoundException {
        ExceptionUtils.checkNotNull(sourceFile, "sourceFile");
        return new WordWithOptionalImpl(this.pdfConverter,
                new FileInputStream(sourceFile),
                null,
                true,
                customEvaluates);
    }

    @Override
    public WordWithOptional with(String sourceFilePath, WordContentEvaluate... customEvaluates) throws FileNotFoundException {
        ExceptionUtils.checkNotNullOrBlank(sourceFilePath, "sourceFilePath");
        return new WordWithOptionalImpl(this.pdfConverter,
                new FileInputStream(sourceFilePath),
                null,
                true,
                customEvaluates);
    }

    @Override
    public WordWithOptional with(InputStream sourceFilePath, WordContentEvaluate... customEvaluates) {
        return this.with(sourceFilePath,
                true,
                customEvaluates);
    }

    @Override
    public WordWithOptional with(XWPFDocument sourceDocument, WordContentEvaluate... customEvaluates) {
        ExceptionUtils.checkNotNull(sourceDocument, "sourceDocument");
        return new WordWithOptionalImpl(this.pdfConverter,
                null,
                sourceDocument,
                true,
                customEvaluates);
    }

    @Override
    public WordWithOptional with(InputStream sourceInputStream, boolean closeStream, WordContentEvaluate... customEvaluates) {
        ExceptionUtils.checkNotNull(sourceInputStream, "sourceInputStream");
        return new WordWithOptionalImpl(this.pdfConverter,
                sourceInputStream,
                null,
                closeStream,
                customEvaluates);
    }

}
