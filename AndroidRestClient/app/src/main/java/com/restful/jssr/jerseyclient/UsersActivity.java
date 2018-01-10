package com.restful.jssr.jerseyclient;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public class UsersActivity extends AppCompatActivity {

    private final static String TAG = "UsersActivity";

    String token;
    private String url = "";
    private ArrayList<HashMap<String, String>> usersList;
    ListView userListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);

        userListView = (ListView) findViewById(R.id.usersListView);

        String baseURL = ((Globals) this.getApplication()).getBASE_URL();
        String usersURL = ((Globals) this.getApplication()).getUSERS_URL();
        url = baseURL + usersURL;
        Log.d(TAG, url);

        Intent intent = getIntent();
        token = intent.getStringExtra("token");
        Log.d(TAG, token);

        usersList = new ArrayList<>();

        new DataHandler().execute();
    }


    private class DataHandler extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... strings) {

            String response = null;

            try {
                URL _url = new URL(url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) _url.openConnection();
                httpURLConnection.setRequestProperty("token", token);
                httpURLConnection.setRequestMethod("GET");
//                httpURLConnection.setDoOutput(true);
                InputStream inputStream = new BufferedInputStream(httpURLConnection.getInputStream());
                response = convertStreamToString(inputStream);

            } catch (Exception e) {

            }

            if (response != null) {
                try {

                    JSONArray usersListArray = new JSONArray(response);

                    for (int i = 0; i < usersListArray.length(); i++) {
                        JSONObject c = usersListArray.getJSONObject(i);
                        HashMap<String, String> user = new HashMap<>();

                        String first = c.getString("first");
                        user.put("first", first);
                        String last = c.getString("last");
                        user.put("last", last);
                        String email = c.getString("email");
                        user.put("email", email);
                        String username = c.getString("username");
                        user.put("username", username);

//                        JSONObject links = c.getJSONObject("links");
//                        String link = links.getString("link");
//                        String rel = links.getString("rel");

                        usersList.add(user);
                    }
                } catch (final JSONException e) {
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

            ListAdapter adapter = new SimpleAdapter(
                    UsersActivity.this, usersList,
                    R.layout.single_user, new String[]{"first", "last",
                    "email", "username"}, new int[]{R.id.first,
                    R.id.last, R.id.email, R.id.username});

            userListView.setAdapter(adapter);
        }


        public String convertStreamToString(InputStream inputStream) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder sb = new StringBuilder();

            String line;
            try {
                while ((line = reader.readLine()) != null) {
                    sb.append(line).append('\n');
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return sb.toString();
        }
    }
}