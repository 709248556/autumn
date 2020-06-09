package com.autumn.util;

import org.apache.commons.io.IOUtils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Base64;


/**
 * @author 老码农 2018-08-20 15:16:29
 */
public class Base64Utils {

    /**
     * 默认编码
     */
    public static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;

    /**
     * 是否为 Base64 编码
     *
     * @param str 字符
     * @return
     */
    public static boolean isBase64(String str) {
        if (StringUtils.isNullOrBlank(str)) {
            return false;
        } else {
            if (str.length() % 4 != 0) {
                return false;
            }
            char[] strChars = str.toCharArray();
            for (char c : strChars) {
                if ((c >= 'a' && c <= 'z')
                        || (c >= 'A' && c <= 'Z')
                        || (c >= '0' && c <= '9')
                        || c == '+'
                        || c == '/'
                        || c == '=') {
                    continue;
                } else {
                    return false;
                }
            }
            return true;
        }
    }

    /**
     * 编码
     *
     * @param source 源
     * @return
     */
    public static byte[] encode(byte[] source) {
        if (source == null) {
            return null;
        }
        return Base64.getEncoder().encode(source);
    }

    /**
     * 编码转为字符串
     *
     * @param source  源
     * @param charset 字符编码类型
     * @return
     */
    public static String encodeToString(byte[] source, Charset charset) {
        if (source == null) {
            return null;
        }
        if (charset == null) {
            charset = DEFAULT_CHARSET;
        }
        byte[] result = encode(source);
        return new String(result, charset);
    }

    /**
     * 编码转为字符串
     *
     * @param source  源
     * @param charset 字符编码类型
     * @return
     */
    public static String encodeToString(String source, Charset charset) {
        if (source == null) {
            return null;
        }
        if (charset == null) {
            charset = DEFAULT_CHARSET;
        }
        byte[] result = encode(source.getBytes(charset));
        return new String(result, charset);
    }

    /**
     * url编码
     *
     * @param source 源
     * @return
     */
    public static byte[] encodeUrlSafe(byte[] source) {
        if (source == null) {
            return null;
        }
        return Base64.getUrlEncoder().encode(source);
    }

    /**
     * url编码转为字符串
     *
     * @param source  源
     * @param charset 字符编码类型
     * @return
     */
    public static String encodeUrlSafeToString(String source, Charset charset) {
        if (source == null) {
            return null;
        }
        if (charset == null) {
            charset = DEFAULT_CHARSET;
        }
        byte[] result = encodeUrlSafe(source.getBytes(charset));
        return new String(result, charset);
    }

    /**
     * 解码
     *
     * @param source 源
     * @return
     */
    public static byte[] decode(byte[] source) {
        if (source == null) {
            return null;
        }
        return Base64.getDecoder().decode(source);
    }

    /**
     * 从字符串解码
     *
     * @param source  源
     * @param charset 字符编码类型
     * @return
     */
    public static byte[] decodeFromString(String source, Charset charset) {
        if (source == null) {
            return null;
        }
        if (charset == null) {
            charset = DEFAULT_CHARSET;
        }
        if (source.isEmpty()) {
            return new byte[0];
        }
        return decode(source.getBytes(charset));
    }

    /**
     * url解码
     *
     * @param source 源
     * @return
     */
    public static byte[] decodeUrlSafe(byte[] source) {
        if (source == null) {
            return null;
        }
        return Base64.getUrlDecoder().decode(source);
    }

    /**
     * 根据流的 Base64 解码
     *
     * @param base64Stream Base64流字
     * @return
     * @throws IOException
     */
    public static byte[] decodeToBytes(String base64Stream) throws IOException {
        byte[] result = decode(base64Stream.getBytes(StandardCharsets.UTF_8));
        return result;
        //return new BASE64Decoder().decodeBuffer(base64Stream);
    }

    /**
     * 根据流的 Base64 解码
     *
     * @param base64Stream Base64流字
     * @return
     * @throws IOException
     */
    public static ByteArrayInputStream decodeToInputStream(String base64Stream) throws IOException {
        return new ByteArrayInputStream(decodeToBytes(base64Stream));
    }

    /**
     * 根据流的 Base64 解码
     *
     * @param base64Stream Base64流
     * @return
     * @throws IOException
     */
    public static byte[] decodeToBytes(InputStream inputStream) throws IOException {
        return Base64.getDecoder().decode(IOUtils.toByteArray(inputStream));
        // return new BASE64Decoder().decodeBuffer(inputStream);
    }

    /**
     * 编码
     *
     * @param inputStream
     * @param outputStream
     * @throws IOException
     */
    public static void encode(InputStream inputStream, OutputStream outputStream) throws IOException {
        byte[] input = IOUtils.toByteArray(inputStream);
        byte[] encoderBytes = Base64.getEncoder().encode(input);
        outputStream.write(encoderBytes, 0, encoderBytes.length);
        // new BASE64Encoder().encode(inputStream, outputStream);
    }

    /**
     * 编码
     *
     * @param bytes        字节数组
     * @param outputStream
     * @throws IOException
     */
    public static void encode(byte[] bytes, OutputStream outputStream) throws IOException {
        byte[] encoderBytes = Base64.getEncoder().encode(bytes);
        outputStream.write(encoderBytes, 0, encoderBytes.length);
        //new BASE64Encoder().encode(bytes, outputStream);
    }

    /**
     * 编码
     *
     * @param inputStream 输入流
     * @param charset     编码类型
     * @return
     * @throws IOException
     */
    public static String encodeToString(InputStream inputStream, Charset charset) throws IOException {
        byte[] input = IOUtils.toByteArray(inputStream);
        byte[] encoderBytes = Base64.getEncoder().encode(input);
        if (charset == null) {
            charset = DEFAULT_CHARSET;
        }
        return new String(encoderBytes, charset);

        /*ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            new BASE64Encoder().encode(inputStream, outputStream);
            if (charset == null) {
                charset = DEFAULT_CHARSET;
            }
            return new String(outputStream.toByteArray(), charset);
        } finally {
            IOUtils.closeQuietly(outputStream);
        }*/
    }

    /**
     * 从字符串url解码
     *
     * @param source  源
     * @param charset 字符编码类型
     * @return
     */
    public static byte[] decodeUrlSafeFromString(String source, Charset charset) {
        if (source == null) {
            return null;
        }
        if (charset == null) {
            charset = DEFAULT_CHARSET;
        }
        if (source.isEmpty()) {
            return new byte[0];
        }
        return decodeUrlSafe(source.getBytes(charset));
    }

}
