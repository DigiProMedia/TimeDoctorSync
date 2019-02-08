package com.digipro.timedoctor.rest;

import java.io.DataOutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.digipro.timedoctor.constant.Property;
import com.digipro.timedoctor.dao.model.ChannelIntegrationAuth;
import com.digipro.timedoctor.rest.ro.devops.Operation;
import com.digipro.timedoctor.rest.ro.devops.WorkItemRo;
import com.digipro.timedoctor.util.GsonUtil;

public class DevOpsRest {
	private Properties properties;
	private static final Log log = LogFactory.getLog(DevOpsRest.class);
	private static final int LIMIT = 500;

	public DevOpsRest(Properties properties) {
		this.properties = properties;
	}

	public WorkItemRo getWorkItem(ChannelIntegrationAuth channelInt, int workItemId) {
		String endpoint = properties.getProperty(Property.DEV_OPS_API_URL) + "/workItems/" + workItemId;

		WorkItemRo ro = GsonUtil.gson.fromJson(makeGetRequest(channelInt, endpoint), WorkItemRo.class);
		System.err.println(ro.getFields().getMicrosoftVSTSSchedulingCompletedWork());
		return ro;
	}

	public void patchWorkItemScheduling(ChannelIntegrationAuth channelInt, int workItemId, String completed,
			String remaining) {
		String endpoint = properties.getProperty(Property.DEV_OPS_API_URL) + "/workItems/" + workItemId;
		List<Operation> opList = new ArrayList<>();
		Operation op = new Operation();
		op.setOp("add");
		op.setPath("/fields/Microsoft.VSTS.Scheduling.CompletedWork");
		op.setValue(completed);
		opList.add(op);

		op = new Operation();
		op.setOp("add");
		op.setPath("/fields/Microsoft.VSTS.Scheduling.RemainingWork");
		op.setValue(remaining);
		opList.add(op);

		makePatchRequest(channelInt, endpoint, GsonUtil.gson.toJson(opList));
	}

	private String makeGetRequest(ChannelIntegrationAuth channelInt, String endpoint) {
		try {

			URL url = new URL(endpoint);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Authorization", "Bearer " + channelInt.getConfig().getDevOpsAccessToken());

			if (conn.getResponseCode() == HttpURLConnection.HTTP_OK)
				return IOUtils.toString(conn.getInputStream());
			else {
				log.error("Response Code " + conn.getResponseCode());
				log.error("Response Message " + conn.getResponseMessage());
				throw new RuntimeException("Could not get worklogs for Channel " + channelInt.getChannelId());
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

	private String makePatchRequest(ChannelIntegrationAuth channelInt, String endpoint, String body) {
		try {
			if (endpoint.indexOf("?") > -1)
				endpoint += "&api-version=4.1";
			else
				endpoint += "?api-version=4.1";

			allowMethods("PATCH");

			byte[] postData = body.getBytes(StandardCharsets.UTF_8);
			URL url = new URL(endpoint);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setInstanceFollowRedirects(false);
			// conn.setRequestProperty("X-HTTP-Method-Override", "PATCH");
			conn.setRequestMethod("PATCH");
			conn.setRequestProperty("Authorization", "Bearer " + channelInt.getConfig().getDevOpsAccessToken());
			conn.setRequestProperty("Content-Type", "application/json-patch+json");
			// conn.setRequestProperty("charset", "utf-8");
			conn.setRequestProperty("Content-Length", Integer.toString(postData.length));
			conn.setUseCaches(false);
			try (DataOutputStream wr = new DataOutputStream(conn.getOutputStream())) {
				wr.write(postData);
			}

			System.err.println(conn.getResponseCode());
			System.err.println(conn.getResponseMessage());
			String response = IOUtils.toString(conn.getInputStream());
			System.err.println(response);
			return response;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

	private static void allowMethods(String... methods) {
		try {
			Field methodsField = HttpURLConnection.class.getDeclaredField("methods");

			Field modifiersField = Field.class.getDeclaredField("modifiers");
			modifiersField.setAccessible(true);
			modifiersField.setInt(methodsField, methodsField.getModifiers() & ~Modifier.FINAL);

			methodsField.setAccessible(true);

			String[] oldMethods = (String[]) methodsField.get(null);
			Set<String> methodsSet = new LinkedHashSet<>(Arrays.asList(oldMethods));
			methodsSet.addAll(Arrays.asList(methods));
			String[] newMethods = methodsSet.toArray(new String[0]);

			methodsField.set(null/* static field */, newMethods);
		} catch (NoSuchFieldException | IllegalAccessException e) {
			throw new IllegalStateException(e);
		}
	}

}
