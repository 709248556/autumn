package com.autumn.evaluator.functions;

import com.autumn.evaluator.*;
import com.autumn.evaluator.annotation.FunctionRegister;
import com.autumn.evaluator.annotation.ParamRegister;
import com.autumn.evaluator.exception.FunctionParamException;
import com.autumn.util.TypeUtils;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * 数组函数
 */
public class ArrayFunction {

    private static ArrayList<Variant> arrayRows(Variant array, Variant col) {
        ArrayList<Variant> cols = new ArrayList<>(Arrays.asList(array.toArray()));
        ArrayList<Variant> rows = null;
        if (col.getVariantType() == VariantType.INTEGER) {
            int colIndex = col.intValue() - 1;
            if (colIndex < 0 || colIndex > cols.size() - 1) {
                throw new IndexOutOfBoundsException();
            }
            rows = new ArrayList<>(Arrays.asList(cols.get(colIndex).toArray()));
        } else {
            for (Variant data : cols) {
                if (data.getName() != null && data.getName().trim().equalsIgnoreCase(col.toString())) {
                    rows = new ArrayList<>(Arrays.asList(data.toArray()));
                    break;
                }
            }
        }
        if (rows == null) {
            rows = new ArrayList<>();
        }
        return rows;
    }

    private static void checkArrayParam(Variant array, String funName) {
        if (!array.isArray()) {
            throw new FunctionParamException(funName, "Array", funName + " 函数的 Array 参数必须是数组类型");
        }
    }

    /**
     * 根据列索引或列名称与行索引获取数组值
     */
    @FunctionRegister(name = "ArrayValue", category = "数组", caption = "根据列索引或列名称与行索引获取数组值 。", minParamCount = 3)
    @ParamRegister(order = 1, name = "Array", caption = "具有数组值变量或表达式。", paramType = VariantType.ARRAY)
    @ParamRegister(order = 2, name = "Col", caption = "列索引(从1开始)或列名称。", paramType = VariantType.INTEGER
            | VariantType.STRING)
    @ParamRegister(order = 3, name = "RowIndex", caption = "行索引(从1开始)。", paramType = VariantType.INTEGER)
    public static class ArrayValue extends AbstractFunctionBase implements Callable {
        @Override
        public Variant call(String name, FunctionParamContext paramContext, Context context) {
            Variant array = paramContext.getParam(1).getValue();
            checkArrayParam(array, name);
            Variant col = paramContext.getParam(2).getValue();
            Variant row = paramContext.getParam(3).getValue();
            ArrayList<Variant> rows = arrayRows(array, col);
            int rowIndex = row.intValue() - 1;
            if (rowIndex < 0 || rowIndex > rows.size() - 1) {
                throw new IndexOutOfBoundsException();
            }
            return rows.get(rowIndex);
        }
    }

    /**
     * 根据列索引或列名称返回组数首行值
     */
    @FunctionRegister(name = "ArrayFirstValue", category = "数组", caption = "根据列索引或列名称返回组数首行值,若维数为0则返回空值 。", minParamCount = 2)
    @ParamRegister(order = 1, name = "Array", caption = "具有数组值变量或表达式。", paramType = VariantType.ARRAY)
    @ParamRegister(order = 2, name = "Col", caption = "列索引(从1开始)或列名称。", paramType = VariantType.INTEGER
            | VariantType.STRING)
    public static class ArrayFirstValue extends AbstractFunctionBase implements Callable {
        @Override
        public Variant call(String name, FunctionParamContext paramContext, Context context) {
            Variant array = paramContext.getParam(1).getValue();
            checkArrayParam(array, name);
            Variant col = paramContext.getParam(2).getValue();
            ArrayList<Variant> rows = arrayRows(array, col);
            if (rows.size() > 0) {
                return rows.get(0);
            } else {
                return new Variant();
            }
        }
    }

    /**
     * 根据列索引或列名称返回组数最后行值
     */
    @FunctionRegister(name = "ArrayLastValue", category = "数组", caption = "根据列索引或列名称返回组数最后行值,若维数为0则返回空值 。", minParamCount = 2)
    @ParamRegister(order = 1, name = "Array", caption = "具有数组值变量或表达式。", paramType = VariantType.ARRAY)
    @ParamRegister(order = 2, name = "Col", caption = "列索引(从1开始)或列名称。", paramType = VariantType.INTEGER
            | VariantType.STRING)
    public static class ArrayLastValue extends AbstractFunctionBase implements Callable {
        @Override
        public Variant call(String name, FunctionParamContext paramContext, Context context) {
            Variant array = paramContext.getParam(1).getValue();
            checkArrayParam(array, name);
            Variant col = paramContext.getParam(2).getValue();
            ArrayList<Variant> rows = arrayRows(array, col);
            if (rows.size() > 0) {
                return rows.get(rows.size() - 1);
            } else {
                return new Variant();
            }
        }
    }

    /**
     * 根据列索引或列名称返回连接 STRING 值
     */
    @FunctionRegister(name = "ArrayJoin", category = "数组", caption = "根据列索引或列名称返回连接字符窜值,无若则返回空字符窜。", minParamCount = 3)
    @ParamRegister(order = 1, name = "Array", caption = "具有数组值变量或表达式。", paramType = VariantType.ARRAY)
    @ParamRegister(order = 2, name = "Col", caption = "列索引(从1开始)或列名称。", paramType = VariantType.INTEGER
            | VariantType.STRING)
    @ParamRegister(order = 3, name = "Separator", caption = "分隔符。", paramType = VariantType.STRING)
    public static class ArrayJoin extends AbstractFunctionBase implements Callable {
        @Override
        public Variant call(String name, FunctionParamContext paramContext, Context context) {
            Variant array = paramContext.getParam(1).getValue();
            checkArrayParam(array, name);
            Variant col = paramContext.getParam(2).getValue();
            Variant separator = paramContext.getParam(3).getValue();
            ArrayList<Variant> rows = arrayRows(array, col);
            if (rows.isEmpty()) {
                return new Variant("");
            }
            String[] strRows = new String[rows.size()];
            int index = 0;
            for (Variant row : rows) {
                if (row.isNull()) {
                    strRows[index] = "";
                } else {
                    strRows[index] = row.toString(true);
                }
                index++;
            }
            return new Variant(Utils.join(separator.toString(), strRows));
        }
    }

    /**
     * 获取数组列
     */
    @FunctionRegister(name = "ARRAY", category = "数组", caption = " 根据索引获取数组,返回一个值，该值可能是一个数组或结果值。", minParamCount = 2)
    @ParamRegister(order = 1, name = "Array", caption = "具有数组值变量或表达式。", paramType = VariantType.ARRAY)
    @ParamRegister(order = 2, name = "Col", caption = "列索引(从1开始)或列名称。", paramType = VariantType.INTEGER
            | VariantType.STRING)
    public static class Array extends AbstractFunctionBase implements Callable {
        @Override
        public Variant call(String name, FunctionParamContext paramContext, Context context) {
            Variant array = paramContext.getParam(1).getValue();
            checkArrayParam(array, name);
            ArrayList<Variant> arrays = new ArrayList<>(Arrays.asList(array.toArray()));
            Variant col = paramContext.getParam(2).getValue();
            if (col.getVariantType() == VariantType.INTEGER) {
                int colIndex = col.intValue() - 1;
                if (colIndex < 0 || colIndex > arrays.size() - 1) {
                    throw new IndexOutOfBoundsException();
                }
                return arrays.get(colIndex);
            } else {
                for (Variant data : arrays) {
                    if (data.getName() != null && data.getName().trim().equalsIgnoreCase(col.toString())) {
                        return data;
                    }
                }
                throw new FunctionParamException(name, "Col", String.format("找不到数组的 %s 列", col.toString()));
            }
        }
    }

    /**
     * 获取数组维数
     */
    @FunctionRegister(name = "ArrayLength", category = "数组", caption = "获取数组维数。", minParamCount = 1)
    @ParamRegister(order = 1, name = "Array", caption = "具有数组值变量或表达式。", paramType = VariantType.ARRAY)
    public static class ArrayLength extends AbstractFunctionBase implements Callable {
        @Override
        public Variant call(String name, FunctionParamContext paramContext, Context context) {
            Variant array = paramContext.getParam(1).getValue();
            checkArrayParam(array, name);
            return new Variant(array.getArrayLength());
        }
    }

}