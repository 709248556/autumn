package com.autumn.word;

import com.autumn.exception.ExceptionUtils;

/**
 * 标记
 */
public class Token {

	/**
	 * 默认
	 */
	public static Token DEFAULT = new Token("${", "}", "#{", "}", ".", "&");

	/**
	 * 普通开始标记
	 */
	private final String generalBeginMark;

	/**
	 * 普通结束标记
	 */
	private final String generalEndMark;

	/**
	 * 集合开始标记
	 */
	private final String collectionBeginMark;

	/**
	 * 集合结束标记
	 */
	private final String collectionEndMark;

	/**
	 * 数组连接标记
	 */
	private final String arrayConnectMark;

	/**
	 * 数组合并行标记
	 */
	private final String arrayMergeRowMark;

	/**
	 * 实例化 Token 类新实例
	 *
	 * @param generalBeginMark
	 *            普通开始标记
	 * @param generalEndMark
	 *            普通结束标记
	 * @param collectionBeginMark
	 *            集合开始标记
	 * @param collectionEndMark
	 *            集合结束标记
	 * @param arrayConnectMark
	 *            数组连接标记
	 * @param arrayMergeRowMark
	 *            数组合并行标记
	 */
	public Token(String generalBeginMark, String generalEndMark, String collectionBeginMark, String collectionEndMark,
			String arrayConnectMark, String arrayMergeRowMark) {
		ExceptionUtils.checkNotNullOrBlank(generalBeginMark, "generalBeginMark");
		ExceptionUtils.checkNotNullOrBlank(generalEndMark, "generalEndMark");
		ExceptionUtils.checkNotNullOrBlank(collectionBeginMark, "collectionBeginMark");
		ExceptionUtils.checkNotNullOrBlank(collectionEndMark, "collectionEndMark");
		ExceptionUtils.checkNotNullOrBlank(arrayConnectMark, "arrayConnectMark");
		ExceptionUtils.checkNotNullOrBlank(arrayMergeRowMark, "arrayMergeRowMark");
		if (generalBeginMark.compareToIgnoreCase(generalEndMark) == 0) {
			throw new IllegalArgumentException("generalBeginMark 与 generalEndMark 不能相等。");
		}
		if (collectionBeginMark.compareToIgnoreCase(collectionEndMark) == 0) {
			throw new IllegalArgumentException("collectionBeginMark 与 collectionEndMark 不能相等。");
		}
		if (generalBeginMark.compareToIgnoreCase(collectionBeginMark) == 0) {
			throw new IllegalArgumentException("generalBeginMark 与 collectionBeginMark 不能相等。");
		}
		this.generalBeginMark = generalBeginMark;
		this.generalEndMark = generalEndMark;
		this.collectionBeginMark = collectionBeginMark;
		this.collectionEndMark = collectionEndMark;
		this.arrayConnectMark = arrayConnectMark;
		this.arrayMergeRowMark = arrayMergeRowMark;
	}

	/**
	 * 获取普通开始标记
	 * 
	 * @return
	 */
	public final String getGeneralBeginMark() {
		return this.generalBeginMark;
	}

	/**
	 * 获取普通结束标记
	 * 
	 * @return
	 */
	public final String getGeneralEndMark() {
		return this.generalEndMark;
	}

	/**
	 * 获取集合开始标记
	 * 
	 * @return
	 */
	public final String getCollectionBeginMark() {
		return this.collectionBeginMark;
	}

	/**
	 * 获取集合结束标记
	 * 
	 * @return
	 */
	public final String getCollectionEndMark() {
		return this.collectionEndMark;
	}

	/**
	 * 获取 数组连接标记
	 * 
	 * @return
	 */
	public final String getArrayConnectMark() {
		return this.arrayConnectMark;
	}

	/**
	 * 获取数组合并行标记
	 * 
	 * @return
	 */
	public final String getArrayMergeRowMark() {
		return this.arrayMergeRowMark;
	}

}