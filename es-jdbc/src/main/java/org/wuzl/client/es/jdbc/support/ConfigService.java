package org.wuzl.client.es.jdbc.support;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

public class ConfigService {

	private final static Logger logger = Logger.getLogger(ConfigService.class);

	private final static Properties props = new Properties();

	static {
		InputStream is = ClassLoader
				.getSystemResourceAsStream("system.properties");
		try {
			props.load(is);
		} catch (IOException e) {
			logger.error("配置文件记载出错!", e);
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		is = ClassLoader.getSystemResourceAsStream("jdbc.properties");

		try {
			Properties jdbcProperties = new Properties();
			jdbcProperties.load(is);
			props.putAll(jdbcProperties);
		} catch (IOException e) {
			logger.error("配置文件记载出错!", e);
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static String getProperty(String key) {
		return props.getProperty(key);
	}

	public static String getProperty(String key, String defaultVal) {
		return props.getProperty(key, defaultVal);
	}

	public static int getIntProperty(String key) {
		String val = props.getProperty(key);
		try {
			return Integer.parseInt(val);
		} catch (NumberFormatException e) {
			logger.info("获取key出错 : " + key, e);
			return 0;
		}

	}

	public static int getIntProperty(String key, int defaultVal) {
		String val = props.getProperty(key);
		if (val == null) {
			return defaultVal;
		}
		try {
			return Integer.parseInt(val);
		} catch (NumberFormatException e) {
			return defaultVal;
		}
	}
}