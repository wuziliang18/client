package org.wuzl.client.es.interfaces;

import java.util.List;
import java.util.Map;
import org.elasticsearch.index.query.QueryBuilder;

public interface EsService<Type, ID> {
	/**
	 * 新增
	 * 
	 * @param id
	 * @param obj
	 * @return
	 */
	public ID insert(ID id, Type obj);

	/**
	 * 新增
	 * 
	 * @param obj
	 * @return
	 */
	public ID insert(Type obj);

	/**
	 * 根据id获取
	 * 
	 * @param id
	 * @return
	 */
	public Type getById(ID id);

	/**
	 * 修改
	 * 
	 * 
	 * @param id
	 * @param obj
	 */
	public void update(ID id, Type obj);

	/**
	 * 删除
	 * 
	 * @param id
	 */
	public void delete(ID id);

	/**
	 * 查询
	 * 
	 * @param queryBuilder
	 * @return
	 */
	public List<Map<String,Object>> search(QueryBuilder queryBuilder);
}
