
package com.digipro.timedoctor.rest.ro.td;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Task {

	@SerializedName("task_id")
	@Expose
	private Long taskId;
	@SerializedName("project_id")
	@Expose
	private Long projectId;
	@SerializedName("project_name")
	@Expose
	private String projectName;
	@SerializedName("task_name")
	@Expose
	private String taskName;
	@SerializedName("active")
	@Expose
	private Boolean active;
	@SerializedName("user_id")
	@Expose
	private Long userId;
	@SerializedName("assigned_by")
	@Expose
	private Long assignedBy;
	@SerializedName("url")
	@Expose
	private String url;
	@SerializedName("task_link")
	@Expose
	private String taskLink;

	public Long getTaskId() {
		return taskId;
	}

	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}

	public Long getProjectId() {
		if (projectId == null)
			return 0L;

		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getAssignedBy() {
		return assignedBy;
	}

	public void setAssignedBy(Long assignedBy) {
		this.assignedBy = assignedBy;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getTaskLink() {
		return taskLink;
	}

	public void setTaskLink(String taskLink) {
		this.taskLink = taskLink;
	}

}
