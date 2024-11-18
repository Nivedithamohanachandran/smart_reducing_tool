package model; // replace with your package structure

public class AppUsage {
    private String category;
    private String apps;
    private String timeSpent;

    // Constructor
    public AppUsage(String category, String apps, String timeSpent) {
        this.category = category;
        this.apps = apps;
        this.timeSpent = timeSpent;
    }

    // Getters
    public String getCategory() {
        return category;
    }

    public String getApps() {
        return apps;
    }

    public String getTimeSpent() {
        return timeSpent;
    }
}
