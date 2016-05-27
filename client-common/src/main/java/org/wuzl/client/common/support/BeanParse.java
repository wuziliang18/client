package org.wuzl.client.common.support;

import java.util.Map;

public interface BeanParse<Type> {
	public Type getBeanFromMap(Map<String, Object> map);
}
