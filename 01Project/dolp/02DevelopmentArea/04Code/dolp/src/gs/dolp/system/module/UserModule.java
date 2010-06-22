package gs.dolp.system.module;

import gs.dolp.jqgrid.JqgridData;
import gs.dolp.system.domain.User;
import gs.dolp.system.service.UserService;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.nutz.dao.Cnd;
import org.nutz.dao.Condition;
import org.nutz.dao.pager.Pager;
import org.nutz.ioc.annotation.InjectName;
import org.nutz.json.Json;
import org.nutz.log.Log;
import org.nutz.log.Logs;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Fail;
import org.nutz.mvc.annotation.Filters;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.Param;

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
		int total = 0;
		String sortColumn = "ID";
		String sortOrder = "ASC";
		if (req.getParameter("page") != null) {
			pageNumber = Integer.parseInt(req.getParameter("page"));
		}
		if (req.getParameter("rows") != null) {
			pageSize = Integer.parseInt(req.getParameter("rows"));
		}
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
		total = userService.count();
		log.debug("共" + total + "条");
		JqgridData<User> jq = new JqgridData<User>();
		jq.setPage(pageNumber);
		jq.setTotal(total);
		jq.setRows(list);
		return jq;
	}

	@At
	@Ok("redirect:/main.jsp")
	@Fail("redirect:/1.jsp")
	@Filters
	public void login(@Param("num") String number, @Param("pwd") String password, HttpSession session) {
		Condition cnd = Cnd.where("NUMBER", "=", number).and("PASSWORD", "=", password);
		User user = userService.fetch(cnd);
		if (null == user) {
			throw new RuntimeException("Error username or password");
		}
		session.setAttribute("logonUser", user);
	}
}
