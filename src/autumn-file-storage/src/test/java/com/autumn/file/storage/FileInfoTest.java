package com.autumn.file.storage;

import org.junit.Test;

/**
 * 
 * @author 老码农 2019-03-13 09:57:37
 */
public class FileInfoTest {

	private void printFileInfo(FileInfo fileInfo) {
		System.out.println("fullPath:  " + fileInfo.getFullPath());
		System.out.println("path:  " + fileInfo.getPath());
		System.out.println("fileName:  " + fileInfo.getName());
		System.out.println("extensionName:  " + fileInfo.getExtensionName());
	}

	@Test
	public void test1() {
		printFileInfo(new FileInfo("/a/b/user.data", true, 10L));
	}

	@Test
	public void test2() {
		printFileInfo(new FileInfo("user.data", true, 10L));
	}

	@Test
	public void test3() {
		printFileInfo(new FileInfo("/user.data", true, 10L));
	}

	@Test
	public void test4() {
		printFileInfo(new FileInfo("a/user.data", true, 10L));
	}

	@Test
	public void test5() {
		printFileInfo(new FileInfo("a/user", true, 10L));
	}

	@Test
	public void test6() {
		printFileInfo(new FileInfo("user", true, 10L));
	}

	@Test
	public void test7() {
		printFileInfo(new FileInfo("user/s", false, 0L));
	}

}
