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
import com.example.dreamweddingplanner1.models.Expense;
import java.util.List;
import java.util.Locale;

public class ExpenseAdapter extends RecyclerView.Adapter<ExpenseAdapter.ExpenseViewHolder> {

    private Context context;
    private List<Expense> expenseList;
    private ExpenseAdapter.OnExpenseActionListener listener;

    public interface OnExpenseActionListener {
        void onEdit(Expense expense);
        void onDelete(Expense expense);
    }

    public ExpenseAdapter(Context context, List<Expense> expenseList, OnExpenseActionListener listener) {
        this.context = context;
        this.expenseList = expenseList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ExpenseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_expense, parent, false);
        return new ExpenseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ExpenseViewHolder holder, int position) {
        Expense expense = expenseList.get(position);

        holder.tvCategory.setText(expense.getCategory());
        holder.tvAmount.setText(String.format(Locale.US, "Rs %.2f", expense.getAmount()));
        holder.tvDate.setText(expense.getDate());

        if (expense.getNote() != null && !expense.getNote().isEmpty()) {
            holder.tvNote.setText(expense.getNote());
            holder.tvNote.setVisibility(View.VISIBLE);
        } else {
            holder.tvNote.setVisibility(View.GONE);
        }

        holder.btnEdit.setOnClickListener(v -> listener.onEdit(expense));
        holder.btnDelete.setOnClickListener(v -> listener.onDelete(expense));
    }

    @Override
    public int getItemCount() {
        return expenseList.size();
    }

    public static class ExpenseViewHolder extends RecyclerView.ViewHolder {
        TextView tvCategory, tvAmount, tvDate, tvNote;
        ImageButton btnEdit, btnDelete;

        public ExpenseViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCategory = itemView.findViewById(R.id.tvExpenseCategory);
            tvAmount = itemView.findViewById(R.id.tvExpenseAmount);
            tvDate = itemView.findViewById(R.id.tvExpenseDate);
            tvNote = itemView.findViewById(R.id.tvExpenseNote);

            btnEdit = itemView.findViewById(R.id.btnEditExpense);
            btnDelete = itemView.findViewById(R.id.btnDeleteExpense);
        }
    }
}