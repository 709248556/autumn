package com.autumn.word;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.jodconverter.DocumentConverter;
import org.jodconverter.LocalConverter;
import org.jodconverter.office.LocalOfficeManager;
import org.jodconverter.office.OfficeManager;
import org.junit.Test;

import com.autumn.evaluator.Variant;
import com.autumn.util.DateUtils;
import com.autumn.util.ResourceUtils;
import com.autumn.word.configure.JodLocalConverterProperties;
import com.autumn.word.impl.WordEvaluateImpl;
import com.autumn.word.pdf.PdfConverter;
import com.autumn.word.pdf.impl.Docx4JPdfConverterImpl;
import com.autumn.word.pdf.impl.JodPdfConverterImpl;

public class WordTest {

    /**
     * Word解析测试
     *
     * @throws Exception
     */
    @Test
    public void workEvaluateTest() throws Exception {
        printInTime("初始化-开始");
        String root = getDefaultLocalPath();
        String sourcePath = root + "/Word模板.docx";
        String docxPath = root + "/生成.docx";
        TestModel testModel = this.createTestModel();
        WordSession session = new WordSession(testModel);
        printInTime("初始化-完成,开始解析(" + sourcePath + ")");
        XWPFDocument doc = session.evaluate(sourcePath);
        printInTime("解析-完成,开始保存Word(" + docxPath + ")");
        doc.write(new FileOutputStream(docxPath));
        printInTime("保存Word-完成(" + docxPath + ")");

        System.out.println("全部生成成功");
    }

    /**
     * Word解析测试
     *
     * @throws Exception
     */
    @Test
    public void workEvaluateTest1() throws Exception {
        printInTime("初始化-开始");
        String root = getDefaultLocalPath();
        String sourcePath = root + "/Word模板.docx";
        String docxPath = root + "/生成.docx";
        TestModel testModel = this.createTestModel();
        WordEvaluate eva = new WordEvaluateImpl(null);
        printInTime("初始化-完成,开始解析(" + sourcePath + ")");
        eva.with(sourcePath).evaluate(testModel).toDocument().to(docxPath).execute();
        printInTime("解析-并保存Word(" + docxPath + ")");
        System.out.println("全部生成成功");
    }

    /**
     * Word解析测试
     *
     * @throws Exception
     */
    @Test
    public void workCustomEvaluateTest() throws Exception {
        printInTime("初始化-开始");
        String root = getDefaultLocalPath();
        String sourcePath = root + "/Word模板.docx";
        String docxPath = root + "/生成自定义.docx";
        TestModel testModel = this.createTestModel();
        WordEvaluate eva = new WordEvaluateImpl(null);
        printInTime("初始化-完成,开始解析(" + sourcePath + ")");
        eva.with(sourcePath, new CustomWordContentEvaluate())
                .evaluate(testModel)
                .toDocument()
                .to(docxPath)
                .execute();
        printInTime("解析-并保存Word(" + docxPath + ")");
        System.out.println("全部生成成功");
    }

    /**
     * docx4J Pdf 测试
     *
     * @throws Exception
     */
    @Test
    public void docx4JPdfTest() throws Exception {
        printInTime("初始化-开始");
        String root = getDefaultLocalPath();
        String docxPath = root + "/生成.docx";
        String pdfPath = root + "/生成.pdf";
        PdfConverter pdfConverter = new Docx4JPdfConverterImpl();
        printInTime("开始第一次生成pdf(" + pdfPath + ")");
        pdfConverter.toPdf(new FileInputStream(docxPath), new FileOutputStream(pdfPath));
        printInTime("第一次生成pdf-完成,开始第二次生成pdf(" + pdfPath + ")");
        pdfConverter.toPdf(new FileInputStream(docxPath), new FileOutputStream(pdfPath));
        printInTime("第二次生成pdf-完成");
        System.out.println("全部生成成功");
    }

    /**
     * docx4J Pdf 测试
     *
     * @throws Exception
     */
    @Test
    public void docx4JPdfTest1() throws Exception {
        printInTime("初始化-开始");
        String root = getDefaultLocalPath();
        String docxPath = root + "/生成.docx";
        String pdfPath = root + "/生成.pdf";
        printInTime("开始第一次生成pdf(" + pdfPath + ")");
        WordEvaluate eva = new WordEvaluateImpl(new Docx4JPdfConverterImpl());
        eva.with(docxPath).toPdf().to(pdfPath).execute();
        printInTime("第一次生成pdf-完成,开始第二次生成pdf(" + pdfPath + ")");
        eva.with(docxPath).toPdf().to(pdfPath).execute();
        printInTime("第二次生成pdf-完成");
        System.out.println("全部生成成功");
    }

    @Test
    public void JodConverterPdfTest() throws Exception {
        printInTime("初始化-开始");
        String root = getDefaultLocalPath();
        String docxPath = root + "/生成.docx";
        String pdfPath = root + "/生成.pdf";

        OfficeManager officeManager = createOfficeManager();
        officeManager.start();

        DocumentConverter documentConverter = LocalConverter.make(officeManager);

        PdfConverter pdfConverter = new JodPdfConverterImpl(documentConverter);
        printInTime("开始第一次生成pdf(" + pdfPath + ")");
        pdfConverter.toPdf(new FileInputStream(docxPath), new FileOutputStream(pdfPath));
        printInTime("第一次生成pdf-完成,开始第二次生成pdf(" + pdfPath + ")");
        pdfConverter.toPdf(new FileInputStream(docxPath), new FileOutputStream(pdfPath));
        printInTime("第二次生成pdf-完成");
        System.out.println("全部生成成功");
    }

    @Test
    public void JodConverterPdfTest2() throws Exception {
        printInTime("初始化-开始");
        OfficeManager officeManager = createOfficeManager();
        officeManager.start();
        DocumentConverter documentConverter = LocalConverter.make(officeManager);
        String root = getDefaultLocalPath();
        String docxPath = root + "/生成.docx";
        String pdfPath = root + "/生成.pdf";
        printInTime("开始第一次生成pdf(" + pdfPath + ")");
        WordEvaluate eva = new WordEvaluateImpl(new JodPdfConverterImpl(documentConverter));
        eva.with(docxPath).toPdf().to(pdfPath).execute();
        printInTime("第一次生成pdf-完成,开始第二次生成pdf(" + pdfPath + ")");
        eva.with(docxPath).toPdf().to(pdfPath).execute();
        printInTime("第二次生成pdf-完成");
        System.out.println("全部生成成功");
    }

    @Test
    public void JodConverterPdfFullTest() throws Exception {
        printInTime("初始化-开始");
        OfficeManager officeManager = createOfficeManager();
        officeManager.start();
        DocumentConverter documentConverter = LocalConverter.make(officeManager);

        String root = getDefaultLocalPath();
        String sourcePath = root + "/Word模板.docx";
        String pdfPath = root + "/生成.pdf";

        printInTime("开始第一次生成pdf(" + pdfPath + ")");
        TestModel testModel = this.createTestModel();

        WordEvaluate eva = new WordEvaluateImpl(new JodPdfConverterImpl(documentConverter));

        eva.with(sourcePath).evaluate(testModel).toPdf().to(pdfPath).execute();

        printInTime("第一次生成pdf-完成,开始第二次生成pdf(" + pdfPath + ")");
        eva.with(sourcePath).evaluate(testModel).toPdf().to(pdfPath).execute();
        printInTime("第二次生成pdf-完成");
        System.out.println("全部生成成功");
    }

    private OfficeManager createOfficeManager() {
        JodLocalConverterProperties properties = new JodLocalConverterProperties();
        final LocalOfficeManager.Builder builder = LocalOfficeManager.builder();
        // properties.setPortNumbers("8100, 8101, 8102, 8103, 8104, 8105, 8106, 8107,
        // 8108, 8109");
        if (!StringUtils.isBlank(properties.getPortNumbers())) {
            final Set<Integer> ports = new HashSet<>();
            for (final String portNumber : StringUtils.split(properties.getPortNumbers(), ", ")) {
                ports.add(NumberUtils.toInt(portNumber, 2002));
            }
            builder.portNumbers(ArrayUtils.toPrimitive(ports.toArray(new Integer[ports.size()])));
        }

        builder.officeHome(properties.getOfficeHome());
        builder.workingDir(properties.getWorkingDir());
        builder.templateProfileDir(properties.getTemplateProfileDir());
        builder.killExistingProcess(properties.isKillExistingProcess());
        builder.processTimeout(properties.getProcessTimeout());
        builder.processRetryInterval(properties.getProcessRetryInterval());
        builder.taskExecutionTimeout(properties.getTaskExecutionTimeout());
        builder.maxTasksPerProcess(properties.getMaxTasksPerProcess());
        builder.taskQueueTimeout(properties.getTaskQueueTimeout());

        // Starts the manager
        return builder.build();
    }

    /**
     * Word合并测试
     *
     * @throws Exception
     */
    @Test
    public void workMergeTest() throws Exception {
        String root = getDefaultLocalPath();
        String docxPath = root + "/生成.docx";
        String docxMergePath = root + "/合并.docx";
        printInTime("读取文件");
        XWPFDocument mainDoc = new XWPFDocument(new FileInputStream(docxPath));
        XWPFDocument appendDoc = new XWPFDocument(new FileInputStream(docxPath));
        printInTime("开始合并");
        WordUtils.mergeDocument(mainDoc, appendDoc, true);
        printInTime("结束合并");
        mainDoc.write(new FileOutputStream(docxMergePath));
        printInTime("保存成功");
    }

    private void printInTime(String name) {
        System.out.println(DateUtils.dateFormat(new Date(), DateUtils.createFormatDateTimeMillisecond()) + " : " + name);
    }

    @Test
    public void workFounctTest() {
        TestModel testModel = this.createTestModel();
        WordSession session = new WordSession(testModel);
        Variant variant = session
                .evaluateExpression("ArrayMemberIfValue(\"expenses\",\"expenseName\",\"项目3\",\"expense\")");
        System.out.println(variant.toString());
    }

    private TestModel createTestModel() {
        TestModel testModel = new TestModel();
        testModel.setUserId(20L);
        testModel.setUserName("张三");
        for (int i = 1; i <= 15; i++) {
            TestModelExpense expense = new TestModelExpense();
            expense.setExpenseName("项目" + i);
            expense.setExpense(new BigDecimal(10).multiply(new BigDecimal(i)));
            testModel.getExpenses().add(expense);
        }
        for (int i = 1; i <= 15; i++) {
            TestModelEmployee employee = new TestModelEmployee();
            employee.setName("张三" + i);
            employee.setAge(20 + i);
            employee.setBirthDate(DateUtils.getDate(new Date()));
            if (i % 2 == 0) {
                employee.setSex("男");
            } else {
                employee.setSex("女");
            }
            testModel.getEmployees().add(employee);
        }
        TestModelEmployee user;

        user = new TestModelEmployee();
        user.setName("李小的");
        user.setAge(26);
        user.setBirthDate(DateUtils.getDate(new Date()));
        user.setSex("女");
        testModel.getUsers().add(user);

        user = new TestModelEmployee();
        user.setName("李四");
        user.setAge(22);
        user.setBirthDate(DateUtils.getDate(new Date()));
        user.setSex("男");
        testModel.getUsers().add(user);

        user = new TestModelEmployee();
        user.setName("李四");
        user.setAge(26);
        user.setBirthDate(DateUtils.getDate(new Date()));
        user.setSex("女");
        testModel.getUsers().add(user);

        user = new TestModelEmployee();
        user.setName("王五");
        user.setAge(15);
        user.setBirthDate(DateUtils.getDate(new Date()));
        user.setSex("女");
        testModel.getUsers().add(user);

        user = new TestModelEmployee();
        user.setName("王五");
        user.setAge(59);
        user.setBirthDate(DateUtils.getDate(new Date()));
        user.setSex("女");
        testModel.getUsers().add(user);

        user = new TestModelEmployee();
        user.setName("王五");
        user.setAge(24);
        user.setBirthDate(DateUtils.getDate(new Date()));
        user.setSex("男");
        testModel.getUsers().add(user);

        user = new TestModelEmployee();
        user.setName("李小三");
        user.setAge(30);
        user.setBirthDate(DateUtils.getDate(new Date()));
        user.setSex("男");
        testModel.getUsers().add(user);

        user = new TestModelEmployee();
        user.setName("李四3");
        user.setAge(45);
        user.setBirthDate(DateUtils.getDate(new Date()));
        user.setSex("男");
        testModel.getUsers().add(user);

        user = new TestModelEmployee();
        user.setName("李四3");
        user.setAge(23);
        user.setBirthDate(DateUtils.getDate(new Date()));
        user.setSex("男");
        testModel.getUsers().add(user);

        return testModel;
    }

    /**
     * 获取默认本地文件路径
     *
     * @return
     */
    private static String getDefaultLocalPath() {
        return ResourceUtils.getResourceRootPath() + "/static";
    }
}
