package org.wuzl.client.es.jdbc.dao.support;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

public class BaseMapMapper implements ObjectMapper<Map<String, Object>> {
	private final static Logger log = Logger.getLogger(BaseMapMapper.class);

	@Override
	public Map<String, Object> mapping(ResultSet rs) {
		Map<String, Object> map = new HashMap<String, Object>();

		try {
			ResultSetMetaData metadata = rs.getMetaData();
			int columns = metadata.getColumnCount();
			for (int i = 1; i <= columns; i++) {
				String name = metadata.getColumnLabel(i);
				int type = metadata.getColumnType(i);
				if (type == Types.BIGINT) {
					map.put(name, rs.getLong(name));
				} else if (type == Types.INTEGER || type == Types.TINYINT) {
					map.put(name, rs.getInt(name));
				} else if (type == Types.CHAR || type == Types.VARCHAR) {
					map.put(name, rs.getString(name));
				} else {// 都当做string
					map.put(name, rs.getString(name));
				}
			}
		} catch (SQLException e) {
			log.error("UserMapper  : Get columns error!", e);
		}
		return map;
	}
}
