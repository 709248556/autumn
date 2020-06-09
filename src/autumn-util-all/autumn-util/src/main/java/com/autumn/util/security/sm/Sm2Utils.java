package com.autumn.util.security.sm;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Enumeration;

import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1InputStream;
import org.bouncycastle.asn1.ASN1Integer;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.DEROctetString;
import org.bouncycastle.asn1.DEROutputStream;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.math.ec.ECPoint;
import org.bouncycastle.util.encoders.Base64;

import com.autumn.exception.ExceptionUtils;

/**
 * 
 * @author 老码农
 *
 *         2018-01-09 15:38:51
 */
public class Sm2Utils {

	/**
	 * 使用默认加密
	 * 
	 * @param curveParameters
	 * @param publicKey
	 * @param data
	 * @return
	 * @throws IOException
	 *
	 */
	public static byte[] encrypt(byte[] publicKey, byte[] data) throws IOException {
		return encrypt(SmCurveParameters.DEFAULT_INSTANCE, publicKey, data);
	}

	/**
	 * 加密
	 * 
	 * @param curveParameters
	 * @param publicKey
	 * @param data
	 * @return
	 * @throws IOException
	 *
	 */
	public static byte[] encrypt(SmCurveParameters curveParameters, byte[] publicKey, byte[] data) throws IOException {
		ExceptionUtils.checkNotNull(curveParameters, "curveParameters");
		ExceptionUtils.checkNotNull(publicKey, "publicKey");
		ExceptionUtils.checkNotNull(data, "data");
		byte[] source = new byte[data.length];
		System.arraycopy(data, 0, source, 0, data.length);
		Cipher cipher = new Cipher();
		ECPoint userKey = curveParameters.getEcCurve().decodePoint(publicKey);
		ECPoint c1 = cipher.initializeEnc(curveParameters, userKey);
		cipher.encrypt(source);
		byte[] c3 = new byte[32];
		cipher.doFinal(c3);

		ASN1Integer x = new ASN1Integer(c1.normalize().getXCoord().toBigInteger());
		ASN1Integer y = new ASN1Integer(c1.normalize().getYCoord().toBigInteger());
		DEROctetString derDig = new DEROctetString(c3);
		DEROctetString derEnc = new DEROctetString(source);
		ASN1EncodableVector v = new ASN1EncodableVector();
		v.add(x);
		v.add(y);
		v.add(derDig);
		v.add(derEnc);
		DERSequence seq = new DERSequence(v);
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		DEROutputStream dos = new DEROutputStream(bos);
		dos.writeObject(seq);
		return bos.toByteArray();
	}

	/**
	 * 使用默认参数解密
	 * 
	 * @param privateKey
	 * @param encryptedData
	 * @return
	 * @throws IOException
	 *
	 */
	public static byte[] decrypt(byte[] privateKey, byte[] encryptedData) throws IOException {
		return decrypt(SmCurveParameters.DEFAULT_INSTANCE, privateKey, encryptedData);
	}

	/**
	 * 解密
	 * 
	 * @param curveParameters
	 * @param privateKey
	 * @param encryptedData
	 * @return
	 * @throws IOException
	 *
	 */
	@SuppressWarnings("resource")
	public static byte[] decrypt(SmCurveParameters curveParameters, byte[] privateKey, byte[] encryptedData)
			throws IOException {
		ExceptionUtils.checkNotNull(curveParameters, "curveParameters");
		ExceptionUtils.checkNotNull(privateKey, "privateKey");
		ExceptionUtils.checkNotNull(encryptedData, "encryptedData");

		byte[] enc = new byte[encryptedData.length];
		System.arraycopy(encryptedData, 0, enc, 0, encryptedData.length);
		BigInteger userD = new BigInteger(1, privateKey);
		ByteArrayInputStream bis = new ByteArrayInputStream(enc);
		ASN1InputStream dis = new ASN1InputStream(bis);
		ASN1Primitive derObj = dis.readObject();
		ASN1Sequence asn1 = (ASN1Sequence) derObj;
		ASN1Integer x = (ASN1Integer) asn1.getObjectAt(0);
		ASN1Integer y = (ASN1Integer) asn1.getObjectAt(1);

		@SuppressWarnings("deprecation")
		ECPoint c1 = curveParameters.getEcCurve().createPoint(x.getValue(), y.getValue(), true);
		Cipher cipher = new Cipher();
		cipher.initializeDec(userD, c1);
		DEROctetString data = (DEROctetString) asn1.getObjectAt(3);
		enc = data.getOctets();
		cipher.decrypt(enc);
		byte[] c3 = new byte[32];
		cipher.doFinal(c3);
		return enc;
	}

	/**
	 * 使用默认参数进行签名
	 * 
	 * @param userId
	 * @param privateKey
	 * @param sourceData
	 * @return
	 * @throws IOException
	 *
	 */
	public static byte[] sign(byte[] userId, byte[] privateKey, byte[] sourceData) throws IOException {
		return sign(SmCurveParameters.DEFAULT_INSTANCE, userId, privateKey, sourceData);
	}

	/**
	 * 使用默认参数进行签名
	 * 
	 * @param userId
	 * @param privateKey
	 * @param sourceData
	 * @return
	 * @throws IOException
	 *
	 */
	public static byte[] sign(byte[] privateKey, byte[] sourceData) throws IOException {
		return sign(SmCurveParameters.DEFAULT_INSTANCE, null, privateKey, sourceData);
	}

	/**
	 * 签名
	 * 
	 * @param curveParameters
	 * @param privateKey
	 * @param sourceData
	 * @return
	 * @throws IOException
	 *
	 */
	public static byte[] sign(SmCurveParameters curveParameters, byte[] privateKey, byte[] sourceData)
			throws IOException {
		return sign(SmCurveParameters.DEFAULT_INSTANCE, null, privateKey, sourceData);
	}

	/**
	 * 签名
	 * 
	 * @param curveParameters
	 * @param userId
	 * @param privateKey
	 * @param sourceData
	 * @return
	 * @throws IOException
	 *
	 */
	public static byte[] sign(SmCurveParameters curveParameters, byte[] userId, byte[] privateKey, byte[] sourceData)
			throws IOException {
		ExceptionUtils.checkNotNull(curveParameters, "curveParameters");
		ExceptionUtils.checkNotNull(privateKey, "privateKey");
		ExceptionUtils.checkNotNull(sourceData, "sourceData");
		Sm2 sm2 = Sm2.newInstance(curveParameters);
		BigInteger userD = new BigInteger(privateKey);
		ECPoint userKey = curveParameters.getEcPoint().multiply(userD);

		Sm3Digest sm3 = new Sm3Digest();
		byte[] z = sm2.getZ(userId, userKey);

		sm3.update(z, 0, z.length);
		sm3.update(sourceData, 0, sourceData.length);
		byte[] md = new byte[32];
		sm3.doFinal(md, 0);

		Sm2Result sm2Result = sm2.sign(md, userD);

		ASN1Integer dr = new ASN1Integer(sm2Result.getR());
		ASN1Integer ds = new ASN1Integer(sm2Result.getS());
		ASN1EncodableVector v2 = new ASN1EncodableVector();
		v2.add(dr);
		v2.add(ds);
		DERSequence sign = new DERSequence(v2);
		byte[] signdata = sign.getEncoded();
		return signdata;
	}

	/**
	 * 使用默认参数和Utf-8签名
	 * 
	 * @param base64PrivateKey
	 *            Base64私钥
	 * @param sourceData
	 *            源数据
	 * @param charset
	 *            编码
	 * @return
	 * @throws IOException
	 *
	 */
	public static String sign(String base64PrivateKey, String sourceData) throws IOException {
		return sign(SmCurveParameters.DEFAULT_INSTANCE, null, base64PrivateKey, sourceData, StandardCharsets.UTF_8);
	}

	/**
	 * 使用默认参数和Utf-8签名
	 * 
	 * @param userId
	 *            用户id
	 * @param base64PrivateKey
	 *            Base64私钥
	 * @param sourceData
	 *            源数据
	 * @return
	 * @throws IOException
	 *
	 */
	public static String sign(String userId, String base64PrivateKey, String sourceData) throws IOException {
		return sign(SmCurveParameters.DEFAULT_INSTANCE, userId, base64PrivateKey, sourceData, StandardCharsets.UTF_8);
	}

	/**
	 * 使用Utf-8签名
	 * 
	 * @param curveParameters
	 *            曲线参数
	 * @param userId
	 *            用户id
	 * @param base64PrivateKey
	 *            Base64私钥
	 * @param sourceData
	 *            源数据
	 * @return
	 * @throws IOException
	 *
	 */
	public static String sign(SmCurveParameters curveParameters, String userId, String base64PrivateKey,
			String sourceData) throws IOException {
		return sign(curveParameters, userId, base64PrivateKey, sourceData, StandardCharsets.UTF_8);
	}

	/**
	 * 签名
	 * 
	 * @param curveParameters
	 *            曲线参数
	 * @param userId
	 *            用户id
	 * @param base64PrivateKey
	 *            Base64私钥
	 * @param sourceData
	 *            源数据
	 * @param charset
	 *            编码
	 * @return
	 * @throws IOException
	 *
	 */
	public static String sign(SmCurveParameters curveParameters, String userId, String base64PrivateKey,
			String sourceData, Charset charset) throws IOException {
		ExceptionUtils.checkNotNull(curveParameters, "curveParameters");
		ExceptionUtils.checkNotNullOrBlank(base64PrivateKey, "base64PrivateKey");
		ExceptionUtils.checkNotNull(sourceData, "sourceData");
		if (charset == null) {
			charset = StandardCharsets.UTF_8;
		}
		byte[] userArray = null;
		if (userId != null) {
			userArray = userId.getBytes(charset);
		}
		byte[] privateKey = Base64.decode(base64PrivateKey.getBytes(charset));
		byte[] dataArray = sourceData.getBytes(charset);
		byte[] result = sign(curveParameters, userArray, privateKey, dataArray);
		return new String(Base64.encode(result), charset);
	}

	/**
	 * 使用默认签名验证
	 * 
	 * @param userId
	 * @param publicKey
	 * @param sourceData
	 * @param signData
	 * @return
	 * @throws IOException
	 *
	 */
	public static boolean verifySign(byte[] userId, byte[] publicKey, byte[] sourceData, byte[] signData)
			throws IOException {
		return verifySign(SmCurveParameters.DEFAULT_INSTANCE, userId, publicKey, sourceData, signData);
	}

	/**
	 * 使用默认签名验证
	 * 
	 * @param publicKey
	 * @param sourceData
	 * @param signData
	 * @return
	 * @throws IOException
	 *
	 */
	public static boolean verifySign(byte[] publicKey, byte[] sourceData, byte[] signData) throws IOException {
		return verifySign(SmCurveParameters.DEFAULT_INSTANCE, null, publicKey, sourceData, signData);
	}

	/**
	 * 签名验证
	 * 
	 * @param curveParameters
	 * @param publicKey
	 * @param sourceData
	 * @param signData
	 * @return
	 * @throws IOException
	 *
	 */
	public static boolean verifySign(SmCurveParameters curveParameters, byte[] publicKey, byte[] sourceData,
			byte[] signData) throws IOException {
		return verifySign(curveParameters, null, publicKey, sourceData, signData);
	}

	/**
	 * 签名验证
	 * 
	 * @param curveParameters
	 * @param userId
	 * @param publicKey
	 * @param sourceData
	 * @param signData
	 * @return
	 * @throws IOException
	 *
	 */
	@SuppressWarnings({ "resource" })
	public static boolean verifySign(SmCurveParameters curveParameters, byte[] userId, byte[] publicKey,
			byte[] sourceData, byte[] signData) throws IOException {
		ExceptionUtils.checkNotNull(curveParameters, "curveParameters");
		ExceptionUtils.checkNotNull(publicKey, "publicKey");
		ExceptionUtils.checkNotNull(sourceData, "sourceData");
		ExceptionUtils.checkNotNull(signData, "signData");

		if (publicKey == null || publicKey.length == 0) {
			return false;
		}
		if (sourceData == null || sourceData.length == 0) {
			return false;
		}
		Sm2 sm2 = Sm2.newInstance(curveParameters);
		ECPoint userKey = curveParameters.getEcCurve().decodePoint(publicKey);

		Sm3Digest sm3 = new Sm3Digest();
		byte[] z = sm2.getZ(userId, userKey);
		sm3.update(z, 0, z.length);
		sm3.update(sourceData, 0, sourceData.length);
		byte[] md = new byte[32];
		sm3.doFinal(md, 0);

		ByteArrayInputStream bis = new ByteArrayInputStream(signData);
		ASN1InputStream dis = new ASN1InputStream(bis);
		ASN1Primitive derObj = dis.readObject();

		Enumeration<?> e = ((ASN1Sequence) derObj).getObjects();

		BigInteger r = ((ASN1Integer) e.nextElement()).getValue();
		BigInteger s = ((ASN1Integer) e.nextElement()).getValue();
		Sm2Result result = new Sm2Result(r, s);
		return sm2.verify(md, userKey, result);
	}

	/**
	 * 使用默认参数和Utf-8签名验证
	 * 
	 * @param base64PublicKey
	 * @param sourceData
	 * @param base64SignData
	 * @return
	 * @throws IOException
	 *
	 */
	public static boolean verifySign(String base64PublicKey, String sourceData, String base64SignData)
			throws IOException {
		return verifySign(SmCurveParameters.DEFAULT_INSTANCE, null, base64PublicKey, sourceData, base64SignData,
				StandardCharsets.UTF_8);
	}

	/**
	 * 使用默认参数和Utf-8签名验证
	 * 
	 * @param userId
	 * @param base64PublicKey
	 * @param sourceData
	 * @param base64SignData
	 * @return
	 * @throws IOException
	 *
	 */
	public static boolean verifySign(String userId, String base64PublicKey, String sourceData, String base64SignData)
			throws IOException {
		return verifySign(SmCurveParameters.DEFAULT_INSTANCE, userId, base64PublicKey, sourceData, base64SignData,
				StandardCharsets.UTF_8);
	}

	/**
	 * 使用默认参数签名验证
	 * 
	 * @param userId
	 * @param base64PublicKey
	 * @param sourceData
	 * @param base64SignData
	 * @param charset
	 * @return
	 * @throws IOException
	 *
	 */
	public static boolean verifySign(String userId, String base64PublicKey, String sourceData, String base64SignData,
			Charset charset) throws IOException {
		return verifySign(SmCurveParameters.DEFAULT_INSTANCE, userId, base64PublicKey, sourceData, base64SignData,
				charset);
	}

	/**
	 * 签名验证
	 * 
	 * @param curveParameters
	 * @param userId
	 * @param base64PublicKey
	 * @param sourceData
	 * @param base64SignData
	 * @param charset
	 * @return
	 * @throws IOException
	 *
	 */
	public static boolean verifySign(SmCurveParameters curveParameters, String userId, String base64PublicKey,
			String sourceData, String base64SignData, Charset charset) throws IOException {
		ExceptionUtils.checkNotNull(curveParameters, "curveParameters");
		ExceptionUtils.checkNotNullOrBlank(base64PublicKey, "base64PrivateKey");
		ExceptionUtils.checkNotNull(sourceData, "sourceData");
		ExceptionUtils.checkNotNullOrBlank(base64SignData, "base64SignData");
		if (charset == null) {
			charset = StandardCharsets.UTF_8;
		}
		byte[] userArray = null;
		if (userId != null) {
			userArray = userId.getBytes(charset);
		}
		byte[] publicKey = Base64.decode(base64PublicKey.getBytes(charset));
		byte[] dataArray = sourceData.getBytes(charset);
		byte[] signData = Base64.decode(base64SignData.getBytes(charset));
		return verifySign(curveParameters, userArray, publicKey, dataArray, signData);
	}
}