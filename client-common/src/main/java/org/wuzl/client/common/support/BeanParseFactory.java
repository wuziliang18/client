package org.wuzl.client.common.support;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@SuppressWarnings("unchecked")
public class BeanParseFactory {
	@SuppressWarnings("rawtypes")
	static final ConcurrentHashMap<String, BeanParse> BEAN_PARSE_MAP = new ConcurrentHashMap<String, BeanParse>();

	public static <T> BeanParse<T> getBeanParse(String type) {
		return BEAN_PARSE_MAP.get(type);
	}

	public static <T> BeanParse<T> addBeanParse(String type,
			BeanParse<T> beanParse) {
		BEAN_PARSE_MAP.putIfAbsent(type, beanParse);
		return BEAN_PARSE_MAP.get(type);
	}

	public void setBeanParseMap(
			@SuppressWarnings("rawtypes") Map<String, BeanParse> beanParseMap) {
		BeanParseFactory.BEAN_PARSE_MAP.putAll(beanParseMap);
	}
}
