package com.example.home;

import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private PieChart pieChart;
    private RecyclerView usageRecyclerView;
    private Spinner timePeriodSpinner;
    private AppUsageAdapter appUsageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pieChart = findViewById(R.id.pieChart);
        usageRecyclerView = findViewById(R.id.usageRecyclerView);
        timePeriodSpinner = findViewById(R.id.timePeriodSpinner);

        setupRecyclerView();
        setupSpinner();
        setupTabs();

        if (hasUsagePermission()) {
            updateContent("Daily"); // Default to "Daily"
        } else {
            requestUsagePermission();
        }
    }

    private void setupSpinner() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.time_periods,
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        timePeriodSpinner.setAdapter(adapter);

        timePeriodSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedPeriod = parent.getItemAtPosition(position).toString();
                updateContent(selectedPeriod); // Update content based on selection
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }

    private void setupTabs() {
        TabLayout tabLayout = findViewById(R.id.tabLayout);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                if (position == 1) {
                    Intent intent = new Intent(MainActivity.this, RestrictionsActivity.class);
                    startActivity(intent);
                } else if (position == 2) {
                    Intent intent = new Intent(MainActivity.this, MoodActivity.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}

            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });
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

    private void requestUsagePermission() {
        Toast.makeText(
                this,
                "Usage permission is required to display app usage data.",
                Toast.LENGTH_LONG
        ).show();
        Intent intent = new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS);
        startActivity(intent);
    }

    private void updateContent(String period) {
        List<AppUsage> appUsageData = getAppUsageData(period);
        updatePieChart(appUsageData);
        appUsageAdapter.updateData(appUsageData);
    }

    private void updatePieChart(List<AppUsage> appUsageData) {
        pieChart.setUsePercentValues(true); // Display percentages in the chart
        pieChart.getDescription().setEnabled(false); // Remove description text
        pieChart.setDrawEntryLabels(false); // Remove text inside the chart

        List<PieEntry> entries = new ArrayList<>();
        for (AppUsage appUsage : appUsageData) {
            entries.add(new PieEntry(appUsage.getUsagePercentage(), appUsage.getAppName()));
        }

        PieDataSet dataSet = new PieDataSet(entries, ""); // No label inside the chart
        dataSet.setSliceSpace(3f);
        dataSet.setValueTextSize(0f); // Remove percentage text inside the pie
        dataSet.setValueTextColor(android.graphics.Color.TRANSPARENT); // Hide text color

        // Customize colors for slices
        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(getResources().getColor(R.color.colorSocialMedia));
        colors.add(getResources().getColor(R.color.colorProductivity));
        colors.add(getResources().getColor(R.color.colorEntertainment));
        colors.add(getResources().getColor(R.color.colorHealth));
        colors.add(getResources().getColor(R.color.colorOthers));
        dataSet.setColors(colors);

        PieData data = new PieData(dataSet);
        pieChart.setData(data);

        // Customize legend (key)
        Legend legend = pieChart.getLegend();
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.CENTER);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        legend.setOrientation(Legend.LegendOrientation.VERTICAL);
        legend.setDrawInside(false);
        legend.setTextSize(12f); // Adjust legend text size

        pieChart.invalidate(); // Refresh chart
    }


    private void setupRecyclerView() {
        usageRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        appUsageAdapter = new AppUsageAdapter(new ArrayList<>());
        usageRecyclerView.setAdapter(appUsageAdapter);
    }

    private List<AppUsage> getAppUsageData(String period) {
        List<AppUsage> appUsageList = new ArrayList<>();
        UsageStatsManager usageStatsManager = (UsageStatsManager) getSystemService(Context.USAGE_STATS_SERVICE);

        Calendar calendar = Calendar.getInstance();
        if ("Daily".equals(period)) {
            calendar.add(Calendar.DAY_OF_MONTH, -1);
        } else if ("Weekly".equals(period)) {
            calendar.add(Calendar.WEEK_OF_YEAR, -1);
        } else if ("Monthly".equals(period)) {
            calendar.add(Calendar.MONTH, -1);
        }
        long startTime = calendar.getTimeInMillis();
        long endTime = System.currentTimeMillis();

        List<UsageStats> usageStats = usageStatsManager.queryUsageStats(
                UsageStatsManager.INTERVAL_DAILY,
                startTime,
                endTime
        );

        long totalForegroundTime = 0;
        for (UsageStats stat : usageStats) {
            totalForegroundTime += stat.getTotalTimeInForeground();
        }

        for (UsageStats stat : usageStats) {
            long totalTime = stat.getTotalTimeInForeground();
            if (totalTime > 0) {
                String appName = stat.getPackageName();
                float usagePercentage = (totalTime * 100f) / totalForegroundTime;
                appUsageList.add(new AppUsage(appName, formatTime(totalTime), usagePercentage));
            }
        }
        return appUsageList;
    }

    private String formatTime(long millis) {
        long seconds = millis / 1000;
        long minutes = seconds / 60;
        long hours = minutes / 60;
        minutes = minutes % 60;

        return (hours > 0 ? hours + "h " : "") + minutes + "m";
    }
}
