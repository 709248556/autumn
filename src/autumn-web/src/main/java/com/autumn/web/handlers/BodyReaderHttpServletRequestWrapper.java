package com.autumn.web.handlers;

import com.autumn.exception.ExceptionUtils;
import org.apache.commons.io.IOUtils;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * 流转换器
 * <p>
 * </p>
 *
 * @description: TODO
 * @author: 老码农
 * @create: 2019-11-14 23:51
 **/
public class BodyReaderHttpServletRequestWrapper extends HttpServletRequestWrapper {

    private Charset charset = StandardCharsets.UTF_8;
    private int bufferSize = 4096;
    private final byte[] body;

    public BodyReaderHttpServletRequestWrapper(HttpServletRequest request) throws IOException {
        super(request);
        this.body = this.inputBodyBytes(request.getInputStream());
    }

    /**
     * 获取体
     *
     * @return
     */
    public byte[] getBody() {
        return this.body;
    }

    /**
     * 获取编码
     *
     * @return
     */
    public Charset getCharset() {
        return this.charset;
    }

    /**
     * 设置编码
     *
     * @param charset
     */
    public void setCharset(Charset charset) {
        if (charset != null) {
            this.charset = charset;
        } else {
            this.charset = StandardCharsets.UTF_8;
        }
    }

    /**
     * 获取缓冲区大小
     *
     * @return
     */
    public int getBufferSize() {
        return this.bufferSize;
    }

    /**
     * 设置缓冲区大小
     *
     * @param bufferSize 缓冲区大小
     */
    public void setBufferSize(int bufferSize) {
        if (bufferSize > 0) {
            this.bufferSize = bufferSize;
        } else {
            throw ExceptionUtils.throwArgumentException("bufferSize", "bufferSize不能小于或等于零。");
        }
    }

    private String parameterString = null;

    /**
     * 获取请求参数
     *
     * @return
     */
    public String getParameterString() {
        if (this.parameterString == null) {
            StringBuilder sb = new StringBuilder(100);
            int index = 0;
            for (Map.Entry<String, String[]> entry : this.getParameterMap().entrySet()) {
                if (index > 0) {
                    sb.append(entry.getKey()).append("&");
                }
                sb.append(entry.getKey()).append("=");
                for (int i = 0; i < entry.getValue().length; i++) {
                    if (i > 0) {
                        sb.append(",");
                    }
                    sb.append(entry.getValue()[i]);
                }
                index++;
            }
            this.parameterString = sb.toString();
        }
        return this.parameterString;
    }

    /**
     * 获取请求所有的String
     *
     * @return
     */
    public String getRequestFullString() {
        StringBuilder sb = new StringBuilder(this.body.length + this.getParameterString().length() + 20);
        sb.append("parameter:");
        sb.append(this.getParameterString());
        sb.append(",body:");
        sb.append(this.getBodyString());
        return sb.toString();
    }

    private String bodyString = null;

    /**
     * 获取请求Body
     *
     * @return
     */
    public final String getBodyString() {
        if (this.bodyString == null) {
            this.bodyString = this.getBodyString(this.getCharset());
        }
        return this.bodyString;
    }

    /**
     * 获取请求Body
     *
     * @param charset 编码
     * @return
     */
    public final String getBodyString(Charset charset) {
        if (charset == null) {
            charset = this.getCharset();
        }
        return new String(this.body, charset);
    }

    /**
     * 输入体字节集合
     *
     * @param inputStream
     * @return
     * @throws IOException
     */
    protected byte[] inputBodyBytes(ServletInputStream inputStream) throws IOException {
        if (inputStream == null) {
            return new byte[0];
        }
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[this.getBufferSize()];
            int len;
            while ((len = inputStream.read(buffer)) > -1) {
                byteArrayOutputStream.write(buffer, 0, len);
            }
            byteArrayOutputStream.flush();
            return byteArrayOutputStream.toByteArray();
        } finally {
            if (inputStream != null) {
                IOUtils.closeQuietly(inputStream);
            }
        }
    }

    @Override
    public BufferedReader getReader() throws IOException {
        return new BufferedReader(new InputStreamReader(this.getInputStream()));
    }

    private ServletInputStream inputStream = null;

    @Override
    public ServletInputStream getInputStream() throws IOException {
        if (this.inputStream == null) {
            final ByteArrayInputStream arrayInputStream = new ByteArrayInputStream(this.body);
            this.inputStream = new ServletInputStream() {
                @Override
                public int read() throws IOException {
                    return arrayInputStream.read();
                }

                @Override
                public boolean isFinished() {
                    return false;
                }

                @Override
                public boolean isReady() {
                    return false;
                }

                @Override
                public void setReadListener(ReadListener readListener) {

                }
            };
        }
        return this.inputStream;
    }

}
