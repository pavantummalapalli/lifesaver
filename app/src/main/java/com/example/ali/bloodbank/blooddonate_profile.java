package com.example.ali.bloodbank;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
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
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import javax.microedition.khronos.egl.EGLDisplay;

/**
 * Created by ALI on 24/06/2015.
 */
public class blooddonate_profile extends ActionBarActivity {
    public static final String MyPREFERENCES = "MyPrefs";
    public static final String usernamekey="username";
    TextView titlebartext;

    EditText name_et_profile, day_et_profile,month_et_profile,year_et_profile,mobile_et_profile,email_et_profile,
            state_et_profile,city_et_profile,pincode_et_profile;
    Button save_btn_profile,edit_btn;
    Spinner bloodgroupselect_sp_profile;
    RadioGroup gender_rg_profile,smoker_rg_profile,drinker_rg_profile,drug_rg_profile,disease_rg_profile;
    RadioButton selected_profile;
    Switch healthy_sw_profile;

    // Progress Dialog
    private ProgressDialog pDialog;

    JSONParser jsonParser = new JSONParser();

    private static String url_fetch_data = "http://asli.esy.es/readarow.php";
    private static String url_update_profile = "http://asli.esy.es/updateprofile.php";

    // JSON Node names
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_ARRAY = "userdata";

    String name,gender_profile,day,month,year,bloodgroup_profile,mobile,email,state,city,pincode,healthy_profile,smoker_profile,drinker_profile,drug_profile,disease_profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.blooddonate_profile);
        //set custom title bar
        RelativeLayout blooddonate_profile_titlebar=(RelativeLayout) findViewById(R.id.blooddonate_profile_titlebar);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.blooddonate_profile_titlebar);
        titlebartext=(TextView) findViewById(R.id.blooddonate_profile_titlebar_text);
        SharedPreferences sharedPreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        String ss=sharedPreferences.getString(usernamekey,"");
        titlebartext.setText(ss);

        name_et_profile=(EditText) findViewById(R.id.name_et_profile);
        day_et_profile=(EditText) findViewById(R.id.day_et_profile);
        month_et_profile=(EditText) findViewById(R.id.month_et_profile);
        year_et_profile=(EditText) findViewById(R.id.year_et_profile);
        mobile_et_profile=(EditText) findViewById(R.id.mobile_et_profile);
        email_et_profile=(EditText) findViewById(R.id.email_et_profile);
        state_et_profile=(EditText) findViewById(R.id.state_et_profile);
        city_et_profile=(EditText) findViewById(R.id.city_et_profile);
        pincode_et_profile=(EditText) findViewById(R.id.pincode_et_profile);
        save_btn_profile=(Button) findViewById(R.id.save_btn_profile);
        edit_btn=(Button) findViewById(R.id.edit_btn);
        bloodgroupselect_sp_profile=(Spinner) findViewById(R.id.bloodgroupselect_sp_profile);
        gender_rg_profile=(RadioGroup) findViewById(R.id.gender_rg_profile);
        smoker_rg_profile=(RadioGroup) findViewById(R.id.smoker);
        drinker_rg_profile=(RadioGroup) findViewById(R.id.drinker);
        drug_rg_profile=(RadioGroup) findViewById(R.id.drug);
        disease_rg_profile=(RadioGroup) findViewById(R.id.disease);
        healthy_sw_profile=(Switch) findViewById(R.id.healthy);
        save_btn_profile.setEnabled(false);
        new loadprofile().execute();

        edit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                name_et_profile.setEnabled(true);
                day_et_profile.setEnabled(true);
                month_et_profile.setEnabled(true);
                year_et_profile.setEnabled(true);
                mobile_et_profile.setEnabled(true);
                email_et_profile.setEnabled(true);
                state_et_profile.setEnabled(true);
                city_et_profile.setEnabled(true);
                pincode_et_profile.setEnabled(true);
                for (int i=0; i<gender_rg_profile.getChildCount(); i++)
                {
                    ((RadioButton)gender_rg_profile.getChildAt(i)).setEnabled(true);
                }

                bloodgroupselect_sp_profile.setEnabled(true);
                for (int i=0; i<smoker_rg_profile.getChildCount(); i++)
                {
                    ((RadioButton)smoker_rg_profile.getChildAt(i)).setEnabled(true);
                }
                for (int i=0; i<drinker_rg_profile.getChildCount(); i++)
                {
                    ((RadioButton)drinker_rg_profile.getChildAt(i)).setEnabled(true);
                }
                for (int i=0; i<drug_rg_profile.getChildCount(); i++)
                {
                    ((RadioButton)drug_rg_profile.getChildAt(i)).setEnabled(true);
                }
                for (int i=0; i<disease_rg_profile.getChildCount(); i++)
                {
                    ((RadioButton)disease_rg_profile.getChildAt(i)).setEnabled(true);
                }
                healthy_sw_profile.setClickable(true);
                save_btn_profile.setEnabled(true);

            }
        });

        save_btn_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name=name_et_profile.getText().toString();
                String day=day_et_profile.getText().toString();
                String month=month_et_profile.getText().toString();
                String year=year_et_profile.getText().toString();
                String mobile=mobile_et_profile.getText().toString();
                String state=state_et_profile.getText().toString();
                String city=city_et_profile.getText().toString();
                String pincode=pincode_et_profile.getText().toString();
                if (name.equals("") || day.equals("") || month.equals("") || year.equals("") || mobile.equals("") || state.equals("") || city.equals("") || pincode.equals(""))
                {
                    Toast.makeText(getApplicationContext(),"All Fields are Mandatory except email",Toast.LENGTH_LONG).show();
                }
                else
                {
                    new updateprofile().execute();
                }

            }
        });



    }

    class updateprofile extends AsyncTask<String,String,String>{
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(blooddonate_profile.this);
            pDialog.setMessage("Updating Your Profile...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }
        protected String doInBackground(String... args) {

            name=name_et_profile.getText().toString();
            day=day_et_profile.getText().toString();
            month=month_et_profile.getText().toString();
            year=year_et_profile.getText().toString();
            mobile=mobile_et_profile.getText().toString();
            email=email_et_profile.getText().toString();
            state=state_et_profile.getText().toString();
            city=city_et_profile.getText().toString();
            pincode=pincode_et_profile.getText().toString();
            gender_profile=((RadioButton) findViewById(gender_rg_profile.getCheckedRadioButtonId())).getText().toString();
            bloodgroup_profile=bloodgroupselect_sp_profile.getSelectedItem().toString();
            smoker_profile=((RadioButton) findViewById(smoker_rg_profile.getCheckedRadioButtonId())).getText().toString();
            drinker_profile=((RadioButton) findViewById(drinker_rg_profile.getCheckedRadioButtonId())).getText().toString();
            drug_profile=((RadioButton) findViewById(drug_rg_profile.getCheckedRadioButtonId())).getText().toString();
            disease_profile=((RadioButton) findViewById(drug_rg_profile.getCheckedRadioButtonId())).getText().toString();
            if (healthy_sw_profile.isChecked())
            {
                healthy_profile="YES";
            }
            else healthy_profile="NO";
            SharedPreferences sharedPreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
            String ss=sharedPreferences.getString(usernamekey,"");
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("usernamekey", ss));
            params.add(new BasicNameValuePair("name", name));
            params.add(new BasicNameValuePair("day", day));
            params.add(new BasicNameValuePair("month", month));
            params.add(new BasicNameValuePair("year", year));
            params.add(new BasicNameValuePair("mobile", mobile));
            params.add(new BasicNameValuePair("email", email));
            params.add(new BasicNameValuePair("state", state));
            params.add(new BasicNameValuePair("city", city));
            params.add(new BasicNameValuePair("pincode", pincode));
            params.add(new BasicNameValuePair("gender", gender_profile));
            params.add(new BasicNameValuePair("bloodgroup", bloodgroup_profile));
            params.add(new BasicNameValuePair("smoker", smoker_profile));
            params.add(new BasicNameValuePair("drinker", drinker_profile));
            params.add(new BasicNameValuePair("drug", drug_profile));
            params.add(new BasicNameValuePair("disease", disease_profile));
            params.add(new BasicNameValuePair("healthy", healthy_profile));

            JSONObject json = jsonParser.makeHttpRequest(url_update_profile, "POST", params);

            try {
                int success = json.getInt(TAG_SUCCESS);

                if (success == 1) {
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
                name_et_profile.setEnabled(false);
                day_et_profile.setEnabled(false);
                month_et_profile.setEnabled(false);
                year_et_profile.setEnabled(false);
                mobile_et_profile.setEnabled(false);
                email_et_profile.setEnabled(false);
                state_et_profile.setEnabled(false);
                city_et_profile.setEnabled(false);
                pincode_et_profile.setEnabled(false);
                for (int i=0; i<gender_rg_profile.getChildCount(); i++)
                {
                    ((RadioButton)gender_rg_profile.getChildAt(i)).setEnabled(false);
                }

                bloodgroupselect_sp_profile.setEnabled(false);
                for (int i=0; i<smoker_rg_profile.getChildCount(); i++)
                {
                    ((RadioButton)smoker_rg_profile.getChildAt(i)).setEnabled(false);
                }
                for (int i=0; i<drinker_rg_profile.getChildCount(); i++)
                {
                    ((RadioButton)drinker_rg_profile.getChildAt(i)).setEnabled(false);
                }
                for (int i=0; i<drug_rg_profile.getChildCount(); i++)
                {
                    ((RadioButton)drug_rg_profile.getChildAt(i)).setEnabled(false);
                }
                for (int i=0; i<disease_rg_profile.getChildCount(); i++)
                {
                    ((RadioButton)disease_rg_profile.getChildAt(i)).setEnabled(false);
                }
                healthy_sw_profile.setClickable(false);
                save_btn_profile.setEnabled(false);
                pDialog.dismiss();
                Toast.makeText(getApplicationContext(), "Profile Updated Successfully", Toast.LENGTH_LONG).show();
            }
            else
            {
                pDialog.dismiss();
                Toast.makeText(getApplicationContext(), "sorry, Profile is not updated :(", Toast.LENGTH_LONG).show();
            }
        }


    }


    class loadprofile extends AsyncTask<String,String,String>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(blooddonate_profile.this);
            pDialog.setMessage("Fetching Your Data...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        protected String doInBackground(String... args) {
            SharedPreferences sharedPreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
            String ss=sharedPreferences.getString(usernamekey,"");
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("usernamekey", ss));
            JSONObject json = jsonParser.makeHttpRequest(url_fetch_data, "POST", params);
            //Log.d("Single Product Details", json.toString());

            try {
                int success = json.getInt(TAG_SUCCESS);

                if (success == 1) {
                    JSONArray arr=json.getJSONArray(TAG_ARRAY);
                    JSONObject obj=arr.getJSONObject(0);
                    name=obj.getString("name");
                    day=obj.getString("day");
                    month=obj.getString("month");
                    year=obj.getString("year");
                    mobile=obj.getString("mobile");
                    email=obj.getString("email");
                    state=obj.getString("state");
                    city=obj.getString("city");
                    pincode=obj.getString("pincode");
                    gender_profile=obj.getString("gender");
                    bloodgroup_profile=obj.getString("bloodgroup");
                    smoker_profile=obj.getString("smoker");
                    drinker_profile=obj.getString("drinker");
                    drug_profile=obj.getString("drug");
                    disease_profile=obj.getString("disease");
                    healthy_profile=obj.getString("healthy");

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
                name_et_profile.setText(name);
                name_et_profile.setEnabled(false);
                day_et_profile.setText(day);
                day_et_profile.setEnabled(false);
                month_et_profile.setText(month);
                month_et_profile.setEnabled(false);
                year_et_profile.setText(year);
                year_et_profile.setEnabled(false);
                mobile_et_profile.setText(mobile);
                mobile_et_profile.setEnabled(false);
                email_et_profile.setText(email);
                email_et_profile.setEnabled(false);
                state_et_profile.setText(state);
                state_et_profile.setEnabled(false);
                city_et_profile.setText(city);
                city_et_profile.setEnabled(false);
                pincode_et_profile.setText(pincode);
                pincode_et_profile.setEnabled(false);
                if (gender_profile.equalsIgnoreCase("Male"))
                {
                    gender_rg_profile.check(R.id.male_rb_profile);
                }
                else gender_rg_profile.check(R.id.female_rb_profile);
                for (int i=0; i<gender_rg_profile.getChildCount(); i++)
                {
                    ((RadioButton)gender_rg_profile.getChildAt(i)).setEnabled(false);
                }
                String bloodarr[]={"O-","O+","A-","A+","B-","B+","AB-","AB+"};
                int index=0;
                for (int i=0; i<8; i++)
                {
                    if (bloodarr[i].equalsIgnoreCase(bloodgroup_profile))
                    {
                        index=i;
                        break;
                    }
                }
                bloodgroupselect_sp_profile.setSelection(index);
                bloodgroupselect_sp_profile.setEnabled(false);
                if (smoker_profile.equalsIgnoreCase("NO"))
                {
                    smoker_rg_profile.check(R.id.smoker_no);
                }
                else smoker_rg_profile.check(R.id.smoker_yes);
                for (int i=0; i<smoker_rg_profile.getChildCount(); i++)
                {
                    ((RadioButton)smoker_rg_profile.getChildAt(i)).setEnabled(false);
                }
                if (drinker_profile.equalsIgnoreCase("NO"))
                {
                    drinker_rg_profile.check(R.id.drinker_no);
                }
                else drinker_rg_profile.check(R.id.drinker_yes);
                for (int i=0; i<drinker_rg_profile.getChildCount(); i++)
                {
                    ((RadioButton)drinker_rg_profile.getChildAt(i)).setEnabled(false);
                }

                if (drug_profile.equalsIgnoreCase("NO"))
                {
                    drug_rg_profile.check(R.id.drug_no);
                }
                else drug_rg_profile.check(R.id.drug_yes);
                for (int i=0; i<drug_rg_profile.getChildCount(); i++)
                {
                    ((RadioButton)drug_rg_profile.getChildAt(i)).setEnabled(false);
                }

                if (disease_profile.equalsIgnoreCase("NO"))
                {
                    disease_rg_profile.check(R.id.disease_no);
                }
                else disease_rg_profile.check(R.id.disease_yes);
                for (int i=0; i<disease_rg_profile.getChildCount(); i++)
                {
                    ((RadioButton)disease_rg_profile.getChildAt(i)).setEnabled(false);
                }

                if (healthy_profile.equalsIgnoreCase("YES"))
                {
                    healthy_sw_profile.setChecked(true);
                }
                else healthy_sw_profile.setChecked(false);
                healthy_sw_profile.setClickable(false);
                pDialog.dismiss();

            }
            else
            {
                pDialog.dismiss();

            }
        }

    }

}
