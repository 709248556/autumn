package com.autumn.word;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.OutputStream;

/**
 * 解析目标
 *
 * @author 老码农 2019-04-24 02:56:59
 */
public interface WordEvaluateWithOptionalTarget {

    /**
     * 转换
     *
     * @param targetFile 目标文件
     * @return
     * @throws FileNotFoundException
     */
    WordEvaluateJob to(File targetFile) throws FileNotFoundException;

    /**
     * 转换
     *
     * @param targetFilePath 目标文件路径
     * @return
     * @throws FileNotFoundException
     */
    WordEvaluateJob to(String targetFilePath) throws FileNotFoundException;

    /**
     * 转换
     *
     * @param targetOutputStream 目标流
     * @return
     */
    WordEvaluateJob to(OutputStream targetOutputStream);

    /**
     * 转换
     *
     * @param targetOutputStream 目标流
     * @param closeStream        关闭流
     * @return
     */
    WordEvaluateJob to(OutputStream targetOutputStream, boolean closeStream);

}
