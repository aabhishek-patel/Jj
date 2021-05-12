package com.aabhishekpatel.jj;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.aabhishekpatel.jj.Login.LoginActivity;
import com.aabhishekpatel.jj.Tab.MainActivity;

import java.util.Locale;

public class LoadingScreenActivity extends AppCompatActivity {

    Button change_language;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadLocale();
        setContentView(R.layout.activity_loading_screen);

        change_language = findViewById(R.id.change_language);

        //getWindow().setStatusBarColor(ContextCompat.getColor(LoadingScreenActivity.this,R.color.black));


        change_language.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showChangeLanguageDialog();
            }
        });

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent =  new Intent(LoadingScreenActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        },3000);
    }

    /*------------------------------- showChangeLanguageDialog -------------------------------*/
    private void showChangeLanguageDialog() {
        final String[] listItems = {"English" , "हिन्दी"};
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(LoadingScreenActivity.this);
        mBuilder.setTitle(R.string.change_language);
        mBuilder.setSingleChoiceItems(listItems, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (i == 0){
                    //English
                    setLocale("en");
                    recreate();
                }

                if (i == 1){
                    //Hindi
                    setLocale("hi");
                    recreate();
                }

                //dismiss alert when Language selected
                dialogInterface.dismiss();
            }
        });
        AlertDialog mDialog = mBuilder.create();
        //show alert dialog
        mDialog.show();
    }

    private void setLocale(String lang) {
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
        //save data to shared preference
        SharedPreferences.Editor editor = getSharedPreferences("Settings",MODE_PRIVATE).edit();
        editor.putString("My_Lang",lang);
        editor.apply();
    }

    //load lang saved in shatre4d prefrence
    public void loadLocale(){
        SharedPreferences prefs = getSharedPreferences("Settings", Activity.MODE_PRIVATE);
        String language = prefs.getString("My_Lang", "");
        setLocale(language);
    }
}