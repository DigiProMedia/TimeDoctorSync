package com.digipro.service;

import java.util.List;
import java.util.Properties;

import com.digipro.dao.model.ChannelIntegrationAuth;
import com.digipro.db.dao.SuperAdminDao;

public class AuthService {
	private Properties properties;
	private SuperAdminDao adminDao;

	public AuthService(Properties properties, SuperAdminDao adminDao) {
		this.properties = properties;
		this.adminDao = adminDao;
	}

	public List<ChannelIntegrationAuth> getChannelTdIntegrations() {
		return adminDao.getTimeDoctorChannels();
	}

	public void renewToken(ChannelIntegrationAuth channelInt) {

	}
}
