package com.dolplay.dolpbase.common.util;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * 字符串MD5加密的帮助类
 * @author Administrator
 */
public class StringUtils {

	public static String encoderByMd5(String str) {
		String newstr = DigestUtils.md5Hex(str);
		return newstr;
	}

	public static void main(String[] args) {
		System.out.println(StringUtils.encoderByMd5("123"));
	}
}