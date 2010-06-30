package gs.dolp.system.module;

import gs.dolp.jqgrid.JqgridData;
import gs.dolp.system.domain.User;
import gs.dolp.system.service.UserService;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.nutz.dao.Cnd;
import org.nutz.dao.Condition;
import org.nutz.dao.pager.Pager;
import org.nutz.ioc.annotation.InjectName;
import org.nutz.json.Json;
import org.nutz.lang.Strings;
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

	// TODO
	// 将该方法的内容放到service类中实现
	@At
	@Ok("json")
	public JqgridData<User> getGridData(@Param("page") String page, @Param("rows") String rows,
			@Param("sidx") String sidx, @Param("sord") String sord) {

		int pageNumber = 1;
		int pageSize = 10;
		String sortColumn = "ID";
		String sortOrder = "ASC";
		if (!Strings.isEmpty(page)) {
			pageNumber = Integer.parseInt(page);
		}
		if (!Strings.isEmpty(rows)) {
			pageSize = Integer.parseInt(rows);
		}
		if (!Strings.isEmpty(sidx)) {
			sortColumn = sidx;
		}
		if (!Strings.isEmpty(sord)) {
			sortOrder = sord;
		}
		Pager pager = userService.dao().createPager(pageNumber, pageSize);
		Condition cnd = Cnd.wrap("1=1 ORDER BY " + sortColumn + " " + sortOrder);
		List<User> list = userService.query(cnd, pager);
		log.debug(Json.toJson(list));
		int count = userService.count();
		int totalPage = count / pageSize + (count % pageSize == 0 ? 0 : 1);
		JqgridData<User> jq = new JqgridData<User>();
		jq.setPage(pageNumber);
		jq.setTotal(totalPage);
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

	@At
	@Fail("json")
	public User save(@Param("..") User user) {
		if (user.getId() == 0) {
			userService.dao().insert(user);
		} else {
			userService.dao().update(user);
		}
		return user;
	}

	@At
	@Fail("json")
	public void deleteRow(@Param("id") String ids) {
		if (!Strings.isEmpty(ids)) {
			Condition cnd = Cnd.wrap("ID IN (" + ids + ")");
			userService.clear(cnd);
		}
	}
}
