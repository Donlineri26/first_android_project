package com.example.firstproject;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class EditFragment extends Fragment {

    private static final String TAG = "azazaza";
    private DatabaseHelper databaseHelper;
    private SQLiteDatabase db;
    private Cursor recordCursor;
    private Date date;
    private SimpleDateFormat format;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        databaseHelper = new DatabaseHelper(getContext());
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        return inflater.inflate(R.layout.fragment_edit, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        MainActivity.isEditWindow = true;
        EditText name = (EditText) getView().findViewById(R.id.edit_text_tag);
        EditText data = (EditText) getView().findViewById(R.id.edit_text_field);
        String old_name = "", old_data = "";
        int old_color = getResources().getColor(R.color.white);
        db = databaseHelper.getWritableDatabase();
        Log.d(TAG, "itemId "+MainActivity.itemId);
        if (MainActivity.itemId != 0) {
            recordCursor = db.rawQuery("select * from " + DatabaseHelper.TABLE +
                            " where " + DatabaseHelper.COLUMN_ID + "=" + MainActivity.itemId,
                    null);
            Log.d(TAG, "count: "+recordCursor.getCount());
            recordCursor.moveToFirst();
            old_name = recordCursor.getString(1);
            old_data = recordCursor.getString(4);
            old_color = recordCursor.getInt(3);
            recordCursor.close();
        }
        name.setText(old_name);
        data.setText(old_data);
        MainActivity.colorRecord = old_color;
        view.findViewById(R.id.editRecordColor).setBackgroundColor(old_color);
        if(MainActivity.isCheked){
            MainActivity activity = (MainActivity) getActivity();
            if (activity != null) activity.changeInterface();
        }
        String old_n = old_name;
        String old_d = old_data;
        int old_c = old_color;
        view.findViewById(R.id.save_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkChanges(old_n, old_d, name.getText().toString(),
                        data.getText().toString()))
                    if (MainActivity.itemId == 0)
                        addRecord(name.getText().toString(), data.getText().toString(),
                                MainActivity.colorRecord);
                    else
                        editRecord(name.getText().toString(), data.getText().toString(),
                                MainActivity.colorRecord, MainActivity.itemId);
                else if ((MainActivity.itemId != 0) && (MainActivity.colorRecord != old_c))
                    editRecord(name.getText().toString(), data.getText().toString(),
                            MainActivity.colorRecord, MainActivity.itemId);
                db.close();
                NavHostFragment.findNavController(EditFragment.this)
                        .navigate(R.id.action_EditFragment_to_MainFragment);
            }
        });
        view.findViewById(R.id.editRecordColor).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FrameLayout frameLayout = (FrameLayout) view.findViewById(R.id.colorCollection);
                if (frameLayout.getVisibility() == View.GONE) {
                    frameLayout.setVisibility(View.VISIBLE);
                } else
                    frameLayout.setVisibility(View.GONE);
            }
        });
    }

    public static boolean checkChanges(String old_n, String old_d, String new_n,
                                       String new_d){
        if (old_n.equals(new_n) && old_d.equals(new_d))
            return false;
        return !new_n.isEmpty() || !new_d.isEmpty();
    }

    private ContentValues getRecord(String name, String data, int color) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.COLUMN_NAME, name);
        contentValues.put(DatabaseHelper.COLUMN_DATA, data);
        contentValues.put(DatabaseHelper.COLUMN_COLOR, color);
        date = new Date();
        format = new SimpleDateFormat("hh:mm dd.MM.yyyy", Locale.getDefault());
        contentValues.put(DatabaseHelper.COLUMN_DATE, format.format(date));
        return contentValues;
    }

    private void addRecord(String name, String data, int color) {
        db.insert(DatabaseHelper.TABLE, null, getRecord(name, data, color));
    }

    private void editRecord(String name, String data, int color, int id) {
        db.update(DatabaseHelper.TABLE, getRecord(name, data, color),
                DatabaseHelper.COLUMN_ID + "=" + id, null);
    }

    public void deleteRecord() {
        if (MainActivity.itemId != 0) {
            db.delete(DatabaseHelper.TABLE, DatabaseHelper.COLUMN_ID + " = ?",
                    new String[] {String.valueOf(MainActivity.itemId)});
//            db.execSQL("UPDATE "+DatabaseHelper.TABLE+" SET "+DatabaseHelper.COLUMN_ID+" = " +
//                    DatabaseHelper.COLUMN_ID + " - 1 WHERE "+DatabaseHelper.COLUMN_ID+">" +
//                    MainActivity.itemId);
//            Cursor cursor = db.rawQuery("select * from " + DatabaseHelper.TABLE,
//                    null);
//            while(cursor.moveToNext()) {
//                Log.d(TAG, "index: "+recordCursor.getInt(0));
//            }
//            cursor.close();
        }
        db.close();
        NavHostFragment.findNavController(this)
                .navigate(R.id.action_EditFragment_to_MainFragment);
    }

//    @Override
//    public void onStop() {
//        super.onStop();
//        recordCursor.close();
//    }

}
