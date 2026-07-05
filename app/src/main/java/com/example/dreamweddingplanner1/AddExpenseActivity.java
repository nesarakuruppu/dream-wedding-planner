package com.example.dreamweddingplanner1;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.dreamweddingplanner1.database.DatabaseHelper;

public class AddExpenseActivity extends AppCompatActivity {

    private DatabaseHelper dbHelper;
    private EditText etCategory, etAmount, etDate, etNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expense);

        dbHelper = new DatabaseHelper(this);

        etCategory = findViewById(R.id.etCategory);
        etAmount = findViewById(R.id.etAmount);
        etDate = findViewById(R.id.etDate);
        etNote = findViewById(R.id.etNote);

        Button btnSave = findViewById(R.id.btnSaveExpense);

        btnSave.setOnClickListener(v -> {
            String category = etCategory.getText().toString().trim();
            String amountStr = etAmount.getText().toString().trim();
            String date = etDate.getText().toString().trim();
            String note = etNote.getText().toString().trim();

            if (category.isEmpty() || amountStr.isEmpty() || date.isEmpty()) {
                Toast.makeText(this, "Category, Amount and Date are required!", Toast.LENGTH_SHORT).show();
                return;
            }

            double amount;
            try {
                amount = Double.parseDouble(amountStr);
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Please enter a valid amount!", Toast.LENGTH_SHORT).show();
                return;
            }

            long result = dbHelper.addExpense(category, amount, date, note);
            if (result > 0) {
                Toast.makeText(this, "Expense Added!", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "Failed to add expense", Toast.LENGTH_SHORT).show();
            }
        });
    }
}