package com.example.ali.bloodbank;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ALI on 28/06/2015.
 */
public class user_profile extends ActionBarActivity{
    TextView titlebartext;
    TextView name_user,gender_user,bloodgroup_user,dob_user,smoker_user,drinker_user,drug_user,disease_user,
        mobile_user,email_user,state_user,city_user,pincode_user;

    String username_intent;

    ProgressDialog pDialog;
    JSONParser jsonParser = new JSONParser();

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
        smoker_user=(TextView) findViewById(R.id.smoker_user);
        drinker_user=(TextView) findViewById(R.id.drinker_user);
        drug_user=(TextView) findViewById(R.id.drug_user);
        disease_user=(TextView) findViewById(R.id.disease_user);
        mobile_user=(TextView) findViewById(R.id.mobile_user);
        email_user=(TextView) findViewById(R.id.email_user);
        state_user=(TextView) findViewById(R.id.state_user);
        city_user=(TextView) findViewById(R.id.city_user);
        pincode_user=(TextView) findViewById(R.id.pincode_user);

        new load_user_profile().execute();


    }

    class load_user_profile extends AsyncTask<String,String,String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(user_profile.this);
            pDialog.setMessage("Please Wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        protected String doInBackground(String... args) {
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("username",username_intent));
            JSONObject json = jsonParser.makeHttpRequest(url_fetch_data,"POST",params);

            try {
                int success = json.getInt(TAG_SUCCESS);

                if (success == 1) {
                    JSONArray arr=json.getJSONArray(TAG_ARRAY);
                    JSONObject obj=arr.getJSONObject(0);
                    name=obj.getString("name");
                    gender=obj.getString("gender");
                    bloodgroup=obj.getString("bloodgroup");
                    day=obj.getString("day");
                    month=obj.getString("month");
                    year=obj.getString("year");
                    smoker=obj.getString("smoker");
                    drinker=obj.getString("drinker");
                    drug=obj.getString("drug");
                    disease=obj.getString("disease");
                    mobile=obj.getString("mobile");
                    email=obj.getString("email");
                    state=obj.getString("state");
                    city=obj.getString("city");
                    pincode=obj.getString("pincode");

                    return "success";
                } else {
                    return "failure";
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return "failure";




        }

        protected void onPostExecute(String s) {
            if (s.equals("success"))
            {
                name_user.setText(name);
                gender_user.setText(gender);
                bloodgroup_user.setText(bloodgroup);
                dob=day+"-"+month+"-"+year; dob_user.setText(dob);
                smoker_user.setText(smoker);
                drinker_user.setText(drinker);
                drug_user.setText(drug);
                disease_user.setText(disease);
                mobile_user.setText(mobile);
                email_user.setText(email);
                state_user.setText(state);
                city_user.setText(city);
                pincode_user.setText(pincode);
                pDialog.dismiss();
            }
            else
            {
                pDialog.dismiss();
                Toast.makeText(getApplicationContext(),"Profile Not Loaded",Toast.LENGTH_LONG).show();
            }

        }


    }



}
