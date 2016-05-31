package org.wuzl.client.es.jdbc.importdata.support;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.log4j.Logger;
import org.wuzl.client.es.EsServiceFactory;
import org.wuzl.client.es.interfaces.EsService;
import org.wuzl.client.es.jdbc.support.ConfigService;
import org.wuzl.client.es.jdbc.support.ExecutorsEx;

public abstract class AbstractImportData {
	private final static Logger log = Logger
			.getLogger(AbstractImportData.class);
	private final AtomicInteger count = new AtomicInteger(0);
	private final int batchSize = ConfigService.getIntProperty(
			"es.import.size", 100);
	private final AtomicLong begin = new AtomicLong(0 - batchSize);
	private volatile boolean isEnd = false;
	private final int threadCount = ConfigService.getIntProperty(
			"es.import.thread", 10);
	private final ExecutorService threadPool = ExecutorsEx
			.newFixedThreadPool(threadCount);
	private final String index;
	private final String type;
	@SuppressWarnings("rawtypes")
	private final EsService<Map> esService;

	public AbstractImportData(String index, String type) {
		this.index = index;
		this.type = type;
		this.esService = EsServiceFactory.getEsService(this.index, this.type,
				Map.class);
	}

	public int importData() {
		log.info(String.format("开始导入数据，index：%s，type：%s，线程数:%d,每次导入条数：%d",
				index, type, threadCount, batchSize));
		int searchCount = 0;
		while (!isEnd) {
			searchCount++;
			if ((searchCount % 50) == 0) {
				log.info(String.format("当前操作数:%s", begin.get()));
			}
			threadPool.submit(new Runnable() {
				@Override
				public void run() {
					searchAndImport();
				}
			});
		}
		log.info("开始关闭线程池");
		threadPool.shutdown();
		try {
			threadPool.awaitTermination(Long.MAX_VALUE, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
		}
		return count.get();
	}

	protected int searchAndImport() {
		long searchBegin = begin.addAndGet(batchSize);
		List<Map<String, Object>> rows = doSearch(searchBegin);
		if (rows == null || rows.size() == 0) {
			isEnd = true;
			return 0;
		}
		// 获取最后一个id 判断是否有主键断层如果有尝试重新配置beginid
		long lastId = Long.parseLong(String.valueOf(rows.get(rows.size() - 1)
				.get("_id")));
		if ((lastId - searchBegin) / batchSize > 10) {
			int count = 10;
			while (count-- >= 0) {
				long currentId = begin.get();
				if (lastId > currentId && (lastId - currentId) / batchSize > 5) {
					if (begin.compareAndSet(currentId, lastId)) {
						break;
					}
				}
			}
		}
		// 导入es
		bulk(rows);
		count.addAndGet(rows.size());
		return rows.size();
	}

	protected void bulk(List<Map<String, Object>> rows) {
		@SuppressWarnings("rawtypes")
		Map<String, Map> objs = new HashMap<String, Map>();
		for (Map<String, Object> map : rows) {
			objs.put(String.valueOf(map.remove("_id")), map);
		}
		for (int i = 0; i < 5; i++) {
			try {
				esService.bulk(objs);
				break;
			} catch (Exception e) {
				if (i == 4) {
					log.error(String.format("type[%s]出现5次错误,已经放弃同步,开始id是[%s]!",
							type, rows.get(0).get("_id")), e);
				} else {
					log.error(String.format("type[%s]出现错误,开始第%d次重试!", type, i),
							e);
				}
			}
		}
	}

	protected int getBatchSize() {
		return batchSize;
	}

	protected abstract List<Map<String, Object>> doSearch(long searchBegin);
}
