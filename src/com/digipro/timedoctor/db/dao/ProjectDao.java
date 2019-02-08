package com.digipro.timedoctor.db.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import javax.sql.DataSource;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.digipro.timedoctor.dao.model.ChannelIntegrationAuth;
import com.digipro.timedoctor.dao.model.Config;
import com.digipro.timedoctor.rest.ro.td.Item;
import com.digipro.timedoctor.rest.ro.td.Task;
import com.digipro.timedoctor.service.TimeDoctorService;
import com.digipro.timedoctor.util.GsonUtil;

public class ProjectDao extends BaseDao {
	private static final Log log = LogFactory.getLog(ProjectDao.class);

	public ProjectDao(Properties properties) {
		super(properties);
	}

	public void insertNewTasks(List<Task> taskList) {

		if (taskList == null || taskList.size() == 0)
			return;

		StringBuilder taskIds = new StringBuilder();
		for (Task task : taskList)
			taskIds.append(task.getTaskId() + ",");

		taskIds = taskIds.replace(taskIds.length() - 1, taskIds.length(), "");
		Set<Long> existingTaskMap = new HashSet<>();
		ResultSet rs = null;
		Connection conn = null;
		PreparedStatement stmt = null;
		try {
			DataSource dataSource = getDS();
			conn = dataSource.getConnection();
			stmt = conn.prepareStatement(
					"SELECT n_task_id from project_task where n_task_id in (" + taskIds.toString() + ")");
			rs = stmt.executeQuery();

			while (rs.next())
				existingTaskMap.add(rs.getLong(1));

			stmt.close();

			stmt = conn.prepareStatement(
					"INSERT INTO project_task (n_task_id,n_user_id,n_project_id,vch_task_name,b_active,n_assigned_by_id,vch_td_url,vch_project_system_url) "
							+ "VALUES(?,?,?,?,?,?,?,?)");

			int insertCount = 0;
			for (Task task : taskList) {
				if (existingTaskMap.contains(task.getTaskId()))
					continue;

				insertCount++;
				stmt.setLong(1, task.getTaskId());
				stmt.setLong(2, task.getUserId());
				stmt.setLong(3, task.getProjectId());
				stmt.setString(4, task.getTaskName());
				stmt.setBoolean(5, task.getActive());
				stmt.setLong(6, task.getAssignedBy());
				stmt.setString(7, task.getUrl());
				stmt.setString(8, task.getTaskLink());

				stmt.addBatch();
			}

			log.info(String.format("Inserting %s Tasks", insertCount));

			stmt.executeBatch();

		} catch (Exception sqlException) {
			throw new RuntimeException(sqlException);
		} finally {
			cleanup(rs, conn, stmt);
		}
	}

	public void insertWorklogs(List<Item> itemList) {
		log.info(String.format("Inserting %s Task Worklogs", itemList.size()));

		if (itemList == null || itemList.size() == 0)
			return;

		ResultSet rs = null;
		Connection conn = null;
		PreparedStatement stmt = null;
		try {
			DataSource dataSource = getDS();
			conn = dataSource.getConnection();
			stmt = conn.prepareStatement(
					"INSERT INTO PROJECT_TASK_WORKLOG (n_worklog_id,n_seconds_worked,n_td_user_id,vch_user_name,n_task_id, "
							+ "vch_task_name,vch_task_url,n_project_id,vch_project_name) VALUES(?,?,?,?,?,?,?,?,?)");
			for (Item item : itemList) {
				stmt.setLong(1, parseLong(item.getId()));
				stmt.setInt(2, parseInt(item.getLength()));
				stmt.setLong(3, parseLong(item.getUserId()));
				stmt.setString(4, item.getUserName());
				stmt.setLong(5, parseLong(item.getTaskId()));
				stmt.setString(6, item.getTaskName());
				stmt.setString(7, item.getTaskUrl());
				stmt.setLong(8, parseLong(item.getProjectId()));
				stmt.setString(9, item.getProjectName());
				stmt.addBatch();
			}

			stmt.executeBatch();

		} catch (Exception sqlException) {
			throw new RuntimeException(sqlException);
		} finally {
			cleanup(rs, conn, stmt);
		}

	}

	private int parseInt(String number) {
		if (!StringUtils.isBlank(number))
			return Integer.parseInt(number);

		return 0;
	}

	private long parseLong(String number) {
		if (!StringUtils.isBlank(number))
			return Long.parseLong(number);

		return 0;
	}

}
