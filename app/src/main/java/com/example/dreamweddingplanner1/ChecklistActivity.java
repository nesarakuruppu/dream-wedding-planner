package com.example.dreamweddingplanner1;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.dreamweddingplanner1.adapters.TaskAdapter;
import com.example.dreamweddingplanner1.database.DatabaseHelper;
import com.example.dreamweddingplanner1.models.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;
import java.util.List;

public class ChecklistActivity extends AppCompatActivity
        implements TaskAdapter.OnTaskCheckedListener, TaskAdapter.OnTaskActionListener {

    private RecyclerView recyclerView;
    private TaskAdapter adapter;
    private List<Task> taskList = new ArrayList<>();
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checklist);

        dbHelper = new DatabaseHelper(this);

        recyclerView = findViewById(R.id.recyclerViewTasks);
        if (recyclerView == null) {
            Toast.makeText(this, "Error: RecyclerView not found!", Toast.LENGTH_LONG).show();
            return;
        }

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FloatingActionButton fabAdd = findViewById(R.id.fabAddTask);
        if (fabAdd != null) {
            fabAdd.setOnClickListener(v ->
                    startActivity(new Intent(this, AddTaskActivity.class)));
        }

        loadTasks();
    }

    private void loadTasks() {
        taskList.clear();
        Cursor cursor = dbHelper.getAllTasks();

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.C_ID));
                String taskDesc = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.C_TASK));
                String deadline = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.C_DEADLINE));
                boolean completed = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.C_COMPLETED)) == 1;

                taskList.add(new Task(id, taskDesc, deadline, completed));
            } while (cursor.moveToNext());
        }
        cursor.close();

        adapter = new TaskAdapter(this, taskList, this, this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onTaskChecked(int id, boolean isChecked) {
        dbHelper.updateTaskStatus(id, isChecked);
    }

    @Override
    public void onEdit(Task task) {
        Intent intent = new Intent(this, EditTaskActivity.class);
        intent.putExtra("TASK_ID", task.getId());
        intent.putExtra("TASK", task.getTask());
        intent.putExtra("DEADLINE", task.getDeadline());
        startActivity(intent);
    }

    @Override
    public void onDelete(Task task) {
        new AlertDialog.Builder(this)
                .setTitle("Delete Task")
                .setMessage("Delete this task?\n" + task.getTask())
                .setPositiveButton("Yes", (dialog, which) -> {
                    dbHelper.deleteTask(task.getId());
                    loadTasks();
                    Toast.makeText(this, "Task deleted", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadTasks();  // Refresh after edit/add
    }
}