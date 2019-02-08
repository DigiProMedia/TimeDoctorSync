package com.digipro.timedoctor.db.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.lang3.StringUtils;

import com.digipro.timedoctor.dao.model.ChannelIntegrationAuth;
import com.digipro.timedoctor.dao.model.Config;
import com.digipro.timedoctor.rest.ro.td.AuthRo;
import com.digipro.timedoctor.util.GsonUtil;

public class SuperAdminDao extends BaseDao {
	public SuperAdminDao(Properties properties) {
		super(properties);
	}

	public List<ChannelIntegrationAuth> getTimeDoctorChannels() {
		ResultSet rs = null;
		Connection conn = null;
		PreparedStatement stmt = null;
		List<ChannelIntegrationAuth> channelList = new ArrayList<>();
		try {
			DataSource dataSource = getDS();
			conn = dataSource.getConnection();
			stmt = conn.prepareStatement("SELECT * from dp_integrations where dp_int_name = 'time_doctor' ");
			rs = stmt.executeQuery();
			while (rs.next()) {
				ChannelIntegrationAuth channelInt = new ChannelIntegrationAuth();
				channelInt.setApiKey(rs.getString("dp_int_api_username"));
				channelInt.setApiSecret(rs.getString("dp_int_api_password"));
				channelInt.setChannelId(rs.getInt("channel_id"));
				channelInt.setModified(getCalendar(rs.getTimestamp("dp_int_mod_date")));

				// TODO To remove
				channelInt.setExpiresSeconds(rs.getInt("dp_td_expires_in"));
				channelInt.setAccessToken(rs.getString("dp_td_access_token"));
				channelInt.setRefreshToken(rs.getString("dp_td_refresh_token"));

				if (!StringUtils.isEmpty(rs.getString("dp_json_config")))
					channelInt.setConfig(GsonUtil.gson.fromJson(rs.getString("dp_json_config"), Config.class));
				else {
					// TODO: Remove
					Calendar expires = Calendar.getInstance();
					if (channelInt.getModified() != null) {
						expires.setTimeInMillis(channelInt.getModified().getTimeInMillis());
						expires.add(Calendar.SECOND, channelInt.getExpiresSeconds());
					}

					Config config = new Config();
					config.setAccessToken(channelInt.getAccessToken());
					config.setExpiresAt(expires.getTimeInMillis());
					config.setRefreshToken(channelInt.getRefreshToken());
					config.setTdCompanyId(rs.getInt("dp_td_company"));
					channelInt.setConfig(config);
					updateTimeDoctorChannel(channelInt);
				}

				channelList.add(channelInt);
			}

		} catch (Exception sqlException) {
			throw new RuntimeException(sqlException);
		} finally {
			cleanup(rs, conn, stmt);
		}

		return channelList;
	}

	public void updateTimeDoctorChannel(ChannelIntegrationAuth channelInt) {
		ResultSet rs = null;
		Connection conn = null;
		PreparedStatement stmt = null;
		try {
			DataSource dataSource = getDS();
			conn = dataSource.getConnection();

			stmt = conn.prepareStatement(
					"UPDATE dp_integrations set dp_td_access_token = ?, dp_td_refresh_token = ?, dp_td_expires_in = ?, "
							+ " dp_json_config = ? where dp_int_name = 'time_doctor' and channel_id = ?");

			stmt.setString(1, channelInt.getConfig().getAccessToken());
			stmt.setString(2, channelInt.getConfig().getRefreshToken());
			stmt.setInt(3, channelInt.getExpiresSeconds());
			stmt.setString(4, GsonUtil.gson.toJson(channelInt.getConfig()));
			stmt.setInt(5, channelInt.getChannelId());
			stmt.executeUpdate();

		} catch (Exception sqlException) {
			throw new RuntimeException(sqlException);
		} finally {
			cleanup(rs, conn, stmt);
		}

	}

}
