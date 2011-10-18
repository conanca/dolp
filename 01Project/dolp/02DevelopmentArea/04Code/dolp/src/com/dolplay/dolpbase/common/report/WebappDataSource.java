/*
 * JasperReports - Free Java Reporting Library.
 * Copyright (C) 2001 - 2011 Jaspersoft Corporation. All rights reserved.
 * http://www.jaspersoft.com
 *
 * Unless you have purchased a commercial license agreement from Jaspersoft,
 * the following license terms apply:
 *
 * This program is part of JasperReports.
 *
 * JasperReports is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * JasperReports is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with JasperReports. If not, see <http://www.gnu.org/licenses/>.
 */
package com.dolplay.dolpbase.common.report;

import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;

/**
 * @author Teodor Danciu (teodord@users.sourceforge.net)
 * @version $Id: WebappDataSource.java 4595 2011-09-08 15:55:10Z teodord $
 */
public class WebappDataSource implements JRDataSource {

	/**
	 *
	 */
	private List<Map<String, Object>> data;

	private int index = -1;

	/**
	 *
	 */
	public WebappDataSource() {
	}

	/**
	 *
	 */
	public boolean next() throws JRException {
		index++;

		return (index < data.size());
	}

	/**
	 *
	 */
	public Object getFieldValue(JRField field) throws JRException {
		Object value = null;

		String fieldName = field.getName();
		Map<String, Object> row = data.get(index);
		Set<String> keys = row.keySet();
		for (String key : keys) {
			if (key.equals(fieldName)) {
				value = row.get(key);
			}
		}
		return value;
	}

	public void setData(List<Map<String, Object>> data) {
		this.data = data;
	}

}
