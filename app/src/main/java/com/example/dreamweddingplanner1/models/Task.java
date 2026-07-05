package com.example.dreamweddingplanner1.models;

public class Task {
    private int id;
    private String task;
    private String deadline;
    private boolean completed;

    public Task(int id, String task, String deadline, boolean completed) {
        this.id = id;
        this.task = task;
        this.deadline = deadline;
        this.completed = completed;
    }

    // Getters
    public int getId() { return id; }
    public String getTask() { return task; }
    public String getDeadline() { return deadline; }
    public boolean isCompleted() { return completed; }

    // Setter for completed status
    public void setCompleted(boolean completed) { this.completed = completed; }
}