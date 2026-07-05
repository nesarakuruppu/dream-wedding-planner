package com.example.dreamweddingplanner1;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Bottom Navigation
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);

        bottomNav.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            Intent intent = null;

            if (id == R.id.nav_guests) {
                intent = new Intent(this, GuestListActivity.class);
            } else if (id == R.id.nav_budget) {
                intent = new Intent(this, BudgetActivity.class);
            } else if (id == R.id.nav_checklist) {
                intent = new Intent(this, ChecklistActivity.class);
            } else if (id == R.id.nav_inspiration) {
                intent = new Intent(this, InspirationActivity.class);
            }

            if (intent != null) {
                startActivity(intent);
                return true;
            }
            return false;
        });
    }
}