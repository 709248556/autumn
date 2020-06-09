package com.autumn.util;

import com.autumn.exception.ExceptionUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * BigDecimal 帮助
 * 
 * @author 老码农
 *
 *         2017-11-17 15:12:49
 */
public class BigDecimalUtils {

	private static final BigDecimal RMB_MAX_VALUE = new BigDecimal("9999999999999999.99");
	private static final String RMB_CN_FULL = "整";
	private static final String RMB_CN_FULL_BRANCH = "零分";
	private static final String RMB_CN_NEGATIVE = "负";
	private static final String RMB_CN_ZEOR_FULL = "零元整";
	private static final String[] RMB_CN_UPPER_NUMBER = {"零", "壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖"};
	private static final String[] RMB_CN_UPPER_MONETRAY_UNIT = {"分", "角", "元", "拾", "佰", "仟", "万", "拾", "佰", "仟", "亿", "拾", "佰", "仟", "兆", "拾", "佰", "仟"};
	private static final int RMB_MONEY_DIGITS = 2;


	/**
	 * 向上四舍五入(>=0.5则进位)
	 * 
	 * @param value
	 *            值
	 * @param digits
	 *            小数位数
	 * @return
	 */
	public static BigDecimal roundUp(BigDecimal value, int digits) {
		return roundUp(value, digits, null);
	}

	/**
	 * 向上四舍五入(>=0.5则进位,为值为 null 则采用零)
	 * 
	 * @param value
	 *            值
	 * @param digits
	 *            小数位数
	 * @return
	 */
	public static BigDecimal roundUpDefaultZero(BigDecimal value, int digits) {
		return roundUp(value, digits, BigDecimal.ZERO);
	}

	/**
	 * 向上四舍五入(>=0.5则进位)
	 * 
	 * @param value
	 *            值
	 * @param digits
	 *            小数位数
	 * @param defaultValue
	 *            默认值
	 * @return
	 */
	public static BigDecimal roundUp(BigDecimal value, int digits, BigDecimal defaultValue) {
		BigDecimal result = value;
		if (result == null) {
			result = defaultValue;
		}
		if (result == null) {
			return result;
		}
		return result.setScale(digits, BigDecimal.ROUND_HALF_UP);
	}

	/**
	 * 四舍五入(截断)
	 * 
	 * @param value
	 *            值
	 * @param digits
	 *            小数位数
	 * @return
	 */
	public static BigDecimal roundDown(BigDecimal value, int digits) {
		return roundDown(value, digits, null);
	}

	/**
	 * 四舍五入(截断,为值为 null 则采用零)
	 * 
	 * @param value
	 *            值
	 * @param digits
	 *            小数位数
	 * @return
	 */
	public static BigDecimal roundDownDefaultZero(BigDecimal value, int digits) {
		return roundDown(value, digits, BigDecimal.ZERO);
	}

	/**
	 * 獲取值或0
	 * 
	 * @param value
	 * @return
	 */
	public static BigDecimal getValueOrZero(BigDecimal value) {
		if (value == null) {
			return BigDecimal.ZERO;
		}
		return value;
	}

	/**
	 * 
	 * @param left
	 * @param rigth
	 * @return
	 */
	public static BigDecimal multiply(BigDecimal left, BigDecimal rigth) {
		return getValueOrZero(left).multiply(getValueOrZero(rigth));
	}

	/**
	 * 
	 * @param left
	 * @param rigth
	 * @return
	 */
	public static BigDecimal multiply(BigDecimal left, Long rigth) {
		return getValueOrZero(left).multiply(rigth == null ? BigDecimal.ZERO : new BigDecimal(rigth));
	}

	/**
	 * 
	 * @param left
	 * @param rigth
	 * @return
	 */
	public static BigDecimal multiply(BigDecimal left, Integer rigth) {
		return getValueOrZero(left).multiply(rigth == null ? BigDecimal.ZERO : new BigDecimal(rigth));
	}

	/**
	 * 四舍五入(截断)
	 * 
	 * @param value
	 *            值
	 * @param digits
	 *            小数位数
	 * @param defaultValue
	 *            默认值
	 * @return
	 */
	public static BigDecimal roundDown(BigDecimal value, int digits, BigDecimal defaultValue) {
		BigDecimal result = value;
		if (result == null) {
			result = defaultValue;
		}
		if (result == null) {
			return result;
		}
		return result.setScale(digits, BigDecimal.ROUND_HALF_UP);
	}

	/**
	 * 是否大于
	 * 
	 * @param left
	 *            比较第一个
	 * @param rigth
	 *            比较第二个
	 * @return
	 */
	public static boolean gt(BigDecimal left, BigDecimal rigth) {
		if (left != null && rigth != null) {
			return left.compareTo(rigth) > 0;
		} else {
			return left != null;
		}
	}

	/**
	 * 是否大于零
	 * 
	 * @param value
	 *            值
	 * @return
	 */
	public static boolean gtZero(BigDecimal value) {
		return gt(value, BigDecimal.ZERO);
	}

	/**
	 * 是否大于或等于
	 * 
	 * @param left
	 *            比较第一个
	 * @param rigth
	 *            比较第二个
	 * @return
	 */
	public static boolean ge(BigDecimal left, BigDecimal rigth) {
		if (left != null && rigth != null) {
			return left.compareTo(rigth) >= 0;
		} else {
			if (left != null) {
				return true;
			}
			return left == null && rigth == null;
		}
	}

	/**
	 * 是否大于或等于零
	 * 
	 * @param value
	 *            值
	 * @return
	 */
	public static boolean geZero(BigDecimal value) {
		return value != null && ge(value, BigDecimal.ZERO);
	}

	/**
	 * 是否小于
	 * 
	 * @param left
	 *            比较第一个
	 * @param rigth
	 *            比较第二个
	 * @return
	 */
	public static boolean lt(BigDecimal left, BigDecimal rigth) {
		if (left != null && rigth != null) {
			return left.compareTo(rigth) < 0;
		} else {
			return rigth != null;
		}
	}

	/**
	 * 是否小于零
	 * 
	 * @param value
	 *            值
	 * @return
	 */
	public static boolean ltZero(BigDecimal value) {
		return value != null && lt(value, BigDecimal.ZERO);
	}

	/**
	 * 是否小于或等于
	 * 
	 * @param left
	 *            比较第一个
	 * @param rigth
	 *            比较第二个
	 * @return
	 */
	public static boolean le(BigDecimal left, BigDecimal rigth) {
		if (left != null && rigth != null) {
			return left.compareTo(rigth) <= 0;
		} else {
			if (rigth != null) {
				return true;
			}
			return left == null && rigth == null;
		}
	}

	/**
	 * 是否小于或等于零
	 * 
	 * @param value
	 *            值
	 * @return
	 */
	public static boolean leZero(BigDecimal value) {
		return value != null && le(value, BigDecimal.ZERO);
	}

	/**
	 * 是否等于
	 * 
	 * @param left
	 *            比较第一个
	 * @param rigth
	 *            比较第二个
	 * @return
	 */
	public static boolean eq(BigDecimal left, BigDecimal rigth) {
		if (left != null && rigth != null) {
			return left.compareTo(rigth) == 0;
		} else {
			return left == null && rigth == null;
		}
	}

	/**
	 * 是否等于零
	 * 
	 * @param value
	 *            值
	 * @return
	 */
	public static boolean eqZero(BigDecimal value) {
		return eq(value, BigDecimal.ZERO);
	}

	/**
	 * 是否等于空或零
	 * 
	 * @param value
	 *            值
	 * @return
	 */
	public static boolean eqNullOrZero(BigDecimal value) {
		return value == null || eq(value, BigDecimal.ZERO);
	}

	/**
	 * 是否不等于
	 * 
	 * @param left
	 *            比较第一个
	 * @param rigth
	 *            比较第二个
	 * @return
	 */
	public static boolean notEq(BigDecimal left, BigDecimal rigth) {
		if (left != null && rigth != null) {
			return left.compareTo(rigth) != 0;
		} else {
			return !(left == null && rigth == null);
		}
	}

	/**
	 * 是否不等于零
	 * 
	 * @param value
	 *            值
	 * @return
	 */
	public static boolean notEqZero(BigDecimal value) {
		return notEq(value, BigDecimal.ZERO);
	}

	/**
	 * 不等于null或不等于零
	 * 
	 * @param value
	 *            值
	 * @return
	 */
	public static boolean notNullOrZero(BigDecimal value) {
		return value != null && notEqZero(value);
	}

	/**
	 * 相除
	 * 
	 * @param left
	 *            被除数
	 * @param rigth
	 *            除数
	 * @param digits
	 *            小数位数
	 * @return
	 */
	public static BigDecimal divide(BigDecimal left, BigDecimal rigth, int digits) {
		return left.divide(rigth, digits, RoundingMode.HALF_UP);
	}

	/**
	 * 相除
	 * 
	 * @param left
	 *            被除数
	 * @param rigth
	 *            除数
	 * @param digits
	 *            小数位数
	 * @return
	 */
	public static BigDecimal divide(long left, long rigth, int digits) {
		return new BigDecimal(left).divide(new BigDecimal(rigth), digits, RoundingMode.HALF_UP);
	}

	/**
	 * 相除
	 * 
	 * @param left
	 *            被除数
	 * @param rigth
	 *            除数
	 * @param digits
	 *            小数位数
	 * @return
	 */
	public static BigDecimal divide(int left, int rigth, int digits) {
		return new BigDecimal(left).divide(new BigDecimal(rigth), digits, RoundingMode.HALF_UP);
	}

	/**
	 * 相除
	 * 
	 * @param left
	 *            被除数
	 * @param rigth
	 *            除数
	 * @param digits
	 *            小数位数
	 * @return
	 */
	public static BigDecimal divide(double left, double rigth, int digits) {
		return new BigDecimal(Double.toString(left)).divide(new BigDecimal(Double.toString(rigth)), digits,
				RoundingMode.HALF_UP);
	}

	/**
	 * 相除
	 * 
	 * @param left
	 *            被除数
	 * @param rigth
	 *            除数
	 * @param digits
	 *            小数位数
	 * @return
	 */
	public static BigDecimal divide(float left, float rigth, int digits) {
		return new BigDecimal(Float.toString(left)).divide(new BigDecimal(Float.toString(rigth)), digits,
				RoundingMode.HALF_UP);
	}

	/**
	 * 人民币大写转换
	 *
	 * @param numberOfMoney 值
	 * @return
	 */
	public static String toRmb(BigDecimal numberOfMoney) {
		if (numberOfMoney == null) {
			return RMB_CN_ZEOR_FULL;
		}
		numberOfMoney = BigDecimalUtils.roundUp(numberOfMoney, 4);
		if (BigDecimalUtils.gt(numberOfMoney, RMB_MAX_VALUE)) {
			ExceptionUtils.throwArgumentOverflowException("numberOfMoney", "超过最大转换值 " + RMB_MAX_VALUE.toString());
		}
		int signum = numberOfMoney.signum();
		if (BigDecimalUtils.eqZero(numberOfMoney)) {
			return RMB_CN_ZEOR_FULL;
		}
		StringBuilder sb = new StringBuilder();
		long number = numberOfMoney.movePointRight(RMB_MONEY_DIGITS).setScale(0, BigDecimal.ROUND_HALF_UP).abs()
				.longValue();
		int zero = 0;
		long scale = number % 100;
		int numUnit;
		int numIndex = 0;
		boolean getZero = false;
		if (scale <= 0) {
			numIndex = 2;
			number = number / 100;
			getZero = true;
		}
		if ((scale > 0) && (scale % 10 <= 0)) {
			numIndex = 1;
			number = number / 10;
			getZero = true;
		}
		int zeroSize = 0;
		while (number > 0) {
			numUnit = (int) (number % 10);
			if (numUnit > 0) {
				if ((numIndex == 9) && (zeroSize >= 3)) {
					sb.insert(0, RMB_CN_UPPER_MONETRAY_UNIT[6]);
				}
				if ((numIndex == 13) && (zeroSize >= 3)) {
					sb.insert(0, RMB_CN_UPPER_MONETRAY_UNIT[10]);
				}
				sb.insert(0, RMB_CN_UPPER_MONETRAY_UNIT[numIndex]);
				sb.insert(0, RMB_CN_UPPER_NUMBER[numUnit]);
				getZero = false;
				zeroSize = 0;
			} else {
				++zeroSize;
				if (!(getZero)) {
					sb.insert(0, RMB_CN_UPPER_NUMBER[numUnit]);
				}
				if (numIndex == 2) {
					if (number > 0) {
						sb.insert(0, RMB_CN_UPPER_MONETRAY_UNIT[numIndex]);
					}
				} else if (((numIndex - 2) % 4 == zero) && (number % 1000 > 0)) {
					sb.insert(0, RMB_CN_UPPER_MONETRAY_UNIT[numIndex]);
				}
				getZero = true;
			}
			number = number / 10;
			++numIndex;
		}
		if (signum == -1) {
			sb.insert(0, RMB_CN_NEGATIVE);
		}
		if (scale <= 0) {
			sb.append(RMB_CN_FULL);
		} else {
			if (scale % 10 == 0) {
				sb.append(RMB_CN_FULL_BRANCH);
			}
		}
		return sb.toString();
	}
}
