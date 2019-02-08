package com.digipro.timedoctor.db.model;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;
import java.util.Properties;

import com.digipro.timedoctor.constant.Constants;
import com.digipro.timedoctor.dao.model.ChannelIntegrationAuth;
import com.digipro.timedoctor.db.dao.SuperAdminDao;
import com.digipro.timedoctor.service.AuthService;
import com.digipro.timedoctor.service.TimeDoctorService;

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
