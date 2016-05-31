package org.wuzl.client.es.jdbc.importdata;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.wuzl.client.es.jdbc.dao.UserDao;
import org.wuzl.client.es.jdbc.importdata.support.AbstractImportData;

import com.alibaba.fastjson.JSONObject;

public class ImportUserData extends AbstractImportData {
	private final static Logger log = Logger.getLogger(ImportUserData.class);
	final UserDao userDao = new UserDao();

	public ImportUserData() {
		super("dayima", "user");
	}

	@Override
	protected List<Map<String, Object>> doSearch(long searchBegin) {
		List<Map<String, Object>> rows = userDao.getUserListByIdAndTime(
				searchBegin, getBatchSize());
		for (Map<String, Object> map : rows) {
			String userExtraExtraInfo = String.valueOf(map
					.get("userExtraExtraInfo"));
			try {
				JSONObject extraJson = JSONObject
						.parseObject(userExtraExtraInfo);
				if (extraJson != null) {
					String sliming = extraJson.getString("sliming");
					String userStatus = extraJson.getString("user_status");
					if (sliming != null && sliming.length() > 0) {
						try {
							map.put("userSliming", Long.parseLong(sliming));
						} catch (Exception ex) {
							log.error("Sliming Invalid:" + sliming);
							map.put("userSliming", -1);
						}
					} else {
						map.put("userSliming", -1);
					}
					if (userStatus != null && userStatus.length() > 0) {
						try {
							map.put("userStatus", Long.parseLong(userStatus));
						} catch (Exception ex) {
							log.error("userStatus Invalid:" + userStatus);
							map.put("userStatus", -1);
						}
					} else {
						map.put("userStatus", -1);
					}
				} else {
					map.put("userSliming", -1);
					map.put("userStatus", -1);
				}
			} catch (Exception e) {
				log.error("json parse error:" + userExtraExtraInfo);
			}
		}
		return rows;
	}
}
