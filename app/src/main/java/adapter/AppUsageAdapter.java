package adapter; // replace with your package structure

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.home.R;
import model.AppUsage;
import java.util.List;

public class AppUsageAdapter extends RecyclerView.Adapter<AppUsageAdapter.ViewHolder> {

    private List<AppUsage> usageList;

    // Constructor to initialize usage list
    public AppUsageAdapter(List<AppUsage> usageList) {
        this.usageList = usageList;
    }

    // ViewHolder to define views in each item
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView category, apps, timeSpent;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            category = itemView.findViewById(R.id.category);
            apps = itemView.findViewById(R.id.apps);
            timeSpent = itemView.findViewById(R.id.timeSpent);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the item layout for each item in RecyclerView
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_app_usage, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Bind data to the item at the specified position
        AppUsage usage = usageList.get(position);
        holder.category.setText(usage.getCategory());
        holder.apps.setText(usage.getApps());
        holder.timeSpent.setText(usage.getTimeSpent());
    }

    @Override
    public int getItemCount() {
        return usageList.size();
    }
}
