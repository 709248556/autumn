package com.autumn.util.security.sm;

/**
 * 
 * @author 老码农
 *
 * 2018-01-09 15:51:23
 */
public class Sm4Context
{
	public int mode;
	
	public long[] sk;
	
	public boolean isPadding;

	public Sm4Context() 
	{
		this.mode = 1;
		this.isPadding = true;
		this.sk = new long[32];
	}
}
