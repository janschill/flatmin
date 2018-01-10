package com.restful.jssr.jerseyclient;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.SimpleAdapter;
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
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class ExpensesActivity extends AppCompatActivity {

    private final static String TAG = "ExpensesActivity";

    private String url = "";
    private String urlUsers = "";
    private String token;
    ArrayList<HashMap<String, String>> expensesList;
    ArrayList<HashMap<String, String>> usersList;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expenses);

        String baseURL = ((Globals) this.getApplication()).getBASE_URL();
        String expensesURL = ((Globals) this.getApplication()).getEXPENSES_URL();
        String usersURL = ((Globals) this.getApplication()).getUSERS_URL();
        urlUsers = baseURL + usersURL;
        url = baseURL + expensesURL;
        Log.d(TAG, url);

        Intent intent = getIntent();
        token = intent.getStringExtra("token");
        Log.d(TAG, "Token from main: " + token);

        expensesList = new ArrayList<>();
        usersList = new ArrayList<>();

        listView = (ListView) findViewById(R.id.eListView);

        new ExpenseList().execute();
        new UsersList().execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_expense, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add_expense:

                final EditText title = new EditText(this);
                title.setHint("Title");
                final EditText expense = new EditText(this);
                expense.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
                expense.setHint("Value");

                LinearLayout layout = new LinearLayout(this);
                layout.setOrientation(LinearLayout.VERTICAL);
                layout.addView(title);
                layout.addView(expense);


                final String[] usernames = new String[usersList.size()];
                final boolean[] checked = new boolean[usersList.size()];

                final List<String> usernameList = Arrays.asList(usernames);

                int i = 0;
                for (HashMap<String, String> map : usersList) {
                    usernames[i] = map.get("username");
                    checked[i] = true;
//                    RadioButton radioButton = new RadioButton(this);
//                    radioButton.setId(Integer.parseInt(map.get("idusers")));
//                    radioButton.setText(map.get("username"));
                    i++;
//                    Log.e(TAG, "" + radioButton.getText());
//                    Log.e(TAG, "" + radioButton.getId());
                }

                AlertDialog dialog = new AlertDialog.Builder(this)
                        .setTitle("New expense")
                        .setView(layout)
                        .setMultiChoiceItems(usernames, checked, new DialogInterface.OnMultiChoiceClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i, boolean b) {
                                checked[i] = b;

                                String current = usernameList.get(i);

                                Toast.makeText(getApplicationContext(), current + ' ' + checked, Toast.LENGTH_SHORT);
                            }
                        })
                        .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String betrag = String.valueOf(expense.getText());
                                String notiz = String.valueOf(title.getText());

                                JSONArray debtor = new JSONArray();
                                JSONObject jsonObject = new JSONObject();

                                try {
                                    jsonObject.put("betrag", betrag);
                                    jsonObject.put("token", token);
                                    jsonObject.put("notiz", notiz);
                                    for (int i = 0; i < usernames.length; i++) {
                                        if (checked[i])
                                            debtor.put(new JSONObject().put("username", usernames[i]));
                                    }
                                    jsonObject.put("debtor", debtor);

                                } catch (Exception e) {

                                }

                                Log.d(TAG, "expense " + jsonObject.toString());

                                new AddNewExpense().execute(jsonObject.toString());
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


    private class AddNewExpense extends AsyncTask<String, Void, String> {

        HttpURLConnection httpURLConnection = null;
        DataOutputStream output;

        @Override
        protected String doInBackground(String... params) {
            Log.d(TAG, "" + params[0]);
            String data = "";

            try {
                Log.d(TAG, "URL: _____ " + url);
                Log.d(TAG, "token:" + token);
                httpURLConnection = (HttpURLConnection) new URL(url).openConnection();
                httpURLConnection.setRequestProperty("token", token);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setRequestProperty("Content-type", "application/json");

                output = new DataOutputStream(httpURLConnection.getOutputStream());
                output.writeBytes(params[0]);
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
                expensesList.clear();
                new ExpenseList().execute();
            }

            Log.e("TAG", result);
        }
    }


    private class UsersList extends AsyncTask<Void, Void, Void> {

        String response = null;

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                URL _url = new URL(urlUsers);
                HttpURLConnection httpURLConnection = (HttpURLConnection) _url.openConnection();
                httpURLConnection.setRequestProperty("token", token);
                httpURLConnection.setRequestMethod("GET");
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

                        String idusers = c.getString("idusers");
                        user.put("idusers", idusers);
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
        }
    }

    private class ExpenseList extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {

            String response = null;

            try {
                URL _url = new URL(url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) _url.openConnection();
                httpURLConnection.setRequestMethod("GET");
                InputStream inputStream = new BufferedInputStream(httpURLConnection.getInputStream());
                response = convertStreamToString(inputStream);

            } catch (Exception e) {
                e.printStackTrace();
            }

            if (response != null) {
                try {

                    JSONArray expensesArray = new JSONArray(response);

                    for (int i = 0; i < expensesArray.length(); i++) {
                        JSONObject c = expensesArray.getJSONObject(i);
                        HashMap<String, String> expense = new HashMap<>();

                        String betrag = c.getString("betrag") + "€";
                        expense.put("betrag", betrag);
                        String datum = c.getString("datum");
                        expense.put("datum", datum);
                        String idusers = c.getString("idusers");
                        expense.put("idusers", idusers);

                        if (c.has("notiz")) {
                            String notiz = c.getString("notiz");
                            expense.put("notiz", notiz);
                        }
//                        JSONObject links = c.getJSONObject("links");
//                        String link = links.getString("link");
//                        String rel = links.getString("rel");


                        expensesList.add(expense);
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
                    ExpensesActivity.this, expensesList,
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