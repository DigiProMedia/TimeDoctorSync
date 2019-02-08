
package com.digipro.timedoctor.rest.ro.devops;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SystemChangedBy {

    @SerializedName("displayName")
    @Expose
    private String displayName;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("_links")
    @Expose
    private Links__ links;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("uniqueName")
    @Expose
    private String uniqueName;
    @SerializedName("imageUrl")
    @Expose
    private String imageUrl;
    @SerializedName("descriptor")
    @Expose
    private String descriptor;

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Links__ getLinks() {
        return links;
    }

    public void setLinks(Links__ links) {
        this.links = links;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUniqueName() {
        return uniqueName;
    }

    public void setUniqueName(String uniqueName) {
        this.uniqueName = uniqueName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getDescriptor() {
        return descriptor;
    }

    public void setDescriptor(String descriptor) {
        this.descriptor = descriptor;
    }

}
