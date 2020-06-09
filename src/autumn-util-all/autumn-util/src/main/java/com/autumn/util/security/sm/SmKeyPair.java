package com.autumn.util.security.sm;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import org.bouncycastle.crypto.AsymmetricCipherKeyPair;
import org.bouncycastle.crypto.params.ECPrivateKeyParameters;
import org.bouncycastle.crypto.params.ECPublicKeyParameters;
import org.bouncycastle.util.encoders.Base64;

import com.autumn.exception.ExceptionUtils;

/**
 * sm KeyPair
 * 
 * @author 老码农
 *
 *         2018-01-09 20:30:01
 */
public class SmKeyPair {

	private final AsymmetricCipherKeyPair cipherKeyPair;
	private final ECPublicKeyParameters publicParam;
	private final ECPrivateKeyParameters privateParam;

	/**
	 * 
	 * @param cipherKeyPair
	 */
	public SmKeyPair(AsymmetricCipherKeyPair cipherKeyPair) {
		ExceptionUtils.checkNotNull(cipherKeyPair, "cipherKeyPair");
		this.cipherKeyPair = cipherKeyPair;
		this.publicParam = (ECPublicKeyParameters) cipherKeyPair.getPublic(); // CA公钥
		this.privateParam = (ECPrivateKeyParameters) cipherKeyPair.getPrivate(); // CA私钥
	}

	public AsymmetricCipherKeyPair getCipherKeyPair() {
		return cipherKeyPair;
	}

	/**
	 * 获取公钥
	 * 
	 * @return
	 *
	 */
	public ECPublicKeyParameters getPublic() {
		return publicParam;
	}

	/**
	 * 公钥转换为16进制
	 * 
	 * @return
	 *
	 */
	@SuppressWarnings("deprecation")
	public String publicToHexString() {
		return SmUtils.byteToHex(this.getPublic().getQ().getEncoded());
	}

	/**
	 * 对公钥转换为Base64 的 Utf - 8
	 * 
	 * @param charset
	 * @return
	 *
	 */
	public String publicToBase64() {
		return publicToBase64(StandardCharsets.UTF_8);
	}

	/**
	 * 公钥转换为Base64
	 * 
	 * @param charset
	 * @return
	 *
	 */
	@SuppressWarnings("deprecation")
	public String publicToBase64(Charset charset) {
		if (charset == null) {
			charset = StandardCharsets.UTF_8;
		}
		return new String(Base64.encode(this.getPublic().getQ().getEncoded()), charset);
	}

	/**
	 * 获取私钥
	 * 
	 * @return
	 *
	 */
	public ECPrivateKeyParameters getPrivate() {
		return privateParam;
	}

	/**
	 * 私钥转换为16进制字符
	 * 
	 * @return
	 *
	 */
	public String privateToHexString() {
		return SmUtils.byteToHex(getPrivate().getD().toByteArray());
	}

	/**
	 * 对私钥转换为Base64 的 Utf - 8
	 * 
	 * @param charset
	 * @return
	 *
	 */
	public String privateToBase64() {
		return privateToBase64(StandardCharsets.UTF_8);
	}

	/**
	 * 私钥转换为Base64
	 * 
	 * @param charset
	 * @return
	 *
	 */
	public String privateToBase64(Charset charset) {
		if (charset == null) {
			charset = StandardCharsets.UTF_8;
		}
		return new String(Base64.encode(getPrivate().getD().toByteArray()), charset);
	}
}
