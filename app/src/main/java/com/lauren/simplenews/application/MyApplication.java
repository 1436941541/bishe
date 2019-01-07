package com.lauren.simplenews.application;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * 创建人： 杨云杰
 * 创建时间 ： 2018/12/27
 * 描述：
 */
public class MyApplication extends Application {
    private static Context mContext;
    @Override
    public void onCreate() {
        mContext = getApplicationContext();
        super.onCreate();
    }
    public static Context getContext(){
        return mContext;
    }
    public static boolean isNetworkConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable();
            }
        }
        return false;
    }
}
