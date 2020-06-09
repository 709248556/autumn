package com.autumn.util.security.sm;

import org.bouncycastle.crypto.AsymmetricCipherKeyPair;
import org.bouncycastle.crypto.params.ECPrivateKeyParameters;
import org.bouncycastle.crypto.params.ECPublicKeyParameters;
import org.bouncycastle.math.ec.ECPoint;

import java.math.BigInteger;

/**
 * 密文
 * 
 * @author 老码农
 *
 *         2018-01-09 13:10:12
 */
public class Cipher {
	private int ct;
	private ECPoint p2;
	private Sm3Digest sm3Keybase;
	private Sm3Digest sm3Digest;
	private byte[] key;
	private byte keyOff;

	public Cipher() {
		this.ct = 1;
		this.key = new byte[32];
		this.keyOff = 0;
	}

	/**
	 * 重置
	 * 
	 */
	private void reset() {
		this.sm3Keybase = new Sm3Digest();
		this.sm3Digest = new Sm3Digest();
		byte[] p = SmUtils.byteConvert32Bytes(p2.normalize().getXCoord().toBigInteger());
		this.sm3Keybase.update(p, 0, p.length);
		this.sm3Digest.update(p, 0, p.length);
		p = SmUtils.byteConvert32Bytes(p2.normalize().getYCoord().toBigInteger());
		this.sm3Keybase.update(p, 0, p.length);
		this.ct = 1;
		nextKey();
	}

	private void nextKey() {
		Sm3Digest sm3keycur = new Sm3Digest(this.sm3Keybase);
		sm3keycur.update((byte) (ct >> 24 & 0xff));
		sm3keycur.update((byte) (ct >> 16 & 0xff));
		sm3keycur.update((byte) (ct >> 8 & 0xff));
		sm3keycur.update((byte) (ct & 0xff));
		sm3keycur.doFinal(key, 0);
		this.keyOff = 0;
		this.ct++;
	}

	public ECPoint initializeEnc(SmCurveParameters curveParameters, ECPoint userKey) {
		AsymmetricCipherKeyPair key = curveParameters.generateKeyPair();
		ECPrivateKeyParameters ecpriv = (ECPrivateKeyParameters) key.getPrivate();
		ECPublicKeyParameters ecpub = (ECPublicKeyParameters) key.getPublic();
		BigInteger k = ecpriv.getD();
		ECPoint c1 = ecpub.getQ();
		this.p2 = userKey.multiply(k);
		reset();
		return c1;
	}

	/**
	 * 加密
	 * 
	 * @param data
	 *
	 */
	public void encrypt(byte[] data) {
		this.sm3Digest.update(data, 0, data.length);
		for (int i = 0; i < data.length; i++) {
			if (keyOff == key.length) {
				nextKey();
			}
			data[i] ^= key[keyOff++];
		}
	}

	public void initializeDec(BigInteger userD, ECPoint c1) {
		this.p2 = c1.multiply(userD);
		reset();
	}

	/**
	 * 解密
	 * 
	 * @param data
	 *
	 */
	public void decrypt(byte[] data) {
		for (int i = 0; i < data.length; i++) {
			if (keyOff == key.length) {
				nextKey();
			}
			data[i] ^= key[keyOff++];
		}
		this.sm3Digest.update(data, 0, data.length);
	}

	/**
	 * 
	 * @param c3
	 *
	 */
	public void doFinal(byte[] c3) {
		byte[] p = SmUtils.byteConvert32Bytes(p2.normalize().getYCoord().toBigInteger());
		this.sm3Digest.update(p, 0, p.length);
		this.sm3Digest.doFinal(c3, 0);
		reset();
	}
}
