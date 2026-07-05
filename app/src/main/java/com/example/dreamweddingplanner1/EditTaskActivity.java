package com.example.dreamweddingplanner1;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.dreamweddingplanner1.database.DatabaseHelper;

public class EditTaskActivity extends AppCompatActivity {

    private DatabaseHelper dbHelper;
    private EditText etTask, etDeadline;
    private int taskId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        dbHelper = new DatabaseHelper(this);
        taskId = getIntent().getIntExtra("TASK_ID", -1);

        etTask = findViewById(R.id.etTask);
        etDeadline = findViewById(R.id.etDeadline);

        Button btnSave = findViewById(R.id.btnSaveTask);
        btnSave.setText("Update Task");

        loadTaskData();

        btnSave.setOnClickListener(v -> updateTask());
    }

    private void loadTaskData() {
        etTask.setText(getIntent().getStringExtra("TASK"));
        etDeadline.setText(getIntent().getStringExtra("DEADLINE"));
    }

    private void updateTask() {
        String task = etTask.getText().toString().trim();
        String deadline = etDeadline.getText().toString().trim();

        if (task.isEmpty()) {
            Toast.makeText(this, "Task description is required!", Toast.LENGTH_SHORT).show();
            return;
        }

        int result = dbHelper.updateTask(taskId, task, deadline, false); // completed will be handled separately
        if (result > 0) {
            Toast.makeText(this, "Task Updated Successfully!", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "Failed to update task", Toast.LENGTH_SHORT).show();
        }
    }
}