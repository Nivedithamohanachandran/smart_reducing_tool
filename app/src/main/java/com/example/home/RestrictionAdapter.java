// RestrictionAdapter.java
package com.example.home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class RestrictionAdapter extends RecyclerView.Adapter<RestrictionAdapter.ViewHolder> {
    private List<AppUsage> restrictedAppList;

    public RestrictionAdapter(List<AppUsage> restrictedAppList) {
        this.restrictedAppList = restrictedAppList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_app_usage, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        AppUsage appUsage = restrictedAppList.get(position);
        holder.appNameTextView.setText(appUsage.getAppName());
        holder.usageDurationTextView.setText("Limit: " + appUsage.getUsageDuration());
    }

    @Override
    public int getItemCount() {
        return restrictedAppList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView appNameTextView;
        TextView usageDurationTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            appNameTextView = itemView.findViewById(R.id.appNameTextView);
            usageDurationTextView = itemView.findViewById(R.id.usageDurationTextView);
        }
    }
}
