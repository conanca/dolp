package gs.dolp.system.filter;

import gs.dolp.common.domain.AjaxResData;
import gs.dolp.common.domain.SystemMessage;
import gs.dolp.system.domain.Privilege;

import java.lang.reflect.Method;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.nutz.mvc.ActionFilter;
import org.nutz.mvc.View;
import org.nutz.mvc.view.UTF8JsonView;

/**
 * @author Administrator
 *	Module层检测用户是否登录的过滤器
 */
public class CheckPrivilege implements ActionFilter {

	@Override
	public View match(ServletContext context, HttpServletRequest request, Method method) {
		// 获取被过滤的入口方法的path
		String methodPath = method.getDeclaringClass().getName() + "." + method.getName();
		@SuppressWarnings("unchecked")
		// 获取session中当前用户的所有权限
		List<Privilege> currentPrivileges = (List<Privilege>) request.getSession().getAttribute("currPrivs");
		// 比较该用户有没有入口方法的权限
		for (Privilege currPriv : currentPrivileges) {
			String currPrivMethodPath = currPriv.getMethodPath();
			if (currPrivMethodPath != null && currPrivMethodPath.equals(methodPath)) {
				return null;
			}
		}
		UTF8JsonView jsonView = new UTF8JsonView(null);
		AjaxResData reData = new AjaxResData();
		reData.setUserdata(new SystemMessage(null, null, "用户没有此权限"));
		jsonView.setData(reData);
		return jsonView;
	}
}
