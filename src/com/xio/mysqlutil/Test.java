package com.xio.mysqlutil;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Test {
	public static void main(String[] args) throws JsonMappingException, JsonProcessingException {
		// mysqlUtil 기본 테스트
		// mysqlUtilTest();

		// jackson 기본 테스트
		// jacksonTest1();

		// 리스트 파싱 테스트 1
		// jacksonListTest1();
		// 리스트 파싱 테스트 2
		// jacksonListTest2();
		// 리스트 파싱 테스트 3
		// jacksonListTest3();
		// 리스트 파싱 테스트 4
		// jacksonListTest4();

		mysqlUtilJacksonTest();
	}

	private static void mysqlUtilJacksonTest() {
		MysqlUtil.setDBInfo("localhost", "sbsst", "sbs123414", "mysqlutil");

		MysqlUtil.setDevMode(true);

		// select rows
		SecSql sql1 = new SecSql();
		sql1.append("SELECT * FROM article ORDER BY id DESC");

		List<Article> articleList = MysqlUtil.selectRows(sql1, Article.class);
		System.out.println("articleList : " + articleList);

		// select row
		SecSql sql2 = new SecSql();
		sql2.append("SELECT * FROM article WHERE id = ?", 1);
		Article article = MysqlUtil.selectRow(sql2, Article.class);
		System.out.println("article : " + article);
	}

	private static void jacksonListTest4() throws JsonMappingException, JsonProcessingException {
		ObjectMapper om = new ObjectMapper();

		// jsonString to List<Map<String, Object>>
		// List<Map<String, Object>> to List<Article>
		String jsonStr1 = "[{\"id\":2,\"title\":\"제목2\",\"body\":\"내용2\",\"memberId\":2}, {\"id\":1,\"title\":\"제목1\",\"body\":\"내용1\",\"memberId\":1}]";

		List<Map<String, Object>> list1 = om.readValue(jsonStr1, new TypeReference<List<Map<String, Object>>>() {
		});

		List<Article> list2 = om.convertValue(list1, new TypeReference<List<Article>>() {
		});

		System.out.println(list2);
	}

	private static void jacksonListTest3() throws JsonMappingException, JsonProcessingException {
		ObjectMapper om = new ObjectMapper();

		// jsonString to List<Article>
		String jsonStr1 = "[{\"id\":2,\"title\":\"제목2\",\"body\":\"내용2\",\"memberId\":2}, {\"id\":1,\"title\":\"제목1\",\"body\":\"내용1\",\"memberId\":1}]";

		List<Article> list1 = om.readValue(jsonStr1, new TypeReference<List<Article>>() {
		});
		System.out.println(list1);
		System.out.println(list1.get(0));
		System.out.println(list1.get(0).id);
	}

	private static void jacksonListTest2() throws JsonMappingException, JsonProcessingException {
		ObjectMapper om = new ObjectMapper();

		// jsonString to List<Map<String, Object>>
		String jsonStr1 = "[{\"id\":2,\"title\":\"제목2\",\"body\":\"내용2\",\"memberId\":2}, {\"id\":1,\"title\":\"제목1\",\"body\":\"내용1\",\"memberId\":1}]";

		List<Map<String, Object>> list1 = om.readValue(jsonStr1, new TypeReference<List<Map<String, Object>>>() {
		});
		System.out.println(list1);
		System.out.println(list1.get(0));
		System.out.println(list1.get(0).get("id"));
	}

	private static void jacksonListTest1() throws JsonMappingException, JsonProcessingException {
		ObjectMapper om = new ObjectMapper();

		// jsonString to List
		String jsonStr1 = "[{\"id\":2,\"title\":\"제목2\",\"body\":\"내용2\",\"memberId\":2}, {\"id\":1,\"title\":\"제목1\",\"body\":\"내용1\",\"memberId\":1}]";

		List list1 = om.readValue(jsonStr1, List.class);
		System.out.println(list1);
		System.out.println(list1.get(0));
		System.out.println(((Map) list1.get(0)).get("id"));
	}

	private static void jacksonTest1() throws JsonMappingException, JsonProcessingException {
		ObjectMapper om = new ObjectMapper();

		// jsonString to Map
		// {"id":1, "title":"제목"}
		String jsonStr1 = "{\"id\":1,\"title\":\"제목\",\"body\":\"내용\",\"memberId\":1}";
		Map map1 = om.readValue(jsonStr1, Map.class);
		System.out.println("map1.get(\"id\") : " + map1.get("id"));
		System.out.println("(int)map1.get(\"id\") + 1 : " + ((int) map1.get("id") + 1));
		System.out.println("map1.get(\"title\") : " + map1.get("title"));

		// jsonString to Article
		// {"id":1, "title":"제목"}
		Article article1 = om.readValue(jsonStr1, Article.class);
		System.out.println(article1);

		// map to Article
		Article articl2 = om.convertValue(map1, Article.class);
		System.out.println(articl2);
	}

	private static void mysqlUtilTest() {
		MysqlUtil.setDBInfo("localhost", "sbsst", "sbs123414", "mysqlutil");

		MysqlUtil.setDevMode(true);

		// select rows
		SecSql sql1 = new SecSql();
		sql1.append("SELECT * FROM article ORDER BY id DESC");

		List<Map<String, Object>> articleMapList = MysqlUtil.selectRows(sql1);
		System.out.println("articleMapList : " + articleMapList);

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

		SecSql sql6 = new SecSql().append("INSERT INTO article");
		sql6.append("SET regDate = NOW()");
		sql6.append(", updateDate = NOW()");
		sql6.append(", title = ?", newTitle);
		sql6.append(", body = ?", newBody);
		int newArticleId = MysqlUtil.insert(sql6);
		System.out.println("newArticleId : " + newArticleId);

		// update
		SecSql sql7 = new SecSql();
		sql7.append("UPDATE article");
		sql7.append("SET updateDate = NOW()");
		sql7.append(", title = CONCAT(title, '_NEW')");
		sql7.append("WHERE id = ?", 3);
		MysqlUtil.update(sql7);

		SecSql sql8 = new SecSql();
		sql8.append("SELECT title FROM article WHERE id = ?", 3);
		String article3__title = MysqlUtil.selectRowStringValue(sql8);
		System.out.println("article3__title : " + article3__title);

		// delete
		SecSql sql9 = new SecSql();
		sql9.append("DELETE FROM article WHERE id = ?", newArticleId);
		MysqlUtil.delete(sql9);

		SecSql sql10 = new SecSql();
		sql10.append("SELECT COUNT(*) AS cnt FROM article WHERE id = ?", newArticleId);
		int count = MysqlUtil.selectRowIntValue(sql10);
		System.out.println("count : " + count);

		MysqlUtil.closeConnection();
	}
}

@JsonIgnoreProperties(ignoreUnknown = true)
class Article {
	public int id;
	public String title;
	public String body;

	@Override
	public String toString() {
		return "Article [id=" + id + ", title=" + title + ", body=" + body + "]";
	}
}