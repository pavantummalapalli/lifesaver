package com.team.lifesaver;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.*;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;
import android.util.Log;
/**
 * Created by ALI on 20/06/2015.
 */
public class blooddonate_signup extends ActionBarActivity {

    TextView titlebartext;
    Spinner bloodgroupselect_sp;
    EditText name_et, username_et_register, day_et, month_et, year_et, mobile_et, email_et, state_et, city_et, pincode_et,
            password_et_register, repeatpassword_et_register;
    Button signup_btn_register;
    RadioGroup gender_rg;
    RadioButton gender_selected;

    // Progress Dialog
    private ProgressDialog pDialog;

    JSONParser jsonParser = new JSONParser();


    private static String url_create_product = "http://asli.esy.es/insert.php";

    // JSON Node names
    private static final String TAG_SUCCESS = "success";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.blooddonate_signup);
        //set custom title bar
        LinearLayout blooddonate_titlebar = (LinearLayout) findViewById(R.id.blooddonate_titlebar);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.blooddonate_titlebar);
        titlebartext = (TextView) findViewById(R.id.blooddonate_titlebar_text);
        titlebartext.setText("Register");
        //assign variables
        name_et = (EditText) findViewById(R.id.name_et);
        username_et_register = (EditText) findViewById(R.id.username_et_register);
        day_et = (EditText) findViewById(R.id.day_et);
        month_et = (EditText) findViewById(R.id.month_et);
        year_et = (EditText) findViewById(R.id.year_et);
        mobile_et = (EditText) findViewById(R.id.mobile_et);
        email_et = (EditText) findViewById(R.id.email_et);
        state_et = (EditText) findViewById(R.id.state_et);
        city_et = (EditText) findViewById(R.id.city_et);
        pincode_et = (EditText) findViewById(R.id.pincode_et);
        password_et_register = (EditText) findViewById(R.id.password_et_register);
        repeatpassword_et_register = (EditText) findViewById(R.id.repeatpassword_et_register);
        signup_btn_register = (Button) findViewById(R.id.signup_btn_register);
        gender_rg = (RadioGroup) findViewById(R.id.gender_rg);
        gender_rg.check(R.id.male_rb);
        bloodgroupselect_sp = (Spinner) findViewById(R.id.bloodgroupselect_sp);

        signup_btn_register.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        String name = name_et.getText().toString();
                        String username = username_et_register.getText().toString();
                        String day = day_et.getText().toString();
                        String month = month_et.getText().toString();
                        String year = year_et.getText().toString();
                        String mobile = mobile_et.getText().toString();
                        String email = email_et.getText().toString();
                        String state = state_et.getText().toString();
                        String city = city_et.getText().toString();
                        String pincode = pincode_et.getText().toString();
                        String password = password_et_register.getText().toString();
                        String repeatpassword = repeatpassword_et_register.getText().toString();
                        int selected = gender_rg.getCheckedRadioButtonId();
                        gender_selected = (RadioButton) findViewById(selected);
                        String gender = gender_selected.getText().toString();
                        String bloodgroup = bloodgroupselect_sp.getSelectedItem().toString();
                        if (!(password.equals(repeatpassword))) {
                            Toast.makeText(getApplicationContext(), "Password Does Not Matches!!", Toast.LENGTH_LONG).show();
                        } else if (username.equals("")) {
                            Toast.makeText(getApplicationContext(), "Username should not be NULL", Toast.LENGTH_LONG).show();
                        } else if (name.equals("") || day.equals("") || month.equals("") || year.equals("") || mobile.equals("") ||
                                state.equals("") || city.equals("") || pincode.equals("") || password.equals("")) {
                            Toast.makeText(getApplicationContext(), "All Fields are Mandatory except Email", Toast.LENGTH_LONG).show();
                        } else {
                            new insertnewrow().execute();
                        }


                    }
                }
        );

    }

    class insertnewrow extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(blooddonate_signup.this);
            pDialog.setMessage("Registering...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        protected String doInBackground(String... args) {
            String name = name_et.getText().toString();
            String username = username_et_register.getText().toString();
            String day = day_et.getText().toString();
            String month = month_et.getText().toString();
            String year = year_et.getText().toString();
            String mobile = mobile_et.getText().toString();
            String email = email_et.getText().toString();
            String state = state_et.getText().toString();
            String city = city_et.getText().toString();
            String pincode = pincode_et.getText().toString();
            String password = password_et_register.getText().toString();
            int selected = gender_rg.getCheckedRadioButtonId();
            gender_selected = (RadioButton) findViewById(selected);
            String gender = gender_selected.getText().toString();
            String bloodgroup = bloodgroupselect_sp.getSelectedItem().toString();

            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("name", name));
            params.add(new BasicNameValuePair("username", username));
            params.add(new BasicNameValuePair("day", day));
            params.add(new BasicNameValuePair("month", month));
            params.add(new BasicNameValuePair("year", year));
            params.add(new BasicNameValuePair("mobile", mobile));
            params.add(new BasicNameValuePair("email", email));
            params.add(new BasicNameValuePair("state", state));
            params.add(new BasicNameValuePair("city", city));
            params.add(new BasicNameValuePair("pincode", pincode));
            params.add(new BasicNameValuePair("password", password));
            params.add(new BasicNameValuePair("gender", gender));
            params.add(new BasicNameValuePair("bloodgroup", bloodgroup));

            JSONObject json = jsonParser.makeHttpRequest(url_create_product, "POST", params);

            try {
                int success = json.getInt(TAG_SUCCESS);

                if (success == 1) {
                    finish();
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
            if (s.equals("success")) {
                pDialog.dismiss();
                Toast.makeText(getApplicationContext(), "Successfully Registered :), You can now Login", Toast.LENGTH_LONG).show();
            }
            else
            {
                pDialog.dismiss();
                Toast.makeText(getApplicationContext(), "Failed to create account :(, May be USERNAME ALREADY EXISTS", Toast.LENGTH_LONG).show();
            }
        }


    }
}
