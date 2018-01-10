package com.restful.jssr.jerseyclient;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class RegisterActivity extends AppCompatActivity {

    private static final String TAG = "SignupActivity";

    private String url = "";

    EditText _firstText;
    EditText _lastText;
    EditText _emailText;
    EditText _usernameText;
    EditText _passwordText;
    Button _signupButton;
    TextView _loginLink;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        String baseURL = ((Globals) this.getApplication()).getBASE_URL();
        String usersURL = ((Globals) this.getApplication()).getUSERS_URL();
        url = baseURL + usersURL;

        _firstText = (EditText) findViewById(R.id.input_first);
        _lastText = (EditText) findViewById(R.id.input_last);
        _emailText = (EditText) findViewById(R.id.input_email);
        _usernameText = (EditText) findViewById(R.id.input_username);
        _passwordText = (EditText) findViewById(R.id.input_password);
        _signupButton = (Button) findViewById(R.id.btn_signup);
        _loginLink = (TextView) findViewById(R.id.link_login);

        _signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
            }
        });

        _loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void signup() {
        Log.d(TAG, "Signup");

        if (!validate()) {
            onSignupFailed();
            return;
        }

        _signupButton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(RegisterActivity.this,
                R.style.ThemeOverlay_AppCompat_Dark);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Creating Account...");
        progressDialog.show();

        final JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("first", _firstText.getText().toString());
            jsonObject.put("last", _lastText.getText().toString());
            jsonObject.put("email", _emailText.getText().toString());
            jsonObject.put("username", _usernameText.getText().toString());
            jsonObject.put("password", _passwordText.getText().toString());
            Log.d(TAG, jsonObject.toString());

        } catch (Exception e) {
        }


        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {

                        new DataHandler().execute(jsonObject.toString());

                        progressDialog.dismiss();
                    }
                }, 3000);
    }


    public void onSignupSuccess() {
        _signupButton.setEnabled(true);
        finish();
    }

    public void onSignupFailed() {
        Toast.makeText(getBaseContext(), "Sign up failed", Toast.LENGTH_LONG).show();
        _signupButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String first = _firstText.getText().toString();
        String last = _lastText.getText().toString();
        String email = _emailText.getText().toString();
        String username = _usernameText.getText().toString();
        String password = _passwordText.getText().toString();

        if (first.isEmpty() || first.length() < 3) {
            _firstText.setError("at least 3 characters");
            valid = false;
        } else {
            _firstText.setError(null);
        }

        if (last.isEmpty() || last.length() < 3) {
            _lastText.setError("at least 3 characters");
            valid = false;
        } else {
            _lastText.setError(null);
        }

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _emailText.setError("enter a valid email address");
            valid = false;
        } else {
            _emailText.setError(null);
        }

        if (username.isEmpty() || username.length() < 3) {
            _usernameText.setError("at least 3 characters");
            valid = false;
        } else {
            _usernameText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4) {
            _passwordText.setError("more than 4 characters");
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        return valid;
    }

    private class DataHandler extends AsyncTask<String, Void, Integer> {
        HttpURLConnection httpURLConnection = null;
        DataOutputStream output;
        String data = "";
        int code = 500;

        @Override
        protected Integer doInBackground(String... params) {

            try {
                httpURLConnection = (HttpURLConnection) new URL(url).openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setRequestProperty("Content-type", "application/json");

                Log.d(TAG, "" + params[0]);
                output = new DataOutputStream(httpURLConnection.getOutputStream());
                output.writeBytes(params[0]);
                code = httpURLConnection.getResponseCode();
                Log.d(TAG, "" + code);
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
                Log.d(TAG, "" + data);

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (httpURLConnection != null) {
                    httpURLConnection.disconnect();
                }
            }
            return code;
        }

        @Override
        protected void onPostExecute(Integer result) {
            super.onPostExecute(result);

            Log.e(TAG, "" + result);
            if (result == 201) {
                Toast.makeText(getBaseContext(), "Sign up successful", Toast.LENGTH_LONG).show();
                onSignupSuccess();
            } else
                onSignupFailed();
        }
    }
}

