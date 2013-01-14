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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dolplay.dolpbase.common.domain.AjaxResData;

/**
 * 在入口方法中应用Shiro注解来进行安全过滤
 * @author wendal
 *
 */
public class ShiroActionFilter implements ActionFilter {
	private static Logger logger = LoggerFactory.getLogger(ShiroActionFilter.class);

	@Override
	public View match(final ActionContext actionContext) {
		try {
			ShiroAnnotationsAuthorizingMethodInterceptor.defaultAuth.assertAuthorized(new MethodInvocation() {

				@Override
				public Object proceed() throws Throwable {
					throw Lang.noImplement();
				}

				@Override
				public Object getThis() {
					return actionContext.getModule();
				}

				@Override
				public Method getMethod() {
					return actionContext.getMethod();
				}

				@Override
				public Object[] getArguments() {
					return actionContext.getMethodArgs();
				}
			});
		} catch (AuthorizationException e) {
			UTF8JsonView jsonView = new UTF8JsonView(null);
			String permission = actionContext.getMethod().getAnnotation(RequiresPermissions.class).value()[0];
			logger.warn("权限不足", e);
			jsonView.setData(AjaxResData.getInstanceErrorNotice("当前用户无该权限" + permission));
			return jsonView;
		} catch (Exception e) {
			UTF8JsonView jsonView = new UTF8JsonView(null);
			logger.warn("鉴权异常", e);
			jsonView.setData(AjaxResData.getInstanceErrorNotice("鉴权异常"));
			return jsonView;
		}
		return null;
	}
}
