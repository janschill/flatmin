package com.restful.jssr.jerseyclient;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;


public class ShoppingListActivity extends AppCompatActivity {

    private final static String TAG = "ShoppingListActivity";

    String token;
    private String url = "";
    private ArrayList<HashMap<String, String>> shoppingList;
    String baseURL;
    String shoppinglistURL;

    ListView shoppingListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_list);

        shoppingListView = (ListView) findViewById(R.id.shoppingListView);

        baseURL = ((Globals) this.getApplication()).getBASE_URL();
        shoppinglistURL = ((Globals) this.getApplication()).getSHOPPINGLIST_URL();
        url = baseURL + shoppinglistURL;
        Log.d(TAG, url);

        Intent intent = getIntent();
        token = intent.getStringExtra("token");
        Log.d(TAG, token);

        shoppingList = new ArrayList<>();

        new ShoppingList().execute();
    }

    public void deleteItem(View view) {
        View parent = (View) view.getParent();
        TextView itemTextView = (TextView) parent.findViewById(R.id.item);
        String item = String.valueOf(itemTextView.getText());
        String id;

        for (HashMap<String, String> map : shoppingList) {
            if (map.get("item").equals(item)) {
                id = map.get("idtodolist");

                new DeleteItem().execute(id);

                return;
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_shoppingitem, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add_shoppingitem:
                final EditText taskEditText = new EditText(this);
                AlertDialog dialog = new AlertDialog.Builder(this)
                        .setTitle("New item")
                        .setView(taskEditText)
                        .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String task = String.valueOf(taskEditText.getText());
                                new AddNewItem().execute(task);
                                Log.d(TAG, "Task to add: " + task);
                            }
                        })
                        .setNegativeButton("Cancel", null)
                        .create();
                dialog.show();


                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private class DeleteItem extends AsyncTask<String, Void, String> {

        HttpURLConnection httpURLConnection = null;

        @Override
        protected String doInBackground(String... params) {
            String data = "";

            try {
                Log.d(TAG, "URL: _____ " + url + params[0]);
                Log.d(TAG, "_ " + token);
                httpURLConnection = (HttpURLConnection) new URL(url + params[0]).openConnection();
                httpURLConnection.setRequestProperty("token", token);
                httpURLConnection.setRequestMethod("DELETE");
                httpURLConnection.setRequestProperty("Content-type", "application/json");

                InputStream in = httpURLConnection.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(in);

                int inputStreamData = inputStreamReader.read();
                while (inputStreamData != -1) {
                    char current = (char) inputStreamData;
                    inputStreamData = inputStreamReader.read();
                    data += current;
                }

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (httpURLConnection != null) {
                    httpURLConnection.disconnect();
                }
            }

            return data;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            if (result != null || !result.equals("")) {
                shoppingList.clear();
                new ShoppingList().execute();
            }

            Log.e("TAG", result);
        }
    }

    private class AddNewItem extends AsyncTask<String, Void, String> {

        HttpURLConnection httpURLConnection = null;
        DataOutputStream output;

        @Override
        protected String doInBackground(String... params) {
            Log.d(TAG, "execute: " + params[0]);
            JSONObject jsonObject = new JSONObject();
            String data = "";

            try {
                jsonObject.put("item", params[0].toString());
                Log.d(TAG, "URL: _____ " + url);
                Log.d(TAG, "_ " + jsonObject.toString());
                Log.d(TAG, "_ " + token);
                httpURLConnection = (HttpURLConnection) new URL(url).openConnection();
                httpURLConnection.setRequestProperty("token", token);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setRequestProperty("Content-type", "application/json");

                output = new DataOutputStream(httpURLConnection.getOutputStream());
                output.writeBytes(jsonObject.toString());
                output.flush();
                output.close();

                InputStream in = httpURLConnection.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(in);

                int inputStreamData = inputStreamReader.read();
                while (inputStreamData != -1) {
                    char current = (char) inputStreamData;
                    inputStreamData = inputStreamReader.read();
                    data += current;
                }

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (httpURLConnection != null) {
                    httpURLConnection.disconnect();
                }
            }

            return data;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            if (result != null || !result.equals("")) {
                shoppingList.clear();
                new ShoppingList().execute();
            }

            Log.e("TAG", result);
        }
    }

    private class ShoppingList extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... strings) {

            String response = null;

            try {
                URL _url = new URL(url);
                Log.d(TAG, "URL: _____ " + url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) _url.openConnection();
                httpURLConnection.setRequestProperty("token", token);
                httpURLConnection.setRequestMethod("GET");
                InputStream inputStream = new BufferedInputStream(httpURLConnection.getInputStream());
                response = convertStreamToString(inputStream);

            } catch (Exception e) {

            }

            if (response != null) {
                try {

                    JSONArray shoppingListArray = new JSONArray(response);

                    for (int i = 0; i < shoppingListArray.length(); i++) {
                        JSONObject c = shoppingListArray.getJSONObject(i);
                        HashMap<String, String> shoppingItem = new HashMap<>();

                        String id = c.getString("idtodolist");
                        shoppingItem.put("idtodolist", id);
                        String item = c.getString("item");
                        shoppingItem.put("item", item);

//                        JSONObject links = c.getJSONObject("links");
//                        String link = links.getString("link");
//                        String rel = links.getString("rel");

                        shoppingList.add(shoppingItem);
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
                    ShoppingListActivity.this, shoppingList,
                    R.layout.single_shoppingitem, new String[]{"item"}, new int[]{R.id.item});

            shoppingListView.setAdapter(adapter);
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