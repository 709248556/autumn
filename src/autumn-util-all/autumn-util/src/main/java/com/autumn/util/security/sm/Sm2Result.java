package com.autumn.util.security.sm;

import java.math.BigInteger;

/**
 * 
 * @author 老码农
 *
 *         2018-01-09 15:38:40
 */
public class Sm2Result {

	/**
	 * 签名
	 */
	private final BigInteger r;
	private final BigInteger s;

	/**
	 * 
	 * @param r
	 * @param s
	 */
	public Sm2Result(BigInteger r, BigInteger s) {
		this.r = r;
		this.s = s;
	}

	/**
	 * 获取 R
	 * 
	 * @return
	 *
	 */
	public BigInteger getR() {
		return r;
	}

	/**
	 * 获取 S
	 * 
	 * @return
	 *
	 */
	public BigInteger getS() {
		return s;
	}

}