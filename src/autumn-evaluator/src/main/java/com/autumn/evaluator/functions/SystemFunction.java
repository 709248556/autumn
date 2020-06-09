package com.autumn.evaluator.functions;

import com.autumn.evaluator.*;
import com.autumn.evaluator.annotation.FunctionRegister;
import com.autumn.evaluator.annotation.ParamRegister;

/**
 * 系统函数
 */
public class SystemFunction {

    /**
     * 返判断一个条件是否满足，如果满足返回一个值，如果不满则返回另一个值(此函数为系统与三元运算符由解析器特殊处理)
     */
    @FunctionRegister(name = "If", category = "逻辑", caption = "返判断一个条件是否满足，如果满足返回一个值，如果不满则返回另一个值。", minParamCount = 3)
    @ParamRegister(order = 1, name = "Logical_test", caption = "任何一个或判断为 True 或 False 的值或表达式。", paramType = VariantType.BOOLEAN)
    @ParamRegister(order = 2, name = "Value_if_true", caption = "当 logical_test 等于 True 时的返回值。", paramType = VariantType.NULL)
    @ParamRegister(order = 3, name = "Value_if_false", caption = "当 logical_test 等于 False 时的返回值。", paramType = VariantType.NULL)
    public static class If extends AbstractFunctionBase implements Callable {
        @Override
        public Variant call(String name, FunctionParamContext paramContext, Context context) {
            //短路操作
            if (paramContext.getParam(1).getValue().booleanValue()) {
                return paramContext.getParam(2).getValue();
            } else {
                return paramContext.getParam(3).getValue();
            }
        }
    }


    /**
     * 根据分支返回符合条件值,从第二个参数(第二个为表示奇数)开始(奇数参数为条件，偶数参数为返回值),最后一个参数为默认值。
     */
    @FunctionRegister(name = "Switch", category = "逻辑", caption = "根据分支返回符合条件值,从第二个参数(第二个为表示奇数)开始(奇数参数为条件，偶数参数为返回值),最后一个参数为默认值。", isDynamicParam = true, dynamicParamType = VariantType.NULL, minParamCount = 4)
    @ParamRegister(order = 1, name = "Const", caption = "任何常量或任何返回常量的表达式。", paramType = VariantType.BOOLEAN | VariantType.DATETIME | VariantType.DECIMAL | VariantType.DOUBLE | VariantType.INTEGER | VariantType.STRING | VariantType.UUID)
    public static class Switch extends AbstractFunctionBase implements Callable {
        @Override
        public Variant call(String name, FunctionParamContext paramContext, Context context) {
            //短路操作
            Variant p1 = paramContext.getParam(1).getValue();
            for (int p = 2; p < paramContext.getParamSize() - 1; p += 2) {
                if (Variant.isEquality(p1, paramContext.getParam(p).getValue())) {
                    return paramContext.getParam(p + 1).getValue();
                }
            }
            return paramContext.getParam(paramContext.getParamSize()).getValue();
        }
    }


    /**
     * 检查是否是所有参数均为 True ,如果所有参数都是 True 则返回 True 否则为False
     */
    @FunctionRegister(name = "And", category = "逻辑", caption = "检查是否是所有参数均为 True ,如果所有参数都是 True 则返回 True 否则为False 。", isDynamicParam = true, dynamicParamType = VariantType.BOOLEAN, minParamCount = 1)
    public static class And extends AbstractFunctionBase implements Callable {
        @Override
        public Variant call(String name, FunctionParamContext paramContext, Context context) {
            //短路操作
            for (FunctionParam v : paramContext.getParams()) {
                Variant value = v.getValue();
                if (!value.isNull()) {
                    if (value.getVariantType() != VariantType.BOOLEAN) {
                        throw new RuntimeException(String.format("%s函数必须参数必须是 VariantType.BOOLEAN 类型 。", name));
                    }
                    if (!value.booleanValue()) {
                        return new Variant(false);
                    }
                } else {
                    return new Variant(false);
                }
            }
            return new Variant(true);
        }
    }


    /**
     * 任何参数返回 True ，则返回 True ，只有所有参数都参数都是 False 时，才返回 False
     */
    @FunctionRegister(name = "Or", category = "逻辑", caption = "任何参数返回 True ，则返回 True ，只有所有参数都参数都是 False 时，才返回 False 。", isDynamicParam = true, dynamicParamType = VariantType.BOOLEAN, minParamCount = 1)
    public static class Or extends AbstractFunctionBase implements Callable {
        @Override
        public Variant call(String name, FunctionParamContext paramContext, Context context) {
            //短路操作
            for (FunctionParam v : paramContext.getParams()) {
                Variant value = v.getValue();
                if (!value.isNull()) {
                    if (value.getVariantType() != VariantType.BOOLEAN) {
                        throw new RuntimeException(String.format("%s函数必须参数必须是 VariantType.BOOLEAN 类型 。", name));
                    }
                    if (value.booleanValue()) {
                        return new Variant(true);
                    }
                }
            }
            return new Variant(false);
        }
    }


    /**
     * 返回逻辑值 False
     */
    @FunctionRegister(name = "FALSE", category = "逻辑", caption = "返回逻辑值 False 。")
    public static class False extends AbstractFunctionBase implements Callable {
        @Override
        public Variant call(String name, FunctionParamContext paramContext, Context context) {
            return new Variant(false);
        }
    }


    /**
     * 返回逻辑值 True
     */
    @FunctionRegister(name = "True", category = "逻辑", caption = "返回逻辑值 True 。")
    public static class True extends AbstractFunctionBase implements Callable {
        @Override
        public Variant call(String name, FunctionParamContext paramContext, Context context) {
            return new Variant(true);
        }
    }


    /**
     * 返回 NULL 值。
     */
    @FunctionRegister(name = "NULL", category = "信息", caption = "返回 NULL 值。")
    public static class Null extends AbstractFunctionBase implements Callable {
        @Override
        public Variant call(String name, FunctionParamContext paramContext, Context context) {
            return new Variant();
        }
    }


    /**
     * 根据参数返回相反的逻辑值,如果参数为True 则返回 False , 否则返回 True
     */
    @FunctionRegister(name = "Not", category = "逻辑", caption = "根据参数返回相反的逻辑值,如果参数为True 则返回 False , 否则返回 True 。", minParamCount = 1)
    @ParamRegister(order = 1, name = "logical", caption = "任何一个或判断为 True 或 False 的值或表达式。", paramType = VariantType.BOOLEAN)
    public static class Not extends AbstractFunctionBase implements Callable {
        @Override
        public Variant call(String name, FunctionParamContext paramContext, Context context) {
            Variant param1 = paramContext.getParam(1).getValue();
            return new Variant(!param1.booleanValue());
        }
    }


    /**
     * 判断是一值是为 NULL 或 DBNull.Value,符合则返回 True,否则返回 false
     */
    @FunctionRegister(name = "IsNull", category = "逻辑", caption = "判断是一值是为 NULL 或 DBNull.Value,符合则返回 True,否则返回 false 。", minParamCount = 1)
    @ParamRegister(order = 1, name = "value", caption = "任何值或表达式。", paramType = VariantType.NULL)
    public static class IsNull extends AbstractFunctionBase implements Callable {
        @Override
        public Variant call(String name, FunctionParamContext paramContext, Context context) {
            Variant param1 = paramContext.getParam(1).getValue();
            return new Variant(param1.isNull());
        }
    }


    /**
     * 判断是一值是为 NULL 或 空白字符时,符合则返回 True,否则返回 false
     */
    @FunctionRegister(name = "IsNullOrEmpty", category = "逻辑", caption = "判断是一值是为 NULL 或 空白字符,符合则返回 True,否则返回 false 。", minParamCount = 1)
    @ParamRegister(order = 1, name = "value", caption = "任何值或表达式。", paramType = VariantType.NULL)
    public static class IsNullOrEmpty extends AbstractFunctionBase implements Callable {
        @Override
        public Variant call(String name, FunctionParamContext paramContext, Context context) {
            Variant param1 = paramContext.getParam(1).getValue();
            if (param1.isNull() || (param1.isString() && "".equals(param1.getValue().toString().trim()))) {
                return new Variant(true);
            }
            return new Variant(false);
        }
    }


    /**
     * 判断是否存在一个变量,存在则返回 True,否则返回 false
     */
    @FunctionRegister(name = "IsExistVariable", category = "逻辑", caption = "判断是否存在一个变量,存在则返回 True,否则返回 false 。", minParamCount = 1)
    @ParamRegister(order = 1, name = "name", caption = "变量名称。", paramType = VariantType.STRING)
    public static class IsExistVariable extends AbstractFunctionBase implements Callable {
        @Override
        public Variant call(String name, FunctionParamContext paramContext, Context context) {
            String vName = paramContext.getParam(1).getValue().toString();
            boolean exist = false;
            if (context != null) {
                exist = context.hasVariable(vName);
            }
            if (!exist) {
                exist = Variable.hasVariable(vName);
            }
            return new Variant(exist);
        }
    }


    /**
     * 设置变量(不存在测添加，存在则变更),并返回设置的变量
     */
    @FunctionRegister(name = "Set", category = "系统", caption = "设置变量(不存在测添加，存在则变更),并返回设置的变量。", minParamCount = 2)
    @ParamRegister(order = 1, name = "name", caption = "变量名称。", paramType = VariantType.STRING)
    @ParamRegister(order = 2, name = "value", caption = "变量值。", paramType = VariantType.NULL)
    public static class Set extends AbstractFunctionBase implements Callable {
        @Override
        public Variant call(String name, FunctionParamContext paramContext, Context context) {
            if (context == null) {
                throw new RuntimeException("上下文不能为空。");
            }
            String vName = paramContext.getParam(1).getValue().toString().trim();
            Variant value = paramContext.getParam(2).getValue();
            try {
                context.lock();
                context.setVariable(vName, value);
                return value;
            } finally {
                context.unLock();
            }
        }
    }


    /**
     * 删除变量(成功则返回 True 否则返回 False)
     */
    @FunctionRegister(name = "Del", category = "系统", caption = "删除变量(成功则返回 True 否则返回 False)。", minParamCount = 1)
    @ParamRegister(order = 1, name = "name", caption = "变量名称。", paramType = VariantType.STRING)
    public static class Del extends AbstractFunctionBase implements Callable {
        @Override
        public Variant call(String name, FunctionParamContext paramContext, Context context) {
            if (context == null) {
                throw new RuntimeException("上下文不能为空。");
            }
            String vName = paramContext.getParam(1).getValue().toString().trim();
            try {
                context.lock();
                return new Variant(context.delVariable(vName));
            } finally {
                context.unLock();
            }
        }
    }


    /**
     * 执行一个循环,并将表达式存入变量，返回变量的最终值。
     */
    @FunctionRegister(name = "For", category = "系统", caption = "执行一个循环,并将表达式存入变量，返回变量的最终值(会产生临时变量 s = 循环值, m = 终值)。", minParamCount = 5)
    @ParamRegister(order = 1, name = "StartIndex", caption = "开始索引(包含)。", paramType = VariantType.INTEGER)
    @ParamRegister(order = 2, name = "MaxIndex", caption = "最大索引(包含)。", paramType = VariantType.INTEGER)
    @ParamRegister(order = 3, name = "VariableName", caption = "变量名称", paramType = VariantType.STRING)
    @ParamRegister(order = 4, name = "StartValue", caption = "开始值", paramType = VariantType.NULL)
    @ParamRegister(order = 5, name = "Expression", caption = "计算表达式", paramType = VariantType.STRING)
    @ParamRegister(order = 6, name = "Setp", caption = "步长(默认为1)", paramType = VariantType.INTEGER, defaultValue = "1")
    public static class For extends AbstractFunctionBase implements Callable {
        @Override
        public Variant call(String name, FunctionParamContext paramContext, Context context) {
            if (context == null) {
                throw new RuntimeException("上下文不能为空。");
            }
            Variant p1 = paramContext.getParam(1).getValue();
            Variant p2 = paramContext.getParam(2).getValue();
            Variant p3 = paramContext.getParam(3).getValue();
            Variant p4 = paramContext.getParam(4).getValue();
            Variant p5 = paramContext.getParam(5).getValue();
            Variant p6 = paramContext.getParam(6).getValue();
            if (Variant.lessThan(p2, p1)) {
                throw new RuntimeException("最大索引必须大于或等于开始索引。");
            }
            if ("".equals(p3.toString().trim())) {
                throw new RuntimeException("变量名称不能为空。");
            }
            if (p4.isNull()) {
                throw new RuntimeException("开始值不能为空。");
            }
            if ("".equals(p5.toString().trim())) {
                throw new RuntimeException("表达式不能为空。");
            }
            int startIndex = p1.intValue();
            int maxIndex = p2.intValue();
            int setp = p6.intValue();
            if (setp <= 0) {
                throw new RuntimeException("步长必须大于等于1。");
            }
            String variableName = p3.toString();
            String expression = p5.toString().trim();
            Variant v = p4;
            if (maxIndex >= startIndex) {
                Variant tempStartIndex = null;
                Variant tempMaxIndex = null;
                try {
                    context.lock();
                    context.setVariable(variableName, v);
                    if (context.hasVariable("s")) {
                        tempStartIndex = context.getVariable("s").clone();
                    }
                    if (context.hasVariable("m")) {
                        tempMaxIndex = context.getVariable("m").clone();
                    }
                    context.setVariable("m", new Variant(maxIndex));
                    DefaultEvaluatorSession eva = new DefaultEvaluatorSession();
                    for (int i = startIndex; i <= maxIndex; i += setp) {
                        context.setVariable("s", new Variant(i));
                        v = eva.parse(expression, context).clone();
                        context.setVariable(variableName, v);
                    }
                } finally {
                    if (tempStartIndex != null) {
                        context.setVariable("s", new Variant(tempStartIndex.getValue()));
                    } else {
                        context.delVariable("s");
                    }
                    if (tempMaxIndex != null) {
                        context.setVariable("m", new Variant(tempMaxIndex.getValue()));
                    } else {
                        context.delVariable("m");
                    }
                    context.unLock();
                }
            }
            return v;
        }
    }

}