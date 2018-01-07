package com.jssr.flatminclient;

import android.app.Application;

public class Globals extends Application {

    private final String BASE_URL = "http://192.168.178.74:8080/flatmin2/app/";
    private final String INCOME_URL = "income/";
    private final String EXPENSES_URL = "expenses/";
    private final String SHOPPINGLIST_URL = "shoppinglist/";
    private final String USERS_URL = "users/";
    private final String AUTHENTICATE_URL = "authenticate/";

    public Globals() {

    }

    public String getBASE_URL() {
        return this.BASE_URL;
    }

    public String getINCOME_URL() {
        return INCOME_URL;
    }

    public String getEXPENSES_URL() {
        return EXPENSES_URL;
    }

    public String getSHOPPINGLIST_URL() {
        return SHOPPINGLIST_URL;
    }

    public String getUSERS_URL() {
        return USERS_URL;
    }

    public String getAUTHENTICATE_URL() { return AUTHENTICATE_URL; }
}
