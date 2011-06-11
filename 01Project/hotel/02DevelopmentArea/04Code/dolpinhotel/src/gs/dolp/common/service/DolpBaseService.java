package gs.dolp.common.service;

import gs.dolp.system.domain.SysPara;
import gs.dolp.system.domain.User;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.ioc.aop.Aop;

/**
 * @author Administrator
 * 共通Service类， 使用时可以继承该类。
 * 注：实体的主键是数值型的。
 * @param <T>
 */
public abstract class DolpBaseService<T> extends JqgridService<T> {

	public DolpBaseService(Dao dao) {
		super(dao);
	}

	/**
	 * 获取指定名称的系统参数的值
	 * @param name
	 * @return
	 */
	@Aop(value = "log")
	public String getSysParaValue(String name) {
		SysPara sysPara = dao().fetch(SysPara.class, Cnd.where("NAME", "=", name));
		if (sysPara == null) {
			StringBuilder message = new StringBuilder("系统参数:\"");
			message.append(name);
			message.append("\"不存在!");
			throw new RuntimeException(message.toString());
		}
		return sysPara.getValue();
	}

	/**
	 * 获取指定Id的用户的 ID-NAME 的Map
	 * @param userIds
	 * @return
	 */
	@Aop(value = "log")
	public Map<String, String> getUserMap(String[] userIdArr) {
		if (userIdArr == null) {
			return null;
		}
		Map<String, String> userMap = new LinkedHashMap<String, String>();
		List<User> users = dao().query(User.class, Cnd.where("ID", "IN", userIdArr), null);
		for (User u : users) {
			userMap.put(String.valueOf(u.getId()), u.getName());
		}
		return userMap;
	}

}
