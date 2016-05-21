package org.wuzl.client.es.interfaces;

import java.util.Map;

public interface EsAdminService {
	public void createIndex(String index, Map<String, String> settings);

	public void deleteIndex(String... indexs);

	public void putMapping(String index, String type,
			Map<String, Object> mapping);

	public void putMappingString(String index, String type, String mappingStr);
}
