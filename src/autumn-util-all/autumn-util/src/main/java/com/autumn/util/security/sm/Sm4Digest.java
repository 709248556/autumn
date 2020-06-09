package com.autumn.util.security.sm;

import com.autumn.util.Base64Utils;

import java.nio.charset.Charset;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author 老码农
 * <p>
 * 2018-01-09 15:51:29
 */
public class Sm4Digest {

    /**
     * gbk 编码
     */
    public static final Charset GBK_CHARSET = Charset.forName("GBK");

    private String secretKey = "";

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    private String iv = "";

    public String getIv() {
        return iv;
    }

    public void setIv(String iv) {
        this.iv = iv;
    }

    private boolean hexString = false;

    public boolean isHexString() {
        return hexString;
    }

    public void setHexString(boolean hexString) {
        this.hexString = hexString;
    }

    public Sm4Digest() {
    }

    private final static String ENCRYPT_PATTERN = "\\s*|\t|\r|\n";

    public String encryptDataEcb(String plainText) {
        try {
            Sm4Context ctx = new Sm4Context();
            ctx.isPadding = true;
            ctx.mode = Sm4.SM4_ENCRYPT;

            byte[] keyBytes;
            if (hexString) {
                keyBytes = SmUtils.hexStringToBytes(secretKey);
            } else {
                keyBytes = secretKey.getBytes();
            }

            Sm4 sm4 = new Sm4();
            sm4.setSm4KeyEnc(ctx, keyBytes);
            byte[] encrypted = sm4.cryptEcb(ctx, plainText.getBytes(GBK_CHARSET));

            String cipherText = new String(Base64Utils.encode(encrypted), GBK_CHARSET);

            //String cipherText = new BASE64Encoder().encode(encrypted);

            if (cipherText != null && cipherText.trim().length() > 0) {
                Pattern p = Pattern.compile(ENCRYPT_PATTERN);
                Matcher m = p.matcher(cipherText);
                cipherText = m.replaceAll("");
            }
            return cipherText;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String decryptDataEcb(String cipherText) {
        try {
            Sm4Context ctx = new Sm4Context();
            ctx.isPadding = true;
            ctx.mode = Sm4.SM4_DECRYPT;

            byte[] keyBytes;
            if (hexString) {
                keyBytes = SmUtils.hexStringToBytes(secretKey);
            } else {
                keyBytes = secretKey.getBytes();
            }

            Sm4 sm4 = new Sm4();
            sm4.setSm4KeyDec(ctx, keyBytes);


            byte[] decrypted = sm4.cryptEcb(ctx, Base64Utils.decodeFromString(cipherText, GBK_CHARSET));

            //byte[] decrypted = sm4.cryptEcb(ctx, new BASE64Decoder().decodeBuffer(cipherText));

            return new String(decrypted, "GBK");
        } catch (Exception e) {
            return null;
        }
    }

    public String encryptDataCbc(String plainText) {
        try {
            Sm4Context ctx = new Sm4Context();
            ctx.isPadding = true;
            ctx.mode = Sm4.SM4_ENCRYPT;
            byte[] keyBytes;
            byte[] ivBytes;
            if (hexString) {
                keyBytes = SmUtils.hexStringToBytes(secretKey);
                ivBytes = SmUtils.hexStringToBytes(iv);
            } else {
                keyBytes = secretKey.getBytes();
                ivBytes = iv.getBytes();
            }

            Sm4 sm4 = new Sm4();
            sm4.setSm4KeyEnc(ctx, keyBytes);
            byte[] encrypted = sm4.cryptCbc(ctx, ivBytes, plainText.getBytes(GBK_CHARSET));

			String cipherText = new String(Base64Utils.encode(encrypted), GBK_CHARSET);

           // String cipherText = new BASE64Encoder().encode(encrypted);

            if (cipherText != null && cipherText.trim().length() > 0) {
                Pattern p = Pattern.compile(ENCRYPT_PATTERN);
                Matcher m = p.matcher(cipherText);
                cipherText = m.replaceAll("");
            }
            return cipherText;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String decryptDataCbc(String cipherText) {
        return decryptDataCbc(cipherText, GBK_CHARSET);
    }

    public String decryptDataCbc(String cipherText, Charset charset) {
        try {
            if (charset == null) {
                charset = GBK_CHARSET;
            }
            Sm4Context ctx = new Sm4Context();
            ctx.isPadding = true;
            ctx.mode = Sm4.SM4_DECRYPT;

            byte[] keyBytes;
            byte[] ivBytes;
            if (hexString) {
                keyBytes = SmUtils.hexStringToBytes(secretKey);
                ivBytes = SmUtils.hexStringToBytes(iv);
            } else {
                keyBytes = secretKey.getBytes();
                ivBytes = iv.getBytes();
            }

            Sm4 sm4 = new Sm4();
            sm4.setSm4KeyDec(ctx, keyBytes);

            byte[] decrypted = sm4.cryptCbc(ctx, ivBytes, Base64Utils.decodeFromString(cipherText, GBK_CHARSET));

            //byte[] decrypted = sm4.cryptCbc(ctx, ivBytes, new BASE64Decoder().decodeBuffer(cipherText));
            return new String(decrypted, charset);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
