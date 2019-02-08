
package com.digipro.timedoctor.rest.ro.td;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Project {

    @SerializedName("project_id")
    @Expose
    private Integer projectId;
    @SerializedName("project_source")
    @Expose
    private String projectSource;
    @SerializedName("project_name")
    @Expose
    private String projectName;
    @SerializedName("archived")
    @Expose
    private Boolean archived;
    @SerializedName("default")
    @Expose
    private Boolean _default;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("users")
    @Expose
    private List<Integer> users = null;

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public String getProjectSource() {
        return projectSource;
    }

    public void setProjectSource(String projectSource) {
        this.projectSource = projectSource;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public Boolean getArchived() {
        return archived;
    }

    public void setArchived(Boolean archived) {
        this.archived = archived;
    }

    public Boolean getDefault() {
        return _default;
    }

    public void setDefault(Boolean _default) {
        this._default = _default;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<Integer> getUsers() {
        return users;
    }

    public void setUsers(List<Integer> users) {
        this.users = users;
    }

}
