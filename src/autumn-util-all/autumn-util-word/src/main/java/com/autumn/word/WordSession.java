package com.autumn.word;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

import com.autumn.word.impl.DefaultWordContentEvaluate;
import org.apache.commons.io.IOUtils;
import org.apache.poi.ooxml.POIXMLProperties;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFFooter;
import org.apache.poi.xwpf.usermodel.XWPFHeader;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFTable;

import com.autumn.evaluator.DefaultEvaluatorSession;
import com.autumn.evaluator.FunctionManager;
import com.autumn.evaluator.Variant;
import com.autumn.exception.ExceptionUtils;
import com.autumn.util.TypeUtils;

/**
 * Word 会话
 * @author ycg
 */
public class WordSession {

    static {
        // 注册函数
        FunctionManager.register(WordFunction.class);
    }

    private static final WordContentEvaluate WORK_EVALUATE = new DefaultWordContentEvaluate();

    /**
     * 获取标记
     */
    private final Token token;

    /**
     * 获取标记
     *
     * @return
     */
    public final Token getToken() {
        return token;
    }

    private final WordContext context;

    /**
     * 获取上下文
     *
     * @return
     */
    public final WordContext getContext() {
        return context;
    }

    /**
     * 获取实体对象
     */
    private final Object entity;

    /**
     * 获取实体
     *
     * @return
     */
    public final Object getEntity() {
        return this.entity;
    }

    /**
     * 解析器
     */
    private final DefaultEvaluatorSession evaluator;

    /**
     * 获取解析器
     *
     * @return
     */
    public final DefaultEvaluatorSession getEvaluator() {
        return evaluator;
    }

    /**
     *
     */
    private final List<WordContentEvaluate> contentEvaluates;

    /**
     * 实例化 WordSession 类新实例
     *
     * @param obj 对象
     */
    public WordSession(Object obj) {
        this(obj, Token.DEFAULT);
    }

    /**
     * 实例化 WordSession 类新实例
     *
     * @param entity 对象
     * @param token  标记
     */
    public WordSession(Object entity, Token token) {
        ExceptionUtils.checkNotNull(entity, "entity");
        ExceptionUtils.checkNotNull(token, "token");
        Class<?> type = entity.getClass();
        if (TypeUtils.isBaseType(type)) {
            ExceptionUtils.throwValidationException("entity 必须是对象类型，不支持基本类型。");
        }
        if (entity instanceof Collection) {
            ExceptionUtils.throwValidationException("entity 必须是对象类型，不支持集合类型。");
        }
        if (type.isArray()) {
            ExceptionUtils.throwValidationException("entity 必须是对象类型，不支持数组类型。");
        }
        this.token = token;
        this.context = new WordContext(entity, this);
        this.evaluator = new DefaultEvaluatorSession();
        this.entity = entity;
        this.contentEvaluates = new ArrayList<>();
    }

    /**
     * 获取内容解析集合
     *
     * @return
     */
    public List<WordContentEvaluate> getContentEvaluates() {
        return this.contentEvaluates;
    }

    /**
     * 解析一个表达式
     *
     * @param expression 表达式
     * @return
     */
    public final Variant evaluateExpression(String expression) {
        ExceptionUtils.checkNotNullOrBlank(expression, "expression");
        return this.evaluator.parse(expression, this.getContext());
    }

    /**
     * 解析
     *
     * @param filePath 文件路径
     */
    public final XWPFDocument evaluate(String filePath) throws IOException {
        return this.evaluate(new FileInputStream(filePath));
    }

    /**
     * 解析
     *
     * @param inputStream 输入流
     */
    public final XWPFDocument evaluate(InputStream inputStream) throws IOException {
        return this.evaluate(inputStream, EvaluateOptions.DEFAULT);
    }

    /**
     * 解析
     *
     * @param filePath 文件路径
     * @param options  选项
     * @return
     * @throws IOException
     */
    public final XWPFDocument evaluate(String filePath, EvaluateOptions options) throws IOException {
        return this.evaluate(new FileInputStream(filePath), options);
    }

    /**
     * 解析
     *
     * @param inputStream 输入流
     * @param options     选项
     * @return
     * @throws IOException
     */
    public final XWPFDocument evaluate(InputStream inputStream, EvaluateOptions options) throws IOException {
        return evaluate(inputStream, options, true);
    }

    /**
     * 解析
     *
     * @param inputStream 输入流
     * @param options     选项
     * @param closeStream 关闭流
     * @return
     * @throws IOException
     */
    public final XWPFDocument evaluate(InputStream inputStream, EvaluateOptions options, boolean closeStream)
            throws IOException {
        ExceptionUtils.checkNotNull(inputStream, "inputStream");
        try {
            synchronized (this.getContext()) {
                XWPFDocument doc = new XWPFDocument(inputStream);
                return evaluate(doc, options);
            }
        } finally {
            if (closeStream) {
                IOUtils.closeQuietly(inputStream);
            }
        }
    }

    /**
     * @return
     */
    private List<WordContentEvaluate> toContentEvaluates() {
        List<WordContentEvaluate> evaluates = new ArrayList<>(this.getContentEvaluates());
        evaluates.add(WORK_EVALUATE);
        return evaluates;
    }

    /**
     * 解析
     *
     * @param document 文档
     * @param options  选项
     * @return
     * @throws IOException
     */
    public final XWPFDocument evaluate(XWPFDocument document, EvaluateOptions options) {
        ExceptionUtils.checkNotNull(document, "document");
        if (options == null) {
            options = EvaluateOptions.DEFAULT;
        }
        synchronized (this.getContext()) {
            List<WordContentEvaluate> evaluates = toContentEvaluates();
            for (WordContentEvaluate evaluate : evaluates) {
                this.generalEvaluate(document, evaluate);
                this.tableEvaluate(document, evaluate);
            }
            POIXMLProperties properties = document.getProperties();
            if (properties != null) {
                properties.getCoreProperties().setTitle(options.getTitle());
                properties.getCoreProperties().setCategory(options.getCategory());
                properties.getCoreProperties().setContentType(options.getContentType());
                properties.getCoreProperties().setSubjectProperty(options.getSubject());
                properties.getCoreProperties().setDescription(options.getDescription());
            }
            return document;
        }
    }

    /**
     * 普通解析
     *
     * @param doc                 文档
     * @param wordContentEvaluate 内容解析器
     */
    private void generalEvaluate(XWPFDocument doc, WordContentEvaluate wordContentEvaluate) {
        List<XWPFParagraph> paragraphs = doc.getParagraphs();
        for (XWPFParagraph para : paragraphs) {
            wordContentEvaluate.evaluateGeneralParagraph(this, para);
        }
        List<XWPFHeader> headers = doc.getHeaderList();
        for (XWPFHeader header : headers) {
            paragraphs = header.getListParagraph();
            for (XWPFParagraph para : paragraphs) {
                wordContentEvaluate.evaluateGeneralParagraph(this, para);
            }
        }
        List<XWPFFooter> footers = doc.getFooterList();
        for (XWPFFooter footer : footers) {
            paragraphs = footer.getListParagraph();
            for (XWPFParagraph para : paragraphs) {
                wordContentEvaluate.evaluateGeneralParagraph(this, para);
            }
        }
    }

    /**
     * 表解析
     *
     * @param doc                 文档
     * @param wordContentEvaluate 内容解析器
     */
    private void tableEvaluate(XWPFDocument doc, WordContentEvaluate wordContentEvaluate) {
        List<XWPFTable> tables = doc.getTables();
        for (XWPFTable table : tables) {
            wordContentEvaluate.evaluateTable(this, table);
        }
    }
}