package gs.dolp.system.module;

import gs.dolp.jqgrid.JqgridData;
import gs.dolp.system.domain.User;
import gs.dolp.system.service.UserService;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.nutz.dao.Cnd;
import org.nutz.dao.Condition;
import org.nutz.dao.pager.Pager;
import org.nutz.ioc.annotation.InjectName;
import org.nutz.json.Json;
import org.nutz.log.Log;
import org.nutz.log.Logs;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Fail;
import org.nutz.mvc.annotation.Ok;

@InjectName("userModule")
@At("/system/user")
@Fail("json")
public class UserModule {

	private UserService userService;
	private static final Log log = Logs.getLog(UserModule.class);

	@At
	@Ok("json")
	public JqgridData<User> getGridData(HttpServletRequest req) {
		int pageNumber = 1;
		int pageSize = 10;
		if (req.getParameter("page") != null) {
			pageNumber = Integer.parseInt(req.getParameter("page"));
		}
		if (req.getParameter("rows") != null) {
			pageSize = Integer.parseInt(req.getParameter("rows"));
		}
		String sortColumn = "ID";
		String sortOrder = "ASC";
		if (req.getParameter("sidx") != null) {
			sortColumn = req.getParameter("sidx");
		}
		if (req.getParameter("sord") != null) {
			sortOrder = req.getParameter("sord");
		}
		Pager pager = userService.dao().createPager(pageNumber, pageSize);
		Condition cnd = Cnd.wrap("1=1 ORDER BY " + sortColumn + " " + sortOrder);
		List<User> list = userService.query(cnd, pager);
		log.debug(Json.toJson(list));
		JqgridData<User> jq = new JqgridData<User>();
		jq.setPage(pageNumber);
		jq.setTotal(0);
		jq.setRows(list);
		return jq;
	}
}
