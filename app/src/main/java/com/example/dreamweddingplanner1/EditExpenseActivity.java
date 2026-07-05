package com.example.dreamweddingplanner1;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.dreamweddingplanner1.database.DatabaseHelper;

public class EditExpenseActivity extends AppCompatActivity {

    private DatabaseHelper dbHelper;
    private EditText etCategory, etAmount, etDate, etNote;
    private int expenseId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expense);

        dbHelper = new DatabaseHelper(this);
        expenseId = getIntent().getIntExtra("EXPENSE_ID", -1);

        etCategory = findViewById(R.id.etCategory);
        etAmount = findViewById(R.id.etAmount);
        etDate = findViewById(R.id.etDate);
        etNote = findViewById(R.id.etNote);

        Button btnSave = findViewById(R.id.btnSaveExpense);
        btnSave.setText("Update Expense");

        loadExpenseData();

        btnSave.setOnClickListener(v -> updateExpense());
    }

    private void loadExpenseData() {
        etCategory.setText(getIntent().getStringExtra("CATEGORY"));
        etAmount.setText(String.valueOf(getIntent().getDoubleExtra("AMOUNT", 0)));
        etDate.setText(getIntent().getStringExtra("DATE"));
        etNote.setText(getIntent().getStringExtra("NOTE"));
    }

    private void updateExpense() {
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

        int result = dbHelper.updateExpense(expenseId, category, amount, date, note);
        if (result > 0) {
            Toast.makeText(this, "Expense Updated Successfully!", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "Failed to update expense", Toast.LENGTH_SHORT).show();
        }
    }
}