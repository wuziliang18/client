package org.wuzl.client.es;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.wuzl.client.es.interfaces.EsAdminService;
import org.wuzl.client.es.interfaces.EsService;

import com.alibaba.fastjson.JSON;
/**
 * http://192.168.127.222:9200/dayima/_mapping/topic_category?pretty
 * @author wuzl
 *
 */
public class EsGroupMain {
	public static void main(String[] args) {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
				"classpath:app-context.xml");
		EsServiceFactory factory = context.getBean(EsServiceFactory.class);
		 EsAdminService adminService = EsServiceFactory.getEsAdminService();
		// mapping
		 Map<String, Object> mapping=createMapping();
		 adminService.putMapping("dayima", "topic_category", mapping);
		EsService<Employee> esService = EsServiceFactory.getEsService("dayima",
				"topic_category", Employee.class);
		
		outJson(esService.getByIdToMap("259518"));

	}

	public static Map<String, Object> createMapping() {
		Map<String, Object> mapping = new HashMap<String, Object>();
		Map<String, Object> properties = new HashMap<String, Object>();
		Map<String, Object> fieldMap = null;
		
		fieldMap = new HashMap<String, Object>();
		fieldMap.put("type", "long");
		fieldMap.put("index", "not_analyzed");
		properties.put("topicCategoryId", fieldMap);
		fieldMap = new HashMap<String, Object>();
		fieldMap.put("type", "string");
		fieldMap.put("analyzer", "ik");
		properties.put("topicCategoryTitle", fieldMap);
		fieldMap = new HashMap<String, Object>();
		fieldMap.put("type", "long");
		fieldMap.put("index", "not_analyzed");
		properties.put("topicCategoryNum", fieldMap);
		fieldMap = new HashMap<String, Object>();
		fieldMap.put("type", "long");
		fieldMap.put("index", "not_analyzed");
		properties.put("topicCategoryDisplayorder", fieldMap);
		fieldMap = new HashMap<String, Object>();
		fieldMap.put("type", "long");
		fieldMap.put("index", "not_analyzed");
		properties.put("topicCategoryStatus", fieldMap);
		fieldMap = new HashMap<String, Object>();
		fieldMap.put("type", "long");
		fieldMap.put("index", "not_analyzed");
		properties.put("topicCategoryDateline", fieldMap);
		fieldMap = new HashMap<String, Object>();
		fieldMap.put("type", "string");
		fieldMap.put("index", "not_analyzed");
		properties.put("topicCategoryPic", fieldMap);
		fieldMap = new HashMap<String, Object>();
		fieldMap.put("type", "long");
		fieldMap.put("index", "not_analyzed");
		properties.put("topicCategoryLastUpload", fieldMap);
		fieldMap = new HashMap<String, Object>();
		fieldMap.put("type", "long");
		fieldMap.put("index", "not_analyzed");
		properties.put("topicCategoryCatId", fieldMap);
		fieldMap = new HashMap<String, Object>();
		fieldMap.put("type", "long");
		fieldMap.put("index", "not_analyzed");
		properties.put("topicCategoryUid", fieldMap);
		fieldMap = new HashMap<String, Object>();
		fieldMap.put("type", "long");
		fieldMap.put("index", "not_analyzed");
		properties.put("topicCategoryMembernum", fieldMap);
		fieldMap = new HashMap<String, Object>();
		fieldMap.put("type", "string");
		fieldMap.put("analyzer", "ik");
		properties.put("topicCategoryDescs", fieldMap);
		fieldMap = new HashMap<String, Object>();
		fieldMap.put("type", "long");
		fieldMap.put("index", "not_analyzed");
		properties.put("topicCategoryType", fieldMap);
		fieldMap = new HashMap<String, Object>();
		fieldMap.put("type", "long");
		fieldMap.put("index", "not_analyzed");
		properties.put("topicCategoryTeamType", fieldMap);
		fieldMap = new HashMap<String, Object>();
		fieldMap.put("type", "string");
		fieldMap.put("index", "not_analyzed");
		properties.put("topicCategoryVerfitycode", fieldMap);
		fieldMap = new HashMap<String, Object>();
		fieldMap.put("type", "long");
		fieldMap.put("index", "not_analyzed");
		properties.put("topicCategoryVerfitySmsTime", fieldMap);
		fieldMap = new HashMap<String, Object>();
		fieldMap.put("type", "long");
		fieldMap.put("index", "not_analyzed");
		properties.put("topicCategoryVerfityTime", fieldMap);
		fieldMap = new HashMap<String, Object>();
		fieldMap.put("type", "string");
		fieldMap.put("index", "not_analyzed");
		properties.put("topicCategoryMobile", fieldMap);
		fieldMap = new HashMap<String, Object>();
		fieldMap.put("type", "long");
		fieldMap.put("index", "not_analyzed");
		properties.put("topicCategoryCalval", fieldMap);
		fieldMap = new HashMap<String, Object>();
		fieldMap.put("type", "long");
		fieldMap.put("index", "not_analyzed");
		properties.put("topicCategoryManual", fieldMap);
		fieldMap = new HashMap<String, Object>();
		fieldMap.put("type", "long");
		fieldMap.put("index", "not_analyzed");
		properties.put("topicCategoryReplynum", fieldMap);
		fieldMap = new HashMap<String, Object>();
		fieldMap.put("type", "string");
		fieldMap.put("index", "not_analyzed");
		properties.put("topicCategoryLink", fieldMap);
		// Map<String, Object> idMap = new HashMap<String, Object>();
		// idMap.put("path", "id");
		// mapping.put("_id", idMap);
		mapping.put("properties", properties);
		return mapping;
	}

	public static void outJson(Object obj) {
		System.out.println(JSON.toJSON(obj));
	}
}
