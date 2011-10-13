package com.dolplay.dolpbase.common.domain;

import java.util.Map;

/**
 * 被注入的 DolpProperties类，用于获取配置文件中的一个或多个属性
 * @author Administrator
 *
 */
public class DolpProperties {

	private Map<String, Object> propMap;

	public DolpProperties(Map<String, Object> propMap) {
		super();
		this.propMap = propMap;
	}

	public Map<String, Object> getPropMap() {
		return propMap;
	}

	public void setPropMap(Map<String, Object> propMap) {
		this.propMap = propMap;
	}

	public Object get(String key) {
		return propMap.get(key);
	}
}
