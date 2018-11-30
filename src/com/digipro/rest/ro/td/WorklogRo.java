
package com.digipro.rest.ro.td;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WorklogRo {

    @SerializedName("start_time")
    @Expose
    private String startTime;
    @SerializedName("end_time")
    @Expose
    private String endTime;
    @SerializedName("total")
    @Expose
    private Integer total;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("worklogs")
    @Expose
    private Worklogs worklogs;

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Worklogs getWorklogs() {
        return worklogs;
    }

    public void setWorklogs(Worklogs worklogs) {
        this.worklogs = worklogs;
    }

}
