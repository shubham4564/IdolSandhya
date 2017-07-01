package com.example.shubham.idolsandhya;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by shubham on 6/27/17.
 */

public class RecyclerActivity extends Activity {

    private String TAG = RecyclerActivity.class.getSimpleName();
    private ProgressDialog pDialog;
    private ListView lv;

    // URL to get contacts JSON
    private static String url = "https://www.googleapis.com/youtube/v3/search?key=AIzaSyDl2oUGOweKiHCGQ3jZU7wUBXIGVnRn5Yg&channelId=UCzNfKeIwWsYXlHzK51P4mBg&part=snippet,id&order=date&maxResults=6";
    ArrayList<HashMap<String, String>> videoIdList;


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_vote:
                    Toast.makeText(getApplicationContext(),
                            "Vote",
                            Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(RecyclerActivity.this , MainActivity.class);
                    startActivity(intent);

                    return true;
                case R.id.navigation_aboutme:
                    Toast.makeText(getApplicationContext(),
                            "About Me",
                            Toast.LENGTH_LONG).show();
                    Intent intent1 = new Intent(RecyclerActivity.this , aboutmeActivity.class);
                    startActivity(intent1);

                    return true;
                case R.id.navigation_social:

                    return true;
            }
            return false;
        }

    };


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.recycler_main);

        videoIdList = new ArrayList<>();
        lv = findViewById(R.id.lists);
        new getVideoId().execute();


        RecyclerView recyclerView=findViewById(R.id.list);
        recyclerView.setHasFixedSize(true);
        //to use RecycleView, you need a layout manager. default is LinearLayoutManager
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        RecyclerAdapter adapter=new RecyclerAdapter(RecyclerActivity.this);
        recyclerView.setAdapter(adapter);


        BottomNavigationView navigation = findViewById(R.id.navigation1);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }


    /**
     * Async task class to get json by making HTTP call
     */
    private class getVideoId extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(RecyclerActivity.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            // pDialog.show();

        }
      //private  ArrayList<HashMap<String, String>> a;
        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();

            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(url);

            Log.e(TAG, "Response from url: " + jsonStr);

            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    // Getting JSON Array node
                    JSONArray items = jsonObj.getJSONArray("items");

                    // looping through All VideoId
                    for (int i = 0; i < items.length(); i++) {
                        JSONObject c = items.getJSONObject(i);

                        // Id node is JSON Object
                        JSONObject id = c.getJSONObject("id");
                        String videoId = id.getString("videoId");


                        // tmp hash map for single videoId
                        HashMap<String, String> videoIds = new HashMap<>();

                        // adding each child node to HashMap key => value
                        videoIds.put("videoId", videoId);

                        // adding videoId to video Id list
                        videoIdList.add(videoIds);
                    }

                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG)
                                    .show();
                        }
                    });

                }
            } else {
                Log.e(TAG, "Couldn't get json from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Couldn't get json from server. Check LogCat for possible errors!",
                                Toast.LENGTH_LONG)
                                .show();
                    }
                });

            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            if (pDialog.isShowing())
                pDialog.dismiss();
            /**
             * Updating parsed JSON data into ListView
             * */
            ListAdapter adapter = new SimpleAdapter(
                    RecyclerActivity.this, videoIdList,
                    R.layout.list_item, new String[]{"videoId"}, new int[]{R.id.videoId});

            lv.setAdapter(adapter);

        }

    }
}
