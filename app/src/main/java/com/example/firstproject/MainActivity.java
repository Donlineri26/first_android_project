package com.example.firstproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavHostController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements
        MainFragment.OnFragmentSendDataListener {

    private static final String TAG = "main";
    public static int itemId = 0;
    public static int colorRecord;
    public static boolean isEditWindow;
    public static boolean isCheked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void getSettings(View view) {
        PopupMenu popup = new PopupMenu(this, view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_main, popup.getMenu());
        popup.getMenu().findItem(R.id.menu_interface).setChecked(isCheked);
        if (!isEditWindow){
            popup.getMenu().findItem(R.id.menu_delete).setVisible(false);
            popup.getMenu().findItem(R.id.menu_interface).setVisible(false);
        }
        popup.show();
        popup.getMenu().findItem(R.id.menu_delete).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Log.d(TAG, "onMenuItemClick: yes");
                EditFragment fragment = (EditFragment) FragmentManager.findFragment(view);
                fragment.deleteRecord();
                return true;
            }
        });
        popup.getMenu().findItem(R.id.menu_interface).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Log.d(TAG, "onMenuItemClick: yes");
                isCheked = !item.isChecked();
                item.setChecked(isCheked);
                changeInterface();
                return true;
            }
        });
    }

    public void changeInterface() {
        TextView etf = (TextView) findViewById(R.id.edit_text_field);
        TextView ett = (TextView) findViewById(R.id.edit_text_tag);
        if (isCheked) {
            etf.setBackgroundColor(MainActivity.colorRecord);
            ett.setBackgroundColor(MainActivity.colorRecord);
            changeFont();
        }
        else {
            etf.setBackgroundColor(getResources().getColor(R.color.grey));
            ett.setBackgroundColor(getResources().getColor(R.color.grey));
            etf.setTextColor(getResources().getColor(R.color.black));
            ett.setTextColor(getResources().getColor(R.color.black));
        }
    }


    public void onMenuItemClick(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.menu_settings:
                Log.d(TAG, "onMenuItemClick: "+"settings");
                getSettings();
                break;
            case R.id.menu_help:
                Log.d(TAG, "onMenuItemClick: "+"help");
                getHelp();
                break;
            default:
                break;
        }
    }

    public void getSettings() {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }

    public void getHelp() {

    }

    public void setColorRecord(View view) {
        TextView textView = (TextView) view;
        ColorDrawable cd = (ColorDrawable) textView.getBackground();
        colorRecord = cd.getColor();
        TextView button = (TextView) findViewById(R.id.editRecordColor);
        button.setBackgroundColor(colorRecord);
        findViewById(R.id.colorCollection).setVisibility(View.GONE);
        if (isCheked) {
            findViewById(R.id.edit_text_field).setBackgroundColor(MainActivity.colorRecord);
            findViewById(R.id.edit_text_tag).setBackgroundColor(MainActivity.colorRecord);
            changeFont();
        }
    }

    public void changeFont() {
        TextView textView1 = (TextView) findViewById(R.id.edit_text_field);
        TextView textView2 = (TextView) findViewById(R.id.edit_text_tag);
        if ((MainActivity.colorRecord == getResources().getColor(R.color.black)) ||
            (MainActivity.colorRecord == getResources().getColor(R.color.blue)) ||
            (MainActivity.colorRecord == getResources().getColor(R.color.purple_700)) ||
            (MainActivity.colorRecord == getResources().getColor(R.color.purple_500)) ||
            (MainActivity.colorRecord == getResources().getColor(R.color.brown))) {
            textView1.setTextColor(getResources().getColor(R.color.white));
            textView2.setTextColor(getResources().getColor(R.color.white));
        } else {
            textView1.setTextColor(getResources().getColor(R.color.black));
            textView2.setTextColor(getResources().getColor(R.color.black));
        }
    }

    public void searchRecord(View view) {

    }

    @Override
    public void onSendData(int data) {
        Log.d(TAG, "onSendData: " + "vizov");
        itemId = data;
    }
}
