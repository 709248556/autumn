package com.autumn.util.security.sm;

import java.math.BigInteger;

/**
 * 
 * @author 老码农
 *
 * 2018-01-09 15:50:55
 */
class SmUtils {
	
	public final static int INTEGER_LENGTH_1 = 0x01;
	public final static int INTEGER_LENGTH_16 = 16;
	public final static int INTEGER_LENGTH_24 = 24;
	public final static int INTEGER_LENGTH_32 = 32;
	public final static int INTEGER_LENGTH_33 = 33;
	public final static int INTEGER_LENGTH_64 = 64;
	public final static int INTEGER_LENGTH_2 = 2;
	public final static int INTEGER_LENGTH_4 = 4;
	public final static int INTEGER_LENGTH_8 = 8;
	public final static int INTEGER_LENGTH_68 = 68;
	public final static int INTEGER_LENGTH_15 = 15;
	
	/**
	 * 整形转换成网络传输的字节流（字节数组）型数据
	 * 
	 * @param num
	 *            一个整型数据
	 * @return 4个字节的自己数组
	 */
	public static byte[] intToBytes(int num) {
		byte[] bytes = new byte[INTEGER_LENGTH_4];
		bytes[0] = (byte) (0xff & (num >> 0));
		bytes[1] = (byte) (0xff & (num >> INTEGER_LENGTH_8));
		bytes[2] = (byte) (0xff & (num >> INTEGER_LENGTH_16));
		bytes[3] = (byte) (0xff & (num >> INTEGER_LENGTH_24));
		return bytes;
	}

	/**
	 * 四个字节的字节数据转换成一个整形数据
	 * 
	 * @param bytes
	 *            4个字节的字节数组
	 * @return 一个整型数据
	 */
	public static int byteToInt(byte[] bytes) {
		int num = 0;
		int temp;
		temp = (0x000000ff & (bytes[0])) << 0;
		num = num | temp;
		temp = (0x000000ff & (bytes[1])) << 8;
		num = num | temp;
		temp = (0x000000ff & (bytes[2])) << 16;
		num = num | temp;
		temp = (0x000000ff & (bytes[3])) << 24;
		num = num | temp;
		return num;
	}

	/**
	 * 长整形转换成网络传输的字节流（字节数组）型数据
	 * 
	 * @param num
	 *            一个长整型数据
	 * @return 4个字节的自己数组
	 */
	public static byte[] longToBytes(long num) {
		byte[] bytes = new byte[INTEGER_LENGTH_8];
		for (int i = 0; i < INTEGER_LENGTH_8; i++) {
			bytes[i] = (byte) (0xff & (num >> (i * INTEGER_LENGTH_8)));
		}
		return bytes;
	}

	/**
	 * 大数字转换字节流（字节数组）型数据
	 * 
	 * @param n
	 * @return
	 */
	public static byte[] byteConvert32Bytes(BigInteger n) {
		byte[] tmpd;
		if (n == null) {
			return null;
		}
		int len = SmUtils.INTEGER_LENGTH_32;
		if (n.toByteArray().length == SmUtils.INTEGER_LENGTH_33) {
			tmpd = new byte[len];
			System.arraycopy(n.toByteArray(), 1, tmpd, 0, len);
		} else if (n.toByteArray().length == len) {
			tmpd = n.toByteArray();
		} else {
			tmpd = new byte[len];
			for (int i = 0; i < len - n.toByteArray().length; i++) {
				tmpd[i] = 0;
			}
			System.arraycopy(n.toByteArray(), 0, tmpd, len - n.toByteArray().length, n.toByteArray().length);
		}
		return tmpd;
	}

	

	/**
	 * 根据字节数组获得值(十六进制数字)
	 * 
	 * @param bytes
	 * @return
	 */
	public static String getHexString(byte[] bytes) {
		return getHexString(bytes, true);
	}

	/**
	 * 根据字节数组获得值(十六进制数字)
	 * 
	 * @param bytes
	 * @param upperCase
	 * @return
	 */
	public static String getHexString(byte[] bytes, boolean upperCase) {
		String ret = "";
		for (int i = 0; i < bytes.length; i++) {
			ret += Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1);
		}
		return upperCase ? ret.toUpperCase() : ret;
	}

	

	/**
	 * Convert hex string to byte[]
	 * 
	 * @param hexString
	 *            the hex string
	 * @return byte[]
	 */
	public static byte[] hexStringToBytes(String hexString) {
		if (hexString == null || "".equals(hexString)) {
			return null;
		}
		hexString = hexString.toUpperCase();
		int length = hexString.length() / 2;
		char[] hexChars = hexString.toCharArray();
		byte[] d = new byte[length];
		for (int i = 0; i < length; i++) {
			int pos = i * 2;
			d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
		}
		return d;
	}

	/**
	 * Convert char to byte
	 * 
	 * @param c
	 *            char
	 * @return byte
	 */
	public static byte charToByte(char c) {
		return (byte) "0123456789ABCDEF".indexOf(c);
	}	
	
	/**
	 * 十六进制串转化为byte数组
	 * 
	 * @return the array of byte
	 */
	public static byte[] hexToByte(String hex) throws IllegalArgumentException {
		if (hex.length() % INTEGER_LENGTH_2 != 0) {
			throw new IllegalArgumentException();
		}
		char[] arr = hex.toCharArray();
		byte[] b = new byte[hex.length() / INTEGER_LENGTH_2];
		for (int i = 0, j = 0, l = hex.length(); i < l; i++, j++) {
			String swap = "" + arr[i++] + arr[i];
			int byteint = Integer.parseInt(swap, INTEGER_LENGTH_16) & 0xFF;
			b[j] = new Integer(byteint).byteValue();
		}
		return b;
	}	

	/**
     * 字节数组转换为十六进制字符串
     * 
     * @param b
     *            byte[] 需要转换的字节数组
     * @return String 十六进制字符串
     */
    public static String byteToHex(byte[] b) {
        if (b == null) {
            throw new IllegalArgumentException(
                    "Argument b ( byte array ) is null! ");
        }
        StringBuilder hs = new StringBuilder();
        String stmp;
		for (byte value : b) {
			stmp = Integer.toHexString(value & 0xff);
			if (stmp.length() == 1) {
				hs.append("0").append(stmp);
			} else {
				hs.append(stmp);
			}
		}
        return hs.toString().toUpperCase();
    }
	
}