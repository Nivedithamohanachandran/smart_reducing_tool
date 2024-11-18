package com.example.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;
import androidx.annotation.NonNull;
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
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private TabLayout tabLayout;
    private Spinner timePeriodSpinner;
    private ImageButton datePickerButton;
    private PieChart pieChart;
    private RecyclerView usageRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize views
        tabLayout = findViewById(R.id.tabLayout);
        timePeriodSpinner = findViewById(R.id.timePeriodSpinner);
        datePickerButton = findViewById(R.id.datePickerButton);
        pieChart = findViewById(R.id.pieChart);
        usageRecyclerView = findViewById(R.id.usageRecyclerView);

        setupTabs();
        setupRecyclerView();
        setupTimePeriodSpinner();
        setupDatePicker();
        setupPieChart("Daily"); // Default to Daily
    }

    private void setupTabs() {
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(@NonNull TabLayout.Tab tab) {
                if (tab.getPosition() == 0) {
                    // Load data for "Your Activity" tab
                    // This can remain in MainActivity if needed.
                } else if (tab.getPosition() == 1) {
                    // Navigate to "Restrictions" page
                    Intent intent = new Intent(MainActivity.this, RestrictionsActivity.class);
                    startActivity(intent);
                } else if (tab.getPosition() == 2) {
                    // Load data for "Log Your Mood" tab
                    // This can remain in MainActivity if needed.
                    Intent intent = new Intent(MainActivity.this, MoodActivity.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onTabUnselected(@NonNull TabLayout.Tab tab) { }

            @Override
            public void onTabReselected(@NonNull TabLayout.Tab tab) { }
        });
    }


    private void setupTimePeriodSpinner() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.time_periods, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        timePeriodSpinner.setAdapter(adapter);

        timePeriodSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedPeriod = parent.getItemAtPosition(position).toString();
                setupPieChart(selectedPeriod); // Update PieChart based on selection
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });
    }

    private void setupPieChart(String period) {
        pieChart.setUsePercentValues(true);
        pieChart.getDescription().setEnabled(false);
        pieChart.setDrawHoleEnabled(true);
        pieChart.setHoleRadius(58f);
        pieChart.setTransparentCircleRadius(61f);

        List<PieEntry> entries = new ArrayList<>();
        switch (period) {
            case "Daily":
                entries.add(new PieEntry(30f, "Social Media"));
                entries.add(new PieEntry(25f, "Productivity"));
                entries.add(new PieEntry(15f, "Entertainment"));
                entries.add(new PieEntry(10f, "Health"));
                entries.add(new PieEntry(20f, "Others"));
                break;
            case "Weekly":
                entries.add(new PieEntry(40f, "Social Media"));
                entries.add(new PieEntry(20f, "Productivity"));
                entries.add(new PieEntry(10f, "Entertainment"));
                entries.add(new PieEntry(15f, "Health"));
                entries.add(new PieEntry(15f, "Others"));
                break;
            case "Monthly":
                entries.add(new PieEntry(35f, "Social Media"));
                entries.add(new PieEntry(30f, "Productivity"));
                entries.add(new PieEntry(10f, "Entertainment"));
                entries.add(new PieEntry(5f, "Health"));
                entries.add(new PieEntry(20f, "Others"));
                break;
        }

        PieDataSet dataSet = new PieDataSet(entries, period + " App Usage");
        dataSet.setSliceSpace(3f);
        PieData data = new PieData(dataSet);
        pieChart.setData(data);

        Legend legend = pieChart.getLegend();
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        legend.setOrientation(Legend.LegendOrientation.VERTICAL);
        legend.setDrawInside(false);

        pieChart.invalidate(); // Refresh chart
    }

    private void setupRecyclerView() {
        usageRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        usageRecyclerView.setAdapter(new AppUsageAdapter(getSampleData()));
    }

    private List<AppUsage> getSampleData() {
        List<AppUsage> usageList = new ArrayList<>();
        usageList.add(new AppUsage("Social Media", "2h 30m"));
        usageList.add(new AppUsage("Productivity", "1h 15m"));
        usageList.add(new AppUsage("Entertainment", "45m"));
        usageList.add(new AppUsage("Health", "30m"));
        usageList.add(new AppUsage("Others", "1h"));
        return usageList;
    }

    private void setupDatePicker() {
        datePickerButton.setOnClickListener(v -> {
            // Show DatePicker dialog or calendar
        });
    }
}
