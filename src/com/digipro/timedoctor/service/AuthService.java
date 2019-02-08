package com.digipro.timedoctor.service;

import java.io.DataOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Calendar;
import java.util.List;
import java.util.Properties;

import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.digipro.timedoctor.constant.Property;
import com.digipro.timedoctor.dao.model.ChannelIntegrationAuth;
import com.digipro.timedoctor.db.dao.SuperAdminDao;
import com.digipro.timedoctor.rest.ro.devops.DevOpsAuthRo;
import com.digipro.timedoctor.rest.ro.td.AuthRo;
import com.digipro.timedoctor.util.GsonUtil;

public class AuthService {
	private Properties properties;
	private SuperAdminDao adminDao;
	private static final Log log = LogFactory.getLog(AuthService.class);

	public AuthService(Properties properties, SuperAdminDao adminDao) {
		this.properties = properties;
		this.adminDao = adminDao;
	}

	public List<ChannelIntegrationAuth> getChannelTdIntegrations() {
		return adminDao.getTimeDoctorChannels();
	}

	public void renewDevOpsToken(ChannelIntegrationAuth channelInt) {
		try {
			String clientSecret = URLEncoder.encode(properties.getProperty(Property.DEV_OPS_CLIENT_SECRET), "UTF-8");
			String sUrl = properties.getProperty(Property.TD_API_URL) + "/oauth/v2/token?client_id="
					+ channelInt.getApiKey() + "&client_secret=" + channelInt.getApiSecret()
					+ "&grant_type=refresh_token&refresh_token=" + channelInt.getConfig().getRefreshToken();
			String callback = "https://localhost:8443/devops/auth";

			String params = String.format(
					"client_assertion_type=urn:ietf:params:oauth:client-assertion-type:jwt-bearer&client_assertion=%s&grant_type=refresh_token&assertion=%s&redirect_uri=%s",
					clientSecret, channelInt.getConfig().getDevOpsRefreshToken(), callback);

			byte[] postData = params.getBytes(StandardCharsets.UTF_8);
			URL url = new URL("https://app.vssps.visualstudio.com/oauth2/token");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setInstanceFollowRedirects(false);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			conn.setRequestProperty("charset", "utf-8");
			conn.setRequestProperty("Content-Length", Integer.toString(postData.length));
			conn.setUseCaches(false);
			try (DataOutputStream wr = new DataOutputStream(conn.getOutputStream())) {
				wr.write(postData);
			}

			if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
				DevOpsAuthRo auth = GsonUtil.gson.fromJson(IOUtils.toString(conn.getInputStream()), DevOpsAuthRo.class);

				Calendar expires = Calendar.getInstance();
				expires.setTimeInMillis(expires.getTimeInMillis() + (auth.getExpires_in() * 1000));
				channelInt.getConfig().setDevOpsExpires(expires.getTimeInMillis());
				channelInt.getConfig().setDevOpsAccessToken(auth.getAccess_token());
				channelInt.getConfig().setDevOpsRefreshToken(auth.getRefresh_token());

				adminDao.updateTimeDoctorChannel(channelInt);
				System.err.println("Updated DevOps Access Token " + channelInt.getConfig().getDevOpsAccessToken());
			} else {
				log.error("Response Code " + conn.getResponseCode());
				log.error("Response Message " + conn.getResponseMessage());
				throw new RuntimeException("Could not renew token for Channel " + channelInt.getChannelId());
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public void renewToken(ChannelIntegrationAuth channelInt) {
		String sUrl = properties.getProperty(Property.TD_API_URL) + "/oauth/v2/token?client_id="
				+ channelInt.getApiKey() + "&client_secret=" + channelInt.getApiSecret()
				+ "&grant_type=refresh_token&refresh_token=" + channelInt.getConfig().getRefreshToken();
		try {
			URL url = new URL(sUrl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Authorization", "Bearer " + channelInt.getConfig().getAccessToken());

			if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
				AuthRo auth = GsonUtil.gson.fromJson(IOUtils.toString(conn.getInputStream()), AuthRo.class);

				// TODO: Get rid of these. Pending Rory
				channelInt.setAccessToken(auth.getAccessToken());
				channelInt.setRefreshToken(auth.getRefreshToken());
				channelInt.setModified(Calendar.getInstance());
				channelInt.setExpiresSeconds(auth.getExpiresIn());

				Calendar expires = Calendar.getInstance();
				expires.setTimeInMillis(expires.getTimeInMillis() + (auth.getExpiresIn() * 1000));
				channelInt.getConfig().setExpiresAt(expires.getTimeInMillis());
				channelInt.getConfig().setAccessToken(auth.getAccessToken());
				channelInt.getConfig().setRefreshToken(auth.getRefreshToken());

				adminDao.updateTimeDoctorChannel(channelInt);
			} else {
				log.error("Response Code " + conn.getResponseCode());
				log.error("Response Message " + conn.getResponseMessage());
				throw new RuntimeException("Could not renew token for Channel " + channelInt.getChannelId());
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
