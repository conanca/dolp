package gs.dolp.common.filter;

import gs.dolp.common.jqgrid.domain.ResponseSysMsgData;
import gs.dolp.common.jqgrid.domain.SystemMessage;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;

import org.nutz.mvc.ActionFilter;
import org.nutz.mvc.View;
import org.nutz.mvc.view.UTF8JsonView;

public class CheckLogon implements ActionFilter {

	@Override
	public View match(HttpServletRequest request, Method method) {
		Object obj = request.getSession().getAttribute("logonUser");
		if (null == obj) {
			UTF8JsonView jsonView = new UTF8JsonView(null);
			ResponseSysMsgData reData = new ResponseSysMsgData();
			reData.setUserdata(new SystemMessage(null, null, "请先登录系统"));
			jsonView.setData(reData);
			return jsonView;
		}
		return null;
	}
}
