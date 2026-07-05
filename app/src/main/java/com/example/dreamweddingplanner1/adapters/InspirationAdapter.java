package com.example.dreamweddingplanner1.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.dreamweddingplanner1.R;
import com.example.dreamweddingplanner1.models.Inspiration;
import java.util.List;

public class InspirationAdapter extends RecyclerView.Adapter<InspirationAdapter.InspirationViewHolder> {

    private Context context;
    private List<Inspiration> inspirationList;

    public InspirationAdapter(Context context, List<Inspiration> inspirationList) {
        this.context = context;
        this.inspirationList = inspirationList;
    }

    @NonNull
    @Override
    public InspirationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_inspiration, parent, false);
        return new InspirationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull InspirationViewHolder holder, int position) {
        Inspiration inspiration = inspirationList.get(position);
        holder.tvDescription.setText(inspiration.getDescription());

        Glide.with(context)
                .load(inspiration.getImageUrl())
                .placeholder(R.drawable.ic_launcher_background)
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return inspirationList.size();
    }

    public static class InspirationViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView tvDescription;

        public InspirationViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.ivInspiration);
            tvDescription = itemView.findViewById(R.id.tvInspirationDesc);
        }
    }
}