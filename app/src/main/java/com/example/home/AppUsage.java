package com.example.home;

public class AppUsage {
    private String appName;
    private String usageDuration;

    public AppUsage(String appName, String usageDuration) {
        this.appName = appName;
        this.usageDuration = usageDuration;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getUsageDuration() {
        return usageDuration;
    }

    public void setUsageDuration(String usageDuration) {
        this.usageDuration = usageDuration;
    }
}
