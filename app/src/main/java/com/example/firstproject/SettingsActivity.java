package com.example.firstproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

public class SettingsActivity extends AppCompatActivity {

    public boolean isPassword;
    private String password = "";

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        findViewById(R.id.setPassword).setOnClickListener(new View.OnClickListener() {
            private static final String TAG = "yay";

            @Override
            public void onClick(View v) {
                CheckBox checkBox = v.findViewById(R.id.checkBoxPasswd);
                isPassword = !checkBox.isChecked();
                if (getPassword().equals("")){
                    checkBox.setChecked(isPassword);
                    if (isPassword) {
                        findViewById(R.id.inputPassword).setVisibility(View.VISIBLE);
                    } else
                        findViewById(R.id.inputPassword).setVisibility(View.GONE);
                } else {
                    Log.d(TAG, "onClick: no");
                }
            }
        });
        findViewById(R.id.acceptPassword).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText passwd = (EditText) findViewById(R.id.passwd);
                EditText confpasswd = (EditText) findViewById(R.id.confpasswd);
                String password = passwd.getText().toString();
                String confpassword = confpasswd.getText().toString();
                if ((!password.isEmpty()) && (!confpassword.isEmpty())){
                    if (password.equals(confpassword)) {
                        setPassword(password);
                        passwd.setText("");
                        confpasswd.setText("");
                        findViewById(R.id.inputPassword).setVisibility(View.GONE);
                    }
                }
            }
        });
    }
}