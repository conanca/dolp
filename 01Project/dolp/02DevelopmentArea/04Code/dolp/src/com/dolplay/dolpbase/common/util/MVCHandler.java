package com.dolplay.dolpbase.common.util;

import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;

public class MVCHandler {

	public static String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}

	public static String getBrowser(HttpServletRequest request) {
		String Agent = request.getHeader("User-Agent");
		StringTokenizer st = new StringTokenizer(Agent, ";");
		st.nextToken();
		//得到用户的浏览器名
		String userbrowser = st.nextToken();
		return userbrowser;
	}
}
