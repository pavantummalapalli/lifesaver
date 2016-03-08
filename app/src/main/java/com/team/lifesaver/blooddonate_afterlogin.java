package com.team.lifesaver;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.LinearGradient;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ALI on 20/06/2015.
 */
public class blooddonate_afterlogin extends ActionBarActivity {

    public static final String MyPREFERENCES = "MyPrefs";
    public static final String usernamekey="username";
    private ProgressDialog pDialog;
    private static String url_fetch_data = "http://asli.esy.es/place.php";
    JSONParser jsonParser = new JSONParser();
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_ARRAY = "userdata";

    Button searchdonor_btn,searchdonormap_btn,profile_btn,youtube_btn,tips_btn,logout_btn;

    TextView titlebartext;
    String city_map="Patna",state_map="Bihar";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.blooddonate_afterlogin);
        //set custom title bar
        LinearLayout blooddonate_titlebar=(LinearLayout) findViewById(R.id.blooddonate_titlebar);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.blooddonate_titlebar);
        titlebartext=(TextView) findViewById(R.id.blooddonate_titlebar_text);
        SharedPreferences sharedPreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        String ss=sharedPreferences.getString(usernamekey,"");
        titlebartext.setText("Hi, "+ss);


        searchdonor_btn=(Button) findViewById(R.id.searchdonor_btn);
        searchdonormap_btn=(Button) findViewById(R.id.searchdonormap_btn);
        profile_btn=(Button) findViewById(R.id.profile_btn);
        youtube_btn=(Button) findViewById(R.id.youtube_btn);
        tips_btn=(Button) findViewById(R.id.tips_btn);
        logout_btn=(Button) findViewById(R.id.logout_btn);




        searchdonormap_btn.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        new placefinder().execute();
                    }
                }
        );
        tips_btn.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        Intent i = new Intent(blooddonate_afterlogin.this,blooddonate_tips.class);

                        startActivity(i);
                    }


                }
        );
        logout_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
                Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.commit();
                finish();
            }
        });

        profile_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(blooddonate_afterlogin.this,blooddonate_profile.class);
                startActivity(i);
            }
        });

        searchdonor_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(blooddonate_afterlogin.this,blooddonate_search.class);
                startActivity(i);
            }
        });

        youtube_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse("https://www.youtube.com/watch?v=nGDqtKQnSgw"));
                if (i.resolveActivity(getPackageManager())!=null) {
                    startActivity(i);
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Unable to Open Youtube",Toast.LENGTH_LONG).show();
                }

            }
        });


    }

    class placefinder extends AsyncTask<String,String, String>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(blooddonate_afterlogin.this);
            pDialog.setMessage("Please Wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        protected String doInBackground(String... args) {
            SharedPreferences sharedPreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
            String ss=sharedPreferences.getString(usernamekey,"");
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("username", ss));
            JSONObject json = jsonParser.makeHttpRequest(url_fetch_data, "POST", params);
            try {
                int success = json.getInt(TAG_SUCCESS);

                if (success == 1) {
                    JSONArray arr=json.getJSONArray(TAG_ARRAY);
                    JSONObject obj=arr.getJSONObject(0);
                    Log.d("chick",obj.toString());
                    state_map=obj.getString("state");
                    city_map=obj.getString("city");
                    Log.d("testagain",state_map+","+city_map);

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
            pDialog.dismiss();
            if (s.equals("success"))
            {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse("http://www.google.com/maps/place/"+city_map+",+"+state_map+"/"));
                if (i.resolveActivity(getPackageManager())!=null) {
                    startActivity(i);
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Unable to Open Maps",Toast.LENGTH_LONG).show();
                }
            }
            else
            {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse("http://www.google.com/maps/place/"+"Patna"+",+"+"Bihar"+"/"));
                if (i.resolveActivity(getPackageManager())!=null) {
                    startActivity(i);
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Unable to Open Maps",Toast.LENGTH_LONG).show();
                }
            }

        }


    }



}
