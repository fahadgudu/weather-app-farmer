package com.dapperapps.ciandroid;

import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.IBinder;
import android.support.v4.app.ActivityCompat;
import android.util.Log;


import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executor;

import network.LoginResponse2;
import network.RegisteredRequest;
import network.RestCallback;
import network.RestCallbackObject;
import network.ServerCodes;
import network.ServerConnectListener;
import network.ServerConnectListenerObject;
import network.ServerResponse;
import retrofit2.Call;


public class UpdateLiveFeedCountService extends Service implements ServerConnectListenerObject {
    Map<String, String> values = new HashMap<String, String>();
    private FusedLocationProviderClient mFusedLocationClient;

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        executeSignInRequest();
        return START_STICKY;
    }
    Location mLocation;
    private void executeSignInRequest() {
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mFusedLocationClient.getLastLocation()
                .addOnSuccessListener(new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {
                            mLocation = location;
                            requestRegister();
                        }
                    }
                });



    }

    private void requestRegister() {
        RegisteredRequest obj = new RegisteredRequest();
        RegisteredRequest.User user = obj.new User();
        user.setLatitude(""+mLocation.getLatitude());
        user.setLongitude(""+mLocation.getLongitude());
        obj.setUser(user);

        Call<Object> call = SampleApplication.getRestClient().getApiService().login(obj);
        call.enqueue(new RestCallbackObject<Object>(this, ServerCodes.ServerRequestCodes.LOGIN_REQUEST_CODE,
                SampleApplication.getAppContext()));
    }

    @Override
    public void onSuccess(Object response) {
        //NotificationsCountResponse notificationsCountObj = (NotificationsCountResponse) response;
        Log.e("hp-onSuccess","UpdateLiveFeedCountService");
        // ShortcutBadger library added, Only this line added for showing badge on launcher.
//        ShortcutBadger.with(getApplicationContext()).count((notificationsCountObj.getData().getMyFeedsCount()));
//        Bundle bundle = new Bundle();
//        AppPreference.saveInt(FollowitApplication.getAppContext(), notificationsCountObj.getData().getMyFeedsCount(), AppBundles.MY_FEED_COUNT.getKey());
//        AppPreference.saveInt(FollowitApplication.getAppContext(), notificationsCountObj.getData().getMessagesCount(), AppBundles.MESSAGES_COUNT.getKey());
//        bundle.putInt(AppBundles.MY_FEED_COUNT.getKey(), notificationsCountObj.getData().getMyFeedsCount());
//        bundle.putInt(AppBundles.MESSAGES_COUNT.getKey(), notificationsCountObj.getData().getMessagesCount());
//        EventsListeners.getInstance().broadCastEvent(
//                ListinerCategory.HOME_MENU_NEW, ChangeEvents.NOTIFICATIONS_COUNT, bundle);

    }

    @Override
    public void onFailure(Throwable response) {

    }
}