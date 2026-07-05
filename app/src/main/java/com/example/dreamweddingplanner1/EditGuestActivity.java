package com.example.dreamweddingplanner1;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.dreamweddingplanner1.database.DatabaseHelper;

public class EditGuestActivity extends AppCompatActivity {

    private DatabaseHelper dbHelper;
    private EditText etName, etPhone, etEmail;
    private int guestId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_guest);

        dbHelper = new DatabaseHelper(this);
        guestId = getIntent().getIntExtra("GUEST_ID", -1);

        etName = findViewById(R.id.etGuestName);
        etPhone = findViewById(R.id.etGuestPhone);
        etEmail = findViewById(R.id.etGuestEmail);
        Button btnSave = findViewById(R.id.btnSaveGuest);

        btnSave.setText("Update Guest");

        loadGuestData();

        btnSave.setOnClickListener(v -> updateGuest());
    }

    private void loadGuestData() {
        etName.setText(getIntent().getStringExtra("NAME"));
        etPhone.setText(getIntent().getStringExtra("PHONE"));
        etEmail.setText(getIntent().getStringExtra("EMAIL"));
    }

    private void updateGuest() {
        String name = etName.getText().toString().trim();
        String phone = etPhone.getText().toString().trim();
        String email = etEmail.getText().toString().trim();

        if (name.isEmpty()) {
            Toast.makeText(this, "Name is required!", Toast.LENGTH_SHORT).show();
            return;
        }

        int result = dbHelper.updateGuest(guestId, name, phone, email, "Pending");
        if (result > 0) {
            Toast.makeText(this, "Guest Updated Successfully!", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "Failed to update guest", Toast.LENGTH_SHORT).show();
        }
    }
}