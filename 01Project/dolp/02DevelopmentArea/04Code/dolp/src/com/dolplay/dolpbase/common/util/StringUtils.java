package com.dolplay.dolpbase.common.util;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * 字符串的帮助类
 * @author Administrator
 */
public class StringUtils {

	public static String encoderByMd5(String str) {
		String newstr = DigestUtils.md5Hex(str);
		return newstr;
	}

	public static String getFileSuffix(String fileName) {
		if (fileName == null || !fileName.contains(".")) {
			return null;
		} else {
			return fileName.substring(fileName.lastIndexOf("."));
		}
	}

	public static void main(String[] args) {
		System.out.println(StringUtils.encoderByMd5("123"));
	}

	public static String quote(CharSequence cs, char c) {
		if (null == cs)
			return null;
		return new StringBuilder().append(c).append(cs).append(c).toString();
	}
}