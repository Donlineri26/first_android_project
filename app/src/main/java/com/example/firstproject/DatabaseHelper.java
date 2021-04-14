package com.example.firstproject;

import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.content.Context;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "record_store.db"; // название бд
    private static final int SCHEMA = 1; // версия базы данных
    static final String TABLE = "records"; // название таблицы в бд
    // названия столбцов
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_DATE = "date";
    public static final String COLUMN_COLOR = "color";
    public static final String COLUMN_DATA = "data";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, SCHEMA);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE + " (" + COLUMN_ID
                + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_NAME
                + " TEXT, " + COLUMN_DATE + " TEXT, " + COLUMN_COLOR
                + " INTEGER, "+ COLUMN_DATA + " TEXT);");
        // добавление начальных данных
        db.execSQL("INSERT INTO "+ TABLE +" (" + COLUMN_NAME
                + ", " + COLUMN_DATE  + ", " + COLUMN_COLOR + ", " + COLUMN_DATA
                + ") VALUES ('hello', '00:00', '" + R.color.white + "', 'helloWorld');");
//        db.execSQL("INSERT INTO " + TABLE + " (name, date, color, data) VALUES " +
//                "('hellooo', '00:00', '" + R.color.grey + "', 'helloWorld');");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion,  int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE);
        onCreate(db);
    }
}
