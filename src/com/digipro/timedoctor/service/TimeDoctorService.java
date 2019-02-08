package com.digipro.timedoctor.service;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.digipro.timedoctor.dao.model.ChannelIntegrationAuth;
import com.digipro.timedoctor.db.dao.ProjectDao;
import com.digipro.timedoctor.rest.DevOpsRest;
import com.digipro.timedoctor.rest.TdRest;
import com.digipro.timedoctor.rest.ro.devops.WorkItemRo;
import com.digipro.timedoctor.rest.ro.td.Item;
import com.digipro.timedoctor.rest.ro.td.Task;

public class TimeDoctorService {

	Properties properties;
	AuthService authService;
	TdRest rest;
	DevOpsRest devOpsRest;

	private static final Log log = LogFactory.getLog(TimeDoctorService.class);

	public TimeDoctorService(Properties properties, AuthService authService) {
		this.properties = properties;
		this.authService = authService;
		rest = new TdRest(properties);
		devOpsRest = new DevOpsRest(properties);

	}

	public void importTdData(List<ChannelIntegrationAuth> channelList) {
		ProjectDao dao = new ProjectDao(properties);

		for (ChannelIntegrationAuth channelInt : channelList) {
			if (channelInt.isTokenExpired())
				authService.renewToken(channelInt);

			if (channelInt.getConfig().isDevOpsTokenExpired())
				authService.renewDevOpsToken(channelInt);

			List<Item> itemList = rest.getWorkLogs(channelInt);
			dao.insertWorklogs(itemList);

			List<Task> taskList = rest.getTasks(channelInt, itemList);
			dao.insertNewTasks(taskList);

			log.info("Finished Time Doctor Import");

			updateDedOpsWorkScheduling(channelInt, itemList);

			log.info("Finished Processing");
		}
	}

	private void updateDedOpsWorkScheduling(ChannelIntegrationAuth channelInt, List<Item> itemList) {
		if (channelInt.getConfig().getAccessToken() == null) {
			log.info("DevOps not enabled for Channel " + channelInt.getChannelId()
					+ ". To enable it, you will need to add initial values for devOpsAccessToken, devOpsRefreshToken, devOpsExpires to dp_integrations.vch_json_config");
			return;
		}

		log.info("Starting DevOps import");
		int updateCount = 0;
		DecimalFormat df = new DecimalFormat("#.00");

		for (Item item : itemList) {
			int ticketId = item.getDevOpsTicketId();
			if (ticketId == 0)
				continue;

			// Test
			if (ticketId == 9599) {
				WorkItemRo workItem = devOpsRest.getWorkItem(channelInt, ticketId);
				Double workToDate = workItem.getFields().getMicrosoftVSTSSchedulingCompletedWork();
				if (workToDate == null)
					workToDate = new Double(0);

				workToDate = (workToDate.doubleValue() + (((double) item.getLengthInt()) / 60 / 60));
				Double originalEstimate = workItem.getFields().getMicrosoftVSTSSchedulingOriginalEstimate();

				Double remaining = null;
				if (originalEstimate != null) {
					double orig = originalEstimate.doubleValue();
					orig -= workToDate.doubleValue();

					if (orig <= 0)
						remaining = new Double(0);
					else
						remaining = new Double(orig);
				}

				log.info(String.format("Updating ticket %s. WorkComplete %s WorkRemaining %s", ticketId,
						df.format(workToDate), df.format(remaining)));
				updateCount++;
				devOpsRest.patchWorkItemScheduling(channelInt, ticketId, df.format(workToDate), df.format(remaining));
			}
		}

		log.info("Total DevOps tickets updated " + updateCount);
	}

}
