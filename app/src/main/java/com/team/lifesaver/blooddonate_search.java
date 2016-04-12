package com.team.lifesaver;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.team.lifesaver.handlers.UsersSearchHandler;
import com.team.lifesaver.representations.User;
import com.team.lifesaver.services.UsersSearchService;
import org.json.JSONException;
import java.util.ArrayList;
import java.util.HashMap;


/**
 * Created by ALI on 26/06/2015.
 */
public class blooddonate_search extends ActionBarActivity {

    public static final String MyPREFERENCES = "MyPrefs";
    public static final String usernamekey="username";

    TextView titlebartext; EditText city_et_search; Spinner bloodgroupselect_sp_search;
    Button filter_btn; ListView listView;

    private ProgressDialog pDialog;

    private static String url_fetch_data = "http://asli.esy.es/filter.php";
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_ARRAY = "userdata";

    ArrayList<HashMap<String,String> > arrlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.blooddonate_search);

        //titlebar
        LinearLayout blooddonate_titlebar=(LinearLayout) findViewById(R.id.blooddonate_titlebar);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.blooddonate_titlebar);
        titlebartext=(TextView) findViewById(R.id.blooddonate_titlebar_text);
        titlebartext.setText("Donors Filter");

        city_et_search=(EditText) findViewById(R.id.city_et_search);
        bloodgroupselect_sp_search=(Spinner) findViewById(R.id.bloodgroupselect_sp_search);
        filter_btn=(Button) findViewById(R.id.filter_btn);
        listView=(ListView) findViewById(R.id.listView);

        arrlist=new ArrayList<HashMap<String, String>>();

        filter_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (city_et_search.getText().toString().equals(""))
                {
                    Toast.makeText(getApplicationContext(),"City Name Should Not be NULL", Toast.LENGTH_LONG).show();
                }
                else
                {
                    arrlist.clear();
                    ListAdapter adapter = new SimpleAdapter(blooddonate_search.this,arrlist,R.layout.list_items, new String[]{"username","mobile","bloodgroup"},
                            new int[]{R.id.username_tv_listitem,R.id.mobile_tv_listitem,R.id.bloodgroup_tv_listitem});

                    listView.setAdapter(adapter);
                    doUsersUsearch();
                }
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i=new Intent(blooddonate_search.this,user_profile.class);
                String ppp=((TextView) view.findViewById(R.id.username_tv_listitem)).getText().toString();
                i.putExtra("username",ppp);
                startActivity(i);
            }
        });

    }

    public void doUsersUsearch(){
        String bloodgroup=bloodgroupselect_sp_search.getSelectedItem().toString();
        String city=city_et_search.getText().toString();
        User user = new User();
        user.setBloodgroup(bloodgroup);
        user.setCity(city);
        UsersSearchHandler handler = new UsersSearchHandler(blooddonate_search.this,user,listView);
        try {
            UsersSearchService.usersSearchService(handler);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


}
