package com.xio.mysqlutil;

import java.util.List;
import java.util.Map;

public class Test {
	public static void main(String[] args) {
		MysqlUtil.setDBInfo("localhost", "sbsst", "sbs123414", "mysqlutil");

		MysqlUtil.setDevMode(true);
		// select rows
		SecSql sql1 = new SecSql();
		sql1.append("SELECT * FROM article order by id desc");
		List<Map<String, Object>> articleListMap = MysqlUtil.selectRows(sql1);
		System.out.println("articleListMap : " + articleListMap);

		// select row
		SecSql sql2 = new SecSql();
		sql2.append("SELECT * FROM article WHERE id = ?", 1);
		Map<String, Object> articleMap = MysqlUtil.selectRow(sql2);
		System.out.println("articleMap : " + articleMap);

		// select row string value
		SecSql sql3 = new SecSql();
		sql3.append("SELECT title FROM article WHERE id = ?", 1);
		String title = MysqlUtil.selectRowStringValue(sql3);
		System.out.println("title : " + title);

		// select row int value
		SecSql sql4 = new SecSql();
		sql4.append("SELECT id FROM article WHERE id = ?", 1);
		int id = MysqlUtil.selectRowIntValue(sql4);
		System.out.println("memberId : " + id);

		// select row boolean value
		SecSql sql5 = new SecSql();
		sql5.append("SELECT id = 1 FROM article WHERE id = ?", 1);
		boolean idIs1 = MysqlUtil.selectRowBooleanValue(sql5);
		System.out.println("id is 1 : " + idIs1);

		// insert
		String newTitle = "새 제목";
		String newBody = "새 내용";

		SecSql sql6 = new SecSql();
		sql6.append("INSERT INTO article");
		sql6.append("SET regDate = NOW()");
		sql6.append(", updateDate = NOW()");
		sql6.append(", title = ?", newTitle);
		sql6.append(", body = ?", newBody);
		int newArticleId = MysqlUtil.insert(sql6);
		System.out.println("newArticleId" + newArticleId);

		// update
		SecSql sql7 = new SecSql();
		sql7.append("update article");
		sql7.append("set updateDate = now()");
		sql7.append(", title = CONCAT(title, '_NEW')");
		sql7.append("WHERE id = ?", 3);
		MysqlUtil.update(sql7);

		// select title id 3
		SecSql sql8 = new SecSql();
		sql8.append("select title from article where id = ?", 3);
		String article3__title = MysqlUtil.selectRowStringValue(sql8);
		System.out.println("article3__title : " + article3__title);

		// delete
		SecSql sql9 = new SecSql();
		sql9.append("delete from article where id = ?", newArticleId);
		MysqlUtil.delete(sql9);

		// count
		SecSql sql10 = new SecSql();
		sql10.append("select count(*) as cnt from article where id = ?", newArticleId);
		int count = MysqlUtil.selectRowIntValue(sql10);
		System.out.println("count : " + count);

		MysqlUtil.closeConnection();
	}
}