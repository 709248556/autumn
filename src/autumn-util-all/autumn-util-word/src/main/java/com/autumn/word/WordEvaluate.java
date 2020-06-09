package com.autumn.word;

import org.apache.poi.xwpf.usermodel.XWPFDocument;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * Word 解析
 *
 * @author 老码农 2019-04-24 01:17:09
 */
public interface WordEvaluate {

    /**
     * 解析
     *
     * @param sourceFile      源文件
     * @param customEvaluates 自定义解析集合
     * @return
     * @throws FileNotFoundException
     */
    WordWithOptional with(File sourceFile, WordContentEvaluate... customEvaluates) throws FileNotFoundException;

    /**
     * 解析
     *
     * @param sourceFilePath  源文件路径
     * @param customEvaluates 自定义解析集合
     * @return
     * @throws FileNotFoundException
     */
    WordWithOptional with(String sourceFilePath, WordContentEvaluate... customEvaluates) throws FileNotFoundException;

    /**
     * 解析
     *
     * @param sourceInputStream 源输入流
     * @param customEvaluates   自定义解析集合
     * @return
     */
    WordWithOptional with(InputStream sourceInputStream, WordContentEvaluate... customEvaluates);

    /**
     * 解析
     *
     * @param sourceDocument  源文档
     * @param customEvaluates 自定义解析集合
     * @return
     */
    WordWithOptional with(XWPFDocument sourceDocument, WordContentEvaluate... customEvaluates);

    /**
     * 解析
     *
     * @param sourceInputStream 源输入流
     * @param closeStream       关闭流
     * @param customEvaluates   自定义解析集合
     * @return
     */
    WordWithOptional with(InputStream sourceInputStream, boolean closeStream, WordContentEvaluate... customEvaluates);
}
