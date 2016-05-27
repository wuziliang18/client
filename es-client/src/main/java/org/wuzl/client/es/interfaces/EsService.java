package org.wuzl.client.es.interfaces;

import java.util.List;
import java.util.Map;

import org.elasticsearch.action.search.SearchRequestBuilder;

public interface EsService<Type> {
	/**
	 * 新增
	 * 
	 * @param id
	 * @param obj
	 * @return
	 */
	public String insert(String id, Type obj);

	/**
	 * 新增
	 * 
	 * @param obj
	 * @return
	 */
	public String insert(Type obj);

	/**
	 * 批量操作 key是主键 value是对象
	 * 
	 * @param objs
	 */
	public void bulk(Map<String, Type> objs);

	/**
	 * 根据id获取
	 * 
	 * @param id
	 * @return
	 */
	public Type getById(String id);

	/**
	 * 根据id获取 map
	 * 
	 * @param id
	 * @return
	 */
	public Map<String, Object> getByIdToMap(String id);

	/**
	 * 修改
	 * 
	 * 
	 * @param id
	 * @param obj
	 */
	public void update(String id, Type obj);

	/**
	 * 删除
	 * 
	 * @param id
	 */
	public void delete(String id);

	/**
	 * 查询结果为map
	 * 
	 * @param queryBuilder
	 * @return
	 */
	public List<Type> searchList(SearchRequestBuilder searchRequestBuilder);

	/**
	 * 查询
	 * 
	 * @param queryBuilder
	 * @return
	 */
	public List<Map<String, Object>> searchMapList(
			SearchRequestBuilder searchRequestBuilder);

	/**
	 * 获取查询构造
	 * 
	 * @return
	 */
	public SearchRequestBuilder getSearchRequestBuilder();

}
