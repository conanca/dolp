package com.dolplay.dolpbase.system.util;

import java.util.ArrayList;
import java.util.List;

import org.nutz.dao.Dao;

import com.dolplay.dolpbase.common.util.DaoHandler;
import com.dolplay.dolpbase.system.domain.Privilege;

/**
 * 获取权限表中维护的入口方法的List的帮助类
 * @author Administrator
 *
 */
public class MethodListHandler {
	private static List<String> methodList;

	public static List<String> getMethodList() {
		if (methodList == null) {
			updateMethodList();
		}
		return methodList;
	}

	public static void updateMethodList() {
		Dao dao = DaoHandler.getDao();
		List<Privilege> privilegeList = dao.query(Privilege.class, null, null);
		methodList = new ArrayList<String>();
		for (Privilege privilege : privilegeList) {
			methodList.add(privilege.getMethodPath());
		}
	}
}