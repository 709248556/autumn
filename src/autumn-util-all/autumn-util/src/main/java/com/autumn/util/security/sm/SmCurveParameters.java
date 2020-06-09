package com.autumn.util.security.sm;

import java.math.BigInteger;
import java.security.SecureRandom;

import org.bouncycastle.crypto.AsymmetricCipherKeyPair;
import org.bouncycastle.crypto.generators.ECKeyPairGenerator;
import org.bouncycastle.crypto.params.ECDomainParameters;
import org.bouncycastle.crypto.params.ECKeyGenerationParameters;
import org.bouncycastle.math.ec.ECCurve;
import org.bouncycastle.math.ec.ECFieldElement;
import org.bouncycastle.math.ec.ECPoint;
import org.bouncycastle.math.ec.ECFieldElement.Fp;

import com.autumn.exception.ExceptionUtils;
import com.autumn.util.StringUtils;

/**
 * Sm 曲线参数
 * 
 * @author 老码农
 *
 *         2018-01-10 10:55:39
 */
public class SmCurveParameters {

	/**
	 * Ecc 默认16进制曲线参数
	 */
	public static String[] EC_HEX_PARAMS_DEFAULT = { "FFFFFFFEFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF00000000FFFFFFFFFFFFFFFF",
			"FFFFFFFEFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF00000000FFFFFFFFFFFFFFFC",
			"28E9FA9E9D9F5E344D5A9E4BCF6509A7F39789F515AB8F92DDBCBD414D940E93",
			"FFFFFFFEFFFFFFFFFFFFFFFFFFFFFFFF7203DF6B21C6052B53BBF40939D54123",
			"32C4AE2C1F1981195F9904466A39C9948FE30BBFF2660BE1715A4589334C74C7",
			"BC3736A2F4F6779C59BDCEE36B692153D0A9877CC62A474002DF32E52139F0A0" };

	/**
	 * 默认实例
	 */
	public static SmCurveParameters DEFAULT_INSTANCE = new SmCurveParameters();

	private final BigInteger p;
	private final BigInteger a;
	private final BigInteger b;
	private final BigInteger n;
	private final BigInteger gx;
	private final BigInteger gy;
	private final ECCurve ecCurve;
	private final ECPoint ecPoint;
	private final ECDomainParameters ecSpec;
	private final ECKeyPairGenerator ecKeyPairGenerator;
	private final ECFieldElement ecGxFieldelement;
	private final ECFieldElement ecGyFieldelement;

	/**
	 * 默认使用推荐参数初始化
	 */
	public SmCurveParameters() {
		this(EC_HEX_PARAMS_DEFAULT);
	}

	/**
	 * 初始化
	 * 
	 * @param ecHexParams
	 *            16 进制的参数集合
	 */
	@SuppressWarnings("deprecation")
	public SmCurveParameters(String[] ecHexParams) {
		ExceptionUtils.checkNotNull(ecHexParams, "ecHexParams");
		if (ecHexParams.length != EC_HEX_PARAMS_DEFAULT.length) {
			ExceptionUtils.throwArgumentException("ecHexParams",
					"ecHexParams 参数格式不正确，数组长度必须是 " + EC_HEX_PARAMS_DEFAULT.length + "。");
		}
		for (String par : ecHexParams) {
			if (StringUtils.isNullOrBlank(par) || par.length() != SmUtils.INTEGER_LENGTH_64) {
				ExceptionUtils.throwArgumentException("ecHexParams", "ecHexParams 参数格式不正确，每个参数长度必须由64个16进制组成。");
			}
		}
		this.p = new BigInteger(ecHexParams[0], 16);
		this.a = new BigInteger(ecHexParams[1], 16);
		this.b = new BigInteger(ecHexParams[2], 16);
		this.n = new BigInteger(ecHexParams[3], 16);
		this.gx = new BigInteger(ecHexParams[4], 16);
		this.gy = new BigInteger(ecHexParams[5], 16);
		this.ecGxFieldelement =  new Fp(this.p, this.gx);
		this.ecGyFieldelement = new Fp(this.p, this.gy);
		this.ecCurve = new ECCurve.Fp(this.p, this.a, this.b);
		this.ecPoint = new ECPoint.Fp(this.ecCurve, this.ecGxFieldelement, this.ecGyFieldelement);
		this.ecSpec = new ECDomainParameters(this.ecCurve, this.ecPoint, this.n);
		ECKeyGenerationParameters eccGenparam = new ECKeyGenerationParameters(this.ecSpec, new SecureRandom());
		this.ecKeyPairGenerator = new ECKeyPairGenerator();
		this.ecKeyPairGenerator.init(eccGenparam);
	}

	/**
	 * 获取 P
	 * 
	 * @return
	 *
	 */
	public BigInteger getP() {
		return p;
	}

	public BigInteger getA() {
		return a;
	}

	public BigInteger getB() {
		return b;
	}

	public BigInteger getN() {
		return n;
	}

	public BigInteger getGx() {
		return gx;
	}

	public BigInteger getGy() {
		return gy;
	}

	public ECCurve getEcCurve() {
		return ecCurve;
	}

	public ECPoint getEcPoint() {
		return ecPoint;
	}

	public ECDomainParameters getEcSpec() {
		return ecSpec;
	}

	public ECKeyPairGenerator getEcKeyPairGenerator() {
		return ecKeyPairGenerator;
	}

	public ECFieldElement getEcGxFieldelement() {
		return ecGxFieldelement;
	}

	public ECFieldElement getEcGyFieldelement() {
		return ecGyFieldelement;
	}

	/**
	 * 生成 KeyPair
	 * 
	 * @return
	 *
	 */
	public AsymmetricCipherKeyPair generateKeyPair() {
		return this.getEcKeyPairGenerator().generateKeyPair();
	}

	/**
	 * 生成 Sm Key Pair
	 * 
	 * @return
	 *
	 */
	public SmKeyPair generateSmKeyPair() {
		return new SmKeyPair(generateKeyPair());
	}
}
