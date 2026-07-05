package com.example.dreamweddingplanner1.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.dreamweddingplanner1.R;
import com.example.dreamweddingplanner1.models.Guest;
import java.util.List;

public class GuestAdapter extends RecyclerView.Adapter<GuestAdapter.GuestViewHolder> {

    private Context context;
    private List<Guest> guestList;
    private OnGuestActionListener listener;

    public interface OnGuestActionListener {
        void onEdit(Guest guest);
        void onDelete(Guest guest);
    }

    public GuestAdapter(Context context, List<Guest> guestList, OnGuestActionListener listener) {
        this.context = context;
        this.guestList = guestList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public GuestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_guest, parent, false);
        return new GuestViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GuestViewHolder holder, int position) {
        Guest guest = guestList.get(position);

        holder.tvName.setText(guest.getName());
        holder.tvPhone.setText(guest.getPhone() != null ? guest.getPhone() : "");
        holder.tvEmail.setText(guest.getEmail() != null ? guest.getEmail() : "");
        holder.tvRsvp.setText(guest.getRsvp());

        // Button Click Listeners
        holder.btnEdit.setOnClickListener(v -> listener.onEdit(guest));
        holder.btnDelete.setOnClickListener(v -> listener.onDelete(guest));
    }

    @Override
    public int getItemCount() {
        return guestList.size();
    }

    public static class GuestViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvPhone, tvEmail, tvRsvp;
        ImageButton btnEdit, btnDelete;

        public GuestViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvGuestName);
            tvPhone = itemView.findViewById(R.id.tvGuestPhone);
            tvEmail = itemView.findViewById(R.id.tvGuestEmail);
            tvRsvp = itemView.findViewById(R.id.tvGuestRsvp);

            btnEdit = itemView.findViewById(R.id.btnEditGuest);
            btnDelete = itemView.findViewById(R.id.btnDeleteGuest);
        }
    }
}