package com.autumn.util.mail;

import org.apache.commons.io.IOUtils;

import javax.activation.DataSource;
import javax.mail.internet.ContentType;
import javax.mail.internet.MimeUtility;
import javax.mail.internet.ParseException;
import java.io.*;

/**
 * 数组数据源
 * <p>
 * </p>
 *
 * @description: TODO
 * @author: 老码农
 * @create: 2020-03-18 22:23
 **/
public class ByteArrayDataSource implements DataSource {

    private final byte[] data;    // data
    private final int len;
    private final String type;    // content-type
    private String name = "";

    /**
     * @param is
     * @param type
     * @throws IOException
     */
    public ByteArrayDataSource(InputStream is, String type) throws IOException {
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            byte[] buf = new byte[8192];
            int len;
            while ((len = is.read(buf)) > 0) {
                outputStream.write(buf, 0, len);
            }
            this.data = outputStream.toByteArray();
            this.len = this.data.length;
            this.type = type;
        } finally {
            IOUtils.closeQuietly(is);
        }
    }

    /**
     * @param data
     * @param type
     */
    public ByteArrayDataSource(byte[] data, String type) {
        this.data = data;
        this.len = this.data.length;
        this.type = type;
    }

    /**
     * @param data
     * @param type
     * @throws IOException
     */
    public ByteArrayDataSource(String data, String type) throws IOException {
        String charset = null;
        try {
            ContentType ct = new ContentType(type);
            charset = ct.getParameter("charset");
        } catch (ParseException pex) {
            // ignore parse error
        }
        charset = MimeUtility.javaCharset(charset);
        if (charset == null) {
            charset = MimeUtility.getDefaultJavaCharset();
        }
        // XXX - could convert to bytes on demand rather than copying here
        this.data = data.getBytes(charset);
        this.type = type;
        this.len = this.data.length;
    }


    @Override
    public InputStream getInputStream() throws IOException {
        if (data == null) {
            throw new IOException("no data");
        }
        return new ByteArrayInputStream(this.data);
    }

    @Override
    public OutputStream getOutputStream() throws IOException {
        if (data == null) {
            throw new IOException("no data");
        }
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        outputStream.write(data, 0, data.length);
        return outputStream;
    }

    @Override
    public String getContentType() {
        return this.type;
    }

    @Override
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
