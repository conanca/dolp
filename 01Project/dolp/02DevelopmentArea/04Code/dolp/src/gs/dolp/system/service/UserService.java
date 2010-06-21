package gs.dolp.system.service;

import gs.dolp.system.domain.User;

import org.nutz.dao.Dao;
import org.nutz.service.IdEntityService;

public class UserService extends IdEntityService<User> {

	public UserService(Dao dao) {
		super(dao);
	}
}
