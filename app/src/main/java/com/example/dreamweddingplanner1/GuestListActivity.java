package com.example.dreamweddingplanner1;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.dreamweddingplanner1.adapters.GuestAdapter;
import com.example.dreamweddingplanner1.database.DatabaseHelper;
import com.example.dreamweddingplanner1.models.Guest;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;
import java.util.List;

public class GuestListActivity extends AppCompatActivity implements GuestAdapter.OnGuestActionListener {

    private RecyclerView recyclerView;
    private GuestAdapter adapter;
    private List<Guest> guestList = new ArrayList<>();
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guest_list);

        dbHelper = new DatabaseHelper(this);

        recyclerView = findViewById(R.id.recyclerViewGuests);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FloatingActionButton fabAdd = findViewById(R.id.fabAddGuest);
        fabAdd.setOnClickListener(v -> startActivity(new Intent(this, AddGuestActivity.class)));

        loadGuests();
    }

    private void loadGuests() {
        guestList.clear();
        Cursor cursor = dbHelper.getAllGuests();
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.G_ID));
                String name = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.G_NAME));
                String phone = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.G_PHONE));
                String email = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.G_EMAIL));
                String rsvp = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.G_RSVP));

                guestList.add(new Guest(id, name, phone, email, rsvp));
            } while (cursor.moveToNext());
        }
        cursor.close();

        adapter = new GuestAdapter(this, guestList, this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onEdit(Guest guest) {
        Intent intent = new Intent(this, EditGuestActivity.class);
        intent.putExtra("GUEST_ID", guest.getId());
        intent.putExtra("NAME", guest.getName());
        intent.putExtra("PHONE", guest.getPhone());
        intent.putExtra("EMAIL", guest.getEmail());
        startActivity(intent);
    }

    @Override
    public void onDelete(Guest guest) {
        new AlertDialog.Builder(this)
                .setTitle("Delete Guest")
                .setMessage("Are you sure you want to delete " + guest.getName() + "?")
                .setPositiveButton("Yes", (dialog, which) -> {
                    dbHelper.deleteGuest(guest.getId());
                    loadGuests();
                    Toast.makeText(this, "Guest deleted", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadGuests();
    }
}