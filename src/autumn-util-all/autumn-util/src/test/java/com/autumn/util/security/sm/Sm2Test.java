package com.autumn.util.security.sm;

import org.bouncycastle.util.encoders.Base64;
import org.junit.Test;

/**
 * 
 * @author 老码农
 *
 *         2018-01-09 17:17:21
 */
public class Sm2Test {

	@Test
	public void smSignTest() throws Exception {
		String plainText = "batchNumber=25556666&batchDate='2018-1-1'&timeStamp=54646464&random=ewwewesdsds&details=[{},{}]&secretKey=falddwewewweewew";

		Sm2 sm2 = Sm2.newInstance();

		SmKeyPair keyPair = sm2.getCurveParameters().generateSmKeyPair();

		String publicKey = keyPair.publicToHexString();
		String privateKey = keyPair.privateToHexString();

		System.out.println("privateKey Hex: " + privateKey);
		String prikS = keyPair.privateToBase64();
		System.out.println("privateKey: " + prikS);
		System.out.println("");

		System.out.println("publicKey Hex: " + publicKey);

		String pubkS = keyPair.publicToBase64();
		System.out.println("publicKey: " + pubkS);
		System.out.println("");

		// 测试用户ID
		String userId = "test@guinong.COM";

		System.out.println("ID: " + SmUtils.getHexString(userId.getBytes()));
		System.out.println("");

		System.out.println("签名: ");
		String base64SignData = Sm2Utils.sign(userId, prikS, plainText);
		System.out.println("sign: " + base64SignData);
		System.out.println("");

		System.out.println("验签: ");
		boolean vs = Sm2Utils.verifySign(userId, pubkS, plainText, base64SignData);
		System.out.println("验签结果: " + vs);
		System.out.println("");

	}

	@Test
	public void smSignTest1() throws Exception {
		String plainText = "message digest";
		byte[] sourceData = plainText.getBytes();

		Sm2 sm2 = Sm2.newInstance();

		SmKeyPair keyPair = sm2.getCurveParameters().generateSmKeyPair();

		String publicKey = keyPair.publicToHexString();
		String privateKey = keyPair.privateToHexString();

		System.out.println("私钥: " + privateKey);
		String prikS = keyPair.privateToBase64();
		System.out.println("privateKey: " + prikS);
		System.out.println("");

		System.out.println("公钥: " + publicKey);
		String pubkS = keyPair.publicToBase64();
		System.out.println("publicKey: " + pubkS);
		System.out.println("");

		System.out.println("加密: ");
		byte[] cipherText = Sm2Utils.encrypt(Base64.decode(pubkS.getBytes()), sourceData);
		System.out.println(new String(Base64.encode(cipherText)));
		System.out.println("");

		System.out.println("解密: ");
		plainText = new String(Sm2Utils.decrypt(Base64.decode(prikS.getBytes()), cipherText));
		System.out.println(plainText);
	}
}
