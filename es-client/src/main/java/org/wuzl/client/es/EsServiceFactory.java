package org.wuzl.client.es;

import java.net.InetSocketAddress;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.wuzl.client.es.interfaces.EsAdminService;

import com.alibaba.fastjson.JSON;

public class EsServiceFactory {
	private static final Logger log = Logger.getLogger(EsServiceFactory.class);
	private static Map<String, String> settings;// 连接配置
	private static List<String> transportAddressList;// 服务端列表 格式ip:port
	private static volatile Client client;
	private static final EsAdminService esAdminService = new EsAdminServiceImpl();

	public static void setSettings(Map<String, String> settings) {
		EsServiceFactory.settings = settings;
	}

	public static void setTransportAddressList(List<String> transportAddressList) {
		EsServiceFactory.transportAddressList = transportAddressList;
	}

	/**
	 * 初始化 只需加载一次
	 */
	public static void init() {
		log.info("esclient开始加载");
		if (client != null) {
			throw new RuntimeException("已经加载过了");
		}
		if (transportAddressList == null || transportAddressList.isEmpty()) {
			throw new RuntimeException("没有配置服务列表");
		}
		if (settings == null || settings.isEmpty()) {
			throw new RuntimeException("没有配置连接参数");
		}
		Settings esSettings = Settings.settingsBuilder().put(settings).build();
		TransportClient transportClient = TransportClient.builder()
				.settings(esSettings).build();
		for (String address : transportAddressList) {
			String[] array = address.split(":");
			if (array.length != 2) {
				throw new RuntimeException(String.format(
						"服务地址[%s]配置不正确，正确格式为:ip:port", address));
			}
			transportClient
					.addTransportAddress(new InetSocketTransportAddress(
							new InetSocketAddress(array[0], Integer
									.parseInt(array[1]))));
		}
		client = transportClient;
		log.info("esclient加载完毕");
	}

	public void destory() {
		if (client != null) {
			client.close();
			client = null;
		}
	}

	/**
	 * 获取EsAdminService
	 * 
	 * @return
	 */
	public static EsAdminService getEsAdminService() {
		return esAdminService;
	}

	static class EsAdminServiceImpl implements EsAdminService {

		@Override
		public void createIndex(String index, Map<String, String> settings) {
			client.admin().indices().prepareCreate(index)
					.setSettings(Settings.builder().put(settings)).get();
		}

		@Override
		public void deleteIndex(String... indexs) {
			if (indexs.length == 0) {
				throw new RuntimeException("请指定要删除的index");
			}
			client.admin().indices().prepareDelete(indexs).get();
		}

		@Override
		public void putMapping(String index, String type,
				Map<String, Object> mapping) {
			this.putMappingString(index, type, JSON.toJSONString(mapping));
		}

		@Override
		public void putMappingString(String index, String type,
				String mappingStr) {
			client.admin().indices().preparePutMapping(index).setType(type)
					.setSource(mappingStr).get();
		}
	}
}
