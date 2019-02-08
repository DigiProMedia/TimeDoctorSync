
package com.digipro.timedoctor.rest.ro.devops;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Fields {

	@SerializedName("System.AreaPath")
	@Expose
	private String systemAreaPath;
	@SerializedName("System.TeamProject")
	@Expose
	private String systemTeamProject;
	@SerializedName("System.IterationPath")
	@Expose
	private String systemIterationPath;
	@SerializedName("System.WorkItemType")
	@Expose
	private String systemWorkItemType;
	@SerializedName("System.State")
	@Expose
	private String systemState;
	@SerializedName("System.Reason")
	@Expose
	private String systemReason;
	@SerializedName("System.AssignedTo")
	@Expose
	private SystemAssignedTo systemAssignedTo;
	@SerializedName("System.CreatedDate")
	@Expose
	private String systemCreatedDate;
	@SerializedName("System.CreatedBy")
	@Expose
	private SystemCreatedBy systemCreatedBy;
	@SerializedName("System.ChangedDate")
	@Expose
	private String systemChangedDate;
	@SerializedName("System.ChangedBy")
	@Expose
	private SystemChangedBy systemChangedBy;
	@SerializedName("System.CommentCount")
	@Expose
	private Integer systemCommentCount;
	@SerializedName("System.Title")
	@Expose
	private String systemTitle;
	@SerializedName("Microsoft.VSTS.Scheduling.RemainingWork")
	@Expose
	private Double microsoftVSTSSchedulingRemainingWork;
	@SerializedName("Microsoft.VSTS.Scheduling.OriginalEstimate")
	@Expose
	private Double microsoftVSTSSchedulingOriginalEstimate;
	@SerializedName("Microsoft.VSTS.Scheduling.CompletedWork")
	@Expose
	private Double microsoftVSTSSchedulingCompletedWork;
	@SerializedName("Microsoft.VSTS.Common.StateChangeDate")
	@Expose
	private String microsoftVSTSCommonStateChangeDate;
	@SerializedName("Microsoft.VSTS.Common.Priority")
	@Expose
	private Integer microsoftVSTSCommonPriority;
	@SerializedName("DigiProAgile.PriorityLevelDP")
	@Expose
	private String digiProAgilePriorityLevelDP;
	@SerializedName("Custom.Thisisworkpaidforandincontracttoaclient")
	@Expose
	private Boolean customThisisworkpaidforandincontracttoaclient;

	public String getSystemAreaPath() {
		return systemAreaPath;
	}

	public void setSystemAreaPath(String systemAreaPath) {
		this.systemAreaPath = systemAreaPath;
	}

	public String getSystemTeamProject() {
		return systemTeamProject;
	}

	public void setSystemTeamProject(String systemTeamProject) {
		this.systemTeamProject = systemTeamProject;
	}

	public String getSystemIterationPath() {
		return systemIterationPath;
	}

	public void setSystemIterationPath(String systemIterationPath) {
		this.systemIterationPath = systemIterationPath;
	}

	public String getSystemWorkItemType() {
		return systemWorkItemType;
	}

	public void setSystemWorkItemType(String systemWorkItemType) {
		this.systemWorkItemType = systemWorkItemType;
	}

	public String getSystemState() {
		return systemState;
	}

	public void setSystemState(String systemState) {
		this.systemState = systemState;
	}

	public String getSystemReason() {
		return systemReason;
	}

	public void setSystemReason(String systemReason) {
		this.systemReason = systemReason;
	}

	public SystemAssignedTo getSystemAssignedTo() {
		return systemAssignedTo;
	}

	public void setSystemAssignedTo(SystemAssignedTo systemAssignedTo) {
		this.systemAssignedTo = systemAssignedTo;
	}

	public String getSystemCreatedDate() {
		return systemCreatedDate;
	}

	public void setSystemCreatedDate(String systemCreatedDate) {
		this.systemCreatedDate = systemCreatedDate;
	}

	public SystemCreatedBy getSystemCreatedBy() {
		return systemCreatedBy;
	}

	public void setSystemCreatedBy(SystemCreatedBy systemCreatedBy) {
		this.systemCreatedBy = systemCreatedBy;
	}

	public String getSystemChangedDate() {
		return systemChangedDate;
	}

	public void setSystemChangedDate(String systemChangedDate) {
		this.systemChangedDate = systemChangedDate;
	}

	public SystemChangedBy getSystemChangedBy() {
		return systemChangedBy;
	}

	public void setSystemChangedBy(SystemChangedBy systemChangedBy) {
		this.systemChangedBy = systemChangedBy;
	}

	public Integer getSystemCommentCount() {
		return systemCommentCount;
	}

	public void setSystemCommentCount(Integer systemCommentCount) {
		this.systemCommentCount = systemCommentCount;
	}

	public String getSystemTitle() {
		return systemTitle;
	}

	public void setSystemTitle(String systemTitle) {
		this.systemTitle = systemTitle;
	}

	public Double getMicrosoftVSTSSchedulingRemainingWork() {
		return microsoftVSTSSchedulingRemainingWork;
	}

	public void setMicrosoftVSTSSchedulingRemainingWork(Double microsoftVSTSSchedulingRemainingWork) {
		this.microsoftVSTSSchedulingRemainingWork = microsoftVSTSSchedulingRemainingWork;
	}

	public Double getMicrosoftVSTSSchedulingOriginalEstimate() {
		return microsoftVSTSSchedulingOriginalEstimate;
	}

	public void setMicrosoftVSTSSchedulingOriginalEstimate(Double microsoftVSTSSchedulingOriginalEstimate) {
		this.microsoftVSTSSchedulingOriginalEstimate = microsoftVSTSSchedulingOriginalEstimate;
	}

	public Double getMicrosoftVSTSSchedulingCompletedWork() {
		return microsoftVSTSSchedulingCompletedWork;
	}

	public void setMicrosoftVSTSSchedulingCompletedWork(Double microsoftVSTSSchedulingCompletedWork) {
		this.microsoftVSTSSchedulingCompletedWork = microsoftVSTSSchedulingCompletedWork;
	}

	public String getMicrosoftVSTSCommonStateChangeDate() {
		return microsoftVSTSCommonStateChangeDate;
	}

	public void setMicrosoftVSTSCommonStateChangeDate(String microsoftVSTSCommonStateChangeDate) {
		this.microsoftVSTSCommonStateChangeDate = microsoftVSTSCommonStateChangeDate;
	}

	public Integer getMicrosoftVSTSCommonPriority() {
		return microsoftVSTSCommonPriority;
	}

	public void setMicrosoftVSTSCommonPriority(Integer microsoftVSTSCommonPriority) {
		this.microsoftVSTSCommonPriority = microsoftVSTSCommonPriority;
	}

	public String getDigiProAgilePriorityLevelDP() {
		return digiProAgilePriorityLevelDP;
	}

	public void setDigiProAgilePriorityLevelDP(String digiProAgilePriorityLevelDP) {
		this.digiProAgilePriorityLevelDP = digiProAgilePriorityLevelDP;
	}

	public Boolean getCustomThisisworkpaidforandincontracttoaclient() {
		return customThisisworkpaidforandincontracttoaclient;
	}

	public void setCustomThisisworkpaidforandincontracttoaclient(
			Boolean customThisisworkpaidforandincontracttoaclient) {
		this.customThisisworkpaidforandincontracttoaclient = customThisisworkpaidforandincontracttoaclient;
	}

}
