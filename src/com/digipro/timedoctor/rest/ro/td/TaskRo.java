
package com.digipro.timedoctor.rest.ro.td;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TaskRo {

    @SerializedName("count")
    @Expose
    private String count;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("offset")
    @Expose
    private Object offset;
    @SerializedName("limit")
    @Expose
    private Object limit;
    @SerializedName("tasks")
    @Expose
    private List<Task> tasks = null;

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Object getOffset() {
        return offset;
    }

    public void setOffset(Object offset) {
        this.offset = offset;
    }

    public Object getLimit() {
        return limit;
    }

    public void setLimit(Object limit) {
        this.limit = limit;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

}
