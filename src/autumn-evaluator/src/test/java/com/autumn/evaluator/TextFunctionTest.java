package com.autumn.evaluator;

import org.junit.Test;

public class TextFunctionTest extends AppTest {

    public TextFunctionTest() {
        super("文本函数测试");
    }

    /**
     * 返回一个指定字符或文本字符串在字符串中第一次出现的位置，从左到右查找（忽略大小写）
     */
    public void testSearch() {
        String expression = "Search(\"m\", \"company\")";
        test(expression);
    }

    /**
     * 将文本字符串的首字母及任何非字母字符之后的首字母转换成大写。将其余的字母转换成小写。
     */
    @Test
    public void testProper() {
        String expression = "Proper(\"may1jacklove\")";
        test(expression);
    }

    /**
     * 返回 value 引用的文本
     */
    public void testT() {
        String expression = "T(\"may1jacklove\")";
        test(expression);
    }

    /**
     * 将半角（单字节）字符更改为全角（双字节）字符，与双字节字符集 (DBCS) 一起使用。
     */
    public void testWidechar() {
        String expression = "Widechar(\"may1jacklove\")";
        test(expression);
    }

    /**
     * 对于双字节字符集 (DBCS) 语言，将全角（双字节）字符更改为半角（单字节）字符。
     */
    public void testAsc() {
        String expression = "Asc(\"ｍａｙ１ｊａｃｋｌｏｖｅ\")";
        test(expression);
    }

    /**
     * 将指定的数值或逻辑转换成文本
     */
    public void testText() {
        String expression = "Text(1>5)";
        test(expression);
    }

    /**
     * 根据本机中的字符集，返回由代码数字指定的字符
     */
    public void testChar() {
        String expression = "Char(255)";
        test(expression);
    }

    /**
     * 返回文本字符串第一个字符在本机所用字符集中的数字代码
     */
    public void testCode() {
        String expression = "Code(\"abc\")";
        test(expression);
    }

    /**
     * 将多个文本字符串合并成一个(至需要一个参数，但不限制参数上限)
     */
    public void testConcatenate() {
        String expression = "concatenate(\"abc\", \"de\")";
        test(expression);
    }

    /**
     * 比较两个字符串是否完全相同(区分大小写),返回 真 或 假
     */
    public void testExact() {
        String expression = "Exact(\"abc\", \"abC\")";
        test(expression);
    }

    /**
     * 返回一个字符串在另外一个字符串出现的起始位置(区分大小写)
     */
    public void testFind() {
        String expression = "Find(\"abc\", \"aaabcde\", 1)";
        test(expression);
    }

    /**
     * 从一个文本字符串的第一个字符开始返回指定个数的字符
     */
    public void testLeft() {
        String expression = "Left(\"abc\", 1)";
        test(expression);
    }

    /**
     * 返回字符串中的字符个数
     */
    public void testLen() {
        String expression = "Len(\"abc\")";
        test(expression);
    }

    /**
     * 将一个文本字符串的所有字母转换成为小写形式
     */
    public void testLower() {
        String expression = "Lower(\"abDDc\")";
        test(expression);
    }

    /**
     * 从文本字符串中指定的起始位置返回指定长度的字符
     */
    public void testMid() {
        String expression = "Mid(\"abDDc\", 1, 3)";
        test(expression);
    }

    /**
     * 从文本字符串中指定的起始位置返回指定长度的字符
     */
    public void testReplace() {
        String expression = "Replace(\"abDDc\", 1, 2, \"zzz\")";
        test(expression);
    }

    /**
     * 将空值或空白字符替换为另个一表达式,若不为空值或空白表达式，则返回原值
     */
    public void testNv1() {
        String expression = "Nv1(\"abDDc\", \"zzz\")";
        test(expression);
    }

    /**
     * 根据指定次数重复文本。可用 REPT 在一个中重复填写一个字符串
     */
    public void testRept() {
        String expression = "Rept(\"abDDc\", 2)";
        test(expression);
    }

    /**
     * 从一个文本字符串的最后一个字符开始返回指定个数的字符
     */
    public void testRight() {
        String expression = "Right(\"abDDc\", 2)";
        test(expression);
    }

    /**
     * 将数值转换成人民币大写
     */
    public void testRmb() {
        String expression = "Rmb(123563.33)";
        test(expression);
    }

    /**
     * 将字符串中的部分字符串以新字符替换旧字符
     */
    public void testSubstitute() {
        String expression = "Substitute(\"abcd\", \"a\", \"zz\")";
        test(expression);
    }

    /**
     * 删除字符窜两头空格
     */
    public void testTrim() {
        String expression = "Trim(\"  abcd \",)";
        test(expression);
    }

    /**
     * 将一个文本字符串的所有字母转换成为大写形式
     */
    public void testUpper() {
        String expression = "Upper(\"  abcEE \",)";
        test(expression);
    }

    /**
     * 将一个代表数值的文本字符串转换成数值
     */
    public void testValue() {
        String expression = "Value(\"123\",)";
        test(expression);
    }

    /**
     * 格式化函数
     */
    public void testFormat() {
        String expression = "Format(123.3333,\"N2\")";
        test(expression);
    }

    /**
     * 生成一个新的UUID
     */
    public void testNewUUID() {
        String expression = "NewUUID()";
        test(expression);
    }
}
