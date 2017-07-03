package com.dapperapps.ciandroid;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;

import java.io.File;

import network.RestClient;

public class SampleApplication extends Application {
    public static String TAG = "SampleApplication";

    private static SampleApplication instance;

    private static RestClient restClient;

    public static Context context;

    private static File cacheDir;
    public static boolean DUMMY_DATA_FLAG = false;

    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        super.onCreate();
        instance = this;
        SampleApplication.context = getApplicationContext();
        initializeRestClient();
    }

    public SampleApplication() {
    }

    public static SampleApplication getInstance() {
        return SampleApplication.instance;
    }

    public static Context getAppContext() {
        return instance;
    }

    public static Resources getAppResources() {
        return instance.getResources();
    }

    private void initializeRestClient() {
        restClient = new RestClient();
    }

    public static RestClient getRestClient() {
        return restClient;
    }


}