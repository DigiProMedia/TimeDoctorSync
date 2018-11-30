package com.digipro.service;

import java.util.List;
import java.util.Properties;

import com.digipro.dao.model.ChannelIntegrationAuth;
import com.digipro.rest.TdRest;
import com.digipro.rest.ro.td.WorklogRo;
import com.digipro.util.GsonUtil;

public class TimeDoctorService {

	Properties properties;
	AuthService authService;
	TdRest rest;

	public TimeDoctorService(Properties properties, AuthService authService) {
		this.properties = properties;
		this.authService = authService;
		rest = new TdRest(properties);
	}

	public void importTdData(List<ChannelIntegrationAuth> channelList) {
		for (ChannelIntegrationAuth channelInt : channelList) {
			if (channelInt.isTokenExpired())
				authService.renewToken(channelInt);

			WorklogRo ro = rest.getWorkLogs(channelInt);
			System.err.println(GsonUtil.gson.toJson(ro));
		}
	}

}
