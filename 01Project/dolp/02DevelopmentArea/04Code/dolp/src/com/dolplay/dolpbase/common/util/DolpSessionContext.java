package com.dolplay.dolpbase.common.util;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

public class DolpSessionContext {
	private static Map<String, HttpSession> sessionMap = new HashMap<String, HttpSession>();

	public static synchronized void AddSession(HttpSession session) {
		if (session != null) {
			sessionMap.put(session.getId(), session);
		}
	}

	public static synchronized void DelSession(HttpSession session) {
		if (session != null) {
			sessionMap.remove(session.getId());
		}
	}

	public static synchronized HttpSession getSession(String sessionId) {
		if (sessionId == null) {
			return null;
		} else {
			return sessionMap.get(sessionId);
		}
	}
}