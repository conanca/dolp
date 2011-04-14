package gs.dolp.system.filter;

import gs.dolp.common.domain.AjaxResData;
import gs.dolp.common.domain.SystemMessage;

import org.nutz.mvc.ActionContext;
import org.nutz.mvc.ActionFilter;
import org.nutz.mvc.View;
import org.nutz.mvc.view.UTF8JsonView;

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
			AjaxResData reData = new AjaxResData();
			reData.setSystemMessage(new SystemMessage(null, null, "请先登录系统"));
			jsonView.setData(reData);
			return jsonView;
		}
		return null;
	}
}
