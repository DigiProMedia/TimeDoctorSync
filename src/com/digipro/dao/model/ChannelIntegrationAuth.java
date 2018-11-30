package com.digipro.dao.model;

import java.util.Calendar;

import com.digipro.util.GsonUtil;

public class ChannelIntegrationAuth {
	private String apiKey;
	private String apiSecret;
	private String accessToken;
	private String refreshToken;
	private Calendar modified;
	private int channelId;
	private int expiresSeconds;
	private long tdCompanyId;

	// TODO: Get rid of redundant fields when Rory has moved away from custom table
	// fields
	private Config config;

	public Config getConfig() {
		return config;
	}

	public void setConfig(Config config) {
		this.config = config;
	}

	public String getApiKey() {
		return apiKey;
	}

	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}

	public String getApiSecret() {
		return apiSecret;
	}

	public void setApiSecret(String apiSecret) {
		this.apiSecret = apiSecret;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

	public Calendar getModified() {
		return modified;
	}

	public void setModified(Calendar modified) {
		this.modified = modified;
	}

	public int getChannelId() {
		return channelId;
	}

	public void setChannelId(int channelId) {
		this.channelId = channelId;
	}

	public int getExpiresSeconds() {
		return expiresSeconds;
	}

	public void setExpiresSeconds(int expiresSeconds) {
		this.expiresSeconds = expiresSeconds;
	}

	public long getTdCompanyId() {
		return tdCompanyId;
	}

	public void setTdCompanyId(long tdCompanyId) {
		this.tdCompanyId = tdCompanyId;
	}

	public boolean isTokenExpired() {
		if (getConfig() == null)
			return true;

		Calendar now = Calendar.getInstance();
		Calendar expiredAt = Calendar.getInstance();
		expiredAt.setTimeInMillis(getConfig().getExpiresAt());

		return expiredAt.before(now);
	}

}
