package com.digipro.db.model;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;
import java.util.Properties;

import com.digipro.constant.Constants;
import com.digipro.dao.model.ChannelIntegrationAuth;
import com.digipro.db.dao.SuperAdminDao;
import com.digipro.service.AuthService;
import com.digipro.service.TimeDoctorService;

public class TimeDoctorSync {

	private static Properties properties;

	public static void main(String[] args) {
		loadProperties();
		SuperAdminDao adminDao = new SuperAdminDao(properties);
		AuthService authService = new AuthService(properties, adminDao);
		List<ChannelIntegrationAuth> channelList = authService.getChannelTdIntegrations();

		TimeDoctorService tdService = new TimeDoctorService(properties, authService);
		tdService.importTdData(channelList);
	}

	public static Properties loadProperties() {

		properties = new Properties();
		try {
			properties.load(new FileInputStream(new File(Constants.PROPERTIES)));
			return properties;
		} catch (Exception e) {
			throw new RuntimeException("Clould not load property file " + Constants.PROPERTIES);
		}
	}
}
