package com.autumn.util.security.sm;

import org.bouncycastle.util.encoders.Hex;
import org.junit.Test;

public class Sm3Test {

	@Test
	public void test1() throws Exception {
		byte[] md = new byte[32];
		byte[] msg1 = "abc".getBytes();
		Sm3Digest sm3 = new Sm3Digest();
		sm3.update(msg1, 0, msg1.length);
		sm3.doFinal(md, 0);
		String s = new String(Hex.encode(md));
		System.out.println(s);
	}
}
