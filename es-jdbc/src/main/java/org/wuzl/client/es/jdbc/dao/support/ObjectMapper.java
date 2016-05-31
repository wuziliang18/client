package org.wuzl.client.es.jdbc.dao.support;

import java.sql.ResultSet;
/**
 * 数据库与对象关系转化
 * @author wuzl
 *
 * @param <T>
 */
public interface ObjectMapper<T> {
	/**
	 * 数据库数据转成对象
	 * @param rs
	 * @return
	 */
	public T mapping(ResultSet rs);
	
}
