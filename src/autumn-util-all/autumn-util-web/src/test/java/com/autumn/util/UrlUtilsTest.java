package com.autumn.util;

import org.junit.Test;

/**
 * <p>
 * </p>
 *
 * @description TODO
 * @author: 老码农
 * @create: 2020-04-11 00:56
 **/
public class UrlUtilsTest {

    @Test
    public void test1() {
        String url1 = "//ab//a/b";
        String url2 = "aa/b/c//";
        String url = UrlUtils.getLowerCaseRequestUrl(url1, url2);
        System.out.println(url);
        org.junit.Assert.assertTrue(url.equalsIgnoreCase("/ab/a/b/aa/b/c"));
    }

    @Test
    public void test2() {
        String url1 = "/  /ab/ /a / b";
        String url2 = " aa/ b/c/ /   ";
        String url = UrlUtils.getLowerCaseRequestUrl(url1, url2);
        System.out.println(url);
        org.junit.Assert.assertTrue(url.equalsIgnoreCase("/ab/a/b/aa/b/c"));
    }

    @Test
    public void test3() {
        String url1 = "ab/ /a / b";
        String url2 = " aa/ b/c/ /   ";
        String url = UrlUtils.getLowerCaseRequestUrl(url1, url2);
        System.out.println(url);
        org.junit.Assert.assertTrue(url.equalsIgnoreCase("/ab/a/b/aa/b/c"));
    }

    @Test
    public void test4() {
        String url2 = " aa/ b/c/ /   ";
        String url = UrlUtils.getLowerCaseRequestUrl(null, url2);
        System.out.println(url);
        org.junit.Assert.assertTrue(url.equalsIgnoreCase("/aa/b/c"));
    }

    @Test
    public void test5() {
        String url1 = "ab/ /a / b";
        String url = UrlUtils.getLowerCaseRequestUrl(url1, null);
        System.out.println(url);
        org.junit.Assert.assertTrue(url.equalsIgnoreCase("/ab/a/b"));
    }

    @Test
    public void test6() {
        String url1 = "////";
        String url2 = " aa/ b/c/ /   ";
        String url = UrlUtils.getLowerCaseRequestUrl(url1, url2);
        System.out.println(url);
        org.junit.Assert.assertTrue(url.equalsIgnoreCase("/aa/b/c"));
    }

    @Test
    public void test7() {
        String url1 = "////";
        String url2 = " aa/ B/C/ /   ";
        String url = UrlUtils.getLowerCaseRequestUrl(url1, url2);
        System.out.println(url);
        org.junit.Assert.assertTrue(url.equalsIgnoreCase("/aa/b/c"));
    }

    @Test
    public void test8() {
        String url = UrlUtils.getLowerCaseRequestUrl(null,null);
        System.out.println(url);
        org.junit.Assert.assertTrue(url.equalsIgnoreCase("/"));
    }
}
