package com.dapperapps.ciandroid;


import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.CompositeMultiplePermissionsListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.karumi.dexter.listener.multi.SnackbarOnAnyDeniedMultiplePermissionsListener;

import org.json.JSONObject;

import java.util.Calendar;

import network.AppConstants;
import network.AppUtil;
import network.LoginResponse2;
import network.RegisteredRequest;
import network.RestCallbackObject;
import network.ServerCodes;
import network.ServerConnectListener;
import network.ServerConnectListenerObject;
import network.ServerResponse;
import network.SharedPrefUtility;
import retrofit2.Call;

public class PhoneNumberActivity extends Activity implements ServerConnectListenerObject {

    Button btn_phone_number;
    EditText et_phone_number;
    ImageView mIvEnterPhone;

    private FusedLocationProviderClient mFusedLocationClient;

    protected Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_number);
        mContext = this;
        et_phone_number = (EditText) findViewById(R.id.et_phone_number);
        mIvEnterPhone = (ImageView) findViewById(R.id.iv_enter_phone);

        btn_phone_number = (Button) findViewById(R.id.btn_phone_number);
        executeSignInRequest();
        btn_phone_number.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (et_phone_number.getEditableText().toString().length() == 11) {
                    requestRegister(et_phone_number.getEditableText().toString());
                } else {
                    et_phone_number.setError("دراست موبائل نمبر ڈالیں");
                }


            }
        });
        mIvEnterPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppUtil.playAudio(mContext, "0.mp4");


            }
        });
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

                        }
                    }
                });


    }

    private void requestRegister(String mobileNumber) {
        RegisteredRequest obj = new RegisteredRequest();
        RegisteredRequest.User user = obj.new User();
        user.setLatitude("" + mLocation.getLatitude());
        user.setLongitude("" + mLocation.getLongitude());
        user.setMobile_number(mobileNumber);
        obj.setUser(user);

        Call<Object> call = SampleApplication.getRestClient().getApiService().login(obj);
        call.enqueue(new RestCallbackObject<Object>(this, ServerCodes.ServerRequestCodes.LOGIN_REQUEST_CODE,
                SampleApplication.getAppContext()));
    }

    @Override
    public void onSuccess(Object response) {
        //NotificationsCountResponse notificationsCountObj = (NotificationsCountResponse) response;
        Log.e("hp-onSuccess", "UpdateLiveFeedCountService");

        String s = response.toString();
        try {
            JSONObject obj = new JSONObject(s);
            SharedPrefUtility.getInstance(mContext).savePrefrences(AppConstants.USER_ID, (String) obj.get("id"));
        } catch (Exception e) {

        }
        // ShortcutBadger library added, Only this line added for showing badge on launcher.
//        ShortcutBadger.with(getApplicationContext()).count((notificationsCountObj.getData().getMyFeedsCount()));
//        Bundle bundle = new Bundle();
//        AppPreference.saveInt(FollowitApplication.getAppContext(), notificationsCountObj.getData().getMyFeedsCount(), AppBundles.MY_FEED_COUNT.getKey());
//        AppPreference.saveInt(FollowitApplication.getAppContext(), notificationsCountObj.getData().getMessagesCount(), AppBundles.MESSAGES_COUNT.getKey());
//        bundle.putInt(AppBundles.MY_FEED_COUNT.getKey(), notificationsCountObj.getData().getMyFeedsCount());
//        bundle.putInt(AppBundles.MESSAGES_COUNT.getKey(), notificationsCountObj.getData().getMessagesCount());
//        EventsListeners.getInstance().broadCastEvent(
//                ListinerCategory.HOME_MENU_NEW, ChangeEvents.NOTIFICATIONS_COUNT, bundle);
        this.finish();
    }

    @Override
    public void onFailure(Throwable response) {

    }

}
