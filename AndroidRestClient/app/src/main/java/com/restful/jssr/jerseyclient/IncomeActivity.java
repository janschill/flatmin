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

public class IncomeActivity extends AppCompatActivity {


    private final static String TAG = "IncomeActivity";
    private String url = "";
    ArrayList<HashMap<String, String>> incomeList;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_income);

        String baseURL = ((Globals) this.getApplication()).getBASE_URL();
        String incomeURL = ((Globals) this.getApplication()).getINCOME_URL();
        url = baseURL + incomeURL;
        Log.d(TAG, url);

        Intent intent = getIntent();
        String token = intent.getStringExtra("token");
        Log.d(TAG, "Token from main: " + token);

        incomeList = new ArrayList<>();

        listView = (ListView) findViewById(R.id.incomeListView);

        new IncomeList().execute(url);

    }

    private class IncomeList extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... arg0) {

            String response = null;

            try {
                URL _url = new URL(url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) _url.openConnection();
                httpURLConnection.setRequestMethod("GET");
                InputStream inputStream = new BufferedInputStream(httpURLConnection.getInputStream());
                response = convertStreamToString(inputStream);

            } catch (Exception e) {

            }

            if (response != null) {
                try {

                    JSONArray incomeArray = new JSONArray(response);

                    for (int i = 0; i < incomeArray.length(); i++) {
                        JSONObject c = incomeArray.getJSONObject(i);
                        HashMap<String, String> income = new HashMap<>();

                        String betrag = c.getString("betrag") + "€";
                        income.put("betrag", betrag);
                        String datum = c.getString("datum");
                        income.put("datum", datum);
                        String idusers = c.getString("idusers");
                        income.put("idusers", idusers);

                        if (c.has("notiz")) {
                            String notiz = c.getString("notiz");
                            income.put("notiz", notiz);
                        }
//                        JSONObject links = c.getJSONObject("links");
//                        String link = links.getString("link");
//                        String rel = links.getString("rel");


                        incomeList.add(income);
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
                    IncomeActivity.this, incomeList,
                    R.layout.single_income, new String[]{"betrag", "notiz",
                    "idusers", "datum"}, new int[]{R.id.betrag,
                    R.id.notiz, R.id.idusers, R.id.datum});

            listView.setAdapter(adapter);
        }

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