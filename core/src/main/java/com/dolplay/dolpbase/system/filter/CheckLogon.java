package com.dolplay.dolpbase.system.filter;

import org.nutz.mvc.ActionContext;
import org.nutz.mvc.ActionFilter;
import org.nutz.mvc.View;
import org.nutz.mvc.view.UTF8JsonView;

import com.dolplay.dolpbase.common.domain.AjaxResData;

/**
 * @author Administrator
 *	Module层检测用户是否登录的过滤器
 */
public class CheckLogon implements ActionFilter {

	@Override
	public View match(ActionContext context) {
		Object obj = context.getRequest().getSession().getAttribute("CurrentUser");
		if (null == obj) {
			UTF8JsonView jsonView = new UTF8JsonView(null);
			jsonView.setData(AjaxResData.getInstanceErrorNotice("用户未登录或已退出系统!\n请先登录系统!"));
			return jsonView;
		} else {
			return null;
		}
	}
}