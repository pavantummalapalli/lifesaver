package com.example.ali.bloodbank;

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

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by ALI on 26/06/2015.
 */
public class blooddonate_search extends ActionBarActivity {

    public static final String MyPREFERENCES = "MyPrefs";
    public static final String usernamekey="username";

    TextView titlebartext; EditText city_et_search; Spinner bloodgroupselect_sp_search;
    Button filter_btn; ListView listView;

    private ProgressDialog pDialog;
    JSONParser jsonParser = new JSONParser();
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
                    new loadall().execute();
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

    class loadall extends AsyncTask<String,String,String>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(blooddonate_search.this);
            pDialog.setMessage("Filtering Your Result...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        protected String doInBackground(String... args) {
            SharedPreferences sharedPreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
            String ss=sharedPreferences.getString(usernamekey,"");
            String bloodgroup=bloodgroupselect_sp_search.getSelectedItem().toString();
            String city=city_et_search.getText().toString();
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("bloodgroup", bloodgroup));
            params.add(new BasicNameValuePair("city", city));
            params.add(new BasicNameValuePair("username", ss));

            JSONObject json = jsonParser.makeHttpRequest(url_fetch_data, "POST", params);

            try {
                int success = json.getInt(TAG_SUCCESS);

                if (success == 1) {
                    JSONArray jsonArray = json.getJSONArray(TAG_ARRAY);
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
                        arrlist.add(hm);
                    }

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
                pDialog.dismiss();
                ListAdapter adapter = new SimpleAdapter(blooddonate_search.this,arrlist,R.layout.list_items, new String[]{"username","mobile","bloodgroup"},
                        new int[]{R.id.username_tv_listitem,R.id.mobile_tv_listitem,R.id.bloodgroup_tv_listitem});

                listView.setAdapter(adapter);
            }
            else
            {
                pDialog.dismiss();
                Toast.makeText(getApplicationContext(),"No Result Found",Toast.LENGTH_LONG).show();
            }
        }




    }



}
