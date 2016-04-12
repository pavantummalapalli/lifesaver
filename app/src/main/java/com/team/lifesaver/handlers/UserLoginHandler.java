package com.team.lifesaver.handlers;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.team.lifesaver.blooddonate_afterlogin;
import com.team.lifesaver.representations.User;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

/**
 * Created by pavan.t on 11/03/16.
 */
public class UserLoginHandler {
    public static final String MyPREFERENCES = "MyPrefs";
    public static final String usernamekey="username";
    SharedPreferences sharedPreferences;

    private ProgressDialog pDialog;
    private Context context;
    private User user;

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public UserLoginHandler(Context context, User user) {
        this.context = context;
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void startProgessBar(){
        pDialog = new ProgressDialog(context);
        pDialog.setMessage("Login...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(true);
        pDialog.show();
    }


    public void login(int statusCode, Header[] headers, JSONObject response) throws JSONException {
        if(statusCode ==200){
            if(response.getString("success").equals("true"))
            Toast.makeText(context, "Sucessfully login to Lifesaver", Toast.LENGTH_LONG).show();
            Intent i=new Intent( ((Activity) context),blooddonate_afterlogin.class);

            sharedPreferences=((Activity) context).getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor=sharedPreferences.edit();
            editor.putString(usernamekey, user.getName());
            editor.commit();
            ((Activity) context).finish();
            ((Activity) context).startActivity(i);
        }else{
            Toast.makeText(context,response.getString("error"),Toast.LENGTH_SHORT).show();
        }
    }

    public void stopProgressBar(){
        pDialog.dismiss();
    }

}
