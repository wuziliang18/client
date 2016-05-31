package org.wuzl.client.es.jdbc.dao.support;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.wuzl.client.es.jdbc.support.DataSourceUtil;

public class BaseDao {
	private final static Logger log = Logger.getLogger(BaseDao.class);

	public BaseDao() {
	}

	public Connection getConnection() {
		return DataSourceUtil.getConnection();
	}

	/**
	 * 查询列表
	 * 
	 * @param sql
	 * @param params
	 * @param mapper
	 * @return
	 */
	public <T> List<T> executeQuery(String sql, Object[] params,
			ObjectMapper<T> mapper) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<T> list = new ArrayList<T>();
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			if (params != null) {
				for (int i = 0; i < params.length; i++) {
					if (params[i] == null) {
						params[i] = "";
					}
					pstmt.setObject(i + 1, params[i]);
				}
			}
			rs = pstmt.executeQuery();
			while (rs.next()) {
				T obj = mapper.mapping(rs);
				list.add(obj);
			}

		} catch (Exception e) {
			log.error("Execute SQL " + sql + "error:", e);
		}

		try {
			if (rs != null)
				rs.close();
		} catch (Exception e) {
			log.error("Close Resultset error:", e);
		}
		try {
			if (pstmt != null)
				pstmt.close();
		} catch (Exception e) {
			log.error("Close statement error:", e);
		}
		try {
			if (conn != null)
				conn.close();
		} catch (Exception e) {
			log.error("Close connection error:", e);
		}
		return list;
	}

	/**
	 * 执行单条sql 增删改
	 * 
	 * @param sql
	 * @param params
	 */
	public int extcuteUpdate(String sql, Object[] params) {
		int resultCount = 0;
		PreparedStatement pstmt = null;
		Connection conn = null;
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			if (params != null) {
				for (int i = 0; i < params.length; i++) {
					if (params[i] == null) {
						params[i] = "";
					}
					pstmt.setObject(i + 1, params[i]);
				}
			}
			resultCount = pstmt.executeUpdate();
		} catch (Exception e) {
			log.error("Execute SQL " + sql + "error:", e);
		}

		try {
			if (pstmt != null)
				pstmt.close();
		} catch (Exception e) {
			log.error("Close statement error:", e);
		}
		try {
			if (conn != null)
				conn.close();
		} catch (Exception e) {
			log.error("Close connection error:", e);
		}
		return resultCount;
	}

	/**
	 * 批量执行一条sql 增删改
	 * 
	 * @param sql
	 * @param params
	 */
	public void extcuteUpdate(String sql, Object[][] params) {
		PreparedStatement pstmt = null;
		Connection conn = null;
		try {
			conn = getConnection();
			conn.setAutoCommit(false);
			pstmt = conn.prepareStatement(sql);
			if (params != null) {
				for (int i = 0; i < params.length; i++) {
					Object[] param = params[i];
					for (int j = 0; j < param.length; j++) {
						if (param[j] == null) {
							param[j] = "";
						}
						pstmt.setObject(j + 1, param[j]);
					}
					pstmt.addBatch();
				}
			}
			pstmt.executeBatch();
			conn.commit();
		} catch (Exception e) {
			log.error("Execute SQL " + sql + "error:", e);
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
			} catch (Exception e) {
				log.error("Close statement error:", e);
			}
			try {
				if (conn != null)
					conn.close();
			} catch (Exception e) {
				log.error("Close connection error:", e);
			}
		}
	}

	/**
	 * 查询一条记录
	 * 
	 * @param sql
	 * @param params
	 * @param mapper
	 * @return
	 */
	public <T> T executeQueryOne(String sql, Object[] params,
			ObjectMapper<T> mapper) {
		List<T> rows = this.executeQuery(sql, params, mapper);
		if (rows.size() > 0) {
			return rows.get(0);
		}
		return null;
	}

}
