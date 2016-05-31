package org.wuzl.client.es.jdbc;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.wuzl.client.es.EsServiceFactory;
import org.wuzl.client.es.jdbc.importdata.ImportUserData;
import org.wuzl.client.es.jdbc.support.ConfigService;
import org.wuzl.client.es.jdbc.support.DataSourceUtil;

public class Main {
	private final static Logger log = Logger.getLogger(Main.class);

	public static void main(String[] args) {
		log.info("开始加载jdbc配置");
		log.info(String.format("数据库连接状态：%s", DataSourceUtil
				.getDruidDataSource().isEnable()));
		log.info("开始加载es连接");
		initEsClient();
		long begin = System.currentTimeMillis() / 1000;
		ImportUserData importUserData = new ImportUserData();
		int count = importUserData.importData();
		long end = System.currentTimeMillis() / 1000;
		log.info(String.format("花费时间：%d秒,导入数据:%d", end - begin, count));
		log.info("导入完毕");
		close();
	}

	private static void close() {
		DataSourceUtil.close();
		EsServiceFactory.destory();
	}

	private static void initEsClient() {
		Map<String, String> esSettings = new HashMap<String, String>();
		esSettings.put("cluster.name", "dayima-search");
//		esSettings.put("max_bulk_actions", "10000");
//		esSettings.put("max_concurrent_bulk_requests", "50");
//		esSettings.put("max_bulk_volume", "10m");
//		esSettings.put("flush_interval", "5s");
//		esSettings.put("threadpool.bulk.type", "fixed");
//		esSettings.put("threadpool.bulk.size", "200");
//		esSettings.put("threadpool.bulk.queue_size", "500");
//		esSettings.put("threadpool.index.type", "fixed");
//		esSettings.put("threadpool.index.size", "50");
//		esSettings.put("threadpool.index.queue_size", "500");
//		esSettings.put("threadpool.search.type", "fixed");
//		esSettings.put("threadpool.search.size", "50");
//		esSettings.put("threadpool.search.queue_size", "500");
		EsServiceFactory.setSettings(esSettings);
		EsServiceFactory.setTransportAddress(ConfigService
				.getProperty("es.server.url"));
		EsServiceFactory.init();
	}
}
