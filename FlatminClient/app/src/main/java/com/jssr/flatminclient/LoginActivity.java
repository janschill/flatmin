package com.jssr.flatminclient;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.jssr.flatminclient.R;

import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;
    public static String token = null;
    private String url = "";

    JSONObject jsonObject = new JSONObject();
    Button _loginButton;
    EditText _passwordText;
    TextView _signupLink;
    EditText _usernameText;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        String baseURL = ((Globals) this.getApplication()).getBASE_URL();
        String authenticateURL = ((Globals) this.getApplication()).getAUTHENTICATE_URL();
        url = baseURL + authenticateURL;

    }

    public void login() {
        Log.d(TAG, "Login");

        }




    @Override
    public void onBackPressed() {
        // disable going back to the MainActivity
        moveTaskToBack(true);
    }


}