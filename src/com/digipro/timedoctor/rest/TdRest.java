package com.digipro.timedoctor.rest;

import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.digipro.timedoctor.constant.Property;
import com.digipro.timedoctor.dao.model.ChannelIntegrationAuth;
import com.digipro.timedoctor.rest.ro.td.Item;
import com.digipro.timedoctor.rest.ro.td.Task;
import com.digipro.timedoctor.rest.ro.td.TaskRo;
import com.digipro.timedoctor.rest.ro.td.WorklogRo;
import com.digipro.timedoctor.util.GsonUtil;

public class TdRest {
	private Properties properties;
	private static final Log log = LogFactory.getLog(TdRest.class);
	private static final int LIMIT = 500;

	public TdRest(Properties properties) {
		this.properties = properties;
	}

	public List<Task> getTasks(ChannelIntegrationAuth channelInt, List<Item> itemList) {
		// Get unique users from worklogs
		Set<Long> userSet = new HashSet<>();
		for (Item item : itemList)
			userSet.add(Long.parseLong(item.getUserId()));

		List<Task> taskList = new ArrayList<>();
		String endpoint = properties.getProperty(Property.TD_API_URL) + "/v1.1/companies/"
				+ channelInt.getConfig().getTdCompanyId() + "/users/";

		for (Long userId : userSet) {
			TaskRo ro = GsonUtil.gson.fromJson(makeGetRequest(channelInt, endpoint + userId + "/tasks", 0, LIMIT),
					TaskRo.class);
			if (!CollectionUtils.isEmpty(ro.getTasks())) {
				taskList.addAll(ro.getTasks());

				int offset = 0;
				while (ro.getTasks().size() == LIMIT) {
					offset += LIMIT + 1;
					ro = GsonUtil.gson.fromJson(makeGetRequest(channelInt, endpoint + userId + "/tasks", offset, LIMIT),
							TaskRo.class);

					if (ro == null || CollectionUtils.isEmpty(ro.getTasks()))
						continue;

					taskList.addAll(ro.getTasks());
				}
			}

			// TEST: One User
			// break;
		}

		// Now move TS link to task level instead of worklog level
		Map<Long, Item> itemMap = new HashMap<>();
		for (Item item : itemList)
			itemMap.put(Long.valueOf(item.getTaskId()), item);

		for (Task task : taskList) {
			if (task.getTaskLink() == null) {
				if (itemMap.containsKey(task.getTaskId()))
					task.setTaskLink(itemMap.get(task.getTaskId()).getTaskUrl());
			}
		}
		return taskList;
	}

	/**
	 * TODO: Implement a channel import table that tracks last import date in case
	 * the process doesn't run for some reason
	 *
	 * @param companyId
	 * @return
	 */
	public List<Item> getWorkLogs(ChannelIntegrationAuth channelInt) {
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		String date = format.format(cal.getTime());
		String endpoint = properties.getProperty(Property.TD_API_URL) + "/v1.1/companies/"
				+ channelInt.getConfig().getTdCompanyId() + "/worklogs?start_date=" + date + "&end_date=" + date;

		List<Item> itemList = new ArrayList<>();

		WorklogRo ro = GsonUtil.gson.fromJson(makeGetRequest(channelInt, endpoint, 0, LIMIT), WorklogRo.class);

		if (!CollectionUtils.isEmpty(ro.getWorklogs().getItems())) {
			itemList.addAll(ro.getWorklogs().getItems());

			int offset = 0;
			while (ro.getWorklogs().getCount() == LIMIT) {
				offset += LIMIT + 1;
				ro = GsonUtil.gson.fromJson(makeGetRequest(channelInt, endpoint, offset, LIMIT), WorklogRo.class);
				if (ro == null || CollectionUtils.isEmpty(ro.getWorklogs().getItems()))
					continue;

				itemList.addAll(ro.getWorklogs().getItems());
			}
		}

		/**
		 * TODO: This is a Hack to fix TD url to the correct Ticket URL. If we start
		 * using this software for other clients and other project systems (i.e. Jira)
		 * we'll need to spend more time around requirements for this
		 * 
		 * Convert https://digipromedia.visualstudio.com/_apis/wit/workItems/6785 to
		 * https://digipromedia.visualstudio.com/_workitems/edit/9304
		 */
		for (Item item : itemList) {
			if (item.getTaskUrl() != null && item.getTaskUrl().contains("visualstudio.com"))
				item.setTaskUrl(item.getTaskUrl().substring(0, item.getTaskUrl().indexOf(".com/") + 5)
						+ "_workitems/edit/" + item.getTaskUrl().substring(item.getTaskUrl().lastIndexOf("/") + 1,
								item.getTaskUrl().length()));
		}

		return itemList;
	}

	private String makeGetRequest(ChannelIntegrationAuth channelInt, String endpoint, int offset, int limit) {
		try {
			if (endpoint.indexOf("?") == -1)
				endpoint += "?";
			else
				endpoint += "&";

			endpoint += "offset=" + offset + "&limit=" + limit;

			URL url = new URL(endpoint);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Authorization", "Bearer " + channelInt.getConfig().getAccessToken());

			if (conn.getResponseCode() == HttpURLConnection.HTTP_OK)
				return IOUtils.toString(conn.getInputStream());
			else {
				log.error("Response Code " + conn.getResponseCode());
				log.error("Response Message " + conn.getResponseMessage());
				throw new RuntimeException("Could not get worklogs for Channel " + channelInt.getChannelId());
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

}
