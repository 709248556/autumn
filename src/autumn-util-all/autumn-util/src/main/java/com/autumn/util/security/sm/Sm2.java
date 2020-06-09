package com.autumn.util.security.sm;

import com.autumn.exception.ExceptionUtils;
import org.bouncycastle.crypto.AsymmetricCipherKeyPair;
import org.bouncycastle.crypto.params.ECPrivateKeyParameters;
import org.bouncycastle.crypto.params.ECPublicKeyParameters;
import org.bouncycastle.math.ec.ECPoint;

import java.math.BigInteger;

/**
 * 
 * @author 老码农
 *
 *         2018-01-09 15:38:36
 */
public class Sm2 {

	/**
	 * 新实例
	 * 
	 * @param curveParameters
	 *            曲线参数
	 * @return
	 *
	 */
	public static Sm2 newInstance(SmCurveParameters curveParameters) {
		return new Sm2(curveParameters);
	}

	/**
	 * 使用默认 曲线参数
	 * 
	 * @return
	 *
	 */
	public static Sm2 newInstance() {
		return new Sm2(SmCurveParameters.DEFAULT_INSTANCE);
	}

	private final SmCurveParameters curveParameters;

	/**
	 * 
	 * @param curveParameters
	 */
	public Sm2(SmCurveParameters curveParameters) {
		this.curveParameters = ExceptionUtils.checkNotNull(curveParameters, "curveParameters");
	}

	public byte[] getZ(byte[] userId, ECPoint userKey) {
		Sm3Digest sm3 = new Sm3Digest();

		if (userId != null) {
			int len = userId.length * 8;
			sm3.update((byte) (len >> 8 & 0xFF));
			sm3.update((byte) (len & 0xFF));
			sm3.update(userId, 0, userId.length);
		}

		SmCurveParameters cur = this.getCurveParameters();

		byte[] p = SmUtils.byteConvert32Bytes(cur.getA());
		sm3.update(p, 0, p.length);

		p = SmUtils.byteConvert32Bytes(cur.getB());
		sm3.update(p, 0, p.length);

		p = SmUtils.byteConvert32Bytes(cur.getGx());
		sm3.update(p, 0, p.length);

		p = SmUtils.byteConvert32Bytes(cur.getGy());
		sm3.update(p, 0, p.length);

		p = SmUtils.byteConvert32Bytes(userKey.normalize().getXCoord().toBigInteger());
		sm3.update(p, 0, p.length);

		p = SmUtils.byteConvert32Bytes(userKey.normalize().getYCoord().toBigInteger());
		sm3.update(p, 0, p.length);

		byte[] md = new byte[sm3.getDigestSize()];
		sm3.doFinal(md, 0);
		return md;
	}

	/**
	 * 签名
	 * 
	 * @param md
	 * @param userD
	 * @return
	 *
	 */
	public Sm2Result sign(byte[] md, BigInteger userD) {
		SmCurveParameters cur = this.getCurveParameters();
		BigInteger e = new BigInteger(1, md);
		BigInteger k = null;
		ECPoint kp = null;
		BigInteger r = null;
		BigInteger s = null;
		do {
			do {
				AsymmetricCipherKeyPair keypair = cur.generateKeyPair();
				ECPrivateKeyParameters ecpriv = (ECPrivateKeyParameters) keypair.getPrivate();
				ECPublicKeyParameters ecpub = (ECPublicKeyParameters) keypair.getPublic();
				k = ecpriv.getD();
				kp = ecpub.getQ();

				r = e.add(kp.normalize().getXCoord().toBigInteger());
				r = r.mod(cur.getN());
			} while (r.equals(BigInteger.ZERO) || r.add(k).equals(cur.getN()));

			// (1 + dA)~-1
			BigInteger da1 = userD.add(BigInteger.ONE);
			da1 = da1.modInverse(cur.getN());

			// s
			s = r.multiply(userD);
			s = k.subtract(s).mod(cur.getN());
			s = da1.multiply(s).mod(cur.getN());
		} while (s.equals(BigInteger.ZERO));
		return new Sm2Result(r, s);
	}

	/**
	 * 验证
	 * 
	 * @param md
	 * @param userKey
	 * @param result
	 * @return
	 *
	 */
	public boolean verify(byte[] md, ECPoint userKey, Sm2Result result) {
		SmCurveParameters cur = this.getCurveParameters();
		BigInteger e = new BigInteger(1, md);
		BigInteger t = result.getR().add(result.getS()).mod(cur.getN());
		if (t.equals(BigInteger.ZERO)) {
			return false;
		} else {
			ECPoint x1y1 = cur.getEcPoint().multiply(result.getS()).add(userKey.multiply(t));
			BigInteger r1 = e.add(x1y1.normalize().getXCoord().toBigInteger()).mod(cur.getN());
			return result.getR().equals(r1);
		}
	}

	/**
	 * 获取曲线参数
	 * 
	 * @return
	 *
	 */
	public SmCurveParameters getCurveParameters() {
		return curveParameters;
	}
}
