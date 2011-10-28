package com.dolplay.dolpbase.system.filter;

import java.lang.reflect.Method;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.nutz.json.Json;
import org.nutz.mvc.ActionContext;
import org.nutz.mvc.ActionFilter;
import org.nutz.mvc.View;
import org.nutz.mvc.view.UTF8JsonView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dolplay.dolpbase.common.domain.ExceptionAjaxResData;
import com.dolplay.dolpbase.system.domain.Privilege;
import com.dolplay.dolpbase.system.domain.User;

/**
 * @author Administrator
 *	Module层检测用户操作权限的过滤器
 */
public class CheckPrivilege implements ActionFilter {
	private static Logger logger = LoggerFactory.getLogger(CheckPrivilege.class);

	@Override
	public View match(ActionContext context) {
		ExceptionAjaxResData excpAjaxResData = new ExceptionAjaxResData();
		// 获取被过滤的入口方法的path
		Method method = context.getMethod();
		String methodPath = method.getDeclaringClass().getName() + "." + method.getName();
		// 获取session中当前用户的所有权限
		HttpSession session = context.getRequest().getSession();
		@SuppressWarnings("unchecked")
		List<Privilege> currentPrivileges = (List<Privilege>) session.getAttribute("currPrivs");
		// 比较该用户的权限list中有没有该入口方法的权限，如果有直接返回null
		for (Privilege currPriv : currentPrivileges) {
			String currPrivMethodPath = currPriv.getMethodPath();
			if (currPrivMethodPath != null && currPrivMethodPath.equals(methodPath)) {
				return null;
			}
		}
		// 将提示信息记入log并返回给前台
		StringBuilder sb = new StringBuilder("用户");
		sb.append(((User) session.getAttribute("logonUser")).getNumber());
		sb.append(" 无此权限: ");
		sb.append(methodPath);
		logger.warn(sb.toString());
		UTF8JsonView jsonView = new UTF8JsonView(null);
		excpAjaxResData.setSystemMessage(null, "用户没有此权限: " + context.getPath(), null);
		jsonView.setData(excpAjaxResData);
		logger.debug(Json.toJson(excpAjaxResData));
		return jsonView;
	}
}