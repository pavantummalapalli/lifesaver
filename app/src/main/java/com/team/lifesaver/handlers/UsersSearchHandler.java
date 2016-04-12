package com.team.lifesaver.handlers;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.team.lifesaver.R;
import com.team.lifesaver.representations.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cz.msebera.android.httpclient.Header;

/**
 * Created by pavan.t on 11/03/16.
 */
public class UsersSearchHandler {
    public static final String MyPREFERENCES = "MyPrefs";
    public static final String usernamekey="username";
    SharedPreferences sharedPreferences;

    private ProgressDialog pDialog;
    private Context context;
    private User user;
    private List arrayList = new ArrayList();
    private ListView listView;
    private ListAdapter adapter;

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public UsersSearchHandler(Context context, User user,ListView listView) {
        this.context = context;
        this.user = user;
        this.listView=listView;
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


    public void doUsersSearch(int statusCode, Header[] headers, JSONObject response){

        try {
            String success = response.getString("success");

            if (success.equals("true")) {
                JSONArray jsonArray = response.getJSONArray("users");
                for (int i=0; i<jsonArray.length(); i++)
                {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    HashMap<String,String> hm = new HashMap<String,String>();
                    String username_get=jsonObject.getString("username");
                    String mobile_get=jsonObject.getString("mobile");
                    String bloodgroup_get=jsonObject.getString("bloodgroup");
                    hm.put("username",username_get);
                    hm.put("mobile",mobile_get);
                    hm.put("bloodgroup",bloodgroup_get);
                    arrayList.add(hm);
                }

                 adapter = new SimpleAdapter(((Activity) context),arrayList, R.layout.list_items, new String[]{"username","mobile","bloodgroup"},
                        new int[]{R.id.username_tv_listitem,R.id.mobile_tv_listitem,R.id.bloodgroup_tv_listitem});

                listView.setAdapter(adapter);
            }
        } catch (JSONException e) {
            Toast.makeText(context, "something went wrong", Toast.LENGTH_SHORT).show();
        }
    }

    public void stopProgressBar(){
        pDialog.dismiss();
    }
}
