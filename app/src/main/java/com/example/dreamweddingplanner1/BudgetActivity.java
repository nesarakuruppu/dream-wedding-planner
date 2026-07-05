package com.example.dreamweddingplanner1;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.dreamweddingplanner1.adapters.ExpenseAdapter;
import com.example.dreamweddingplanner1.api.CurrencyApiService;
import com.example.dreamweddingplanner1.api.CurrencyResponse;
import com.example.dreamweddingplanner1.database.DatabaseHelper;
import com.example.dreamweddingplanner1.models.Expense;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BudgetActivity extends AppCompatActivity implements ExpenseAdapter.OnExpenseActionListener {

    private TextView tvCurrencyRate, tvTotalAmount;
    private RecyclerView recyclerView;
    private ExpenseAdapter adapter;
    private List<Expense> expenseList = new ArrayList<>();
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_budget);

        dbHelper = new DatabaseHelper(this);

        tvCurrencyRate = findViewById(R.id.tvCurrencyRate);
        tvTotalAmount = findViewById(R.id.tvTotalAmount);
        recyclerView = findViewById(R.id.recyclerViewExpenses);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FloatingActionButton fabAdd = findViewById(R.id.fabAddExpense);
        fabAdd.setOnClickListener(v -> startActivity(new Intent(this, AddExpenseActivity.class)));

        loadCurrencyRate();
        loadExpenses();
    }

    private void loadCurrencyRate() {
        // Your existing currency code...
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.exchangerate-api.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        CurrencyApiService service = retrofit.create(CurrencyApiService.class);
        service.getLatestRates().enqueue(new Callback<CurrencyResponse>() {
            @Override
            public void onResponse(Call<CurrencyResponse> call, Response<CurrencyResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Double lkrRate = response.body().rates.get("LKR");
                    if (lkrRate != null) {
                        tvCurrencyRate.setText(String.format(Locale.US, "1 USD = %.2f LKR", lkrRate));
                    }
                }
            }

            @Override
            public void onFailure(Call<CurrencyResponse> call, Throwable t) {
                tvCurrencyRate.setText("No internet");
            }
        });
    }

    private void loadExpenses() {
        expenseList.clear();
        Cursor cursor = dbHelper.getAllExpenses();
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.E_ID));
                String category = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.E_CATEGORY));
                double amount = cursor.getDouble(cursor.getColumnIndexOrThrow(DatabaseHelper.E_AMOUNT));
                String date = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.E_DATE));
                String note = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.E_NOTE));
                expenseList.add(new Expense(id, category, amount, date, note));
            } while (cursor.moveToNext());
        }
        cursor.close();

        adapter = new ExpenseAdapter(this, expenseList, this);
        recyclerView.setAdapter(adapter);

        double total = dbHelper.getTotalExpense();
        tvTotalAmount.setText(String.format(Locale.US, "Total: Rs %.2f", total));
    }

    @Override
    public void onEdit(Expense expense) {
        Intent intent = new Intent(this, EditExpenseActivity.class);
        intent.putExtra("EXPENSE_ID", expense.getId());
        intent.putExtra("CATEGORY", expense.getCategory());
        intent.putExtra("AMOUNT", expense.getAmount());
        intent.putExtra("DATE", expense.getDate());
        intent.putExtra("NOTE", expense.getNote());
        startActivity(intent);
    }

    @Override
    public void onDelete(Expense expense) {
        new AlertDialog.Builder(this)
                .setTitle("Delete Expense")
                .setMessage("Delete " + expense.getCategory() + " expense?")
                .setPositiveButton("Yes", (dialog, which) -> {
                    dbHelper.deleteExpense(expense.getId());
                    loadExpenses();
                    Toast.makeText(this, "Expense deleted", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadExpenses();
    }
}