package gs.dolp.system.filter;

import gs.dolp.common.domain.AjaxResData;
import gs.dolp.system.domain.Privilege;

import java.lang.reflect.Method;
import java.util.List;

import org.nutz.mvc.ActionContext;
import org.nutz.mvc.ActionFilter;
import org.nutz.mvc.View;
import org.nutz.mvc.view.UTF8JsonView;

/**
 * @author Administrator
 *	Module层检测用户操作权限的过滤器
 */
public class CheckPrivilege implements ActionFilter {

	@Override
	public View match(ActionContext context) {
		Method method = context.getMethod();
		// 获取被过滤的入口方法的path
		String methodPath = method.getDeclaringClass().getName() + "." + method.getName();
		@SuppressWarnings("unchecked")
		// 获取session中当前用户的所有权限
		List<Privilege> currentPrivileges = (List<Privilege>) context.getRequest().getSession()
				.getAttribute("currPrivs");
		// 比较该用户有没有该入口方法的权限
		for (Privilege currPriv : currentPrivileges) {
			String currPrivMethodPath = currPriv.getMethodPath();
			if (currPrivMethodPath != null && currPrivMethodPath.equals(methodPath)) {
				return null;
			}
		}
		UTF8JsonView jsonView = new UTF8JsonView(null);
		AjaxResData respData = new AjaxResData();
		respData.setSystemMessage(null, null, "用户没有此权限!");
		jsonView.setData(respData);
		return jsonView;
	}
}
