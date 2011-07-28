package com.dolplay.dolpbase.system.filter;

import java.lang.reflect.Method;
import java.util.List;

import org.nutz.json.Json;
import org.nutz.mvc.ActionContext;
import org.nutz.mvc.ActionFilter;
import org.nutz.mvc.View;
import org.nutz.mvc.view.UTF8JsonView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dolplay.dolpbase.common.domain.AjaxResData;
import com.dolplay.dolpbase.common.domain.ExceptionAjaxResData;
import com.dolplay.dolpbase.common.domain.SystemMessage;
import com.dolplay.dolpbase.system.domain.Privilege;
import com.dolplay.dolpbase.system.util.MethodListHandler;

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
		// 判断权限表中有无该方法
		if (!MethodListHandler.getMethodList().contains(methodPath)) {
			excpAjaxResData.setSystemMessage(new SystemMessage(null, null, "权限表中无该方法!"));
			AjaxResData userdata = new AjaxResData();
			userdata.setSystemMessage(null, null, "权限表中无该方法!");
			excpAjaxResData.setUserdata(userdata);
			logger.error("权限表中无该方法!" + methodPath);
		} else {
			// 获取session中当前用户的所有权限
			@SuppressWarnings("unchecked")
			List<Privilege> currentPrivileges = (List<Privilege>) context.getRequest().getSession()
					.getAttribute("currPrivs");
			// 比较该用户的权限list中有没有该入口方法的权限，如果有直接返回null
			for (Privilege currPriv : currentPrivileges) {
				String currPrivMethodPath = currPriv.getMethodPath();
				if (currPrivMethodPath != null && currPrivMethodPath.equals(methodPath)) {
					return null;
				}
			}
			excpAjaxResData.setSystemMessage(new SystemMessage(null, "用户没有此权限!", null));
			AjaxResData userdata = new AjaxResData();
			userdata.setSystemMessage(null, "用户没有此权限!", null);
			excpAjaxResData.setUserdata(userdata);
			logger.warn("用户没有此权限!" + methodPath);
		}
		UTF8JsonView jsonView = new UTF8JsonView(null);
		jsonView.setData(excpAjaxResData);
		logger.debug(Json.toJson(excpAjaxResData));
		return jsonView;
	}
}