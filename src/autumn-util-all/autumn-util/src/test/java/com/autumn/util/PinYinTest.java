package com.autumn.util;

import org.junit.Test;

/**
 * 拼音测式
 *
 * @Description TODO
 * @Author 老码农
 * @Date 2019-08-05 3:38
 */
public class PinYinTest {

    /**
     *
     */
    @Test
    public void firstPinyinTest1() {
        System.out.println(PinYinUtils.getFirstPinyinString("中华人民共和国wwea12232", false, false));
    }

    /**
     *
     */
    @Test
    public void firstPinyinTest2() {
        System.out.println(PinYinUtils.getFirstPinyinString("中华人民共和国wwea12232", true, false));
    }

    /**
     *
     */
    @Test
    public void firstPinyinTest3() {
        System.out.println(PinYinUtils.getFirstPinyinString("中华人民共和国wwea12232", true, true));
    }

    /**
     *
     */
    @Test
    public void pinyinTest1() {
        System.out.println(PinYinUtils.getPinyinString("中华人民232abc共和国", false, false));
    }

    /**
     *
     */
    @Test
    public void pinyinTest2() {
        System.out.println(PinYinUtils.getPinyinString("中华人民232abc共和国", true, false));
    }

    /**
     *
     */
    @Test
    public void pinyinTest3() {
        System.out.println(PinYinUtils.getPinyinString("中华人民232abc共和国", true, true));
    }

}
