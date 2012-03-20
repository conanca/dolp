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

	public String getString(String key) {
		Object obj = get(key);
		if (null != obj) {
			return (String) obj;
		} else {
			return null;
		}
	}

	public Integer getInt(String key) {
		Object obj = get(key);
		if (null != obj) {
			return (Integer) obj;
		} else {
			return null;
		}
	}

	public Float getFloat(String key) {
		Object obj = get(key);
		if (null != obj) {
			return (Float) obj;
		} else {
			return null;
		}
	}

	public Double getDouble(String key) {
		Object obj = get(key);
		if (null != obj) {
			return (Double) obj;
		} else {
			return null;
		}
	}

	public Boolean getBoolean(String key) {
		Object obj = get(key);
		if (null != obj) {
			return (Boolean) obj;
		} else {
			return null;
		}
	}
}
