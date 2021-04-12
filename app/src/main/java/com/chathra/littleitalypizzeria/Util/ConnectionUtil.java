package com.chathra.littleitalypizzeria.Util;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.chathra.littleitalypizzeria.Helper.AlertBar;

public class ConnectionUtil {

    public static boolean isInternetAvailable(Context context, Activity activity){
        ConnectivityManager conMgr =  (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = conMgr.getActiveNetworkInfo();

        if(netInfo == null) {
            AlertBar.notifyDanger(activity, "Please enable internet connection!");
            return false;
        }
        else
            return true;
    }

}
