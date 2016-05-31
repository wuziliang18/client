package org.wuzl.client.es.jdbc.support;

import java.sql.Connection;

import com.alibaba.druid.pool.DruidDataSource;

/**
 * 数据源操作
 * 
 * @author wuzl
 *
 */
public class DataSourceUtil {
	private final static DruidDataSource dataSource;
	static {
		dataSource = new DruidDataSource();
		dataSource.setDriverClassName(ConfigService
				.getProperty("jdbc.driverClassName"));
		dataSource.setUsername(ConfigService.getProperty("jdbc.username"));
		dataSource.setPassword(ConfigService.getProperty("jdbc.password"));
		dataSource.setUrl(ConfigService.getProperty("jdbc.url"));
		dataSource.setInitialSize(ConfigService
				.getIntProperty("jdbc.druid.initialSize"));
		dataSource.setMinIdle(ConfigService
				.getIntProperty("jdbc.druid.minIdle"));
		dataSource.setMaxActive(ConfigService
				.getIntProperty("jdbc.druid.maxActive"));
		dataSource.setMaxWait(60000);
		dataSource.setTimeBetweenEvictionRunsMillis(60000);
		dataSource.setMinEvictableIdleTimeMillis(300000);
		dataSource.setValidationQuery("SELECT 'x'");
		dataSource.setTestWhileIdle(true);
		dataSource.setTestOnBorrow(false);
		dataSource.setTestOnReturn(false);
		dataSource.setPoolPreparedStatements(true);
		dataSource.setMaxPoolPreparedStatementPerConnectionSize(20);
	}

	public static DruidDataSource getDruidDataSource() {
		return dataSource;
	}

	public static Connection getConnection() {
		if (!dataSource.isEnable()) {
			throw new RuntimeException("数据库连接已经关闭");
		}
		try {
			return dataSource.getConnection();
		} catch (Exception e) {
			throw new RuntimeException("数据库连接获取失败:" + e.getMessage());
		}
	}

	public static void close() {
		dataSource.close();
	}

}
