package com.restful.jssr.jerseyclient;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private final String TAG = "MainActivity";
    private String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        token = intent.getStringExtra("token");
        int start = token.indexOf(":") + 2;
        token = token.substring(start, token.length() - 2);
        Log.d(TAG, "Token from main: " + token);

    }

    public void callIncome(View view) {
        View parent = (View) view.getParent();
        Intent intent = new Intent(getApplicationContext(), IncomeActivity.class);
        intent.putExtra("token", token);
        startActivity(intent);
    }

    public void callExpenses(View view) {
        View parent = (View) view.getParent();
        Intent intent = new Intent(getApplicationContext(), ExpensesActivity.class);
        intent.putExtra("token", token);
        startActivity(intent);
    }

    public void callUsers(View view) {
        View parent = (View) view.getParent();
        Intent intent = new Intent(getApplicationContext(), UsersActivity.class);
        intent.putExtra("token", token);
        startActivity(intent);
    }

    public void callShoppingList(View view) {
        View parent = (View) view.getParent();
        Intent intent = new Intent(getApplicationContext(), ShoppingListActivity.class);
        intent.putExtra("token", token);
        startActivity(intent);
    }

}
