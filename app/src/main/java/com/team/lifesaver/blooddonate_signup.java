package com.team.lifesaver;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;


import com.team.lifesaver.handlers.AddressLongLatHandler;
import com.team.lifesaver.handlers.UserSignUpHandler;
import com.team.lifesaver.representations.User;
import com.team.lifesaver.services.AddressGeocodeFind;
import com.team.lifesaver.services.UserSignupService;
import com.team.lifesaver.utils.TextValidator;
import com.team.lifesaver.utils.Validation;

import com.loopj.android.http.*;

import cz.msebera.android.httpclient.Header;


/**
 * Created by pavan.t on 09/03/2016.
 */
public class blooddonate_signup extends ActionBarActivity {

    CalendarView calendar;
    TextView titlebartext;
    Spinner bloodgroupselect_sp;
    EditText name_et, username_et_register, mobile_et, email_et, state_et, city_et, pincode_et,
            password_et_register, repeatpassword_et_register,address_et,long_et,lat_et;;
    Button signup_btn_register;
    RadioGroup gender_rg;
    RadioButton gender_selected;

     int year_=0,month_=0,day_=0;

    // Progress Dialog
    private ProgressDialog pDialog;



    private static String url_create_product = "http://asli.esy.es/insert.php";

    // JSON Node names
    private static final String TAG_SUCCESS = "success";


    public void intialize_Calendar(){
        calendar = (CalendarView) findViewById(R.id.calendarView);
        calendar.setShowWeekNumber(false);
        calendar.setFirstDayOfWeek(2);
        //The background color for the selected week.
        calendar.setSelectedWeekBackgroundColor(getResources().getColor(R.color.green));

        //sets the color for the dates of an unfocused month.
        calendar.setUnfocusedMonthDateColor(getResources().getColor(R.color.transparent));

        //sets the color for the separator line between weeks.
        calendar.setWeekSeparatorLineColor(getResources().getColor(R.color.transparent));

        //sets the color for the vertical bar shown at the beginning and at the end of the selected date.
        calendar.setSelectedDateVerticalBar(R.color.darkgreen);

        //sets the listener to be notified upon selected date change.
        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            //show the selected date as a toast
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int day) {
                year_=year;
                month_=month;
                day_=day;
            }
        });
    }





    public void getGeoLocation(String address){

        long_et = (EditText) findViewById(R.id.long_et);
        lat_et = (EditText) findViewById(R.id.lat_et);
        AddressLongLatHandler handler = new AddressLongLatHandler(long_et,lat_et);
        try {
            AddressGeocodeFind.getGeoCodeForAddress(address,handler);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.blooddonate_signup);
        //set custom title bar
        LinearLayout blooddonate_titlebar = (LinearLayout) findViewById(R.id.blooddonate_titlebar);
        intialize_Calendar();
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.blooddonate_titlebar);
        titlebartext = (TextView) findViewById(R.id.blooddonate_titlebar_text);
        titlebartext.setText("Register");
        //assign variables
        name_et = (EditText) findViewById(R.id.name_et);
        username_et_register = (EditText) findViewById(R.id.username_et_register);
        password_et_register = (EditText) findViewById(R.id.password_et_register);
        repeatpassword_et_register = (EditText) findViewById(R.id.repeatpassword_et_register);
        gender_rg = (RadioGroup) findViewById(R.id.gender_rg);
        gender_rg.check(R.id.male_rb);
        bloodgroupselect_sp = (Spinner) findViewById(R.id.bloodgroupselect_sp);
        mobile_et = (EditText) findViewById(R.id.mobile_et);
        email_et = (EditText) findViewById(R.id.email_et);
        state_et = (EditText) findViewById(R.id.state_et);
        city_et = (EditText) findViewById(R.id.city_et);
        pincode_et = (EditText) findViewById(R.id.pincode_et);
        address_et = (EditText) findViewById(R.id.address_et);
        signup_btn_register = (Button) findViewById(R.id.signup_btn_register);


        name_et.addTextChangedListener(new TextValidator(name_et) {
            @Override public void validate(TextView textView, String text) {
                Validation.hasText(name_et);
            }
        });

        username_et_register.addTextChangedListener(new TextValidator(username_et_register) {
            @Override public void validate(TextView textView, String text) {
                Validation.hasText(username_et_register);
            }
        });

        password_et_register.addTextChangedListener(new TextValidator(password_et_register) {
            @Override public void validate(TextView textView, String text) {
                Validation.hasText(password_et_register);
            }
        });

        repeatpassword_et_register.addTextChangedListener(new TextValidator(repeatpassword_et_register) {
            @Override public void validate(TextView textView, String text) {
                Validation.checkPassWordAndConfirmPassword(password_et_register, repeatpassword_et_register);
            }
        });

        mobile_et.addTextChangedListener(new TextValidator(mobile_et) {
            @Override public void validate(TextView textView, String text) {
                Validation.isPhoneNumber(mobile_et, true);
            }
        });

        email_et.addTextChangedListener(new TextValidator(email_et) {
            @Override public void validate(TextView textView, String text) {
                Validation.isEmailAddress(email_et, true);
            }
        });

        state_et.addTextChangedListener(new TextValidator(state_et) {
            @Override public void validate(TextView textView, String text) {
                Validation.hasText(state_et);
            }
        });
        city_et.addTextChangedListener(new TextValidator(city_et) {
            @Override public void validate(TextView textView, String text) {
                Validation.hasText(city_et);
            }
        });
        pincode_et.addTextChangedListener(new TextValidator(pincode_et) {
            @Override public void validate(TextView textView, String text) {
                Validation.hasText(pincode_et);
            }
        });

        address_et.addTextChangedListener(new TextValidator(address_et) {
            @Override public void validate(TextView textView, String text) {
                Validation.hasText(address_et);
                getGeoLocation(textView.getText().toString());

            }
        });

        signup_btn_register.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        if ( checkValidation () )
                            submitForm();
                        else
                            Toast.makeText(getApplicationContext(), "Form contains error", Toast.LENGTH_LONG).show();
                    }
                }
        );

    }


    private boolean checkValidation() {
        boolean ret = true;
        if (!Validation.hasText(name_et)) ret = false;
        if (!Validation.hasText(username_et_register)) ret = false;
        if (!Validation.hasText(password_et_register)) ret = false;
        if (!Validation.checkPassWordAndConfirmPassword(password_et_register, repeatpassword_et_register)) ret = false;
        if (!Validation.isPhoneNumber(mobile_et, true)) ret = false;
        if (!Validation.isEmailAddress(email_et, true)) ret = false;
        if (!Validation.hasText(state_et)) ret = false;
        if (!Validation.hasText(city_et)) ret = false;
        if (!Validation.hasText(pincode_et)) ret = false;
        if (!Validation.hasText(address_et)) ret = false;
        return ret;
    }

    public void submitForm() {

        int selected = gender_rg.getCheckedRadioButtonId();
        gender_selected = (RadioButton) findViewById(selected);
        String dateofBirth = year_+"-"+month_+"-"+day_;


        User user = new User(name_et.getText().toString(),
                username_et_register.getText().toString(),
                password_et_register.getText().toString(),
                gender_selected.getText().toString(),
                dateofBirth,
                bloodgroupselect_sp.getSelectedItem().toString(),
                mobile_et.getText().toString(),
                email_et.getText().toString(),
                state_et.getText().toString(),
                Integer.parseInt(pincode_et.getText().toString()),
                city_et.getText().toString(),
                address_et.getText().toString(),
                Double.parseDouble(long_et.getText().toString()),
                Double.parseDouble(lat_et.getText().toString()));

        UserSignUpHandler signUpHandler = new UserSignUpHandler(blooddonate_signup.this,user);
        try {
            UserSignupService.signUpService(signUpHandler);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
