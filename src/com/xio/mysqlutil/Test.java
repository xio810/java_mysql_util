package com.xio.mysqlutil;

import java.util.List;
import java.util.Map;

public class Test {
	public static void main(String[] args) {
		MysqlUtil.setDBInfo("localhost", "sbsst", "sbs123414", "mysqlutil");

		MysqlUtil.setDevMode(true);

		List<Map<String, Object>> articleListMap = MysqlUtil.selectRows(new SecSql().append("SELECT * FROM article"));
		System.out.println("articleListMap : " + articleListMap);

		Map<String, Object> articleMap = MysqlUtil
				.selectRow(new SecSql().append("SELECT * FROM article WHERE id = ?", 1));
		System.out.println("articleMap : " + articleMap);

		String title = MysqlUtil.selectRowStringValue(new SecSql().append("SELECT title FROM article WHERE id = ?", 1));
		System.out.println("title : " + title);

		int id = MysqlUtil.selectRowIntValue(new SecSql().append("SELECT id FROM article WHERE id = ?", 1));
		System.out.println("memberId : " + id);

		boolean idIs1 = MysqlUtil
				.selectRowBooleanValue(new SecSql().append("SELECT id = 1 FROM article WHERE id = ?", 1));
		System.out.println("id is 1 : " + idIs1);

		String newTitle = "새 제목";
		String newBody = "새 내용";

		SecSql sql = new SecSql().append("INSERT INTO article");
		sql.append("SET regDate = NOW()");
		sql.append(", updateDate = NOW()");
		sql.append(", title = ?", newTitle);
		sql.append(", body = ?", newBody);

		MysqlUtil.insert(sql);

		MysqlUtil.closeConnection();
	}
}