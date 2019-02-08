package com.digipro.timedoctor.dao.model;

import java.util.Calendar;

public class Config {
	String accessToken;
	String refreshToken;
	long expiresAt;
	int tdCompanyId;

	String devOpsAccessToken;
	String devOpsRefreshToken;
	long devOpsExpires;

	public String getDevOpsAccessToken() {
		return devOpsAccessToken;
	}

	public void setDevOpsAccessToken(String devOpsAccessToken) {
		this.devOpsAccessToken = devOpsAccessToken;
	}

	public String getDevOpsRefreshToken() {
		return devOpsRefreshToken;
	}

	public void setDevOpsRefreshToken(String devOpsRefreshToken) {
		this.devOpsRefreshToken = devOpsRefreshToken;
	}

	public long getDevOpsExpires() {
		return devOpsExpires;
	}

	public void setDevOpsExpires(long devOpsExpires) {
		this.devOpsExpires = devOpsExpires;
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

	public long getExpiresAt() {
		return expiresAt;
	}

	public void setExpiresAt(long expiresAt) {
		this.expiresAt = expiresAt;
	}

	public int getTdCompanyId() {
		return tdCompanyId;
	}

	public void setTdCompanyId(int tdCompanyId) {
		this.tdCompanyId = tdCompanyId;
	}

	public boolean isDevOpsTokenExpired() {
		Calendar expires = Calendar.getInstance();
		expires.setTimeInMillis(devOpsExpires);

		return Calendar.getInstance().after(expires);
	}

}
