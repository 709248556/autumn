package com.autumn.web.utils;

import com.autumn.application.dto.input.AdvancedQueryInput;
import com.autumn.application.service.DataImportApplicationService;
import com.autumn.application.service.QueryApplicationService;
import com.autumn.audited.ClientBrowserInfo;
import com.autumn.audited.WebClientBrowserInfo;
import com.autumn.exception.ExceptionUtils;
import com.autumn.util.StringUtils;
import com.autumn.util.excel.utils.ExcelUtils;
import com.autumn.web.auditing.WebClientInfoProvider;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * 控制器帮助
 * <p>
 * </p>
 *
 * @description TODO
 * @author: 老码农
 * @create: 2020-03-23 20:21
 **/
public class ControllerUtils {

    /**
     * 解析参数
     * @param id
     * @return
     */
    public static Long parseParameterById(String id) {
        if (StringUtils.isNullOrBlank(id)) {
            ExceptionUtils.throwValidationException("无效的路径。");
        }
        try {
            return Long.parseLong(id.trim());
        } catch (Exception err) {
            throw ExceptionUtils.throwValidationException("参数[" + id + "]无效,格式不正确。");
        }
    }

    /**
     * Url编号
     *
     * @param value   值
     * @param charset 编码
     * @return
     */
    public static String urlEncode(String value, Charset charset) {
        if (value == null) {
            return "";
        }
        if (charset == null) {
            charset = StandardCharsets.UTF_8;
        }
        try {
            return URLEncoder.encode(value, charset.name());
        } catch (UnsupportedEncodingException e) {
            return value;
        }
    }

    /**
     * 设置响应文件名
     *
     * @param response 响应
     * @param fileName
     */
    public static void setResponseFileName(HttpServletResponse response, String fileName) {
        WebClientInfoProvider provider = new WebClientInfoProvider();
        ClientBrowserInfo browserInfo = provider.getBrowserInfo();
        String browserName = browserInfo.getBrowserName().toLowerCase();
        if (browserName.contains(WebClientBrowserInfo.BROWSER_IE.toLowerCase())) {
            fileName = urlEncode(fileName, StandardCharsets.UTF_8);
        } else {
            fileName = urlEncode(fileName, StandardCharsets.ISO_8859_1);
        }
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
    }


    /**
     * 创建Excel 响应流体
     *
     * @param service  服务
     * @param input    输入
     * @param response 响应
     * @return
     */
    public static <TKey extends Serializable, TOutputItem, TOutputDetails> StreamingResponseBody createExcelExportBody(
            QueryApplicationService<TKey, TOutputItem, TOutputDetails> service, AdvancedQueryInput input, HttpServletResponse response)
            throws UnsupportedEncodingException {
        Workbook workbook = service.exportByExcel(input);
        response.setContentType("application/" + ExcelUtils.EXCEL_EXTENSION_NAME + ";charset=utf-8");
        setResponseFileName(response, service.getModuleName() + ExcelUtils.EXCEL_JOIN_EXTENSION_NAME);
        return outputStream -> {
            try {
                workbook.write(outputStream);
            } finally {
                outputStream.flush();
                outputStream.close();
            }
        };
    }

    /**
     * 创建 Excel导入模板 响应流体
     *
     * @param service  服务
     * @param response 响应
     * @return
     */
    public static <ImportTemplate> StreamingResponseBody createExcelImportTemplateBody(
            DataImportApplicationService<ImportTemplate> service, HttpServletResponse response)
            throws UnsupportedEncodingException {
        Workbook workbook = service.excelImportTemplate();
        response.setContentType("application/" + ExcelUtils.EXCEL_EXTENSION_NAME + ";charset=utf-8");
        setResponseFileName(response, service.getModuleName() + ExcelUtils.EXCEL_JOIN_EXTENSION_NAME);
        return outputStream -> {
            try {
                workbook.write(outputStream);
            } finally {
                outputStream.flush();
                outputStream.close();
            }
        };
    }
}
