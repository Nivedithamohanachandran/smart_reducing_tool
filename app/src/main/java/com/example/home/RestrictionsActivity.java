package com.example.home;

import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.home.adapter.RestrictionAdapter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class RestrictionsActivity extends AppCompatActivity {
    private RecyclerView restrictionRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restrictions);

        restrictionRecyclerView = findViewById(R.id.restrictionRecyclerView);
        restrictionRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        if (hasUsagePermission()) {
            // Load and display app usage stats
            restrictionRecyclerView.setAdapter(new RestrictionAdapter(getRestrictedApps(), this));
        } else {
            // Show a toast or dialog explaining the need for permission
            Toast.makeText(
                    this,
                    "Usage permission is required to display app usage data. Please enable it in settings.",
                    Toast.LENGTH_LONG
            ).show();

            // Optionally, redirect to settings
            Intent intent = new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS);
            startActivity(intent);
        }
    }

    private boolean hasUsagePermission() {
        UsageStatsManager usageStatsManager = (UsageStatsManager) getSystemService(Context.USAGE_STATS_SERVICE);
        List<UsageStats> stats = usageStatsManager.queryUsageStats(
                UsageStatsManager.INTERVAL_DAILY,
                0,
                System.currentTimeMillis()
        );
        return stats != null && !stats.isEmpty();
    }

    private List<AppUsage> getRestrictedApps() {
        List<AppUsage> appRestrictions = new ArrayList<>();
        UsageStatsManager usageStatsManager = (UsageStatsManager) getSystemService(Context.USAGE_STATS_SERVICE);

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        long startTime = calendar.getTimeInMillis();
        long endTime = System.currentTimeMillis();

        List<UsageStats> usageStats = usageStatsManager.queryUsageStats(
                UsageStatsManager.INTERVAL_DAILY,
                startTime,
                endTime
        );

        long totalForegroundTime = 0;

        // Step 1: Calculate total foreground time
        for (UsageStats stat : usageStats) {
            totalForegroundTime += stat.getTotalTimeInForeground();
        }

        // Step 2: Create AppUsage objects with calculated usagePercentage
        if (usageStats != null) {
            for (UsageStats stat : usageStats) {
                long totalTime = stat.getTotalTimeInForeground();
                if (totalTime > 0) {
                    String appName = stat.getPackageName(); // Replace with actual app name if necessary
                    String usageTime = formatTime(totalTime);
                    float usagePercentage = (totalTime * 100f) / totalForegroundTime; // Calculate usage percentage
                    appRestrictions.add(new AppUsage(appName, usageTime, usagePercentage)); // Pass usagePercentage here
                }
            }
        } else {
            Toast.makeText(this, "No usage stats available.", Toast.LENGTH_SHORT).show();
        }

        return appRestrictions;
    }


    private String formatTime(long millis) {
        long seconds = millis / 1000;
        long minutes = seconds / 60;
        long hours = minutes / 60;

        minutes = minutes % 60;

        return (hours > 0 ? hours + "h " : "") + minutes + "m";
    }
}
