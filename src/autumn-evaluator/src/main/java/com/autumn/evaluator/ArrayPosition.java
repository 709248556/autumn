package com.autumn.evaluator;

import com.autumn.exception.ArgumentNullException;
import com.autumn.exception.ArgumentOverflowException;
import com.autumn.util.StringUtils;

import java.io.Serializable;

/**
 * 数组位置
 */
public final class ArrayPosition implements Serializable {

    /**
     * 序列号
     */
    private static final long serialVersionUID = 5688781572920509477L;

    /**
     * 实例化 ArrayPosition 类新实例
     *
     * @param row          行
     * @param col          列
     * @param positionName 位值
     * @param colName      列名称
     */
    private ArrayPosition(int row, int col, String positionName, String colName) {
        this.setRowNumber(row);
        this.setColNumber(col);
        this.setPositionName(positionName);
        this.setColName(colName);
    }

    /**
     * 获取行号
     */
    private int rowNumber;

    public int getRowNumber() {
        return rowNumber;
    }

    private void setRowNumber(int value) {
        rowNumber = value;
    }

    /**
     * 获取列号
     */
    private int colNumber;

    public int getColNumber() {
        return colNumber;
    }

    private void setColNumber(int value) {
        colNumber = value;
    }

    /**
     * 获取列名称
     */
    private String colName;

    public String getColName() {
        return colName;
    }

    private void setColName(String value) {
        colName = value;
    }

    /**
     * 获取位置名称
     */
    private String positionName;

    public String getPositionName() {
        return positionName;
    }

    private void setPositionName(String value) {
        positionName = value;
    }

    /**
     * 获取数组位置
     *
     * @param row 行号
     * @param col 列号
     * @return
     */
    public static ArrayPosition getArrayPosition(int row, int col) {
        if (row < 1) {
            throw new ArgumentOverflowException("row", "行号不能小于 1 。");
        }
        if (col < 1) {
            throw new ArgumentOverflowException("col", "列号不能小于 1 。");
        }
        String colName = ArrayPosition.getArrayColName(col);
        return new ArrayPosition(row, col, (colName + (new Integer(row)).toString()), colName);
    }

    /**
     * 获取数组位置
     *
     * @param positionName 位置
     * @return
     */
    public static ArrayPosition getArrayPosition(String positionName) {
        if (StringUtils.isNullOrBlank(positionName)) {
            throw new ArgumentNullException("positionName");
        }
        String left, right;
        int index;
        positionName = Extension.toTrimUpper(positionName);
        for (index = 0; index < positionName.length(); index++) {
            char value = positionName.charAt(index);
            if (Character.isDigit(value)) {
                break;
            } else {
                if (Extension.isEnglishLetter(value)) {
                    throw new RuntimeException("数组标识符必须由字母A-Z构成。");
                }
            }
        }
        left = positionName.substring(0, index);
        right = positionName.substring(index);
        if (StringUtils.isNullOrBlank(right)) {
            throw new RuntimeException("数组位置必须由字母(A-Z)和十进制(0-9)构成。");
        }
        int rowNumber = Integer.parseInt(right);
        if (rowNumber <= 0) {
            throw new RuntimeException("数组位置必须由字母(A-Z)和十进制(0-9)构成。");
        }
        int colNumber = ArrayPosition.getArrayColNumber(left, false);
        return new ArrayPosition(rowNumber, colNumber, left, positionName);
    }

    /**
     * 获取数组名称
     *
     * @param col 输入列号,始终从1开始
     * @return 返回列名
     * @throws ArgumentNullException col 小于 1 引发的异常。
     */
    public static String getArrayColName(int col) {
        if (col < 1) {
            throw new ArgumentOverflowException("col 不能小于1。");
        }
        StringBuilder result = new StringBuilder();
        int bit;
        int value;
        int begin = col;
        while (begin > 0) {
            bit = begin % 26;
            begin /= 26;
            if (bit == 0) {
                value = 26;
                begin--;
            } else {
                value = bit;
            }
            result.insert(0, (char) (value + 64));
        }
        return result.toString();
    }

    /**
     * 获取数组列号
     *
     * @param colName 名称(A-ZZZ)
     * @return
     */
    public static int getArrayColNumber(String colName) {
        return ArrayPosition.getArrayColNumber(colName, true);
    }

    /**
     * 获取数组列号
     *
     * @param colName 名称(A-ZZZ)
     * @param check   检查
     * @return
     */
    private static int getArrayColNumber(String colName, final boolean check) {
        if (StringUtils.isNullOrBlank(colName)) {
            throw new ArgumentNullException("colName");
        }
        if (check && !Extension.isEnglishLetter(colName)) {
            throw new RuntimeException("数组标识符必须由字母A-Z构成。");
        }
        Integer colNumber = 0;
        int temp;
        for (int index = 0; index < colName.length(); index++) {
            temp = colNumber << 3;
            temp += colNumber + colNumber;
            colNumber = colNumber << 4;
            colNumber += temp + colName.charAt(index) - 64;
        }
        return colNumber;
    }

    /**
     * 比较位置是否相等
     *
     * @param left  左
     * @param right 右
     * @return
     */
    public static boolean comparablePosition(ArrayPosition left, ArrayPosition right) {
        if (left != null && right != null) {
            return left.getRowNumber() == right.getRowNumber() && left.getColNumber() == right.getColNumber();
        } else {
            return left == null && right == null;
        }
    }

    /**
     * 输出
     *
     * @return
     */
    @Override
    public String toString() {
        return this.getColName();
    }

}