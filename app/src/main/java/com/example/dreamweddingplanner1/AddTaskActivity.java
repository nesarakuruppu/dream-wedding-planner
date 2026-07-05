package com.example.dreamweddingplanner1;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.dreamweddingplanner1.database.DatabaseHelper;

public class AddTaskActivity extends AppCompatActivity {

    private DatabaseHelper dbHelper;
    private EditText etTask, etDeadline;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        dbHelper = new DatabaseHelper(this);

        etTask = findViewById(R.id.etTask);
        etDeadline = findViewById(R.id.etDeadline);
        Button btnSave = findViewById(R.id.btnSaveTask);

        btnSave.setOnClickListener(v -> {
            String task = etTask.getText().toString().trim();
            String deadline = etDeadline.getText().toString().trim();

            if (task.isEmpty()) {
                Toast.makeText(this, "Task description is required!", Toast.LENGTH_SHORT).show();
                return;
            }

            long result = dbHelper.addTask(task, deadline);

            if (result > 0) {
                Toast.makeText(this, "Task Added Successfully!", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "Failed to add task", Toast.LENGTH_SHORT).show();
            }
        });
    }
}