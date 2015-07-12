package com.example.ali.bloodbank;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.*;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;
import android.util.Log;

/**
 * Created by ALI on 19/06/2015.
 */
public class blooddonate_login extends ActionBarActivity {

    public static final String MyPREFERENCES = "MyPrefs";
    public static final String usernamekey="username";
    SharedPreferences sharedPreferences;

    TextView titlebartext;
    EditText username_et,password_et; Button login_btn,signup_btn;

    // Progress Dialog
    private ProgressDialog pDialog;

    JSONParser jsonParser = new JSONParser();


    private static String url_check = "http://asli.esy.es/login.php";
    public static final String USER_NAME="USERNAME";
    String username,password;

    // JSON Node names
    private static final String TAG_SUCCESS = "success";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.blooddonate_login);
        //set custom title bar
        LinearLayout blooddonate_titlebar=(LinearLayout) findViewById(R.id.blooddonate_titlebar);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.blooddonate_titlebar);
        titlebartext=(TextView) findViewById(R.id.blooddonate_titlebar_text);
        titlebartext.setText("Login");

        //assign variables
        username_et=(EditText) findViewById(R.id.username_et);
        password_et=(EditText) findViewById(R.id.password_et);
        login_btn=(Button) findViewById(R.id.login_btn);
        signup_btn=(Button) findViewById(R.id.signup_btn);


        login_btn.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        username=username_et.getText().toString();
                        password=password_et.getText().toString();
                        if (username.equals(""))
                        {
                            Toast.makeText(getApplicationContext(),"Username Field Should Not be Empty",Toast.LENGTH_LONG).show();
                        }
                        else if (password.equals(""))
                        {
                            Toast.makeText(getApplicationContext(),"Password Field Should Not be Empty",Toast.LENGTH_LONG).show();
                        }
                        else
                        {
                            new validate().execute();
                        }
                    }
                }
        );



        signup_btn.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        Intent i = new Intent(blooddonate_login.this,blooddonate_signup.class);
                        startActivity(i);
                    }
                }
        );




    }
  /*  @Override
    protected void onResume() {
        sharedPreferences=getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        if (sharedPreferences.contains(usernamekey))
        {
            Intent i = new Intent(blooddonate_login.this,blooddonate_afterlogin.class);
            startActivity(i);
        }
        super.onResume();
    }*/
    class validate extends AsyncTask<String,String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(blooddonate_login.this);
            pDialog.setMessage("Loging In...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        protected String doInBackground(String... args)
        {
            String username=username_et.getText().toString();
            String password=password_et.getText().toString();
            List<NameValuePair> params=new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("username",username));
            params.add(new BasicNameValuePair("password",password));
            JSONObject json=jsonParser.makeHttpRequest(url_check,"POST",params);
            try{
                int success=json.getInt(TAG_SUCCESS);
                if (success==1)
                {
                    return "success";
                }
                else
                {
                    return "failure";
                }

            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }
            return "failure";
        }
        protected void onPostExecute(String s)
        {
            pDialog.dismiss();
            if (s.equals("success"))
            {
                sharedPreferences=getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
                Editor editor=sharedPreferences.edit();
                editor.putString(usernamekey,username);
                editor.commit();
                Intent i=new Intent(blooddonate_login.this,blooddonate_afterlogin.class);
                finish();
                startActivity(i);
            }
            else
            {
                Toast.makeText(getApplicationContext(),"Incorrect Username or Password",Toast.LENGTH_LONG).show();
            }
        }
    }


}
