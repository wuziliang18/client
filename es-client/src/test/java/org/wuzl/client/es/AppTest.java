package org.wuzl.client.es;

import java.util.HashMap;
import java.util.Map;

import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.wuzl.client.es.interfaces.EsAdminService;
import org.wuzl.client.es.interfaces.EsService;

import com.alibaba.fastjson.JSON;

public class AppTest {
	public static void main(String[] args) {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
				"classpath:app-context.xml");
		EsServiceFactory factory = context.getBean(EsServiceFactory.class);
		// EsAdminService adminService = EsServiceFactory.getEsAdminService();
		// 新建
		// Map<String, String> settings=new HashMap<String, String>();
		// settings.put("index.number_of_shards", "3");
		// settings.put("index.number_of_replicas", "1");
		// adminService.createIndex("dayima", settings);
		// 删除
		// adminService.deleteIndex("dayima");
		// mapping
		// Map<String, Object> mapping=createMapping();
		// adminService.putMapping("dayima", "topic", mapping);
		EsService<Employee> esService = EsServiceFactory.getEsService("dayima",
				"topic", Employee.class);
//		Employee employee = new Employee();
//		employee.setFirstName("zhang");
//		employee.setLastName("san");
//		employee.setAge(25);
//		employee.setAbout("zhang is good");
//		employee.setInterests(new String[] { "book", "game" });
//
//		esService.insert("3", employee);
//		outJson(esService.getById("1"));
		outJson(esService.searchList(QueryBuilders.termQuery("lastName", "zl")));;

	}

	public static Map<String, Object> createMapping() {
		Map<String, Object> mapping = new HashMap<String, Object>();
		Map<String, Object> properties = new HashMap<String, Object>();
		Map<String, Object> fieldMap = null;
		fieldMap = new HashMap<String, Object>();

		fieldMap.put("type", "long");
		fieldMap.put("index", "not_analyzed");
		properties.put("id", fieldMap);
		fieldMap = new HashMap<String, Object>();
		fieldMap.put("type", "long");
		fieldMap.put("index", "not_analyzed");
		properties.put("topicCategoryId", fieldMap);
		fieldMap = new HashMap<String, Object>();
		fieldMap.put("type", "long");
		fieldMap.put("index", "not_analyzed");
		properties.put("uid", fieldMap);
		// fieldMap = new HashMap<String, Object>();
		// fieldMap.put("type", "string");
		// fieldMap.put("analyzer", "ik");
		// properties.put("title", fieldMap);
		// fieldMap = new HashMap<String, Object>();
		// fieldMap.put("type", "string");
		// fieldMap.put("analyzer", "ik");
		// properties.put("content", fieldMap);
		fieldMap = new HashMap<String, Object>();
		fieldMap.put("type", "long");
		fieldMap.put("index", "not_analyzed");
		properties.put("status", fieldMap);
		fieldMap = new HashMap<String, Object>();
		fieldMap.put("type", "long");
		fieldMap.put("index", "not_analyzed");
		properties.put("dateline", fieldMap);
		fieldMap = new HashMap<String, Object>();
		fieldMap.put("type", "string");
		fieldMap.put("index", "not_analyzed");
		properties.put("ip", fieldMap);
		fieldMap = new HashMap<String, Object>();
		fieldMap.put("type", "long");
		fieldMap.put("index", "not_analyzed");
		properties.put("replynum", fieldMap);
		fieldMap = new HashMap<String, Object>();
		fieldMap.put("type", "long");
		fieldMap.put("index", "not_analyzed");
		properties.put("lastreplytime", fieldMap);
		fieldMap = new HashMap<String, Object>();
		fieldMap.put("type", "long");
		fieldMap.put("index", "not_analyzed");
		properties.put("settop", fieldMap);
		fieldMap = new HashMap<String, Object>();
		fieldMap.put("type", "long");
		fieldMap.put("index", "not_analyzed");
		properties.put("lastoptime", fieldMap);
		fieldMap = new HashMap<String, Object>();
		fieldMap.put("type", "long");
		fieldMap.put("index", "not_analyzed");
		properties.put("lock", fieldMap);
		fieldMap = new HashMap<String, Object>();
		fieldMap.put("type", "long");
		fieldMap.put("index", "not_analyzed");
		properties.put("setbottom", fieldMap);
		fieldMap = new HashMap<String, Object>();
		fieldMap.put("type", "long");
		fieldMap.put("index", "not_analyzed");
		properties.put("flag", fieldMap);
		fieldMap = new HashMap<String, Object>();
		fieldMap.put("type", "long");
		fieldMap.put("index", "not_analyzed");
		properties.put("digest", fieldMap);
		fieldMap = new HashMap<String, Object>();
		fieldMap.put("type", "long");
		fieldMap.put("index", "not_analyzed");
		properties.put("createtime", fieldMap);
		fieldMap = new HashMap<String, Object>();
		fieldMap.put("type", "string");
		fieldMap.put("index", "not_analyzed");
		properties.put("lastpost", fieldMap);
		fieldMap = new HashMap<String, Object>();
		fieldMap.put("type", "long");
		fieldMap.put("index", "not_analyzed");
		properties.put("favnum", fieldMap);
		fieldMap = new HashMap<String, Object>();
		fieldMap.put("type", "long");
		fieldMap.put("index", "not_analyzed");
		properties.put("attachPictures", fieldMap);
		fieldMap = new HashMap<String, Object>();
		fieldMap.put("type", "long");
		fieldMap.put("index", "not_analyzed");
		properties.put("viewnum", fieldMap);
		fieldMap = new HashMap<String, Object>();
		fieldMap.put("type", "long");
		fieldMap.put("index", "not_analyzed");
		properties.put("settoptime", fieldMap);
		fieldMap = new HashMap<String, Object>();
		fieldMap.put("type", "long");
		fieldMap.put("index", "not_analyzed");
		properties.put("hidden", fieldMap);

		mapping.put("properties", properties);
		// // _id
		// Map<String, Object> idMap = new HashMap<String, Object>();
		// idMap.put("path", "id");
		// mapping.put("_id", idMap);
		return mapping;
	}

	public static void outJson(Object obj) {
		System.out.println(JSON.toJSON(obj));
	}
}
