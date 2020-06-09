package com.autumn.util.security;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.autumn.util.ByteUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.output.ByteArrayOutputStream;

import com.autumn.exception.ExceptionUtils;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

/**
 * 哈希 帮助
 *
 * @author shao
 * @date 2017/11/7 21:08
 */
public class HashUtils {

    /**
     * Md5
     */
    public static final String ALGORITHM_MD5 = "MD5";

    /**
     * SHA-256
     */
    public static final String ALGORITHM_SHA_256 = "SHA-256";

    /**
     * HmacSHA256
     */
    public static final String ALGORITHM_HMAC_SHA_256 = "HmacSHA256";

    /**
     * 获取 Mac 实例
     *
     * @param algorithm 算法
     * @return
     */
    private static Mac getMacInstance(String algorithm) {
        try {
            return Mac.getInstance(algorithm);
        } catch (NoSuchAlgorithmException err) {
            throw ExceptionUtils.throwSystemException("包中无法加载 " + algorithm + " 算法。");
        }
    }

    /**
     * 获取 MessageDigest 实例
     *
     * @param algorithm 算法
     * @return
     */
    private static MessageDigest getMessageDigestInstance(String algorithm) {
        try {
            return MessageDigest.getInstance(algorithm);
        } catch (NoSuchAlgorithmException err) {
            throw ExceptionUtils.throwSystemException("包中无法加载 " + algorithm + " 算法。");
        }
    }

    /**
     * 采用 utf-8 获取 hmacSHA256
     *
     * @param data 数据
     * @param key  键
     * @return
     * @throws InvalidKeyException
     */
    public static String hmacSHA256(String data, String key) throws InvalidKeyException {
        return hmacSHA256(data, key, StandardCharsets.UTF_8);
    }

    /**
     * 获取 hmacSHA256
     *
     * @param data    数据
     * @param key     键
     * @param charset 编码
     * @return
     * @throws InvalidKeyException
     */
    public static String hmacSHA256(String data, String key, Charset charset) throws InvalidKeyException {
        if (charset == null) {
            charset = StandardCharsets.UTF_8;
        }
        Mac mac = getMacInstance(ALGORITHM_HMAC_SHA_256);
        SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(charset), ALGORITHM_HMAC_SHA_256);
        mac.init(keySpec);
        byte[] array = mac.doFinal(data.getBytes(charset));
        return ByteUtils.getHexString(array);
    }

    /**
     * 采用utf-8生成 sha256
     *
     * @param data 数据
     * @return
     */
    public static String sha256(String data) {
        return sha256(data, StandardCharsets.UTF_8);
    }

    /**
     * 生成 sha256
     *
     * @param data    数据
     * @param charset 编码
     * @return 返回小写
     */
    public static String sha256(String data, Charset charset) {
        ExceptionUtils.checkNotNull(data, "data");
        if (charset == null) {
            charset = StandardCharsets.UTF_8;
        }
        MessageDigest digest = getMessageDigestInstance(ALGORITHM_SHA_256);
        digest.update(data.getBytes(charset));
        return ByteUtils.getHexString(digest.digest());
    }

    /**
     * 采用utf-8生成 md5
     *
     * @param data 数据
     * @return
     */
    public static String md5(String data) {
        return md5(data, StandardCharsets.UTF_8);
    }

    /**
     * 生成 md5
     *
     * @param data    数据
     * @param charset 编码
     * @return 返回小写
     */
    public static String md5(String data, Charset charset) {
        ExceptionUtils.checkNotNull(data, "data");
        if (charset == null) {
            charset = StandardCharsets.UTF_8;
        }
        MessageDigest digest = getMessageDigestInstance(ALGORITHM_MD5);
        digest.update(data.getBytes(charset));
        return ByteUtils.getHexString(digest.digest());
    }

    /**
     * 获取流的Md5
     *
     * @param digest       消息
     * @param input        输入
     * @param outputStream 输出流
     * @return
     * @throws IOException
     * @throws NoSuchAlgorithmException
     */
    private static void writeMd5ByInputStream(MessageDigest digest, InputStream input, ByteArrayOutputStream outputStream) throws IOException {
        ExceptionUtils.checkNotNull(input, "input");
        try {
            byte[] buffer = new byte[4096];
            int length;
            while ((length = input.read(buffer, 0, buffer.length)) != -1) {
                digest.update(buffer, 0, length);
                if (outputStream != null) {
                    outputStream.write(buffer, 0, length);
                }
            }
        } finally {
            IOUtils.closeQuietly(input);
        }
    }

    /**
     * 获取流的Md5
     *
     * @param input 输入
     * @return
     * @throws IOException
     * @throws NoSuchAlgorithmException
     */
    public static String toMd5ByInputStream(InputStream input) throws IOException {
        return toMd5ByInputStream(input, null);
    }

    /**
     * 获取流的Md5
     *
     * @param input        输入
     * @param outputStream 输出流
     * @return
     * @throws IOException
     * @throws NoSuchAlgorithmException
     */
    public static void writeMd5ByInputStream(InputStream input, ByteArrayOutputStream outputStream) throws IOException {
        MessageDigest digest = getMessageDigestInstance(ALGORITHM_MD5);
        writeMd5ByInputStream(digest, input, outputStream);
    }

    /**
     * 获取流的Md5,并写入输也流
     *
     * @param input        输入
     * @param outputStream 输出流
     * @return
     * @throws IOException
     * @throws NoSuchAlgorithmException
     */
    public static String toMd5ByInputStream(InputStream input, ByteArrayOutputStream outputStream) throws IOException {
        MessageDigest digest = getMessageDigestInstance(ALGORITHM_MD5);
        writeMd5ByInputStream(digest, input, outputStream);
        BigInteger bigInt = new BigInteger(1, digest.digest());
        return bigInt.toString(16);
    }
}
