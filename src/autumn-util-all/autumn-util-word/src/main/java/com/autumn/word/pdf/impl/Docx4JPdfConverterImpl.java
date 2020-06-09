package com.autumn.word.pdf.impl;

import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.io.IOUtils;
import org.docx4j.Docx4J;
import org.docx4j.convert.out.FOSettings;
import org.docx4j.fonts.IdentityPlusMapper;
import org.docx4j.fonts.Mapper;
import org.docx4j.fonts.PhysicalFont;
import org.docx4j.fonts.PhysicalFonts;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;

import com.autumn.word.pdf.PdfProperties;

/**
 * Docx4J pdf 转换
 * 
 * @author 老码农 2019-04-23 14:54:28
 */
public class Docx4JPdfConverterImpl extends AbstractPdfConverter {

	/**
	 * 字体映射
	 */
	public static final Mapper FONT_MAPPER = new IdentityPlusMapper();

	static {
		addFontMapper("黑体", "SimHei");
		addFontMapper("华文彩云", "STCAIYUN");
		addFontMapper("华文仿宋", "STFangsong");
		addFontMapper("华文行楷", "STXingkai");
		addFontMapper("华文琥珀", "STHUPO");
		addFontMapper("华文楷体", "STKAITI");
		addFontMapper("华文隶书", "STLITI");
		addFontMapper("华文宋体", "STSONG");
		addFontMapper("华文细黑", "STXIHEI");
		addFontMapper("华文新魏", "STXINWEI");
		addFontMapper("华文中宋", "STZHONGS", "STZhongsong");
		addFontMapper("楷体", "KaiTi");
		addFontMapper("隶书", "LiSu");
		addFontMapper("微软雅黑", "Microsoft Yahei");
		addFontMapper("新宋体", "NSimSun");
		addFontMapper("幼圆", "YouYuan");
		addFontMapper("宋体扩展", "simsun-extB");
		addFontMapper("仿宋", "FangSong");
		addFontMapper("仿宋_GB2312", "FangSong_GB2312");
	}

	/**
	 * 添加字体映射
	 * 
	 * @param name
	 *            字体名称
	 * @param physicalFontNames
	 *            物理字体名称集合
	 */
	private static void addFontMapper(String name, String... physicalFontNames) {
		for (String fontName : physicalFontNames) {
			PhysicalFont physicalFont = PhysicalFonts.get(fontName);
			if (physicalFont != null) {
				FONT_MAPPER.put(name, physicalFont);
				break;
			}
		}
	}

	@Override
	protected void toPdfInternal(InputStream input, OutputStream output, PdfProperties properties, boolean closeStream)
			throws Exception {
		if (properties == null) {
			properties = PdfProperties.DEFAULT;
		}
		WordprocessingMLPackage wordMLPackage = null;
		try {
			wordMLPackage = WordprocessingMLPackage.load(input);
			wordMLPackage.setFontMapper(FONT_MAPPER);
			FOSettings foSettings = Docx4J.createFOSettings();
			foSettings.setWmlPackage(wordMLPackage);
			Docx4J.toFO(foSettings, output, Docx4J.FLAG_EXPORT_PREFER_XSL);

		} finally {
			if (wordMLPackage != null) {
				wordMLPackage.reset();
				wordMLPackage = null;
			}
			if (closeStream) {
				IOUtils.closeQuietly(input);
				IOUtils.closeQuietly(output);
			}
		}
	}

}
