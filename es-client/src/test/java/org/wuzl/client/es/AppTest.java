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
		 EsAdminService adminService = EsServiceFactory.getEsAdminService();
		// 新建
		 Map<String, String> settings=new HashMap<String, String>();
		 settings.put("index.number_of_shards", "3");
		 settings.put("index.number_of_replicas", "1");
		 adminService.createIndex("dayima", settings);
		// 删除
		// adminService.deleteIndex("dayima");
		// mapping
		 Map<String, Object> mapping=createMapping();
		 adminService.putMapping("dayima", "topic", mapping);
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
		outJson(esService.getByIdToMap("8322286"));
//		outJson(esService.searchList(QueryBuilders.termQuery("lastName", "zl")));;

	}

	public static Map<String, Object> createMapping() {
		Map<String, Object> mapping = new HashMap<String, Object>();
		Map<String, Object> properties = new HashMap<String, Object>();
		Map<String, Object> fieldMap = null;
		
		fieldMap = new HashMap<String, Object>();
		fieldMap.put("type", "long");
		fieldMap.put("index", "not_analyzed");
		properties.put("topicId", fieldMap);
		fieldMap = new HashMap<String, Object>();
		fieldMap.put("type", "long");
		fieldMap.put("index", "not_analyzed");
		properties.put("topicTopicCategoryId", fieldMap);
		fieldMap = new HashMap<String, Object>();
		fieldMap.put("type", "long");
		fieldMap.put("index", "not_analyzed");
		properties.put("topicUid", fieldMap);
		fieldMap = new HashMap<String, Object>();
		fieldMap.put("type", "string");
		fieldMap.put("analyzer", "ik");
		properties.put("topicTitle", fieldMap);
		fieldMap = new HashMap<String, Object>();
		fieldMap.put("type", "string");
		fieldMap.put("analyzer", "ik");
		properties.put("topicContent", fieldMap);
		fieldMap = new HashMap<String, Object>();
		fieldMap.put("type", "long");
		fieldMap.put("index", "not_analyzed");
		properties.put("topicStatus", fieldMap);
		fieldMap = new HashMap<String, Object>();
		fieldMap.put("type", "long");
		fieldMap.put("index", "not_analyzed");
		properties.put("topicDateline", fieldMap);
		fieldMap = new HashMap<String, Object>();
		fieldMap.put("type", "string");
		fieldMap.put("index", "not_analyzed");
		properties.put("topicIp", fieldMap);
		fieldMap = new HashMap<String, Object>();
		fieldMap.put("type", "long");
		fieldMap.put("index", "not_analyzed");
		properties.put("topicReplynum", fieldMap);
		fieldMap = new HashMap<String, Object>();
		fieldMap.put("type", "long");
		fieldMap.put("index", "not_analyzed");
		properties.put("topicLastreplytime", fieldMap);
		fieldMap = new HashMap<String, Object>();
		fieldMap.put("type", "long");
		fieldMap.put("index", "not_analyzed");
		properties.put("topicSettop", fieldMap);
		fieldMap = new HashMap<String, Object>();
		fieldMap.put("type", "long");
		fieldMap.put("index", "not_analyzed");
		properties.put("topicLastoptime", fieldMap);
		fieldMap = new HashMap<String, Object>();
		fieldMap.put("type", "long");
		fieldMap.put("index", "not_analyzed");
		properties.put("topicLock", fieldMap);
		fieldMap = new HashMap<String, Object>();
		fieldMap.put("type", "long");
		fieldMap.put("index", "not_analyzed");
		properties.put("topicSetbottom", fieldMap);
		fieldMap = new HashMap<String, Object>();
		fieldMap.put("type", "long");
		fieldMap.put("index", "not_analyzed");
		properties.put("topicFlag", fieldMap);
		fieldMap = new HashMap<String, Object>();
		fieldMap.put("type", "long");
		fieldMap.put("index", "not_analyzed");
		properties.put("topicDigest", fieldMap);
		fieldMap = new HashMap<String, Object>();
		fieldMap.put("type", "long");
		fieldMap.put("index", "not_analyzed");
		properties.put("topicCreatetime", fieldMap);
		fieldMap = new HashMap<String, Object>();
		fieldMap.put("type", "string");
		fieldMap.put("index", "not_analyzed");
		properties.put("topicLastpost", fieldMap);
		fieldMap = new HashMap<String, Object>();
		fieldMap.put("type", "long");
		fieldMap.put("index", "not_analyzed");
		properties.put("topicFavnum", fieldMap);
		fieldMap = new HashMap<String, Object>();
		fieldMap.put("type", "long");
		fieldMap.put("index", "not_analyzed");
		properties.put("topicAttachPictures", fieldMap);
		fieldMap = new HashMap<String, Object>();
		fieldMap.put("type", "long");
		fieldMap.put("index", "not_analyzed");
		properties.put("topicViewnum", fieldMap);
		fieldMap = new HashMap<String, Object>();
		fieldMap.put("type", "long");
		fieldMap.put("index", "not_analyzed");
		properties.put("topicSettoptime", fieldMap);
		fieldMap = new HashMap<String, Object>();
		fieldMap.put("type", "long");
		fieldMap.put("index", "not_analyzed");
		properties.put("topicHidden", fieldMap);
		fieldMap = new HashMap<String, Object>();
		fieldMap.put("type", "string");
		fieldMap.put("index", "not_analyzed");
		properties.put("topicTopicCategoryTitle", fieldMap);


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
