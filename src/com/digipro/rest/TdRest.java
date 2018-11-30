package com.digipro.rest;

import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Properties;

import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.digipro.constant.Property;
import com.digipro.dao.model.ChannelIntegrationAuth;
import com.digipro.rest.ro.td.WorklogRo;
import com.digipro.util.GsonUtil;

public class TdRest {
	private Properties properties;
	private static final Log log = LogFactory.getLog(TdRest.class);

	public TdRest(Properties properties) {
		this.properties = properties;
	}

	/**
	 * TODO: Implement a channel import table that tracks last import date in case
	 * the process doesn't run for some reason
	 * 
	 * TODO: Implement pagination:
	 * https://webapi.timedoctor.com/doc#worklogs#get--v1.1-companies-{company_id}-worklogs
	 * 
	 * @param companyId
	 * @return
	 */
	public WorklogRo getWorkLogs(ChannelIntegrationAuth channelInt) {
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		String date = format.format(cal.getTime());
		String endpoint = properties.getProperty(Property.TD_API_URL) + "/companies/"
				+ channelInt.getConfig().getTdCompanyId() + "/worklogs?start_date=" + date + "&end_date=" + date;
		try {
			URL url = new URL(endpoint);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Authorization", "Bearer " + channelInt.getConfig().getAccessToken());

			if (conn.getResponseCode() == HttpURLConnection.HTTP_OK)
				return GsonUtil.gson.fromJson(IOUtils.toString(conn.getInputStream()), WorklogRo.class);
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
