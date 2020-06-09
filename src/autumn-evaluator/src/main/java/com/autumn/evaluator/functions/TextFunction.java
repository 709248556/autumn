package com.autumn.evaluator.functions;

import com.autumn.evaluator.*;
import com.autumn.evaluator.annotation.FunctionRegister;
import com.autumn.evaluator.annotation.ParamRegister;
import com.autumn.evaluator.exception.FunctionParamException;
import com.autumn.util.BigDecimalUtils;
import com.autumn.util.DateUtils;
import com.autumn.util.TypeUtils;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 文本函数
 * @author ycg
 */
public class TextFunction {

    /**
     * 返回一个指定字符或文本字符串在字符串中第一次出现的位置，从左到右查找（忽略大小写）
     */
    @FunctionRegister(name = "Search", category = "文本函数", caption = "返回一个指定字符或文本字符串在字符串中第一次出现的位置，从左到右查找（忽略大小写）。", minParamCount = 2)
    @ParamRegister(order = 1, name = "find_text", caption = "是要查找的文本。", paramType = VariantType.STRING)
    @ParamRegister(order = 2, name = "within_text", caption = "是要在其中查找 find_text 的文本。", paramType = VariantType.STRING)
    @ParamRegister(order = 3, name = "start_num", caption = "是 within_text 中开始查找的字符的位置。", paramType = VariantType.INTEGER, defaultValue = "1")
    public static class Search extends AbstractFunctionBase implements Callable {
        @Override
        public Variant call(String name, FunctionParamContext paramContext, Context context) {
            Variant p1 = paramContext.getParam(1).getValue();
            Variant p2 = paramContext.getParam(2).getValue();
            Variant p3 = paramContext.getParam(3).getValue();
            String findText = p1.toString();
            String withinText = p2.toString();
            int startNum = TypeUtils.toConvert(int.class, p3.getValue());
            int index = withinText.indexOf(findText, startNum);
            if (index < 0) {
                throw new RuntimeException("未找到指定的值。");
            }
            return new Variant(index + 1);
        }
    }

    private static Pattern LETTER_PATTERN = Pattern.compile("[a-zA-Z]+");

    /**
     * 将文本字符串的首字母及任何非字母字符之后的首字母转换成大写。将其余的字母转换成小写。
     */
    @FunctionRegister(name = "Proper", category = "文本函数", caption = "将文本字符串的首字母及任何非字母字符之后的首字母转换成大写。将其余的字母转换成小写。", minParamCount = 1)
    @ParamRegister(order = 1, name = "text", caption = "包括在一组双引号中的文本字符串、返回文本值的公式或是对包含文本的值。", paramType = VariantType.STRING)
    public static class Proper extends AbstractFunctionBase implements Callable {
        @Override
        public Variant call(String name, FunctionParamContext paramContext, Context context) {
            Variant p1 = paramContext.getParam(1).getValue(); // 获取参数
            if ("".equals(p1.toString())) {
                return new Variant("");
            }
            String text = p1.toString();
            Matcher mMactchCol = LETTER_PATTERN.matcher(text);
            StringBuilder sb = new StringBuilder();
            int index = 0;
            while (mMactchCol.find()) {
                if (text.indexOf(mMactchCol.group()) > index) {
                    sb.append(text, index, text.indexOf(mMactchCol.group()));
                }
                sb.append(mMactchCol.group().substring(0, 1).toUpperCase());
                if (mMactchCol.group().length() > 1) {
                    sb.append(mMactchCol.group().substring(1).toLowerCase());
                }
                index = text.indexOf(mMactchCol.group()) + mMactchCol.group().length();
            }
            if (index < text.length()) {
                sb.append(text.substring(index));
            }
            return new Variant(sb.toString());
        }
    }

    /**
     * 返回 value 引用的文本
     */
    @FunctionRegister(name = "T", category = "文本函数", caption = "返回 value 引用的文本。", minParamCount = 1)
    @ParamRegister(order = 1, name = "value", caption = "任何值或表达式。", paramType = VariantType.NULL)
    public static class T extends AbstractFunctionBase implements Callable {
        @Override
        public Variant call(String name, FunctionParamContext paramContext, Context context) {
            Variant p1 = paramContext.getParam(1).getValue();
            if (p1.isString()) {
                return p1;
            } else {
                return new Variant("");
            }
        }
    }

    /**
     * 将半角（单字节）字符更改为全角（双字节）字符，与双字节字符集 (DBCS) 一起使用。
     */
    @FunctionRegister(name = "Widechar", category = "文本函数", caption = "将半角（单字节）字符更改为全角（双字节）字符，与双字节字符集 (DBCS) 一起使用。", minParamCount = 1)
    @ParamRegister(order = 1, name = "text", caption = "需要转换成双字节的文本。", paramType = VariantType.STRING)
    public static class Widechar extends AbstractFunctionBase implements Callable {
        @Override
        public Variant call(String name, FunctionParamContext paramContext, Context context) {
            Variant p1 = paramContext.getParam(1).getValue(); // 获取参数
            if ("".equals(p1.toString())) {
                return new Variant("");
            }
            char[] c = p1.toString().toCharArray();
            for (int i = 0; i < c.length; i++) {
                if (c[i] == 32) {
                    c[i] = (char) 12288;
                    continue;
                }
                if (c[i] < 127) {
                    c[i] = (char) (c[i] + 65248);
                }
            }
            return new Variant(new String(c));
        }
    }

    /**
     * 对于双字节字符集 (DBCS) 语言，将全角（双字节）字符更改为半角（单字节）字符。
     */
    @FunctionRegister(name = "Asc", category = "文本函数", caption = "对于双字节字符集 (DBCS) 语言，将全角（双字节）字符更改为半角（单字节）字符。", minParamCount = 1)
    @ParamRegister(order = 1, name = "text", caption = "需要转换成单字节的文本。", paramType = VariantType.STRING)
    public static class Asc extends AbstractFunctionBase implements Callable {
        @Override
        public Variant call(String name, FunctionParamContext paramContext, Context context) {
            Variant p1 = paramContext.getParam(1).getValue(); // 获取参数
            if ("".equals(p1.toString())) {
                return new Variant("");
            }
            char[] c = p1.toString().toCharArray();
            for (int i = 0; i < c.length; i++) {
                if (c[i] == 12288) {
                    c[i] = (char) 32;
                    continue;
                }
                if (c[i] > 65280 && c[i] < 65375) {
                    c[i] = (char) (c[i] - 65248);
                }
            }
            return new Variant(new String(c));
        }
    }

    /**
     * 将指定的数值或逻辑转换成文本
     */
    @FunctionRegister(name = "Text", category = "文本函数", caption = "将指定的数值或逻辑转换成文本。", minParamCount = 1)
    @ParamRegister(order = 1, name = "value", caption = "任何值或表达式。", paramType = VariantType.NULL)
    public static class Text extends AbstractFunctionBase implements Callable {
        @Override
        public Variant call(String name, FunctionParamContext paramContext, Context context) {
            Variant param1 = paramContext.getParam(1).getValue();
            return new Variant(String.valueOf(param1));
        }
    }

    /**
     * 根据本机中的字符集，返回由代码数字指定的字符
     */
    @FunctionRegister(name = "Char", category = "文本函数", caption = "根据本机中的字符集，返回由代码数字指定的字符。", minParamCount = 1)
    @ParamRegister(order = 1, name = "number", caption = "介于1到255(具体大小根据字符集)之间的任一数字，该数字对应着要返回的字符。", paramType = VariantType.INTEGER)
    public static class Char extends AbstractFunctionBase implements Callable {
        @Override
        public Variant call(String name, FunctionParamContext paramContext, Context context) {
            Variant param1 = paramContext.getParam(1).getValue();
            return new Variant(String.valueOf(TypeUtils.toConvert(char.class, param1.getValue())));
        }
    }

    /**
     * 返回文本字符串第一个字符在本机所用字符集中的数字代码
     */
    @FunctionRegister(name = "Code", category = "文本函数", caption = "返回文本字符串第一个字符在本机所用字符集中的数字代码。", minParamCount = 1)
    @ParamRegister(order = 1, name = "text", caption = "返回文本字符串第一个字符在本机所用字符集中的数字代码。", paramType = VariantType.STRING)
    public static class Code extends AbstractFunctionBase implements Callable {
        @Override
        public Variant call(String name, FunctionParamContext paramContext, Context context) {
            Variant param1 = paramContext.getParam(1).getValue(); // 获取参数
            String strtemp = String.valueOf(param1).trim();
            if (strtemp.length() > 0) {
                strtemp = strtemp.substring(0, 1);
                int runInt = strtemp.charAt(0);
                return new Variant(runInt);
            } else {
                throw new FunctionParamException(name, paramContext.getParam(1).getName(), "参数值为 NULL 或空字符窜。");
            }
        }
    }

    /**
     * 将多个文本字符串合并成一个(至需要一个参数，但不限制参数上限)
     */
    @FunctionRegister(name = "concatenate", category = "文本函数", caption = "将多个文本字符串合并成一个。", isDynamicParam = true, dynamicParamType = VariantType.STRING, minParamCount = 1)
    public static class Concatenate extends AbstractFunctionBase implements Callable {
        @Override
        public Variant call(String name, FunctionParamContext paramContext, Context context) {
            StringBuilder value = new StringBuilder();
            for (FunctionParam v : paramContext.getParams()) {
                Variant pv = v.getValue().clone();
                if (!pv.isNull()) {
                    value.append(concatenate(pv));
                }
            }
            return new Variant(value.toString());
        }
    }

    public static String concatenate(Variant v) {
        StringBuilder value = new StringBuilder();
        if (v.isArray()) {
            for (Variant arr : v.toArray()) {
                value.append(concatenate(arr));
            }
        } else {
            if (!v.isNull()) {
                value.append(v.toString());
            }
        }
        return value.toString();
    }

    /**
     * 比较两个字符串是否完全相同(区分大小写),返回 真 或 假
     */
    @FunctionRegister(name = "Exact", category = "文本函数", caption = "比较两个字符串是否完全相同(区分大小写),返回 True 或 False 。", minParamCount = 2)
    @ParamRegister(order = 1, name = "text1", caption = "第一个字符窜。", paramType = VariantType.STRING)
    @ParamRegister(order = 2, name = "text2", caption = "第二个字符窜。", paramType = VariantType.STRING)
    public static class Exact extends AbstractFunctionBase implements Callable {
        @Override
        public Variant call(String name, FunctionParamContext paramContext, Context context) {
            Variant param1 = paramContext.getParam(1).getValue();
            Variant param2 = paramContext.getParam(2).getValue();
            return new Variant(param1.toString().equals(param2.toString()));
        }
    }

    /**
     * 返回一个字符串在另外一个字符串出现的起始位置(区分大小写)
     */
    @FunctionRegister(name = "Find", category = "文本函数", caption = "返回一个字符串在另外一个字符串出现的起始位置(区分大小写) 。", minParamCount = 2)
    @ParamRegister(order = 1, name = "find_text", caption = "是要查找的文本。", paramType = VariantType.STRING)
    @ParamRegister(order = 2, name = "within_text", caption = "是要在其中查找 find_text 的文本。", paramType = VariantType.STRING)
    @ParamRegister(order = 3, name = "start_num", caption = "是 within_text 中开始查找的字符的位置 默认为 1。", paramType = VariantType.INTEGER, defaultValue = "1")
    public static class Find extends AbstractFunctionBase implements Callable {
        @Override
        public Variant call(String name, FunctionParamContext paramContext, Context context) {
            Variant param1 = paramContext.getParam(1).getValue();
            Variant param2 = paramContext.getParam(2).getValue();
            Variant param3 = paramContext.getParam(3).getValue(); // 获取参数
            int intStatr;
            intStatr = param3.intValue();
            if (intStatr != 0) {
                intStatr -= 1;
            }
            String strStart = String.valueOf(param1);
            String strEnd = String.valueOf(param2);
            int move = strEnd.indexOf(strStart, intStatr) + 1;
            return new Variant(move);
        }
    }

    /**
     * 从一个文本字符串的第一个字符开始返回指定个数的字符
     */
    @FunctionRegister(name = "Left", category = "文本函数", caption = "从一个文本字符串的第一个字符开始返回指定个数的字符 。", minParamCount = 2)
    @ParamRegister(order = 1, name = "text", caption = "要提取的字符串。", paramType = VariantType.STRING)
    @ParamRegister(order = 2, name = "Num_chars", caption = "要从字符串提取的字符数。", paramType = VariantType.INTEGER)
    public static class Left extends AbstractFunctionBase implements Callable {
        @Override
        public Variant call(String name, FunctionParamContext paramContext, Context context) {
            Variant param1 = paramContext.getParam(1).getValue();
            Variant param2 = paramContext.getParam(2).getValue();
            int intLen = param2.intValue();
            String strValue = param1.toString();
            if (intLen <= 0) {
                intLen = 1;
            }
            if (intLen > strValue.length()) {
                intLen = strValue.length();
            }
            return new Variant(strValue.substring(0, intLen));
        }
    }

    /**
     * 返回字符串中的字符个数
     */
    @FunctionRegister(name = "Len", category = "文本函数", caption = "返回字符串中的字符个数 。", minParamCount = 1)
    @ParamRegister(order = 1, name = "text", caption = "要计算长度的文本字符串，包括空格。", paramType = VariantType.STRING)
    public static class Len extends AbstractFunctionBase implements Callable {
        @Override
        public Variant call(String name, FunctionParamContext paramContext, Context context) {
            Variant param1 = paramContext.getParam(1).getValue();
            return new Variant(param1.toString().length());
        }
    }

    /**
     * 将一个文本字符串的所有字母转换成为小写形式
     */
    @FunctionRegister(name = "Lower", category = "文本函数", caption = "将一个文本字符串的所有字母转换成为小写形式 。", minParamCount = 1)
    @ParamRegister(order = 1, name = "text", caption = "将要转换为小写的字符串。其中不是英文字母的字符不变。", paramType = VariantType.STRING)
    public static class Lower extends AbstractFunctionBase implements Callable {
        @Override
        public Variant call(String name, FunctionParamContext paramContext, Context context) {
            Variant param1 = paramContext.getParam(1).getValue();
            return new Variant(param1.toString().toLowerCase());
        }
    }

    /**
     * 从文本字符串中指定的起始位置返回指定长度的字符
     */
    @FunctionRegister(name = "Mid", category = "文本函数", caption = "从文本字符串中指定的起始位置返回指定长度的字符 。", minParamCount = 3)
    @ParamRegister(order = 1, name = "text", caption = "是包含要提取字符的文本字符串。", paramType = VariantType.STRING)
    @ParamRegister(order = 2, name = "Start_num", caption = "是文本中要提取的第一个字符的位置。", paramType = VariantType.INTEGER)
    @ParamRegister(order = 3, name = "Num_chars", caption = "定希望 MID 从文本中返回字符的个数。", paramType = VariantType.INTEGER)
    public static class Mid extends AbstractFunctionBase implements Callable {
        @Override
        public Variant call(String name, FunctionParamContext paramContext, Context context) {
            Variant param1 = paramContext.getParam(1).getValue();
            Variant param2 = paramContext.getParam(2).getValue();
            Variant param3 = paramContext.getParam(3).getValue(); // 获取参数
            String strTxt = param1.toString();
            int intStart = param2.intValue();
            int intLen = param3.intValue();
            if (intStart <= 0) {
                throw new FunctionParamException(name, paramContext.getParam(2).getName(), "第二个参数不能小于或等于0");
            }
            intStart -= 1;
            if (intLen < 1) {
                throw new FunctionParamException(name, paramContext.getParam(3).getName(), "第三个参数不能小于1");
            }
            if (intLen > strTxt.length() - intStart) {
                intLen = strTxt.length() - intStart;
            }
            return new Variant(strTxt.substring(intStart, intStart + intLen));
        }
    }

    /**
     * 将一个字符串中的部份字符用另一个字符串替换
     */
    @FunctionRegister(name = "Replace", category = "文本函数", caption = "将一个字符串中的部份字符用另一个字符串替换 。", minParamCount = 4)
    @ParamRegister(order = 1, name = "Old_text", caption = "是要替换其部分字符的文本。", paramType = VariantType.STRING)
    @ParamRegister(order = 2, name = "Start_num", caption = "是要用 New_text 替换的 Old_text 中字符的位置。", paramType = VariantType.INTEGER)
    @ParamRegister(order = 3, name = "Num_chars", caption = "是希望 REPLACE 使用 New_text 替换 Old_text 中字符的个数。", paramType = VariantType.INTEGER)
    @ParamRegister(order = 4, name = "New_text", caption = "是要用于替换 Old_text 中字符的文本。", paramType = VariantType.STRING)
    public static class Replace extends AbstractFunctionBase implements Callable {
        @Override
        public Variant call(String name, FunctionParamContext paramContext, Context context) {
            Variant param1 = paramContext.getParam(1).getValue();
            Variant param2 = paramContext.getParam(2).getValue();
            Variant param3 = paramContext.getParam(3).getValue(); // 获取参数
            Variant param4 = paramContext.getParam(4).getValue();
            String strTxt = param1.toString();
            if (!"".equals(strTxt)) {
                int intStart = param2.intValue();
                int intLen = param3.intValue();
                if (intStart <= 0) {
                    throw new FunctionParamException(name, paramContext.getParam(2).getName(), "开始位置小于等于0。");
                }
                intStart -= 1;
                if (intLen < 1) {
                    throw new FunctionParamException(name, paramContext.getParam(3).getName(), "长度小于1。");
                }
                if (intLen > strTxt.length() - intStart) {
                    intLen = strTxt.length() - intStart;
                }

                String strOld = strTxt.substring(intStart, intStart + intLen);
                return new Variant(strTxt.replace(strOld, param4.toString()));
            } else {
                return new Variant(param4.toString());
            }
        }
    }

    /**
     * 将空值或空白字符替换为另个一表达式,若不为空值或空白表达式，则返回原值
     */
    @FunctionRegister(name = "Nv1", category = "文本函数", caption = "将空值或空白字符替换为另个一表达式,若不为空值或空白表达式，则返回原值。", minParamCount = 2)
    @ParamRegister(order = 1, name = "source", caption = "原表达式。", paramType = VariantType.NULL)
    @ParamRegister(order = 2, name = "replace", caption = "替换表达式。", paramType = VariantType.NULL)
    public static class Nv1 extends AbstractFunctionBase implements Callable {
        @Override
        public Variant call(String name, FunctionParamContext paramContext, Context context) {
            Variant param1 = paramContext.getParam(1).getValue();
            Variant param2 = paramContext.getParam(2).getValue();
            if (param1.isNull() || "".equals(param1.toString().trim())) {
                return param2;
            }
            return param1;
        }
    }

    /**
     * 根据指定次数重复文本。可用 REPT 在一个中重复填写一个字符串
     */
    @FunctionRegister(name = "Rept", category = "文本函数", caption = "根据指定次数重复文本。可用 REPT 在一个中重复填写一个字符串 。", minParamCount = 2)
    @ParamRegister(order = 1, name = "text", caption = "需要重复显示的文本。", paramType = VariantType.STRING)
    @ParamRegister(order = 2, name = "Start_num", caption = "是指定文本重复次数的正数。", paramType = VariantType.INTEGER)
    public static class Rept extends AbstractFunctionBase implements Callable {
        @Override
        public Variant call(String name, FunctionParamContext paramContext, Context context) {
            Variant param1 = paramContext.getParam(1).getValue();
            Variant param2 = paramContext.getParam(2).getValue();
            String strTxt = param1.toString();
            if (!"".equals(strTxt)) {
                int intRept = param2.intValue();
                if (intRept <= 0) {
                    throw new FunctionParamException(name, paramContext.getParam(2).getName(), "重复次数小于等于0。");
                }
                StringBuilder runStr = new StringBuilder();
                for (int i = 1; i <= intRept; i++) {
                    runStr.append(strTxt);
                }
                return new Variant(runStr.toString());
            } else {
                return new Variant("");
            }
        }
    }

    /**
     * 从一个文本字符串的最后一个字符开始返回指定个数的字符
     */
    @FunctionRegister(name = "Right", category = "文本函数", caption = "从一个文本字符串的最后一个字符开始返回指定个数的字符 。", minParamCount = 2)
    @ParamRegister(order = 1, name = "text", caption = "要提取的字符串。", paramType = VariantType.STRING)
    @ParamRegister(order = 2, name = "Num_chars", caption = "要从字符串提取的字符数。", paramType = VariantType.INTEGER)
    public static class Right extends AbstractFunctionBase implements Callable {
        @Override
        public Variant call(String name, FunctionParamContext paramContext, Context context) {
            Variant param1 = paramContext.getParam(1).getValue();
            Variant param2 = paramContext.getParam(2).getValue();
            int intMove = param2.intValue();
            int intLen;
            String strValue = param1.toString();
            if (intMove <= 0) {
                intMove = 1;
            }
            if (intMove > strValue.length()) {
                intMove = strValue.length();
            }
            intLen = intMove;
            intMove = strValue.length() - intMove;
            return new Variant(strValue.substring(intMove, intMove + intLen));
        }
    }

    /**
     * 将数值转换成人民币大写
     */
    @FunctionRegister(name = "Rmb", category = "文本函数", caption = "将数值转换成人民币大写 。", minParamCount = 1)
    @ParamRegister(order = 1, name = "NUMBER", caption = "将要成人民币大写的数值。", paramType = VariantType.NUMBER)
    public static class Rmb extends AbstractFunctionBase implements Callable {
        @Override
        public Variant call(String name, FunctionParamContext paramContext, Context context) {
            try {
                Variant param1 = paramContext.getParam(1).getValue();
                BigDecimal numberOfMoney = param1.toBigDecimal();
                return new Variant(BigDecimalUtils.toRmb(numberOfMoney));
            } catch (Exception e) {
                throw new FunctionParamException(name, "NUMBER", e.getMessage());
            }
        }
    }

    /**
     * 将字符串中的部分字符串以新字符替换旧字符
     */
    @FunctionRegister(name = "Substitute", category = "文本函数", caption = "将字符串中的部分字符串以新字符替换旧字符 。", minParamCount = 3)
    @ParamRegister(order = 1, name = "text", caption = "为需要替换其中字符的文本。", paramType = VariantType.STRING)
    @ParamRegister(order = 2, name = "Old_text", caption = "为需要替换的旧文本。", paramType = VariantType.STRING)
    @ParamRegister(order = 3, name = "New_text", caption = "用于替换 Old_text 的文本。", paramType = VariantType.STRING)
    public static class Substitute extends AbstractFunctionBase implements Callable {
        @Override
        public Variant call(String name, FunctionParamContext paramContext, Context context) {
            Variant param1 = paramContext.getParam(1).getValue();
            Variant param2 = paramContext.getParam(2).getValue();
            Variant param3 = paramContext.getParam(3).getValue();
            String strTxt = param1.toString();
            String strOld = param2.toString();
            String strNew = param3.toString();
            if (!"".equals(strTxt)) {
                return new Variant(strTxt.replace(strOld, strNew));
            } else {
                return new Variant("");
            }
        }
    }

    /**
     * 删除字符窜两头空格
     */
    @FunctionRegister(name = "Trim", category = "文本函数", caption = "删除字符窜两头空格。", minParamCount = 1)
    @ParamRegister(order = 1, name = "text", caption = "将要转换成文本的值或表达式。", paramType = VariantType.STRING)
    public static class Trim extends AbstractFunctionBase implements Callable {
        @Override
        public Variant call(String name, FunctionParamContext paramContext, Context context) {
            Variant param1 = paramContext.getParam(1).getValue();
            return new Variant(param1.toString().trim());
        }
    }

    /**
     * 将一个文本字符串的所有字母转换成为大写形式
     */
    @FunctionRegister(name = "Upper", category = "文本函数", caption = "将一个文本字符串的所有字母转换成为大写形式。", minParamCount = 1)
    @ParamRegister(order = 1, name = "text", caption = "将要转换为大写的字符串。其中不是英文字母的字符不变。", paramType = VariantType.STRING)
    public static class Upper extends AbstractFunctionBase implements Callable {
        @Override
        public Variant call(String name, FunctionParamContext paramContext, Context context) {
            Variant param1 = paramContext.getParam(1).getValue();
            return new Variant(param1.toString().toUpperCase());
        }
    }

    /**
     * 将一个代表数值的文本字符串转换成数值
     */
    @FunctionRegister(name = "Value", category = "文本函数", caption = "将一个代表数值的文本字符串转换成数值。", minParamCount = 1)
    @ParamRegister(order = 1, name = "text", caption = "将要转换为的字符串。其中不是英文字母的字符不变。", paramType = VariantType.NULL)
    public static class Value extends AbstractFunctionBase implements Callable {
        @Override
        public Variant call(String name, FunctionParamContext paramContext, Context context) {
            Variant param1 = paramContext.getParam(1).getValue();
            if (param1.isNull() || "".equals(param1.toString().trim())) {
                return new Variant(0);
            }
            if (param1.isDateTime()) {
                return new Variant(param1.toDate().getTime());
            } else {
                return new Variant(new BigDecimal(param1.toString().trim()));
            }
        }
    }

    /**
     * 格式化函数
     */
    @FunctionRegister(name = "Format", category = "文本函数", caption = "格式化一个数字或日期，并返回其字符窜。", minParamCount = 2)
    @ParamRegister(order = 1, name = "value", caption = "将要格式化转换的值。", paramType = VariantType.NULL)
    @ParamRegister(order = 2, name = "expression", caption = "格式表达式,N2表示2位小数，yyyy-MM-dd 表示年月日,空值将返回空字符窜。", paramType = VariantType.STRING)
    public static class Format extends AbstractFunctionBase implements Callable {
        @Override
        public Variant call(String name, FunctionParamContext paramContext, Context context) {
            Variant param1 = paramContext.getParam(1).getValue();
            if (param1.isNull()) {
                return new Variant("");
            }
            Variant param2 = paramContext.getParam(2).getValue();
            if (param2.isNull()) {
                throw new FunctionParamException(name, "expression", "格式表达式不能为空。");
            }
            String str = param2.toString();
            if (param1.isDateTime()) {
                return new Variant(DateUtils.dateFormat((Date) param1.getValue(), str));
            } else {
                if (param1.isNumber()) {
                    if (param1.getVariantType() == VariantType.DECIMAL) {
                        return new Variant(new BigDecimal(param1.getValue().toString())); // 这里是C#中根据str进行格式化，待修改
                    }
                    if (param1.getVariantType() == VariantType.INTEGER) {
                        return new Variant(param1.longValue()); // 这里是C#中根据str进行格式化，待修改
                    }
                    return new Variant(param1.doubleValue()); // 这里是C#中根据str进行格式化，待修改
                } else {
                    if (param1.isUUID()) {
                        return new Variant(param1.getValue()); // 这里是C#中根据str进行格式化，待修改
                    }
                    if (param1.isNull()) {
                        return new Variant(str); // 这里是C#中根据str进行格式化，待修改
                    }
                }
                return param1;
            }
        }
    }

    /**
     * 生成一个新的UUID
     */
    @FunctionRegister(name = "NewUUID", category = "文本函数", caption = "生成一个新的UUID。")
    public static class NewUUID extends AbstractFunctionBase implements Callable {
        @Override
        public Variant call(String name, FunctionParamContext paramContext, Context context) {
            return new Variant(UUID.randomUUID());
        }
    }
}