package com.example.dreamweddingplanner1.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.dreamweddingplanner1.R;
import com.example.dreamweddingplanner1.models.Task;
import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {

    private Context context;
    private List<Task> taskList;
    private TaskAdapter.OnTaskCheckedListener checkedListener;
    private TaskAdapter.OnTaskActionListener actionListener;

    public interface OnTaskCheckedListener {
        void onTaskChecked(int id, boolean isChecked);
    }

    public interface OnTaskActionListener {
        void onEdit(Task task);
        void onDelete(Task task);
    }

    public TaskAdapter(Context context, List<Task> taskList,
                       OnTaskCheckedListener checkedListener,
                       OnTaskActionListener actionListener) {
        this.context = context;
        this.taskList = taskList;
        this.checkedListener = checkedListener;
        this.actionListener = actionListener;
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_task, parent, false);
        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        Task task = taskList.get(position);

        holder.tvTask.setText(task.getTask());
        holder.tvDeadline.setText(task.getDeadline());
        holder.checkBox.setChecked(task.isCompleted());

        holder.checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (checkedListener != null) {
                checkedListener.onTaskChecked(task.getId(), isChecked);
            }
        });

        holder.btnEdit.setOnClickListener(v -> actionListener.onEdit(task));
        holder.btnDelete.setOnClickListener(v -> actionListener.onDelete(task));
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    public static class TaskViewHolder extends RecyclerView.ViewHolder {
        TextView tvTask, tvDeadline;
        CheckBox checkBox;
        ImageButton btnEdit, btnDelete;

        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTask = itemView.findViewById(R.id.tvTaskName);
            tvDeadline = itemView.findViewById(R.id.tvTaskDeadline);
            checkBox = itemView.findViewById(R.id.checkboxTask);
            btnEdit = itemView.findViewById(R.id.btnEditTask);
            btnDelete = itemView.findViewById(R.id.btnDeleteTask);
        }
    }
}