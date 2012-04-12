package com.dolplay.dolpbase.common.util;

import org.nutz.dao.Cnd;
import org.nutz.dao.Condition;
import org.nutz.dao.Dao;
import org.nutz.dao.Sqls;
import org.nutz.dao.entity.Entity;
import org.nutz.dao.sql.OrderBy;
import org.nutz.dao.sql.Sql;
import org.nutz.lang.Strings;

public class DaoUtils {

	/**
	 * 查询某sql的结果条数
	 * @param dao
	 * @param sql
	 * @return
	 */
	public static int queryCount(Dao dao, String sql) {
		Sql sql2 = Sqls.fetchLong("select count(1) FROM (" + sql + ")");
		dao.execute(sql2);
		return sql2.getInt();
	}

	/**
	 * 为某sql添加排序信息
	 * @param sql
	 * @param orderBy
	 */
	public static void addOrderBy(Sql sql, OrderBy orderBy, Entity<?> entity) {
		String sourceSql = sql.getSourceSql();
		if (!sourceSql.contains("$orderBy")) {
			sourceSql += " $orderBy";
			sql.setSourceSql(sourceSql);
		}
		sql.vars().set("orderBy", orderBy.toSql(entity));
	}

	/**
	 * 为某 cnd 添加排序信息
	 * @param cnd
	 * @param orderBy
	 * @param entity
	 */
	public static Condition addOrderBy(Condition cnd, OrderBy orderBy, Entity<?> entity) {
		if (null == orderBy) {
			return cnd;
		}
		if (null == cnd) {
			return orderBy;
		} else {
			String sql = Strings.trim(orderBy.toSql(entity));
			String sortColumn = sql.substring(sql.indexOf("ORDER BY") + 9);
			sortColumn = sortColumn.substring(0, sortColumn.indexOf(" "));
			if (sql.endsWith("DESC")) {
				((Cnd) cnd).desc(sortColumn);
			} else {
				((Cnd) cnd).asc(sortColumn);
			}
			return cnd;
		}
	}
}
