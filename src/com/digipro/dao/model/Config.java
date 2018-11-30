package com.digipro.dao.model;

public class Config {
	String accessToken;
	String refreshToken;
	long expiresAt;
	int tdCompanyId;

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

}
