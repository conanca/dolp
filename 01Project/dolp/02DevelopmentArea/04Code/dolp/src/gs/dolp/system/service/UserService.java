package gs.dolp.system.service;

import gs.dolp.jqgrid.JqgridData;
import gs.dolp.system.domain.User;
import gs.dolp.system.module.UserModule;

import java.util.List;

import org.nutz.dao.Cnd;
import org.nutz.dao.Condition;
import org.nutz.dao.Dao;
import org.nutz.dao.pager.Pager;
import org.nutz.json.Json;
import org.nutz.lang.Strings;
import org.nutz.log.Log;
import org.nutz.log.Logs;
import org.nutz.service.IdEntityService;

public class UserService extends IdEntityService<User> {

	private static final Log log = Logs.getLog(UserModule.class);

	public UserService(Dao dao) {
		super(dao);
	}

	public JqgridData<User> getGridData(String page, String rows, String sidx, String sord) {
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
		Pager pager = dao().createPager(pageNumber, pageSize);
		Condition cnd = Cnd.wrap("1=1 ORDER BY " + sortColumn + " " + sortOrder);
		List<User> list = query(cnd, pager);
		log.debug(Json.toJson(list));
		int count = count();
		int totalPage = count / pageSize + (count % pageSize == 0 ? 0 : 1);
		JqgridData<User> jq = new JqgridData<User>();
		jq.setPage(pageNumber);
		jq.setTotal(totalPage);
		jq.setRows(list);
		return jq;
	}

	public User userAuthenticate(String number, String password) {
		Condition cnd = Cnd.where("NUMBER", "=", number).and("PASSWORD", "=", password);
		User user = fetch(cnd);
		if (null == user) {
			throw new RuntimeException("Error username or password");
		}
		return user;
	}

	public void deleteUsers(String ids) {
		if (!Strings.isEmpty(ids)) {
			Condition cnd = Cnd.wrap("ID IN (" + ids + ")");
			clear(cnd);
		}
	}
}
