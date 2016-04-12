package com.team.lifesaver;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.team.lifesaver.handlers.UserSearchHandler;
import com.team.lifesaver.services.UserSearchService;
import com.team.lifesaver.services.UsersSearchService;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ALI on 28/06/2015.
 */
public class user_profile extends ActionBarActivity{
    TextView titlebartext;
    TextView name_user,gender_user,bloodgroup_user,dob_user,
        mobile_user,email_user,state_user,city_user,pincode_user,address_user;

    String username_intent;

    ProgressDialog pDialog;


    private static String url_fetch_data = "http://asli.esy.es/userdata.php";
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_ARRAY = "userdata";

    String name,gender,bloodgroup,dob="",day,month,year,smoker,drinker,drug,disease,mobile,email,state,city,pincode;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_profile);
        Intent i=getIntent();
        username_intent=i.getStringExtra("username");
        //custom titlebar
        LinearLayout blooddonate_titlebar = (LinearLayout) findViewById(R.id.blooddonate_titlebar);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.blooddonate_titlebar);
        titlebartext = (TextView) findViewById(R.id.blooddonate_titlebar_text);
        titlebartext.setText(username_intent);

        name_user=(TextView) findViewById(R.id.name_user);
        gender_user=(TextView) findViewById(R.id.gender_user);
        bloodgroup_user=(TextView) findViewById(R.id.bloodgroup_user);
        dob_user=(TextView) findViewById(R.id.dob_user);
        mobile_user=(TextView) findViewById(R.id.mobile_user);
        email_user=(TextView) findViewById(R.id.email_user);
        state_user=(TextView) findViewById(R.id.state_user);
        city_user=(TextView) findViewById(R.id.city_user);
        pincode_user=(TextView) findViewById(R.id.pincode_user);
        address_user=(TextView) findViewById(R.id.address_user);

        UserSearchHandler searchHandler = new UserSearchHandler(user_profile.this,name_user,gender_user,bloodgroup_user,dob_user,mobile_user,email_user,state_user,city_user,pincode_user,address_user);
        try {
            UserSearchService.userSearch(searchHandler, username_intent);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }



}
