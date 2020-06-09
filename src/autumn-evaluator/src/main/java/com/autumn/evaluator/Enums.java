package com.autumn.evaluator;

import com.autumn.util.CollectionUtils;
import java.util.Map;

/**
 * 枚举
 * 
 * @author 老码农 2018-03-28 14:10:44
 */
public class Enums {

	/**
	 * 解析类型
	 */
	public enum TokenType {
		/**
		 * 运算符
		 */
		DELIMITER(0),
		/**
		 * 整数
		 */
		INTEGER(1),
		/**
		 * 十进制数
		 */
		DECIMAL(2),
		/**
		 * 双精度浮点数
		 */
		DOUBLE(3),
		/**
		 * 字符
		 */
		STRING(4),
		/**
		 * 日期时间
		 */
		DATETIME(5),
		/**
		 * 函数
		 */
		FUNCTION(6),
		/**
		 * 变量
		 */
		VARIABLE(7),
		/**
		 * 全局唯一标识
		 */
		UUID(8),
		/**
		 * 动态数组
		 */
		ARRAY(9),
		/**
		 * 静态数组
		 */
		STATIC_ARRAY(10);

		private int intValue;
		private static Map<Integer, TokenType> mappings;

		private synchronized static Map<Integer, TokenType> getMappings() {
			if (mappings == null) {
				mappings = CollectionUtils.newHashMap();
			}
			return mappings;
		}

		TokenType(int value) {
			intValue = value;
			TokenType.getMappings().put(value, this);
		}

		public int getValue() {
			return intValue;
		}

		public static TokenType forValue(int value) {
			return getMappings().get(value);
		}
	}

	/**
	 * 节点类型
	 */
	public enum NodeType {

		/**
		 * 未知
		 */
		NONE(0),
		/**
		 * 加法运算，如 a + b，针对数值操作数
		 */
		ADD(1),
		/**
		 * 逻辑 AND 运算，如 a > 0 and b > 0 。
		 */
		AND(2),
		/**
		 * 字符连接符，如 a &amp; b
		 */
		CONCAT(3),
		/**
		 * 除法运算，如 (a / b)，针对数值操作数。
		 */
		DIVIDE(4),
		/**
		 * 表示相等比较的节点，如 a = b 。
		 */
		EQUAL(5),
		/**
		 * "大于"比较，如 (a &gt; b)。
		 */
		GREATER_THAN(6),
		/**
		 * "大于或等于"比较，如 (a &gt;= b)。
		 */
		GREATER_THAN_OR_EQUAL(7),
		/**
		 * "小于"比较，如 (a &lt; b)。
		 */
		LESS_THAN(8),
		/**
		 * "小于或等于"比较，如 (a &lt;= b)。
		 */
		LESS_THAN_OR_EQUAL(9),
		/**
		 * 算术余数运算，如 a % b。
		 */
		MODULO(10),
		/**
		 * 乘法运算，如 (a * b) 。
		 */
		MULTIPLY(11),
		/**
		 * 一元算术求反运算，如 (-a) 。
		 */
		NEGATE(12),
		/**
		 * 一元加法运算，如 (+a) 。
		 */
		UNARY_PLUS(13),
		/**
		 * 一元百分数运算，如 10%
		 */
		PERCENT(14),
		/**
		 * 一元逻辑求反运算，如 !a 或 not a 。
		 */
		NOT(15),
		/**
		 * 不相等比较，如 a != b 或 a &lt;&gt; 6 。
		 */
		NOT_EQUAL(16),
		/**
		 * 逻辑 OR 运算，如 a &gt; 0 or b &gt; 0 。
		 */
		OR(17),
		/**
		 * 数字进行幂运算的数学运算，如 a ^ b 。
		 */
		POWER(18),
		/**
		 * 减法运算，如 (a - b) 。
		 */
		SUBTRACT(19),
		/**
		 * 条件运算
		 */
		CONDITIONAL(50),
		/**
		 * 常量
		 */
		CONSTANT(51),
		/**
		 * 变量
		 */
		VARIABLE(52),
		/**
		 * 条件组
		 */
		CONDITION_GROUP(53),
		/**
		 * 数组
		 */
		ARRAY(54),
		/**
		 * 调用函数
		 */
		CALL(55),
		/**
		 * 空值
		 */
		NULL(99);

		private int intValue;
		private static Map<Integer, NodeType> mappings;

		private synchronized static Map<Integer, NodeType> getMappings() {
			if (mappings == null) {
				mappings = CollectionUtils.newHashMap();
			}
			return mappings;
		}

		NodeType(int value) {
			intValue = value;
			NodeType.getMappings().put(value, this);
		}

		public int getValue() {
			return intValue;
		}

		public static NodeType forValue(int value) {
			return getMappings().get(value);
		}
	}
}
