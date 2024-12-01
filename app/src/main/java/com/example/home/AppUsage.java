package com.example.home;

public class AppUsage {
    private final String appName;
    private final String usageDuration;
    private final float usagePercentage;

    // Constructor
    public AppUsage(String appName, String usageDuration, float usagePercentage) {
        this.appName = appName;
        this.usageDuration = usageDuration;
        this.usagePercentage = usagePercentage;
    }

    // Getter for app name
    public String getAppName() {
        return appName;
    }

    // Getter for usage duration
    public String getUsageDuration() {
        return usageDuration;
    }

    // Getter for usage percentage
    public float getUsagePercentage() {
        return usagePercentage;
    }
}
