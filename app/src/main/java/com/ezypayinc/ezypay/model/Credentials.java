package com.ezypayinc.ezypay.model;

import io.realm.RealmObject;

public class Credentials extends RealmObject {
    private String credential;
    private String platform;
    private String platformToken;

    public Credentials() {
    }

    public String getCredential() {
        return credential;
    }

    public void setCredential(String credential) {
        this.credential = credential;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getPlatformToken() {
        return platformToken;
    }

    public void setPlatformToken(String platformToken) {
        this.platformToken = platformToken;
    }
}
