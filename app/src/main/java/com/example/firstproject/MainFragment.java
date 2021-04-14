package com.example.firstproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import androidx.annotation.ContentView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Currency;

public class MainFragment extends Fragment {

    private static final String TAG = "main";
    ArrayList<Record> records;
    private DatabaseHelper databaseHelper;
    private SQLiteDatabase db;
    private Cursor recordCursor;
    private OnFragmentSendDataListener fragmentSendDataListener;

    interface OnFragmentSendDataListener {
        void onSendData(int data);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            fragmentSendDataListener = (OnFragmentSendDataListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() +
                    " should use interface OnFragmentSendDataListener");
        }
    }

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
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        MainActivity.isEditWindow = false;
        db = databaseHelper.getReadableDatabase();
        recordCursor = db.rawQuery("select * from " + DatabaseHelper.TABLE, null);
        records = new ArrayList<Record>();
//        ContentValues contentValues = new ContentValues();
//        contentValues.put(DatabaseHelper.COLUMN_NAME, "hhhhhj");
//        contentValues.put(DatabaseHelper.COLUMN_DATE, "11:11");
//        db.insert(DatabaseHelper.TABLE, null, contentValues);

        while(recordCursor.moveToNext()) {
            records.add(new Record(recordCursor.getString(1),
                        recordCursor.getString(2), recordCursor.getInt(3),
                        recordCursor.getInt(0)));
            Log.d(TAG, "onViewCreated: "+recordCursor.getInt(0));
        }
        recordCursor.close();
//        Log.d(TAG, "onViewCreated: " + records.get(0).getName());
//        setInitialData();
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.record_list);
        RecordAdapter adapter = new RecordAdapter(this.getContext(), records);
        recyclerView.setAdapter(adapter);

        view.findViewById(R.id.but_getNewRecord).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentSendDataListener.onSendData(0);
                MainFragment.this.goToEdit(true, 0);
            }
        });
        db.close();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: DESTROY!");
    }

//    private void setInitialData() {
//        records.add(new Record("Hello, world!", "00:00", getResources().getColor(R.color.white)));
//        records.add(new Record("Hello", "00:00", getResources().getColor(R.color.white)));
//        records.add(new Record("world!", "00:00", getResources().getColor(R.color.white)));
//    }

    public void goToEdit(boolean isNew, int itemId) {
        if (!isNew) {
            fragmentSendDataListener.onSendData(itemId);
        }
        NavHostFragment.findNavController(this)
                .navigate(R.id.action_MainFragment_to_EditFragment);
    }
}
