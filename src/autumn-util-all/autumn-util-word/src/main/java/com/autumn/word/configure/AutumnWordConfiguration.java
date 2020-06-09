package com.autumn.word.configure;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.jodconverter.DocumentConverter;
import org.jodconverter.LocalConverter;
import org.jodconverter.office.LocalOfficeManager;
import org.jodconverter.office.OfficeManager;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.autumn.word.WordEvaluate;
import com.autumn.word.impl.WordEvaluateImpl;
import com.autumn.word.pdf.PdfConverter;
import com.autumn.word.pdf.impl.Docx4JPdfConverterImpl;
import com.autumn.word.pdf.impl.JodPdfConverterImpl;

/**
 * Word 配置
 * 
 * @author 老码农 2019-04-24 00:11:34
 */
@Configuration
@EnableConfigurationProperties({ JodLocalConverterProperties.class })
public class AutumnWordConfiguration {

	/**
	 * 
	 * @param properties
	 * @return
	 */
	private OfficeManager createOfficeManager(JodLocalConverterProperties properties) {
		final LocalOfficeManager.Builder builder = LocalOfficeManager.builder();
		if (!StringUtils.isBlank(properties.getPortNumbers())) {
			final Set<Integer> iports = new HashSet<>();
			for (final String portNumber : StringUtils.split(properties.getPortNumbers(), ", ")) {
				iports.add(NumberUtils.toInt(portNumber, 2002));
			}
			builder.portNumbers(ArrayUtils.toPrimitive(iports.toArray(new Integer[iports.size()])));
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
		return builder.build();
	}

	/**
	 * 注册 OfficeManager
	 * 
	 * @param properties
	 *            属性
	 * @return
	 */
	@Bean(initMethod = "start", destroyMethod = "stop")
	@ConditionalOnMissingBean(value = { OfficeManager.class })
	@ConditionalOnProperty(prefix = JodLocalConverterProperties.PREFIX, name = {
			"enabled" }, havingValue = "true")
	public OfficeManager autumnWordOfficeManager(JodLocalConverterProperties properties) {
		return createOfficeManager(properties);
	}

	/**
	 * 注册 DocumentConverter
	 * 
	 * @param officeManager
	 * @return
	 */
	@Bean
	@ConditionalOnMissingBean(value = { DocumentConverter.class })
	@ConditionalOnBean({ OfficeManager.class })
	@ConditionalOnProperty(prefix = JodLocalConverterProperties.PREFIX, name = {
			"enabled" }, havingValue = "true")
	public DocumentConverter autumnWordDocumentConverter(OfficeManager officeManager) {
		return LocalConverter.make(officeManager);
	}

	/**
	 * 注册 jodDocumentPdfConverter
	 * 
	 * @return
	 */
	@Bean
	@ConditionalOnBean(value = { DocumentConverter.class })
	@ConditionalOnMissingBean(value = { PdfConverter.class })
	public PdfConverter autumnJodDocumentPdfConverter(DocumentConverter cocumentConverter) {
		return new JodPdfConverterImpl(cocumentConverter);
	}

	/**
	 * 注册 docx4JPdfConverter
	 * 
	 * @return
	 */
	@Bean
	@ConditionalOnMissingBean(value = { PdfConverter.class, DocumentConverter.class })
	public PdfConverter autumnDocx4JPdfConverter() {
		return new Docx4JPdfConverterImpl();
	}

	/**
	 * 注册 WordEvaluate
	 * @param pdfConverter
	 * @return
	 */
	@Bean
	@ConditionalOnBean(value = { PdfConverter.class })
	@ConditionalOnMissingBean(value = { WordEvaluate.class })
	public WordEvaluate autumnWordEvaluate(PdfConverter pdfConverter) {
		return new WordEvaluateImpl(pdfConverter);
	}
}
