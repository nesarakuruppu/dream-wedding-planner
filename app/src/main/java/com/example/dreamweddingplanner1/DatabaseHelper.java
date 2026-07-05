package com.example.dreamweddingplanner1.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "dream_wedding.db";
    private static final int DB_VERSION = 1;

    // Guests Table
    public static final String TABLE_GUESTS = "guests";
    public static final String G_ID = "id";
    public static final String G_NAME = "name";
    public static final String G_PHONE = "phone";
    public static final String G_EMAIL = "email";
    public static final String G_RSVP = "rsvp";

    // Expenses Table
    public static final String TABLE_EXPENSES = "expenses";
    public static final String E_ID = "id";
    public static final String E_CATEGORY = "category";
    public static final String E_AMOUNT = "amount";
    public static final String E_DATE = "date";
    public static final String E_NOTE = "note";

    // Checklist Table
    public static final String TABLE_CHECKLIST = "checklist";
    public static final String C_ID = "id";
    public static final String C_TASK = "task";
    public static final String C_DEADLINE = "deadline";
    public static final String C_COMPLETED = "completed";

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createGuests = "CREATE TABLE " + TABLE_GUESTS + " (" +
                G_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                G_NAME + " TEXT NOT NULL, " +
                G_PHONE + " TEXT, " +
                G_EMAIL + " TEXT, " +
                G_RSVP + " TEXT DEFAULT 'Pending')";
        db.execSQL(createGuests);

        String createExpenses = "CREATE TABLE " + TABLE_EXPENSES + " (" +
                E_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                E_CATEGORY + " TEXT, " +
                E_AMOUNT + " REAL, " +
                E_DATE + " TEXT, " +
                E_NOTE + " TEXT)";
        db.execSQL(createExpenses);

        String createChecklist = "CREATE TABLE " + TABLE_CHECKLIST + " (" +
                C_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                C_TASK + " TEXT NOT NULL, " +
                C_DEADLINE + " TEXT, " +
                C_COMPLETED + " INTEGER DEFAULT 0)";
        db.execSQL(createChecklist);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_GUESTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EXPENSES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CHECKLIST);
        onCreate(db);
    }

    // ==================== GUESTS ====================
    public long addGuest(String name, String phone, String email, String rsvp) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(G_NAME, name);
        values.put(G_PHONE, phone);
        values.put(G_EMAIL, email);
        values.put(G_RSVP, rsvp);
        return db.insert(TABLE_GUESTS, null, values);
    }

    public Cursor getAllGuests() {
        return getReadableDatabase().rawQuery("SELECT * FROM " + TABLE_GUESTS + " ORDER BY " + G_NAME, null);
    }

    public int updateGuest(int id, String name, String phone, String email, String rsvp) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(G_NAME, name);
        values.put(G_PHONE, phone);
        values.put(G_EMAIL, email);
        values.put(G_RSVP, rsvp);
        return db.update(TABLE_GUESTS, values, G_ID + "=?", new String[]{String.valueOf(id)});
    }

    public void deleteGuest(int id) {
        getWritableDatabase().delete(TABLE_GUESTS, G_ID + "=?", new String[]{String.valueOf(id)});
    }

    // ==================== EXPENSES ====================
    public long addExpense(String category, double amount, String date, String note) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(E_CATEGORY, category);
        values.put(E_AMOUNT, amount);
        values.put(E_DATE, date);
        values.put(E_NOTE, note);
        return db.insert(TABLE_EXPENSES, null, values);
    }

    public Cursor getAllExpenses() {
        return getReadableDatabase().rawQuery("SELECT * FROM " + TABLE_EXPENSES + " ORDER BY " + E_DATE + " DESC", null);
    }

    public int updateExpense(int id, String category, double amount, String date, String note) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(E_CATEGORY, category);
        values.put(E_AMOUNT, amount);
        values.put(E_DATE, date);
        values.put(E_NOTE, note);
        return db.update(TABLE_EXPENSES, values, E_ID + "=?", new String[]{String.valueOf(id)});
    }

    public void deleteExpense(int id) {
        getWritableDatabase().delete(TABLE_EXPENSES, E_ID + "=?", new String[]{String.valueOf(id)});
    }

    public double getTotalExpense() {
        Cursor cursor = getReadableDatabase().rawQuery("SELECT SUM(" + E_AMOUNT + ") FROM " + TABLE_EXPENSES, null);
        double total = 0;
        if (cursor.moveToFirst()) total = cursor.getDouble(0);
        cursor.close();
        return total;
    }

    // ==================== TASKS ====================
    public long addTask(String task, String deadline) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(C_TASK, task);
        values.put(C_DEADLINE, deadline);
        values.put(C_COMPLETED, 0);
        return db.insert(TABLE_CHECKLIST, null, values);
    }

    public Cursor getAllTasks() {
        return getReadableDatabase().rawQuery("SELECT * FROM " + TABLE_CHECKLIST + " ORDER BY " + C_DEADLINE, null);
    }

    // ✅ This method was missing - used by ChecklistActivity for checkbox
    public void updateTaskStatus(int id, boolean completed) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(C_COMPLETED, completed ? 1 : 0);
        db.update(TABLE_CHECKLIST, values, C_ID + "=?", new String[]{String.valueOf(id)});
    }

    public int updateTask(int id, String task, String deadline, boolean completed) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(C_TASK, task);
        values.put(C_DEADLINE, deadline);
        values.put(C_COMPLETED, completed ? 1 : 0);
        return db.update(TABLE_CHECKLIST, values, C_ID + "=?", new String[]{String.valueOf(id)});
    }

    public void deleteTask(int id) {
        getWritableDatabase().delete(TABLE_CHECKLIST, C_ID + "=?", new String[]{String.valueOf(id)});
    }
}