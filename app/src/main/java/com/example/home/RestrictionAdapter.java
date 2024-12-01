package com.example.home.adapter;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.home.AppUsage;
import com.example.home.R;

import java.util.List;

public class RestrictionAdapter extends RecyclerView.Adapter<RestrictionAdapter.ViewHolder> {
    private final List<AppUsage> restrictedAppList;
    private final Context context;

    public RestrictionAdapter(List<AppUsage> restrictedAppList, Context context) {
        this.restrictedAppList = restrictedAppList;
        this.context = context;
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

        // Resolve app name and icon from the package name
        try {
            PackageManager pm = context.getPackageManager();
            ApplicationInfo appInfo = pm.getApplicationInfo(appUsage.getAppName(), 0);
            String appName = pm.getApplicationLabel(appInfo).toString();
            Drawable appIcon = pm.getApplicationIcon(appInfo);

            holder.appNameTextView.setText(appName);
            holder.appIconImageView.setImageDrawable(appIcon);
        } catch (PackageManager.NameNotFoundException e) {
            holder.appNameTextView.setText(appUsage.getAppName()); // Default to package name
            holder.appIconImageView.setImageResource(R.drawable.ic_default_app); // Use a default icon
        }

        holder.usageDurationTextView.setText("Usage: " + appUsage.getUsageDuration());
    }

    @Override
    public int getItemCount() {
        return restrictedAppList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView appIconImageView;
        TextView appNameTextView;
        TextView usageDurationTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            appIconImageView = itemView.findViewById(R.id.appIconImageView);
            appNameTextView = itemView.findViewById(R.id.appNameTextView);
            usageDurationTextView = itemView.findViewById(R.id.usageDurationTextView);
        }
    }
}
