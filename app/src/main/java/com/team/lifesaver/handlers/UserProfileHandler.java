package com.team.lifesaver.handlers;

import android.app.ProgressDialog;
import android.content.Context;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.team.lifesaver.representations.User;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

/**
 * Created by pavan.t on 11/03/16.
 */
public class UserProfileHandler {
    private ProgressDialog pDialog;
    private Context context;
    private User user;

    private Spinner bloodgroupselect_profile;
    private EditText name_et_profile,mobile_et_profile,email_et_profile,
            state_et_profile,city_et_profile,pincode_et_profile,address_er_profile;
    private CalendarView calendarView;
    RadioGroup gender_rg;
    RadioButton gender_selected;


    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public UserProfileHandler(Context context, User user) {
        this.context = context;
        this.user = user;
    }


    public void  loadUser(int statusCode, Header[] headers, JSONObject response){

    }


    public void  updateUser(int statusCode, Header[] headers, JSONObject response){

    }



    public void startProgessBar(){
        pDialog = new ProgressDialog(context);
        pDialog.setMessage("Registering...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(true);
        pDialog.show();
    }
}
