package com.example.dreamweddingplanner1;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.dreamweddingplanner1.database.DatabaseHelper;

public class AddGuestActivity extends AppCompatActivity {

    private DatabaseHelper dbHelper;
    private EditText etName, etPhone, etEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_guest);

        dbHelper = new DatabaseHelper(this);

        etName = findViewById(R.id.etGuestName);
        etPhone = findViewById(R.id.etGuestPhone);
        etEmail = findViewById(R.id.etGuestEmail);
        Button btnSave = findViewById(R.id.btnSaveGuest);

        btnSave.setOnClickListener(v -> {
            String name = etName.getText().toString().trim();
            String phone = etPhone.getText().toString().trim();
            String email = etEmail.getText().toString().trim();

            if (name.isEmpty()) {
                Toast.makeText(this, "Name is required!", Toast.LENGTH_SHORT).show();
                return;
            }

            long result = dbHelper.addGuest(name, phone, email, "Pending");

            if (result > 0) {
                Toast.makeText(this, "Guest Added Successfully!", Toast.LENGTH_SHORT).show();
                finish(); // Go back to previous screen
            } else {
                Toast.makeText(this, "Failed to add guest", Toast.LENGTH_SHORT).show();
            }
        });
    }
}