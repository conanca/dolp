package com.dolplay.dolpbase.common.service;

import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.ioc.aop.Aop;

import com.dolplay.dolpbase.common.util.DolpProperties;
import com.dolplay.dolpbase.system.domain.SysPara;

/**
 * @author Administrator
 * 共通Service类， 使用时可以继承该类。
 * 注：实体的主键是数值型的。
 * @param <T>
 */
public abstract class DolpBaseService<T> extends JqgridService<T> {

	private DolpProperties prop;

	public DolpBaseService(Dao dao) {
		super(dao);
	}

	public DolpProperties getProp() {
		return prop;
	}

	public void setProp(DolpProperties prop) {
		this.prop = prop;
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
}