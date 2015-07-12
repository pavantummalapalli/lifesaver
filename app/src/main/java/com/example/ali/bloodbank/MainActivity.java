package com.example.ali.bloodbank;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;


public class MainActivity extends ActionBarActivity {
    public static final String MyPREFERENCES = "MyPrefs";
    public static final String usernamekey="username";
    SharedPreferences sharedPreferences;

    Button firstapp,info;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firstapp=(Button) findViewById(R.id.firstapp);
        info=(Button) findViewById(R.id.info);

        //set title bar
        LinearLayout activity_main_titlebar=(LinearLayout) findViewById(R.id.activity_main_titlebar);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.activity_main_titlebar);

        sharedPreferences=getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        firstapp.setOnClickListener(
                new View.OnClickListener()
                {
                    public void onClick(View v)
                    {
                        if (sharedPreferences.contains(usernamekey))
                        {
                            Intent j = new Intent(MainActivity.this, blooddonate_afterlogin.class);
                            startActivity(j);
                        }
                        else {
                            Intent j = new Intent(MainActivity.this, blooddonate_login.class);
                            startActivity(j);
                        }
                    }
                }
        );


        info.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        Intent i = new Intent(MainActivity.this,app_info.class);
                        startActivity(i);
                    }
                }
        );




    }



}
