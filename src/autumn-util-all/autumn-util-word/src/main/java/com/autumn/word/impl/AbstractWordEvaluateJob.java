package com.autumn.word.impl;

import com.autumn.word.WordEvaluateJob;
import org.apache.commons.io.IOUtils;

import java.io.OutputStream;

/**
 * @author 老码农 2019-04-24 03:55:56
 */
abstract class AbstractWordEvaluateJob<TOptionalFormat extends AbstractOptionalTargetFormat>
        implements WordEvaluateJob {

    protected final TOptionalFormat pptionalTargetFormat;

    /**
     *
     */
    protected final OutputStream outputStream;
    /**
     *
     */
    protected final boolean closeStream;

    /**
     * @param pptionalTargetFormat
     * @param outputStream
     * @param closeStream
     */
    public AbstractWordEvaluateJob(TOptionalFormat pptionalTargetFormat, OutputStream outputStream,
                                   boolean closeStream) {
        this.pptionalTargetFormat = pptionalTargetFormat;
        this.outputStream = outputStream;
        this.closeStream = closeStream;
    }

    @Override
    public final void execute() throws Exception {
        try {
            this.executeInternal();
        } finally {
            if (this.pptionalTargetFormat.isCloseStream()) {
                IOUtils.closeQuietly(this.pptionalTargetFormat.getInputStream());
            }
            if (this.closeStream) {
                IOUtils.closeQuietly(this.outputStream);
            }
        }
    }

    /**
     * 执行内部方法
     *
     * @throws Exception
     */
    protected abstract void executeInternal() throws Exception;

}
