
package com.digipro.timedoctor.rest.ro.devops;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Links__ {

    @SerializedName("avatar")
    @Expose
    private Avatar__ avatar;

    public Avatar__ getAvatar() {
        return avatar;
    }

    public void setAvatar(Avatar__ avatar) {
        this.avatar = avatar;
    }

}
