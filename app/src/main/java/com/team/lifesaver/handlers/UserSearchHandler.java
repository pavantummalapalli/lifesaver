package com.team.lifesaver.handlers;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.team.lifesaver.R;
import com.team.lifesaver.representations.User;
import com.team.lifesaver.user_profile;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

/**
 * Created by pavan.t on 11/03/16.
 */
public class UserSearchHandler {
    public static final String MyPREFERENCES = "MyPrefs";
    public static final String usernamekey="username";
    SharedPreferences sharedPreferences;

    private ProgressDialog pDialog;
    private Context context;
    private User user;
    private TextView name_user,gender_user,bloodgroup_user,dob_user,
            mobile_user,email_user,state_user,city_user,pincode_user,address_user;



    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public UserSearchHandler(Context context,TextView name_user,TextView gender_user,TextView bloodgroup_user, TextView dob_user,
                             TextView mobile_user,TextView email_user,TextView state_user,TextView city_user,TextView pincode_user,TextView address_user) {
        this.context=context;
        this.name_user=name_user;
        this.gender_user=gender_user;
        this.bloodgroup_user=bloodgroup_user;
        this.dob_user=dob_user;
        this.mobile_user=mobile_user;
        this.email_user=email_user;
        this.state_user=state_user;
        this.city_user=city_user;
        this.pincode_user=pincode_user;
        this.address_user=address_user;

    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void startProgessBar(){
        pDialog = new ProgressDialog(context);
        pDialog.setMessage("searching...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(true);
        pDialog.show();
    }

    public void searchUser(int statusCode, Header[] headers, JSONObject response){
            try {
                String success = response.getString("success");
                if (success.equals("true")) {
                    name_user.setText(response.getJSONObject("user").getString("name"));
                    gender_user.setText(response.getJSONObject("user").getString("gender"));
                    bloodgroup_user.setText(response.getJSONObject("user").getString("bloodgroup"));
                    dob_user.setText(response.getJSONObject("user").getString("dateOfBirth"));
                    mobile_user.setText(response.getJSONObject("user").getString("mobile"));
                    email_user.setText(response.getJSONObject("user").getString("email"));
                    state_user.setText(response.getJSONObject("user").getString("state"));
                    city_user.setText(response.getJSONObject("user").getString("city"));
                    pincode_user.setText(Integer.toString(response.getJSONObject("user").getInt("pincode")));
                    address_user.setText(response.getJSONObject("user").getString("address"));
                }
            } catch (JSONException e) {
                Toast.makeText(context, "something went wrong", Toast.LENGTH_SHORT).show();
            }
    }

    public void stopProgressBar(){
        pDialog.dismiss();
    }

}
