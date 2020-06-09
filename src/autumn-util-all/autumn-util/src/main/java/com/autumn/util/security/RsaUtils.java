package com.autumn.util.security;

import com.autumn.exception.ExceptionUtils;
import com.autumn.util.Base64Utils;
import org.apache.commons.io.IOUtils;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.security.*;
import java.security.interfaces.RSAPrivateCrtKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * Rsa 实例工具
 *
 * @author 老码农 2018-08-20 14:22:13
 */
public class RsaUtils {

    /**
     * RSA标记
     */
    public static final String RSA_ALGORITHM = "RSA";

    /**
     * 签名标记
     */
    public static final String SIGN_ALGORITHMS = "SHA1WithRSA";

    /**
     * 默认键大小
     */
    public static final int DEFAULT_KEY_SIZE = 1024;

    private static final KeyFactory RSA_KEYFACTORY;

    static {
        try {
            RSA_KEYFACTORY = KeyFactory.getInstance(RSA_ALGORITHM);
        } catch (NoSuchAlgorithmException e) {
            throw ExceptionUtils.throwSystemException("没有Rsa KeyFactory 实例:" + e.getMessage());
        }
    }

    /**
     * 生成 KeyPair
     *
     * @param size 大小
     * @return
     */
    public static KeyPair generateKeyPair(int size) {
        KeyPairGenerator keyPairGen;
        try {
            keyPairGen = KeyPairGenerator.getInstance(RSA_ALGORITHM);
        } catch (NoSuchAlgorithmException e) {
            throw ExceptionUtils.throwSystemException("无 Rsa 的 KeyPairGenerator 实例。");
        }
        keyPairGen.initialize(size);
        return keyPairGen.generateKeyPair();
    }

    /**
     * 生成 1024 位 的KeyPair
     *
     * @return
     */
    public static KeyPair generateKeyPair() {
        return generateKeyPair(DEFAULT_KEY_SIZE);
    }

    /**
     * 获取私钥
     *
     * @param base64PrivateKey 私钥字符串（经过base64编码）
     * @param charset          字符编码类型
     * @throws InvalidKeySpecException 无效的Key引发的异常
     */
    public static PrivateKey getPrivateKey(String base64PrivateKey, Charset charset) throws InvalidKeySpecException {
        ExceptionUtils.checkNotNull(base64PrivateKey, "base64PrivateKey");
        if (charset == null) {
            charset = Base64Utils.DEFAULT_CHARSET;
        }
        byte[] keyBytes = Base64Utils.decode(base64PrivateKey.getBytes(charset));
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
        return RSA_KEYFACTORY.generatePrivate(keySpec);
    }

    /**
     * 获取私钥(UTF-8)
     *
     * @param base64PrivateKey 私钥字符串（经过base64编码）
     * @throws InvalidKeySpecException 无效的Key引发的异常
     */
    public static PrivateKey getPrivateKey(String base64PrivateKey) throws InvalidKeySpecException {
        return getPrivateKey(base64PrivateKey, Base64Utils.DEFAULT_CHARSET);
    }

    /**
     * 获取公钥
     *
     * @param base64PublicKey 公钥字符串（经过base64编码）
     * @param charset         字符编码类型
     * @throws InvalidKeySpecException 无效的Key引发的异常
     */
    public static PublicKey getPublicKey(String base64PublicKey, Charset charset) throws InvalidKeySpecException {
        ExceptionUtils.checkNotNull(base64PublicKey, "base64PublicKey");
        if (charset == null) {
            charset = Base64Utils.DEFAULT_CHARSET;
        }
        byte[] keyBytes = Base64Utils.decode(base64PublicKey.getBytes(charset));
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        return RSA_KEYFACTORY.generatePublic(keySpec);
    }

    /**
     * 获取公钥(UTF-8)
     *
     * @param base64PublicKey 公钥字符串（经过base64编码）
     * @return
     * @throws InvalidKeySpecException
     */
    public static PublicKey getPublicKey(String base64PublicKey) throws InvalidKeySpecException {
        return getPublicKey(base64PublicKey, Base64Utils.DEFAULT_CHARSET);
    }

    /**
     * 读取公钥
     *
     * @param privateKey 私钥
     * @return
     */
    public static PublicKey readPublicKey(PrivateKey privateKey) throws InvalidKeySpecException,
            NoSuchAlgorithmException {
        RSAPrivateCrtKey rsaPrivateKey;
        if (!(privateKey instanceof RSAPrivateCrtKey)) {
            String base64 = getKeyBase64(privateKey, Base64Utils.DEFAULT_CHARSET);
            privateKey = getPrivateKey(base64, Base64Utils.DEFAULT_CHARSET);
        }
        rsaPrivateKey = (RSAPrivateCrtKey) privateKey;
        RSAPublicKeySpec publicKeySpec = new RSAPublicKeySpec(rsaPrivateKey.getModulus(), rsaPrivateKey.getPublicExponent());
        KeyFactory keyFactory = KeyFactory.getInstance(RSA_ALGORITHM);
        return keyFactory.generatePublic(publicKeySpec);
    }

    /**
     * 获取密钥的Base64位编码
     *
     * @param key     密钥
     * @param charset 字符编码类型
     * @return
     */
    public static String getKeyBase64(Key key, Charset charset) {
        ExceptionUtils.checkNotNull(key, "key");
        if (charset == null) {
            charset = Base64Utils.DEFAULT_CHARSET;
        }
        return new String(Base64Utils.encode(key.getEncoded()), charset);
    }

    /**
     * 获取密钥的Base64位编码
     *
     * @param key 密钥
     * @return
     */
    public static String getKeyBase64(Key key) {
        return getKeyBase64(key, Base64Utils.DEFAULT_CHARSET);
    }

    /**
     * 签名
     *
     * @param content    内容
     * @param privateKey 私钥
     * @return
     * @throws InvalidKeyException 无效的键引发的异常
     * @throws SignatureException  签名引发的异常
     */
    public static byte[] sign(byte[] content, PrivateKey privateKey) throws InvalidKeyException, SignatureException {
        ExceptionUtils.checkNotNull(content, "content");
        ExceptionUtils.checkNotNull(privateKey, "privateKey");
        if (content.length == 0) {
            return new byte[0];
        }
        Signature signature;
        try {
            signature = Signature.getInstance(SIGN_ALGORITHMS);
        } catch (NoSuchAlgorithmException e) {
            throw ExceptionUtils.throwSystemException("没有Rsa Signature 实例:" + e.getMessage(), e);
        }
        signature.initSign(privateKey);
        signature.update(content);
        return signature.sign();
    }

    /**
     * 签名验证
     *
     * @param content   内容
     * @param sign      签名
     * @param publicKey 公钥
     * @return 验证成功则返回 true,否则返回 false
     * @throws InvalidKeyException
     * @throws SignatureException
     */
    public static boolean signVerify(byte[] content, byte[] sign, PublicKey publicKey)
            throws InvalidKeyException, SignatureException {
        ExceptionUtils.checkNotNull(content, "content");
        ExceptionUtils.checkNotNull(sign, "sign");
        ExceptionUtils.checkNotNull(publicKey, "publicKey");
        Signature signature;
        try {
            signature = Signature.getInstance(SIGN_ALGORITHMS);
        } catch (NoSuchAlgorithmException e) {
            throw ExceptionUtils.throwSystemException("没有Rsa Signature 实例:" + e.getMessage(), e);
        }
        signature.initVerify(publicKey);
        signature.update(content);
        return signature.verify(sign);
    }

    /**
     * 签名并转为Base64编码字符串
     *
     * @param content    内容
     * @param privateKey 私钥
     * @param charset    编码类型
     * @return
     * @throws InvalidKeyException 无效的键引发的异常
     * @throws SignatureException  签名引发的异常
     */
    public static String signToBase64String(byte[] content, PrivateKey privateKey, Charset charset)
            throws InvalidKeyException, SignatureException {
        byte[] result = sign(content, privateKey);
        byte[] str = Base64Utils.encode(result);
        if (charset == null) {
            charset = Base64Utils.DEFAULT_CHARSET;
        }
        return new String(str, charset);
    }

    /**
     * 签名并转为Base64编码字符串
     *
     * @param content    内容
     * @param privateKey 私钥
     * @return
     * @throws InvalidKeyException 无效的键引发的异常
     * @throws SignatureException  签名引发的异常
     */
    public static String signToBase64String(byte[] content, PrivateKey privateKey)
            throws InvalidKeyException, SignatureException {
        return signToBase64String(content, privateKey, Base64Utils.DEFAULT_CHARSET);
    }

    /**
     * 签名并转为Base64编码字符串
     *
     * @param content    内容
     * @param privateKey 私钥
     * @param charset    编码类型
     * @return
     * @throws InvalidKeyException 无效的键引发的异常
     * @throws SignatureException  签名引发的异常
     */
    public static String signToBase64String(String content, PrivateKey privateKey, Charset charset)
            throws InvalidKeyException, SignatureException {
        ExceptionUtils.checkNotNull(content, "content");
        if (charset == null) {
            charset = Base64Utils.DEFAULT_CHARSET;
        }
        byte[] result = sign(content.getBytes(charset), privateKey);
        return new String(Base64Utils.encode(result), charset);
    }

    /**
     * 签名并转为Base64编码字符串
     *
     * @param content    内容
     * @param privateKey 私钥
     * @return
     * @throws InvalidKeyException 无效的键引发的异常
     * @throws SignatureException  签名引发的异常
     */
    public static String signToBase64String(String content, PrivateKey privateKey)
            throws InvalidKeyException, SignatureException {
        return signToBase64String(content, privateKey, Base64Utils.DEFAULT_CHARSET);
    }

    /**
     * 签名验证
     *
     * @param content    内容
     * @param signBase64 base64签名
     * @param publicKey  公钥
     * @param charset    字符编码类型
     * @return 验证成功则返回 true,否则返回 false
     * @throws InvalidKeyException
     * @throws SignatureException
     */
    public static boolean signVerify(String content, String signBase64, PublicKey publicKey, Charset charset)
            throws InvalidKeyException, SignatureException {
        ExceptionUtils.checkNotNull(content, "content");
        ExceptionUtils.checkNotNull(signBase64, "signBase64");
        if (charset == null) {
            charset = Base64Utils.DEFAULT_CHARSET;
        }
        byte[] sign = Base64Utils.decode(signBase64.getBytes(charset));
        return signVerify(content.getBytes(charset), sign, publicKey);
    }

    /**
     * 签名验证
     *
     * @param content    内容
     * @param signBase64 base64签名
     * @param publicKey  公钥
     * @return 验证成功则返回 true,否则返回 false
     * @throws InvalidKeyException
     * @throws SignatureException
     */
    public static boolean signVerify(String content, String signBase64, PublicKey publicKey)
            throws InvalidKeyException, SignatureException {
        return signVerify(content, signBase64, publicKey, Base64Utils.DEFAULT_CHARSET);
    }

    /**
     * Rsa数据块
     *
     * @param cipher
     * @param opmode
     * @param datas
     * @param keySize
     * @return
     */
    private static byte[] rsaDataBlock(Cipher cipher, int opmode, byte[] datas, int keySize) {
        int maxBlock;
        if (opmode == Cipher.DECRYPT_MODE) {
            maxBlock = keySize / 8;
        } else {
            maxBlock = keySize / 8 - 11;
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] buff;
        int i = 0;
        try {
            while (datas.length > offSet) {
                if (datas.length - offSet > maxBlock) {
                    buff = cipher.doFinal(datas, offSet, maxBlock);
                } else {
                    buff = cipher.doFinal(datas, offSet, datas.length - offSet);
                }
                out.write(buff, 0, buff.length);
                i++;
                offSet = i * maxBlock;
            }
            return out.toByteArray();
        } catch (Exception e) {
            throw ExceptionUtils.throwSystemException("数据块出错：" + e.getMessage(), e);
        } finally {
            IOUtils.closeQuietly(out);
        }
    }

    /**
     * 加密
     *
     * @param content   内容
     * @param publicKey 公钥
     * @return
     * @throws InvalidKeyException 无效的公钥
     */
    public static byte[] encrypt(byte[] content, PublicKey publicKey) throws InvalidKeyException {
        ExceptionUtils.checkNotNull(content, "content");
        ExceptionUtils.checkNotNull(publicKey, "publicKey");
        if (!(publicKey instanceof RSAPublicKey)) {
            throw ExceptionUtils.throwSystemException(publicKey.getClass().getName() + " 无法转换为 RSAPublicKey。");
        }
        RSAPublicKey rsaPublicKey = (RSAPublicKey) publicKey;
        Cipher cipher;
        try {
            cipher = Cipher.getInstance(RSA_ALGORITHM);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            throw ExceptionUtils.throwSystemException("没有Rsa Cipher 实例:" + e.getMessage(), e);
        }
        cipher.init(Cipher.ENCRYPT_MODE, rsaPublicKey);
        return rsaDataBlock(cipher, Cipher.ENCRYPT_MODE, content, rsaPublicKey.getModulus().bitLength());
    }

    /**
     * 加密并转换为Base64编码
     *
     * @param content   内容
     * @param publicKey 公钥
     * @param charset   字符编码类型
     * @return
     * @throws InvalidKeyException 无效的公钥
     */
    public static String encryptToBase64String(String content, PublicKey publicKey, Charset charset)
            throws InvalidKeyException {
        ExceptionUtils.checkNotNull(content, "content");
        if (charset == null) {
            charset = Base64Utils.DEFAULT_CHARSET;
        }
        byte[] result = encrypt(content.getBytes(charset), publicKey);
        return new String(Base64Utils.encode(result), charset);
    }

    /**
     * 使用UTF-8加密并转换为Base64编码
     *
     * @param content   内容
     * @param publicKey 公钥
     * @return
     * @throws InvalidKeyException 无效的公钥
     */
    public static String encryptToBase64String(String content, PublicKey publicKey) throws InvalidKeyException {
        return encryptToBase64String(content, publicKey, Base64Utils.DEFAULT_CHARSET);
    }

    /**
     * 解密
     *
     * @param content    内容
     * @param privateKey 私钥
     * @return
     * @throws InvalidKeyException 无效的私钥
     */
    public static byte[] decrypt(byte[] content, PrivateKey privateKey) throws InvalidKeyException {
        ExceptionUtils.checkNotNull(content, "content");
        ExceptionUtils.checkNotNull(privateKey, "privateKey");
        if (!(privateKey instanceof RSAPrivateKey)) {
            throw ExceptionUtils.throwSystemException(privateKey.getClass().getName() + " 无法转换为 RSAPrivateKey。");
        }
        RSAPrivateKey rsaPrivateKey = (RSAPrivateKey) privateKey;
        Cipher cipher;
        try {
            cipher = Cipher.getInstance(RSA_ALGORITHM);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            throw ExceptionUtils.throwSystemException("没有Rsa Cipher 实例:" + e.getMessage(), e);
        }
        cipher.init(Cipher.DECRYPT_MODE, rsaPrivateKey);
        return rsaDataBlock(cipher, Cipher.DECRYPT_MODE, content, rsaPrivateKey.getModulus().bitLength());
    }

    /**
     * 根据Base64字符解密为源文
     *
     * @param base64Content base64源文
     * @param privateKey    私钥
     * @param charset       字符编码
     * @return
     * @throws InvalidKeyException 无效的私钥
     */
    public static String decryptFormString(String base64Content, PrivateKey privateKey, Charset charset)
            throws InvalidKeyException {
        ExceptionUtils.checkNotNull(base64Content, "base64Content");
        if (charset == null) {
            charset = Base64Utils.DEFAULT_CHARSET;
        }
        byte[] decode = Base64Utils.decodeFromString(base64Content, charset);
        byte[] result = decrypt(decode, privateKey);
        return new String(result, charset);
    }

    /**
     * 使用UTF-8根据Base64字符解密为源文
     *
     * @param base64Content base64源文
     * @param privateKey    私钥
     * @return
     * @throws InvalidKeyException 无效的私钥
     */
    public static String decryptFormString(String base64Content, PrivateKey privateKey) throws InvalidKeyException {
        return decryptFormString(base64Content, privateKey, Base64Utils.DEFAULT_CHARSET);
    }

    /**
     * 转换为 Pkcs1 公钥
     *
     * @param pkcs8Base64PrivateKey pkcs8(java)的Base64格式的 公钥
     * @return
     */
    public static String toPkcs1PublicKey(String pkcs8Base64PublicKey) throws IOException {
        return toPkcs1PublicKey(pkcs8Base64PublicKey, Base64Utils.DEFAULT_CHARSET);
    }

    /**
     * 转换为 Pkcs1 公钥
     *
     * @param pkcs8Base64PrivateKey pkcs8(java)的Base64格式的 公钥
     * @param charset               编码
     * @return
     */
    public static String toPkcs1PublicKey(String pkcs8Base64PublicKey, Charset charset) throws IOException {
        byte[] pubBytes = Base64Utils.decodeFromString(pkcs8Base64PublicKey, charset);
        return toPkcs1PublicKey(pubBytes, charset);
    }

    /**
     * 转换为 Pkcs1 公钥
     *
     * @param pkcs8Base64PrivateKey pkcs8(java)的Base64格式的 公钥
     * @return
     */
    public static String toPkcs1PublicKey(PublicKey pkcs8PublicKey) throws IOException {
        return toPkcs1PublicKey(pkcs8PublicKey, Base64Utils.DEFAULT_CHARSET);
    }

    /**
     * 转换为 Pkcs1 公钥
     *
     * @param pkcs8PublicKey pkcs8(java) 公钥
     * @param charset        编码
     * @return
     */
    public static String toPkcs1PublicKey(PublicKey pkcs8PublicKey, Charset charset) throws IOException {
        return toPkcs1PublicKey(pkcs8PublicKey.getEncoded(), charset);
    }

    /**
     * 转换为 Pkcs1 公钥
     *
     * @param pkcs8PublicKey pkcs8(java) 公钥
     * @param charset        编码
     * @return
     */
    private static String toPkcs1PublicKey(byte[] pubBytes, Charset charset) throws IOException {
        SubjectPublicKeyInfo spkInfo = SubjectPublicKeyInfo.getInstance(pubBytes);
        ASN1Primitive primitive = spkInfo.parsePublicKey();
        byte[] publicKeyPKCS1 = primitive.getEncoded();
        return Base64Utils.encodeToString(publicKeyPKCS1, charset);
    }

    /**
     * 转换为 Pkcs1 私钥
     *
     * @param pkcs8Base64PrivateKey pkcs8(java)的Base64格式的 私钥
     * @return
     */
    public static String toPkcs1PrivateKey(String pkcs8Base64PrivateKey) throws IOException {
        return toPkcs1PrivateKey(pkcs8Base64PrivateKey, Base64Utils.DEFAULT_CHARSET);
    }

    /**
     * 转换为 Pkcs1 私钥
     *
     * @param pkcs8Base64PrivateKey pkcs8(java)的Base64格式的 私钥
     * @param charset               编码
     * @return
     */
    public static String toPkcs1PrivateKey(String pkcs8Base64PrivateKey, Charset charset) throws IOException {
        byte[] privBytes = Base64Utils.decodeFromString(pkcs8Base64PrivateKey, charset);
        return toPkcs1PrivateKey(privBytes, charset);
    }

    /**
     * 转换为 Pkcs1 私钥
     *
     * @param pkcs8Base64PrivateKey pkcs8(java)的Base64格式的 私钥
     * @return
     */
    public static String toPkcs1PrivateKey(PrivateKey pkcs8PrivateKey) throws IOException {
        return toPkcs1PrivateKey(pkcs8PrivateKey, Base64Utils.DEFAULT_CHARSET);
    }

    /**
     * 转换为 Pkcs1 私钥
     *
     * @param pkcs8Base64PrivateKey pkcs8(java)的Base64格式的 私钥
     * @param charset               编码
     * @return
     */
    public static String toPkcs1PrivateKey(PrivateKey pkcs8PrivateKey, Charset charset) throws IOException {
        return toPkcs1PrivateKey(pkcs8PrivateKey.getEncoded(), charset);
    }

    /**
     * 转换为 Pkcs1 私钥
     *
     * @param pkcs8Base64PrivateKey pkcs8(java)的 私钥
     * @param charset               编码
     * @return
     */
    private static String toPkcs1PrivateKey(byte[] privBytes, Charset charset) throws IOException {
        PrivateKeyInfo pkInfo = PrivateKeyInfo.getInstance(privBytes);
        ASN1Encodable encodable = pkInfo.parsePrivateKey();
        ASN1Primitive primitive = encodable.toASN1Primitive();
        byte[] privateKeyPKCS1 = primitive.getEncoded();
        return Base64Utils.encodeToString(privateKeyPKCS1, charset);
    }

    /**
     * 转换为 Pkcs8 公钥
     *
     * @param pkcs1Base64PublicKey pkcs1的Base64格式的 公钥
     * @return
     */
    public static String toPkcs8PublicKey(String pkcs1Base64PublicKey) throws IOException {
        return toPkcs8PublicKey(pkcs1Base64PublicKey, Base64Utils.DEFAULT_CHARSET);
    }

    /**
     * 转换为 Pkcs8 公钥
     *
     * @param pkcs1Base64PrivateKey pkcs1的Base64格式的 公钥
     * @param charset               编码
     * @return
     */
    public static String toPkcs8PublicKey(String pkcs1Base64PublicKey, Charset charset) throws IOException {
        byte[] publicBytes = Base64Utils.decodeFromString(pkcs1Base64PublicKey, charset);
        AlgorithmIdentifier algorithmIdentifier = new AlgorithmIdentifier(PKCSObjectIdentifiers.pkcs8ShroudedKeyBag);
        ASN1Sequence asn1Encodables = ASN1Sequence.getInstance(publicBytes);
        SubjectPublicKeyInfo publicKeyInfo = new SubjectPublicKeyInfo(algorithmIdentifier, asn1Encodables);
        byte[] pkcs1Bytes = publicKeyInfo.getEncoded();
        return Base64Utils.encodeToString(pkcs1Bytes, charset);
    }

    /**
     * 转换为 Pkcs8 私钥
     *
     * @param pkcs1Base64PrivateKey pkcs1 的 Base64 格式的 私钥
     * @return
     */
    public static String toPkcs8PrivateKey(String pkcs1Base64PrivateKey) throws IOException {
        return toPkcs8PrivateKey(pkcs1Base64PrivateKey, Base64Utils.DEFAULT_CHARSET);
    }

    /**
     * 转换为 Pkcs8 私钥
     *
     * @param pkcs8Base64PrivateKey pkcs1 的 Base64 格式的 私钥
     * @param charset               编码
     * @return
     */
    public static String toPkcs8PrivateKey(String pkcs1Base64PrivateKey, Charset charset) throws IOException {
        byte[] privBytes = Base64Utils.decodeFromString(pkcs1Base64PrivateKey, charset);
        AlgorithmIdentifier algorithmIdentifier = new AlgorithmIdentifier(PKCSObjectIdentifiers.pkcs8ShroudedKeyBag);
        ASN1Sequence asn1Encodables = ASN1Sequence.getInstance(privBytes);
        PrivateKeyInfo privateKeyInfo = new PrivateKeyInfo(algorithmIdentifier, asn1Encodables);
        byte[] pkcs1Bytes = privateKeyInfo.getEncoded();
        return Base64Utils.encodeToString(pkcs1Bytes, charset);
    }

}
