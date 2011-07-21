package com.dolplay.dolpbase.system.filter;

import org.nutz.mvc.ActionContext;
import org.nutz.mvc.ActionFilter;
import org.nutz.mvc.View;
import org.nutz.mvc.view.UTF8JsonView;

import com.dolplay.dolpbase.common.domain.AjaxResData;
import com.dolplay.dolpbase.common.domain.ExceptionAjaxResData;
import com.dolplay.dolpbase.common.domain.SystemMessage;

/**
 * @author Administrator
 *	Module层检测用户是否登录的过滤器
 */
public class CheckLogon implements ActionFilter {

	@Override
	public View match(ActionContext context) {
		Object obj = context.getRequest().getSession().getAttribute("logonUser");
		if (null == obj) {
			UTF8JsonView jsonView = new UTF8JsonView(null);
			ExceptionAjaxResData excpAjaxResData = new ExceptionAjaxResData();
			excpAjaxResData.setSystemMessage(new SystemMessage(null, null, "请先登录系统!"));
			AjaxResData userdata = new AjaxResData();
			userdata.setSystemMessage(null, null, "请先登录系统!");
			excpAjaxResData.setUserdata(userdata);
			jsonView.setData(excpAjaxResData);
			return jsonView;
		}
		return null;
	}
}