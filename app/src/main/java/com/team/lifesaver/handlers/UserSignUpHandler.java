package com.team.lifesaver.handlers;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.team.lifesaver.representations.User;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

/**
 * Created by pavan.t on 10/03/16.
 */
public class UserSignUpHandler {
    private ProgressDialog pDialog;
    private Context context;
    private User user;


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

    public UserSignUpHandler(Context context, User user) {
        this.context = context;
        this.user = user;
    }

    public void startProgessBar(){
        pDialog = new ProgressDialog(context);
        pDialog.setMessage("Registering...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(true);
        pDialog.show();
    }

    public void signUp(int statusCode, Header[] headers, JSONObject response) throws JSONException {
            if(statusCode ==200){
                Toast.makeText(context,"Sucessfully created please login to Lifesaver",Toast.LENGTH_LONG).show();
                ((Activity) context).finish();
            }else{
                    Toast.makeText(context,response.getString("error"),Toast.LENGTH_SHORT).show();
                }
            }

    public void stopProgressBar(){
        pDialog.dismiss();
    }
}
