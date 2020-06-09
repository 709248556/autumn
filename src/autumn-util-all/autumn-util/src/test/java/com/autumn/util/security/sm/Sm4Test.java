package com.autumn.util.security.sm;

import org.junit.Test;

public class Sm4Test {

	@Test
	public void test1() throws Exception {
		String plainText = "abcd";

		Sm4Digest sm4 = new Sm4Digest();
		sm4.setSecretKey("JeF8U9wHFOMfs2Y8");
		sm4.setHexString(false);

		System.out.println("ECB模式");
		String cipherText = sm4.encryptDataEcb(plainText);
		System.out.println("密文: " + cipherText);
		System.out.println("");

		plainText = sm4.decryptDataEcb(cipherText);
		System.out.println("明文: " + plainText);
		System.out.println("");

		System.out.println("CBC模式");
		sm4.setIv("UISwD9fW6cFh9SNS");
		cipherText = sm4.encryptDataCbc(plainText);
		System.out.println("密文: " + cipherText);
		System.out.println("");

		plainText = sm4.decryptDataCbc(cipherText);
		System.out.println("明文: " + plainText);
	}
}
