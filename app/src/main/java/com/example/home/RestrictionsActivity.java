// RestrictionsActivity.java
package com.example.home;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class RestrictionsActivity extends AppCompatActivity {
    private RecyclerView restrictionRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restrictions);

        restrictionRecyclerView = findViewById(R.id.restrictionRecyclerView);
        setupRecyclerView();
    }

    private void setupRecyclerView() {
        restrictionRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        restrictionRecyclerView.setAdapter(new RestrictionAdapter(getRestrictedApps()));
    }

    private List<AppUsage> getRestrictedApps() {
        List<AppUsage> appRestrictions = new ArrayList<>();
        appRestrictions.add(new AppUsage("Instagram", "1h"));
        appRestrictions.add(new AppUsage("YouTube", "2h"));
        appRestrictions.add(new AppUsage("Twitter", "1h 30m"));
        appRestrictions.add(new AppUsage("TikTok", "1h 15m"));
        return appRestrictions;
    }
}
