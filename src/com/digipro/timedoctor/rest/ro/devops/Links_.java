
package com.digipro.timedoctor.rest.ro.devops;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Links_ {

    @SerializedName("avatar")
    @Expose
    private Avatar_ avatar;

    public Avatar_ getAvatar() {
        return avatar;
    }

    public void setAvatar(Avatar_ avatar) {
        this.avatar = avatar;
    }

}
