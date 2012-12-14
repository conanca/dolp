package com.dolplay.dolpbase.common.filter;

import java.lang.reflect.Method;

import org.apache.shiro.aop.MethodInvocation;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.nutz.lang.Lang;
import org.nutz.mvc.ActionContext;
import org.nutz.mvc.ActionFilter;
import org.nutz.mvc.View;
import org.nutz.mvc.view.UTF8JsonView;

import com.dolplay.dolpbase.common.domain.AjaxResData;

/**
 * 在入口方法中应用Shiro注解来进行安全过滤
 * @author wendal
 *
 */
public class ShiroActionFilter implements ActionFilter {

	public View match(final ActionContext actionContext) {
		try {
			ShiroAnnotationsAuthorizingMethodInterceptor.defaultAuth.assertAuthorized(new MethodInvocation() {

				public Object proceed() throws Throwable {
					throw Lang.noImplement();
				}

				public Object getThis() {
					return actionContext.getModule();
				}

				public Method getMethod() {
					return actionContext.getMethod();
				}

				public Object[] getArguments() {
					return actionContext.getMethodArgs();
				}
			});
		} catch (AuthorizationException e) {
			UTF8JsonView jsonView = new UTF8JsonView(null);
			String permission = actionContext.getMethod().getAnnotation(RequiresPermissions.class).value()[0];
			jsonView.setData(AjaxResData.getInstanceErrorNotice("当前用户无该权限" + permission));
			return jsonView;
		}
		return null;
	}
}
