package com.autumn.util;

import com.autumn.util.security.RsaUtils;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.InvalidKeySpecException;

/**
 * Rsa 测式
 *
 * @author 老码农 2018-08-20 16:07:03
 */
public class RsaTest {

    /**
     * 密钥测试
     *
     * @throws InvalidKeySpecException
     */
    @Test
    public void test1() throws InvalidKeySpecException, IOException {
        KeyPair KeyPair = RsaUtils.generateKeyPair();

        String publicKeyStr = RsaUtils.getKeyBase64(KeyPair.getPublic());
        String privateKeyStr = RsaUtils.getKeyBase64(KeyPair.getPrivate());

        System.out.println("publicKeyPkcs8:" + publicKeyStr);
        System.out.println("privateKeyPkcs8:" + privateKeyStr);

        PublicKey publicKey = RsaUtils.getPublicKey(publicKeyStr);
        PrivateKey privateKey = RsaUtils.getPrivateKey(privateKeyStr);

        String publicPkcs1KeyStr = RsaUtils.toPkcs1PublicKey(publicKey);
        String privatePkcs1KeyStr = RsaUtils.toPkcs1PrivateKey(privateKey);

        System.out.println("publicKeyPkcs1:" + publicPkcs1KeyStr);
        System.out.println("privateKeyPkcs1:" + privatePkcs1KeyStr);

        System.out.println("publicKeyPkcs8:" + RsaUtils.toPkcs8PublicKey(publicPkcs1KeyStr));
        System.out.println("privateKeyPkcs8:" + RsaUtils.toPkcs8PrivateKey(privatePkcs1KeyStr));

        System.out.println(publicKey != null);
        System.out.println(privateKey != null);

    }

    /**
     * 签名测试
     *
     * @throws InvalidKeyException
     * @throws SignatureException
     * @throws InvalidKeySpecException
     */
    @Test
    public void signTest() throws InvalidKeyException, SignatureException, InvalidKeySpecException {

        String content = "中华人民共和国";

        KeyPair KeyPair = RsaUtils.generateKeyPair();

        String privateKeyStr = RsaUtils.getKeyBase64(KeyPair.getPrivate());
        String publicKeyStr = RsaUtils.getKeyBase64(KeyPair.getPublic());

        System.out.println("privateKey:" + privateKeyStr);
        System.out.println("publicKey:" + publicKeyStr);


        String sign = RsaUtils.signToBase64String(content, KeyPair.getPrivate());
        System.out.println("sign:" + sign);

        boolean verify = RsaUtils.signVerify(content, sign, KeyPair.getPublic());
        System.out.println("verify:" + verify);
    }

    /**
     * 加解密测试
     *
     * @throws InvalidKeyException
     */
    @Test
    public void cipherTest1() throws InvalidKeyException {

        String content = "中华人民共和国";

        KeyPair KeyPair = RsaUtils.generateKeyPair();

        String privateKeyStr = RsaUtils.getKeyBase64(KeyPair.getPrivate());
        String publicKeyStr = RsaUtils.getKeyBase64(KeyPair.getPublic());

        System.out.println("privateKey:" + privateKeyStr);
        System.out.println("publicKey:" + publicKeyStr);

        byte[] encrypt = RsaUtils.encrypt(content.getBytes(StandardCharsets.UTF_8), KeyPair.getPublic());

        // System.out.println("encrypt:" + encrypt);

        byte[] decrypt = RsaUtils.decrypt(encrypt, KeyPair.getPrivate());

        System.out.println(decrypt != null);
    }

    /**
     * 加解密测试
     *
     * @throws InvalidKeyException
     */
    @Test
    public void cipherTest() throws InvalidKeyException {

        String content = "中华人民共和国";

        KeyPair KeyPair = RsaUtils.generateKeyPair();

        String privateKeyStr = RsaUtils.getKeyBase64(KeyPair.getPrivate());
        String publicKeyStr = RsaUtils.getKeyBase64(KeyPair.getPublic());

        System.out.println("privateKey:" + privateKeyStr);
        System.out.println("publicKey:" + publicKeyStr);

        String encrypt = RsaUtils.encryptToBase64String(content, KeyPair.getPublic());

        System.out.println("encrypt:" + encrypt);

        String source = RsaUtils.decryptFormString(encrypt, KeyPair.getPrivate());

        System.out.println("source:" + source);


        org.junit.Assert.assertTrue(source.equals(content));
    }
}
