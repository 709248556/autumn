package com.autumn.word.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import com.autumn.word.*;
import org.apache.commons.io.IOUtils;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

import com.autumn.exception.ExceptionUtils;
import com.autumn.word.pdf.PdfConverter;
import com.autumn.word.pdf.PdfProperties;

/**
 * @author 老码农 2019-04-24 01:41:50
 */
class WordWithOptionalImpl implements WordWithOptional {

    private PdfConverter pdfConverter;
    private InputStream inputStream;
    private XWPFDocument document;
    private boolean closeStream;
    private final WordContentEvaluate[] customEvaluates;

    /**
     * document
     *
     * @param pdfConverter
     * @param inputStream
     * @param document
     * @param closeStream
     * @param customEvaluates
     */
    public WordWithOptionalImpl(PdfConverter pdfConverter, InputStream inputStream, XWPFDocument document,
                                boolean closeStream, WordContentEvaluate... customEvaluates) {
        this.pdfConverter = pdfConverter;
        this.inputStream = inputStream;
        this.document = document;
        this.closeStream = closeStream;
        this.customEvaluates = customEvaluates;
    }

    @Override
    public WordEvaluateWithOptional evaluate(Object obj) throws IOException {
        return this.evaluate(obj, EvaluateOptions.DEFAULT);
    }

    @Override
    public WordEvaluateWithOptional evaluate(Object obj, EvaluateOptions options) throws IOException {
        return this.evaluate(obj, Token.DEFAULT, options);
    }

    @Override
    public WordEvaluateWithOptional evaluate(Object obj, Token token, EvaluateOptions options) throws IOException {
        try {
            if (this.document == null) {
                this.document = new XWPFDocument(this.inputStream);
            }
            WordSession session = new WordSession(obj, token);
            if (this.customEvaluates != null) {
                for (WordContentEvaluate customEvaluate : this.customEvaluates) {
                    if (customEvaluate != null) {
                        session.getContentEvaluates().add(customEvaluate);
                    }
                }
            }
            session.evaluate(this.document, options);
            return new WordEvaluateWithOptionalImpl(this.document, this.pdfConverter);
        } finally {
            if (closeStream) {
                IOUtils.closeQuietly(this.inputStream);
            }
        }
    }

    @Override
    public WordEvaluateWithOptionalTarget toPdf() {
        return toPdf(PdfProperties.DEFAULT);
    }

    @Override
    public WordEvaluateWithOptionalTarget toPdf(PdfProperties pdfProperties) {
        if (this.document != null) {
            ByteArrayOutputStream docOutput = new ByteArrayOutputStream();
            try {
                document.write(docOutput);
                this.inputStream = new ByteArrayInputStream(docOutput.toByteArray());
            } catch (IOException e) {
                ExceptionUtils.throwValidationException(e.getMessage());
            } finally {
                IOUtils.closeQuietly(docOutput);
            }
        }
        return new WordEvaluateWithOptionalTargetImpl(
                new PdfOptionalTargetFormat(this.inputStream, this.closeStream, this.pdfConverter, pdfProperties));
    }

}
